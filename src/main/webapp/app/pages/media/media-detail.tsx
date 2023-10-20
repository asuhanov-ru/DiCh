import React, { useEffect, useState, useMemo } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Row, Col } from 'reactstrap';
import RBush from 'rbush';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './media.reducer';
import { getEntity as getImageEntity } from './image/reducer';
import { getEntities as getPageOcr, doOcr } from './ocr/ocr_entity_reducer';
import { getEntities as getPageLayout } from './ocr/ocr_layout_reducer';
import { getEntities as getPageTextBlocks } from './ocr/text_block_reducer';
import { MediaPane } from './ocr/media_pane';
import { OutlinePane } from './ocr/outline_pane';
import { TextPane } from './ocr/text_pane';
import { mediaPaneDefaultState } from './config';

export const MediaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();
  // TO-DO Add spiner while data is fetching
  const isFetching = useAppSelector(state => state.media.loading || state.pageImageTransfer.loading);
  const mediaEntity = useAppSelector(state => state.media.entity);
  const ocrEntities = useAppSelector(state => state.ocrTransfer.entities);
  const imageTransfer = useAppSelector(state => state.pageImageTransfer.entity);

  const [currentPage, setCurrentPage] = useState<number>(1);
  const [highlighted, setHighlighted] = useState([]);

  const [selectedPolys, setSelectedPolys] = useState([]);

  const [isContentChanged, setIsContentChanged] = useState(false);

  const [mediaPaneState, setMediaPaneState] = useState<any>(mediaPaneDefaultState);

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  useEffect(() => {
    if (mediaEntity && mediaEntity.id) {
      dispatch(getImageEntity({ id: mediaEntity.id, pageNumber: currentPage }));
      dispatch(getPageOcr({ id: mediaEntity.id, pageNumber: currentPage }));
      dispatch(getPageLayout({ id: mediaEntity.id, pageNumber: currentPage }));
      dispatch(getPageTextBlocks({ id: mediaEntity.id, pageNumber: currentPage }));
      setMediaPaneState({ ...mediaPaneState, navigate: { page: currentPage, total: mediaEntity.lastPageNumber } });
    }
  }, [currentPage, mediaEntity]);

  const handleDoOcr = () => {
    dispatch(doOcr({ id: mediaEntity.id, pageNumber: currentPage }));
  };

  const handleSetPage = page => {
    if (page > 0 && page < mediaEntity?.lastPageNumber) {
      setCurrentPage(page);
    }
  };

  const polyTree = useMemo(() => {
    if (ocrEntities && ocrEntities.length > 0) {
      const tree = new RBush(ocrEntities.length);
      ocrEntities.map((el, idx) =>
        tree.insert({ minX: el.n_left, minY: el.n_top, maxX: el.n_left + el.n_width, maxY: el.n_top + el.n_heigth, foo: idx })
      );
      return tree;
    }
    return null;
  }, [ocrEntities]);

  const paneEnabled = (paneName: string): boolean => mediaPaneState.tab[paneName];

  const handleMediaPaneClick = ({ x, y }) => {
    if (polyTree) {
      const selectedItems = polyTree.search({
        minX: x,
        minY: y,
        maxX: x,
        maxY: y,
      });
      setSelectedPolys(selectedItems);
      return;
    }
    setSelectedPolys([]);
  };

  const handleDispatchCommand = ({ command, value }) => {
    switch (command) {
      case 'ocrSelected':
        handleDoOcr();
        break;
      default:
        break;
    }
  };

  return (
    <>
      <Row>
        <div style={{ display: 'flex', justifyContent: 'center', marginTop: '8px' }}>
          <h2 data-cy="mediaDetailsHeading">{mediaEntity.fileName}</h2>
        </div>
      </Row>

      <Row>
        <Col>
          <MediaPane
            image={`data:image/jpeg;base64,${imageTransfer?.image}`}
            highlights={highlighted}
            currentPage={currentPage}
            totalPages={mediaEntity?.lastPageNumber}
            setPage={handleSetPage}
            onClick={handleMediaPaneClick}
            currentEditorState={mediaPaneState}
            setEditorState={setMediaPaneState}
            dispatchCommand={handleDispatchCommand}
          />
        </Col>
        <Col>
          <>
            {paneEnabled('editor') && <TextPane setHighlighted={setHighlighted} />}
            {paneEnabled('outlines') && (
              <OutlinePane setPage={handleSetPage} currentPage={currentPage} currentEditorState={mediaPaneState} />
            )}
          </>
        </Col>
      </Row>
    </>
  );
};

export default MediaDetail;

import React, { useEffect, useState, useMemo } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Row, Col } from 'reactstrap';
import { convertFromRaw, EditorState, SelectionState } from 'draft-js';
import { Editor } from 'react-draft-wysiwyg';
import RBush from 'rbush';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import './styles.css';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './media.reducer';
import { getEntity as getImageEntity } from './image/reducer';
import { getEntities as getPageOcr } from './ocr/ocr_entity_reducer';
import { getEntities as getPageLayout } from './ocr/ocr_layout_reducer';
import { getEntities as getPageTextBlocks } from './ocr/text_block_reducer';
import { MediaPane } from './ocr/media_pane';
import { editorToolbarExtensions } from './config';
import { ToolGroup } from '../../shared/ui/toolbar/controls';

type Props = {
  onClick: () => void;
};

export const MediaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();
  // TO-DO Add spiner while data is fetching
  const isFetching = useAppSelector(state => state.media.loading || state.pageImageTransfer.loading);
  const mediaEntity = useAppSelector(state => state.media.entity);
  const ocrEntities = useAppSelector(state => state.ocrTransfer.entities);
  const imageTransfer = useAppSelector(state => state.pageImageTransfer.entity);
  const layoutTransfer = useAppSelector(state => state.ocrLayoutTransfer.entities);
  const pageTextBlocks = useAppSelector(state => state.textBlockTransfer.entities);

  const [currentPage, setCurrentPage] = useState<number>(1);
  const [editorState, setEditorState] = useState(EditorState.createEmpty());
  const [selectionState, setSelectionState] = useState(SelectionState.createEmpty());
  const [highlighted, setHighlighted] = useState([]);

  const [selectedPolys, setSelectedPolys] = useState([]);

  const [isContentChanged, setIsContentChanged] = useState(false);

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  useEffect(() => {
    if (mediaEntity && mediaEntity.id) {
      dispatch(getImageEntity({ id: mediaEntity.id, pageNumber: currentPage }));
      dispatch(getPageOcr({ id: mediaEntity.id, pageNumber: currentPage }));
      dispatch(getPageLayout({ id: mediaEntity.id, pageNumber: currentPage }));
      dispatch(getPageTextBlocks({ id: mediaEntity.id, pageNumber: currentPage }));
    }
  }, [currentPage, mediaEntity]);

  useEffect(() => {
    if (ocrEntities && ocrEntities.length > 0 && pageTextBlocks && pageTextBlocks.length > 0) {
      const content = {
        entityMap: {},
        blocks: [],
      };

      for (let i = 0; i < pageTextBlocks.length; i++) {
        const { blockUUID } = pageTextBlocks[i];
        content.blocks.push({
          key: blockUUID,
          text: ocrEntities
            .filter(({ textBlockUUID }) => textBlockUUID === blockUUID)
            .map(({ s_word }) => s_word)
            .join(' '),
          // TO-DO add persisted styles
          depth: 0,
          inlineStyleRanges: [],
          type: 'unstyled',
          entityRanges: [],
          data: {},
        });
      }
      setEditorState(EditorState.createWithContent(convertFromRaw(content)));
    } else {
      setEditorState(EditorState.createEmpty());
    }

    setHighlighted([]);
    setSelectionState(SelectionState.createEmpty());
  }, [ocrEntities, pageTextBlocks]);

  const handleSetPage = page => {
    if (page > 0 && page < mediaEntity?.lastPageNumber) {
      setCurrentPage(page);
    }
  };

  const onEditorStateChange = newEditorState => {
    const selection = newEditorState.getSelection();

    if (selectionState !== selection) {
      const focusKey = selection.getFocusKey();
      const anchorKey = selection.getAnchorKey();
      const focusOffset = selection.getFocusOffset();
      const anchorOffset = selection.getAnchorOffset();

      if (focusKey === anchorKey && focusOffset === anchorOffset) {
        const block = newEditorState.getCurrentContent().getBlockForKey(focusKey);
        const words = ocrEntities
          .filter(({ textBlockUUID }) => focusKey === textBlockUUID)
          .map(el => ({ ...el, wordLenght: el?.s_word?.length }));

        let selectedWordIndex = 0;
        let wordEndPosition = 0;
        for (let i = 0; i < words.length; i++) {
          wordEndPosition = wordEndPosition + words[i].wordLenght + 1;
          if (focusOffset <= wordEndPosition) {
            selectedWordIndex = i;
            break;
          }
        }
        const selectedWord = words[selectedWordIndex];
        const selectedWordParent = layoutTransfer.find(el => el.itemGUID === selectedWord?.textLineUUID);
        setHighlighted([{ ...selectedWord, selectedWordParent }]);
      } else {
        setHighlighted([]);
      }
    }
    setSelectionState(selection);
    setEditorState(newEditorState);
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

  const handleSetState = (state: any) => {};

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
          />
        </Col>
        <Col>
          <Editor
            editorClassName="rdw-storybook-editor"
            editorState={editorState}
            onEditorStateChange={onEditorStateChange}
            toolbarCustomButtons={editorToolbarExtensions.options.map((opt, index) => (
              <ToolGroup key={index} name={opt} config={editorToolbarExtensions[opt]} state={editorState} setState={handleSetState} />
            ))}
          />
          <div>{selectionState.serialize()}</div>
        </Col>
      </Row>
    </>
  );
};

export default MediaDetail;

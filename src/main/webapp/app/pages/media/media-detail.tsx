import React, { useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { convertFromRaw, EditorState } from 'draft-js';
import { Editor } from 'react-draft-wysiwyg';
import '../../../../../../node_modules/react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import './styles.css';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './media.reducer';
import { getEntity as getImageEntity } from './image/reducer';
import { getEntities as getPageOcr } from './ocr/reducer';
import { Ocr } from './ocr';

type Props = {
  onClick: () => void;
};
const CustomOption: React.FC<Props> = ({ onClick }) => (
  <div className="rdw-storybook-custom-option" onClick={onClick}>
    B
  </div>
);

export const MediaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [selectedPageNumber, setSelectedPageNumber] = useState<number>(1);
  const isFetching = useAppSelector(state => state.media.loading || state.pageImageTransfer.loading);
  const mediaEntity = useAppSelector(state => state.media.entity);
  const ocrEntities = useAppSelector(state => state.ocrTransfer.entities);
  const imageTransfer = useAppSelector(state => state.pageImageTransfer.entity);
  const [contentState, setContentState] = useState({
    entityMap: {},
    blocks: [
      {
        key: '1004,1005,1006',
        text: 'Initialized from content state.',
        type: 'unstyled',
        depth: 0,
        inlineStyleRanges: [],
        entityRanges: [],
        data: {},
      },
    ],
  });
  const [editorState, setEditorState] = useState(EditorState.createWithContent(convertFromRaw(contentState)));
  const [showOcrProps, setShowOcrProps] = useState(false);

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  useEffect(() => {
    if (mediaEntity && mediaEntity.id) {
      dispatch(getImageEntity({ id: mediaEntity.id, pageNumber: currentPage }));
      dispatch(getPageOcr({ id: mediaEntity.id, pageNumber: currentPage }));
    }
  }, [currentPage, mediaEntity]);

  useEffect(() => {
    const content = {
      entityMap: {},
      blocks: [
        {
          key: 'empty',
          text: '',
          type: 'unstyled',
          depth: 0,
          inlineStyleRanges: [],
          entityRanges: [],
          data: {},
        },
      ],
    };
    let i = 0;
    let keys = [];
    let text = [];

    if (ocrEntities && ocrEntities?.length) {
      content.blocks = [];
      if (!showOcrProps)
        ocrEntities.forEach((ocr_entity, idx) => {
          keys.push(ocr_entity.id);
          text.push(`${ocr_entity.s_word}`);
          i++;
          if (i > 10) {
            i = 0;
            const block = {
              key: keys.join(','),
              text: text.join(' '),
              depth: 0,
              inlineStyleRanges: [],
              type: 'unstyled',
              entityRanges: [],
              data: {},
            };
            content.blocks.push(block);
            keys = [];
            text = [];
          }
        });
      else
        ocrEntities.forEach((ocr_entity, idx) => {
          const block = {
            key: `${ocr_entity.id}`,
            text: `${idx} (${ocr_entity.n_left},${ocr_entity.n_top}). ${ocr_entity.s_word}`,
            depth: 0,
            inlineStyleRanges: [
              {
                offset: `${idx} (${ocr_entity.n_left},${ocr_entity.n_top}). `.length,
                length: `${ocr_entity.s_word}`.length,
                style: 'BOLD',
              },
            ],
            type: 'unstyled',
            entityRanges: [],
            data: {},
          };
          content.blocks.push(block);
        });
    }
    setContentState(content);
    setEditorState(EditorState.createWithContent(convertFromRaw(content)));
  }, [ocrEntities, showOcrProps]);

  const handlePrev = () => {
    if (currentPage > 0) setCurrentPage(currentPage - 1);
  };

  const changePageNumber = evt => setSelectedPageNumber(evt.target.valueAsNumber);

  const handleNext = () => {
    if (currentPage < mediaEntity?.lastPageNumber) setCurrentPage(currentPage + 1);
  };

  const handleGo = () => setCurrentPage(selectedPageNumber);

  const onContentStateChange = content => {
    setContentState(content);
  };

  const onEditorStateChange = newEditorState => {
    setEditorState(newEditorState);
  };

  return (
    <>
      <Row>
        <Col md="8">
          <h2 data-cy="mediaDetailsHeading">Media</h2>
          <span>{mediaEntity.fileName}</span>
        </Col>
      </Row>
      &nbsp;
      <Row>
        <Col md="8">
          <h4>
            Page {currentPage} of {mediaEntity?.lastPageNumber}
          </h4>
          <Button onClick={handlePrev} color="info">
            Previous
          </Button>
          &nbsp;
          <Button color="info" onClick={handleNext}>
            Next
          </Button>
          &nbsp;
          <input type="number" value={selectedPageNumber} onChange={changePageNumber} disabled={isFetching} />
          &nbsp;
          <Button color="info" onClick={handleGo}>
            Go
          </Button>
        </Col>
      </Row>
      &nbsp;
      <Row>
        <Col>
          <Ocr image={`data:image/jpeg;base64,${imageTransfer?.image}`} />
        </Col>
        <Col>
          <div>
            <Editor
              editorState={editorState}
              wrapperClassName="rdw-storybook-wrapper"
              editorClassName="rdw-storybook-editor"
              toolbarClassName="toolbar-class"
              onEditorStateChange={onEditorStateChange}
              onContentStateChange={onContentStateChange}
              toolbarCustomButtons={[<CustomOption key="togleView" onClick={() => setShowOcrProps(!showOcrProps)} />]}
            />
          </div>
        </Col>
      </Row>
    </>
  );
};

export default MediaDetail;

import React, { useEffect, useState, useMemo } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Row, Col } from 'reactstrap';
import { convertFromRaw, convertToRaw, EditorState, SelectionState } from 'draft-js';
import { Editor } from 'react-draft-wysiwyg';
import RBush from 'rbush';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import './styles.css';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './media.reducer';
import { getEntity as getImageEntity } from './image/reducer';
import { getEntities as getPageOcr } from './ocr/reducer';
import { MediaPane } from './ocr/media_pane';
import { editorToolbarExtensions } from './config';
import { ToolGroup } from '../../shared/ui/toolbar/controls';

type Props = {
  onClick: () => void;
};

export const MediaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();
  const [currentPage, setCurrentPage] = useState<number>(1);

  const isFetching = useAppSelector(state => state.media.loading || state.pageImageTransfer.loading);
  const mediaEntity = useAppSelector(state => state.media.entity);
  const ocrEntities = useAppSelector(state => state.ocrTransfer.entities);
  const imageTransfer = useAppSelector(state => state.pageImageTransfer.entity);
  const [editorState, setEditorState] = useState(EditorState.createEmpty());
  const [selectionState, setSelectionState] = useState(SelectionState.createEmpty());

  const [highlighted, setHighlighted] = useState([]);
  const [currentContent, setCurrentContent] = useState(editorState.getCurrentContent());

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

      ocrEntities.forEach((ocr_entity, idx) => {
        keys.push(idx);
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
    }
    const newEditorState = EditorState.createWithContent(convertFromRaw(content));
    setEditorState(newEditorState);
    setCurrentContent(newEditorState.getCurrentContent());
    setHighlighted([]);
    setSelectionState(SelectionState.createEmpty());
  }, [ocrEntities]);

  const handleSetPage = page => {
    if (page > 0 && page < mediaEntity?.lastPageNumber) {
      setCurrentPage(page);
    }
  };

  const onEditorStateChange = newEditorState => {
    const selection = newEditorState.getSelection();
    // const newContent = newEditorState.getCurrentContent();

    // if (newContent !== currentContent) return;

    if (selectionState !== selection) {
      const focusKey = selection.getFocusKey();
      const anchorKey = selection.getAnchorKey();
      const focusOffset = selection.getFocusOffset();
      const anchorOffset = selection.getAnchorOffset();

      if (focusKey === anchorKey) {
        const wordIndexes = focusKey.split(',').map(el => Number(el));
        const words = ocrEntities.filter((_, idx) => wordIndexes.includes(idx)).map(el => ({ ...el, wordLenght: el?.s_word?.length }));
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

        setSelectionState(selection);
        // setEditorState(newEditorState);
        setHighlighted([selectedWord]);
      } else {
        setSelectionState(SelectionState.createEmpty());
        setHighlighted([]);
      }
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

  const polyTreeJSON = undefined;

  const handleSetState = (state: any) => {};

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
            polyTreeJSON={polyTreeJSON}
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

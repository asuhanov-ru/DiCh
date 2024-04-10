import React, { useState, useEffect } from 'react';
import { convertFromRaw, EditorState, SelectionState } from 'draft-js';
import { Editor } from 'react-draft-wysiwyg';

import { useAppSelector } from 'app/config/store';

import { ToolGroup } from '../../../../shared/ui/toolbar/controls';

import { editorToolbarExtensions, mediaPaneDefaultState } from '../../config';

import './styles.css';

type Props = {
  setHighlighted: (highlights: any[]) => void;
};
export const TextPane: React.FC<Props> = ({ setHighlighted }) => {
  const pageTextBlocks = useAppSelector(state => state.textBlockTransfer.entities);
  const ocrEntities = useAppSelector(state => state.ocrTransfer.entities);
  const layoutTransfer = useAppSelector(state => state.ocrLayoutTransfer.entities);

  const [editorState, setEditorState] = useState(EditorState.createEmpty());
  const [selectionState, setSelectionState] = useState(SelectionState.createEmpty());

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
  // Commands from toolbar extension
  const handleSetState = (state: any) => {};

  return (
    <Editor
      editorClassName="ocr-text-editor"
      editorState={editorState}
      onEditorStateChange={onEditorStateChange}
      toolbarCustomButtons={editorToolbarExtensions.options.map((opt, index) => (
        <ToolGroup key={index} name={opt} config={editorToolbarExtensions[opt]} state={editorState} setState={handleSetState} />
      ))}
    />
  );
};

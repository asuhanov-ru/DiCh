import React, { useState } from 'react';
import { Tree, NodeRendererProps } from 'react-arborist';

import { useAppSelector } from 'app/config/store';
import { TreeNode } from '../../../../shared/ui/treeview/components/treenode';
import { outlinesPaneToolbar } from '../../config';
import { ToolGroup } from '../../../../shared/ui/toolbar/controls';

import './styles.css';
import { set } from 'react-hook-form';

type Props = {
  setPage: (page: number) => void;
  currentEditorState: any;
  currentPage: number;
};

export const OutlinePane: React.FC<Props> = ({ setPage, currentEditorState, currentPage }) => {
  const data = useAppSelector(state => state.media.entity.outlines);
  const [currentSelection, setCurrentSelection] = useState<any>({});
  const handleSetState = (state: any) => {
    const { command, value } = state;
    switch (command) {
      case 'gotoPage':
        if (currentSelection[0].data) setPage(currentSelection[0].data.pageNumber);
        break;
      default:
        break;
    }
  };
  return (
    <div className="outline-pane">
      <div className="rdw-editor-toolbar">
        {outlinesPaneToolbar.options.map((opt, index) => {
          const config = outlinesPaneToolbar[opt];
          const state = currentEditorState[opt];
          return <ToolGroup key={index} name={opt} config={config} state={state} setState={handleSetState} />;
        })}
      </div>
      <Tree
        data={data?.children ? data.children : []}
        openByDefault={false}
        disableDrag
        indent={16}
        padding={8}
        height={500}
        width={'100%'}
        disableMultiSelection
        onSelect={setCurrentSelection}
      >
        {TreeNode}
      </Tree>
    </div>
  );
};

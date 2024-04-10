import React from 'react';
import { NodeRendererProps } from 'react-arborist';

import './styles.scss';

type Props = {
  name: string;
};
export function TreeNode({ node, style, dragHandle }: NodeRendererProps<Props>) {
  const { name } = node.data;

  return (
    <div className="dich-tree-node-wrapper" ref={dragHandle} style={style} onClick={() => node.toggle()}>
      <div className="dich-tree-node-bg">
        <span className="dich-tree-node-text">{name}</span>
      </div>
    </div>
  );
}

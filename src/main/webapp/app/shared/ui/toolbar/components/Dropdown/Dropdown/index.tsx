import React, { useState } from 'react';

import './styles.css';

type Props = {
  children: any;
  onChange: () => void;
  className: string;
  expanded: boolean;
  doExpand: () => void;
  doCollapse: () => void;
  onExpandEvent: () => void;
  optionWrapperClassName: string;
  ariaLabel: string;
  title: string;
};

export const Dropdown: React.FC<Props> = ({}) => {
  const [highlighted, setHighlighted] = useState<number>(-1);
  return <div></div>;
};

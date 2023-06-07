import React, { useState, useEffect } from 'react';
import { Input } from 'reactstrap';
import classNames from 'classnames';

import './styles.css';

type Props = {
  disabled?: boolean;
  onClick?: (string) => void;
  onChange?: (string) => void;
  children?: any;
  title?: string;
  active?: boolean;
  value?: string;
  className?: string;
  activeClassName?: string;
  name: string;
};

export const Edit: React.FC<Props> = ({
  name,
  disabled = false,
  onClick,
  children,
  title,
  active,
  value,
  className,
  activeClassName = '',
  onChange,
}) => {
  const [editorContent, setEditorContent] = useState(value);
  const handleChange = evt => setEditorContent(evt.target.valueAsNumber);
  const handleKeyUp = evt => {
    if (evt.key === 'Enter') onChange({ command: name, value: editorContent });
  };
  useEffect(() => {
    setEditorContent(value);
  }, [value]);
  return (
    <Input
      value={editorContent}
      onChange={handleChange}
      onKeyUp={handleKeyUp}
      type="number"
      className={classNames('rdw-edit-wrapper', className)}
    />
  );
};

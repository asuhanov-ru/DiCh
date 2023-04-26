import React from 'react';
import classNames from 'classnames';

import './styles.css';

type Props = {
  disabled?: boolean;
  onClick: (string) => void;
  children?: any;
  title?: string;
  active?: boolean;
  value?: string;
  className?: string;
  activeClassName?: string;
};

export const Options: React.FC<Props> = ({
  disabled = false,
  onClick,
  children,
  title,
  active,
  value,
  className,
  activeClassName = '',
}) => {
  const handleClick = () => {
    if (!disabled) {
      onClick(value);
    }
  };

  return (
    <div
      className={classNames('rdw-option-wrapper', className, {
        [`rdw-option-active ${activeClassName}`]: active,
        'rdw-option-disabled': disabled,
      })}
      onClick={handleClick}
      aria-selected={active}
      title={title}
    >
      {children}
    </div>
  );
};

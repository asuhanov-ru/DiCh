import React from 'react';

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

export const Static: React.FC<Props> = ({ className, title, value }) => <div className={className}>{`${title}${value}`}</div>;

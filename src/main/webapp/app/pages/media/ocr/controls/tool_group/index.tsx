import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import classNames from 'classnames';
import { get } from 'lodash';
import { Input } from 'reactstrap';

import { Options } from '../../components/Option';
import { Edit } from '../../components/Edit';

import './styles.css';

type Props = {
  name: string;
  config: any;
  state: any;
  setState: (any) => void;
};

export const ToolGroup: React.FC<Props> = ({ name, config, state, setState }) => {
  const handleClick = value => {
    setState({ command: value });
  };
  const handleChange = value => {
    setState(value);
  };

  const renderOption = (component, props: any, key) => {
    switch (component) {
      case 'option':
        return (
          <Options {...props.option} key={key}>
            <FontAwesomeIcon {...props.button} />
          </Options>
        );
      case 'edit':
        return <Edit {...props.edit} key={key} />;
      default:
        return undefined;
    }
  };

  return (
    <div className={classNames('rdw-inline-wrapper', config?.className)} aria-label="rdw-inline-control">
      {config?.options.map((style, index) => {
        const stateValueIndex = `${style}`;
        const stateValue = get(state, stateValueIndex);
        const controlProps = {
          button: {
            icon: config[style]?.icon,
          },
          option: {
            value: style,
            onClick: handleClick,
            className: classNames(config[style]?.className),
            title: config[style]?.title,
          },
          static: {
            value: stateValue,
          },
          edit: {
            value: stateValue,
            name: style,
            className: classNames('rdw-toolcontrol-editor', config[style]?.className),
            onChange: handleChange,
          },
        };
        return <>{renderOption(config[style]?.component, controlProps, index)}</>;
      })}
    </div>
  );
};

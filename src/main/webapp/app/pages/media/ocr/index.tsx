import React, { useRef } from 'react';
import { PanViewer } from './viewer';

import { defaultToolbar } from './config';
import { ToolGroup } from './controls';

type ReactPanZoomProps = {
  image: string;
  alt?: string;
  ref?: any;
  highlights?: object[];
  currentPage: number;
  totalPages: number;
  setPage: (number) => void;
};

export const Ocr = ({ image, alt, ref, highlights = [], currentPage, totalPages, setPage }: ReactPanZoomProps) => {
  const [dx, setDx] = React.useState(0);
  const [dy, setDy] = React.useState(0);
  const [zoom, setZoom] = React.useState(1);
  const [rotation, setRotation] = React.useState(0);
  const [flip, setFlip] = React.useState(false);
  const [mouseX, setMouseX] = React.useState(0);
  const [mouseY, setMouseY] = React.useState(0);

  const [toolbar, setToolbar] = React.useState(defaultToolbar);
  const editorState = {
    navigate: {
      page: currentPage,
      total: totalPages,
    },
  };

  const resetAll = () => {
    setDx(0);
    setDy(0);
    setZoom(1);
    setRotation(0);
    setFlip(false);
  };
  const zoomIn = () => {
    setZoom(zoom + 0.2);
  };

  const zoomOut = () => {
    if (zoom >= 1) {
      setZoom(zoom - 0.2);
    }
  };

  const rotateLeft = () => {
    if (rotation === -3) {
      setRotation(0);
    } else {
      setRotation(rotation - 1);
    }
  };

  const flipImage = () => {
    setFlip(!flip);
  };

  const onPan = (paramDx: number, paramDy: number) => {
    setDx(paramDx);
    setDy(paramDy);
  };

  const setPointerPosition = (x, y) => {
    setMouseX(x), setMouseY(y);
  };

  const handleSetState = (state: any) => {
    const { command, value } = state;
    switch (command) {
      case 'next':
        setPage(currentPage + 1);
        break;
      case 'prev':
        setPage(currentPage - 1);
        break;
      case 'page':
        setPage(value);
        break;
      case 'resetTransfrom':
        resetAll();
        break;
      case 'zoomIn':
        zoomIn();
        break;
      case 'zoomOut':
        zoomOut();
        break;
      default:
        break;
    }
  };

  return (
    <div>
      <div className="rdw-editor-toolbar">
        {toolbar.options.map((opt, index) => {
          const config = toolbar[opt];
          const state = editorState[opt];
          return <ToolGroup key={index} name={opt} config={config} state={state} setState={handleSetState} />;
        })}
      </div>
      <div
        className="pan-viewer-wrapper"
        style={{
          float: 'left',
          border: '1px solid',
          overflow: 'hidden',
          width: '100%',
          marginTop: '8px',
        }}
      >
        <PanViewer
          setPointerPosition={setPointerPosition}
          style={{
            width: '100%',
            height: '100%',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            zIndex: 1,
          }}
          zoom={zoom}
          setZoom={setZoom}
          pandx={dx}
          pandy={dy}
          onPan={onPan}
          rotation={rotation}
          key={dx}
          highlights={highlights}
        >
          <img src={image} alt={alt} ref={ref} />
        </PanViewer>
      </div>
    </div>
  );
};

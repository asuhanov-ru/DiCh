import React, { useEffect } from 'react';
import { RouteComponentProps } from 'react-router-dom';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity } from './page-ocr.reducer';

const PageOcrDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);
  return <div>OCR</div>;
};

export default PageOcrDetail;

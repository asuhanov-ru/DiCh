import React, { useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './media.reducer';

export const MediaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const mediaEntity = useAppSelector(state => state.media.entity);

  const handlePrev = () => {
    if (currentPage > 0) setCurrentPage(currentPage - 1);
  };

  return (
    <>
      <Row>
        <Col md="8">
          <h2 data-cy="mediaDetailsHeading">Media</h2>
          <span>{mediaEntity.fileName}</span>
        </Col>
      </Row>
      <Row>
        <Col md="8">
          <Button onClick={handlePrev} color="info">
            Previous
          </Button>
          &nbsp;
          <Button color="info">Next</Button>
        </Col>
      </Row>
    </>
  );
};

export default MediaDetail;

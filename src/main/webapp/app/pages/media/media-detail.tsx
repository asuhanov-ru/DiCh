import React, { useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './media.reducer';
import { getEntity as getImageEntity } from './image/reducer';

export const MediaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const mediaEntity = useAppSelector(state => state.media.entity);

  useEffect(() => {
    if (mediaEntity && mediaEntity.id) dispatch(getImageEntity({ id: mediaEntity.id, pageNumber: currentPage }));
  }, [currentPage, mediaEntity]);

  const imageTransfer = useAppSelector(state => state.pageImageTransfer.entity);

  const handlePrev = () => {
    if (currentPage > 0) setCurrentPage(currentPage - 1);
  };

  const handleNext = () => {
    if (currentPage < mediaEntity?.lastPageNumber) setCurrentPage(currentPage + 1);
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
          <Button color="info" onClick={handleNext}>
            Next
          </Button>
        </Col>
      </Row>
      <Row>
        <img src={`data:image/jpeg;base64,${imageTransfer?.image}`} />
      </Row>
    </>
  );
};

export default MediaDetail;

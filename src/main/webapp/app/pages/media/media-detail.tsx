import React, { useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './media.reducer';
import { getEntity as getImageEntity } from './image/reducer';
import { getEntities as getPageOcr } from './ocr/reducer';
import { Ocr } from './ocr';

export const MediaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [selectedPageNumber, setSelectedPageNumber] = useState<number>(1);
  const isFetching = useAppSelector(state => state.media.loading || state.pageImageTransfer.loading);
  const mediaEntity = useAppSelector(state => state.media.entity);
  const imageTransfer = useAppSelector(state => state.pageImageTransfer.entity);

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  useEffect(() => {
    if (mediaEntity && mediaEntity.id) {
      dispatch(getImageEntity({ id: mediaEntity.id, pageNumber: currentPage }));
      dispatch(getPageOcr({ id: mediaEntity.id, pageNumber: currentPage }));
    }
  }, [currentPage, mediaEntity]);

  const handlePrev = () => {
    if (currentPage > 0) setCurrentPage(currentPage - 1);
  };

  const changePageNumber = evt => setSelectedPageNumber(evt.target.valueAsNumber);

  const handleNext = () => {
    if (currentPage < mediaEntity?.lastPageNumber) setCurrentPage(currentPage + 1);
  };

  const handleGo = () => setCurrentPage(selectedPageNumber);

  return (
    <>
      <Row>
        <Col md="8">
          <h2 data-cy="mediaDetailsHeading">Media</h2>
          <span>{mediaEntity.fileName}</span>
        </Col>
      </Row>
      &nbsp;
      <Row>
        <Col md="8">
          <h4>
            Page {currentPage} of {mediaEntity?.lastPageNumber}
          </h4>
          <Button onClick={handlePrev} color="info">
            Previous
          </Button>
          &nbsp;
          <Button color="info" onClick={handleNext}>
            Next
          </Button>
          &nbsp;
          <input type="number" value={selectedPageNumber} onChange={changePageNumber} disabled={isFetching} />
          &nbsp;
          <Button color="info" onClick={handleGo}>
            Go
          </Button>
        </Col>
      </Row>
      &nbsp;
      <Row>
        <Ocr image={`data:image/jpeg;base64,${imageTransfer?.image}`} />
      </Row>
    </>
  );
};

export default MediaDetail;

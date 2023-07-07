import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './book-mark.reducer';

export const BookMarkDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bookMarkEntity = useAppSelector(state => state.bookMark.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bookMarkDetailsHeading">
          <Translate contentKey="diChApp.bookMark.detail.title">BookMark</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bookMarkEntity.id}</dd>
          <dt>
            <span id="bookMarkUUID">
              <Translate contentKey="diChApp.bookMark.bookMarkUUID">Book Mark UUID</Translate>
            </span>
          </dt>
          <dd>{bookMarkEntity.bookMarkUUID}</dd>
          <dt>
            <span id="mediaId">
              <Translate contentKey="diChApp.bookMark.mediaId">Media Id</Translate>
            </span>
          </dt>
          <dd>{bookMarkEntity.mediaId}</dd>
          <dt>
            <span id="pageNumber">
              <Translate contentKey="diChApp.bookMark.pageNumber">Page Number</Translate>
            </span>
          </dt>
          <dd>{bookMarkEntity.pageNumber}</dd>
          <dt>
            <span id="textBlockUUID">
              <Translate contentKey="diChApp.bookMark.textBlockUUID">Text Block UUID</Translate>
            </span>
          </dt>
          <dd>{bookMarkEntity.textBlockUUID}</dd>
          <dt>
            <span id="anchor">
              <Translate contentKey="diChApp.bookMark.anchor">Anchor</Translate>
            </span>
          </dt>
          <dd>{bookMarkEntity.anchor}</dd>
          <dt>
            <span id="label">
              <Translate contentKey="diChApp.bookMark.label">Label</Translate>
            </span>
          </dt>
          <dd>{bookMarkEntity.label}</dd>
        </dl>
        <Button tag={Link} to="/book-mark" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/book-mark/${bookMarkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BookMarkDetail;

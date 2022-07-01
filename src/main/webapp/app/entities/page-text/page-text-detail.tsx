import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './page-text.reducer';

export const PageTextDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const pageTextEntity = useAppSelector(state => state.pageText.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pageTextDetailsHeading">
          <Translate contentKey="diChApp.pageText.detail.title">PageText</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pageTextEntity.id}</dd>
          <dt>
            <span id="page_id">
              <Translate contentKey="diChApp.pageText.page_id">Page Id</Translate>
            </span>
          </dt>
          <dd>{pageTextEntity.page_id}</dd>
          <dt>
            <span id="text">
              <Translate contentKey="diChApp.pageText.text">Text</Translate>
            </span>
          </dt>
          <dd>{pageTextEntity.text}</dd>
          <dt>
            <Translate contentKey="diChApp.pageText.pageImage">Page Image</Translate>
          </dt>
          <dd>{pageTextEntity.pageImage ? pageTextEntity.pageImage.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/page-text" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/page-text/${pageTextEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/page-ocr/${pageTextEntity.id}`} replace color="primary">
          <FontAwesomeIcon icon="arrow-right" /> <span className="d-none d-md-inline">OCR</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PageTextDetail;

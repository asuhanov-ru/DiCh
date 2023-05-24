import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './page-layout.reducer';

export const PageLayoutDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const pageLayoutEntity = useAppSelector(state => state.pageLayout.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pageLayoutDetailsHeading">
          <Translate contentKey="diChApp.pageLayout.detail.title">PageLayout</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pageLayoutEntity.id}</dd>
          <dt>
            <span id="mediaId">
              <Translate contentKey="diChApp.pageLayout.mediaId">Media Id</Translate>
            </span>
          </dt>
          <dd>{pageLayoutEntity.mediaId}</dd>
          <dt>
            <span id="pageNumber">
              <Translate contentKey="diChApp.pageLayout.pageNumber">Page Number</Translate>
            </span>
          </dt>
          <dd>{pageLayoutEntity.pageNumber}</dd>
          <dt>
            <span id="iterator_level">
              <Translate contentKey="diChApp.pageLayout.iterator_level">Iterator Level</Translate>
            </span>
          </dt>
          <dd>{pageLayoutEntity.iterator_level}</dd>
          <dt>
            <span id="rect_top">
              <Translate contentKey="diChApp.pageLayout.rect_top">Rect Top</Translate>
            </span>
          </dt>
          <dd>{pageLayoutEntity.rect_top}</dd>
          <dt>
            <span id="rect_left">
              <Translate contentKey="diChApp.pageLayout.rect_left">Rect Left</Translate>
            </span>
          </dt>
          <dd>{pageLayoutEntity.rect_left}</dd>
          <dt>
            <span id="rect_right">
              <Translate contentKey="diChApp.pageLayout.rect_right">Rect Right</Translate>
            </span>
          </dt>
          <dd>{pageLayoutEntity.rect_right}</dd>
          <dt>
            <span id="rect_bottom">
              <Translate contentKey="diChApp.pageLayout.rect_bottom">Rect Bottom</Translate>
            </span>
          </dt>
          <dd>{pageLayoutEntity.rect_bottom}</dd>
          <dt>
            <span id="parent_id">
              <Translate contentKey="diChApp.pageLayout.parent_id">Parent Id</Translate>
            </span>
          </dt>
          <dd>{pageLayoutEntity.parent_id}</dd>
        </dl>
        <Button tag={Link} to="/page-layout" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/page-layout/${pageLayoutEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PageLayoutDetail;

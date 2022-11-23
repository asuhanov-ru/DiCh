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

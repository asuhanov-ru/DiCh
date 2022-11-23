import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './collections.reducer';

export const CollectionsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const collectionsEntity = useAppSelector(state => state.collections.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="collectionsDetailsHeading">
          <Translate contentKey="diChApp.collections.detail.title">Collections</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{collectionsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="diChApp.collections.name">Name</Translate>
            </span>
          </dt>
          <dd>{collectionsEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="diChApp.collections.description">Description</Translate>
            </span>
          </dt>
          <dd>{collectionsEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/collections" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/collections/${collectionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CollectionsDetail;

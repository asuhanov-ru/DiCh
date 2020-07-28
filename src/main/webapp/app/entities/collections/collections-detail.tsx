import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './collections.reducer';
import { ICollections } from 'app/shared/model/collections.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICollectionsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CollectionsDetail = (props: ICollectionsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { collectionsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="diChApp.collections.detail.title">Collections</Translate> [<b>{collectionsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
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
        <Button tag={Link} to="/collections" replace color="info">
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

const mapStateToProps = ({ collections }: IRootState) => ({
  collectionsEntity: collections.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CollectionsDetail);

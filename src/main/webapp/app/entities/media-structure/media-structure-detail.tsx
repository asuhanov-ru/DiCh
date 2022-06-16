import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './media-structure.reducer';

export const MediaStructureDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const mediaStructureEntity = useAppSelector(state => state.mediaStructure.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mediaStructureDetailsHeading">
          <Translate contentKey="diChApp.mediaStructure.detail.title">MediaStructure</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{mediaStructureEntity.id}</dd>
          <dt>
            <span id="objName">
              <Translate contentKey="diChApp.mediaStructure.objName">Obj Name</Translate>
            </span>
          </dt>
          <dd>{mediaStructureEntity.objName}</dd>
          <dt>
            <span id="objType">
              <Translate contentKey="diChApp.mediaStructure.objType">Obj Type</Translate>
            </span>
          </dt>
          <dd>{mediaStructureEntity.objType}</dd>
          <dt>
            <span id="parentId">
              <Translate contentKey="diChApp.mediaStructure.parentId">Parent Id</Translate>
            </span>
          </dt>
          <dd>{mediaStructureEntity.parentId}</dd>
          <dt>
            <span id="tag">
              <Translate contentKey="diChApp.mediaStructure.tag">Tag</Translate>
            </span>
          </dt>
          <dd>{mediaStructureEntity.tag}</dd>
          <dt>
            <Translate contentKey="diChApp.mediaStructure.media">Media</Translate>
          </dt>
          <dd>{mediaStructureEntity.media ? mediaStructureEntity.media.fileName : ''}</dd>
        </dl>
        <Button tag={Link} to="/media-structure" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/media-structure/${mediaStructureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MediaStructureDetail;

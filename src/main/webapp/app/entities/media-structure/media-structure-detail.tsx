import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './media-structure.reducer';
import { IMediaStructure } from 'app/shared/model/media-structure.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMediaStructureDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MediaStructureDetail = (props: IMediaStructureDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { mediaStructureEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="diChApp.mediaStructure.detail.title">MediaStructure</Translate> [<b>{mediaStructureEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
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
        <Button tag={Link} to="/media-structure" replace color="info">
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

const mapStateToProps = ({ mediaStructure }: IRootState) => ({
  mediaStructureEntity: mediaStructure.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MediaStructureDetail);

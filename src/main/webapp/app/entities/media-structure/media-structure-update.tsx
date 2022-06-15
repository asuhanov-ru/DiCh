import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMedia } from 'app/shared/model/media.model';
import { getEntities as getMedia } from 'app/entities/media/media.reducer';
import { getEntity, updateEntity, createEntity, reset } from './media-structure.reducer';
import { IMediaStructure } from 'app/shared/model/media-structure.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMediaStructureUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MediaStructureUpdate = (props: IMediaStructureUpdateProps) => {
  const [mediaId, setMediaId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { mediaStructureEntity, media, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/media-structure');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMedia();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...mediaStructureEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diChApp.mediaStructure.home.createOrEditLabel">
            <Translate contentKey="diChApp.mediaStructure.home.createOrEditLabel">Create or edit a MediaStructure</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : mediaStructureEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="media-structure-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="media-structure-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="objNameLabel" for="media-structure-objName">
                  <Translate contentKey="diChApp.mediaStructure.objName">Obj Name</Translate>
                </Label>
                <AvField id="media-structure-objName" type="text" name="objName" />
              </AvGroup>
              <AvGroup>
                <Label id="objTypeLabel" for="media-structure-objType">
                  <Translate contentKey="diChApp.mediaStructure.objType">Obj Type</Translate>
                </Label>
                <AvField id="media-structure-objType" type="text" name="objType" />
              </AvGroup>
              <AvGroup>
                <Label id="parentIdLabel" for="media-structure-parentId">
                  <Translate contentKey="diChApp.mediaStructure.parentId">Parent Id</Translate>
                </Label>
                <AvField id="media-structure-parentId" type="string" className="form-control" name="parentId" />
              </AvGroup>
              <AvGroup>
                <Label id="tagLabel" for="media-structure-tag">
                  <Translate contentKey="diChApp.mediaStructure.tag">Tag</Translate>
                </Label>
                <AvField id="media-structure-tag" type="text" name="tag" />
              </AvGroup>
              <AvGroup>
                <Label for="media-structure-media">
                  <Translate contentKey="diChApp.mediaStructure.media">Media</Translate>
                </Label>
                <AvInput id="media-structure-media" type="select" className="form-control" name="media.id">
                  <option value="" key="0" />
                  {media
                    ? media.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.fileName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/media-structure" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  media: storeState.media.entities,
  mediaStructureEntity: storeState.mediaStructure.entity,
  loading: storeState.mediaStructure.loading,
  updating: storeState.mediaStructure.updating,
  updateSuccess: storeState.mediaStructure.updateSuccess
});

const mapDispatchToProps = {
  getMedia,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MediaStructureUpdate);

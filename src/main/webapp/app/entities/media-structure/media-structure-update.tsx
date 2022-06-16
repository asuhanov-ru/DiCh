import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMedia } from 'app/shared/model/media.model';
import { getEntities as getMedia } from 'app/entities/media/media.reducer';
import { IMediaStructure } from 'app/shared/model/media-structure.model';
import { getEntity, updateEntity, createEntity, reset } from './media-structure.reducer';

export const MediaStructureUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const media = useAppSelector(state => state.media.entities);
  const mediaStructureEntity = useAppSelector(state => state.mediaStructure.entity);
  const loading = useAppSelector(state => state.mediaStructure.loading);
  const updating = useAppSelector(state => state.mediaStructure.updating);
  const updateSuccess = useAppSelector(state => state.mediaStructure.updateSuccess);
  const handleClose = () => {
    props.history.push('/media-structure');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getMedia({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...mediaStructureEntity,
      ...values,
      media: media.find(it => it.id.toString() === values.media.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...mediaStructureEntity,
          media: mediaStructureEntity?.media?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diChApp.mediaStructure.home.createOrEditLabel" data-cy="MediaStructureCreateUpdateHeading">
            <Translate contentKey="diChApp.mediaStructure.home.createOrEditLabel">Create or edit a MediaStructure</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="media-structure-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diChApp.mediaStructure.objName')}
                id="media-structure-objName"
                name="objName"
                data-cy="objName"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.mediaStructure.objType')}
                id="media-structure-objType"
                name="objType"
                data-cy="objType"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.mediaStructure.parentId')}
                id="media-structure-parentId"
                name="parentId"
                data-cy="parentId"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.mediaStructure.tag')}
                id="media-structure-tag"
                name="tag"
                data-cy="tag"
                type="text"
              />
              <ValidatedField
                id="media-structure-media"
                name="media"
                data-cy="media"
                label={translate('diChApp.mediaStructure.media')}
                type="select"
              >
                <option value="" key="0" />
                {media
                  ? media.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.fileName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/media-structure" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MediaStructureUpdate;

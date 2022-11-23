import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOcrTasks } from 'app/shared/model/ocr-tasks.model';
import { getEntity, updateEntity, createEntity, reset } from './ocr-tasks.reducer';

export const OcrTasksUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const ocrTasksEntity = useAppSelector(state => state.ocrTasks.entity);
  const loading = useAppSelector(state => state.ocrTasks.loading);
  const updating = useAppSelector(state => state.ocrTasks.updating);
  const updateSuccess = useAppSelector(state => state.ocrTasks.updateSuccess);
  const handleClose = () => {
    props.history.push('/ocr-tasks');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createTime = convertDateTimeToServer(values.createTime);
    values.startTime = convertDateTimeToServer(values.startTime);
    values.stopTime = convertDateTimeToServer(values.stopTime);

    const entity = {
      ...ocrTasksEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createTime: displayDefaultDateTime(),
          startTime: displayDefaultDateTime(),
          stopTime: displayDefaultDateTime(),
        }
      : {
          ...ocrTasksEntity,
          createTime: convertDateTimeFromServer(ocrTasksEntity.createTime),
          startTime: convertDateTimeFromServer(ocrTasksEntity.startTime),
          stopTime: convertDateTimeFromServer(ocrTasksEntity.stopTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diChApp.ocrTasks.home.createOrEditLabel" data-cy="OcrTasksCreateUpdateHeading">
            <Translate contentKey="diChApp.ocrTasks.home.createOrEditLabel">Create or edit a OcrTasks</Translate>
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
                  id="ocr-tasks-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diChApp.ocrTasks.mediaId')}
                id="ocr-tasks-mediaId"
                name="mediaId"
                data-cy="mediaId"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.ocrTasks.pageNumber')}
                id="ocr-tasks-pageNumber"
                name="pageNumber"
                data-cy="pageNumber"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.ocrTasks.jobStatus')}
                id="ocr-tasks-jobStatus"
                name="jobStatus"
                data-cy="jobStatus"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.ocrTasks.createTime')}
                id="ocr-tasks-createTime"
                name="createTime"
                data-cy="createTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('diChApp.ocrTasks.startTime')}
                id="ocr-tasks-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('diChApp.ocrTasks.stopTime')}
                id="ocr-tasks-stopTime"
                name="stopTime"
                data-cy="stopTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ocr-tasks" replace color="info">
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

export default OcrTasksUpdate;

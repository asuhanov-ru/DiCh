import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPageImage } from 'app/shared/model/page-image.model';
import { getEntities as getPageImages } from 'app/entities/page-image/page-image.reducer';
import { IPageText } from 'app/shared/model/page-text.model';
import { getEntity, updateEntity, createEntity, reset } from './page-text.reducer';

export const PageTextUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const pageImages = useAppSelector(state => state.pageImage.entities);
  const pageTextEntity = useAppSelector(state => state.pageText.entity);
  const loading = useAppSelector(state => state.pageText.loading);
  const updating = useAppSelector(state => state.pageText.updating);
  const updateSuccess = useAppSelector(state => state.pageText.updateSuccess);
  const handleClose = () => {
    props.history.push('/page-text' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPageImages({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...pageTextEntity,
      ...values,
      pageImage: pageImages.find(it => it.id.toString() === values.pageImage.toString()),
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
          ...pageTextEntity,
          pageImage: pageTextEntity?.pageImage?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diChApp.pageText.home.createOrEditLabel" data-cy="PageTextCreateUpdateHeading">
            <Translate contentKey="diChApp.pageText.home.createOrEditLabel">Create or edit a PageText</Translate>
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
                  id="page-text-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diChApp.pageText.page_id')}
                id="page-text-page_id"
                name="page_id"
                data-cy="page_id"
                type="text"
              />
              <ValidatedField label={translate('diChApp.pageText.text')} id="page-text-text" name="text" data-cy="text" type="textarea" />
              <ValidatedField
                id="page-text-pageImage"
                name="pageImage"
                data-cy="pageImage"
                label={translate('diChApp.pageText.pageImage')}
                type="select"
              >
                <option value="" key="0" />
                {pageImages
                  ? pageImages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/page-text" replace color="info">
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

export default PageTextUpdate;

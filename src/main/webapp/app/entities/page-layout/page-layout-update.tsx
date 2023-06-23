import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPageLayout } from 'app/shared/model/page-layout.model';
import { getEntity, updateEntity, createEntity, reset } from './page-layout.reducer';

export const PageLayoutUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const pageLayoutEntity = useAppSelector(state => state.pageLayout.entity);
  const loading = useAppSelector(state => state.pageLayout.loading);
  const updating = useAppSelector(state => state.pageLayout.updating);
  const updateSuccess = useAppSelector(state => state.pageLayout.updateSuccess);
  const handleClose = () => {
    props.history.push('/page-layout');
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
    const entity = {
      ...pageLayoutEntity,
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
      ? {}
      : {
          ...pageLayoutEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diChApp.pageLayout.home.createOrEditLabel" data-cy="PageLayoutCreateUpdateHeading">
            <Translate contentKey="diChApp.pageLayout.home.createOrEditLabel">Create or edit a PageLayout</Translate>
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
                  id="page-layout-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diChApp.pageLayout.mediaId')}
                id="page-layout-mediaId"
                name="mediaId"
                data-cy="mediaId"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.pageLayout.pageNumber')}
                id="page-layout-pageNumber"
                name="pageNumber"
                data-cy="pageNumber"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.pageLayout.iterator_level')}
                id="page-layout-iterator_level"
                name="iterator_level"
                data-cy="iterator_level"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.pageLayout.rect_top')}
                id="page-layout-rect_top"
                name="rect_top"
                data-cy="rect_top"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.pageLayout.rect_left')}
                id="page-layout-rect_left"
                name="rect_left"
                data-cy="rect_left"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.pageLayout.rect_right')}
                id="page-layout-rect_right"
                name="rect_right"
                data-cy="rect_right"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.pageLayout.rect_bottom')}
                id="page-layout-rect_bottom"
                name="rect_bottom"
                data-cy="rect_bottom"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.pageLayout.parent_id')}
                id="page-layout-parent_id"
                name="parent_id"
                data-cy="parent_id"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.pageLayout.itemGUID')}
                id="page-layout-itemGUID"
                name="itemGUID"
                data-cy="itemGUID"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.pageLayout.parentGUID')}
                id="page-layout-parentGUID"
                name="parentGUID"
                data-cy="parentGUID"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/page-layout" replace color="info">
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

export default PageLayoutUpdate;

import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBookMark } from 'app/shared/model/book-mark.model';
import { getEntity, updateEntity, createEntity, reset } from './book-mark.reducer';

export const BookMarkUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bookMarkEntity = useAppSelector(state => state.bookMark.entity);
  const loading = useAppSelector(state => state.bookMark.loading);
  const updating = useAppSelector(state => state.bookMark.updating);
  const updateSuccess = useAppSelector(state => state.bookMark.updateSuccess);
  const handleClose = () => {
    props.history.push('/book-mark');
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
      ...bookMarkEntity,
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
          ...bookMarkEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diChApp.bookMark.home.createOrEditLabel" data-cy="BookMarkCreateUpdateHeading">
            <Translate contentKey="diChApp.bookMark.home.createOrEditLabel">Create or edit a BookMark</Translate>
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
                  id="book-mark-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diChApp.bookMark.bookMarkUUID')}
                id="book-mark-bookMarkUUID"
                name="bookMarkUUID"
                data-cy="bookMarkUUID"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.bookMark.mediaId')}
                id="book-mark-mediaId"
                name="mediaId"
                data-cy="mediaId"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.bookMark.pageNumber')}
                id="book-mark-pageNumber"
                name="pageNumber"
                data-cy="pageNumber"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.bookMark.textBlockUUID')}
                id="book-mark-textBlockUUID"
                name="textBlockUUID"
                data-cy="textBlockUUID"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.bookMark.anchor')}
                id="book-mark-anchor"
                name="anchor"
                data-cy="anchor"
                type="text"
              />
              <ValidatedField label={translate('diChApp.bookMark.label')} id="book-mark-label" name="label" data-cy="label" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/book-mark" replace color="info">
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

export default BookMarkUpdate;

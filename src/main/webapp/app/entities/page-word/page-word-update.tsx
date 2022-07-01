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
import { IPageWord } from 'app/shared/model/page-word.model';
import { getEntity, updateEntity, createEntity, reset } from './page-word.reducer';

export const PageWordUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const pageImages = useAppSelector(state => state.pageImage.entities);
  const pageWordEntity = useAppSelector(state => state.pageWord.entity);
  const loading = useAppSelector(state => state.pageWord.loading);
  const updating = useAppSelector(state => state.pageWord.updating);
  const updateSuccess = useAppSelector(state => state.pageWord.updateSuccess);
  const handleClose = () => {
    props.history.push('/page-word');
  };

  useEffect(() => {
    if (!isNew) {
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
      ...pageWordEntity,
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
          ...pageWordEntity,
          pageImage: pageWordEntity?.pageImage?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diChApp.pageWord.home.createOrEditLabel" data-cy="PageWordCreateUpdateHeading">
            <Translate contentKey="diChApp.pageWord.home.createOrEditLabel">Create or edit a PageWord</Translate>
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
                  id="page-word-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('diChApp.pageWord.word')} id="page-word-word" name="word" data-cy="word" type="text" />
              <ValidatedField label={translate('diChApp.pageWord.left')} id="page-word-left" name="left" data-cy="left" type="text" />
              <ValidatedField label={translate('diChApp.pageWord.top')} id="page-word-top" name="top" data-cy="top" type="text" />
              <ValidatedField label={translate('diChApp.pageWord.right')} id="page-word-right" name="right" data-cy="right" type="text" />
              <ValidatedField
                label={translate('diChApp.pageWord.bottom')}
                id="page-word-bottom"
                name="bottom"
                data-cy="bottom"
                type="text"
              />
              <ValidatedField
                id="page-word-pageImage"
                name="pageImage"
                data-cy="pageImage"
                label={translate('diChApp.pageWord.pageImage')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/page-word" replace color="info">
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

export default PageWordUpdate;

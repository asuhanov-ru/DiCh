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
import { ITextBlock } from 'app/shared/model/text-block.model';
import { getEntity, updateEntity, createEntity, reset } from './text-block.reducer';

export const TextBlockUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const media = useAppSelector(state => state.media.entities);
  const textBlockEntity = useAppSelector(state => state.textBlock.entity);
  const loading = useAppSelector(state => state.textBlock.loading);
  const updating = useAppSelector(state => state.textBlock.updating);
  const updateSuccess = useAppSelector(state => state.textBlock.updateSuccess);
  const handleClose = () => {
    props.history.push('/text-block');
  };

  useEffect(() => {
    if (!isNew) {
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
      ...textBlockEntity,
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
          ...textBlockEntity,
          media: textBlockEntity?.media?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diChApp.textBlock.home.createOrEditLabel" data-cy="TextBlockCreateUpdateHeading">
            <Translate contentKey="diChApp.textBlock.home.createOrEditLabel">Create or edit a TextBlock</Translate>
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
                  id="text-block-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diChApp.textBlock.pageNumber')}
                id="text-block-pageNumber"
                name="pageNumber"
                data-cy="pageNumber"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.textBlock.blockIndex')}
                id="text-block-blockIndex"
                name="blockIndex"
                data-cy="blockIndex"
                type="text"
              />
              <ValidatedField id="text-block-media" name="media" data-cy="media" label={translate('diChApp.textBlock.media')} type="select">
                <option value="" key="0" />
                {media
                  ? media.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.fileName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/text-block" replace color="info">
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

export default TextBlockUpdate;

import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITextBlock } from 'app/shared/model/text-block.model';
import { getEntities as getTextBlocks } from 'app/entities/text-block/text-block.reducer';
import { IInlineStyleRanges } from 'app/shared/model/inline-style-ranges.model';
import { getEntity, updateEntity, createEntity, reset } from './inline-style-ranges.reducer';

export const InlineStyleRangesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const textBlocks = useAppSelector(state => state.textBlock.entities);
  const inlineStyleRangesEntity = useAppSelector(state => state.inlineStyleRanges.entity);
  const loading = useAppSelector(state => state.inlineStyleRanges.loading);
  const updating = useAppSelector(state => state.inlineStyleRanges.updating);
  const updateSuccess = useAppSelector(state => state.inlineStyleRanges.updateSuccess);
  const handleClose = () => {
    props.history.push('/inline-style-ranges');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTextBlocks({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...inlineStyleRangesEntity,
      ...values,
      textBlock: textBlocks.find(it => it.id.toString() === values.textBlock.toString()),
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
          ...inlineStyleRangesEntity,
          textBlock: inlineStyleRangesEntity?.textBlock?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diChApp.inlineStyleRanges.home.createOrEditLabel" data-cy="InlineStyleRangesCreateUpdateHeading">
            <Translate contentKey="diChApp.inlineStyleRanges.home.createOrEditLabel">Create or edit a InlineStyleRanges</Translate>
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
                  id="inline-style-ranges-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diChApp.inlineStyleRanges.startPos')}
                id="inline-style-ranges-startPos"
                name="startPos"
                data-cy="startPos"
                type="text"
              />
              <ValidatedField
                label={translate('diChApp.inlineStyleRanges.stopPos')}
                id="inline-style-ranges-stopPos"
                name="stopPos"
                data-cy="stopPos"
                type="text"
              />
              <ValidatedField
                id="inline-style-ranges-textBlock"
                name="textBlock"
                data-cy="textBlock"
                label={translate('diChApp.inlineStyleRanges.textBlock')}
                type="select"
              >
                <option value="" key="0" />
                {textBlocks
                  ? textBlocks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inline-style-ranges" replace color="info">
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

export default InlineStyleRangesUpdate;

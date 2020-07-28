import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICollections } from 'app/shared/model/collections.model';
import { getEntities as getCollections } from 'app/entities/collections/collections.reducer';
import { getEntity, updateEntity, createEntity, reset } from './media.reducer';
import { IMedia } from 'app/shared/model/media.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMediaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MediaUpdate = (props: IMediaUpdateProps) => {
  const [collectionsId, setCollectionsId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { mediaEntity, collections, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/media');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCollections();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...mediaEntity,
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
          <h2 id="diChApp.media.home.createOrEditLabel">
            <Translate contentKey="diChApp.media.home.createOrEditLabel">Create or edit a Media</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : mediaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="media-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="media-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="fileNameLabel" for="media-fileName">
                  <Translate contentKey="diChApp.media.fileName">File Name</Translate>
                </Label>
                <AvField
                  id="media-fileName"
                  type="text"
                  name="fileName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fileTypeLabel" for="media-fileType">
                  <Translate contentKey="diChApp.media.fileType">File Type</Translate>
                </Label>
                <AvField
                  id="media-fileType"
                  type="text"
                  name="fileType"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fileDescLabel" for="media-fileDesc">
                  <Translate contentKey="diChApp.media.fileDesc">File Desc</Translate>
                </Label>
                <AvField id="media-fileDesc" type="text" name="fileDesc" />
              </AvGroup>
              <AvGroup>
                <Label for="media-collections">
                  <Translate contentKey="diChApp.media.collections">Collections</Translate>
                </Label>
                <AvInput id="media-collections" type="select" className="form-control" name="collections.id">
                  <option value="" key="0" />
                  {collections
                    ? collections.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/media" replace color="info">
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
  collections: storeState.collections.entities,
  mediaEntity: storeState.media.entity,
  loading: storeState.media.loading,
  updating: storeState.media.updating,
  updateSuccess: storeState.media.updateSuccess
});

const mapDispatchToProps = {
  getCollections,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MediaUpdate);

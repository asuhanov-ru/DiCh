import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ocr-tasks.reducer';

export const OcrTasksDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ocrTasksEntity = useAppSelector(state => state.ocrTasks.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ocrTasksDetailsHeading">
          <Translate contentKey="diChApp.ocrTasks.detail.title">OcrTasks</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ocrTasksEntity.id}</dd>
          <dt>
            <span id="mediaId">
              <Translate contentKey="diChApp.ocrTasks.mediaId">Media Id</Translate>
            </span>
          </dt>
          <dd>{ocrTasksEntity.mediaId}</dd>
          <dt>
            <span id="pageNumber">
              <Translate contentKey="diChApp.ocrTasks.pageNumber">Page Number</Translate>
            </span>
          </dt>
          <dd>{ocrTasksEntity.pageNumber}</dd>
          <dt>
            <span id="jobStatus">
              <Translate contentKey="diChApp.ocrTasks.jobStatus">Job Status</Translate>
            </span>
          </dt>
          <dd>{ocrTasksEntity.jobStatus}</dd>
          <dt>
            <span id="createTime">
              <Translate contentKey="diChApp.ocrTasks.createTime">Create Time</Translate>
            </span>
          </dt>
          <dd>
            {ocrTasksEntity.createTime ? <TextFormat value={ocrTasksEntity.createTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="diChApp.ocrTasks.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>{ocrTasksEntity.startTime ? <TextFormat value={ocrTasksEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="stopTime">
              <Translate contentKey="diChApp.ocrTasks.stopTime">Stop Time</Translate>
            </span>
          </dt>
          <dd>{ocrTasksEntity.stopTime ? <TextFormat value={ocrTasksEntity.stopTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/ocr-tasks" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ocr-tasks/${ocrTasksEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OcrTasksDetail;

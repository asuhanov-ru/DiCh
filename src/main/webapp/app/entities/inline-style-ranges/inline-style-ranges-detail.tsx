import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inline-style-ranges.reducer';

export const InlineStyleRangesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const inlineStyleRangesEntity = useAppSelector(state => state.inlineStyleRanges.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inlineStyleRangesDetailsHeading">
          <Translate contentKey="diChApp.inlineStyleRanges.detail.title">InlineStyleRanges</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{inlineStyleRangesEntity.id}</dd>
          <dt>
            <span id="startPos">
              <Translate contentKey="diChApp.inlineStyleRanges.startPos">Start Pos</Translate>
            </span>
          </dt>
          <dd>{inlineStyleRangesEntity.startPos}</dd>
          <dt>
            <span id="stopPos">
              <Translate contentKey="diChApp.inlineStyleRanges.stopPos">Stop Pos</Translate>
            </span>
          </dt>
          <dd>{inlineStyleRangesEntity.stopPos}</dd>
          <dt>
            <Translate contentKey="diChApp.inlineStyleRanges.textBlock">Text Block</Translate>
          </dt>
          <dd>{inlineStyleRangesEntity.textBlock ? inlineStyleRangesEntity.textBlock.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/inline-style-ranges" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inline-style-ranges/${inlineStyleRangesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InlineStyleRangesDetail;

import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './text-block.reducer';

export const TextBlockDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const textBlockEntity = useAppSelector(state => state.textBlock.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="textBlockDetailsHeading">
          <Translate contentKey="diChApp.textBlock.detail.title">TextBlock</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{textBlockEntity.id}</dd>
          <dt>
            <span id="pageNumber">
              <Translate contentKey="diChApp.textBlock.pageNumber">Page Number</Translate>
            </span>
          </dt>
          <dd>{textBlockEntity.pageNumber}</dd>
          <dt>
            <span id="blockIndex">
              <Translate contentKey="diChApp.textBlock.blockIndex">Block Index</Translate>
            </span>
          </dt>
          <dd>{textBlockEntity.blockIndex}</dd>
          <dt>
            <Translate contentKey="diChApp.textBlock.media">Media</Translate>
          </dt>
          <dd>{textBlockEntity.media ? textBlockEntity.media.fileName : ''}</dd>
        </dl>
        <Button tag={Link} to="/text-block" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/text-block/${textBlockEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TextBlockDetail;

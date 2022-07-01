import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './page-word.reducer';

export const PageWordDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const pageWordEntity = useAppSelector(state => state.pageWord.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pageWordDetailsHeading">
          <Translate contentKey="diChApp.pageWord.detail.title">PageWord</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pageWordEntity.id}</dd>
          <dt>
            <span id="word">
              <Translate contentKey="diChApp.pageWord.word">Word</Translate>
            </span>
          </dt>
          <dd>{pageWordEntity.word}</dd>
          <dt>
            <span id="left">
              <Translate contentKey="diChApp.pageWord.left">Left</Translate>
            </span>
          </dt>
          <dd>{pageWordEntity.left}</dd>
          <dt>
            <span id="top">
              <Translate contentKey="diChApp.pageWord.top">Top</Translate>
            </span>
          </dt>
          <dd>{pageWordEntity.top}</dd>
          <dt>
            <span id="right">
              <Translate contentKey="diChApp.pageWord.right">Right</Translate>
            </span>
          </dt>
          <dd>{pageWordEntity.right}</dd>
          <dt>
            <span id="bottom">
              <Translate contentKey="diChApp.pageWord.bottom">Bottom</Translate>
            </span>
          </dt>
          <dd>{pageWordEntity.bottom}</dd>
          <dt>
            <Translate contentKey="diChApp.pageWord.pageImage">Page Image</Translate>
          </dt>
          <dd>{pageWordEntity.pageImage ? pageWordEntity.pageImage.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/page-word" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/page-word/${pageWordEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PageWordDetail;

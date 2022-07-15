import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './translation.reducer';

export const TranslationDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const translationEntity = useAppSelector(state => state.translation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="translationDetailsHeading">
          <Translate contentKey="diChApp.translation.detail.title">Translation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{translationEntity.id}</dd>
          <dt>
            <span id="lang">
              <Translate contentKey="diChApp.translation.lang">Lang</Translate>
            </span>
          </dt>
          <dd>{translationEntity.lang}</dd>
          <dt>
            <span id="n_version">
              <Translate contentKey="diChApp.translation.n_version">N Version</Translate>
            </span>
          </dt>
          <dd>{translationEntity.n_version}</dd>
        </dl>
        <Button tag={Link} to="/translation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/translation/${translationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TranslationDetail;

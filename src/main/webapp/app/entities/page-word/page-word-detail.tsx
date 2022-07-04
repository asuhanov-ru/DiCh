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
            <span id="s_word">
              <Translate contentKey="diChApp.pageWord.s_word">S Word</Translate>
            </span>
          </dt>
          <dd>{pageWordEntity.s_word}</dd>
          <dt>
            <span id="n_top">
              <Translate contentKey="diChApp.pageWord.n_top">N Top</Translate>
            </span>
          </dt>
          <dd>{pageWordEntity.n_top}</dd>
          <dt>
            <span id="n_left">
              <Translate contentKey="diChApp.pageWord.n_left">N Left</Translate>
            </span>
          </dt>
          <dd>{pageWordEntity.n_left}</dd>
          <dt>
            <span id="n_heigth">
              <Translate contentKey="diChApp.pageWord.n_heigth">N Heigth</Translate>
            </span>
          </dt>
          <dd>{pageWordEntity.n_heigth}</dd>
          <dt>
            <span id="n_width">
              <Translate contentKey="diChApp.pageWord.n_width">N Width</Translate>
            </span>
          </dt>
          <dd>{pageWordEntity.n_width}</dd>
          <dt>
            <span id="n_idx">
              <Translate contentKey="diChApp.pageWord.n_idx">N Idx</Translate>
            </span>
          </dt>
          <dd>{pageWordEntity.n_idx}</dd>
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

import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './author.reducer';

export const AuthorDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const authorEntity = useAppSelector(state => state.author.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="authorDetailsHeading">
          <Translate contentKey="diChApp.author.detail.title">Author</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{authorEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="diChApp.author.name">Name</Translate>
            </span>
          </dt>
          <dd>{authorEntity.name}</dd>
          <dt>
            <span id="callsign">
              <Translate contentKey="diChApp.author.callsign">Callsign</Translate>
            </span>
          </dt>
          <dd>{authorEntity.callsign}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="diChApp.author.description">Description</Translate>
            </span>
          </dt>
          <dd>{authorEntity.description}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="diChApp.author.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {authorEntity.image ? (
              <div>
                {authorEntity.imageContentType ? (
                  <a onClick={openFile(authorEntity.imageContentType, authorEntity.image)}>
                    <img src={`data:${authorEntity.imageContentType};base64,${authorEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {authorEntity.imageContentType}, {byteSize(authorEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/author" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/author/${authorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AuthorDetail;

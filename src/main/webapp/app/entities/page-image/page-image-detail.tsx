import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './page-image.reducer';

export const PageImageDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const pageImageEntity = useAppSelector(state => state.pageImage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pageImageDetailsHeading">
          <Translate contentKey="diChApp.pageImage.detail.title">PageImage</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pageImageEntity.id}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="diChApp.pageImage.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {pageImageEntity.image ? (
              <div>
                {pageImageEntity.imageContentType ? (
                  <a onClick={openFile(pageImageEntity.imageContentType, pageImageEntity.image)}>
                    <img src={`data:${pageImageEntity.imageContentType};base64,${pageImageEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {pageImageEntity.imageContentType}, {byteSize(pageImageEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/page-image" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/page-image/${pageImageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/views/page-ocr/${pageImageEntity.id}`} replace color="primary">
          <FontAwesomeIcon icon="arrow-right" /> <span className="d-none d-md-inline">OCR</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PageImageDetail;

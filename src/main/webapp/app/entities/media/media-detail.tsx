import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './media.reducer';
import { IMedia } from 'app/shared/model/media.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMediaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MediaDetail = (props: IMediaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { mediaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="diChApp.media.detail.title">Media</Translate> [<b>{mediaEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="fileName">
              <Translate contentKey="diChApp.media.fileName">File Name</Translate>
            </span>
          </dt>
          <dd>{mediaEntity.fileName}</dd>
          <dt>
            <span id="fileType">
              <Translate contentKey="diChApp.media.fileType">File Type</Translate>
            </span>
          </dt>
          <dd>{mediaEntity.fileType}</dd>
          <dt>
            <span id="fileDesc">
              <Translate contentKey="diChApp.media.fileDesc">File Desc</Translate>
            </span>
          </dt>
          <dd>{mediaEntity.fileDesc}</dd>
          <dt>
            <Translate contentKey="diChApp.media.collections">Collections</Translate>
          </dt>
          <dd>{mediaEntity.collections ? mediaEntity.collections.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/media" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/media/${mediaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ media }: IRootState) => ({
  mediaEntity: media.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MediaDetail);

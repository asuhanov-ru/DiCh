import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './media-structure.reducer';
import { IMediaStructure } from 'app/shared/model/media-structure.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMediaStructureProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const MediaStructure = (props: IMediaStructureProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { mediaStructureList, match, loading } = props;
  return (
    <div>
      <h2 id="media-structure-heading">
        <Translate contentKey="diChApp.mediaStructure.home.title">Media Structures</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="diChApp.mediaStructure.home.createLabel">Create new Media Structure</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {mediaStructureList && mediaStructureList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="diChApp.mediaStructure.objName">Obj Name</Translate>
                </th>
                <th>
                  <Translate contentKey="diChApp.mediaStructure.objType">Obj Type</Translate>
                </th>
                <th>
                  <Translate contentKey="diChApp.mediaStructure.parentId">Parent Id</Translate>
                </th>
                <th>
                  <Translate contentKey="diChApp.mediaStructure.tag">Tag</Translate>
                </th>
                <th>
                  <Translate contentKey="diChApp.mediaStructure.media">Media</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mediaStructureList.map((mediaStructure, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${mediaStructure.id}`} color="link" size="sm">
                      {mediaStructure.id}
                    </Button>
                  </td>
                  <td>{mediaStructure.objName}</td>
                  <td>{mediaStructure.objType}</td>
                  <td>{mediaStructure.parentId}</td>
                  <td>{mediaStructure.tag}</td>
                  <td>
                    {mediaStructure.media ? <Link to={`media/${mediaStructure.media.id}`}>{mediaStructure.media.fileName}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mediaStructure.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mediaStructure.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mediaStructure.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="diChApp.mediaStructure.home.notFound">No Media Structures found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ mediaStructure }: IRootState) => ({
  mediaStructureList: mediaStructure.entities,
  loading: mediaStructure.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MediaStructure);

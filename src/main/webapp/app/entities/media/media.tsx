import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMedia } from 'app/shared/model/media.model';
import { getEntities } from './media.reducer';

export const Media = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const mediaList = useAppSelector(state => state.media.entities);
  const loading = useAppSelector(state => state.media.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="media-heading" data-cy="MediaHeading">
        <Translate contentKey="diChApp.media.home.title">Media</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="diChApp.media.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/media/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="diChApp.media.home.createLabel">Create new Media</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {mediaList && mediaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="diChApp.media.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="diChApp.media.fileName">File Name</Translate>
                </th>
                <th>
                  <Translate contentKey="diChApp.media.fileType">File Type</Translate>
                </th>
                <th>
                  <Translate contentKey="diChApp.media.fileDesc">File Desc</Translate>
                </th>
                <th>
                  <Translate contentKey="diChApp.media.collections">Collections</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mediaList.map((media, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/media/${media.id}`} color="link" size="sm">
                      {media.id}
                    </Button>
                  </td>
                  <td>{media.fileName}</td>
                  <td>{media.fileType}</td>
                  <td>{media.fileDesc}</td>
                  <td>{media.collections ? <Link to={`/collections/${media.collections.id}`}>{media.collections.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/media/${media.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/media/${media.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/media/${media.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="diChApp.media.home.notFound">No Media found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Media;

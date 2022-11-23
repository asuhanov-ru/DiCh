import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICollections } from 'app/shared/model/collections.model';
import { getEntities } from './collections.reducer';

export const Collections = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const collectionsList = useAppSelector(state => state.collections.entities);
  const loading = useAppSelector(state => state.collections.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="collections-heading" data-cy="CollectionsHeading">
        <Translate contentKey="diChApp.collections.home.title">Collections</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="diChApp.collections.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/collections/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="diChApp.collections.home.createLabel">Create new Collections</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {collectionsList && collectionsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="diChApp.collections.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="diChApp.collections.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="diChApp.collections.description">Description</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {collectionsList.map((collections, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/collections/${collections.id}`} color="link" size="sm">
                      {collections.id}
                    </Button>
                  </td>
                  <td>{collections.name}</td>
                  <td>{collections.description}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/collections/${collections.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/collections/${collections.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/collections/${collections.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="diChApp.collections.home.notFound">No Collections found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Collections;

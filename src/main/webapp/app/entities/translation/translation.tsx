import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITranslation } from 'app/shared/model/translation.model';
import { getEntities } from './translation.reducer';

export const Translation = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const translationList = useAppSelector(state => state.translation.entities);
  const loading = useAppSelector(state => state.translation.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="translation-heading" data-cy="TranslationHeading">
        <Translate contentKey="diChApp.translation.home.title">Translations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="diChApp.translation.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/translation/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="diChApp.translation.home.createLabel">Create new Translation</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {translationList && translationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="diChApp.translation.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="diChApp.translation.lang">Lang</Translate>
                </th>
                <th>
                  <Translate contentKey="diChApp.translation.n_version">N Version</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {translationList.map((translation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/translation/${translation.id}`} color="link" size="sm">
                      {translation.id}
                    </Button>
                  </td>
                  <td>{translation.lang}</td>
                  <td>{translation.n_version}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/translation/${translation.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/translation/${translation.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/translation/${translation.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="diChApp.translation.home.notFound">No Translations found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Translation;

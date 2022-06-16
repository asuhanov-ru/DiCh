import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Collections from './collections';
import CollectionsDetail from './collections-detail';
import CollectionsUpdate from './collections-update';
import CollectionsDeleteDialog from './collections-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CollectionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CollectionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CollectionsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Collections} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CollectionsDeleteDialog} />
  </>
);

export default Routes;

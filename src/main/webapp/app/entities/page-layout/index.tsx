import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PageLayout from './page-layout';
import PageLayoutDetail from './page-layout-detail';
import PageLayoutUpdate from './page-layout-update';
import PageLayoutDeleteDialog from './page-layout-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PageLayoutUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PageLayoutUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PageLayoutDetail} />
      <ErrorBoundaryRoute path={match.url} component={PageLayout} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PageLayoutDeleteDialog} />
  </>
);

export default Routes;

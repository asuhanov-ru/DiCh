import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PageText from './page-text';
import PageTextDetail from './page-text-detail';
import PageTextUpdate from './page-text-update';
import PageTextDeleteDialog from './page-text-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PageTextUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PageTextUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PageTextDetail} />
      <ErrorBoundaryRoute path={match.url} component={PageText} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PageTextDeleteDialog} />
  </>
);

export default Routes;

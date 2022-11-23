import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PageWord from './page-word';
import PageWordDetail from './page-word-detail';
import PageWordUpdate from './page-word-update';
import PageWordDeleteDialog from './page-word-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PageWordUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PageWordUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PageWordDetail} />
      <ErrorBoundaryRoute path={match.url} component={PageWord} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PageWordDeleteDialog} />
  </>
);

export default Routes;

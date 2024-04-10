import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TextBlock from './text-block';
import TextBlockDetail from './text-block-detail';
import TextBlockUpdate from './text-block-update';
import TextBlockDeleteDialog from './text-block-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TextBlockUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TextBlockUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TextBlockDetail} />
      <ErrorBoundaryRoute path={match.url} component={TextBlock} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TextBlockDeleteDialog} />
  </>
);

export default Routes;

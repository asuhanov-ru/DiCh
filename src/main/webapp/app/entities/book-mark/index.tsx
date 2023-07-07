import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BookMark from './book-mark';
import BookMarkDetail from './book-mark-detail';
import BookMarkUpdate from './book-mark-update';
import BookMarkDeleteDialog from './book-mark-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BookMarkUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BookMarkUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BookMarkDetail} />
      <ErrorBoundaryRoute path={match.url} component={BookMark} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BookMarkDeleteDialog} />
  </>
);

export default Routes;

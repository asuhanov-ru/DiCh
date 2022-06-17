import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PageImage from './page-image';
import PageImageDetail from './page-image-detail';
import PageImageUpdate from './page-image-update';
import PageImageDeleteDialog from './page-image-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PageImageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PageImageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PageImageDetail} />
      <ErrorBoundaryRoute path={match.url} component={PageImage} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PageImageDeleteDialog} />
  </>
);

export default Routes;

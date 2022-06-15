import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MediaStructure from './media-structure';
import MediaStructureDetail from './media-structure-detail';
import MediaStructureUpdate from './media-structure-update';
import MediaStructureDeleteDialog from './media-structure-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MediaStructureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MediaStructureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MediaStructureDetail} />
      <ErrorBoundaryRoute path={match.url} component={MediaStructure} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MediaStructureDeleteDialog} />
  </>
);

export default Routes;

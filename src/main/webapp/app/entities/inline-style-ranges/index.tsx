import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InlineStyleRanges from './inline-style-ranges';
import InlineStyleRangesDetail from './inline-style-ranges-detail';
import InlineStyleRangesUpdate from './inline-style-ranges-update';
import InlineStyleRangesDeleteDialog from './inline-style-ranges-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InlineStyleRangesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InlineStyleRangesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InlineStyleRangesDetail} />
      <ErrorBoundaryRoute path={match.url} component={InlineStyleRanges} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InlineStyleRangesDeleteDialog} />
  </>
);

export default Routes;

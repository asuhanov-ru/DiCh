import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OcrTasks from './ocr-tasks';
import OcrTasksDetail from './ocr-tasks-detail';
import OcrTasksUpdate from './ocr-tasks-update';
import OcrTasksDeleteDialog from './ocr-tasks-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OcrTasksUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OcrTasksUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OcrTasksDetail} />
      <ErrorBoundaryRoute path={match.url} component={OcrTasks} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OcrTasksDeleteDialog} />
  </>
);

export default Routes;

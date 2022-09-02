import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PageOcr from './page-ocr';
import PageMedia from './media';

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}/page-ocr`} component={PageOcr} />
        <ErrorBoundaryRoute path={`${match.url}/page-media`} component={PageMedia} />
      </Switch>
    </div>
  );
};

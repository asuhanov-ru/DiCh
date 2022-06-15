import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Collections from './collections';
import Media from './media';
import MediaStructure from './media-structure';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}collections`} component={Collections} />
      <ErrorBoundaryRoute path={`${match.url}media`} component={Media} />
      <ErrorBoundaryRoute path={`${match.url}media-structure`} component={MediaStructure} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;

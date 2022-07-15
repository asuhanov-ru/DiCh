import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Collections from './collections';
import Media from './media';
import MediaStructure from './media-structure';
import PageImage from './page-image';
import PageText from './page-text';
import PageWord from './page-word';
import Translation from './translation';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}collections`} component={Collections} />
        <ErrorBoundaryRoute path={`${match.url}media`} component={Media} />
        <ErrorBoundaryRoute path={`${match.url}media-structure`} component={MediaStructure} />
        <ErrorBoundaryRoute path={`${match.url}page-image`} component={PageImage} />
        <ErrorBoundaryRoute path={`${match.url}page-text`} component={PageText} />
        <ErrorBoundaryRoute path={`${match.url}page-word`} component={PageWord} />
        <ErrorBoundaryRoute path={`${match.url}translation`} component={Translation} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};

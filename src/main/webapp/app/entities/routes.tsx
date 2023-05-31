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
import OcrTasks from './ocr-tasks';
import PageLayout from './page-layout';
import Author from './author';
import Book from './book';
import TextBlock from './text-block';
import InlineStyleRanges from './inline-style-ranges';
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
        <ErrorBoundaryRoute path={`${match.url}ocr-tasks`} component={OcrTasks} />
        <ErrorBoundaryRoute path={`${match.url}page-layout`} component={PageLayout} />
        <ErrorBoundaryRoute path={`${match.url}author`} component={Author} />
        <ErrorBoundaryRoute path={`${match.url}book`} component={Book} />
        <ErrorBoundaryRoute path={`${match.url}text-block`} component={TextBlock} />
        <ErrorBoundaryRoute path={`${match.url}inline-style-ranges`} component={InlineStyleRanges} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};

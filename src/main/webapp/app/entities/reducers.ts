import collections from 'app/entities/collections/collections.reducer';
import media from 'app/entities/media/media.reducer';
import mediaStructure from 'app/entities/media-structure/media-structure.reducer';
import pageImage from 'app/entities/page-image/page-image.reducer';
import pageText from 'app/entities/page-text/page-text.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  collections,
  media,
  mediaStructure,
  pageImage,
  pageText,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;

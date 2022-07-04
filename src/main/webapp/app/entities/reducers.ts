import collections from 'app/entities/collections/collections.reducer';
import media from 'app/entities/media/media.reducer';
import mediaStructure from 'app/entities/media-structure/media-structure.reducer';
import pageImage from 'app/entities/page-image/page-image.reducer';
import pageText from 'app/entities/page-text/page-text.reducer';
import pageWord from 'app/entities/page-word/page-word.reducer';
import pageOcr from 'app/pages/page-ocr/page-ocr.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  collections,
  media,
  mediaStructure,
  pageImage,
  pageText,
  pageWord,
  pageOcr,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;

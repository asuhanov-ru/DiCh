import collections from 'app/entities/collections/collections.reducer';
import media from 'app/entities/media/media.reducer';
import mediaStructure from 'app/entities/media-structure/media-structure.reducer';
import pageImage from 'app/entities/page-image/page-image.reducer';
import pageText from 'app/entities/page-text/page-text.reducer';
import pageWord from 'app/entities/page-word/page-word.reducer';
import pageOcr from 'app/pages/page-ocr/page-ocr.reducer';
import translation from 'app/entities/translation/translation.reducer';
import pageImageTransfer from 'app/pages/media/image/reducer';
import ocrTasks from 'app/entities/ocr-tasks/ocr-tasks.reducer';
import pageLayout from 'app/entities/page-layout/page-layout.reducer';
import ocrTransfer from 'app/pages/media/ocr/reducer';
import author from 'app/entities/author/author.reducer';
import book from 'app/entities/book/book.reducer';
import textBlock from 'app/entities/text-block/text-block.reducer';
import inlineStyleRanges from 'app/entities/inline-style-ranges/inline-style-ranges.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  collections,
  media,
  mediaStructure,
  pageImage,
  pageText,
  pageWord,
  pageOcr,
  translation,
  pageImageTransfer,
  ocrTasks,
  pageLayout,
  ocrTransfer,
  author,
  book,
  textBlock,
  inlineStyleRanges,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;

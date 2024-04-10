export const defaultToolbar = {
  options: ['navigate', 'panZoom', 'layoutSelection', 'ocrTools', 'tab'],
  navigate: {
    options: ['prev', 'next', 'page', 'total'],
    prev: { icon: 'arrow-left', className: undefined, title: 'Prev page', component: 'option' },
    next: { icon: 'arrow-right', className: undefined, title: 'Next page', component: 'option' },
    page: { icon: '', className: undefined, title: undefined, component: 'edit' },
    total: { icon: '', className: undefined, title: '/ ', component: 'static' },
  },
  panZoom: {
    options: ['panZoomOnOff', 'zoomIn', 'zoomOut', 'zoomPercent', 'transformOrigin', 'resetTransfrom'],
    panZoomOnOff: { icon: 'fa-up-down-left-right', className: undefined, title: undefined, component: 'option', property: 'switch' },
    zoomIn: { icon: 'fa-plus', className: undefined, title: undefined, component: 'option' },
    zoomOut: { icon: 'fa-minus', className: undefined, title: undefined, component: 'option' },
    zoomPercent: { icon: 'faArrowLeft', className: undefined, title: undefined, component: 'edit' },
    transformOrigin: {
      inDropdown: true,
      options: ['topLeft', 'center'],
      topLeft: {},
      center: {},
    },
    resetTransfrom: { icon: 'fa-arrow-up-right-from-square', className: undefined, title: 'Reset transform', component: 'option' },
  },
  layoutSelection: {
    options: ['selectionToolOnOff', 'layoutOptions'],
    selectionToolOnOff: { icon: 'mouse-pointer', className: undefined, title: undefined, component: 'option', property: 'switch' },
    layoutOptions: {
      options: ['block', 'paragraph'],
    },
  },
  ocrTools: {
    options: ['ocrSelected'],
    ocrSelected: { icon: 'fa-list', className: undefined, title: 'Do Ocr', component: 'option' },
  },
  tab: {
    options: ['editor', 'bookMark', 'glossary', 'outlines', 'book'],
    editor: { icon: 'edit', className: undefined, title: 'Editor', component: 'option', property: 'switch' },
    bookMark: { icon: 'book-bookmark', className: undefined, title: 'Bookmarks', component: 'option', property: 'switch' },
    glossary: { icon: 'table', className: undefined, title: 'Glossary', component: 'option', property: 'switch' },
    outlines: { icon: 'code-branch', className: undefined, title: 'Outlines', component: 'option', property: 'switch' },
    book: { icon: 'book', className: undefined, title: 'Books', component: 'option', property: 'switch' },
  },
};

export const editorToolbarExtensions = {
  options: ['word'],
  word: {
    options: ['split', 'merge', 'delete', 'replace', 'save'],
    split: { icon: 'fa-divide', className: undefined, title: undefined, component: 'option' },
    merge: { icon: 'fa-plus', className: undefined, title: undefined, component: 'option' },
    delete: { icon: 'fa-trash', className: undefined, title: undefined, component: 'option' },
    replace: { icon: 'fa-pencil-alt', className: undefined, title: undefined, component: 'option' },
    save: { icon: 'fa-save', className: undefined, title: undefined, component: 'option' },
  },
};

export const mediaPaneDefaultState = {
  navigate: {
    page: 1,
  },
  panZoom: {
    panZoomOnOff: true,
  },
  layoutSelection: {
    selectionToolOnOff: false,
  },
  tab: {
    editor: true,
    glossary: false,
    outlines: false,
    book: false,
  },
};

export const outlinesPaneToolbar = {
  options: ['word'],
  word: {
    options: ['gotoPage'],
    gotoPage: { icon: 'fa-walking', className: undefined, title: 'Goto page', component: 'option' },
  },
};

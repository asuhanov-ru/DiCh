export const defaultToolbar = {
  options: ['navigate', 'panZoom', 'layoutSelection', 'ocrTools'],
  navigate: {
    options: ['prev', 'next', 'page', 'total'],
    prev: { icon: 'arrow-left', className: undefined, title: undefined, component: 'option' },
    next: { icon: 'arrow-right', className: undefined, title: undefined, component: 'option' },
    page: { icon: 'faArrowLeft', className: undefined, title: undefined, component: 'edit' },
    total: { icon: 'faArrowLeft', className: undefined, title: undefined, component: 'static' },
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
    resetTransfrom: { icon: 'fa-arrow-up-right-from-square', className: undefined, title: undefined, component: 'option' },
  },
  layoutSelection: {
    options: ['selectionTollOnOff', 'layoutOptions'],
    selectionTollOnOff: { icon: 'location-arrow', className: undefined, title: undefined, component: 'option', property: 'switch' },
    layoutOptions: {
      options: ['block', 'paragraph'],
    },
  },
  ocrTools: {
    options: ['ocrSelected'],
    ocrSelected: { icon: 'fa-list', className: undefined, title: undefined, component: 'option' },
  },
};

import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/collections">
        <Translate contentKey="global.menu.entities.collections" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/media">
        <Translate contentKey="global.menu.entities.media" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/media-structure">
        <Translate contentKey="global.menu.entities.mediaStructure" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/page-image">
        <Translate contentKey="global.menu.entities.pageImage" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/page-text">
        <Translate contentKey="global.menu.entities.pageText" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/page-word">
        <Translate contentKey="global.menu.entities.pageWord" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/translation">
        <Translate contentKey="global.menu.entities.translation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/ocr-tasks">
        <Translate contentKey="global.menu.entities.ocrTasks" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/page-layout">
        <Translate contentKey="global.menu.entities.pageLayout" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/author">
        <Translate contentKey="global.menu.entities.author" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/book">
        <Translate contentKey="global.menu.entities.book" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/text-block">
        <Translate contentKey="global.menu.entities.textBlock" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/inline-style-ranges">
        <Translate contentKey="global.menu.entities.inlineStyleRanges" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/book-mark">
        <Translate contentKey="global.menu.entities.bookMark" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;

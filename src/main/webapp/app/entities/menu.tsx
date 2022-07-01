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
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;

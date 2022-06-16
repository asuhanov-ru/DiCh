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
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
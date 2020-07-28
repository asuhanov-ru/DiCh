import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
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
  </NavDropdown>
);

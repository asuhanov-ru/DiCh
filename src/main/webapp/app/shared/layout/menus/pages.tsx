import React from 'react';
import { translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';
import PagesMenuItems from 'app/pages/menu';

export const PagesMenu = props => (
  <NavDropdown icon="th-list" name="PAGES" id="pages-menu" data-cy="page" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <PagesMenuItems />
  </NavDropdown>
);

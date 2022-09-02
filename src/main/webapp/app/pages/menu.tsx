import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const PagesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/views/page-media">Media</MenuItem>
    </>
  );
};

export default PagesMenu as React.ComponentType<any>;

import { unsafeCSS, registerStyles } from '@vaadin/vaadin-themable-mixin/register-styles';

import vaadinSideNavItemCss from 'themes/starpass/components/vaadin-side-nav-item.css?inline';
import vaadinGridCss from 'themes/starpass/components/vaadin-grid.css?inline';
import vaadinButtonCss from 'themes/starpass/components/vaadin-button.css?inline';


if (!document['_vaadintheme_starpass_componentCss']) {
  registerStyles(
        'vaadin-side-nav-item',
        unsafeCSS(vaadinSideNavItemCss.toString())
      );
      registerStyles(
        'vaadin-grid',
        unsafeCSS(vaadinGridCss.toString())
      );
      registerStyles(
        'vaadin-button',
        unsafeCSS(vaadinButtonCss.toString())
      );
      
  document['_vaadintheme_starpass_componentCss'] = true;
}

if (import.meta.hot) {
  import.meta.hot.accept((module) => {
    window.location.reload();
  });
}


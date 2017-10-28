import "../../common/common";
import carouselRouting from "./carousel.routing";

const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'ui.router',
  'Common',
  'ui.bootstrap',
]);
app.config(carouselRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'carousel';
  $rootScope.config = {
    "navScene": 'main',
    "isSidebarFold": false,
    "disableNavigation": false,
    "hideSidebar": false,
    "sidebar": "full",
    "productNavBar": 'disabled'
  };
  $rootScope.navConfig = {
    "product": {
      "show": true,
      "folded": false
    },
    "service": {
      "show": true,
      "folded": false
    },
    "system": {
      "show": true,
      "folded": false
    }
  };

});

export default MODULE_NAME;
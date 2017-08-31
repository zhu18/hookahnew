import '../../common/common';
import localResourcesRouting from './localResources.routing';
import "treeGridCss";
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(localResourcesRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'localResources';
  $rootScope.config = {
    "navScene": 'main',
    "isSidebarFold": false,
    "disableNavigation": false,
    "hideSidebar": false,
    "sidebar": "full",
    "productNavBar": "col-1"
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
  $rootScope.config.title = '地方渠道资源管理';
  $rootScope.config.mainNav =[
    {
      "title":"地方渠道资源管理",
      "url":"localResources.list"
    }
  ];

});

export default MODULE_NAME;
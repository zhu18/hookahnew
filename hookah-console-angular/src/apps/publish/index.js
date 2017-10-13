import '../../common/common';
import publishRouting from './publish.routing';
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(publishRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'publish';
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
  $rootScope.config.title = '需求大厅';
  $rootScope.config.mainNav =[
    {
      "title":"需求查询",
      "url":"publish.list"
    }
  ];

});

export default MODULE_NAME;
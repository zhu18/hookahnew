import '../../common/common';
import facilitator from './facilitator.routing';
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(facilitator);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'facilitator';
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
  $rootScope.config.title = '服务商管理';
  $rootScope.config.mainNav =[
    {
      "title":"服务商查询",
      "url":"facilitator.list"
    }
  ];

});

export default MODULE_NAME;
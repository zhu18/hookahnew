import '../../common/common';
import settleRouting from './settle.routing';
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(settleRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'settle';
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
  $rootScope.config.title = '结算管理';
  $rootScope.config.mainNav =[
    {
      "title":"结算管理",
      "url":"settle.list"
    },{
      "title":"交易管理",
      "url":"settle.transactionManage"
    }
  ];

});

export default MODULE_NAME;
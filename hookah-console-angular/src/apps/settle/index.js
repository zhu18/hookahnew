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
    },
    // {
    //   "title":"新增帮助",
    //   "url":"help.add"
    // },
    /*{
      "title":"分类查询",
      "url":"help.category.search"
    },
    {
      "title":"新增一级分类",
      "url":"help.category.add"
    }
*/
  ];

});

export default MODULE_NAME;
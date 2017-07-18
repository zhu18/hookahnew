import '../../common/common';
import moneyRouting from './money.routing';
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(moneyRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'money';
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
  $rootScope.config.title = '资金管理';
  $rootScope.config.mainNav =[
    {
      "title":"提现审核",
      "url":"money.list"
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
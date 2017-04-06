import '../../common/common';
import goodsRouting from './goods.routing';
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'ui.router',
  'Common'
]);
app.config(goodsRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'goods';
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
  $rootScope.config.title = '商品管理';
  $rootScope.config.mainNav =[
    {
      "title":"商品查询",
      "url":"items.search"
    },
    {
      "title":"商品分类",
      "url":"items.category"
    }
  ];

});

export default MODULE_NAME;
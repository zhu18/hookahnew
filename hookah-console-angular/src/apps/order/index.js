import "../../common/common";
import orderRouting from "./order.routing";

const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'ui.router',
  'Common',
  'ui.bootstrap'
]);
app.config(orderRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'order';
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
  $rootScope.config.title = '订单管理';
  $rootScope.config.mainNav = [
    {
      "title": "订单查询",
      "url": "order.search"
    },
  ];

});

export default MODULE_NAME;
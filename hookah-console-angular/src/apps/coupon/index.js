import '../../common/common';
import couponController from './coupon.routing';
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(couponController);

app.run(function ($rootScope) {
  $rootScope.currentProductId = 'coupon';
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
  $rootScope.config.title = '优惠券管理';
  $rootScope.config.mainNav =[
    {
      "title":"优惠券列表",
      "url":"coupon.list"
    },

    {
      "title":"优惠券查询",
      "url":"coupon.query"
    }

  ];

});

export default MODULE_NAME;
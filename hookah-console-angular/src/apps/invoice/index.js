import '../../common/common';
import invoiceRouting from './invoice.routing';
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap'
]);
app.config(invoiceRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'invoice';
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
  $rootScope.config.title = '发票管理';
  $rootScope.config.mainNav =[
    {
      "title":"发票查询",
      "url":"invoice.list"
    },
    {
      "title":"增票资质审核",
      "url":"invoice.auditing"
    }
  ];

});

export default MODULE_NAME;
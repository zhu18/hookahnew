import "../../common/common";
import shelfRouting from "./shelf.routing";

const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'ui.router',
  'Common',
  'ui.bootstrap',
  'ngFileUpload'
]);
app.config(shelfRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'shelfold';
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
  $rootScope.config.title = '货架管理';
  $rootScope.config.mainNav = [
    {
      "title": "货架查询",
      "url": "shelfold.search"
    },
    {
      "title": "新增货架",
      "url": "shelfold.add"
    }
  ];

});

export default MODULE_NAME;
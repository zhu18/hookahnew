import "../../common/common";
import sysnoticRouting from "./sysnotic.routing";
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'ui.router',
  'Common',
  'ui.bootstrap'
]);
app.config(sysnoticRouting);
app.config(['$httpProvider', function ($httpProvider) {
  $httpProvider.defaults.withCredentials = true;
}]);
app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'sysnotice';
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
  $rootScope.config.title = '新闻公告管理';
  $rootScope.config.mainNav = [
    {
      "title": "公告查询",
      "url": "sysnotice.search"
    },
    {
      "title": "发布公告",
      "url": "sysnotice.add"
    }
  ];

});

export default MODULE_NAME;
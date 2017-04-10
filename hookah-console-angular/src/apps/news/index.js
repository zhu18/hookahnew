import '../../common/common';
import newsRouting from './news.routing';
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'ui.router',
  'Common',
  'ui.bootstrap'
]);
app.config(newsRouting);
app.config(['$httpProvider', function ($httpProvider) {
  $httpProvider.defaults.withCredentials = true;
}]);
app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'news';
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
  $rootScope.config.title = '资讯管理';
  $rootScope.config.mainNav =[
    {
      "title":"资讯查询",
      "url":"information.search"
    }
  ];

});

export default MODULE_NAME;
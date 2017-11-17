import '../../common/common';
import pointsRouting from './points.routing';
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(pointsRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'points';
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
  $rootScope.config.title = '积分管理';
  $rootScope.config.mainNav =[
    {
      "title":"积分查询",
      "url":"points.pointsList"
    },{
      "title":"积分设置",
      "url":"points.pointsManage"
    }
  ];

});

export default MODULE_NAME;
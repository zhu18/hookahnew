import '../../common/common';
import categoryRouting from './category.routing';
import "treeGridCss";
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(categoryRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'category';
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
  $rootScope.config.title = '分类管理';
  $rootScope.config.mainNav =[
    {
      "title":"分类查询",
      "url":"category.search"
    },
    {
      "title":"添加分类",
      "url":"category.add"
    }
  ];

});

export default MODULE_NAME;
import "../../common/common";
import sysRouting from "./sys.routing";
import "treeGridCss";
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(sysRouting);

app.run(function ($rootScope, $state) {
  $rootScope.tree_data = [];
  $rootScope.currentProductId = 'sys';
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
  $rootScope.config.title = '系统设置';
  $rootScope.config.mainNav = [
    // {
    //   "title": "商品查询",
    //   "url": "items.search"
    // },
    {
      "title": "字典管理",
      "url": "sys.dict.search"
    },
    {
      "title": "地域管理",
      "url": "sys.region.search"
    },
    {
      "title": "行业管理",
      "url": "sys.industry.search"
    }

  ];

});

export default MODULE_NAME;
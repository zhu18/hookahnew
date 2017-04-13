import "../../common/common";
import sysRouting from "./sys.routing";
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(sysRouting);

app.run(function ($rootScope, $state) {
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
    {
      "title": "商品查询",
      "url": "items.search"
    },
    {
      "title": "字典管理",
      "showChild": true,
      "childs": [
        {
          "title": "字典查询",
          "url": "sys.dict.search"
        }
        , {
          "title": "新增",
          "url": "sys.dict.add"
        }
      ]
    }

  ];

});

export default MODULE_NAME;
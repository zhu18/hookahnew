import "../../common/common";
import goodsAttrRouting from "./goodsAttr.routing";
import "treeGridCss";
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(goodsAttrRouting);

app.run(function ($rootScope, $state) {
  $rootScope.tree_data = [];
  $rootScope.currentProductId = 'goodsAttr';
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
  $rootScope.config.title = '属性管理';
  $rootScope.config.mainNav = [
    {
      "title": "属性管理",
      "url": "goodsAttr.search"
    },
    {
      "title":"添加属性",
      "url":"add.goodsAttr"
    }
  ];

});

export default MODULE_NAME;
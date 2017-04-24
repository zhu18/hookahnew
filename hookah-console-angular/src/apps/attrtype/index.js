import '../../common/common';
import attrtypeRouting from './attrtype.routing';
import "treeGridCss";
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(attrtypeRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'attrtype';
  $rootScope.tree_data = [];
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
  $rootScope.config.mainNav =[
    {
      "title":"属性查询",
      "url":"attrtype.search"
    },
    {
      "title":"添加属性",
      "url":"attrtype.add"
    }
  ];

});

export default MODULE_NAME;
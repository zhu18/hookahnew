import '../../common/common';
import commentRouting from './comment.routing';
import "treeGridCss";
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(commentRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'help';
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
  $rootScope.config.title = '评价管理';
  $rootScope.config.mainNav =[
    {
      "title":"评价查询",
      "url":"comment.review"
    },
    // {
    //   "title":"新增帮助",
    //   "url":"help.add"
    // },
    /*{
      "title":"分类查询",
      "url":"help.category.search"
    },
    {
      "title":"新增一级分类",
      "url":"help.category.add"
    }
*/
  ];

});

export default MODULE_NAME;
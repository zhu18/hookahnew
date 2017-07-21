import '../../common/common';
import operationalRouting from './operational.routing';
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(operationalRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'operational';
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
  $rootScope.config.title = '数据分析';
  $rootScope.config.mainNav =[
    {
      "title":"交易数据统计分析",
      "url":"operational.transaction"
    },{
      "title":"网站流量统计分析",
      "url":"operational.website"
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
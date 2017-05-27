import '../../common/common';
import esRouting from './es.routing';
import "treeGridCss";
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(esRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'es';
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
  $rootScope.config.title = 'ES管理';
  $rootScope.config.mainNav =[

  {
      "title": "ES管理",
      "showChild": true,
      "childs": [
          {
              "title":"索引删除",
              "url":"elastic.search"
          },
         {
              "title":"索引重建",
              "url":"elastic.add"
          },
          {
              "title":"商品删除",
              "url":"elastic.delGoods"
          },

      ]
  }
  ];

});

export default MODULE_NAME;
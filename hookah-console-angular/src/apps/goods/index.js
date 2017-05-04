import '../../common/common';
import goodsRouting from './goods.routing';
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap'
]);
app.config(goodsRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'goods';
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
  $rootScope.config.title = '商品管理';
  $rootScope.config.mainNav =[

  {
      "title": "商品管理",
      "showChild": true,
      "childs": [
          {
              "title":"商品查询",
              "url":"items.search"
          },
          {
              "title":"商品审核",
              "url":"items.check"
          },
          {
              "title":"审核结果",
              "url":"items.checkedList"
          }
      ]
  },
    {
        "title": "货架管理",
        "showChild": false,
        "childs": [
            {
                "title": "货架查询",
                "url": "shelf.search"
            },
            {
                "title": "新增货架",
                "url": "shelf.add"
            }
        ]
    }
  ];

});

export default MODULE_NAME;
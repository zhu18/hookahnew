import '../../common/common';
import goodsRouting from './goods.routing';
import "treeGridCss";
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
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
              "title":"待审核资源",
              "url":"items.check"
          },
          {
              "title":"已审核资源",
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
    ,
      {
          "title": "分类管理",
          "showChild": false,
          "childs": [
              {
                  "title":"分类查询",
                  "url":"category.search"
              },
              {
                  "title":"添加分类",
                  "url":"category.add"
              }
          ]
      }
      ,
      {
          "title": "属性管理",
          "showChild": false,
          "childs": [
              {
                  "title":"属性查询",
                  "url":"attrtype.search"
              },
              {
                  "title":"添加属性",
                  "url":"attrtype.add"
              }
          ]
      }
  ];

});

export default MODULE_NAME;
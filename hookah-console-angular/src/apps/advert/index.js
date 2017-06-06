import "../../common/common";
import advertRouting from "./advert.routing";
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'ui.router',
  'Common',
  'ui.bootstrap'
]);
app.config(advertRouting);
app.config(['$httpProvider', function ($httpProvider) {
  $httpProvider.defaults.withCredentials = true;
}]);
app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'advert';
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
  $rootScope.config.title = '广告管理';
  $rootScope.config.mainNav = [
    // {
    //   "title": "广告查询",
    //   "url": "advert.search"
    // },
    // {
    //   "title": "新增广告",
    //   "url": "advert.add"
    // }
    {
      "title": "轮播管理",
      "showChild": true,
      "childs": [
        {
          "title": "轮播分组查询",
          "url": "advert.carousel.search"
        }
        , {
          "title": "新建轮播分组",
          "url": "advert.carousel.add"
        }
      ]
    }
    , {
      "title": "合作机构管理",
      "showChild": false,
      "childs": [
          {
              "title": "合作机构查询",
              "url": "cooperation.search"
          }
          , {
              "title": "新增合作机构",
              "url": "cooperation.add"
          }
      ]
    }
    ,{
          "title": "公告管理",
          "showChild": false,
          "childs": [
              {
                  "title": "公告查询",
                  "url": "sysnotice.search"
              }
              , {
                  "title": "发布公告",
                  "url": "sysnotice.add"
              }
          ]
      }
  ];

});

export default MODULE_NAME;
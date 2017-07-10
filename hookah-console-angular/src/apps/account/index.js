import "../../common/common";
import accountRouting from "./account.routing";
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'ui.router',
  'Common',
  'ui.bootstrap'
]);
app.config(accountRouting);
app.config(['$httpProvider', function ($httpProvider) {
  $httpProvider.defaults.withCredentials = true;
}]);
app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'account';
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
  $rootScope.config.title = '账号管理';
  $rootScope.config.mainNav = [
    {
      "title": "账号查询",
      "url": "account.search"
    },
    // {
    //   "title": "新增账号",
    //   "url": "account.add"
    // },
    {
      "title": "角色管理",
      "showChild": true,
      "childs": [
        {
          "title": "角色查询",
          "url": "account.role.search"
        },
        // {
        //   "title": "新增角色",
        //   "url": "account.role.add"
        // },
        // {
        //   "title": "角色设置",
        //   "url": "account.role.setting"
        // },
      ]
    }
    , {
      "title": "权限管理",
      "showChild": true,
      "childs": [
        {
          "title": "权限查询",
          "url": "account.permission.search"
        },
        {
          "title": "新增权限",
          "url": "account.permission.add"
        },
        // {
        //   "title": "权限设置",
        //   "url": "account.permission.setting"
        // }
      ]
    }
  ];
});

export default MODULE_NAME;
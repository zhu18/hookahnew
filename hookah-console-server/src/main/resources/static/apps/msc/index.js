import '../../common/common';
import mscRouting from './msc.routing';


const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'ui.router',
  'Common'
]);
app.config(mscRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'msc';
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
  $rootScope.config.title = '消息中心';
  $rootScope.config.mainNav =[
    {
      "title":"站内消息",
      "showChild":true,
      "childs":[
        {
          "title":"全部消息",
          "url":"innerMsg.all({categoryId:0})"
        },
        {
          "title":"未读消息",
          "url":"innerMsg.unread({categoryId:0})"
        },
        {
          "title":"已读消息",
          "url":"innerMsg.read({categoryId:0})"
        }
      ]
    }
  ];

  console.log("app run");
});

export default MODULE_NAME;
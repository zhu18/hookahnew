import '../../common/common';
import categoryRouting from './message.routing';
import "treeGridCss";
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'Common',
  'ui.router',
  'ui.bootstrap',
  'treeGrid'
]);
app.config(categoryRouting);

app.run(function ($rootScope, $state) {
  $rootScope.currentProductId = 'message';
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
  $rootScope.config.title = '消息平台管理';
  $rootScope.config.mainNav =[
    {
      "title":"站内信记录",
      "url":"message.system.search"
    },
    {
      "title":"短信记录",
      "url":"message.sms.search"
    },
    {
      "title":"邮件记录",
      "url":"message.email.search"
    },
    {
      "title":"消息模板管理",
      "showChild": true,
      "childs": [
          {
            "title":"邮件记录",
            "url":"message.template.search"
          },
          {
            "title":"添加消息模板",
            "url":"message.template.add"
          }
      ]
    }
  ];

});

export default MODULE_NAME;
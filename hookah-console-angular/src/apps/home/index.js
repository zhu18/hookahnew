import '../../common/common';
import homeRouting from './home.routing';
import "../../style/home/home.console.css";
import "../../style/common.css";
const MODULE_NAME = 'Hookah';
let app = angular.module(MODULE_NAME, [
  'ui.router',
  'Common'
]);
/**
 * 配置路由
 */
app.config(homeRouting);

app.run(function ($rootScope, $state) {
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
  console.log("app run");
});

export default MODULE_NAME;

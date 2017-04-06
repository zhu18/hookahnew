var config ={
  user:"",
  site:{
    apiServer:"http://console.hookah.app"
  },
  url:{
    loginUrl:"http://auth.hookah.app/oauth/authorize?client_id=admin&response_type=code&redirect_uri="
  }
};
angular.element(document).ready(function () {
  $.ajaxSetup({
    xhrFields: {
      withCredentials: true
    }
  });
  // 手动加载模块
  // angular.element(document).ready(function () {
  //   angular.bootstrap(document, ['Hookah']);
  // });
  $.ajax({
    type: "GET",
    url: config.site.apiServer+"/api/auth/current_user",
    success: function (data) {
      config.user = data.data;
      angular.element(document).ready(function () {
        angular.bootstrap(document, ['Hookah']);
      });
    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
      console.log(XMLHttpRequest);
      var currUrl = window.location;
      console.log(currUrl);
      if (401 === XMLHttpRequest.status) {
        window.location.href = "http://auth.hookah.app/oauth/authorize?client_id=admin&response_type=code&redirect_uri=" + currUrl;
      }
    }
  });
});
import TopBarDirective from "./directive/TopBarDirective";
import SideBarDirective from "./directive/SideBarDirective";
import ProductNavbarDirective from "./directive/ProductNavbarDirective";
import "bootstrapCss";
import "angular-growl-v2-css";
import "../style/console1412.css";
import MainController from "./controller/MainController";


export default angular.module('Common', [
  'ui.bootstrap',
  'angular-growl',
  'angularSpinner',
])
  .constant('apiServer', {
    "admin": "http://console.hookah.app",
  })
  .constant('loginUrl', "http://auth.ziroot.app/oauth/authorize?client_id=test&response_type=code&redirect_uri=")
  .config(['growlProvider', function (growlProvider) {
    /**
     * 配置消息提示
     */
    growlProvider.globalTimeToLive(3000);
  }])
  .config(['usSpinnerConfigProvider', function (usSpinnerConfigProvider) {
    /**
     * 配置loading菊花
     */
    let opts = {
      lines: 13 // The number of lines to draw
      , length: 16 // The length of each line
      , width: 4 // The line thickness
      , radius: 15 // The radius of the inner circle
      , scale: 1 // Scales overall size of the spinner
      , corners: 1 // Corner roundness (0..1)
      , color: '#000' // #rgb or #rrggbb or array of colors
      , opacity: 0.25 // Opacity of the lines
      , rotate: 0 // The rotation offset
      , direction: 1 // 1: clockwise, -1: counterclockwise
      , speed: 1 // Rounds per second
      , trail: 60 // Afterglow percentage
      , fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
      , zIndex: 2e9 // The z-index (defaults to 2000000000)
      , className: 'spinner' // The CSS class to assign to the spinner
      , top: '50%' // Top position relative to parent
      , left: '50%' // Left position relative to parent
      , shadow: true // Whether to render a shadow
      , hwaccel: false // Whether to use hardware acceleration
      , position: 'relative' // Element positioning
    };
    usSpinnerConfigProvider.setDefaults(opts);
  }])
  .directive('topBar', TopBarDirective.directiveFactory)
  .directive('sideBar', SideBarDirective.directiveFactory)
  .directive('productNavbar', ProductNavbarDirective.directiveFactory)
  // 定义一个 Service ，稍等将会把它作为 Interceptors 的处理函数
  .factory('HttpInterceptor', ['$q', '$rootScope', '$location', '$window', HttpInterceptor])
  // 添加对应的 Interceptors
  .config(['$httpProvider', function ($httpProvider) {
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push(HttpInterceptor);
  }])
  .filter('yesNo', function () {
    return function (input) {
      return input ? '是' : '否';
    }
  })
  .filter('Status', function () {
    return function (input) {
      return input == 1 ? '启用' : '禁用';
    }
  })
  .controller("MainController", MainController)
  .run(function ($rootScope) {
    console.log("common init..");
    $rootScope.user = config.user;
    $rootScope.site = config.site;
  });
function HttpInterceptor($q, $rootScope, $location, $window) {
  return {
    request: function (config) {
      $rootScope.startSpin();
      config.requestTimestamp = new Date().getTime();
      return config;
    },
    requestError: function (err) {
      return $q.reject(err);
    },
    response: function (res) {
      /**
       * 记录每次请求花费的时间
       */
      res.config.responseTimestamp = new Date().getTime();
      var time = res.config.responseTimestamp - res.config.requestTimestamp;
      console.log('The request took ' + (time / 1000) + ' seconds.');
      if (res.data.totalItems) {
        $rootScope.pagination.store = res.data.list;
        $rootScope.pagination.currentPage = res.data.currentPage;
        $rootScope.pagination.totalItems = res.data.totalItems;
        $rootScope.paginationSupport = true;
        // if(res.data.totalPages >1){
        //
        // }
        if (res.data.totalItems == 0) {
          $rootScope.showNoneDataInfoTip = true;
          $rootScope.showPageHelpInfo = false;
        } else {
          $rootScope.showNoneDataInfoTip = false;
          $rootScope.showPageHelpInfo = true;
        }
        $rootScope.stopSpin();
      }

      $rootScope.stopSpin();
      return res;
    },
    responseError: function (err) {
      if (-1 === err.status) {
        //远程服务器无响应
        $rootScope.openErrorDialogModal("远程服务器无响应");
      } else if (500 === err.status) {
        // 处理各类自定义错误
        $rootScope.openErrorDialogModal("服务器内部错误");
      } else if (501 === err.status) {
        // ...
      } else if (401 === err.status) {
        let currUrl = $location.absUrl();
        $window.location.href = $rootScope.loginUrl + currUrl;
        // $location.absUrl("http://auth.ziroot.app/oauth/authorize?client_id=test&amp;response_type=code&amp;redirect_uri="+currUrl);
        // $rootScope.openErrorDialogModal("未登录请,<a href=''>重新登录</a>");
      } else if (403 === err.status) {
        $rootScope.openErrorDialogModal("无权限执行此操作");
      }
      else if (408 === err.status) {
        $rootScope.openErrorDialogModal("请求超时");
      } else if (419 === err.status) {
        $rootScope.openErrorDialogModal("session过期");
      } else {
        $rootScope.openErrorDialogModal("未知错误");
      }

      return $q.reject(err);
    }
  };
}


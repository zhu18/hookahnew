var config = {
  user: "",
  permissionList: "",
  permissionArray:"",
  site: {
    adminServer: "http://admin.qddata.com.cn",
    apiServer: "http://console.qddata.com.cn",
    websiteServer: "http://trade.qddata.com.cn",
    authServer: "http://auth.qddata.com.cn",
    staticServer: "http://static.qddata.com.cn"
  },
  url: {
    loginUrl: "http://auth.qddata.com.cn/oauth/authorize?client_id=admin&response_type=code&redirect_uri=http://console.qddata.com.cn/login&backurl=",
    uploadUrl: "http://static.qddata.com.cn/upload/fileUpload",
    uploadEditor: "http://static.qddata.com.cn/upload/wangeditor",
  }
};

var permissionFlag = false;
angular.element(document).ready(function () {
  $.ajaxSetup({
    xhrFields: {
      withCredentials: true
    }
  });
  // 手动加载模块
  $.ajax({
    type: "GET",
    url: config.site.apiServer + "/api/auth/current_user",
    success: function (data) {
      config.user = data.data;
      //加载用户权限列表
      $.ajax({
        type: "GET",
        url: config.site.apiServer + "/api/permission/current_user_permission",
        data: {userId: config.user.userId},
        success: function (data) {
          config.permissionList = data.data.toString();
          config.permissionArray = data.data;
          angular.element(document).ready(function () {
            angular.bootstrap(document, ['Hookah']);
          });
        }
      });

    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
      var currUrl = window.location;
      // console.log(currUrl);
      //TODO...
      // console.log(XMLHttpRequest);
      if (401 === XMLHttpRequest.status) {
        window.location.href = config.url.loginUrl + currUrl;
      }
    }
  });

    $.ajax({
        type: "GET",
        url: config.site.apiServer + "/api/auth/isAdmin",
        async: false,
        success: function (data) {
            if (data.code=="1"){
                permissionFlag=true;
            }else {
                permissionFlag = false;
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("系统错误！");
        }
    })
});
import TopBarDirective from "./directive/TopBarDirective";
import SideBarDirective from "./directive/SideBarDirective";
import ProductNavbarDirective from "./directive/ProductNavbarDirective";
import "bootstrapCss";
import "../../vendor/angular-growl/build/angular-growl.min.css";
import "../style/console1412.css";
import MainController from "./controller/MainController";


export default angular.module('Common', [
  'ui.bootstrap.modal',
  'ui.bootstrap.tooltip',
  'angular-growl',
  'angularSpinner',
])
  .constant('loginUrl', "http://auth.qddata.com.cn/oauth/authorize?client_id=test&response_type=code&redirect_uri=")
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
  .directive('convertToNumber', function () {
    return {
      require: 'ngModel',
      link: function (scope, element, attrs, ngModel) {
        ngModel.$parsers.push(function (val) {
          return val ? parseInt(val, 10) : null;
        });
        ngModel.$formatters.push(function (val) {
          return val ? '' + val : null;
        });
      }
    };
  })

  //权限控制
  .factory('permissions', function ($rootScope) {
    return {
      hasPermission: function (permission) {
        if (permission) {
          if (typeof(permission) == "string") {
            // 判断是否有权限，或者是超级管理员（userId=1）
            if ((config.permissionList.indexOf(permission) > -1) || permissionFlag) {
              return true;
            }
          }
        }
        return false;
      }
    };
  })
  // 定义一个 Service ，稍等将会把它作为 Interceptors 的处理函数
  .factory('HttpInterceptor', ['$q', '$rootScope', '$location', '$window', HttpInterceptor])
  // 添加对应的 Interceptors
  .config(['$httpProvider', function ($httpProvider) {
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=utf-8";
    $httpProvider.interceptors.push(HttpInterceptor);
  }])
  .filter('yesNo', function () {
    return function (input) {
      return input ? '是' : '否';
    }
  })
  .filter('isDeleted', function () {
    return function (input) {
      return input ? '是' : '否';
    }
  })
  .filter('shopStatus', function () {
    return function (input) {
      switch (input) {
        case 1:
          return '正常营业';
          break;
        case 2:
          return '歇业';
          break;
        default:
          return '盘点';
      }
    }
  })
  .filter('newsGroup', function () {
    return function (input) {
      switch (input) {
        case 'innovation':
          return '双创中心';
          break;
        case 'exposition':
          return '博览中心';
          break;
        default:
          return '咨询中心';
      }
    }
  })
  .filter('Status', function () {
    return function (input) {
      return input == 1 ? '启用' : '禁用';
    }
  })
  .filter('isMust', function () {
    return function (input) {
      return input == 0 ? '否' : '是';
    }
  })
  .filter('UserType', function () {
    return function (input) {
      switch (input) {
        case 0:
          return '系统';
          break;
        case 1:
          return '未认证';
          break;
        case 2:
          return '个人';
          break;
        case 3:
          return '个人待审核';
          break;
        case 4:
          return '企业';
          break;
        case 5:
          return '企业待审核';
          break;
        case 6:
          return '个人审核未通过';
          break;
        case 7:
          return '企业审核未通过';
          break;

      }
    }
  })
  .filter('goodsType', function () {
    return function (input) {
      switch (input) {
        case 0:
          return '离线数据';
          break;
        case 1:
          return 'API';
          break;
        case 2:
          return '数据模型';
          break;
        case 4:
          return '分析工具--独立软件';
          break;
        case 5:
          return '分析工具--SaaS';
          break;
        case 6:
          return '应用场景--独立软件';
          break;
        case 7:
          return '应用场景--SaaS';
          break;
      }
    }
  })
  .filter('isAttr', function () {
    return function (input) {
      switch (input) {
        case 0:
          return '叶子节点';
          break;
        case 1:
          return '非叶子节点';
          break;
      }
    }
  })
  .filter('checkStatus', function () {
    return function (input) {
      switch (input) {
        case 0:
          return '审核中';
          break;
        case 1:
          return '已通过';
          break;
        case 2:
          return '未通过';
          break;
      }
    }
  })
  .filter('shelvesStatus', function () {
    return function (input) {
      switch (input) {
        case 0:
          return '停用';
          break;
        case 1:
          return '启用';
          break;
        case 2:
          return '审核中';
          break;
      }
    }
  })
  .filter('goodsFormat', function () {
    return function (input) {
      switch (input) {
        case 0:
          return '次';
          break;
        case 1:
          return '月';
          break;
        case 2:
          return '年';
          break;
        case 3:
          return '套';
          break;
      }
    }
  })
  .filter('isOnsale', function () {
    return function (input) {
      switch (input) {
        case 0:
          return '已下架';
          break;
        case 1:
          return '已上架';
          break;
        case 2:
          return '强制下架';
          break;
      }
    }
  })
  .filter('isRead', function () {
      return function (input) {
          switch (input) {
              case 0:
                  return '未读';
                  break;
              case 1:
                  return '已读';
                  break;
          }
      }
  })
  .filter('templateType', function () {
      return function (input) {
          switch (input) {
              case 0:
                  return '短信';
                  break;
              case 1:
                  return '邮件';
                  break;
              case 2:
                  return "站内信";
                  break
          }
      }
  })
  .filter('isSuccess', function () {
      return function (input) {
          return input == 0 ? '失败' : '成功';
      }
  })
  .filter('trustHtml', function ($sce) {
    return function (input) {
      return $sce.trustAsHtml(input);
    }
  })
  .controller("MainController", MainController)
  .run(function ($rootScope, $window, $location, $state, permissions) {
    // console.log("common init..");
    $rootScope.user = config.user;
    $rootScope.url = config.url;
    $rootScope.site = config.site;
    $rootScope.permissionArray = config.permissionArray;
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
      var permission = toState.permission;
      if (!permissions.hasPermission(permission)) {
        event.preventDefault();
        var dialogModal = $rootScope.openErrorDialogModal("您没有权限使用该功能，需要开通请联系管理员！！！");
      }
    });
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
      // console.log(res);
      if (res.data.data != null) {
        if (res.data.data !== undefined) {
          $rootScope.pagination.store = res.data.data.list;
          $rootScope.pagination.currentPage = res.data.data.currentPage;
          $rootScope.pagination.totalItems = res.data.data.totalItems;
          $rootScope.paginationSupport = true;
          // if(res.data.totalPages >1){
          //
          // }
          if (res.data.data.totalItems == 0) {
            $rootScope.showNoneDataInfoTip = true;
            $rootScope.showPageHelpInfo = false;
          } else {
            $rootScope.showNoneDataInfoTip = false;
            $rootScope.showPageHelpInfo = true;
          }
          $rootScope.stopSpin();
        }
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


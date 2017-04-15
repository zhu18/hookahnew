class HelpController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
    if ($state.current.name == "help.add") {
      var editor = new wangEditor('content');
      //上传图片（举例）
      editor.config.uploadImgUrl = $rootScope.url.uploadEditor;
      editor.config.uploadImgFileName = 'filename';
      //关闭菜单栏fixed
      editor.config.menuFixed = false;
      editor.config.menus = $.map(wangEditor.config.menus, function (item, key) {
        if (item === 'location') {
          return null;
        }
        if (item === 'video') {
          return null;
        }
        return item;
      });
      editor.create();
    }
    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/help/all"
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        $scope.tree_data = res.data;
        console.log(res);
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.add = function () {
      console.log("add。。。。");
    };
    $scope.edit = function (event, item) {
      console.log(item);
    };
    $scope.save = function () {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/help/add",
        data: $("#helpForm").serialize()
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("保存成功。。。");
      });
    };
    $scope.delete = function (event, item) {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/help/delete/"+item.helpId
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        if(res.data.code ==1){
          $scope.search();
          growl.addSuccessMessage("删除成功。。。");
        }

      });
    };
    if ($state.current.name == "help.search") {
      $scope.search();
      $scope.expanding_property = "name";
      $scope.col_defs = [
        {
          field: "helpId",
          displayName: "属性"
        }
        , {
          field: "hrefUrl",
          displayName: "连接"
        }
        , {
          field: "aa",
          displayName: "操作",
          cellTemplate: '<a ng-click="cellTemplateScope.edit(row.branch)">修改</a> <span class="text-explode">|</span> <i class="link-space"></i> <a href="javascript:;" ng-click="cellTemplateScope.delete(row.branch)" target="_blank">删除</a> <span class="text-explode">|</span> <i class="link-space"></i> <a href="javascript:;" ng-click="cellTemplateScope.addChild(row.branch)" target="_blank">增加子项</a>',
          cellTemplateScope: {
            edit: function (data) {
              $scope.edit(data);
            },
            delete: function (data) {
              $scope.delete(data);
            },
            addChild: function (data) {
              $scope.addChild(data);
            }
          }
        }
      ];
      $scope.dict_tree = {};
      $scope.tree_data = {};
    }

  }
}

export default HelpController;
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
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.addChild = function (data) {
      $rootScope.helpParent = data;
      $state.go("help.add");
    };
    $scope.edit = function (item) {
      $rootScope.editData = item;
      if (item.children.length > 0) {
        $state.go("help.category.edit");
      } else {

        $state.go("help.edit");
        $("#content").html(item.content);

      }
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
        $state.go('help.search');
      });
    };
    $scope.refresh = function () {
      $scope.search();
    };
    $scope.delete = function (item) {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/help/delete/" + item.helpId
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        if (res.data.code == 1) {
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
          field: "helpUrl",
          displayName: "连接",
          cellTemplate: "<a target='_blank' href='" + $rootScope.site.websiteServer + "{{ row.branch[col.field] }}'>{{ row.branch[col.field] }}</a>",
        },
        {
          field: "creatorName",
          displayName: "创建者"
        },
        {
          field: "addTime",
          displayName: "创建时间"
        }

        , {
          field: "aa",
          displayName: "操作",
          cellTemplate: ' <a href="javascript:;" ng-click="cellTemplateScope.delete(row.branch)" target="_blank">删除</a> <span class="text-explode">|</span> <i class="link-space"></i> <a href="javascript:;" ng-click="cellTemplateScope.addChild(row.branch)" target="_blank">增加子项</a>',
          cellTemplateScope: {
            edit: function (data) {
              $scope.edit(data);
            },
            delete: function (data) {
              if (data.children.length == 0) {
                var modalInstance = $rootScope.openConfirmDialogModal("确定要删除" + '<span style="font-weight: bold;color: #6b3100">' + data.name + '</span>' + "吗？");
                modalInstance.result.then(function () {
                  $scope.delete(data);
                }, function () {
                });
              } else {
                $rootScope.openErrorDialogModal('<span style="font-weight: bold;color: #6b3100">' + data.name + '</span>' + "下有子项，请先删除所有子项");
              }

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
class PermissionController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {

    $scope.expanding_property = {
      field: "permissionExplain",
      displayName: "权限名称"
    };

    $scope.col_defs = [
      // {
      //   field: "permissionId",
      //   displayName: "权限ID"
      // },
      {
        field: "permissionName",
        displayName: "权限代码"
      }
      // {
      //   field: "property",
      //   displayName: "属性"
      // }
      // , {
      //   field: "seq",
      //   displayName: "顺序"
      // }
      , {
        field: "aa",
        displayName: "操作",
        cellTemplate: '<a href="javascript:;" ng-click="cellTemplateScope.delete(row.branch)" target="_blank">删除</a>',
        cellTemplateScope: {
          delete: function (data) {
            $scope.delete(data);
          }
        }
      }
    ];
    $scope.permission_tree = {};

    $scope.treehandler = function (branch) {
      console.log(branch);
    };
    $scope.add = function(){
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/permission/group",
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        $rootScope.group = res.data;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/permission/tree",
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        $scope.tree_data = res.data;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.save = function () {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/permission/save",
        data: $("#permissionForm").serialize()
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        if (res.data.code == "1") {
          $state.go('account.permission.search');
        } else {
          alert(res.data.message);
        }
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.delete = function (item) {
      var confirm = $rootScope.openConfirmDialogModal("确认要删除此&nbsp;<b>" + item.permissionName + "</b>&nbsp;权限吗？");
      confirm.result.then(function () {
        var promise = $http({
          method: 'POST',
          url: $rootScope.site.apiServer + "/api/permission/delete",
          params: {
            permissionId: item.permissionId
          }
        });
        promise.then(function (res, status, config, headers) {
          $rootScope.loadingState = false;
          // alert(res.data.message);
          $scope.search();
          growl.addSuccessMessage("删除"+item.permissionName+"成功。。。");
        });
      });

    };
    $scope.pageChanged = function () {
      $scope.search();
    };
    $scope.search();
  }
}
export default PermissionController;
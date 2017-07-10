class PermissionController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {

    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/permission/permission_all",
        params: {
          currentPage: $rootScope.pagination.currentPage,
          pageSize: $rootScope.pagination.pageSize,
          userName: $scope.userName
        }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        $scope.sysAccount = res.data.data;
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
    $scope.delete = function (event, item) {
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
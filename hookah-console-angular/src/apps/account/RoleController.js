class RoleController {
  constructor($scope, $rootScope, permissions, $http, $state, $uibModal, usSpinnerService, growl) {
    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/role/role_all",
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
    $scope.add = function () {
      $rootScope.item={};
      $rootScope.selectRolePermissions = "";

    };
    $scope.load = function (event, item) {
      $rootScope.item = item;
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/role/getPermissionsByRoleId",
        params: {
          roleId: item.roleId
        }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        $rootScope.selectRolePermissions = res.data.data;
        growl.addSuccessMessage("数据加载完毕。。。");
      });

    };
    $scope.selectAllFlag = false;
    $scope.selectAll = function () {
      var permis = document.getElementsByName("permissions");
      if ($scope.selectAllFlag) {
        for (var i = 0; i < permis.length; i++) {
          permis[i].checked = false;
        }
        $scope.selectAllFlag = false;
      } else {
        for (var i = 0; i < permis.length; i++) {
          permis[i].checked = true;
        }
        $scope.selectAllFlag = true;
      }

    };
    $scope.isSelected = function (id) {
      // console.log($rootScope.item);
      if ($rootScope.selectRolePermissions) {
        var o = $rootScope.selectRolePermissions.toString();

        if (o.indexOf(id) > -1) {
          return true;
        }
        else {
          return false;
        }
      } else {
        return false;
      }

    };
    $scope.save = function () {

      var spCodesTemp = "";
      $('input:checkbox[name=permissions]:checked').each(function (i) {
        if (0 == i) {
          spCodesTemp = $(this).val();
        } else {
          spCodesTemp += ("," + $(this).val());
        }
      });
      var data = "";
      if (angular.isUndefined($rootScope.item.roleId)) {
        data = "roleName=" + $rootScope.item.roleName + "&roleExplain=" + $rootScope.item.roleExplain + "&permissions=" + spCodesTemp;
      } else {
        // data = "roleName=" + $rootScope.item.roleName + "&roleExplain=" + $rootScope.item.roleExplain + "&permissions=" + spCodesTemp;
        data = "roleId=" + $rootScope.item.roleId + "&roleName=" + $rootScope.item.roleName + "&roleExplain=" + $rootScope.item.roleExplain + "&permissions=" + spCodesTemp;
      }
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/role/save",
        data: data
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        if (res.data.code == "1") {
          $state.go('account.role.search');
        } else {
          alert(res.data.message);
        }
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.delete = function (event, item) {
      var confirm = $rootScope.openConfirmDialogModal("确认要删除此&nbsp;<b>" + item.roleName + "</b>&nbsp;角色吗？");
      confirm.result.then(function () {
        var promise = $http({
          method: 'POST',
          url: $rootScope.site.apiServer + "/api/role/delete",
          params: {
            roleId: item.roleId
          }
        });
        promise.then(function (res, status, config, headers) {
          $rootScope.loadingState = false;
          // alert(res.data.message);
          $scope.search();
          growl.addSuccessMessage("删除" + item.roleName + "成功。。。");
        });
      });

    };
    $scope.pageChanged = function () {
      $scope.search();
    };
    $scope.search();
  }
}
export default RoleController;
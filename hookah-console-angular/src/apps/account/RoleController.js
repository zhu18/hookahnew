class RoleController {
  constructor($scope, $rootScope, permissions, $http, $state, $uibModal, usSpinnerService, growl) {

    $scope.select = function (data) {
    };
    $scope.a = {};
    $scope.isEnable = function (data) {
      if (data == 1) {
        return true
      } else {
        return false;
      }
    };
    $scope.isEnableChange = function () {
      $rootScope.item.isEnable = !$rootScope.item.isEnable;

    };
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
      $rootScope.title = "新增角色";
      $rootScope.item = {};
      $rootScope.selectRolePermissions = "";

      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/permission/tree",
        // params: {
        //   currentPage: $rootScope.pagination.currentPage,
        //   pageSize: $rootScope.pagination.pageSize,
        //   userName: $scope.userName
        // }
      });
      promise.then(function (res, status, config, headers) {
        $state.go('account.role.add');
        console.log("sksks");
        $rootScope.loadingState = false;
        $rootScope.tree_data = res.data;
        growl.addSuccessMessage("数据加载完毕。。。");
      });

    };
    $scope.load = function (event, item) {
      $rootScope.title = "修改角色";
      $rootScope.item = item;
      if (item.isEnable == 1) {
        $rootScope.item.isEnable = true;
      } else {
        $rootScope.item.isEnable = false;
      }
      var promise1 = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/permission/tree",
        // params: {
        //   currentPage: $rootScope.pagination.currentPage,
        //   pageSize: $rootScope.pagination.pageSize,
        //   userName: $scope.userName
        // }
      });
      promise1.then(function (res, status, config, headers) {
        $state.go('account.role.add');
        console.log("sksks");
        $rootScope.loadingState = false;
        $rootScope.tree_data = res.data;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
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
        console.log($rootScope.item.isEnable)
        if ($rootScope.item.roleName=="" || $rootScope.item.roleName==null){
            $rootScope.openErrorDialogModal("角色代码不能为空！");
            return;
        }
        if ($rootScope.item.roleExplain=="" || $rootScope.item.roleExplain==null){
            $rootScope.openErrorDialogModal("角色中文名不能为空！");
            return;
        }
        if ($rootScope.item.isEnable=="" || $rootScope.item.isEnable==null){
            $rootScope.item.isEnable=false;
        }

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
        data = "roleName=" + $rootScope.item.roleName + "&roleExplain=" + $rootScope.item.roleExplain + "&enable=" + $rootScope.item.isEnable + "&permissions=" + spCodesTemp;
      } else {
        // data = "roleName=" + $rootScope.item.roleName + "&roleExplain=" + $rootScope.item.roleExplain + "&permissions=" + spCodesTemp;
        data = "roleId=" + $rootScope.item.roleId + "&roleName=" + $rootScope.item.roleName + "&roleExplain=" + $rootScope.item.roleExplain + "&enable=" + $rootScope.item.isEnable + "&permissions=" + spCodesTemp;
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
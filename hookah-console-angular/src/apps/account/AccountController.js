class AccountController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
    $scope.search = function (initCurrentPage) {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/account/sys_all",
        params: {
          currentPage: $rootScope.pagination.currentPage,
          pageSize:initCurrentPage == 'true' ? 10 : $rootScope.pagination.pageSize,
          userName: $scope.userName
        }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        $scope.sysAccount = res.data.data;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.edit = function (event, item) {
      var checkedRoles=$("input[name='roles']:checked");
      if (checkedRoles.length<=0){
          $rootScope.openErrorDialogModal("请勾选至少一个角色！");
          return;
      };
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/account/upd",
        data: $("#editForm").serialize()
      });
      promise.then(function (res, status, config, headers) {
        if (res.data.code == "1") {
          alert(res.data.data);
          $state.go('account.search');
        } else {
          alert(res.data.message);
        }
      });
    };
    $scope.delete = function (event, item) {
      var confirm = $rootScope.openConfirmDialogModal("确认要删除此&nbsp;<b>" + item.userName + "</b>&nbsp;账户吗？");
      confirm.result.then(function () {
        var promise = $http({
          method: 'POST',
          url: $rootScope.site.apiServer + "/api/account/delete",
          params: {
            userId: item.userId,
            userName: item.userName
          }
        });
        promise.then(function (res, status, config, headers) {
          $rootScope.loadingState = false;
          // alert(res.data.message);
          $scope.search();
          growl.addSuccessMessage("数据加载完毕。。。");
        });
      });

    };
    $scope.load = function (event, item) {
      var promiseRoles = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/role/role_all",
        params: {
            pageSize: 1000
        }
      });
      promiseRoles.then(function (res, status, config, headers) {
        $rootScope.roles = res.data.data.list;
        growl.addSuccessMessage("数据加载完毕。。。");
      });

      var promiseUserRoles = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/role/user_role?userId=" + item.userId,
        data: ""
      });
      promiseUserRoles.then(function (res, status, config, headers) {
        $rootScope.user_role = res.data.data;
        growl.addSuccessMessage("数据加载完毕。。。");
      });

      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/account/" + item.userId,
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.cuserd = res.data.data;
      });
    };
    $scope.add = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/role/role_all",
        params: {
            pageSize: 1000
        }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.roles = res.data.data.list;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.save = function () {
      //var roles=document.getElementsByName("roles");
      var checkedRoles=$("input[name='roles']:checked");
      if (checkedRoles.length<=0){
          $rootScope.openErrorDialogModal("请勾选至少一个角色！");
          return;
      };
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/account/save",
        data: $("#userForm").serialize()
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        if (res.data.code == "1") {
          alert(res.data.data);
          $state.go('account.search');
        } else {
          alert(res.data.message);
        }
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.editPassword = function (event, item) {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/account/editPass",
        data: $("#editPasswordForm").serialize()
      });
      promise.then(function (res, status, config, headers) {
        if (res.data.code == "1") {
          alert(res.data.data);
          $state.go('account.search');
        } else {
          alert(res.data.message);
        }
      });
    };
    $scope.pageChanged = function () {
      $scope.search();
    };
    $scope.search('true');
    $scope.isEnable = function (id) {
      if (id == 1) {
        return true;
      } else {
        return false;
      }
    };
    $scope.hasRoles = function (role) {
      var roles = $rootScope.user_role.toString();
      if (roles.indexOf(role) > -1) {
        return true;
      } else {
        return false;
      }

    }
    $scope.refresh = function(){
      $scope.search();
    }
  }
}

export default AccountController;
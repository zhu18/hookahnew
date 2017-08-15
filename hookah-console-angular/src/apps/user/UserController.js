class UserController {
  constructor($scope, $rootScope, $state, $http, $uibModal, usSpinnerService, growl) {
    $scope.title = '用户查询';
    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/user/all",
        params: {
          currentPage: $rootScope.pagination.currentPage,
          pageSize: $rootScope.pagination.pageSize,
          userName: $scope.userName,
          mobile: $scope.mobile,
          email: $scope.email,
          userType: $scope.userType
        }
      });
      promise.then(function (res, status, config, headers) {
        console.log(res);
        $rootScope.loadingState = false;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    //  用户信息详情
    $scope.detail = function (id) {
      $state.go('user.detail', {id: id});
    };
    $scope.goRecharge = function ($event, item) {
      $state.go('user.recharge', {item: item});
    };
    $scope.save = function () {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/account/save",
        data: $("#userForm").serialize()
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.refresh = function () {
      $scope.search();
    };

    $scope.back = function () {
      history.back();
    };
    $scope.pageChanged = function () {
      $scope.search();
    };
    $scope.search();
    if ($state.$current.name == "user.search") {
      $scope.title = '用户查询';
      $scope.userTypes = [
        // {id: -1, name: "全部"}, {id: 1, name: "未认证"}, {id: 2, name: "个人"}, {id: 3, name: "个人待审核"},
        {id: -1, name: "全部"}, {id: 1, name: "未认证"},
        // {id: 4, name: "企业"}, {id: 5, name: "企业待审核"}, {id: 6, name: "个人审核失败"}, {id: 7, name: "企业审核失败"}];
        {id: 4, name: "企业"}, {id: 5, name: "企业待审核"},  {id: 7, name: "企业审核失败"}];
      $scope.userType = -1;
      $scope.search();
    }
  }
}

export default UserController;
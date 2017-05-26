class UserController {
  constructor($scope, $rootScope, $http, $uibModal, usSpinnerService, growl) {
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
            email: $scope.email
        }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.detail = function (event, item) {

      $rootScope.cuser = item;
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/user/"+item.userId
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        $rootScope.cuserd=res.data.data;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
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
    $scope.pageChanged = function () {
      $scope.search();
    };
    $scope.search();
  }
}

export default UserController;
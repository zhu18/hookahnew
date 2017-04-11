class UserVerifyController {
  constructor($scope, $rootScope, $http, $uibModal, usSpinnerService, growl) {
    $scope.title = '用户审核';
    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/user/verify/all"
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        // $scope.sysAccount = res.data.data;
        $rootScope.pagination.store = res.data.data.list;
        $rootScope.pagination.currentPage = res.data.data.currentPage;
        $rootScope.pagination.totalItems = res.data.data.totalItems;
        if (res.data.data.totalItems == 0) {
          $rootScope.showNoneDataInfoTip = true;
          $rootScope.showPageHelpInfo = false;
        } else {
          $rootScope.showNoneDataInfoTip = false;
          $rootScope.showPageHelpInfo = true;
        }
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
        url: $rootScope.site.apiServer + "/api/account/save",
        data: $("#userForm").serialize()
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;

        growl.addSuccessMessage("数据加载完毕。。。");
      });

    };

    $scope.search();
  }
}

export default UserVerifyController;
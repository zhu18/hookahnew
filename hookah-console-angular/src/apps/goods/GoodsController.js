class GoodsController {
  constructor($scope, $rootScope, $http, $uibModal, usSpinnerService, growl) {
    console.log($rootScope.config);

    $scope.search = function () {
      console.log("查询。。。。");
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/goods/all",
        params: $rootScope.pagination
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        // $scope.sysAccount = res.data.data;
        console.log($rootScope.pagination);
        // $rootScope.pagination.store =
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

export default GoodsController;
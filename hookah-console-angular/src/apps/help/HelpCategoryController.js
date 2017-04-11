class HelpCategoryController {
  constructor($scope, $rootScope, $http, $uibModal, usSpinnerService, growl) {
    console.log($rootScope.config);

    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/help/category/category",
        params: {currentPage: $rootScope.pagination.currentPage, pageSize: $rootScope.pagination.pageSize}
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
    $scope.saveCategory = function () {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/help/category/add",
        data: $("#help").serialize()
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("数据加载完毕。。。");
      });

    };
    $scope.pageChanged = function () {
      $scope.search();
      console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };
    $scope.search();
  }
}

export default HelpCategoryController;
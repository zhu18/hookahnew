class NewsController {
  constructor($scope, $rootScope, $http, $uibModal, usSpinnerService, growl) {
    console.log($rootScope.config);
    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/news/all"
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        console.log($rootScope.pagination);
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

export default NewsController;
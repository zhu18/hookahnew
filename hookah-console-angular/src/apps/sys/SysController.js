class SysController {
  constructor($scope, $rootScope, $http, $uibModal, usSpinnerService, growl) {
    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/sys/dict/tree"
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        console.log(res);
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.add = function () {

    };
    $scope.edit = function (event, item) {

    };
    $scope.save = function () {

    };
    $scope.pageChanged = function () {
    };
    $scope.search();
  }
}

export default SysController;
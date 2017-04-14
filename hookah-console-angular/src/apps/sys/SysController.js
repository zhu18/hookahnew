class SysController {
  constructor($scope, $rootScope, $http, $uibModal, usSpinnerService, growl) {
    $scope.col_defs = [
      {
        field: "propery",
        displayName: "属性"
      }];
    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/sys/dict/tree"
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        console.log(res.data);
        $scope.tree_data = res.data;
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
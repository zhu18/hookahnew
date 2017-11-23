class editPointsRuleController {
  constructor($scope, $rootScope, $stateParams, $http, $state, $uibModal, usSpinnerService, growl) {


    $scope.currentPointData=JSON.parse($stateParams.currentPointData);
    console.log($scope.currentPointData);

    $scope.savaRules = function (initCurrentPage) {
      console.log('点击了保存！')
    }
    $scope.search = function (initCurrentPage) {
      // console.log($scope.levelStar);

      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/jr/list",
      });
      promise.then(function (res, status, config, headers) {
        console.log('数据在这里');
        console.log(res);
        if (res.data.code == '1') {
          $scope.pointsList = res.data.data.list;
        } else {

        }
        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });
    };

    $scope.editRule = function (item) {};

    $scope.refresh = function () {
      $scope.search();
    };
    $scope.search('true');
    $scope.back = function () {
      history.back();
    };

  }
}

export default editPointsRuleController;
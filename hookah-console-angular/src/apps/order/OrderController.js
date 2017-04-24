class ShelfController {
  constructor($scope, $rootScope, $http, $uibModal, usSpinnerService, growl) {


    $scope.search = function () {
      console.log("查询。。。。");
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/order/all",
            params: {currentPage: $rootScope.pagination.currentPage,
                     pageSize: $rootScope.pagination.pageSize,
                     orderSn: $scope.orderSn
            }
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            growl.addSuccessMessage("订单数据加载完毕。。。");
        });
    };

      $scope.pageChanged = function () {
          $scope.search();
          console.log('Page changed to: ' + $rootScope.pagination.currentPage);
      };

      $scope.search();

  }
}

export default ShelfController;
class ShelfController {
  constructor($scope, $rootScope, $state, $http, $uibModal, usSpinnerService, growl) {


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

      $scope.getDetails = function (event, orderId) {
          var promise = $http({
              method: 'GET',
              url: $rootScope.site.apiServer + "/api/order/viewDetails",
              params: {
                  orderId: orderId
              }
          });
          promise.then(function (res, status, config, headers) {
              $rootScope.order = res.data.data[0];
              $rootScope.buyer = res.data.data[1];
          });
      };
      $scope.getGoodDetail = function (event, goodsId) {
          var promise = $http({
              method: 'GET',
              url: $rootScope.site.apiServer + "/api/goods/getGoodsInfo",
              params: {
                  goodsId: goodsId
              }
          });
          promise.then(function (res, status, config, headers) {
              if(res.data.code == "1") {
                  $rootScope.editData = res.data.data;
                  $state.go('order.viewGoodDetail', {data: $rootScope.editData});
              }
          });
      };

      $scope.pageChanged = function () {
          $scope.search();
          console.log('Page changed to: ' + $rootScope.pagination.currentPage);
      };
      $scope.back = function () {
          history.back();
      };
      $scope.search();

  }
}

export default ShelfController;
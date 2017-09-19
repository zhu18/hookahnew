class publicController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {

    $scope.search = function (initCurrentPage) {

      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/settleOrder/getList",
        params: {
          orderSn: $scope.orderSn ? $scope.orderSn : null,
          settleStatus: $scope.settleStatus == 0 ? '0' : ($scope.settleStatus ? $scope.settleStatus : null),//审核状态
          shopName: $scope.orgName ? $scope.orgName : null,
          startDate: $scope.startDate ? format($scope.startDate, 'yyyy-MM-dd HH:mm:ss') : null,
          endDate: $scope.endDate ? format($scope.endDate, 'yyyy-MM-dd HH:mm:ss') : null,
          currentPage: initCurrentPage == 'true' ? 1 :$rootScope.pagination.currentPage, //当前页码
          pageSize: $rootScope.pagination.pageSize
        }

      });
      promise.then(function (res, status, config, headers) {
        console.log('数据在这里');
        console.log(res);
        if (res.data.code == '1') {
          $scope.settleList = res.data.data.list;
          // $rootScope.pagination = res.data.data;
          $scope.showNoneDataInfoTip = false;
          if (res.data.data.totalPage > 1) {
            $scope.showPageHelpInfo = true;
          }
        } else {
          $scope.settleList = [];
          $scope.showNoneDataInfoTip = true;

        }
        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });
    };

    $scope.getDetails = function (id) {
      $state.go('settle.settleDetails', {id: id});
    };
    // 去发布
    $scope.public = function (id) {
      $state.go('publish.public', {id: id});
    };


  }
}

export default publicController;
/**
 * Created by lss on 2017/7/3 0003.
 */
class settleDetailController {
  constructor($scope, $rootScope, $state, $http, $stateParams, growl) {
    var promise = $http({
      method: 'GET',
      url: $rootScope.site.apiServer + "/api/settleOrder/getListBySettleId/",
      params: {
        id: $stateParams.id
      }
    });
    promise.then(function (res, status, config, headers) {
      console.log(res);
      if (res.data.code == '1') {
        $scope.order = res.data.data.record;
        $scope.records = res.data.data.records;
        $scope.sid = res.data.data.records;
        $scope.showNoneDataInfoTip = false;
        if (res.data.data.totalPage > 1) {
          $scope.showPageHelpInfo = true;
        }
      } else {
        $scope.settleList = [];
        $scope.showNoneDataInfoTip = true;
      }
    });
    $scope.orderSub = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/settleOrder/settleOrderBy",
        params: {
          sid: $stateParams.id,
          supplierAmount: $scope.supplierAmount,
          tradeCenterAmount: $scope.tradeCenterAmount
        }
      });
      promise.then(function (res, status, config, headers) {
        console.log(res);
        var pruDom=null;
        var modalInstance = null;

        if (res.data.code == '1') {
          pruDom='<div>本次结算处理成功，平台收入'+$scope.tradeCenterAmount+'元，付供应商'+$scope.supplierAmount+'元。</div>'
        } else {
          pruDom='<div>本次结算处理失败，请联系技术人员。</div>'
        }
        modalInstance = $rootScope.openConfirmDialogModal(pruDom);
        modalInstance.result.then(function () {
          history.back();

        });
      });
    };
    $scope.back = function () {
      history.back();
    };
  }
}
export default settleDetailController;
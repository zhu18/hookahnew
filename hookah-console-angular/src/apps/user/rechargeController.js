/**
 * Created by lss on 2017/7/3 0003.
 */
class rechargeController {
  constructor($scope, $rootScope, $state, $http, $stateParams, growl) {
    $scope.userBaseInfo = $stateParams.item;
    console.log($scope.userBaseInfo);

    $scope.recharge = function () {
      if (Number($scope.money) <= 0 || isNaN(Number($scope.money))) {
        alert("充值金额必须大于 0 ！");
        return;
      }
      var modalInstance = $rootScope.openConfirmDialogModal("您要充值的金额是：￥" + '<span style="font-weight: bold;color: red;">' + $scope.money + '</span>');
      modalInstance.result.then(function () {
        var promise = $http({
          method: 'POST',
          url: $rootScope.site.apiServer + "/api/payAccountBg/rechargeByHand",
          params: {
            userId: $scope.userBaseInfo.userId,
            money: $scope.money,
          }
        });
        promise.then(function (res, status, config, headers) {

          if (res.data.code == "1") {
            alert(res.data.message);
            //  $state.go('user.search');
          } else {
            alert(res.data.message);
          }
        });

      }, function () {
        // $log.info('Modal dismissed at: ' + new Date());
      });

    };


    $scope.back = function () {
      history.back();
    };
  }
}
export default rechargeController;
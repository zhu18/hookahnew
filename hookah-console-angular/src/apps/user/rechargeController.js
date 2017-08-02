/**
 * Created by lss on 2017/7/3 0003.
 */
class rechargeController {
  constructor($scope, $rootScope, $state, $http, $stateParams, growl) {
    $scope.userBaseInfo=$stateParams.item;
    console.log($scope.userBaseInfo);

    $scope.recharge = function () {
      if( $scope.money <= 0){
        alert("充值金额必须大于 0 ！")
      }
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/payAccountBg/rechargeByHand",
        params: {
          userId: $scope.userBaseInfo.userId  ,
          money:  $scope.money ,
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
    };



    $scope.back = function () {
      history.back();
    };
  }
}
export default rechargeController;
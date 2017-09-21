class publicController {
  constructor($scope, $rootScope, $http, $state, $stateParams, growl,$filter) {

    $scope.reader = function () {
          var promise = $http({
              method: 'GET',
              url: $rootScope.site.crowdServer + "/api/require/ReqCheck",
              params: {
                  requireSn: $stateParams.id
              }
          });
          promise.then(function (res, status, config, headers) {
              console.log('数据在这里');
              console.log(res);
              if (res.data.code == '1') {
                  var item= res.data.data.list;
                  item = item[0];
                  $scope.requiremetName=item.requiremetName;
                  $scope.contactName=item.contactName;
                  $scope.contactPhone=item.contactPhone;
                  $scope.title=item.title;
                  $scope.description=item.description;
                  $scope.deliveryDeadline=item.deliveryDeadline;
                  $scope.applyDeadline=item.applyDeadline;
                  $scope.rewardMoney=item.rewardMoney;
                  $scope.trusteePercent=item.trusteePercent;
                  // $scope.applyDeadline=item.applyDeadline;
                  $scope.checkRemark=item.checkRemark;
                  $scope.id=item.id;
              } else {


              }
              $rootScope.loadingState = false;
              growl.addSuccessMessage("订单数据加载完毕。。。");
          });
      };
    $scope.reader();

    $scope.public=function (checkStatus) {
          var promise = $http({
              method: 'GET',
              url: $rootScope.site.crowdServer + "/api/require/updateStatus",
              params: {
                  requirementId:$scope.id,
                  status:5,
                  pressTime:$filter('format')($scope.applyDeadline, 'yyyy-MM-dd HH:mm:ss')

              }
          });
          promise.then(function (res, status, config, headers) {
              console.log('数据在这里');
              console.log(res);
              if (res.data.code == '1') {
                  $rootScope.openComponentModal("发布成功！");
                  $state.go('publish.list');
              } else {
                  $rootScope.openComponentModal("发布失败！");

              }
              $rootScope.loadingState = false;
              growl.addSuccessMessage("订单数据加载完毕。。。");
          });
      };
  }
}

export default publicController;
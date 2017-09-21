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
          promise.then(function (res) {
              console.log('数据在这里');
              console.log(res);
              if (res.data.code == '1') {
                  var item= res.data.data.zbRequirement;
                  $scope.zbAnnexes= res.data.data.zbAnnexes;
                  $scope.requiremetName=item.requiremetName;
                  $scope.contactName=item.contactName;
                  $scope.contactPhone=item.contactPhone;
                  $scope.title=item.title;
                  $scope.type=item.type;
                  $scope.tag=item.tag;
                  $scope.description=item.description;
                  $scope.deliveryDeadline=item.deliveryDeadline;
                  $scope.applyDeadline=item.applyDeadline;
                  $scope.rewardMoney=item.rewardMoney;
                  $scope.trusteePercent=item.trusteePercent;
                  $scope.checkRemark=item.checkRemark;
                  $scope.status=item.status;
                  $scope.id=item.id;
              } else {


              }
              $rootScope.loadingState = false;
              growl.addSuccessMessage("订单数据加载完毕。。。");
          });
      };
    $scope.reader();

    $scope.public=function () {
          var promise = $http({
              method: 'GET',
              url: $rootScope.site.crowdServer + "/api/require/updateStatus",
              params: {
                  id:$scope.id,
                  status:5,
                  applyDeadline:$filter('format')($scope.applyDeadline, 'yyyy-MM-dd HH:mm:ss')
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
                  $state.go('publish.list');
              }
              $rootScope.loadingState = false;
              growl.addSuccessMessage("订单数据加载完毕。。。");
          });
      };
  }
}

export default publicController;
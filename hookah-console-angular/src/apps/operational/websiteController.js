class websiteController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
    $scope.commentList = [];
    $scope.choseArr = [];//多选数组

    $scope.search = function () {
      console.log($scope.levelStar);
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/memberCount/count",

      });
      promise.then(function (res, status, config, headers) {
        console.log('数据在这里');
        console.log(res);

        if (res.data.code == '1') {
          $scope.dataInfo = res.data.data;
          $rootScope.pagination = res.data.data;
          $scope.showNoneDataInfoTip = false;
          if (res.data.data.totalPage > 1) {
            $scope.showPageHelpInfo = true;
          }
        } else {
          $scope.commentList = [];
          $scope.showNoneDataInfoTip = true;
        }
        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });

    };

    $scope.refresh = function () {
      $scope.search();
    };
    $scope.search();

  }
}

export default websiteController;
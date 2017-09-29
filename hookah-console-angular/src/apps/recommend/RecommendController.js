class RecommendController {
  constructor($scope, $rootScope, $state, $http, $uibModal, usSpinnerService, growl) {
    $scope.title = '邀请查询';
    $scope.userTypes = [{id: -1, name: "全部"}, {id: 1, name: "个人"}, {id: 4, name: "企业"}];
    $scope.search = function (initCurrentPage) {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/recommend/findRecommendList",
        params: {
          currentPage: initCurrentPage? 1 :$rootScope.pagination.currentPage,
          pageSize: $rootScope.pagination.pageSize,
          userName:$scope.userName,
        }
      });
      promise.then(function (res, status, config, headers) {
        console.log(res);
        $rootScope.loadingState = false;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    //  用户信息详情
    $scope.detail = function (id) {
      $state.go('recommend.detail', {id: id});
    };

    $scope.refresh = function () {
      $scope.search();
    };

    $scope.pageChanged = function () {
      $scope.search();
    };
    $scope.search(true);
  }
}

export default RecommendController;

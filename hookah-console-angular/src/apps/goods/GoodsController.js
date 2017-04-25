class GoodsController {
  constructor($scope, $rootScope, $state, $http, $uibModal, usSpinnerService, growl) {
    console.log($rootScope.config);

    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/goods/all",
        params: {currentPage: $rootScope.pagination.currentPage, pageSize: $rootScope.pagination.pageSize}
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        console.log($rootScope.pagination);
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };


    $scope.delGoods = function (event, item, flag) {
        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/goods/delGoodsById",
            params: {goodsId: item.goodsId, flag: flag
            }
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            if(res.data.code == "1"){
                $scope.search();
                growl.addSuccessMessage("数据加载完毕。。。");
            }
        });
    };


    $scope.updateGoods = function (event, item) {
      console.log("去修改……");
      $rootScope.editData = item;
      $state.go('items.update', {data: item});
    };


    $scope.save = function () {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/account/save",
        data: $("#userForm").serialize()
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("数据加载完毕。。。");
      });

    };


    $scope.pageChanged = function () {
      $scope.search();
      console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };


    $scope.search();
  }
}

export default GoodsController;
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


      /**
       * 1 审核  2强制下架
       * @param item
       * @param n
       */
    $scope.goCheck = function (item, n) {
      console.log("去审核……");
      console.log(n);

        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/goods/getGoodsInfo",
            params: {goodsId: item.goodsId}
        });
        promise.then(function (res, status, config, headers) {
            console.log(res.data)
            if(res.data.code == "1"){
                $rootScope.editData = res.data.data;
                if($rootScope.editData.apiInfo != null){
                    $rootScope.editData.apiInfo.respSample = JSON.stringify(JSON.parse($rootScope.editData.apiInfo.respSample), null, "\t");
                }
                $rootScope.operatorFlag = n;
                $state.go('items.goodsDetail', {data: $rootScope.editData});
            }
        });
    };

    $scope.lookDetail = function (item) {
      console.log("查看商品详情……");
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/goods/getGoodsInfo",
            params: {goodsId: item.goodsId}
        });
        promise.then(function (res, status, config, headers) {
            console.log(res.data)
            if(res.data.code == "1"){
                $rootScope.editData = res.data.data;
                if($rootScope.editData.apiInfo != null){
                    $rootScope.editData.apiInfo.respSample = JSON.stringify(JSON.parse($rootScope.editData.apiInfo.respSample), null, "\t");
                }
                $state.go('items.lookDetail', {data: $rootScope.editData});
            }
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
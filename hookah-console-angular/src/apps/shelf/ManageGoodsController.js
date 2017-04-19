class ManageGoodsController {
  constructor($scope, $rootScope, $stateParams, $http, $state, $uibModal, usSpinnerService, growl) {


    $scope.pageChanged = function () {
          $scope.searchAllGoods();
          console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };


    // 查询货架中商品
    $scope.search = function () {
        $rootScope.shelf = $stateParams.data;
        console.log("已进入货架管理。。。。");
        console.log($stateParams.data.shelvesId);
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/mgGoodssg/findGSMongoById",  // 货架中商品
            // url: $rootScope.site.apiServer + "/api/mgGoodssg/findByIdGSMongo",    // 货架中商品
            params: {currentPage: $rootScope.pagination.currentPage, pageSize: $rootScope.pagination.pageSize, shelvesGoodsId: $stateParams.data.shelvesId}
        });
        promise.then(function (res, status, config, headers) {
            // $scope.shelfGids = res.data.data.goodsIdList;
            // console.log(res.data.data.goodsIdList);

            $rootScope.loadingState = false;
            $scope.shelfGids = res.data.data.list;
            console.log(res.data.data);
            growl.addSuccessMessage("数据加载完毕。。。");
        });
    };

    // 查询所有商品
    $scope.searchAllGoods = function () {
        console.log("查询所有商品。。。。");
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/goods/all",
            params: {currentPage: $rootScope.pagination.currentPage, pageSize: $rootScope.pagination.pageSize}
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            $scope.allGoods = res.data.data.list;
            growl.addSuccessMessage("数据加载完毕。。。");
            console.log(res.data);
        });
    };

    // $scope.delGoods = function (index) {
    //     $scope.pagination.store.splice(index,1);
    // }

    // 移除商品
    $scope.delGoods = function (shelveId, goods) {
        console.log(shelveId);
        console.log(goods.goodsId);
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/mgGoodssg/delSMongoGoodsById",
            params: {shelvesGoodsId:shelveId, goodsId:goods.goodsId}
        });
        promise.then(function (res, status, config, headers) {
            if(res.data.code == "1"){
                $scope.search();
                $scope.searchAllGoods();
                growl.addSuccessMessage("数据重新加载完毕。。。");
            }
        });
    }

    // 添加商品
    $scope.addGoods = function (shelveId, item) {
        console.log(shelveId);
        console.log(item.goodsId);
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/mgGoodssg/addGidByMGid",
            params: {shelvesId:shelveId, goodsId:item.goodsId}
        });
        promise.then(function (res, status, config, headers) {
            if(res.data.code == "1"){
                $scope.search();
                $scope.searchAllGoods();
                growl.addSuccessMessage("数据重新加载完毕。。。");
            }
        });
    }

    $scope.search();

    $scope.searchAllGoods();

  }
}

export default ManageGoodsController;
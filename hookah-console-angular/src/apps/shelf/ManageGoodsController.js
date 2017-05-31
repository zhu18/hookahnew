class ManageGoodsController {
  constructor($scope, $rootScope, $stateParams, $http, $state, $uibModal, usSpinnerService, growl) {

    $scope.searchSection = function(){
        $scope.searchAllGoods();
    }

    $scope.pageChanged = function () {
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/goods/allNotInShelf",
            params: {currentPage: $rootScope.allGoodsPages.currentPage,
                    pageSize: $rootScope.allGoodsPages.pageSize,
                    shelvesGoodsId: $stateParams.data.shelvesId,
                    searchName: $scope.searchName}
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            $rootScope.allGoodsPages = res.data.data;
            $scope.allGoods = res.data.data.list;
        });
    };

    $scope.pageChangedShelf = function () {
          // $scope.search();
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/mgGoodssg/findGSMongoById",  // 货架中商品
            params: {currentPage: $rootScope.shelfPages.currentPage,
                    pageSize: $rootScope.shelfPages.pageSize,
                    shelvesGoodsId: $stateParams.data.shelvesId}
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            $scope.shelfGids = res.data.data.list;
            $rootScope.shelfPages = res.data.data;
            $rootScope.shelfPages.currentPage = res.data.data.currentPage;
            console.log(res.data.data);
        });
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
            $rootScope.loadingState = false;
            $scope.shelfGids = res.data.data.list;
            $rootScope.shelfPages = res.data.data;
            $rootScope.allGoodsPages.currentPage = res.data.data.currentPage;
            console.log(res.data.data);
            growl.addSuccessMessage("数据加载完毕。。。");
        });
    };

    // 查询所有商品
    $scope.searchAllGoods = function () {
        console.log("查询所有不在货架中的商品。。。。");
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/goods/allNotInShelf",
            params: {currentPage: $rootScope.pagination.currentPage,
                    pageSize: $rootScope.pagination.pageSize,
                    shelvesGoodsId: $stateParams.data.shelvesId,
                    searchName: $scope.searchName}
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            $rootScope.allGoodsPages = res.data.data;
            $scope.allGoods = res.data.data.list;
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

    // 添加本页商品
    $scope.batchData = function () {
        // console.log(shelveId);
        console.log($("#batchFrom").serialize());
        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/mgGoodssg/batchAddGidByMGid",
            data: $("#batchFrom").serialize()
        });
        promise.then(function (res, status, config, headers) {
            if(res.data.code == "1"){
                $scope.search();
                $scope.searchAllGoods();
                growl.addSuccessMessage("数据重新加载完毕。。。");
            }
        });
    }

    $scope.searchAllGoods();

    $scope.search();

  }
}

export default ManageGoodsController;
class GoodsCheckController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {

    $scope.search = function () {

      console.log($scope.searchName)
      console.log($scope.searchSn)
      console.log($scope.searchKw)
      console.log($scope.searchKShop)

      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/goods/allNotCheck",
        params: {currentPage: $rootScope.pagination.currentPage,
                 pageSize: $rootScope.pagination.pageSize,
                 goodsName: $scope.searchName,
                 goodsSn: $scope.searchSn,
                 keywords: $scope.searchKw,
                 shopName: $scope.searchShop
        }
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

    $scope.LookGoods = function($event,item){
        console.log(item.goodsName);
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/goods/getGoodsInfo",
            params: {goodsId: item.goodsId}
        });
        promise.then(function (res, status, config, headers) {
            console.log(res.data)
            if(res.data.code == "1"){
                $rootScope.editData = res.data.data;
                $state.go('items.goodsDetail', {data: $rootScope.editData});
            }
        });

    }

    $scope.submitCheck = function(){
        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/goods/goodsCheck",
            data: $("#goodsCheckForm").serialize()
        });
        promise.then(function (res, status, config, headers) {
            console.log(res.data)
            if(res.data.code == "1"){
                $state.go('items.check');
            }
        });
    }

      if ($state.$current.name == "items.check") {
          $scope.search();
      }


  }
}

export default GoodsCheckController;
class GoodsCheckController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {

    $scope.search = function () {

        $rootScope.Name = $scope.searchName;
        $rootScope.Sn = $scope.searchSn;
        $rootScope.Kw = $scope.searchKw;
        $rootScope.Shop = $scope.searchShop;
        $rootScope.CheckStatus = $scope.searchCheckStatus;
        $rootScope.OnSaleStatus = $scope.searchOnSaleStatus;

        // alert("Name=" + $rootScope.Name + "--Sn=" + $rootScope.Sn + "--CheckStatus=" + $rootScope.CheckStatus + "--OnSaleStatus=" + $rootScope.OnSaleStatus);

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

    $scope.checkLookGoods = function(item, n){
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
                $rootScope.operatorFlag = n;
                $state.go('items.checkGoodsDetail', {data: $rootScope.editData});
            }
        });
    }

    $scope.showCurrentGoods = function(item){
        $rootScope.selectId = item.goodsId;
    }

    $scope.submitCheck = function(){
        if($('input[name="checkStatus"]:checked').val() == 2){
            if($("#checkContent").val().trim() != ''){
                var promise = $http({
                    method: 'POST',
                    url: $rootScope.site.apiServer + "/api/goodsCheck/add",
                    data: $("#goodsCheckForm").serialize()
                });
                promise.then(function (res, status, config, headers) {
                    console.log(res.data)
                    if(res.data.code == "1"){
                        $state.go('items.check');
                    }
                });
            }else{
                $rootScope.openErrorDialogModal('请填写审核意见^_^');
            }
        }else{
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/api/goodsCheck/add",
                data: $("#goodsCheckForm").serialize()
            });
            promise.then(function (res, status, config, headers) {
                console.log(res.data)
                if(res.data.code == "1"){
                    $state.go('items.check');
                }
            });
        }
    }

    $scope.forceOff = function(){
        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/goods/forceOff",
            data: $("#forceOffForm").serialize()
        });
        promise.then(function (res, status, config, headers) {
            console.log(res.data)
            if(res.data.code == "1"){
                $state.go('items.search');
            }
        });
    }

    if ($state.$current.name == "items.check") {
        $scope.search();
    }

    $scope.pageChanged = function () {
        $scope.search();
    };

    if ($state.$current.name == "items.check2") {
          $scope.searchName = $rootScope.Name;
          $scope.searchSn = $rootScope.Sn;
    }

    $scope.returnPage = function(){

        $state.go('items.check2');

        var promise = $http({
          method: 'GET',
          url: $rootScope.site.apiServer + "/api/goods/allNotCheck",
          params: {currentPage: $rootScope.pagination.currentPage,
              pageSize: $rootScope.pagination.pageSize,
              goodsName: $rootScope.Name,
              goodsSn: $rootScope.Sn,
              // keywords: $scope.searchKw,
              // shopName: $scope.searchShop,
              checkStatus: $rootScope.CheckStatus,
              onSaleStatus: $rootScope.OnSaleStatus
          }
        });
        promise.then(function (res, status, config, headers) {
          $rootScope.loadingState = false;
        });
    }
  }
}

export default GoodsCheckController;
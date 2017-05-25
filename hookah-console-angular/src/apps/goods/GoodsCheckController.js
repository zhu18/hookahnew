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

    $scope.searchCheckRs = function () {

      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/goodsCheck/all",
        params: {currentPage: $rootScope.pagination.currentPage,
                 pageSize: $rootScope.pagination.pageSize,
                 goodsName: $scope.searchName,
                 goodsSn: $scope.searchSn
        }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("审核结果加载完毕。。。");
      });
    };

    $scope.checkRecord = function (item) {

      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/goodsCheck/all",
        params: {currentPage: $rootScope.pagination.currentPage,
                 pageSize: $rootScope.pagination.pageSize,
                 goodsName: $scope.searchName,
                 goodsSn: $scope.searchSn,
                 goodsId: item.goodsId
        }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("审核结果加载完毕。。。");
      });
    };

    $scope.pageChanged = function () {

        if ($state.$current.name == "items.check") {
            $scope.search();
        }

        if ($state.$current.name == "items.checkedList") {
            $scope.searchCheckRs();
        }
      console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };

    $scope.LookGoods = function(item, n){
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
                // if($rootScope.editData.apiInfo != null){
                //     $rootScope.editData.apiInfo.respSample = JSON.stringify(JSON.parse($rootScope.editData.apiInfo.respSample), null, "\t");
                // }
                $rootScope.operatorFlag = n;
                $state.go('items.goodsDetail', {data: $rootScope.editData});
            }
        });
    }

    $scope.LookDetail = function($event,item){
        console.log("去审核详情……");
        console.log(item);
        $rootScope.editData = item;
        $state.go('items.checkDetail', {data: $rootScope.editData});
    }

    $scope.showCurrentGoods = function(item){
        $rootScope.selectId = item.goodsId;
    }

    $scope.submitCheck = function(){
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


      $scope.aginCheck = function (item) {
          console.log("重新审核……");
          // $state.go('items.check');
          var promise = $http({
              method: 'GET',
              url: $rootScope.site.apiServer + "/api/goods/getGoodsInfo",
              params: {goodsId: item.goodsId}
          });
          promise.then(function (res, status, config, headers) {
              console.log(res.data)
              if(res.data.code == "1"){
                  $rootScope.editData = res.data.data;
                  // if($rootScope.editData.apiInfo != null){
                  //     $rootScope.editData.apiInfo.respSample = JSON.stringify(JSON.parse($rootScope.editData.apiInfo.respSample), null, "\t");
                  // }
                  $state.go('items.goodsDetail', {data: $rootScope.editData});
              }
          });
      };



      if ($state.$current.name == "items.check") {
          $scope.search();
      }

      if ($state.$current.name == "items.checkedList") {
          $scope.searchCheckRs();
      }
  }
}

export default GoodsCheckController;
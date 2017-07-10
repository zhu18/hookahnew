class GoodsCheckController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {

    // $scope.search = function () {
    //
    //     $rootScope.Name = $scope.searchName;
    //     $rootScope.Sn = $scope.searchSn;
    //     $rootScope.Kw = $scope.searchKw;
    //     $rootScope.Shop = $scope.searchShop;
    //     $rootScope.CheckStatus = $scope.searchCheckStatus;
    //     $rootScope.OnSaleStatus = $scope.searchOnSaleStatus;
    //
    //   var promise = $http({
    //     method: 'GET',
    //     url: $rootScope.site.apiServer + "/api/goods/allNotCheck",
    //     params: {currentPage: $rootScope.pagination.currentPage,
    //              pageSize: $rootScope.pagination.pageSize,
    //              goodsName: $scope.searchName,
    //              goodsSn: $scope.searchSn,
    //              keywords: $scope.searchKw,
    //              shopName: $scope.searchShop
    //     }
    //   });
    //   promise.then(function (res, status, config, headers) {
    //     $rootScope.loadingState = false;
    //     growl.addSuccessMessage("数据加载完毕。。。");
    //   });
    // };

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

    $scope.checkedList = function () {

        $rootScope.Name = $scope.searchName;
        $rootScope.Sn = $scope.searchSn;
        $rootScope.Kw = $scope.searchKw;
        $rootScope.Shop = $scope.searchShop;
        $rootScope.CheckStatus = $scope.searchCheckStatus;
        $rootScope.OnSaleStatus = $scope.searchOnSaleStatus;
        $rootScope.orgName = $scope.orgName;


      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/goods/checkedList",
        params: {currentPage: $rootScope.pagination.currentPage,
                 pageSize: $rootScope.pagination.pageSize,
                    goodsName: $scope.searchName,
                    goodsSn: $scope.searchSn,
                    orgName:$scope.orgName
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
                //版本更新
                // $rootScope.operatorFlag = n;
                // $state.go('items.goodsDetail', {data: $rootScope.editData});
                $state.go('items.lookDetail');
				$rootScope.currentS= "1";
				$rootScope.setCurrentS = function (param) {
					// console.log(param);
					$rootScope.currentS = param;

				};
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
    };

    $scope.addData = '';

    $scope.submitCheck = function(){
        // alert(typeof $("#goodsCheckForm").serialize());
        // if($('input[name="checkStatus"]:checked').val() == 2){
        //     if($("#checkContent").val().trim() != ''){
        //         var promise = $http({
        //             method: 'POST',
        //             url: $rootScope.site.apiServer + "/api/goodsCheck/add",
        //             data: $("#goodsCheckForm").serialize()
        //         });
        //         promise.then(function (res, status, config, headers) {
        //             console.log(res.data)
        //             if(res.data.code == "1"){
        //                 // $state.go('items.check');
        //             }
        //         });
        //     }else{
        //         $rootScope.openErrorDialogModal('请填写审核意见^_^');
        //     }
        // }else{
        //     var promise = $http({
        //         method: 'POST',
        //         url: $rootScope.site.apiServer + "/api/goodsCheck/add",
        //         data: $("#goodsCheckForm").serialize()
        //     });
        //     promise.then(function (res, status, config, headers) {
        //         console.log(res.data)
        //         if(res.data.code == "1"){
        //             // $state.go('items.check');
        //         }
        //     });
        // }
    }

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
                  $rootScope.operatorFlag = n;
                  $state.go('items.goodsDetail', {data: $rootScope.editData});
              }
          });
      };

    $scope.forceOff = function(){
        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/goods/forceOff",
            data: $("#forceOffForm").serialize()
        });
        promise.then(function (res, status, config, headers) {
            console.log(res.data)
            if(res.data.code == "1"){
                // $state.go('items.checkedList');
                history.back();
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
                  $rootScope.operatorFlag = 1;
                  $state.go('items.goodsDetail', {data: $rootScope.editData});
              }
          });
      };

      if ($state.$current.name == "items.check") {
          $scope.search();
      }

      if ($state.$current.name == "items.checkedList") {
          // $scope.searchCheckRs();
          $scope.checkedList();
      }

      $scope.pageChanged = function () {

          if ($state.$current.name == "items.check") {
              $scope.search();
          }

          if ($state.$current.name == "items.checkedList") {
              // $scope.searchCheckRs();
              $scope.checkedList();
          }
          console.log('Page changed to: ' + $rootScope.pagination.currentPage);
      };

      if ($state.$current.name == "items.checkedList2") {
          $scope.searchName = $rootScope.Name;
          $scope.searchSn = $rootScope.Sn;
      }

      $scope.returnPage = function(){

          $state.go('items.checkedList2');

          var promise = $http({
              method: 'GET',
              url: $rootScope.site.apiServer + "/api/goods/checkedList",
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
              growl.addSuccessMessage("数据加载完毕。。。");
          });
      }
  }
}

export default GoodsCheckController;
class EsController {
  constructor($scope, $rootScope, $state, $http, $uibModal, usSpinnerService, growl) {
    console.log($rootScope.config);


      $scope.ess = [{no:'1',index:'qingdao-goods-v1',type:'goods'},{no:'2',index:'qingdao-category-v1',type:'category'} ];

    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/es/v1/goods"
        // params: {currentPage: $rootScope.pagination.currentPage,
        //             pageSize: $rootScope.pagination.pageSize,
        //             goodsName: $scope.searchName,
        //             goodsSn: $scope.searchSn,
        //             keywords: $scope.searchKw,
        //             shopName: $scope.searchShop
        // }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        console.log($rootScope.pagination);
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };


    $scope.del = function (id) {

        console.log("id ："+id)
        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/es/del",
            params: {index: id,
            }
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            if(res.data.code == "1"){
                growl.addSuccessMessage("操作成功。。。")
               // $scope.search();
                //growl.addSuccessMessage("数据加载完毕。。。");
            }
        });
    };

      $scope.delGoods = function (goodsId,id,type) {

          if(goodsId == null){
              alert("请录入商品ID");
              return;
          }
          var promise = $http({
              method: 'POST',
              url: $rootScope.site.apiServer + "/api/es/delGoods",
              params: {indexName: id,
                        type:type,
                        goodsId:goodsId
              }
          });
          promise.then(function (res, status, config, headers) {
              $rootScope.loadingState = false;
              if(res.data.code == "1"){
                  growl.addSuccessMessage("操作成功。。。");
                  $scope.search();
                  //growl.addSuccessMessage("数据加载完毕。。。");
              }
          });
      };

      $scope.add = function (val) {
          if(val == null){
              alert("请选择分类");
             return;
          }
          var promise = $http({
              method: 'GET',
              url: $rootScope.site.apiServer + "/api/es/add",
              params: {diff: val
              }
          });
          promise.then(function (res, status, config, headers) {
              console.log(res.data)
              if(res.data.code == "1"){
                  growl.addSuccessMessage("索引重建完毕。。。");
                 // $state.go('elastic.search');
              }
          });
      };

    $scope.pageChanged = function () {
      $scope.search();
      console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };


    if ($state.$current.name == "elastic.delGoods") {
        $scope.search();
    }

  }
}

export default EsController;
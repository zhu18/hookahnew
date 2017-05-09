class ShelfController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {

    $scope.search = function () {
      console.log("货架查询。。。。");
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/shelf/all",
            params: {currentPage: $rootScope.pagination.currentPage,
                        pageSize: $rootScope.pagination.pageSize,
                        shelfName: $scope.shelfName
            }
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            $scope.sysAccount = res.data.data;
            growl.addSuccessMessage("数据加载完毕。。。");
        });
    };

    $scope.add = function () {
        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/shelf/add",
            data: $("#shelfForm").serialize()
        });
        promise.then(function (res, status, config, headers) {
            console.log(res.data)
            if(res.data.code == "1"){
                growl.addSuccessMessage("数据添加完毕。。。");
                $state.go('shelf.search');
            }
        });
    };

    $scope.update = function () {

        console.log("---start---");
        console.log($("#shelfForm").serialize());
        console.log("---end---");

        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/shelf/update",
            data: $("#shelfForm").serialize()
        });
        promise.then(function (res, status, config, headers) {
            console.log(res.data)
            if(res.data.code == "1"){
                growl.addSuccessMessage("数据修改完毕。。。");
                $state.go('shelf.search');
            }
        });
    };

    $scope.pageChanged = function () {
          $scope.search();
          console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };

    $scope.search();

    $scope.updateStatus = function (item, flag) {
          console.log(item.shelvesId, item.shelvesStatus);
          var promise = $http({
              method: 'POST',
              url: $rootScope.site.apiServer + "/api/shelf/updateStatus",
              params: {shelvesId: item.shelvesId, shelvesStatus: flag}
          });
          promise.then(function (res, status, config, headers) {
              if(res.data.code == 1){
                  $scope.search();
              }
          });
    };

    $scope.manageGoods = function (event, item) {
        console.log(item);
        $state.go('shelf.manageGoods', {data: item});
        console.log("即将进货架管理……");
    };

    $scope.updateShelf = function (event, item) {
        console.log(item);
        $rootScope.editData = item;
        $state.go('shelf.update', {data: item});
    };

  }
}

export default ShelfController;
class ShelfController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {

    $scope.search = function () {
      console.log("查询。。。。");
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/shelf/all",
            params: {currentPage: $rootScope.pagination.currentPage, pageSize: $rootScope.pagination.pageSize}
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

    $scope.updateStatus = function (event, item) {
          console.log(item.shelvesId, item.shelvesStatus);
          var promise = $http({
              method: 'POST',
              url: $rootScope.site.apiServer + "/api/shelf/updateStatus",
              params: {shelvesId: item.shelvesId, shelvesStatus: item.shelvesStatus}
          });
          promise.then(function (res, status, config, headers) {
              if(res.data.code == 1){
                  $scope.search();
              }
          });
    };

    $scope.manageGoods = function (event, item) {

            $state.go('shelf.manageGoods');
          // console.log(item.shelvesId, item.shelvesStatus);
          // var promise = $http({
          //     method: 'POST',
          //     url: $rootScope.site.apiServer + "/api/shelf/updateStatus",
          //     params: {shelvesId: item.shelvesId}
          // });
          // promise.then(function (res, status, config, headers) {
          //     if(res.data.code == 1){
          //         $scope.search();
          //     }
          // });
    };

    $scope.updateShelf = function (event, item) {
        $rootScope.editData = item;
        $state.go('shelf.update', {data: item});
    };
  }


}

export default ShelfController;
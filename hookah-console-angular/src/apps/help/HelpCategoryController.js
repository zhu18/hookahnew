class HelpCategoryController {
  constructor($scope, $rootScope, $http, $uibModal, usSpinnerService, growl) {
    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/help/category/category",
        params: {currentPage: $rootScope.pagination.currentPage, pageSize: $rootScope.pagination.pageSize}
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.delete = function (event,item){
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/help/category/delete/"+item.helpId
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("数据加载完毕。。。");
        $scope.search();
      });
    };
    $scope.saveCategory = function () {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/help/category/add",
        data: $("#help").serialize()
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        console.log(res);
        if(res.data.code == "1"){
          growl.addSuccessMessage("保存成功。。。");
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

export default HelpCategoryController;
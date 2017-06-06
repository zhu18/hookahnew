class HelpCategoryController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/help/category/category",
        params: {currentPage: $rootScope.pagination.currentPage, pageSize: $rootScope.pagination.pageSize,name:$scope.name}
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
          $state.go('help.category.search');
        }
      });

    };

    $scope.load = function (event, item) {
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/help/category/" + item.helpId
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            $rootScope.cuserd=res.data.data;
            growl.addSuccessMessage("数据加载完毕。。。");
        });
    };
    $scope.edit = function(){
        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/help/category/update",
            params: {
                helpId:$("#helpId").val(),
                name: $("#name").val()
            }
        });
        promise.then(function (res, status, config, headers) {
            console.log(res.data)
            if(res.data.code == "1"){
                growl.addSuccessMessage("数据修改完毕。。。");
                $state.go('help.category.search');
            }
        });
    };
    $scope.break = function () {
        location.reload();
    };
    $scope.pageChanged = function () {
      $scope.search();
      console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };
    $scope.search();
  }
}

export default HelpCategoryController;
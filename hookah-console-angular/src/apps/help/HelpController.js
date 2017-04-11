class HelpController {
  constructor($scope, $rootScope, $http, $uibModal, usSpinnerService, growl) {

    var editor = new wangEditor('content');
    //上传图片（举例）
    editor.config.uploadImgUrl = '/upload';
    //关闭菜单栏fixed
    editor.config.menuFixed = false;
    editor.config.menus = $.map(wangEditor.config.menus, function(item, key) {
      if (item === 'location') {
        return null;
      }
      if (item === 'video') {
        return null;
      }
      return item;
    });
    editor.create();

    $scope.init = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/help/category/category",
        params: {currentPage: $rootScope.pagination.currentPage, pageSize: 1000}
      });
      promise.then(function (res, status, config, headers) {
        // growl.addSuccessMessage("数据加载完毕。。。");
      });
    };

    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/help/all",
        params: {currentPage: $rootScope.pagination.currentPage, pageSize: $rootScope.pagination.pageSize}
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        // $scope.sysAccount = res.data.data;
        // console.log($rootScope.pagination);
        // $rootScope.pagination.store =
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.add = function () {
      console.log("add。。。。");
    };
    $scope.edit = function (event, item) {
      console.log(item);
    };
    $scope.save = function () {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/help/add",
        data: $("#userForm").serialize()
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.pageChanged = function () {
      $scope.search();
    };
    $scope.search();
    $scope.init();
  }
}

export default HelpController;
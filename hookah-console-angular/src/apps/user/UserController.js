class UserController {
  constructor($scope, $rootScope, $state, $http, $uibModal, usSpinnerService, growl) {
    $scope.title = '用户查询';
    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/user/all",
        params: {
            currentPage: $rootScope.pagination.currentPage,
            pageSize: $rootScope.pagination.pageSize,
            userName: $scope.userName,
            mobile: $scope.mobile,
            email: $scope.email
        }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.detail = function (event, item) {

      $rootScope.cuser = item;
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/user/"+item.userId
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        $rootScope.cuserd=res.data.data;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.save = function () {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/account/save",
        data: $("#userForm").serialize()
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("数据加载完毕。。。");
      });

    };
      $scope.refresh = function(){
          $scope.search();
      }
    $scope.recharge = function () {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/account/recharge",
          params: {
              userId:$("#userId").val(),
              moneyBalance:($("#moneyBalance").val())*100,
              recharge:$("#recharge").val()
          }
      });
      promise.then(function (res, status, config, headers) {
          if (res.data.code == "1"){
              alert(res.data.data);
              $state.go('user.search');
          }else {
              alert(res.data.message);
          }
      });
    };
    $scope.back = function () {
      history.back();
    };
    $scope.pageChanged = function () {
      $scope.search();
    };
    $scope.search();
  }
}

export default UserController;
class AccountController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/account/sys_all",
        params:{userName: $scope.userName}
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        $scope.sysAccount = res.data.data;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    
      $scope.edit = function (event, item) {
          var promise = $http({
              method: 'POST',
              url: $rootScope.site.apiServer + "/api/account/upd",
              data: $("#editForm").serialize()
          });
          promise.then(function (res, status, config, headers) {
              if (res.data.code == "1") {
                  alert(res.data.data);
                  $state.go('account.search');
              } else {
                  alert(res.data.message);
              }
          });
      };
      $scope.delete = function (event, item) {
          var promise = $http({
              method: 'POST',
              url: $rootScope.site.apiServer + "/api/account/delete",
              params: {
                  userId: item.userId,
                  userName: item.userName
              }
          });
          promise.then(function (res, status, config, headers) {
              $rootScope.loadingState = false;
              // alert(res.data.message);
              $scope.search();
              growl.addSuccessMessage("数据加载完毕。。。");
          });
      };
      $scope.load = function (event, item) {
          var promise = $http({
              method: 'GET',
              url: $rootScope.site.apiServer + "/api/account/" + item.userId,
          });
          promise.then(function (res, status, config, headers) {
              $rootScope.cuserd=res.data.data;
          });
      };
    $scope.add = function () {
      console.log("add。。。。");
    };
    // $scope.edit = function (event, item) {
    //   console.log(item);
    // };
    $scope.save = function () {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/account/save",
        data: $("#userForm").serialize()
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
          if(res.data.code == "1"){
              $state.go('account.search');
          }
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };


    $scope.pageChanged = function () {
      $scope.search();
    };
    $scope.search();
  }
}

export default AccountController;
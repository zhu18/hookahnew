class AccountController {
  constructor($scope, $rootScope,$http, $uibModal, usSpinnerService,growl) {
    console.log($rootScope.config);
    // $.ajax({
    //   type: "GET",
    //   url: $rootScope.site.apiServer+"/api/account/sys_all",
    //   success: function (data) {
    //     $scope.sysAccount = data.data;
    //   },
    //   error: function (XMLHttpRequest, textStatus, errorThrown) {
    //     console.log(XMLHttpRequest);
    //     // var currUrl = window.location;
    //     // console.log(currUrl);
    //     // if (401 === XMLHttpRequest.status) {
    //     //   window.location.href = "http://auth.hookah.app/oauth/authorize?client_id=admin&response_type=code&redirect_uri=" + currUrl;
    //     // }
    //   }
    // });
    var promise = $http({
      method: 'GET',
      url: $rootScope.site.apiServer + "/api/account/sys_all"
    });
    promise.then(function (res, status, config, headers) {
      $rootScope.loadingState = false;
      $scope.sysAccount = res.data.data;
      growl.addSuccessMessage("数据加载完毕。。。");
    });

    $scope.search = function(){
      console.log("查询。。。。");
    };
    $scope.add = function(){
      console.log("add。。。。");
    };
    $scope.edit = function(event,item){
      console.log(item);
    };
    $scope.save = function(){
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
  }
}

export default AccountController;
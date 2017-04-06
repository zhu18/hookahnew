class AccountController {
  constructor($scope, $rootScope,$http, $uibModal, usSpinnerService) {
    console.log($rootScope.config);
    $.ajax({
      type: "GET",
      url: $rootScope.site.apiServer+"/api/account/sys_all",
      success: function (data) {
        $scope.sysAccount = data.data;
      },
      error: function (XMLHttpRequest, textStatus, errorThrown) {
        console.log(XMLHttpRequest);
        // var currUrl = window.location;
        // console.log(currUrl);
        // if (401 === XMLHttpRequest.status) {
        //   window.location.href = "http://auth.hookah.app/oauth/authorize?client_id=admin&response_type=code&redirect_uri=" + currUrl;
        // }
      }
    });
    $scope.search = function(){
      console.log("查询。。。。");
    };
    $scope.add = function(){
      console.log("add。。。。");
    };
    $scope.edit = function(){
      console.log("edit。。。。");
    };
    $scope.save = function(){
      var ss= $("#userForm").serialize();
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/account/save",
        data: $("#userForm").serialize()
      });
      promise.then(function (res, status, config, headers) {
        // $scope.tableData = data.data;
        // $rootScope.pagination.totalItems = data.totalItems;
        // $rootScope.pagination.currentPage = data.currentPage;
        // console.log(res.data);
        $rootScope.loadingState = false;
        console.log($rootScope.pagination);

        growl.addSuccessMessage("数据加载完毕。。。");
      });
      console.log("save。。。。"+ss);

    };
  }
}

export default AccountController;
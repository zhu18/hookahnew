class UserVerifyController {
  constructor($scope, $rootScope, $state, $http, $uibModal, usSpinnerService, growl) {
    $scope.title = '用户审核';
    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/user/verify/all"
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        $rootScope.pagination.store = res.data.data.list;
        $rootScope.pagination.currentPage = res.data.data.currentPage;
        $rootScope.pagination.totalItems = res.data.data.totalItems;
        if (res.data.data.totalItems == 0) {
          $rootScope.showNoneDataInfoTip = true;
          $rootScope.showPageHelpInfo = false;
        } else {
          $rootScope.showNoneDataInfoTip = false;
          $rootScope.showPageHelpInfo = true;
        }
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };
    $scope.detail = function (event, item) {

        $rootScope.cuser = item;

        var userType = item.userType;

        if(userType == '3'){
          //个人用户认证
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/user/"+item.userId
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                $rootScope.cuserd=res.data.data;
                $state.go("user.verify.checkUserDetail");
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        }else if(userType == '5'){
          //企业用户认证
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/user/org/"+item.userId
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                $rootScope.cuserd=res.data.data;
                $state.go("user.verify.checkUserDetail");
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        }else{
            $rootScope.openErrorDialogModal("数据有误！");
        }


    };

    $scope.submitCheck = function () {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/userCheck/add",
        data: $("#userCheckForm").serialize()
      });
      promise.then(function (res, status, config, headers) {
          console.log(res.data);
          if(res.data.code == "1"){
              alert("提交成功");
          }
      });

    };

    $scope.search();
  }
}

export default UserVerifyController;
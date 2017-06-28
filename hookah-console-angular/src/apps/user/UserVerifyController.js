class UserVerifyController {
  constructor($scope, $rootScope, $state, $http, $uibModal, usSpinnerService, growl) {
    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/user/verify/all",
        params: {
          currentPage: $rootScope.pagination.currentPage,
          pageSize: $rootScope.pagination.pageSize,
          userName: $scope.userName,
          mobile: $scope.mobile,
          email: $scope.email,
          userType: $scope.userType
        }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("数据加载完毕。。。");

        // $rootScope.loadingState = false;
        // $rootScope.pagination.store = res.data.data.list;
        // $rootScope.pagination.currentPage = res.data.data.currentPage;
        // $rootScope.pagination.totalItems = res.data.data.totalItems;
        // if (res.data.data.totalItems == 0) {
        //   $rootScope.showNoneDataInfoTip = true;
        //   $rootScope.showPageHelpInfo = false;
        // } else {
        //   $rootScope.showNoneDataInfoTip = false;
        //   $rootScope.showPageHelpInfo = true;
        // }
        // growl.addSuccessMessage("数据加载完毕。。。");
      });
    };


    $scope.searchCheckResult = function () {
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/userCheck/all",
            params: {
                currentPage: $rootScope.pagination.currentPage,
                pageSize: $rootScope.pagination.pageSize,
                userName: $scope.userName,
                userType: $scope.userType
            }
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            growl.addSuccessMessage("数据加载完毕。。。");

            // $rootScope.loadingState = false;
            // $rootScope.pagination.store = res.data.data.list;
            // $rootScope.pagination.currentPage = res.data.data.currentPage;
            // $rootScope.pagination.totalItems = res.data.data.totalItems;
            // if (res.data.data.totalItems == 0) {
            //     $rootScope.showNoneDataInfoTip = true;
            //     $rootScope.showPageHelpInfo = false;
            // } else {
            //     $rootScope.showNoneDataInfoTip = false;
            //     $rootScope.showPageHelpInfo = true;
            // }
            // growl.addSuccessMessage("数据加载完毕。。。");
        });
    }

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
                url: $rootScope.site.apiServer + "/api/user/org/"+item.orgId
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
              $state.go('user.verify.resultAll');
          }
      });

    };

    $scope.checkDetail = function (event, item) {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/userCheck/" + item.id,
      });
      promise.then(function (res, status, config, headers) {
          console.log(res.data);
          if(res.data.code == "1"){
              alert("提交成功");
              $state.go('user.verify.resultAll');
          }
      });

    };

      $scope.pageChanged = function () {
          if ($state.$current.name == "user.verify.all") {
              $scope.search();
          }

          if ($state.$current.name == "user.verify.resultAll") {
              $scope.searchCheckResult();
          }
      };

      if ($state.$current.name == "user.verify.all") {
          $scope.title = '用户审核';
          $scope.userTypes = [{id:-1,name:"全部"},{id:3,name:"个人认证"},{id:5,name:"企业认证"}];
          $scope.userType = -1;
          $scope.search();
      }

      if ($state.$current.name == "user.verify.resultAll") {
          $scope.title = '审核记录';
          $scope.searchCheckResult();
          $scope.userTypes = [{id:-1,name:"全部"},{id:0,name:"个人"},{id:1,name:"企业"}];
          $scope.userType = -1;
      }

  }
}

export default UserVerifyController;
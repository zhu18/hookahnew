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
          email: $scope.email,
          userType: $scope.userType
        }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };

    //  用户信息详情
    $scope.detail = function (item) {
        $state.go('user.detail', {id: item.userId});
    };
    $scope.showBigImg=function (imgUrl) {
      console.log(imgUrl)
      var pruDom='<div><img width="100%" src="http://static.qddata.com.cn/'+imgUrl+'" alt=""></div>';
      var modalInstance = $rootScope.openJustShowDialogModal(pruDom);
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
    $scope.refresh = function () {
      $scope.search();
    }
    $scope.recharge = function () {
      var promise = $http({
        method: 'POST',
        url: $rootScope.site.apiServer + "/api/account/recharge",
        params: {
          userId: $("#userId").val(),
          moneyBalance: ($("#moneyBalance").val()) * 100,
          recharge: $("#recharge").val()
        }
      });
      promise.then(function (res, status, config, headers) {
        if (res.data.code == "1") {
          alert(res.data.data);
          $state.go('user.search');
        } else {
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

    if ($state.$current.name == "user.search") {
      $scope.title = '用户查询';
      $scope.userTypes = [{id: -1, name: "全部"}, {id: 1, name: "未认证"}, {id: 2, name: "个人"}, {id: 3, name: "个人待审核"},
        {id: 4, name: "企业"}, {id: 5, name: "企业待审核"}, {id: 6, name: "个人审核失败"}, {id: 7, name: "企业审核失败"}];
      $scope.userType = -1;
      $scope.search();
    }
  }
}

export default UserController;
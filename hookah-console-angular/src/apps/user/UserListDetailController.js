/**
 * Created by lss on 2017/7/4 0004.
 */
class UserListDetailController {
    constructor($scope, $rootScope,$http, $stateParams,growl) {
        var promise = $http({
          method: 'GET',
          url: $rootScope.site.apiServer + "/api/user/" + $stateParams.id
        });
        promise.then(function (res, status, config, headers) {
          $rootScope.cuser=res.data.data.user;
          $rootScope.loadingState = false;

          if ($rootScope.cuser.userType == 2) {//个人用户
            $rootScope.cuserd = res.data.data.userDetail;

          }else if ($rootScope.cuser.userType == 4) {//企业用户
            $rootScope.cuserd = res.data.data.organization;

          }else if ($rootScope.cuser.userType == 1) {//未认证

          }
          $rootScope.loginLog=res.data.data.loginLogs;
          growl.addSuccessMessage("数据加载完毕。。。");
        });

        $scope.showBigImg=function (imgUrl) {
            var pruDom='<div><img width="100%" src="http://static.qddata.com.cn/'+imgUrl+'" alt=""></div>';
            var modalInstance = $rootScope.openJustShowDialogModal(pruDom);
        };
    }
}

export default UserListDetailController;
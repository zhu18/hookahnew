/**
 * Created by lss on 2017/7/3 0003.
 */
class getMoneyDetailController {
  constructor($scope, $rootScope, $state, $http, $stateParams, growl) {

    $scope.search = function () {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/withdrawRecord/getOneById",
        params: {
          id: $stateParams.id
        }
      });
      promise.then(function (res, status, config, headers) {
        console.log(res);
        if (res.data.code == '1') {
          $scope.order = res.data.data;
          $scope.showNoneDataInfoTip = false;
          if (res.data.data.totalPage > 1) {
            $scope.showPageHelpInfo = true;
          }
        } else {
          $scope.settleList = [];
          $scope.showNoneDataInfoTip = true;
        }
      });
    };
    $scope.search();
    $scope.orderSub = function (status) {
      var pruDom = null;
      var modalInstance = null;
      var title1 = '提示消息';
      if (status == '1') {
        pruDom = '<div style="padding: 20px;">确认通过提现申请？</div>';
        $('#checkContent').val('true')
      } else {
        pruDom = '<div style="padding: 20px;"><p>不通过原因：</p><textarea name="" id="checkContent" cols="60" rows="10"></textarea> </textarea></div>'
      }
      modalInstance = $rootScope.openConfirmDialogModel(title1, pruDom);
      modalInstance.result.then(function () {
        $rootScope.loadingState = true;
        growl.addSuccessMessage("正在请求数据。。。");

        if (status == 1) {
          $scope.checkOneFn(status)
        } else if ($('#checkContent').val() && status == 2) {
          $scope.checkOneFn(status);
        } else if ($('#checkContent').val() == '' && status == 2) {
          $rootScope.openErrorDialogModal('原因不能为空', function () {
          })
        }

      });
    };
    $scope.checkOneFn = function (status) {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/withdrawRecord/checkOne",
        params: {
          id: $stateParams.id,
          checkStatus: status,
          checkMsg: $('#checkContent').val()
        }
      });
      promise.then(function (res, status, config, headers) {
        console.log(res);
        if (res.data.code == '1') {
          $scope.search()
        } else {
        }
        $rootScope.loadingState = false;
      });
    }
    $scope.back = function () {
      history.back();
    };
  }
}
export default getMoneyDetailController;
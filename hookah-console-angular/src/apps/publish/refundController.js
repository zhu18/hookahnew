/**
 * Created by lss on 2017/10/24 0024.
 */
class refundController {
    constructor($scope, $rootScope, $http, $state, $stateParams, growl) {
        console.log($stateParams.id);
        console.log($stateParams.userId);
        $scope.reader = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.crowdServer + "/api/refund/selectRefundInfo",
                params: {
                    userId: $stateParams.userId,
                    requiredmentId: $stateParams.id
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    $scope.cardOwner=res.data.data.cardOwner;
                    $scope.cardCode=res.data.data.cardCode;
                    $scope.addTime=res.data.data.addTime;
                    $scope.money=res.data.data2;
                    $scope.addTime=res.data.data.addTime;

                } else {
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };
        $scope.reader();
        $scope.auditing=function (checkStatus) {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.crowdServer + "/api/require/requirementCheck",
                params: {
                    requirementId:$scope.id,
                    checkContent:$scope.checkContent,
                    checkStatus:checkStatus
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    var modalInstance =$rootScope.openConfirmDialogModal("审核结果提交成功！");
                    modalInstance.result.then(function () {
                        $state.go('publish.list');
                    }, function () {
                        $state.go('publish.list');
                    });

                } else {
                    var modalInstance =$rootScope.openConfirmDialogModal("审核结果提交失败！");
                    modalInstance.result.then(function () {
                        $state.go('publish.list');
                    }, function () {
                        $state.go('publish.list');
                    });
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };
    }
}

export default refundController;
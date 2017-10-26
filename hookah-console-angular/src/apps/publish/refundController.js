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

                } else {
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };
        $scope.reader();
        $scope.auditing=function () {
            var promise = $http({
                method: 'get',
                url: $rootScope.site.crowdServer + "/api/refund/goRefund",
                params: {
                    userId: $stateParams.userId,
                    requirementId:$stateParams.id,
                    bankName:$scope.cardOwner,
                    refundAmount:$scope.money,
                    bankCardNum:$scope.cardCode,
                    desc:$scope.desc,
                    payTime:$scope.addTime
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

                    }, function () {

                    });
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };
        $scope.back=function () {
            $state.go('publish.list');
        }
        // 日历插件开始

        $scope.inlineOptions = {
            customClass: getDayClass,
            minDate: new Date(),
            // maxDate: new Date(data),
            showWeeks: true
        };
        $scope.open1 = function () {
            $scope.popup1.opened = true;
        };
        $scope.popup1 = {
            opened: false
        };
        var tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        var afterTomorrow = new Date();
        afterTomorrow.setDate(tomorrow.getDate() + 1);
        $scope.events = [
            {
                date: tomorrow,
                status: 'full'
            },
            {
                date: afterTomorrow,
                status: 'partially'
            }
        ];
        function getDayClass(data) {
            var date = data.date,
                mode = data.mode;
            if (mode === 'day') {
                var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

                for (var i = 0; i < $scope.events.length; i++) {
                    var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                    if (dayToCheck === currentDay) {
                        return $scope.events[i].status;
                    }
                }
            }
            return '';
        }

        // 日历插件结束
    }
}

export default refundController;
/**
 * Created by lss on 2017/9/19 0019.
 */
class invoiceAuditingDetailsController {
    constructor($scope, $rootScope, $http, $state, $stateParams, growl) {
        console.log($stateParams.id);
        $scope.invoiceStatu="2";
        $scope.reader=function () {//渲染页面
            let promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/userInvoiceTitle/back/findById",
                params: {
                    invoiceId:$stateParams.id
                }
            });
            promise.then(function (res, status, config, headers) {
                if (res.data.code == '1') {
                    console.log(res.data.data);
                    var info=res.data.data;
                    $scope.invoiceStatus=info.invoiceStatus;//发票状态
                    $scope.invoiceType=info.invoiceType;//审核状态
                    $scope.auditOpinion=info.auditOpinion;//审核意见
                    $scope.userName=info.userName;//买家账号
                    $scope.realName=info.realName;//实名认证
                    $scope.userType=info.userType;//账号类型
                    $scope.addTime=info.addTime;//申请时间
                    $scope.titleName=info.titleName;//发票抬头
                    $scope.taxpayerIdentifyNo=info.taxpayerIdentifyNo;//纳税人识别号
                    $scope.regTel=info.regTel;//注册电话
                    $scope.regAddress=info.regAddress;//注册地址
                    $scope.bankAccount=info.bankAccount;//银行账户
                } else {

                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };
        $scope.reader();
        $scope.readerData=function (data) {
            // 日历插件开始
            $scope.inlineOptions = {
                customClass: getDayClass,
                minDate: new Date(),
                maxDate: new Date(data),
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
        $scope.readerData()
        $scope.auditing=function () {//审核函数
            var modalInstance =$rootScope.openConfirmDialogModal("确认审核通过？");
            modalInstance.result.then(function () {
                let data={
                    titleId:$stateParams.id,
                    invoiceStatus:$scope.invoiceStatu,
                    auditOpinion:$scope.auditOpinion
                }
                let promise = $http({
                    method: 'post',
                    url: $rootScope.site.apiServer + "/api/userInvoiceTitle/back/check",
                    params: {invoice:JSON.stringify(data)}
                });
                promise.then(function (res, status, config, headers) {
                    if (res.data.code == '1') {
                        console.log(res.data.data);
                        $state.go('invoice.list');
                    } else {

                    }
                    $rootScope.loadingState = false;
                    growl.addSuccessMessage("订单数据加载完毕。。。");
                });
            }, function () {
                $state.go('invoice.listDetails');
            });

        }

    }
}

export default invoiceAuditingDetailsController;
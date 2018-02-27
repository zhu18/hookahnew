/**
 * Created by lss on 2017/9/19 0019.
 */
class invoiceListDetailsController {
    constructor($scope, $rootScope, $http, $state, $stateParams, growl) {
        console.log($stateParams.id);
        $scope.invoiceStatu="2";
        $scope.reader=function () {//渲染页面
            let promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/invoice/back/findById",
                params: {
                    invoiceId:$stateParams.id
                }
            });
            promise.then(function (res, status, config, headers) {
                if (res.data.code == '1') {
                    console.log(res.data.data);
                    var info=res.data.data;
                    $scope.invoiceStatus=info.invoiceStatus;//发票状态
                    $scope.invoiceSn=info.invoiceSn;
                    $scope.invoiceType=info.invoiceType;
                    $scope.auditOpinion=info.auditOpinion;
                    $scope.invoiceChange=info.invoiceChange;
                    var userInvoiceVo=info.userInvoiceVo;//增票资质
                    if(userInvoiceVo){
                        $scope.userName=userInvoiceVo.userName;
                        $scope.realName=userInvoiceVo.realName;
                        $scope.userType=userInvoiceVo.userType;
                    }
                    $scope.addTime=info.addTime;
                    $scope.invoiceAmount=info.invoiceAmount;
                    $scope.orderInfoInvoiceVoList=info.orderInfoInvoiceVoList; // 订单列表
                    if($scope.orderInfoInvoiceVoList.length){
                        for(var i=0;i<$scope.orderInfoInvoiceVoList.length;i++){
                            var res='isShow_'+i;
                            $scope[res]=true;
                        }
                    }
                    var userInvoiceTitle=info.userInvoiceTitle;//增票资质
                    $scope.titleId=info.titleId;
                    if(userInvoiceTitle){
                        $scope.titleName=userInvoiceTitle.titleName;
                        $scope.taxpayerIdentifyNo=userInvoiceTitle.taxpayerIdentifyNo;
                        $scope.regTel=userInvoiceTitle.regTel;
                        $scope.regAddress=userInvoiceTitle.regAddress;
                        $scope.bankAccount=userInvoiceTitle.bankAccount;
                    }else if($scope.titleId == 0){
                        $scope.titleName="个人"
                    }
                    var userInvoiceAddress=info.userInvoiceAddress;//收票信息
                    if(userInvoiceAddress){
                        $scope.invoiceName=userInvoiceAddress.invoiceName;
                        $scope.mobile=userInvoiceAddress.mobile;
                        $scope.receiveAddress=userInvoiceAddress.receiveAddress;
                    }
                    var expressInfo=info.expressInfo;//邮寄信息
                    if(expressInfo){
                        $scope.expressName=expressInfo.expressName;
                        $scope.expressNo=expressInfo.expressNo;
                        $scope.addTime=expressInfo.addTime;
                    }else {
                        $scope.expressName="";
                        $scope.expressNo="";
                        $scope.addTime="";
                    }
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
            if($scope.invoiceStatu=="3" && ($scope.auditOpinion==="" || !$scope.auditOpinion )){
                $rootScope.openJustShowDialogModal('审核意见不能为空！')
            }else {
                var modalInstance =$rootScope.openConfirmDialogModal("确认审核通过？");
                modalInstance.result.then(function () {
                    let data={
                        invoiceId:$stateParams.id,
                        invoiceStatus:$scope.invoiceStatu,
                        auditOpinion:$scope.auditOpinion
                    }
                    let promise = $http({
                        method: 'post',
                        url: $rootScope.site.apiServer + "/api/invoice/back/check",
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
        $scope.save=function () {//邮寄函数
            var modalInstance =$rootScope.openConfirmDialogModal("确认提交信息吗？");
            modalInstance.result.then(function () {
                let data={
                    invoiceId:$stateParams.id,
                    expressName:$scope.expressName,
                    expressNo:$scope.expressNo,
                    addTime:$scope.addTime
                };
                let promise = $http({
                    method: 'post',
                    url: $rootScope.site.apiServer + "/api/invoice/back/send",
                    params: {expressInfo:JSON.stringify(data)}
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


        $scope.controlBtn=function (index) {
            var res='isShow_'+index;
            if ($scope[res]){
                $scope[res]=false;
            }else {
                $scope[res]=true;
            }
        }


    }
}

export default invoiceListDetailsController;
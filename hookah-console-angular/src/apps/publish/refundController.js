/**
 * Created by lss on 2017/10/24 0024.
 */
class refundController {
    constructor($scope, $rootScope, $http, $state, $stateParams, growl) {
        console.log($stateParams.id);
        // $scope.reader = function () {
        //     var promise = $http({
        //         method: 'GET',
        //         url: $rootScope.site.crowdServer + "/api/require/ReqCheck",
        //         params: {
        //             id: $stateParams.id
        //         }
        //     });
        //     promise.then(function (res, status, config, headers) {
        //         console.log('数据在这里');
        //         console.log(res);
        //         if (res.data.code == '1') {
        //             var item= res.data.data.zbRequirement;
        //             $scope.zbAnnexes= res.data.data.zbAnnexes;
        //             $scope.requiremetName=item.requiremetName;
        //             $scope.contactName=item.contactName;
        //             $scope.contactPhone=item.contactPhone;
        //             $scope.title=item.title;
        //             $scope.tag=item.tag.split(',');
        //             $scope.type=item.type;
        //             $scope.description=item.description;
        //             $scope.deliveryDeadline=item.deliveryDeadline;
        //             $scope.applyDeadline=item.applyDeadline;
        //             $scope.rewardMoney=item.rewardMoney;
        //             $scope.trusteePercent=item.trusteePercent;
        //             $scope.applyDeadline=item.applyDeadline;
        //             $scope.checkRemark=item.checkRemark;
        //             $scope.status=item.status;
        //             $scope.id=item.id;
        //             //进度时间
        //             var mgZbRequireStatus = res.data.data.mgZbRequireStatus || null;//进度时间
        //             if(mgZbRequireStatus){
        //                 $scope.addTime=mgZbRequireStatus.addTime;//发布需求
        //                 $scope.checkTime=mgZbRequireStatus.checkTime;//平台审核
        //                 $scope.trusteeTime=mgZbRequireStatus.trusteeTime;//资金托管
        //                 $scope.pressTime=mgZbRequireStatus.pressTime;//平台发布
        //                 $scope.workingTime=mgZbRequireStatus.workingTime;//服务商工作
        //                 $scope.payTime=mgZbRequireStatus.payTime;//验收付款
        //                 $scope.commentTime=mgZbRequireStatus.commentTime;//评价
        //             }
        //         } else {
        //         }
        //         $rootScope.loadingState = false;
        //         growl.addSuccessMessage("订单数据加载完毕。。。");
        //     });
        // };
        // $scope.reader();
        // $scope.auditing=function (checkStatus) {
        //     var promise = $http({
        //         method: 'GET',
        //         url: $rootScope.site.crowdServer + "/api/require/requirementCheck",
        //         params: {
        //             requirementId:$scope.id,
        //             checkContent:$scope.checkContent,
        //             checkStatus:checkStatus
        //         }
        //     });
        //     promise.then(function (res, status, config, headers) {
        //         console.log('数据在这里');
        //         console.log(res);
        //         if (res.data.code == '1') {
        //             var modalInstance =$rootScope.openConfirmDialogModal("审核结果提交成功！");
        //             modalInstance.result.then(function () {
        //                 $state.go('publish.list');
        //             }, function () {
        //                 $state.go('publish.list');
        //             });
        //
        //         } else {
        //             var modalInstance =$rootScope.openConfirmDialogModal("审核结果提交失败！");
        //             modalInstance.result.then(function () {
        //                 $state.go('publish.list');
        //             }, function () {
        //                 $state.go('publish.list');
        //             });
        //         }
        //         $rootScope.loadingState = false;
        //         growl.addSuccessMessage("订单数据加载完毕。。。");
        //     });
        // };
    }
}

export default refundController;
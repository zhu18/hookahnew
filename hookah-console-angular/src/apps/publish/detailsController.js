/**
 * Created by LSS on 2017/9/19 0019.
 */

class detailsController {
    constructor($scope, $rootScope, $http, $state,$stateParams,growl,$filter) {
        console.log($stateParams.id);
        $scope.apply={
        };

        $scope.currDate=$filter('format')(new Date(), 'yyyy-MM-dd HH:mm:ss');
        $scope.screen = function () {
            let promise = $http({
                method: 'GET',
                url: $rootScope.site.crowdServer + "/requireApply/viewApply",
                params: {
                   id:$stateParams.id
                }
            });
            promise.then(function (res, status, config, headers) {
                if (res.data.code == '1') {
                    var zbRequirement= res.data.data.zbRequirement; //基本信息
                    var zbRequirementApplies= res.data.data.zbRequirementApplies; //报名的人
                    var zbPrograms= res.data.data.zbPrograms;
                    var zbComments= res.data.data.zbComments;
                    $scope.zbAnnexes= res.data.data.zbAnnexes;
                    //基本信息
                    $scope.requiremetName=zbRequirement.requiremetName;
                    $scope.contactName=zbRequirement.contactName;
                    $scope.contactPhone=zbRequirement.contactPhone;
                    $scope.title=zbRequirement.title;
                    $scope.tag=zbRequirement.tag;
                    $scope.type=zbRequirement.type;
                    $scope.description=zbRequirement.description;
                    $scope.deliveryDeadline=zbRequirement.deliveryDeadline;
                    $scope.applyDeadline=zbRequirement.applyDeadline;
                    $scope.rewardMoney=zbRequirement.rewardMoney;
                    $scope.trusteePercent=zbRequirement.trusteePercent;
                    $scope.applyDeadline=zbRequirement.applyDeadline;
                    $scope.checkRemark=zbRequirement.checkRemark;
                    $scope.status=zbRequirement.status;
                    $scope.id=zbRequirement.id;
                    //报名tab
                    if(zbRequirementApplies.length>0){
                        $scope.zbRequirementApplies=zbRequirementApplies;
                        $scope.isZbRequirementAppliesShow=false;
                    }else {
                        $scope.isZbRequirementAppliesShow=true;
                    }
                    //成果tab
                    if(zbPrograms.length>0){
                        var item=zbPrograms[0];
                        $scope.title=item.title;
                        $scope.content=item.content;
                        $scope.zbProgramsStatus=item.status;
                        $scope.programId=item.id;
                        $scope.isZbProgramsShow=false;
                    }else {
                        $scope.isZbProgramsShow=true;
                    }
                    // //评价tab
                    if(zbComments.length>0){
                        $scope.zbComments=zbComments;
                        $scope.isZbCommentsShow=false;
                    }else {
                        $scope.isZbCommentsShow=true;
                    }
                } else {

                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };
        $scope.screen();
        $scope.submit=function () {
            console.log($scope.apply.id);
            console.log($scope.apply.name);
            if($scope.apply.id){
                $scope.zbRequirementApplies.forEach(function (item) {
                    if(item.id==$scope.apply.id){
                        $scope.apply.name=item.userName
                    }
                })
                var modalInstance =$rootScope.openConfirmDialogModal("是否确认选中"+$scope.apply.name);
                modalInstance.result.then(function () {
                    var promise = $http({
                        method: 'GET',
                        url: $rootScope.site.crowdServer + "/api/require/checkEnroll",
                        params: {
                            id:$scope.apply.id,
                            requirementId:$scope.id
                        }
                    });
                    promise.then(function (res, status, config, headers) {
                        console.log('数据在这里');
                        console.log(res);
                        if (res.data.code == '1') {
                            var modalInstance =$rootScope.openConfirmDialogModal("提交成功！");
                            modalInstance.result.then(function () {
                                $state.go('publish.list');
                            }, function () {
                                $state.go('publish.list');
                            });
                        } else {

                            var modalInstance =$rootScope.openConfirmDialogModal("提交失败！");
                            modalInstance.result.then(function () {
                                $state.go('publish.list');
                            }, function () {
                                $state.go('publish.list');
                            });
                        }
                        $rootScope.loadingState = false;
                        growl.addSuccessMessage("订单数据加载完毕。。。");
                    });
                    $state.go('publish.list');
                }, function () {
                    $state.go('publish.list');
                });
            }else {
                var modalInstance =$rootScope.openConfirmDialogModal("还未资格筛选，请选择中标的服务商！");
                modalInstance.result.then(function () {

                }, function () {

                });
            }


        };
        $scope.check=function () {
            var modalInstance = null;
            modalInstance = $rootScope.openConfirmDialogModalCheck();
            modalInstance.result.then(function () { //模态点提交
                var promise = null;
                promise = $http({
                    method: 'POST',
                    url: $rootScope.site.crowdServer + "/api/require/updateStatus",
                    params: {
                        id: $scope.id,
                        programId:$scope.programId,
                        checkAdvice: $('#checkContent').val(),
                        status: $('input:radio[name="tRadio"]:checked').val()
                    }
                });
                promise.then(function (res, status, config, headers) {
                    $rootScope.loadingState = false;
                    if (res.data.code == 1) {
                        growl.addSuccessMessage("保存成功。。。");
                        $state.go('publish.list');
                    } else {
                        growl.addErrorMessage("保存失败。。。");
                        $state.go('publish.list');
                    }

                });
            }, function () {
            });
        }

        $scope.public=function (id) {
            $state.go('publish.public', {id: id});
        };
        $scope.back=function (id) {
            $state.go('publish.list', {id: id});
        };
        $scope.giveUp=function (id) {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.crowdServer + "/api/require/updateStatus",
                params: {
                    id:id,
                    status:16
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    var modalInstance =$rootScope.openConfirmDialogModal("确认流标成功！");
                    modalInstance.result.then(function () {
                        $state.go('publish.list');
                    }, function () {
                        $state.go('publish.list');
                    });
                } else {

                    var modalInstance =$rootScope.openConfirmDialogModal("确认流标失败！");
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

export default detailsController;
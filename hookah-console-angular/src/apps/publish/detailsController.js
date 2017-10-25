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
                    var zbRequirement= res.data.data.zbRequirement || null; //基本信息
                    var zbRequirementApplies= res.data.data.zbRequirementApplies || null; //报名的人
                    var zbPrograms= res.data.data.zbPrograms || null;//成果
                    var zbComments= res.data.data.zbComments || null;//评价
                    $scope.zbAnnexes= res.data.data.zbAnnexes || null;//需求材料
                    $scope.reqProgram= res.data.data.reqProgram || null;//需求材料
                    var mgZbRequireStatus = res.data.data.mgZbRequireStatus || null;//进度时间
                    //基本信息
                    $scope.requiremetName=zbRequirement.requiremetName;
                    $scope.contactName=zbRequirement.contactName;
                    $scope.contactPhone=zbRequirement.contactPhone;
                    $scope.title=zbRequirement.title;
                    $scope.tag=zbRequirement.tag.split(',');
                    $scope.type=zbRequirement.type;
                    $scope.description=zbRequirement.description;
                    $scope.deliveryDeadline=zbRequirement.deliveryDeadline;
                    $scope.applyDeadline= zbRequirement.applyDeadline;
                    $scope.rewardMoney=zbRequirement.rewardMoney;
                    $scope.trusteePercent=zbRequirement.trusteePercent;
                    $scope.checkRemark=zbRequirement.checkRemark;
                    $scope.status=zbRequirement.status;
                    $scope.id=zbRequirement.id;
                    $scope.userId=zbRequirement.userId;
                    //进度时间
                    if(mgZbRequireStatus){
                        $scope.addTime=mgZbRequireStatus.addTime;//发布需求
                        $scope.checkTime=mgZbRequireStatus.checkTime;//平台审核
                        $scope.trusteeTime=mgZbRequireStatus.trusteeTime;//资金托管
                        $scope.pressTime=mgZbRequireStatus.pressTime;//平台发布
                        $scope.workingTime=mgZbRequireStatus.workingTime;//服务商工作
                        $scope.payTime=mgZbRequireStatus.payTime;//验收付款
                        $scope.commentTime=mgZbRequireStatus.commentTime;//评价
                    }
                    //报名tab
                    if(zbRequirementApplies && zbRequirementApplies.length>0){
                        $scope.zbRequirementApplies=zbRequirementApplies;
                        $scope.isZbRequirementAppliesShow=false;
                        console.log(1);
                    }else {
                        $scope.isZbRequirementAppliesShow=true;
                        console.log(2);
                    }
                    //成果tab
                    if(zbPrograms && zbPrograms.length>0){
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

        $scope.public=function () {
            var data=new Date($scope.applyDeadline);
            $scope.applyDeadline= data;
            console.log(data.setDate(tomorrow.getDate() + 5));
            console.log($filter('format')(data, 'yyyy-MM-dd HH:mm:ss'));
            console.log($scope.deliveryDeadline);
            if($filter('format')(data, 'yyyy-MM-dd HH:mm:ss')>$scope.deliveryDeadline){
                var modalInstance =$rootScope.openConfirmDialogModal("报名截止日期不得超过交付日期前五天，请重新选择日期！");
                modalInstance.result.then(function () {

                }, function () {

                });

            } else {
                alert("shidehdheidheidh");
                // var promise = $http({
                //     method: 'GET',
                //     url: $rootScope.site.crowdServer + "/api/require/updateStatus",
                //     params: {
                //         id:$scope.id,
                //         status:5,
                //         applyDeadline:$filter('format')($scope.applyDeadline, 'yyyy-MM-dd HH:mm:ss')
                //     }
                // });
                // promise.then(function (res, status, config, headers) {
                //     console.log('数据在这里');
                //     console.log(res);
                //     if (res.data.code == '1') {
                //         var modalInstance =$rootScope.openConfirmDialogModal("发布成功！");
                //         modalInstance.result.then(function () {
                //             $state.go('publish.list');
                //         }, function () {
                //             $state.go('publish.list');
                //         });
                //     } else {
                //
                //         var modalInstance =$rootScope.openConfirmDialogModal("发布失败！");
                //         modalInstance.result.then(function () {
                //             $state.go('publish.list');
                //         }, function () {
                //             $state.go('publish.list');
                //         });
                //     }
                //     $rootScope.loadingState = false;
                //     growl.addSuccessMessage("订单数据加载完毕。。。");
                // });
            }

        };


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

        $scope.back=function (id) {
            $state.go('publish.list', {id: id});
        };
        $scope.refund=function (id,userId) {
            $state.go('publish.refund', {id: id,userId:userId});
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
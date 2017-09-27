/**
 * Created by LSS on 2017/9/19 0019.
 */

class detailsController {
    constructor($scope, $rootScope, $http, $state,$stateParams,growl) {
        console.log($stateParams.id);
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
                    //报名tab
                    if(zbRequirementApplies){

                        $scope.isZbRequirementAppliesShow=false;
                    }else {
                        $scope.isZbRequirementAppliesShow=true;
                    }
                    //成果tab
                    if(zbPrograms){

                        $scope.isZbProgramsShow=false;
                    }else {
                        $scope.isZbProgramsShow=true;
                    }
                    //评价tab

                } else {

                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };
        $scope.screen();
    }
}

export default detailsController;
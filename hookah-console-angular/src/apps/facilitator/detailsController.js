/**
 * Created by lss on 2017/10/20 0020.
 */

class detailsController {
    constructor($scope, $rootScope, $http, $state, $stateParams, growl) {
        $scope.reader = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.crowdServer + "/api/require/AllProvider",
                params: {
                    userId:$stateParams.id
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    var info=res.data.data.list[0];
                    $scope.authType=info.authType;

                    $scope.upname=info.upname;
                    $scope.phoneNum=info.phoneNum;
                    $scope.registerTime=info.registerTime;
                    $scope.registerAddr=info.registerAddr;

                    $scope.status=info.status==1?"未认证":'已认证';
                    $scope.specialSkills=info.specialSkills;
                    $scope.providerDesc=info.providerDesc;

                    $scope.educationsExpList=info.educationsExpList;
                    $scope.worksExpList=info.worksExpList;
                    $scope.projectsExpList=info.projectsExpList;
                    $scope.appCaseList=info.appCaseList;
                    $scope.swpList=info.swpList;
                    $scope.inPatentsList=info.inPatentsList;

                } else {
                    $scope.settleList = [];
                    $scope.showNoneDataInfoTip = true;
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };

        $scope.reader();

        $scope.auditing=function (id) {
            var modalInstance =$rootScope.openConfirmDialogModal("是否通过审核？");
            modalInstance.result.then(function () {
                var promise = $http({
                    method: 'POST',
                    url: $rootScope.site.crowdServer + "/api/require/provideCheck",
                    params: {
                        userId:$stateParams.id,
                        status:2
                    }
                });
                promise.then(function (res, status, config, headers) {
                    console.log('数据在这里');
                    console.log(res);
                    if (res.data.code == '1') {
                        $state.go('facilitator.list');
                    } else {
                        $state.go('facilitator.list');
                    }

                });
            }, function () {
                var promise = $http({
                    method: 'POST',
                    url: $rootScope.site.crowdServer + "/api/require/provideCheck",
                    params: {
                        userId:$stateParams.id,
                        status:2
                    }
                });
                promise.then(function (res, status, config, headers) {
                    console.log('数据在这里');
                    console.log(res);
                    if (res.data.code == '1') {
                        $state.go('facilitator.list');
                    } else {

                        $state.go('facilitator.list');
                    }

                });
            });
        };

        $scope.back=function () {
            $state.go('facilitator.list');
        }
    }
}

export default detailsController;

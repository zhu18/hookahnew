/**
 * Created by lss on 2017/9/19 0019.
 */
class auditingController {
    constructor($scope, $rootScope, $http, $state, $stateParams, growl) {
        console.log($stateParams.id);
        $scope.reader = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.crowdServer + "/api/require/allRequirement",
                params: {
                    requireSn: $stateParams.id
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    var item= res.data.data.list;
                        item = item[0];
                    $scope.requiremetName=item.requiremetName;
                    $scope.contactName=item.contactName;
                    $scope.contactPhone=item.contactPhone;
                    $scope.title=item.title;
                    $scope.description=item.description;
                    $scope.deliveryDeadline=item.deliveryDeadline;
                    $scope.applyDeadline=item.applyDeadline;
                    $scope.rewardMoney=item.rewardMoney;
                    $scope.trusteePercent=item.trusteePercent;
                    // $scope.applyDeadline=item.applyDeadline;
                    $scope.checkRemark=item.checkRemark;
                    $scope.id=item.id;
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
                url: $rootScope.site.crowdServer + "/api/require/allRequirement",
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

                } else {


                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };
        // 去发布
        $scope.public = function (id) {
            $state.go('publish.public', {id: id});
        };
    }
}

export default auditingController;
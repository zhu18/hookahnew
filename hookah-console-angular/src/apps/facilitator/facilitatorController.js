
/**
 * Created by lss on 2017/9/19 0019.
 */
class facilitatorController {
    constructor($scope, $rootScope, $http, $state, $stateParams, growl) {
        $scope.search = function (initCurrentPage) {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.crowdServer + "/api/require/AllProvider",
                params: {
                    authType: $scope.authType ? $scope.authType : null,
                    status: $scope.status,//审核状态
                    upname: $scope.upname ? $scope.upname : null,
                    currentPage: initCurrentPage == 'true' ? 1 :$rootScope.pagination.currentPage, //当前页码
                    pageSize: $rootScope.pagination.pageSize,
                    startTime:$scope.startDate?format($scope.startDate, 'yyyy-MM-dd HH:mm:ss'):null,
                    endTime:$scope.endDate?format($scope.endDate, 'yyyy-MM-dd HH:mm:ss'):null
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    $scope.facilitatorList = res.data.data.list;
                    $scope.showNoneDataInfoTip = true;
                    if (res.data.data.totalPage > 0) {
                        $scope.showNoneDataInfoTip = false;
                        $scope.showPageHelpInfo = true;
                    }else {
                        $scope.showPageHelpInfo = true;
                    }
                } else {
                    $scope.settleList = [];
                    $scope.showNoneDataInfoTip = true;
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };

        $scope.search();
        $scope.refresh = function () {
            $scope.search();
        };
        $scope.pageChanged = function () {
            $scope.search();
        };
        $scope.getDetails=function (id) {
            $state.go('facilitator.details', {id: id});

        }
    }
}

export default facilitatorController;
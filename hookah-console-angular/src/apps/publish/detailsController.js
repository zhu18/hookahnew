/**
 * Created by LSS on 2017/9/19 0019.
 */

class detailsController {
    constructor($scope, $rootScope, $http, $state,$stateParams,growl) {
        console.log($stateParams.id);
        $scope.render = function () {
            let promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/settleOrder/getList",
                params: {
                   id:$stateParams.id
                }

            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    $scope.settleList = res.data.data.list;
                    $scope.showNoneDataInfoTip = false;
                    if (res.data.data.totalPage > 1) {
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
        $scope.render();
        $scope.back = function () {
            $state.go('publish.list');
        };
    }
}

export default detailsController;
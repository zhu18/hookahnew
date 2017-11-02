/**
 * Created by lss on 2017/11/2 0002.
 */
/**
 * Created by lss on 2017/9/19 0019.
 */
class recommendController {
    constructor($scope, $rootScope, $http, $state, $stateParams, growl) {
        console.log($stateParams.id);
        $scope.search = function () { //渲染页面数据
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.crowdServer + "/api/require/ReqCheck",
                params: {
                    id: $stateParams.id
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    var item= res.data.data.zbRequirement;
                    $scope.zbAnnexes= res.data.data.zbAnnexes;
                    $scope.requiremetName=item.requiremetName;
                    $scope.contactName=item.contactName;
                    $scope.contactPhone=item.contactPhone;

                } else {
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };
        $scope.search();
        $scope.prior=function () {//人为输入优先级保存事件

        };

    }
}

export default recommendController;
/**
 * Created by lss on 2017/11/2 0002.
 */
/**
 * Created by lss on 2017/9/19 0019.
 */
class recommendController {
    constructor($scope, $rootScope, $http, $state, $stateParams, growl) {
        console.log($stateParams.id);
        $rootScope.pagination.currentPage="1";
        $scope.sort='desc';
        $scope.search = function (sort) { //渲染页面数据
            if(sort=='desc'){
                $scope.sort='asc';
            }else if(sort=='asc'){
                $scope.sort='desc';
            }
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.crowdServer + "/api/require/taskManagement",
                params: {
                    title: $scope.title ? $scope.title : null,
                    currentPage:$rootScope.pagination.currentPage, //当前页码
                    pageSize: $rootScope.pagination.pageSize,
                    userName:$scope.userName ,
                    requireSn:$scope.requireSn,
                    sort: $scope.sort
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    $scope.lists= res.data.data.list;
                } else {
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };
        $scope.search();
        $scope.orderNum="";
        $scope.prior=function (id,orderNum) {//人为输入优先级保存事件
            console.log(orderNum);
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.crowdServer + "/api/require/taskNumber",
                params: {
                    requirementId:id,
                    orderNum:orderNum
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    var modalInstance =$rootScope.openConfirmDialogModal("保存成功!");
                    modalInstance.result.then(function () {

                    }, function () {

                    });
                } else {
                    var modalInstance =$rootScope.openConfirmDialogModal(res.data.message);
                    modalInstance.result.then(function () {

                    }, function () {

                    });
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };

    }
}

export default recommendController;
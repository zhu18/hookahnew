/**
 * Created by lss on 2017/7/14 0014.
 */
class viewOrderSupplier {
    constructor($scope, $rootScope, $state, $http, $uibModal, uibDateParser, usSpinnerService, growl) {
        $scope.search = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/order/soldOrder",
                params: {
                    currentPage: $rootScope.pagination.currentPage,
                    pageSize: $rootScope.pagination.pageSize,
                    orderSn: $scope.orderSn?$scope.orderSn:null,
                    userName:$scope.userName?$scope.userName:null,
                    addUser:$scope.addUser?$scope.addUser:null,
                    startDate:$scope.startDate?format($scope.startDate, 'yyyy-MM-dd HH:mm:ss'):null,
                    solveStatus:$scope.solveStatus?$scope.solveStatus:null,
                    endDate:$scope.endDate?format($scope.endDate, 'yyyy-MM-dd HH:mm:ss'):null
                }
            });
            promise.then(function (res, status, config, headers) {
                $scope.supplierList=res.data.data.list
            });
        };
        $scope.search()
    }
}
export default viewOrderSupplier;
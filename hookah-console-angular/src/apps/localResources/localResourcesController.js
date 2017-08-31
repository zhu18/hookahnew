class localResourcesController {
    constructor($scope, $rootScope, $http, $state, $stateParams, $uibModal, usSpinnerService, growl) {


        $scope.goodsStatuss = [{id:-1,name:'全部'},{id:0,name:'未上架'},{id:1,name:'已上架'}];
        $scope.onSaleStatus = $scope.goodsStatuss[0].id;
        $scope.search = function () {
            $rootScope.tree_data = [];
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/pushGoods/all",
                params:{
                        currentPage: $rootScope.pagination.currentPage,
                        pageSize: $rootScope.pagination.pageSize,
                        goodsName: $scope.goodsName?$scope.goodsName:null,
                        goodsSn:$scope.goodsSn?$scope.goodsSn:null,
                        keywords:$scope.keywords?$scope.keywords:null,
                        onSaleStatus:$scope.onSaleStatus
                }
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                $scope.pushData = res.data.data;
                console.log(res.data.data);
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };

        $scope.search();


        $scope.pageChanged = function () {//翻页
        		$scope.search();
        };//翻页
    }
}

export default localResourcesController;
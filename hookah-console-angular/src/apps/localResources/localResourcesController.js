class localResourcesController {
    constructor($scope, $rootScope, $http, $state, $stateParams, $uibModal, usSpinnerService, growl) {
        console.log("参数获取");
        $scope.goodsStatuss = [{id:-1,name:'全部'},{id:0,name:'已上架'},{id:1,name:'未上架'}];
        $scope.goodsStatus = $scope.goodsStatuss[0].id;
        $scope.search = function () {
            $rootScope.tree_data = [];
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/category/allTree"
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                $scope.tree_data = res.data.data;
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };

    }
}

export default localResourcesController;
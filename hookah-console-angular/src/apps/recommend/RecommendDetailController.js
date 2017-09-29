class RecommendDetailController {
    constructor($scope, $rootScope, $http, $stateParams, growl) {
        $scope.title="后台邀请好友明细";
        $scope.search = function (initCurrentPage) {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/recommend/findRecommendList",
                params: {
                    currentPage: initCurrentPage? 1 :$rootScope.pagination.currentPage,
                    pageSize: $rootScope.pagination.pageSize,
                    userId:$stateParams.id
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log(res);
                $rootScope.loadingState = false;
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };
        $scope.back = function () {
            history.back();
        };
        $scope.refresh = function () {
            $scope.search();
        };
        $scope.pageChanged = function () {
            $scope.search();
        };
        $scope.search(true);
    }
}

export default RecommendDetailController;
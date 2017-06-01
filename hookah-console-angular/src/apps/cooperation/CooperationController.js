class CooperationController {
    constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
        $scope.search = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/coo/search",
                params: {currentPage: $rootScope.pagination.currentPage,
                    pageSize: $rootScope.pagination.pageSize,
                    cooName: $scope.cooName
                }
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };
        $scope.load = function (event, cooperation) {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/coo/" + cooperation.cooperationId,
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                $rootScope.cuserd=res.data.data;
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };
        $scope.add = function () {
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/coo/add",
                data: $("#newCooperation").serialize()
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                if (res.data.code == "1"){
                    alert(res.data.data);
                    $state.go('cooperation.search');
                }else{
                    alert(res.data.message);
                }
                growl.addSuccessMessage("数据加载完毕。。。");
            });

        };
        $scope.edit = function () {
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/coo/modify",
                params: {
                    cooperationId:$("#cooperationId").val(),
                    cooPhone: $("#cooPhone").val(),
                    cooAddress: $("#cooAddress").val(),
                    url: $("#url").val(),
                    pictureUrl: $("#pictureUrl").val(),
                    cooName: $("#cooName").val(),
                }
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                if (res.data.code = 1){
                    alert(res.data.message);
                }
                alert(res.data.message);
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };
        $scope.delete = function (event, cooperation) {
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/coo/delete",
                params: {
                    cooperationId: cooperation.cooperationId,
                    cooName: cooperation.cooName
                }
            });

            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                alert(res.data.data);
                $scope.search();
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };
        $scope.pageChanged = function () {
            $scope.search();
        };
        $scope.search();
    }
}

export default CooperationController;
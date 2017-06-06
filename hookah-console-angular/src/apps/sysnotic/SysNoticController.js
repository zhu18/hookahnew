class SysNoticController {
    constructor($scope, $rootScope, $state, $http, $uibModal, usSpinnerService, growl) {
        $scope.search = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/sysnotice/search",
                params: {currentPage: $rootScope.pagination.currentPage,
                    pageSize: $rootScope.pagination.pageSize,
                    noticeTitle: $scope.noticeTitle
                }
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };
        $scope.load = function (event, item) {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/sysnotice/" + item.noticeId,
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
                url: $rootScope.site.apiServer + "/sysnotice/add",
                data: $("#newSysnotice").serialize()
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                if (res.data.code == "1"){
                    alert(res.data.data);
                    $state.go('sysnotice.search');
                }else{
                    alert(res.data.message);
                }
                growl.addSuccessMessage("数据加载完毕。。。");
            });

        };
        $scope.edit = function () {
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/sysnotice/upd",
                params: {
                    noticeId:$("#noticeId").val(),
                    status: $("#status").val(),
                    noticeContent: $("#noticeContent").val(),
                    url: $("#url").val(),
                    pictureUrl: $("#pictureUrl").val(),
                    noticeTitle: $("#noticeTitle").val(),
                }
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                if (res.data.code == "1"){
                    alert(res.data.data);
                    $state.go('sysnotice.search');


                }else{
                    alert(res.data.message);
                }
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };
        $scope.delete = function (event, item) {
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/sysnotice/delete",
                params: {
                    noticeId: item.noticeId,
                    noticeTitle: item.noticeTitle
                }
            });

            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
               // alert(res.data.message);
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
export default SysNoticController;
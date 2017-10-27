/**
 * Created by lss on 2017/10/26 0026.
 */
class cardController {
    constructor($scope, $rootScope, $http, $state, $stateParams, growl) {
        console.log($stateParams.id);
        $scope.level=1;
        $scope.readerInfo = function () {
            //基本信息
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.crowdServer + "/api/auth/providerCard",
                params: {
                    id: $stateParams.id
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    var info= res.data.data;
                    if(info){
                        $scope.level= res.data.data2;
                        $scope.authType  = info.authType;
                        $scope.upname= info.upname;
                        $scope.ucity= info.ucity;
                        $scope.specialSkills= info.specialSkills;
                        $scope.providerDesc= info.providerDesc;
                    }

                } else {
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };
        $scope.readerTrade = function (initCurrentPage) {
            //交易记录
            var promise = $http({
                method: 'post',
                url: $rootScope.site.crowdServer + "/api/getTradeRecord",
                params: {
                    id: $stateParams.id,
                    currentPage:initCurrentPage == 'true' ? 1 :$rootScope.pagination.currentPage, //当前页码
                    pageSize: $rootScope.pagination.pageSize
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    $scope.tradeRecord= res.data.data.list;
                    $scope.showNoneDataInfoTip = true;
                    if (res.data.data.totalPage > 0) {
                        $scope.showNoneDataInfoTip = false;
                        $scope.showPageHelpInfo = true;
                    }else {
                        $scope.showPageHelpInfo = true;
                    }
                } else {
                    $scope.showNoneDataInfoTip = true;
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };
        $scope.readerComment = function (initCurrentPage) {
            //评价记录
            var promise = $http({
                method: 'post',
                url: $rootScope.site.crowdServer + "/api/getCommentRecord",
                params: {
                    id: $stateParams.id,
                    currentPage:initCurrentPage == 'true' ? 1 :$rootScope.pagination.currentPage, //当前页码
                    pageSize: $rootScope.pagination.pageSize
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    $scope.commentRecord= res.data.data.list;
                    $scope.showNoneDataInfoTip = true;
                    if (res.data.data.totalPage > 0) {
                        $scope.showNoneDataInfoTip = false;
                        $scope.showPageHelpInfo = true;
                    }else {
                        $scope.showPageHelpInfo = true;
                    }
                } else {
                    $scope.showNoneDataInfoTip = true;
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };
        $scope.readerInfo();
        $scope.readerTrade();
        $scope.readerComment();
    }
}

export default cardController;
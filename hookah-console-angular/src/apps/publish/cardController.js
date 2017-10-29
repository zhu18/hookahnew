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
                url: $rootScope.site.crowdServer + "/api/auth/getAuthInfoByUserId",
                params: {
                    userId: $stateParams.id
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    var info= res.data.data;
                    if(info){

                        $scope.authType  = info.authType;
                        $scope.upname= info.upname;
                        $scope.ucity= info.ucity;
                        $scope.specialSkills= info.specialSkills;
                        $scope.providerDesc= info.providerDesc;
                    }
                    var level=res.data.data2?res.data.data2:null;
                    $scope.levels=[
                        {
                            "commentNum": 0,
                            "commentLevel": 5
                        },
                        {
                            "commentNum": 0,
                            "commentLevel": 4
                        },
                        {
                            "commentNum": 0,
                            "commentLevel": 3
                        },
                        {
                            "commentNum": 0,
                            "commentLevel": 2
                        },
                        {
                            "commentNum": 0,
                            "commentLevel": 1
                        }
                    ]
                    $scope.total=''

                    level.forEach(function (item) {
                        var commentLevel=item.commentLevel;
                        var commentNum=item.commentNum;
                        $scope.total += item.commentNum;
                        $scope.myStyle = {
                            "color" : "white",
                        }
                        $scope.levels.forEach(function (item) {
                            if(commentLevel == item.commentLevel){
                                item.commentNum=commentNum;
                            }
                        })
                    })


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
                    userId: $stateParams.id,
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
                    userId: $stateParams.id,
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
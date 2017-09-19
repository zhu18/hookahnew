/**
 * Created by lss on 2017/9/19 0019.
 */
class auditingController {
    constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
        $scope.settleList = [];
        $scope.choseArr = [];//多选数组
        $scope.currentIndex=null; //初始化日历插件默认选择项

        $scope.setDate = function (dataFormat, number, aIndex) {
            var now = new Date();
            var date = new Date(now.getTime() - 1);
            var year = date.getFullYear();
            var month = date.getMonth();
            var day = 1;
            if (number == 0) {
                $scope.startDate = new Date(year, month, day);
                $scope.endDate = new Date();
            } else {
                $scope.startDate = new Date(year, month - number, day);
                $scope.endDate = new Date(year, month, 0);
            }
            $scope.currentIndex = aIndex;

        }
        var format = function (time, format) {
            var t = new Date(time);
            var tf = function (i) {
                return (i < 10 ? '0' : "") + i
            };
            return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function (a) {
                switch (a) {
                    case 'yyyy':
                        return tf(t.getFullYear());
                        break;
                    case 'MM':
                        return tf(t.getMonth() + 1);
                        break;
                    case 'mm':
                        return tf(t.getMinutes());
                        break;
                    case 'dd':
                        return tf(t.getDate());
                        break;
                    case 'HH':
                        return tf(t.getHours());
                        break;
                    case 'ss':
                        return tf(t.getSeconds());
                        break;
                }
            })
        };
        $scope.setDate('month', 0, 0);

        $scope.search = function (initCurrentPage) {
            // console.log($scope.levelStar);

            if ($scope.startDate !== null && $scope.endDate !== null && ($scope.startDate > $scope.endDate)) {
                //继续
                alert('开始时间必须大于结束时间！请重新选择日期。');
                return;
            }
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/settleOrder/getList",
                params: {
                    orderSn: $scope.orderSn ? $scope.orderSn : null,
                    settleStatus: $scope.settleStatus == 0 ? '0' : ($scope.settleStatus ? $scope.settleStatus : null),//审核状态
                    shopName: $scope.orgName ? $scope.orgName : null,
                    startDate: $scope.startDate ? format($scope.startDate, 'yyyy-MM-dd HH:mm:ss') : null,
                    endDate: $scope.endDate ? format($scope.endDate, 'yyyy-MM-dd HH:mm:ss') : null,
                    currentPage: initCurrentPage == 'true' ? 1 :$rootScope.pagination.currentPage, //当前页码
                    pageSize: $rootScope.pagination.pageSize
                }

            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    $scope.settleList = res.data.data.list;
                    // $rootScope.pagination = res.data.data;
                    $scope.showNoneDataInfoTip = false;
                    if (res.data.data.totalPage > 1) {
                        $scope.showPageHelpInfo = true;
                    }
                } else {
                    $scope.settleList = [];
                    $scope.showNoneDataInfoTip = true;

                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };


        $scope.getDetails = function (id) {
            $state.go('settle.settleDetails', {id: id});
        };
        // 去发布
        $scope.public = function (id) {
            $state.go('publish.public', {id: id});
        };

        $scope.pageChanged = function () {
            $scope.search();
            console.log('Page changed to: ' + $rootScope.pagination.currentPage);
        };


    }
}

export default auditingController;
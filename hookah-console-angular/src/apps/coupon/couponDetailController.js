/**
 * Created by Administrator on 2017/11/9 0009.
 */
class couponDetailController {
    constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl,$stateParams) {
        console.log($stateParams.id);
        $scope.pageSizes = [               //自定义显示条数
            {id:-1, name:"显示条数"},
            {id:1, name:"20"},
            {id:2, name:"50"}
        ];
        $scope.useStatus = [               //自定义使用状态数据
            {id:-1, name:"全部"},
            {id:1, name:"已使用"},
            {id:2, name:"未使用"},
            {id:2, name:"已过期"}
        ];
        $scope.controlScreenShow=true;
        $scope.controlScreenBtn=function () { //控制筛选盒子显隐的函数
            if ($scope.controlScreenShow){
                $scope.controlScreenShow=false;
            }else {
                $scope.controlScreenShow=true;

            }
        };

        $scope.renderInfo = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/coupon/getCouponById",
                params: {
                    couponId:$stateParams.id
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);

                if (res.data.code == '1') {
                    var info=res.data.data;
                    $scope.couponType=info.couponType; //优惠券类型
                    $scope.couponName=info.couponName; //名称
                    $scope.applyGoods=info.applyGoods; //可使用商品
                    $scope.applyChannel=info.applyChannel;
                    $scope.faceValue=(info.faceValue/100);
                    $scope.couponStatus=info.couponStatus;
                    $scope.expiryStartDate=info.expiryStartDate;
                    $scope.expiryEndDate=info.expiryEndDate;

                    $scope.totalCount=info.totalCount;
                    $scope.receivedCount=info.receivedCount;

                    $scope.usedCount=info.usedCount;

                } else {

                }

                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });

        };
        $scope.renderInfo();

        $scope.renderList = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/coupon/getCouponDetail",
                params: {
                    couponId:$stateParams.id,
                    userCouponStatus:$scope.userCouponStatus,
                    orderSn:$scope.orderSn,
                    currentPage:$rootScope.pagination.currentPage, //当前页码
                    pageSize: $rootScope.pagination.pageSize,
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);

                if (res.data.code == '1') {
                    $scope.userList=res.data.data.list;
                } else {

                }

                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });

        };
        $scope.renderList();
        $scope.refresh = function () {
            $scope.search();
        };
        $scope.pageChanged = function () {
            $scope.renderList();
        };

    }
}

export default couponDetailController;


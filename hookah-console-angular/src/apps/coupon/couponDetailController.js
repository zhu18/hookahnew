/**
 * Created by Administrator on 2017/11/9 0009.
 */
class couponDetailController {
    constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl,$stateParams) {
        console.log($stateParams.id);
        $scope.pageSizes = [               //自定定义类型数据
            {id:'20', name:"20"},
            {id:'50', name:"50"}
        ];
        $rootScope.pagination.pageSize="20";

        $scope.useStatus = [               //自定定义类型数据
            {id:"", name:"全部"},
            {id:0, name:"未使用"},
            {id:1, name:"已使用"},
            {id:2, name:"已过期"}
        ];
        $scope.userCouponStatus="";
        $scope.controlScreen=function () { //控制搜索框展示函数
            $scope.controlScreenShow=true;
            $scope.screenTitle='收起筛选';
            $scope.controlScreenBtn=function () { //控制筛选盒子显隐的函数
                if ($scope.controlScreenShow){
                    $scope.controlScreenShow=false;
                    $scope.screenTitle='展开筛选';
                }else {
                    $scope.controlScreenShow=true;
                    $scope.screenTitle='收起筛选';
                }
            };
        }
        $scope.controlScreen();
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
                    $scope.faceValue=info.faceValue;
                    $scope.couponStatus=info.couponStatus;
                    $scope.expiryStartDate=info.expiryStartDate;
                    $scope.expiryEndDate=info.expiryEndDate;

                    $scope.totalCount=info.totalCount;
                    $scope.receivedCount=info.receivedCount;

                    $scope.usedCount=info.usedCount;
                    $scope.discountValue=info.discountValue;
                    $scope.unReceivedCount=info.unReceivedCount;
                    $scope.unUsedCount=info.unUsedCount;

                } else {

                }

                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });

        };  //优惠券基本信息
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
                    $scope.userList = res.data.data.list;
                    $scope.showNoneDataInfoTip = false;
                    if (res.data.data.list.length > 0) {

                        if (res.data.data.totalPage > 1) { //当页面大于1页的时候才显示下面的页面选择
                            $scope.showPageHelpInfo = true;
                        } else {
                            $scope.showPageHelpInfo = false;

                        }
                    } else {
                        $rootScope.loadingState = false;
                        $scope.showNoneDataInfoTip = true;
                    }

                } else {
                    $scope.userList = [];
                    $scope.showNoneDataInfoTip = true;
                }

                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });

        };  //单条优惠券-使用用户列表
        $scope.renderList();
        $scope.refresh = function () { //刷新按钮
            $scope.renderInfo();
            $scope.renderList();
        };
        $scope.pageChanged = function () { //翻页按钮
            $scope.search();
        };
        $scope.back = function () { //返回按钮
            $state.go('coupon.list')
        };

    }
}

export default couponDetailController;


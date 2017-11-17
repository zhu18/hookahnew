/**
 * Created by Administrator on 2017/11/9 0009.
 */
class userDetailController {
    constructor($scope, $rootScope, $http, $state, growl,$stateParams) {
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
                url: $rootScope.site.apiServer + "/api/coupon/getUserCouponById",
                params: {
                    userId:$stateParams.id
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);

                if (res.data.code == '1') {
                    var info=res.data.data;
                    $scope.userId=info.userId; //优惠券类型
                    $scope.userName=info.userName; //名称
                    $scope.userSn=info.userSn; //可使用商品
                    $scope.unused=info.unused;
                    $scope.used=info.used;
                    $scope.expired=info.expired;
                    $scope.mobile=info.mobile;
                    $scope.couponName=info.couponName;
                    $scope.faceValue=info.faceValue;

                    $scope.userType=info.userType  ;

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
                    userId:$stateParams.id,
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

                        if (res.data.data.totalPage > 1) {
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

        };
        $scope.renderList();
        $scope.refresh = function () {
            $scope.renderInfo();
            $scope.renderList();
        };
        $scope.pageChanged = function () {
            $scope.renderList();
        };
        $scope.back = function () { //返回按钮
            $state.go('coupon.query')
        };

        $scope.getDetail=function (id) {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/coupon/getUserCouponDetail",
                params: {
                    userCouponId:id,
                    userId:$stateParams.id
                }
            });
            promise.then(function (res, status, config, headers) {
                if (res.data.code == '1') {
                    var info=res.data.data;
                    var couponType='';
                    var applyGoods='';
                    var applyChannel='';
                    var receivedMode='';
                    var applyPlatform='';

                    switch (info.couponType){
                        case 0:
                            couponType='注册赠券';
                            break;
                        case 1:
                            couponType='购物赠券';
                            break;
                        case 2:
                            couponType='全场赠券';
                            break;
                        case 3:
                            couponType='会员赠券';
                            break;

                    }

                    switch (info.applyGoods){
                        case 0:
                            applyGoods='全部商品';
                            break;
                        case 1:
                            applyGoods='部分商品';
                            break;
                        case 2:
                            applyGoods='部分分类';
                            break;
                    }
                    switch (info.applyChannel){
                        case 0:
                            applyChannel='无限制';
                            break;
                        case 1:
                            applyChannel='满 <span>'+info.discountValue+'</span>可用';
                            break;
                        case 2:
                            applyChannel='折扣';
                            break;
                    }

                    switch (info.receivedMode){
                        case 0:
                            receivedMode='后台赠送';
                            break;
                        case 1:
                            receivedMode='主动领取';
                            break;
                    }

                    switch (info.applyPlatform){
                        case 0:
                            applyPlatform='全平台';
                            break;
                    }
                    var html='<table class="table">' +
                        '<tr> ' +
                        '<th>优惠码</th> ' +
                        '<td> <span>'+info.couponSn+'</span> </td> ' +
                        '<th>优惠劵名称</th> ' +
                        '<td> <span>'+info.couponName+'</span> </td> ' +
                        '</tr> ' +
                        '<tr> ' +
                        '<th>优惠券类型</th> ' +
                        '<td> <span>'+couponType+'</span> </td> ' +
                        '<th>可使用商品</th> ' +
                        '<td> <span>'+applyGoods+'</span> </td> </tr> ' +
                        '<tr> <th>使用门槛</th> ' +
                        '<td> <span>'+applyChannel+'</span> </td> ' +
                        '<th>面值</th> ' +
                        '<td> <span>'+info.faceValue+'</span> </td> </tr> ' +
                        '<tr> ' +
                        '<th>适用平台</th> ' +
                        '<td> <span data-ng-bind="couponName">'+applyPlatform+'</span> </td> ' +
                        '<th>有效期</th> ' +
                        '<td> <span data-ng-bind="couponName">'+info.expiryStartDate+'至'+info.expiryEndDate+'</span> </td> </tr> ' +
                        '<tr> ' +
                        '<th>领取方式</th> ' +
                        '<td> <span data-ng-bind="couponName">'+receivedMode+'</span> </td> ' +
                        '<th>领取时间</th> ' +
                        '<td> <span data-ng-bind="couponName">'+info.receivedTime+'</span> </td> </tr> ' +
                        '<tr> ' +
                        '<th>当前状态</th> ' +
                        '<td> <span data-ng-bind="couponName">'+info.couponName+'</span> </td> ' +
                        '<th>使用时间</th> ' +
                        '<td> <span data-ng-bind="couponName">'+info.usedTime+'</span> </td> ' +
                        '</tr> ' +
                        '<tr> ' +
                        '<th>订单编号</th> ' +
                        '<td colspan="3"> <span data-ng-bind="couponName">'+info.orderSn+'</span> </td> </tr> ' +
                        '</table>';

                    var modalInstance =$rootScope.openJustShowDialogModal(html);


                } else {

                }

                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        }

    }
}

export default userDetailController;


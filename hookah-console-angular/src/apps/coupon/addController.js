/**
 * Created by lss on 2017/11/9 0009
 */
class couponController {
    constructor($scope, $rootScope, $http, $state,$stateParams,growl,$filter) {
        console.log($stateParams.id);
        var data={};
        $scope.typeStatus = [               //自定定义类型数据
            {id:0, name:"注册赠券"},
            {id:1, name:"购物赠券"},
            {id:2, name:"全场赠券"},
            {id:3, name:"会员赠券"}
        ];
        $scope.couponType=0;

        $scope.applyPlatforms = [            //自定定义类型数据
            {id:0, name:"全平台"}
        ];
        $scope.applyPlatform=0;
        $scope.applyChannel="0";
        $scope.applyGoods="0";

        if ($stateParams.id){
            console.log('修改');
            $scope.title="修改";
            $scope.getCouponById = function () { //Render page function
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
                            $scope.couponType=info.couponType;
                            $scope.couponName=info.couponName;
                            $scope.applyPlatform=info.applyPlatform;
                            $scope.totalCount=info.totalCount;
                            $scope.faceValue=(info.faceValue/100);
                            $scope.limitedCount=info.limitedCount;
                            $scope.applyChannel=info.applyChannel;
                            $scope.discountValue=(info.discountValue/100);
                            $scope.expiryStartTime=new Date(info.expiryStartDate);
                            $scope.expiryEndTime=new Date(info.expiryEndDate);
                            $scope.validDays=info.validDays;
                            $scope.applyGoods=info.applyGoods;
                    } else {
                            $rootScope.openJustShowDialogModal(res.data.message);
                    }

                    $rootScope.loadingState = false;
                    growl.addSuccessMessage("订单数据加载完毕。。。");
                });

            };
            $scope.getCouponById();
        }else {
            console.log('添加');
            $scope.title="添加";
        }
        $scope.save=function () {
            if ($stateParams.id){
                var url=$rootScope.site.apiServer + "/api/coupon/modify";
                data= {
                    id:$stateParams.id,
                    couponType:$scope.couponType,
                    couponName:$scope.couponName,
                    applyPlatform:$scope.applyPlatform,
                    totalCount:$scope.totalCount,
                    faceValue:($scope.faceValue*100),
                    limitedCount:$scope.limitedCount,
                    applyChannel:$scope.applyChannel,
                    discountValue:($scope.discountValue*100),
                    expiryStartTime:$scope.expiryStartTime,
                    expiryEndTime:$scope.expiryEndTime,
                    validDays:$scope.validDays,
                    applyGoods:$scope.applyGoods
                };
            }else {
                var url=$rootScope.site.apiServer + "/api/coupon/add";
                data= {
                    couponType:$scope.couponType,
                    couponName:$scope.couponName,
                    applyPlatform:$scope.applyPlatform,
                    totalCount:$scope.totalCount,
                    faceValue:($scope.faceValue*100),
                    limitedCount:$scope.limitedCount,
                    applyChannel:$scope.applyChannel,
                    discountValue:($scope.discountValue*100),
                    expiryStartTime:$filter('date')($scope.expiryStartTime, "yyyy-MM-dd hh:mm:ss"),
                    expiryEndTime:$filter('date')($scope.expiryEndTime, "yyyy-MM-dd hh:mm:ss"),
                    validDays:$scope.validDays,
                    applyGoods:$scope.applyGoods
                };
            }
            var promise = $http({
                method: 'post',
                url: url ,
                params:{coupon:JSON.stringify(data)}
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);

                if (res.data.code == '1') {
                    $rootScope.openJustShowDialogModal("添加成功！");
                    $state.go('coupon.list')
                } else {

                    $rootScope.openJustShowDialogModal(res.data.message);

                }

                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        };
        $scope.back = function () { //返回按钮
            $state.go('coupon.list')
        };
        // 日历插件开始
        $scope.inlineOptions = {
            customClass: getDayClass,
            minDate: new Date(),
            showWeeks: false
        };
        $scope.open1 = function () {
            $scope.popup1.opened = true;
        };
        $scope.open2 = function () {
            $scope.popup2.opened = true;
        };
        $scope.popup1 = {
            opened: false
        };
        $scope.popup2 = {
            opened: false
        };
        var tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate()+1 );
        var afterTomorrow = new Date();
        afterTomorrow.setDate(tomorrow.getDate() +1 );
        $scope.events = [
            {
                date: tomorrow,
                status: 'full'
            },
            {
                date: afterTomorrow,
                status: 'partially'
            }
        ];
        function getDayClass(data) {
            var date = data.date,
                mode = data.mode;
            if (mode === 'day') {
                var dayToCheck = new Date(date).setHours(0, 0, 0, 0);
                for (var i = 0; i < $scope.events.length; i++) {
                    var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                    if (dayToCheck === currentDay) {
                        return $scope.events[i].status;
                    }
                }
            }
            return '';
        }

        // 日历插件结束
    }
}

export default couponController;
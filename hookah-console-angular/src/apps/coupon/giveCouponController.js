/**
 * Created by lss on 2017/11/17 0017.
 */
class giveCouponController {
    constructor($scope, $rootScope, $http, $state,$stateParams,growl,$filter) {
        console.log($stateParams.id);
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
        $scope.search = function () { //Render page function
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/coupon/all",
                params: {
                    couponName: $scope.couponName,//审核状态
                    couponType: $scope.couponType,
                    type: 1,
                    currentPage: $rootScope.pagination.currentPage, //当前页码
                    pageSize: $rootScope.pagination.pageSize,
                }
            });
            promise.then(function (res) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    $scope.couponList = res.data.data.list;
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
                    $scope.couponList = [];
                    $scope.showNoneDataInfoTip = true;
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });

        }; //列表搜索
        $scope.search();
        //全选 多选 功能 开始
        $scope.selected = [];
        var updateSelected = function (action, id) {
            if (action == 'add' && $scope.selected.indexOf(id) == -1) $scope.selected.push(id);
            if (action == 'remove' && $scope.selected.indexOf(id) != -1) $scope.selected.splice($scope.selected.indexOf(id), 1);
        };
        //更新某一列数据的选择
        $scope.updateSelection = function ($event, id) {
            var checkbox = $event.target;
            var action = (checkbox.checked ? 'add' : 'remove');
            updateSelected(action, id);
        };
        //全选操作
        $scope.selectAll = function ($event) {
            var checkbox = $event.target;
            var action = (checkbox.checked ? 'add' : 'remove');
            for (var i = 0; i < $scope.couponList.length; i++) {
                var contact = $scope.couponList[i];
                updateSelected(action, contact.id);
            }
        };
        $scope.isSelected = function (id) {
            return $scope.selected.indexOf(id) >= 0;
        };
        $scope.isSelectedAll = function () {
            return $rootScope.pagination.pageSize == $scope.couponList.length;
        };
        //全选 多选 功能 结束
        $scope.save=function () {

            var url=$rootScope.site.apiServer + "/api/coupon/sendCouponToUser";
            var data= {
                userId:$stateParams.id,
                couponIds:$scope.selected,
            };

            var promise = $http({
                method: 'post',
                url: url ,
                params:{coupon:JSON.stringify(data)}
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);

                if (res.data.code == '1') {

                    var modalInstance =$rootScope.openConfirmDialogModal("赠送成功！");
                    modalInstance.result.then(function () {
                        $state.go('coupon.query')
                    }, function () {

                    });
                } else {
                    var modalInstance =$rootScope.openJustShowDialogModal(res.data.message);
                    modalInstance.result.then(function () {

                    }, function () {

                    });

                }

                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        }
        $scope.pageChanged = function () {
            $scope.search();
        };
        $scope.back = function () { //返回按钮
            $state.go('coupon.query')
        };
    }
}

export default giveCouponController;
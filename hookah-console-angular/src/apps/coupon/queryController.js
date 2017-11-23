/**
 * Created by Administrator on 2017/11/9 0009.
 */
class queryController {
    constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
        $scope.pageSizes = [               //自定定义类型数据
            {id:'20', name:"20"},
            {id:'50', name:"50"}
        ];
        $rootScope.pagination.pageSize="20";
        $scope.exports = [               //自定定义类型数据
            {id:"", name:"导出数据"},
            {id:0, name:"选中用户",href:"1"},
            {id:1, name:"全部用户",href:"2"}
        ];
        $scope.export="";
        $scope.pageSize=1;
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
        $scope.controlScreen()
        $scope.search = function () { //Render page function
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/coupon/getUserCouponList",
                params: {
                    userName: $scope.userName,
                    userSn: $scope.userSn,
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
        $scope.delete=function (id) {
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/api/coupon/delete",
                params: {
                    couponId:id
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);

                if (res.data.code == '1') {
                    var modalInstance =$rootScope.openConfirmDialogModal("删除成功！");
                    modalInstance.result.then(function () {
                        $scope.search();
                    }, function () {

                    });
                } else {
                    var modalInstance =$rootScope.openConfirmDialogModal("删除失败！");
                    modalInstance.result.then(function () {

                    }, function () {

                    });
                }

                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        }

        $scope.search();
        $scope.refresh = function () {
            $scope.search();
        };
        $scope.pageChanged = function () {
            $scope.search();
        };
        $scope.getDetail = function (id) {//Go to detail page
            $state.go('coupon.userDetail',{id:id})
        };
        $scope.giveCoupon = function (id) {//Go to detail page
            $state.go('coupon.giveCoupon',{id:id})
        };

    }
}

export default queryController;

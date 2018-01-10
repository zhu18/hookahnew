/**
 * Created by lss on 2017/9/19 0019.
 */
class invoiceListController {
    constructor($scope, $rootScope, $http, $state, $stateParams, growl) {
        $scope.buyerAccountType=[
            {id:-1, name:"全部"},
            {id:2, name:"个人"},
            {id:4, name:"企业"}
        ];
        $scope.userType=-1;
        $scope.invoiceStates=[
            {id:-1, name:"全部"},
            {id:4, name:"已开票"},
            {id:1, name:"待审核"},
            {id:2, name:"待邮寄"}
        ];
        $scope.invoiceStatus=-1;
        $scope.invoiceType=[
            {id:-1, name:"全部"},
            {id:0, name:"普通发票"},
            {id:1, name:"增值税专用发票"}
        ];
        $scope.invoiceType=-1;
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
                url: $rootScope.site.apiServer + "/api/invoice/back/all",
                params: {
                    userName: $scope.userName,//审核状态
                    userType: $scope.userType,
                    invoiceStatus: $scope.invoiceStatus,
                    invoiceType: $scope.invoiceType,
                    currentPage: $rootScope.pagination.currentPage, //当前页码
                    pageSize: $rootScope.pagination.pageSize,
                }
            });
            promise.then(function (res) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    $scope.invoiceList = res.data.data.list;
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
                    $scope.invoiceList = [];
                    $scope.showNoneDataInfoTip = true;
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });

        }; //列表搜索
        $scope.search()
    }
}

export default invoiceListController;
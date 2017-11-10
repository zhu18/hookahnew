/**
 * Created by Administrator on 2017/11/9 0009.
 */
class detailController {
    constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
        $scope.search = function (initCurrentPage) {
            console.log($scope.levelStar);
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/supplier/all",
                params: {
                    checkStatus: $scope.checkStatus == 0 ? '0' : ($scope.checkStatus ? $scope.checkStatus : null),//审核状态
                    contactPhone: $scope.contactPhone ? $scope.contactPhone : null,
                    orgName: $scope.orgName ? $scope.orgName : null,
                    startDate: $scope.startDate ? format($scope.startDate, 'yyyy-MM-dd HH:mm:ss') : null,
                    endDate: $scope.endDate ? format($scope.endDate, 'yyyy-MM-dd HH:mm:ss') : null,
                    currentPage: initCurrentPage == 'true' ? 1 :$rootScope.pagination.currentPage, //当前页码
                    pageSize: $rootScope.pagination.pageSize,
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);

                if (res.data.code == '1') {
                    $scope.supplierList = res.data.data.list;
                    // $rootScope.pagination = res.data.data;
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
                    $scope.supplierList = [];
                    $scope.showNoneDataInfoTip = true;

                }

                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });

        };
        $scope.search('true');
        $scope.refresh = function () {
            $scope.search();
        };
        $scope.pageChanged = function () {
            $scope.search();
        };
        $scope.remark = function (item) {
            console.log(item.id);
            console.log(item.checkStatus);
            var promise = null;
            if (item.checkStatus == "0") {
                var modalInstance = null;
                modalInstance = $rootScope.openConfirmDialogModalSupplier();
                modalInstance.result.then(function () { //模态点提交
                    var promise = null;
                    promise = $http({
                        method: 'POST',
                        url: $rootScope.site.apiServer + "/api/supplier/updateInfo",
                        params: {
                            id: item.id,
                            checkContent: $('#checkContent').val(),
                            checkStatus: $('input:radio[name="tRadio"]:checked').val()
                        }
                    });
                    promise.then(function (res, status, config, headers) {
                        $rootScope.loadingState = false;
                        if (res.data.code == 1) {
                            growl.addSuccessMessage("保存成功。。。");
                            $scope.search();
                        } else {
                            growl.addErrorMessage("保存失败。。。");
                        }

                    });
                }, function () {
                });
            } else {
                promise = $http({
                    method: 'get',
                    url: $rootScope.site.apiServer + "/api/supplier/viewResult",
                    params: {
                        id: item.id
                    }
                });
                promise.then(function (res, status, config, headers) {
                    $rootScope.loadingState = false;
                    var tempVal = '审核意见：' + res.data.data.checkContent + '<br><br>审核结果：';

                    if (res.data.code == 1) {
                        if (res.data.data.checkStatus == 1) {
                            tempVal += '审核通过';
                        } else if (res.data.data.checkStatus == 2) {
                            tempVal += '审核不通过';
                        }
                        $rootScope.openJustShowDialogModal(tempVal);
                    } else {
                    }
                });
            }
        };
    }
}

export default detailController;


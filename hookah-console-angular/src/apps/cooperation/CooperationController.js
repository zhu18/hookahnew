class CooperationController {
    constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
        $scope.search = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/coo/search",
                params: {currentPage: $rootScope.pagination.currentPage,
                    pageSize: $rootScope.pagination.pageSize,
                    cooName: $scope.cooName
                }
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };
        $scope.load = function (event, cooperation) {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/coo/" + cooperation.cooperationId,
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.cuserd=res.data.data;
            });
        };
        $scope.add = function () {
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/coo/add",
                data: $("#newCooperation").serialize()
            });
            promise.then(function (res, status, config, headers) {
                if (res.data.code == "1"){
                    alert(res.data.data);
                    $state.go('cooperation.search');
                }else{
                    alert(res.data.message);
                }
            });

        };
        $scope.edit = function () {
            var f = $scope.checkNum();
            if (f) {
                var promise = $http({
                    method: 'POST',
                    url: $rootScope.site.apiServer + "/coo/modify",
                    params: {
                        cooperationId: $("#cooperationId").val(),
                        cooPhone: $("#cooPhone").val(),
                        cooAddress: $("#cooAddress").val(),
                        url: $("#url").val(),
                        pictureUrl: $("#pictureUrl").val(),
                        cooName: $("#cooName").val(),
                        cooOrder: $("#orderBy").val()
                    }
                });
                promise.then(function (res, status, config, headers) {
                    if (res.data.code == "1") {
                        alert(res.data.data);
                        $state.go('cooperation.search');
                    } else {
                        alert(res.data.message);
                    }
                });
            }
        };
        $scope.updateStatus = function (cooperation, flag) {
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/coo/updateState",
                params: {
                    cooperationId:cooperation.cooperationId,
                    state:flag
                }
            });
            promise.then(function (res, status, config, headers) {
                if (res.data.code == "1"){
                    $scope.search();
                }else {
                    alert(res.data.message);
                }
            });
        };
        $scope.delete = function (event, cooperation) {
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/coo/delete",
                params: {
                    cooperationId: cooperation.cooperationId,
                    cooName: cooperation.cooName
                }
            });

            promise.then(function (res, status, config, headers) {
                alert(res.data.data);
                $scope.search();
            });
        };
        $scope.checkNum = function () {
            var num = $("#orderBy").val();
            if(num){
                if (isNaN(num)){
                    alert("您输入的不是数字");
                    return false;
                }else{
                    if (num.length > 3){
                        alert("显示顺序大于3位请重新输入");
                        return false;
                    }else {
                        return true;
                    }
                }
            }else {
                alert("请输入显示顺序");
                return false;
            }
        };
        // $scope.checkPhoneNum = function () {
        //     var num = $("#cooPhone").val();
        //     var reg = /^1[34578]\d{9}$/;
        //     if (num){
        //         if(!reg.test(num)){
        //             alert("您输入的号码不正确");
        //         }
        //     }else {
        //         alert("请输入手机号")
        //     }
        // };
        $scope.pageChanged = function () {
            $scope.search();
        };
        $scope.refresh = function () {
            $scope.search();
        };
        $scope.search();
    }
}

export default CooperationController;
class MessageController {
    constructor($scope, $rootScope, $http, $state, $stateParams, $uibModal, usSpinnerService, growl) {

        $scope.systemSearch = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/message/system/all",
                params: {currentPage: $rootScope.pagination.currentPage,
                    pageSize: $rootScope.pagination.pageSize,
                    goodsName: $scope.searchName,
                    isRead: $scope.messageIsRead,
                    keywords: $scope.keywords,
                    eventType: $scope.eventType,
                    startData: $scope.searchCheckStatus,
                    endData: $scope.searchOnSaleStatus
                }
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                console.log($rootScope.pagination);
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        }

        $scope.emailSearch = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/message/email/all",
                params: {currentPage: $rootScope.pagination.currentPage,
                    pageSize: $rootScope.pagination.pageSize,
                    goodsName: $scope.searchName,
                    isRead: $scope.messageIsRead,
                    keywords: $scope.keywords,
                    eventType: $scope.eventType,
                    startData: $scope.searchCheckStatus,
                    endData: $scope.searchOnSaleStatus
                }
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                console.log($rootScope.pagination);
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        }

        $scope.smsSearch = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/message/sms/all",
                params: {currentPage: $rootScope.pagination.currentPage,
                    pageSize: $rootScope.pagination.pageSize,
                    goodsName: $scope.searchName,
                    isRead: $scope.messageIsRead,
                    keywords: $scope.keywords,
                    eventType: $scope.eventType,
                    startData: $scope.searchCheckStatus,
                    endData: $scope.searchOnSaleStatus
                }
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                console.log($rootScope.pagination);
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        }

        if ($state.$current.name == "message.system.search") {
            //消息是否已读
            $scope.messageIsReads = [{id: -1, name: "全部"}, {id: 0, name: "未读"}, {id: 1, name: "已读"}];
            $scope.messageIsRead = -1;

            //获取消息事件类型列表
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/api/message/eventType/all",
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                console.log("获取数据:" + res.data);
                if (res.data.code == 1) {
                    console.log(res.data.data);
                    $scope.eventTypes = res.data.data;
                    // $scope.eventType = $scope.eventTypes[0].code;
                    $scope.eventType = "-1";
                }
            });

            $scope.systemSearch();
        }

        if($state.$current.name == "message.email.search"){
            //消息是否已读
            $scope.messageIsReads = [{id: -1, name: "全部"}, {id: 0, name: "未读"}, {id: 1, name: "已读"}];
            $scope.messageIsRead = -1;

            //获取消息事件类型列表
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/api/message/eventType/all",
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                console.log("获取数据:" + res.data);
                if (res.data.code == 1) {
                    console.log(res.data.data);
                    $scope.eventTypes = res.data.data;
                    // $scope.eventType = $scope.eventTypes[0].code;
                    $scope.eventType = "-1";
                }
            });

            $scope.emailSearch();

        }



        if($state.$current.name == "message.sms.search"){
            //消息是否已读
            $scope.messageIsReads = [{id: -1, name: "全部"}, {id: 0, name: "未读"}, {id: 1, name: "已读"}];
            $scope.messageIsRead = -1;

            //获取消息事件类型列表
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/api/message/eventType/all",
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                console.log("获取数据:" + res.data);
                if (res.data.code == 1) {
                    console.log(res.data.data);
                    $scope.eventTypes = res.data.data;
                    // $scope.eventType = $scope.eventTypes[0].code;
                    $scope.eventType = "-1";
                }
            });

            $scope.smsSearch();
        }
    }
}

export default MessageController;
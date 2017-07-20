class MessageController {
    constructor($scope, $rootScope, $http, $state, $stateParams, $uibModal, usSpinnerService, growl) {
		$scope.len = 0;
		$scope.content = '';
        $scope.systemSearch = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/message/system/all",
                params: {pageNumber: $rootScope.pagination.currentPage,
                    pageSize: $rootScope.pagination.pageSize,
                    isRead: $scope.messageIsRead,
                    keywords: $scope.keywords,
                    eventType: $scope.eventType,
                    receiveUserName: $scope.receiveUserName,
                    startTime: $scope.startDate?format($scope.startDate, 'yyyy-MM-dd HH:mm:ss'):null,
                    endTime: $scope.endDate?format($scope.endDate, 'yyyy-MM-dd HH:mm:ss'):null
                }
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                console.log($rootScope.pagination);
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };

        $scope.emailSearch = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/message/email/all",
                params: {pageNumber: $rootScope.pagination.currentPage,
                    pageSize: $rootScope.pagination.pageSize,
                    isSuccess: $scope.messageIsSuccess,
                    receiveAddr:$scope.receiveAddr,
                    keywords: $scope.keywords,
                    eventType: $scope.eventType,
                    startTime: $scope.startDate?format($scope.startDate, 'yyyy-MM-dd HH:mm:ss'):null,
                    endTime: $scope.endDate?format($scope.endDate, 'yyyy-MM-dd HH:mm:ss'):null
                }
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                console.log($rootScope.pagination);
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };

        $scope.smsSearch = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/message/sms/all",
                params: {pageNumber: $rootScope.pagination.currentPage,
                    pageSize: $rootScope.pagination.pageSize,
                    receiveAddr:$scope.receiveAddr,
                    isSuccess: $scope.messageIsSuccess,
                    keywords: $scope.keywords,
                    eventType: $scope.eventType,
                    startTime: $scope.startDate?format($scope.startDate, 'yyyy-MM-dd HH:mm:ss'):null,
                    endTime: $scope.endDate?format($scope.endDate, 'yyyy-MM-dd HH:mm:ss'):null
                }
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                console.log($rootScope.pagination);
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };

        $scope.templateSearch = function () {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/message/template/all",
                params: {pageNumber: $rootScope.pagination.currentPage,
                    pageSize: $rootScope.pagination.pageSize,
                    goodsName: $scope.searchName,
                    isVaild: $scope.vaild,
                    keywords: $scope.keywords,
                    eventType: $scope.eventType,
                    templateType:$scope.templateType,
                    startTime: $scope.startDate?format($scope.startDate, 'yyyy-MM-dd HH:mm:ss'):null,
                    endTime: $scope.endDate?format($scope.endDate, 'yyyy-MM-dd HH:mm:ss'):null
                }
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                console.log($rootScope.pagination);
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };

        $scope.getInfo=function(){
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/message/constants/all",
                params:{}
            });
            promise.then(function (res, status, config, headers){
                $rootScope.loadingState = false;
                $rootScope.list = res.data.data;
                console.log(res.data.data);
                $scope.len = res.data.data.length;
                console.log("len:"+res.data.data.length);
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };

        $scope.add = function(){
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/api/message/template/add",
                data: $("#infoForm").serialize()
            });
            promise.then(function (res, status, config, headers){
                $rootScope.loadingState = false;
                console.log(res.data.data);
                growl.addSuccessMessage("数据加载完毕。。。");
            })
        };

        $scope.stopOrOpenTemplate = function(){
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/api/message/template/stopOrOpen",
                data: {tempId: item.id}
            });
            promise.then(function (res, status, config, headers){
                $rootScope.loadingState = false;
                console.log(res.data.data);
                growl.addSuccessMessage("数据加载完毕。。。");
            })
        };

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
                    $scope.eventTypes.unshift({"code":"-1","describle":"全部"});
                    // $scope.eventType = $scope.eventTypes[0].code;
                    $scope.eventType = "-1";
                }
            });

            $scope.systemSearch();
        }

        if ($state.$current.name == "message.template.add") {

            $scope.getInfo();

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
                    // $scope.eventTypes.unshift({"code":"-1","describle":"全部"});
                    $scope.eventType = $scope.eventTypes[0].code;
                    // $scope.eventType = "-1";
                }
            });


            $scope.flag=false;
            // $scope.$watch('content',function(newVal,oldVal){
            //     // console.log("typeof:"+typeof newVal);
            //     // console.log("new:"+newVal.charAt(newVal.length-1));
            //     if(newVal.charAt(newVal.length-1) == '['){
            //         $scope.getInfo();
            //         $scope.flag=true;
            //     }else{
            //         $scope.flag=false;
            //     }
            // })
        }
		var ds = 0;
        $scope.keyDownFn = function(e){
            var lenth = $scope.len;
            var conTxt = '';
			var keycode = window.event?e.keyCode:e.which;
			if(keycode == 219){
				$scope.getInfo();
				$scope.flag=true;
				conTxt = $scope.content;
            }
            if(keycode == 40){
				ds += 1;
				if (ds > lenth) {
					ds = lenth;
				}
				$('.tipBox li').eq(ds).addClass('active').siblings().removeClass('active')
            }
			if(keycode == 38){
				ds -= 1;
				if (ds <= 0) {
					ds = 0;
				}
				$('.tipBox li').eq(ds).addClass('active').siblings().removeClass('active')
			}
			if(keycode == 13){
			    $('#text').blur();
				$('.tipBox li').each(function () {
					if ($(this).hasClass('active')) {
						$scope.content += $(this).attr('d-code')+']';

					}
				});
				$('#text').focus();
				$scope.flag=false;
				ds = 0;
            }
        };


        if($state.$current.name == "message.email.search"){
            //消息是否成功
            $scope.messageIsSuccessList = [{id: -1, name: "全部"}, {id: 0, name: "失败"}, {id: 1, name: "成功"}];
            $scope.messageIsSuccess = -1;

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
                    $scope.eventTypes.unshift({"code":"-1","describle":"全部"});
                    // $scope.eventType = $scope.eventTypes[0].code;
                    $scope.eventType = "-1";
                }
            });

            $scope.emailSearch();

        }



        if($state.$current.name == "message.sms.search"){
            //消息是否成功
            $scope.messageIsSuccessList = [{id: -1, name: "全部"}, {id: 0, name: "失败"}, {id: 1, name: "成功"}];
            $scope.messageIsSuccess = -1;

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
                    $scope.eventTypes.unshift({"code":"-1","describle":"全部"});
                    // $scope.eventType = $scope.eventTypes[0].code;
                    $scope.eventType = "-1";
                }
            });

            $scope.smsSearch();
        }

        if($state.$current.name == "message.template.search"){
            //消息是否已读
            $scope.vailds = [{id: -1, name: "全部"}, {id: 0, name: "停用"}, {id: 1, name: "启用"}];
            $scope.vaild = -1;

            //模板类型列表
            $scope.templateTypes = [{id: 0, name: "短信"}, {id: 1, name: "邮件"},{id: 2, name: "站内信"}];
            $scope.templateTypes.unshift({id: -1, name: "全部"});
            $scope.templateType = -1;

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
                    $scope.eventTypes.unshift({"code":"-1","describle":"全部"});
                    $scope.eventType = "-1";
                }
            });

            $scope.templateSearch();
        }

        //获取消息事件类型列表
        $scope.getEventTypeSelect = function () {
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
                    $scope.eventTypes.unshift({"code":"-1","describle":"全部"});
                    $scope.eventType = "-1";
                }
            });
        }

        $scope.pageChanged = function () {
            if($state.$current.name == "message.sms.search"){
                $scope.smsSearch();
            }else if($state.$current.name == "message.email.search"){
                $scope.emailSearch();
            }else if($state.$current.name == "message.system.search"){
                $scope.systemSearch();
            }else if($state.$current.name == "message.template.search"){
                $scope.templateSearch();
            }
            console.log('Page changed to: ' + $rootScope.pagination.currentPage);
        };

        $scope.refresh = function(){
            if($state.$current.name == "message.sms.search"){
                $scope.smsSearch();
            }else if($state.$current.name == "message.email.search"){
                $scope.emailSearch();
            }else if($state.$current.name == "message.system.search"){
                $scope.systemSearch();
            }else if($state.$current.name == "message.template.search"){
                $scope.templateSearch();
            }
        }


        // 处理日期插件的获取日期的格式
        var format = function(time, format)

        {
            var t = new Date(time);
            var tf = function(i){return (i < 10 ? '0' : "") + i};
            return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
                switch(a){
                    case 'yyyy':
                        return tf(t.getFullYear());
                        break;
                    case 'MM':
                        return tf(t.getMonth() + 1);
                        break;
                    case 'mm':
                        return tf(t.getMinutes());
                        break;
                    case 'dd':
                        return tf(t.getDate());
                        break;
                    case 'HH':
                        return tf(t.getHours());
                        break;
                    case 'ss':
                        return tf(t.getSeconds());
                        break;
                }
            })
        }
        // 日历插件开始
        $scope.inlineOptions = {
            customClass: getDayClass,
            minDate:new Date(2000, 5, 22),
            showWeeks: true
        };
        $scope.open1 = function() {
            $scope.popup1.opened = true;
        };
        $scope.open2 = function() {
            $scope.popup2.opened = true;
        };
        $scope.popup1 = {
            opened: false
        };
        $scope.popup2 = {
            opened: false
        };
        var tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        var afterTomorrow = new Date();
        afterTomorrow.setDate(tomorrow.getDate() + 1);
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
                var dayToCheck = new Date(date).setHours(0,0,0,0);

                for (var i = 0; i < $scope.events.length; i++) {
                    var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

                    if (dayToCheck === currentDay) {
                        return $scope.events[i].status;
                    }
                }
            }

            return '';
        }

    }
}

export default MessageController;
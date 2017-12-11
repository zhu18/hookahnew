class OperateController {
  constructor($scope, $rootScope, $http, $stateParams, $uibModal, usSpinnerService, growl) {
    $scope.title = "运营流量统计分析";
    $scope.search = function () {
      if ($scope.startDate !== null && $scope.endDate !== null && ($scope.startDate > $scope.endDate)) {
        alert('开始时间必须大于结束时间！请重新选择日期。');
        return;
      }
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/tongji/reqUser",
        params: {
          startTime: $scope.startDate ? format($scope.startDate, 'yyyy-MM-dd HH:mm:ss') : null,
          endTime: $scope.endDate ? format($scope.endDate, 'yyyy-MM-dd HH:mm:ss') : null,
        }
      });
      promise.then(function (res, status, config, headers) {
        console.log('数据在这里');
        console.log(res.data.data);
        if(res.data.data !== null){
          if (res.data.data.length > 0) {
            $rootScope.showNoneDataInfoTip=false;
            $rootScope.loadingState = false;
            $scope.operateList = res.data.data;

          }
        }else{
          $scope.operateList=[];
          $rootScope.loadingState = false;
          $rootScope.showNoneDataInfoTip=true;
        }


        growl.addSuccessMessage("订单数据加载完毕。。。");
      });
    };

    //后退

    $scope.pageChanged = function () {
      $scope.search();
    };
    $scope.refresh = function () {
      $scope.search();
    };
    $scope.search('true');

    // 处理日期插件的获取日期的格式
    var format = function (time, format) {
      var t = new Date(time);
      var tf = function (i) {
        return (i < 10 ? '0' : "") + i
      };
      return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function (a) {
        switch (a) {
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
    $scope.startDateOptions = {
      customClass: getDayClass,
      // minDate: new Date(2000, 5, 22),
      maxDate: new Date(),
      // showWeeks: true
    };
    $scope.endDateOptions = {
      // dateDisabled: disabled,
      // formatYear: 'yy',
      maxDate: new Date(),
      // minDate: new Date(),
      // startingDay: 1
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

    $scope.currentIndex = null;//初始化日历插件默认选择项
    $scope.setDate = function (dataFormat, number, aIndex) {

      var now = new Date();
      var date = new Date(now.getTime() - 1);
      var year = date.getFullYear();
      var month = date.getMonth();
      var day = date.getDate();
      if (dataFormat == 'day') {
        day -= number;
      } else if (dataFormat == 'week') {
        day -= number * 7;
      } else if (dataFormat == 'month') {
        month -= number;
      } else if (dataFormat == 'year') {
        year -= number;
      }
      $scope.startDate = new Date(year, month, day);
      $scope.endDate = new Date();
      $scope.currentIndex = aIndex;
    }
    // 日历插件结束
  }
}

export default OperateController;
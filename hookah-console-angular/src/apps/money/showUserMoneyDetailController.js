/**
 * Created by lss on 2017/7/3 0003.
 */
class showUserMoneyDetailController {
  constructor($scope, $rootScope, $state, $http, $stateParams, growl) {
    $scope.userBaseInfo=$stateParams.item;

    $scope.search = function () {

      if ($scope.startDate !== null && $scope.endDate !== null && ($scope.startDate > $scope.endDate)) {
        //继续
        alert('开始时间必须大于结束时间！请重新选择日期。');
        return;
      }


      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/userFund/userFundDetail",
        params: {
          tradeType : $scope.tradeType  ? $scope.tradeType  : null,
          tradeStatus: $scope.tradeStatus == 0 ? '0' : ($scope.tradeStatus ? $scope.tradeStatus : null),//审核状态
          startDate: $scope.startDate ? format($scope.startDate, 'yyyy-MM-dd HH:mm:ss') : null,
          endDate: $scope.endDate ? format($scope.endDate, 'yyyy-MM-dd HH:mm:ss') : null,
          currentPage: $rootScope.pagination.currentPage, //当前页码
          pageSize: $rootScope.pagination.pageSize
        }
      });
      promise.then(function (res, status, config, headers) {
        console.log(res);
        if (res.data.code == '1') {
          $scope.userMoneyList = res.data.data.list;
          $scope.showNoneDataInfoTip = false;
          if (res.data.data.totalPage > 1) {
            $scope.showPageHelpInfo = true;
          }
        } else {
          $scope.userMoneyList = [];
          $scope.showNoneDataInfoTip = true;
        }
      });
    };
    $scope.pageChanged = function () {
      $scope.search();
      console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };


    $scope.search();
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
      minDate: new Date(2000, 5, 22),
      maxDate: new Date(),
      showWeeks: true
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

    $scope.setDate = function (dataFormat, number) {
      var now = new Date();
      var date = new Date(now.getTime() - 1);
      var year = date.getFullYear();
      var month = date.getMonth() ;
      var day = date.getDate();
      if (dataFormat == 'day') {
        day -= number;
      } else if (dataFormat == 'week') {
        day -=  number * 7;
      } else if (dataFormat == 'month') {
        month -= number;
      } else if (dataFormat == 'year') {
        year -= number;
      }

      $scope.startDate = new Date(year, month, day);
      $scope.endDate = new Date();

    }
    // 日历插件结束
    $scope.back = function () {
      history.back();
    };
  }
}
export default showUserMoneyDetailController;
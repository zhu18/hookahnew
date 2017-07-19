/**
 * Created by lss on 2017/7/3 0003.
 */
class showUserMoneyDetailController {
  constructor($scope, $rootScope, $state, $http, $stateParams, growl) {
    $scope.userBaseInfo=$stateParams.item;

    $scope.search = function () {
/*      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/withdrawRecord/getOneById",
        params: {
          id: $stateParams.id
        }
      });
      promise.then(function (res, status, config, headers) {
        console.log(res);
        if (res.data.code == '1') {
          $scope.order = res.data.data;
          $scope.showNoneDataInfoTip = false;
          if (res.data.data.totalPage > 1) {
            $scope.showPageHelpInfo = true;
          }
        } else {
          $scope.settleList = [];
          $scope.showNoneDataInfoTip = true;
        }
      });*/
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
    $scope.inlineOptions = {
      customClass: getDayClass,
      minDate: new Date(2000, 5, 22),
      showWeeks: true
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

    // 日历插件结束
    $scope.back = function () {
      history.back();
    };
  }
}
export default showUserMoneyDetailController;
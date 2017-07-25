class transactionManageController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
    $scope.settleList = [];
    $scope.choseArr = [];//多选数组

    $scope.search = function () {
      // console.log($scope.levelStar);
      if ($scope.startDate !== null && $scope.endDate !== null && ($scope.startDate > $scope.endDate)) {
        //继续
        alert('开始时间必须大于结束时间！请重新选择日期。');
        return;
      }
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/settleOrder/getTradeList",
        params: {
          goodsName: $scope.goodsName ? $scope.goodsName : null,
          orderSn: $scope.orderSn ? $scope.orderSn : null,
          addUser  : $scope.addUser   ? $scope.addUser : null,
          startDate: $scope.startDate ? format($scope.startDate, 'yyyy-MM-dd HH:mm:ss') : null,
          endDate: $scope.endDate ? format($scope.endDate, 'yyyy-MM-dd HH:mm:ss') : null,
          currentPage: $rootScope.pagination.currentPage, //当前页码
          pageSize: $rootScope.pagination.pageSize
        }
      });
      promise.then(function (res, status, config, headers) {
        console.log('数据在这里');
        console.log(res);
        if (res.data.code == '1') {
          $scope.tradeList = res.data.data.list;
          // $rootScope.pagination = res.data.data;
          $scope.showNoneDataInfoTip = false;
          if (res.data.data.totalPage > 1) {
            $scope.showPageHelpInfo = true;
          }
        } else {
          $scope.tradeList = [];
          $scope.showNoneDataInfoTip = true;

        }
        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });
    };

    $scope.getDetails = function (id) {
      $state.go('settle.settleDetails', {id: id});
    };



    $scope.pageChanged = function () {
      $scope.search();
      console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };
    $scope.MultipleCheck = function (status) {
      if ($scope.choseArr.length > 0) {
        $scope.settleCheck($scope.choseArr.join(), status)
        console.log($scope.choseArr.join())
      } else {
        alert('请选择多个订单！')
      }
    };
    $scope.settleCheck = function (orderSn, status) {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/settle/checksettles",
        params: {
          settleIds: orderSn,
          status: status
        }
      });
      promise.then(function (res, status, config, headers) {
        console.log('数据在这里');
        console.log(res);

        if (res.data.code == '1') {
          $scope.search();

        } else {

        }

        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });
    }



    $scope.refresh = function () {
      $scope.search();
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
    $scope.setDate = function (dataFormat, number) {
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

    }
    // 日历插件结束
  }
}

export default transactionManageController;
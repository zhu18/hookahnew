class CommentController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
    $scope.search = function () {
      console.log($scope.levelStar);
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/supplier/all",
        params: {
          checkStatus: $scope.checkStatus ? $scope.checkStatus : null,//审核状态
          contactPhone: $scope.contactPhone ? $scope.contactPhone : null,
          orgName: $scope.orgName ? $scope.orgName : null,
          startDate: $scope.startDate ? format($scope.startDate, 'yyyy-MM-dd HH:mm:ss') : null,
          endDate: $scope.endDate ? format($scope.endDate, 'yyyy-MM-dd HH:mm:ss') : null,
          currentPage: $rootScope.pagination.currentPage, //当前页码
          pageSize: $rootScope.pagination.pageSize,
        }
      });
      promise.then(function (res, status, config, headers) {
        console.log('数据在这里');
        console.log(res);

        if (res.data.code == '1') {
          $scope.supplierList = res.data.data.list;
          $rootScope.pagination = res.data.data;
          $scope.showNoneDataInfoTip = false;
          if (res.data.data.totalPage > 1) {
            $scope.showPageHelpInfo = true;
          } else {
            $scope.showPageHelpInfo = false;
          }
        } else {
          $scope.supplierList = [];
          $scope.showNoneDataInfoTip = true;

        }

        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });

    };
    $scope.search();
    $scope.refresh = function () {
      $scope.search();
    };
    $scope.pageChanged = function () {
        $scope.search();
    };
    $scope.remark = function (id) {
        var modalInstance=null;
        modalInstance = $rootScope.openConfirmDialogModalSupplier();
        modalInstance.result.then(function () { //模态点提交
              var promise = null;
              promise = $http({
                  method: 'POST',
                  url: $rootScope.site.apiServer + "/api/supplier/updateInfo",
                  params: {
                      id:id,
                      checkContent:$('#checkContent').val(),
                      checkStatus:$('input:radio[name="tRadio"]:checked').val()
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

      };
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
  }
}

export default CommentController;
class CommentController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
    $scope.commentList = [];
    $scope.choseArr = [];//多选数组

    $scope.search = function () {
      console.log($scope.levelStar);
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/comment/findCommentsByCondition",
        params: {
          orderSn: $scope.orderSn ? $scope.orderSn : null,
          goodsName: $scope.goodsName ? $scope.goodsName : null,
          startTime: $scope.startDate ? format($scope.startDate, 'yyyy-MM-dd HH:mm:ss') : null,
          endTime: $scope.endDate ? format($scope.endDate, 'yyyy-MM-dd HH:mm:ss') : null,
          commentContent: $scope.commentContent ? $scope.commentContent : null,//评价关键字
          goodsCommentGrade: $scope.goodsCommentGrade ? $scope.goodsCommentGrade : null,//评分等级
          status: $scope.status == 0 ? '0' : ($scope.status ? $scope.status : null),//审核状态
          pageNumber: $rootScope.pagination.currentPage, //当前页码
          pageSize: $rootScope.pagination.pageSize
        }
      });
      promise.then(function (res, status, config, headers) {
        console.log('数据在这里');
        console.log(res);

        if (res.data.code == '1') {
          $scope.commentList = res.data.data.list;
          $rootScope.pagination = res.data.data;
          $scope.showNoneDataInfoTip = false;
          if (res.data.data.totalPage > 1) {
            $scope.showPageHelpInfo = true;
          }

        } else {
          $scope.commentList = [];
          $scope.showNoneDataInfoTip = true;

        }

        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });

    };
    $scope.pageChanged = function () {
      $scope.search();
      console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };
    $scope.MultipleCheck = function (status) {
      if ($scope.choseArr.length > 0) {
        $scope.commentCheck($scope.choseArr.join(), status)
        console.log($scope.choseArr.join())
      } else {
        alert('请选择多个订单！')
      }
    };
    $scope.commentCheck = function (orderSn, status) {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/comment/checkComments",
        params: {
          commentIds: orderSn,
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

    //多选
    var str = "";
    var len = $scope.commentList.length;
    var flag = '';//是否点击了全选，是为a
    $scope.x = false;//默认未选中

    $scope.all = function (c) { //全选
      var commIdArr = [];

      angular.forEach($scope.commentList, function (value, key) {

        if (value.status == 0) {
          commIdArr.push(value.commId)
        }

      });
      console.log(commIdArr);

      if (c == true) {
        $scope.x = true;
        $scope.choseArr = commIdArr;
        flag = 'a';
      } else {
        $scope.x = false;
        $scope.choseArr = [];
        flag = 'b';
      }
    };

    $scope.chk = function (z, x) { //单选或者多选


      if (x == true) {//选中
        $scope.choseArr.push(z);
        flag = 'c'
        if ($scope.choseArr.length == len) {
          $scope.master = true
        }
      } else {
        $scope.choseArr.splice($scope.choseArr.indexOf(z), 1);//取消选中
      }

      if ($scope.choseArr.length == 0) {
        $scope.master = false
      }
    };
    //多选结束

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
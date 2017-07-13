class CommentController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
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
          status: $scope.status ? $scope.status : null,//审核状态
          pageNumber: $rootScope.pagination.currentPage, //当前页码
          pageSize: $rootScope.pagination.pageSize
        }
      });
      promise.then(function (res, status, config, headers) {
        console.log('数据在这里');
        console.log(res);

        if (res.data.code !== '0') {
          $scope.commentList = res.data.data.list;
        }else{
          $scope.commentList = [];
          $scope.showNoneDataInfoTip = true;

        }

        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });

    };


    $scope.refresh = function () {
      $scope.search();
    };

    if ($state.current.name == "comment.review") {
      $scope.search();
      $scope.expanding_property = "name";
      $scope.col_defs = [
        {
          field: "helpId",
          displayName: "属性"
        }
        , {
          field: "helpUrl",
          displayName: "连接",
          cellTemplate: "<a target='_blank' href='" + $rootScope.site.websiteServer + "{{ row.branch[col.field] }}'>{{ row.branch[col.field] }}</a>",
        },
        {
          field: "creatorName",
          displayName: "创建者"
        },
        {
          field: "addTime",
          displayName: "创建时间"
        }

        , {
          field: "aa",
          displayName: "操作",
          cellTemplate: ' <a href="javascript:;" ng-click="cellTemplateScope.delete(row.branch)" target="_blank">删除</a> <span class="text-explode">|</span> <i class="link-space"></i> <a href="javascript:;" ng-click="cellTemplateScope.addChild(row.branch)">增加子项</a>',
          cellTemplateScope: {
            edit: function (data) {
              $scope.edit(data);
            },
            delete: function (data) {
              if (data.children.length == 0) {
                var modalInstance = $rootScope.openConfirmDialogModal("确定要删除" + '<span style="font-weight: bold;color: #6b3100">' + data.name + '</span>' + "吗？");
                modalInstance.result.then(function () {
                  $scope.delete(data);
                }, function () {
                });
              } else {
                $rootScope.openErrorDialogModal('<span style="font-weight: bold;color: #6b3100">' + data.name + '</span>' + "下有子项，请先删除所有子项");
              }

            },
            addChild: function (data) {
              $scope.addChild(data);
            }
          }
        }
      ];
      $scope.dict_tree = {};
      $scope.tree_data = {};
    }
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
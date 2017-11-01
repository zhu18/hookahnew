class publicController {
  constructor($scope, $rootScope, $http, $state, $stateParams, growl,$filter) {

    $scope.reader = function () {
          var promise = $http({
              method: 'GET',
              url: $rootScope.site.crowdServer + "/api/require/ReqCheck",
              params: {
                  id: $stateParams.id
              }
          });
          promise.then(function (res) {
              if (res.data.code == '1') {
                  var item= res.data.data.zbRequirement;
                  $scope.zbAnnexes= res.data.data.zbAnnexes;
                  $scope.requiremetName=item.requiremetName;
                  $scope.contactName=item.contactName;
                  $scope.contactPhone=item.contactPhone;
                  $scope.title=item.title;
                  $scope.type=item.type;
                  $scope.tag=item.tag?item.tag.split(','):null;
                  $scope.description=item.description;
                  $scope.deliveryDeadline=item.deliveryDeadline;
                  var tomorrow = new Date();
                  $scope.applyDeadline=new Date(tomorrow.setDate(tomorrow.getDate() + 7));
                  $scope.rewardMoney=item.rewardMoney;
                  $scope.managedMoney=res.data.data.managedMoney;
                  $scope.trusteePercent=item.trusteePercent;
                  $scope.checkRemark=item.checkRemark;
                  $scope.status=item.status;
                  $scope.id=item.id;
                  //进度时间
                  var mgZbRequireStatus = res.data.data.mgZbRequireStatus || null;//进度时间
                  if(mgZbRequireStatus){
                      $scope.addTime=mgZbRequireStatus.addTime;//发布需求
                      $scope.checkTime=mgZbRequireStatus.checkTime;//平台审核
                      $scope.trusteeTime=mgZbRequireStatus.trusteeTime;//资金托管
                      $scope.pressTime=mgZbRequireStatus.pressTime;//平台发布
                      $scope.workingTime=mgZbRequireStatus.workingTime;//服务商工作
                      $scope.payTime=mgZbRequireStatus.payTime;//验收付款
                      $scope.commentTime=mgZbRequireStatus.commentTime;//评价
                      $scope.cancelTime=mgZbRequireStatus.cancelTime;//交易完成
                      $scope.toBeRefundedTime =mgZbRequireStatus.toBeRefundedTime ;//待退款
                  }
                  $scope.readerData(item.deliveryDeadline)
              } else {


              }
              $rootScope.loadingState = false;
              growl.addSuccessMessage("订单数据加载完毕。。。");
          });
      };
    $scope.reader();

    $scope.public=function () {
        if($filter('format')($scope.applyDeadline, 'yyyy-MM-dd HH:mm:ss')>$scope.deliveryDeadline){
            var modalInstance =$rootScope.openConfirmDialogModal("报名截止日期超过交付日期了，请重新选择日期！");
            modalInstance.result.then(function () {

            }, function () {

            });
        }else {
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.crowdServer + "/api/require/updateStatus",
                params: {
                    id:$scope.id,
                    status:5,
                    applyDeadline:$filter('format')($scope.applyDeadline, 'yyyy-MM-dd HH:mm:ss')
                }
            });
            promise.then(function (res, status, config, headers) {
                console.log('数据在这里');
                console.log(res);
                if (res.data.code == '1') {
                    var modalInstance =$rootScope.openConfirmDialogModal("发布成功！");
                    modalInstance.result.then(function () {
                        $state.go('publish.list');
                    }, function () {
                        $state.go('publish.list');
                    });
                } else {

                    var modalInstance =$rootScope.openConfirmDialogModal("发布失败！");
                    modalInstance.result.then(function () {
                        $state.go('publish.list');
                    }, function () {
                        $state.go('publish.list');
                    });
                }
                $rootScope.loadingState = false;
                growl.addSuccessMessage("订单数据加载完毕。。。");
            });
        }

      };

    $scope.readerData=function (data) {
          // 日历插件开始

          $scope.inlineOptions = {
              customClass: getDayClass,
              minDate: new Date(),
              maxDate: new Date(data),
              showWeeks: true
          };
          $scope.open1 = function () {
              $scope.popup1.opened = true;
          };
          $scope.popup1 = {
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
}

export default publicController;
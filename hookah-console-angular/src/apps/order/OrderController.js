class ShelfController {
  constructor($scope, $rootScope, $state, $http, $uibModal, uibDateParser, usSpinnerService, growl) {
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

    $scope.search1 = function () {
        console.log($scope.startDate);
        var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/order/all",
        params: {
          currentPage: $rootScope.pagination.currentPage,
          pageSize: $rootScope.pagination.pageSize,
          orderSn: $scope.orderSn,
          userName:$scope.userName,
          userType:$scope.userType,
          payStatus:$scope.payStatus,
          startDate:$scope.startDate?format($scope.startDate, 'yyyy-MM-dd HH:mm:ss'):null,
          solveStatus:$scope.solveStatus,
          endDate:$scope.endDate?format($scope.endDate, 'yyyy-MM-dd HH:mm:ss'):null
        }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });
    };
    // $scope.search = function () {
    //     var promise = $http({
    //     method: 'GET',
    //     url: $rootScope.site.apiServer + "/api/order/all",
    //     params: {
    //       currentPage: $rootScope.pagination.currentPage,
    //       pageSize: $rootScope.pagination.pageSize,
    //       orderSn: $scope.orderSn,
    //       userName:$scope.userName,
    //       userType:$scope.userType,
    //       payStatus:$scope.payStatus,
    //       startDate:null,
    //       solveStatus:$scope.solveStatus,
    //       endDate:null
    //     }
    //   });
    //   promise.then(function (res, status, config, headers) {
    //     $rootScope.loadingState = false;
    //     growl.addSuccessMessage("订单数据加载完毕。。。");
    //   });
    // };
    $scope.getDetails = function (event, orderId) {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/order/viewDetails",
        params: {
          orderId: orderId
        }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.order = res.data.data[0];
        $rootScope.buyer = res.data.data[1];
      });
    };
    $scope.getGoodDetail = function (event, goodsId) {
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/goods/getGoodsInfo",
        params: {
          goodsId: goodsId
        }
      });
      promise.then(function (res, status, config, headers) {
        if (res.data.code == "1") {
          $rootScope.editData = res.data.data;
          $state.go('order.viewGoodDetail', {data: $rootScope.editData});
        }
      });
    };
    $scope.pageChanged = function () {
      $scope.search();
      console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };
    $scope.back = function () {
      history.back();
    };
    $scope.search1();
    $scope.remark = function (goodsId, orderId, goodsType, isOffline, goods) {
      console.log("goodsId：" + goodsId, "orderId：" + orderId, "goodsType：" + goodsType, "isOffline：" + isOffline);
      var inserDOM = null;
      var modalInstance = null;

      var promiseGet01 = $http({ //请求备注信息
        method: 'get',
        url: $rootScope.site.apiServer + "/api/order/getRemark",
        params: {
          goodsId: goodsId,
          orderId: orderId
        }
      });
      promiseGet01.then(function (res, status, config, headers) {
        console.log(res);
        if (isOffline == 0) {
          if (res.data.code == 1) {
            if (goodsType == 0) { //常规商品
              var tempUrl = null;
              if (res.data.data.data.isOnline == 0) {
                tempUrl = res.data.data.data.localUrl;
              } else {
                tempUrl = res.data.data.data.onlineUrl;
              }
              var pruDom = "<h4>商品交付信息：</h4>" +
                "<h5>离线数据包下载地址：<a target='_blank' style='text-decoration: underline;color:blue; ' href='" + tempUrl + "'>下载</a></h5>" +
                "<h5>数据包解压密码：<span>" + res.data.data.data.dataPwd + "</span></h5>"; //离线数据包 
              modalInstance = $rootScope.openConfirmDialogModal(pruDom);


            } else if (goodsType == 1) { //API
              return false;
            } else if (goodsType == 2) { //模型
              var modalDom = "<h4>模型压缩包下载地址：</h4>" +
                  "<h5>联系人姓名：<input type='text' id='J_val01' value='" + res.data.data.data.concatInfo.concatName + "'></h5>" +
                  "<h5>联系人电话：<input type='text' id='J_val02' value='" + res.data.data.data.concatInfo.concatPhone + "'></h5>" +
                  "<h5>联系人邮箱：<input type='text' id='J_val03' value='" + res.data.data.data.concatInfo.concatEmail + "'></h5>" +
                  "<h5>模型文件：<a target='_blank' href='" + res.data.data.data.modelFile + "'>下载</a></h5>" +
                  "<h5>模型文件密码：<span>" + res.data.data.data.modelFilePwd + "</span></h5>" +
                  "<h5>配置文件：<a target='_blank' href='" + res.data.data.data.configFile + "'>下载</a></h5>" +
                  "<h5>配置文件密码：<span>" + res.data.data.data.configFilePwd + "</span></h5>" +
                  "<h5>配置参数：<a target='_blank' href='" + res.data.data.data.configParams + "'>下载</a></h5>" +
                  "<h5>配置参数密码：<span>" + res.data.data.data.configParamsPwd + "</span></h5>"
                ;
              modalInstance = $rootScope.openConfirmDialogModal(modalDom);

            } else if (goodsType == 4 || goodsType == 6) { //独立软件 手动填写数据
              var proSoftDom = "<h4>商品交付信息：</h4>" +
                "<h5>安装包下载地址：<span>" + res.data.data.url.dataAddress + "</span></h5>" +
                "<h5>序列号：<input type='text' id='J_val01' value='" + res.data.data.payInfoSerialNumber + "'></h5>" +
                "<h5>许可文件获取地址：<input type='text' id='J_val02' value='" + res.data.data.payInfoFileUrl + "'></h5>";
              modalInstance = $rootScope.openConfirmDialogModal(proSoftDom);

            } else if (goodsType == 5 || goodsType == 7) { //SaaS  手动填写数据
              var sassDom = "<h4>商品交付信息：</h4>" +
                "<h5>在线访问地址：<span>" + res.data.data.url.dataAddress + "</span></h5>" +
                "<h5>用户名：<input type='text' id='J_val01' value='" + res.data.data.payInfoUserName + "'></h5>" +
                "<h5>密码：<input type='text' id='J_val02' value='" + res.data.data.payInfoPassword + "'></h5>";//sass
              modalInstance = $rootScope.openConfirmDialogModal(sassDom);
            }
          } else {
            console.log('other');
          }
        } else { //线下
          var offlineDom = "<h4>线下交付联系方式：</h4>" +
            "<h5>联系人姓名：<input type='text' id='J_concatName' value='" + res.data.data.data.concatName + "'></h5>" +
            "<h5>联系人电话：<input type='text' id='J_concatPhone' value='" + res.data.data.data.concatPhone + "'></h5>" +
            "<h5>联系人邮箱：<input type='text' id='J_concatEmail' value='" + res.data.data.data.concatEmail + "'></h5>";
          modalInstance = $rootScope.openConfirmDialogModal(offlineDom);

        }

        modalInstance.result.then(function () { //模态点确定
          var promise = null;
          if (isOffline == 0) {
            promise = $http({
              method: 'POST',
              url: $rootScope.site.apiServer + "/api/order/updRemark",
              params: {
                goodsId: goodsId,
                orderId: orderId,
                remark: $('#J_val01').val() + ',' + $('#J_val02').val() + ',' + $('#J_val03').val()
              }
            });

          } else {
            promise = $http({
              method: 'POST',
              url: $rootScope.site.apiServer + "/api/order/updConcatInfo",
              params: {
                goodsId: goodsId,
                orderId: orderId,
                concatName: $('#J_concatName').val(),
                concatPhone: $('#J_concatPhone').val(),
                concatEmail: $('#J_concatEmail').val()
              }
            });
          }

          promise.then(function (res, status, config, headers) {
            console.log(res)
            $rootScope.loadingState = false;
            console.log(res);
            if (res.data.code == 1) {
              growl.addSuccessMessage("保存成功。。。");
              $scope.search();
            } else {
              growl.addErrorMessage("保存失败。。。");
            }

          });
        }, function () {
          // $log.info('Modal dismissed at: ' + new Date());
        });
      });


      /*
       switch (goodsType) {
       case 0:
       return '常规商品'; //离线数据包    数据源-离线数据
       case 1:
       return 'API'; //不做修改  下载     数据源-API
       case 2:
       return '数据模型';//模型       模型
       case 4:
       return '分析工具--独立软件';//独立部署软件     分析工具-独立部署软件
       case 5:
       return '分析工具--SaaS';//saas          分析工具-SaaS
       case 6:
       return '应用场景--独立软件';//独立部署软件     应用场景-独立部署软件
       case 7:
       return '应用场景--SaaS'; //saas    应用场景-SaaS
       break;
       }
       */


    };
      $scope.inlineOptions = {
          customClass: getDayClass,
          minDate:new Date(2000, 5, 22),
          showWeeks: true
      };
      // $scope.dateOptions = {
      //     dateDisabled: disabled,
      //     formatYear: 'yy',
      //     maxDate: new Date(2020, 5, 22),
      //     minDate: new Date(2000, 5, 22),
      //     startingDay: 1
      // };

      // Disable weekend selection
      // function disabled(data) {
      //     var date = data.date,
      //         mode = data.mode;
      //     return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
      // }
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

      // var start = {
      //     format: "YYYY-MM-DD hh:mm:ss",
      //     isTime: true,
      //     maxDate: new Date(2000, 5, 22),
      //     choosefun: function (elem, datas) {
      //         end.minDate = datas; //开始日选好后，重置结束日的最小日期
      //     }
      // };
      // var end = {
      //     format: "YYYY-MM-DD hh:mm:ss",
      //     isTime: true,
      //     maxDate: new Date(2000, 5, 22),
      //     choosefun: function (elem, datas) {
      //         start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
      //     }
      //
      // };
      // $.jeDate("#startDate", start);
      // $.jeDate("#endDate", end);
  }
}
export default ShelfController;
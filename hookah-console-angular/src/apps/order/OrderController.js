class ShelfController {
  constructor($scope, $rootScope, $state, $http, $uibModal, usSpinnerService, growl) {


    $scope.search = function () {
      console.log("查询。。。。");
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/order/all",
        params: {
          currentPage: $rootScope.pagination.currentPage,
          pageSize: $rootScope.pagination.pageSize,
          orderSn: $scope.orderSn
        }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });
    };

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
    $scope.search();
    $scope.remark = function (goodsId, orderId, goodsType, isOffline) {
      console.log("goodsId：" + goodsId, "orderId：" + orderId, "goodsType：" + goodsType, "isOffline：" + isOffline);
      var inserDOM = null;
      var modalInstance = null;

      var promiseGet01 = $http({ //请求支付信息
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
              var pruDom = "<h4>商品交付信息：</h4><h5>离线数据包下载地址：<span>" + res.data.data.data.onlineUrl + "</span></h5><h5>数据包解压密码：<span>" + res.data.data.data.dataPwd + "</span></h5>";//离线数据包
              modalInstance = $rootScope.openConfirmDialogModal(pruDom);

            } else if (goodsType == 1) { //API
              return false;
            } else if (goodsType == 2) { //模型
              var modalDom = "<h4>模型压缩包下载地址：</h4>" +
                  "<h5>联系人姓名：<input type='text' value='" + res.data.data.data.concatInfo.concatName + "'></h5>" +
                  "<h5>联系人电话：<input type='text' value='" + res.data.data.data.concatInfo.concatPhone + "'></h5>" +
                  "<h5>联系人邮箱：<input type='text' value='" + res.data.data.data.concatInfo.concatEmail + "'></h5>" +
                  "<h5>模型文件：<span>" + res.data.data.data.modelFile + "</span></h5>" +
                  "<h5>模型文件密码：<span>" + res.data.data.data.modelFilePwd + "</span></h5>" +
                  "<h5>配置文件：<span>" + res.data.data.data.configFile + "</span></h5>" +
                  "<h5>配置文件密码：<span>" + res.data.data.data.configFilePwd + "</span></h5>" +
                  "<h5>配置参数：<span>" + res.data.data.data.configParams + "</span></h5>" +
                  "<h5>配置参数密码：<span>" + res.data.data.data.configParamsPwd + "</span></h5>"
                ;
              modalInstance = $rootScope.openConfirmDialogModal(modalDom);

            } else if (goodsType == 4 || goodsType == 6) { //独立软件 手动填写数据  TODO:安装包下载地址暂时没有
              var proSoftDom = "<h4>商品交付信息：</h4><h5>安装包下载地址：<span>" + res.data.data.url.dataAddress + "</span></h5><h5>序列号：<input type='text' value='" + res.data.data.payInfoSerialNumber + "'></h5><h5>许可文件获取地址：<input type='text' value='" + res.data.data.payInfoFileUrl + "'></h5>";//独立部署软件
              modalInstance = $rootScope.openConfirmDialogModal(proSoftDom);

            } else if (goodsType == 5 || goodsType == 7) { //SaaS  手动填写数据
              var sassDom = "<h4>商品交付信息：</h4><h5>在线访问地址：<span>" + res.data.data.url.dataAddress + "</span></h5><h5>用户名：<input type='text' value='" + res.data.data.payInfoUserName + "'></h5><h5>密码：<input type='text' value='" + res.data.data.payInfoPassword + "'></h5>";//sass
              modalInstance = $rootScope.openConfirmDialogModal(sassDom);
            }
          } else {
            console.log('other') ;
          }
        } else { //线下
          var offlineDom = "<h4>线下交付联系方式：</h4>" +
            "<h5>联系人姓名：<input type='text' value='" + res.data.data.data.concatName + "'></h5>" +
            "<h5>联系人电话：<input type='text' value='" + res.data.data.data.concatPhone + "'></h5>" +
            "<h5>联系人邮箱：<input type='text' value='" + res.data.data.data.concatEmail + "'></h5>";
          modalInstance = $rootScope.openConfirmDialogModal(offlineDom);

        }

        modalInstance.result.then(function () { //模态点确定
          var promise=null;
          if(isOffline == 0){
            promise = $http({
              method: 'POST',
              url: $rootScope.site.apiServer + "/api/order/getRemark",
              params: {
                goodsId: goodsId,
                orderId: orderId,
                remark: ''
              }
            });

          }else{
            promise = $http({
              method: 'POST',
              url: $rootScope.site.apiServer + "/api/order//updConcatInfo",
              params: {
                goodsId: goodsId,
                orderId: orderId,
                concatName:'zhaoshujun',
                concatPhone:'111',
                concatEmail:'234@qqw.com'
              }
            });
          }

          promise.then(function (res, status, config, headers) {
            console.log(res)
            $rootScope.loadingState = false;
            console.log(res);
            if (res.data.code == 1) {
              growl.addSuccessMessage("删除成功。。。");
              $scope.search();
            } else {
              growl.addErrorMessage("删除失败。。。");
            }

          });
        }, function () {
          // $log.info('Modal dismissed at: ' + new Date());
        });
      });


      /*
       switch (goodsType) {
       case 0:
       return '常规商品'; //离线数据包
       break;
       case 1:
       return 'API'; //不做修改
       break;
       case 2:
       return '数据模型';//模型
       break;
       case 4:
       return '分析工具--独立软件';//独立部署软件
       break;
       case 5:
       return '分析工具--SaaS';//saas
       break;
       case 6:
       return '应用场景--独立软件';//独立部署软件
       break;
       case 7:
       return '应用场景--SaaS'; //saas
       break;
       }
       */


    };


  }
}

export default ShelfController;
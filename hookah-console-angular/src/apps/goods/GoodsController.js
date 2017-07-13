class GoodsController {
  constructor($scope, $rootScope, $state, $stateParams, $http, $sce, $uibModal, usSpinnerService, growl) {
    console.log($rootScope.config);
	  $rootScope.currentS= "1";
	  $rootScope.setCurrentS = function (param) {
			  // console.log(param);
			  $rootScope.currentS = param;

	  };


      // if ($state.current.name == "items.lookDetail") {
      //     var editor = new wangEditor('goodsDesc');
      //     //上传图片（举例）
      //     editor.config.uploadImgUrl = $rootScope.url.uploadEditor;
      //     editor.config.uploadImgFileName = 'filename';
      //     //关闭菜单栏fixed
      //     editor.config.menuFixed = false;
      //     editor.config.menus = $.map(wangEditor.config.menus, function (item, key) {
      //         if (item === 'location') {
      //             return null;
      //         }
      //         if (item === 'video') {
      //             return null;
      //         }
      //         return item;
      //     });
      //     editor.create();
      // }

    $scope.search = function () {

        $rootScope.Name = $scope.searchName;
        $rootScope.Sn = $scope.searchSn;
        $rootScope.Kw = $scope.searchKw;
        $rootScope.Shop = $scope.searchShop;
        $rootScope.CheckStatus = $scope.searchCheckStatus;
        $rootScope.OnSaleStatus = $scope.searchOnSaleStatus;

        // alert("Name=" + $rootScope.Name + "--Sn=" + $rootScope.Sn + "--CheckStatus=" + $rootScope.CheckStatus + "--OnSaleStatus=" + $rootScope.OnSaleStatus);

      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/goods/all",
        params: {currentPage: $rootScope.pagination.currentPage,
                    pageSize: $rootScope.pagination.pageSize,
                    goodsName: $scope.searchName,
                    goodsSn: $scope.searchSn,
                    keywords: $scope.searchKw,
                    shopName: $scope.searchShop,
                    checkStatus: $scope.searchCheckStatus,
                    onSaleStatus: $scope.searchOnSaleStatus
        }
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        console.log($rootScope.pagination);
        growl.addSuccessMessage("数据加载完毕。。。");
      });
    };

    $scope.delGoods = function (event, item, flag) {
        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/goods/delGoodsById",
            params: {goodsId: item.goodsId, flag: flag
            }
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            if(res.data.code == "1"){
                $scope.search();
                growl.addSuccessMessage("数据加载完毕。。。");
            }
        });
    };

    $scope.updateGoods = function (event, item) {
      console.log("去修改……");
      $rootScope.editData = item;
      $state.go('items.update', {data: item});
    };

  /**
   * 1 审核  2强制下架
   * @param item
   * @param n
   */
    $scope.goCheck = function (item, n) {
      console.log("去审核……");
      console.log(n);
      // $rootScope.item = item;
      // $rootScope.n = n;

        // var promise = $http({
        //     method: 'GET',
        //     url: $rootScope.site.apiServer + "/api/goods/getGoodsInfo",
        //     params: {goodsId: item.goodsId}
        // });
        // promise.then(function (res, status, config, headers) {
        //     console.log(res.data)
        //     if(res.data.code == "1"){
        //         $rootScope.editData = res.data.data;
        //         $rootScope.operatorFlag = n;
        //
        //
        //         $rootScope.packageApiInfo = res.data.data.packageApiInfo;
        //
        //         $rootScope.addData = {
        //             goodsCheck: {
        //                 goodsId: $rootScope.editData.goodsId,//商品ID
        //                 goodsSn: $rootScope.editData.goodsSn,//商品编号
        //                 goodsName: $rootScope.editData.goodsName,//商品名称
        //             },
        //             apiInfoBean: {
        //                 apiType: $rootScope.packageApiInfo.apiType,//接口类型q
        //                 invokeMethod: $rootScope.packageApiInfo.invokeMethod,//调用方法名q
        //                 respDataFormat: $rootScope.packageApiInfo.respDataFormat,//返回格式q
        //                 apiUrl: $rootScope.packageApiInfo.apiUrl,//接口地址q
        //                 apiMethod: $rootScope.packageApiInfo.apiMethod,//请求方式q
        //                 reqSample: $rootScope.packageApiInfo.reqSample,//请求示例
        //                 apiDesc: $rootScope.packageApiInfo.apiDesc, //接口描述
        //                 reqParamList:$rootScope.packageApiInfo.reqParamList, //接口描述
        //                 respParamList:$rootScope.packageApiInfo.respParamList, //接口描述
        //                 respSample: $rootScope.packageApiInfo.respSample, //返回示例
        //
        //                 respDataMapping: {
        //                     codeAttrBean: {
        //                         codeAttr: $rootScope.packageApiInfo.respDataMapping.codeAttrBean.codeAttr,//编码属性
        //                         codeInfoBean:{
        //                             successCode: $rootScope.packageApiInfo.respDataMapping.codeAttrBean.codeInfoBean.successCode,//成功
        //                             failedCode: $rootScope.packageApiInfo.respDataMapping.codeAttrBean.codeInfoBean.failedCode,//失败
        //                             successNoData: $rootScope.packageApiInfo.respDataMapping.codeAttrBean.codeInfoBean.successNoData,//成功无数据
        //                         }
        //                     },
        //                     infoAttr: $rootScope.packageApiInfo.respDataMapping.infoAttr,//信息属性
        //                     dataAttr: $rootScope.packageApiInfo.respDataMapping.dataAttr,//数据属性
        //                     totalNumAttr: $rootScope.packageApiInfo.respDataMapping.totalNumAttr,//总条数属性
        //                 },
        //
        //                 updateFreq: $rootScope.packageApiInfo.updateFreq,//更新频率
        //                 dataNumDivRowNum: $rootScope.packageApiInfo.dataNumDivRowNum,//数据条数/行数
        //                 encryptInfo:{
        //                     secretKeyName: $rootScope.packageApiInfo.encryptInfo.secretKeyName,//密钥名称
        //                     secretKeyValue: $rootScope.packageApiInfo.encryptInfo.secretKeyValue//密钥值
        //                 }
        //             },
        //         };

               $state.go('items.checkGoodsDetail', {data1: item, data2:n});

        //        console.log("JOSNSTR"+ JSON.stringify($rootScope.addData))
        //     }
        // });
    };

    $scope.lookDetail = function (item) {
      console.log("查看商品详情……");
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/goods/getGoodsInfo",
            params: {goodsId: item.goodsId}
        });
        promise.then(function (res, status, config, headers) {
            console.log(res.data)
            if(res.data.code == "1"){
                $rootScope.editData = res.data.data;
                $state.go('items.lookDetail', {data: $rootScope.editData});
            }
        });
    };

      $scope.goOff = function(item){
          var promise = $http({
              method: 'POST',
              url: $rootScope.site.apiServer + "/api/goods/off",
              params: {goodsId:item.goodsId}
          });
          promise.then(function (res, status, config, headers) {
              console.log(res.data)
              if(res.data.code == "1"){
                  $scope.refresh();
              }
          });
      }

    $scope.pageChanged = function () {
      $scope.search();
      console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };

    if ($state.$current.name == "items.search") {
      $scope.search();
    }

    $scope.refresh = function(){
      $scope.search();
    }

      /**
       * select 框 以及 option
       * @type {[*]}
       */
    $scope.checkStatuss = [{id:-1, name:"全部"}, {id:0, name:"待审核"}, {id:1, name:"已通过"}, {id:2, name:"未通过"}];
    $scope.searchCheckStatus = -1;

    $scope.onSaleStatuss = [{id:-1, name:"全部"}, {id:0, name:"已下架"}, {id:1, name:"已上架"}, {id:2, name:"强制下架"}];
    $scope.searchOnSaleStatus = -1;


    if ($state.$current.name == "items.searchByCon") {
          $scope.searchName = $rootScope.Name;
          $scope.searchSn = $rootScope.Sn;
          $scope.searchCheckStatus = $rootScope.CheckStatus == undefined ? -1 : $rootScope.CheckStatus;
          $scope.searchOnSaleStatus = $rootScope.OnSaleStatus == undefined ? -1 : $rootScope.OnSaleStatus;
    }

    $scope.returnPage = function(){

        $state.go('items.searchByCon');

        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/goods/all",
            params: {currentPage: $rootScope.pagination.currentPage,
                pageSize: $rootScope.pagination.pageSize,
                goodsName: $rootScope.Name,
                goodsSn: $rootScope.Sn,
                // keywords: $scope.searchKw,
                // shopName: $scope.searchShop,
                checkStatus: $rootScope.CheckStatus,
                onSaleStatus: $rootScope.OnSaleStatus
            }
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            growl.addSuccessMessage("数据加载完毕。。。");
        });
    }

  }
}

export default GoodsController;
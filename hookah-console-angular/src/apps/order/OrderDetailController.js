/**
 * Created by lss on 2017/7/3 0003.
 */
class orderDetailController {
    constructor($scope, $rootScope, $state, $http,$stateParams, growl) {

        var promise = $http({
          method: 'GET',
          url: $rootScope.site.apiServer + "/api/order/viewDetails",
          params: {
            orderId: $stateParams.id
          }
        });
        promise.then(function (res, status, config, headers) {
          $rootScope.order = res.data.data[0];
          $rootScope.buyer = res.data.data[1];
          if(!($rootScope.order.payStatusName=="未付款")){
                $scope.payStatusName=true;
          }
        });
        $scope.back = function () {
            history.back();
        };
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
                                    "<h5>模型文件：<a target='_blank' href='" + res.data.data.data.modelFile.fileAddress + "'>下载</a></h5>" +
                                    "<h5>模型文件密码：<span>" + res.data.data.data.modelFile.filePwd + "</span></h5>" +
                                    "<h5>配置文件：<a target='_blank' href='" + res.data.data.data.configFile.fileAddress + "'>下载</a></h5>" +
                                    "<h5>配置文件密码：<span>" + res.data.data.data.configFile.filePwd + "</span></h5>" +
                                    "<h5>配置参数：<a target='_blank' href='" + res.data.data.data.paramFile.fileAddress + "'>下载</a></h5>" +
                                    "<h5>配置参数密码：<span>" + res.data.data.data.paramFile.filePwd + "</span></h5>"
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
    }
}
export default orderDetailController;
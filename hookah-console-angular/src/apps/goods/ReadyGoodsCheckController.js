class GoodsCheckController {
	constructor($scope, $rootScope, $stateParams, $http, $state, $uibModal, usSpinnerService, growl) {

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
				url: $rootScope.site.apiServer + "/api/goods/allNotCheck",
				params: {
					currentPage: $rootScope.pagination.currentPage,
					pageSize: $rootScope.pagination.pageSize,
					goodsName: $scope.searchName,
					goodsSn: $scope.searchSn,
					keywords: $scope.searchKw,
					shopName: $scope.searchShop,
					orgName: $scope.orgName
				}
			});
			promise.then(function (res, status, config, headers) {
				$rootScope.loadingState = false;
				growl.addSuccessMessage("数据加载完毕。。。");
			});
		};

		$scope.checkLookGoods = function (item, n) {
			console.log(item.goodsName);
			var promise = $http({
				method: 'GET',
				url: $rootScope.site.apiServer + "/api/goods/getGoodsInfo",
				params: {goodsId: item.goodsId}
			});
			promise.then(function (res, status, config, headers) {
				console.log(res.data)
				if (res.data.code == "1") {
					$rootScope.editData = res.data.data;
					$rootScope.operatorFlag = n;
					$state.go('items.checkGoodsDetail', {data: $rootScope.editData});

					$rootScope.currentS = "1";
					$rootScope.setCurrentS = function (param) {
						// console.log(param);
						$rootScope.currentS = param;

					};

					$rootScope.packageApiInfo = res.data.data.packageApiInfo;

					$rootScope.addData = {
						goodsCheck: {
							goodsId: $rootScope.editData.goodsId,//商品ID
							goodsSn: $rootScope.editData.goodsSn,//商品编号
							goodsName: $rootScope.editData.goodsName,//商品名称
						},
						apiInfoBean: {
							apiType: $rootScope.packageApiInfo.apiType,//接口类型q
							invokeMethod: $rootScope.packageApiInfo.invokeMethod,//调用方法名q
							respDataFormat: $rootScope.packageApiInfo.respDataFormat,//返回格式q
							apiUrl: $rootScope.packageApiInfo.apiUrl,//接口地址q
							apiMethod: $rootScope.packageApiInfo.apiMethod,//请求方式q
							reqSample: $rootScope.packageApiInfo.reqSample,//请求示例
							apiDesc: $rootScope.packageApiInfo.apiDesc, //接口描述
                            reqParamList:$rootScope.packageApiInfo.reqParamList, //接口描述
                            respParamList:$rootScope.packageApiInfo.respParamList, //接口描述
							respSample: $rootScope.packageApiInfo.respSample, //返回示例

                            respDataMapping: {
                                codeAttrBean: {
                                    codeAttr: $rootScope.packageApiInfo.respDataMapping.codeAttrBean.codeAttr,//编码属性
                                    codeInfoBean:{
                                        successCode: $rootScope.packageApiInfo.respDataMapping.codeAttrBean.codeInfoBean.successCode,//成功
                                        failedCode: $rootScope.packageApiInfo.respDataMapping.codeAttrBean.codeInfoBean.failedCode,//失败
                                        successNoData: $rootScope.packageApiInfo.respDataMapping.codeAttrBean.codeInfoBean.successNoData,//成功无数据
									}
								},
                                infoAttr: $rootScope.packageApiInfo.respDataMapping.infoAttr,//信息属性
                                dataAttr: $rootScope.packageApiInfo.respDataMapping.dataAttr,//数据属性
                                totalNumAttr: $rootScope.packageApiInfo.respDataMapping.totalNumAttr,//总条数属性
							},

                            updateFreq: $rootScope.packageApiInfo.updateFreq,//更新频率
							dataNumDivRowNum: $rootScope.packageApiInfo.dataNumDivRowNum,//数据条数/行数
                            encryptInfo:{
                                secretKeyName: $rootScope.packageApiInfo.encryptInfo.secretKeyName,//密钥名称
                                secretKeyValue: $rootScope.packageApiInfo.encryptInfo.secretKeyValue//密钥值
							}
						},
					};
					console.log("STR"+JSON.stringify($rootScope.addData));
				}
			});
		}

		$scope.tablePlus = function (that){

            var requestHtml = '';//请求接口
            requestHtml += '<tr>';
            requestHtml += '<td></td>';
            requestHtml += '<td></td>';
            requestHtml += '<td></td>';
            requestHtml += '<td></td>';
            requestHtml += '<td></td>';
            requestHtml += '<td><</td>';
            requestHtml += '<td><span class="column-text" onclick="tablePlus(this)">+</span></td>';
            requestHtml += '</tr>';
			alert(requestHtml);

			alert($(that).parents('.table-hover tbody'));
            $(that).parents('.table-hover tbody').append(requestHtml);
		}

		$scope.showCurrentGoods = function (item) {
			$rootScope.selectId = item.goodsId;
		}

		$scope.submitCheck = function () {

            if ($("#checkContent").val().trim() != '') {
            	if($rootScope.editData.goodsType == 1 && $rootScope.editData.isOffline == 0 ){

                    $rootScope.addData.goodsCheck.checkStatus = $('input[name="checkStatus"]:checked').val();
                    $rootScope.addData.goodsCheck.checkContent = $('#checkContent').val();
                    var promise = $http({
                        method: 'post',
                        url: $rootScope.site.apiServer + "/api/goodsCheck/addApi",
                        data: "voStr="+JSON.stringify($rootScope.addData)
                    });
                    promise.then(function (res, status, config, headers) {
                        if (res.data.code == "1") {
                            $state.go('items.check');
                        }
                    });
				}else{

            		$scope.dataGoods={};
                    $scope.dataGoods.goodsId = $('#goodsId').val();
                    $scope.dataGoods.goodsSn = $('#goodsSn').val();
                    $scope.dataGoods.goodsName = $('#goodsName').val();
                    $scope.dataGoods.checkStatus = $('input[name="checkStatus"]:checked').val();
                    $scope.dataGoods.checkContent = $('#checkContent').val();
                    var promise = $http({
                        method: 'post',
                        url: $rootScope.site.apiServer + "/api/goodsCheck/add",
                        data: "voStr="+JSON.stringify($scope.dataGoods)
                    });
                    promise.then(function (res, status, config, headers) {
                        if (res.data.code == "1") {
                            $state.go('items.check');
                        }
                    });
				}
			} else {
				$rootScope.openErrorDialogModal('请填写审核意见^_^');
			}

		}

		$scope.forceOff = function () {
			var promise = $http({
				method: 'POST',
				url: $rootScope.site.apiServer + "/api/goods/forceOff",
				data: $("#forceOffForm").serialize()
			});
			promise.then(function (res, status, config, headers) {
				console.log(res.data)
				if (res.data.code == "1") {
					$state.go('items.search');
				}
			});
		}

		if ($state.$current.name == "items.check") {
			$scope.search();
		}

		$scope.pageChanged = function () {
			$scope.search();
		};

		if ($state.$current.name == "items.check2") {
			$scope.searchName = $rootScope.Name;
			$scope.searchSn = $rootScope.Sn;
		}

        // if ($state.$current.name == "items.checkGoodsDetail") {
			// $scope.item = $rootScope.item;
			// $scope.n = $rootScope.n;
        //     $scope.checkLookGoods($stateParams.data1, $stateParams.data2);
        // }

		$scope.returnPage = function () {

			$state.go('items.check2');

			var promise = $http({
				method: 'GET',
				url: $rootScope.site.apiServer + "/api/goods/allNotCheck",
				params: {
					currentPage: $rootScope.pagination.currentPage,
					pageSize: $rootScope.pagination.pageSize,
					goodsName: $rootScope.Name,
					goodsSn: $rootScope.Sn,
					// keywords: $scope.searchKw,
					// shopName: $scope.searchShop,
					checkStatus: $rootScope.CheckStatus,
					onSaleStatus: $rootScope.OnSaleStatus,
					orgName: $scope.orgName
				}
			});
			promise.then(function (res, status, config, headers) {
				$rootScope.loadingState = false;
			});
		}
	}
}

export default GoodsCheckController;
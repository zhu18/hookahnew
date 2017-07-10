class GoodsCheckController {
	constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {

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
							checkStatus: $rootScope.packageApiInfo.checkStatus,//审核状态
							checkContentddd: $rootScope.packageApiInfo.checkContentddd,//审核意见
						},
						apiInfoBean: {
							apiType: $rootScope.packageApiInfo.apiType,//接口类型q
							invokeMethod: $rootScope.packageApiInfo.invokeMethod,//调用方法名q
							respDataFormat: $rootScope.packageApiInfo.respDataFormat,//返回格式q
							apiUrl: $rootScope.packageApiInfo.apiUrl,//接口地址q
							apiMethod: $rootScope.packageApiInfo.apiMethod,//请求方式q
							reqSample: $rootScope.packageApiInfo.reqSample,//请求示例
							apiDesc: $rootScope.packageApiInfo.apiDesc, //接口描述

							respSample: $rootScope.packageApiInfo.respSample, //返回示例
							codeAttr: $rootScope.packageApiInfo.respDataMapping.codeAttrBean.codeAttr,//编码属性
							successCode: $rootScope.packageApiInfo.respDataMapping.codeAttrBean.codeInfoBean.successCode,//成功
							failedCode: $rootScope.packageApiInfo.respDataMapping.codeAttrBean.codeInfoBean.failedCode,//失败
							successNoData: $rootScope.packageApiInfo.respDataMapping.codeAttrBean.codeInfoBean.successNoData,//成功无数据
							infoAttr: $rootScope.packageApiInfo.respDataMapping.infoAttr,//信息属性
							dataAttr: $rootScope.packageApiInfo.respDataMapping.dataAttr,//数据属性
							totalNumAttr: $rootScope.packageApiInfo.respDataMapping.totalNumAttr,//总条数属性
							updateFreq: $rootScope.packageApiInfo.updateFreq,//更新频率
							dataNumDivRowNum: $rootScope.packageApiInfo.dataNumDivRowNum,//数据条数/行数
							secretKeyName: $rootScope.packageApiInfo.encryptInfo.secretKeyName,//密钥名称
							secretKeyValue: $rootScope.packageApiInfo.encryptInfo.secretKeyValue//密钥值
						},
					};
				}
			});
		}

		$scope.showCurrentGoods = function (item) {
			$rootScope.selectId = item.goodsId;
		}

		$scope.submitCheck = function () {
			console.log(typeof $rootScope.addData);
			console.log(JSON.stringify($rootScope.addData.goodsCheck));
			if ($("#checkContent").val().trim() != '') {
				var promise = $http({
					method: 'post',
					url: $rootScope.site.apiServer + "/api/goodsCheck/add",
					data: "voStr="+JSON.stringify($rootScope.addData)
				});
				promise.then(function (res, status, config, headers) {
					if (res.data.code == "1") {
						$state.go('items.check');
					}
				});
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
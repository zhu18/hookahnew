class GoodsListController {
	constructor($scope, $rootScope, $state, $stateParams, $http, $sce, $uibModal, usSpinnerService, growl) {
		// $rootScope.currentS= "1";
		// $rootScope.setCurrentS = function (param) {
		// 	// console.log(param);
		// 	$rootScope.currentS = param;
		//
		// };
		// console.log($state.$current.name);
		$scope.pageTitle = null; //页面title
		$scope.getUrl = null;
		$rootScope.pagination.currentPage = 1;



		$scope.searchName = '';
		$scope.searchSn = '';
		$scope.searchKw = '';
		$scope.searchShop = '';
		$scope.searchCheckStatus = '';
		$scope.searchOnSaleStatus = '';
		$scope.orgName = '';

		$scope.checkStatuss = [{id:-1, name:"全部"}, {id:0, name:"待审核"}, {id:1, name:"已通过"}, {id:2, name:"未通过"}];
		$scope.searchCheckStatus = -1;

		$scope.onSaleStatuss = [{id:-1, name:"全部"}, {id:0, name:"已下架"}, {id:1, name:"已上架"}, {id:2, name:"强制下架"}];
		$scope.searchOnSaleStatus = -1;

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
			var promise = $http({
				method: 'GET',
				url: $rootScope.site.apiServer + $scope.getUrl,
				params: {currentPage: $rootScope.pagination.currentPage,
					pageSize: $rootScope.pagination.pageSize,
					goodsName: $scope.searchName,
					goodsSn: $scope.searchSn,
					keywords: $scope.searchKw,
					shopName: $scope.searchShop,
					checkStatus: $scope.searchCheckStatus,
					onSaleStatus: $scope.searchOnSaleStatus,
					orgName:$scope.orgName
				}
			});
			promise.then(function (res, status, config, headers) {
				$rootScope.loadingState = false;
				growl.addSuccessMessage("数据加载完毕。。。");
			});
		};

		// $scope.delGoods = function (event, item, flag) {
		// 	var promise = $http({
		// 		method: 'POST',
		// 		url: $rootScope.site.apiServer + "/api/goods/delGoodsById",
		// 		params: {goodsId: item.goodsId, flag: flag
		// 		}
		// 	});
		// 	promise.then(function (res, status, config, headers) {
		// 		$rootScope.loadingState = false;
		// 		if(res.data.code == "1"){
		// 			$scope.search();
		// 			growl.addSuccessMessage("数据加载完毕。。。");
		// 		}
		// 	});
		// };
		//
		// $scope.updateGoods = function (event, item) {
		// 	console.log("去修改……");
		// 	$rootScope.editData = item;
		// 	$state.go('items.update', {data: item});
		// };
		//
		// /**
		//  * 1 审核  2强制下架
		//  * @param item
		//  * @param n
		//  */
		// $scope.goCheck = function (item, n) {
		// 	console.log("去审核……");
		// 	console.log(n);
		// 	var promise = $http({
		// 		method: 'GET',
		// 		url: $rootScope.site.apiServer + "/api/goods/getGoodsInfo",
		// 		params: {goodsId: item.goodsId}
		// 	});
		// 	promise.then(function (res, status, config, headers) {
		// 		console.log(res.data)
		// 		if(res.data.code == "1"){
		// 			$rootScope.editDatac = res.data.data;
		// 			$rootScope.operatorFlag = n;
		// 			$state.go('items.goodsDetail', {data: res.data.data});
		// 		}
		// 	});
		// };
		//
		// $scope.lookDetail = function (item) {
		// 	console.log("查看商品详情……");
		// 	var promise = $http({
		// 		method: 'GET',
		// 		url: $rootScope.site.apiServer + "/api/goods/getGoodsInfo",
		// 		params: {goodsId: item.goodsId}
		// 	});
		// 	promise.then(function (res, status, config, headers) {
		// 		console.log(res.data)
		// 		if(res.data.code == "1"){
		// 			$rootScope.editData = res.data.data;
		// 			$state.go('items.lookDetail', {data: $rootScope.editData});
		// 		}
		// 	});
		// };
		//
		$scope.goOff = function(item){ //商品下架
			var confirm = $rootScope.openConfirmDialogModal("确认要将&nbsp;<b>" + item.goodsName + "</b>&nbsp;下架吗？");
			confirm.result.then(function () {
				var promise = $http({
					method: 'POST',
					url: $rootScope.site.apiServer + "/api/goods/off",
					params: {goodsId: item.goodsId}
				});
				promise.then(function (res, status, config, headers) {
					if (res.data.code == "1") {
						growl.addSuccessMessage("操作成功");
						$scope.refresh();
					}
				});
			})
		};//商品下架
		$scope.forceOffShelf = function(item){//商品强制下架
			var title1 = '请输入强制下架理由';
			var content = '<div> <label for="" class="col-sm-3">强制下架理由：</label> <textarea name="" id="checkContent" cols="60" rows="10"></textarea> </div> ';
			var modalInstance = $rootScope.openConfirmDialogModel(title1,content);

			modalInstance.result.then(function () { //模态点提交
				if($('#checkContent').val()){
					var promise = $http({
						method: 'POST',
						url: $rootScope.site.apiServer + "/api/goods/forceOff",
						params: {
							goodsId: item.goodsId,
							offReason:$('#checkContent').val()
						}
					});
					promise.then(function (res, status, config, headers) {
						if (res.data.code == "1") {
							growl.addSuccessMessage("操作成功");
							$scope.refresh();
						}
					});
				}else{
					$rootScope.openErrorDialogModal('强制下架理由不能为空',function(){

					});
				}
			},function(){
			});
		};//商品强制下架
		$scope.goodsDetail = function (item,flag) {//商品详情
			var promise = $http({
				method: 'GET',
				url: $rootScope.site.apiServer + "/api/goods/getGoodsInfo",
				params: {goodsId: item.goodsId}
			});
			promise.then(function (res, status, config, headers) {
				if(res.data.code == "1"){
					$state.go('items.goodsDetail', {
						data:{
							data:res.data.data,
							flag:flag
						}
					});
				}
			});
		};//商品详情


		//
		$scope.pageChanged = function () {//翻页
			$scope.search();
			console.log('Page changed to: ' + $rootScope.pagination.currentPage);
		};//翻页
		//
		// if ($state.$current.name == "items.search") {
		// 	$scope.search();
		// }
		//
		$scope.refresh = function(){
			$scope.searchName = '';
			$scope.searchSn = '';
			$scope.searchKw = '';
			$scope.searchShop = '';
			$scope.searchCheckStatus = -1;
			$scope.searchOnSaleStatus = -1;
			$scope.orgName = '';
			$scope.search();
		};

		//
		// /**
		//  * select 框 以及 option
		//  * @type {[*]}
		//  */
		// $scope.checkStatuss = [{id:-1, name:"全部"}, {id:0, name:"待审核"}, {id:1, name:"已通过"}, {id:2, name:"未通过"}];
		// $scope.searchCheckStatus = -1;
		//
		// $scope.onSaleStatuss = [{id:-1, name:"全部"}, {id:0, name:"已下架"}, {id:1, name:"已上架"}, {id:2, name:"强制下架"}];
		// $scope.searchOnSaleStatus = -1;
		//
		//
		// if ($state.$current.name == "items.searchByCon") {
		// 	$scope.searchName = $rootScope.Name;
		// 	$scope.searchSn = $rootScope.Sn;
		// 	$scope.searchCheckStatus = $rootScope.CheckStatus == undefined ? -1 : $rootScope.CheckStatus;
		// 	$scope.searchOnSaleStatus = $rootScope.OnSaleStatus == undefined ? -1 : $rootScope.OnSaleStatus;
		// }
		//
		// $scope.returnPage = function(){
		//
		// 	$state.go('items.searchByCon');
		//
		// 	var promise = $http({
		// 		method: 'GET',
		// 		url: $rootScope.site.apiServer + "/api/goods/all",
		// 		params: {currentPage: $rootScope.pagination.currentPage,
		// 			pageSize: $rootScope.pagination.pageSize,
		// 			goodsName: $rootScope.Name,
		// 			goodsSn: $rootScope.Sn,
		// 			// keywords: $scope.searchKw,
		// 			// shopName: $scope.searchShop,
		// 			checkStatus: $rootScope.CheckStatus,
		// 			onSaleStatus: $rootScope.OnSaleStatus
		// 		}
		// 	});
		// 	promise.then(function (res, status, config, headers) {
		// 		$rootScope.loadingState = false;
		// 		growl.addSuccessMessage("数据加载完毕。。。");
		// 	});
		// }

		if ($state.$current.name == "items.search") {
			$scope.pageTitle = '商品查询';
			$scope.goodsTypeView = 1;
			$scope.getUrl = "/api/goods/all";
			$scope.search();
		}else if($state.$current.name == "items.check"){
			$scope.pageTitle = '待审核资源';
			$scope.goodsTypeView = 2;
			$scope.getUrl = "/api/goods/allNotCheck";
			$scope.search();
		}else if($state.$current.name == "items.checkedList"){
			$scope.pageTitle = '已审核资源';
			$scope.goodsTypeView = 3;
			$scope.getUrl = "/api/goods/checkedList";
			$scope.search();
		}


	}
}

export default GoodsListController;
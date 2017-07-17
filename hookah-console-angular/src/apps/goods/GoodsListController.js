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
				// console.log('qweqeqweqweqweqweqweqweqweqweqweqwe-----'+JSON.stringify($rootScope.pagination));
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
		// $scope.goOff = function(item){
		// 	var promise = $http({
		// 		method: 'POST',
		// 		url: $rootScope.site.apiServer + "/api/goods/off",
		// 		params: {goodsId:item.goodsId}
		// 	});
		// 	promise.then(function (res, status, config, headers) {
		// 		console.log(res.data)
		// 		if(res.data.code == "1"){
		// 			$scope.refresh();
		// 		}
		// 	});
		// }
		//
		// $scope.pageChanged = function () {
		// 	$scope.search();
		// 	console.log('Page changed to: ' + $rootScope.pagination.currentPage);
		// };
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
class GoodsListController {
	constructor($scope, $rootScope, $state, $stateParams, $http, $sce, $uibModal, usSpinnerService, growl) {
		$scope.pageTitle = null; //页面title
		$scope.getUrl = null;
		$scope.checkStatuss = [{id:-1, name:"全部"}, {id:0, name:"待审核"}, {id:1, name:"已通过"}, {id:2, name:"未通过"}];
		$scope.onSaleStatuss = [{id:-1, name:"全部"}, {id:0, name:"已下架"}, {id:1, name:"已上架"}, {id:2, name:"强制下架"}];
		if($stateParams.data){
			$rootScope.pagination.currentPage = $stateParams.data.data.Params.currentPage;
			$rootScope.pagination.pageSize = $stateParams.data.data.Params.pageSize;
			$scope.searchName = $stateParams.data.data.Params.goodsName;
			$scope.searchSn = $stateParams.data.data.Params.goodsSn;
			$scope.searchKw = $stateParams.data.data.Params.keywords;
			$scope.searchShop = $stateParams.data.data.Params.shopName;
			$scope.queryObj={
				searchOnSaleStatus:$stateParams.data.data.Params.onSaleStatus,
				searchCheckStatus :$stateParams.data.data.Params.checkStatus,
				orgName: $stateParams.data.data.Params.orgName
			};
		}else{
			$rootScope.pagination.currentPage = 1;
			$rootScope.pagination.pageSize = 20;
			$scope.searchName = '';
			$scope.searchSn = '';
			$scope.searchKw = '';
			$scope.searchShop = '';
			$scope.queryObj={
				searchOnSaleStatus:-1,
				searchCheckStatus :-1,
				orgName: ''
			};
		}

		$scope.search = function () {//获取初始数据
			$scope.dataParams = {
				url:$state.$current.name,
				Params : {
					currentPage:$rootScope.pagination.currentPage,
					pageSize: $rootScope.pagination.pageSize,
					goodsName: $scope.searchName,
					goodsSn: $scope.searchSn,
					keywords: $scope.searchKw,
					shopName: $scope.searchShop,
					checkStatus: $scope.queryObj.searchCheckStatus,
					onSaleStatus: $scope.queryObj.searchOnSaleStatus,
					orgName:$scope.queryObj.orgName
				}
			};
			var promise = $http({
				method: 'GET',
				url: $rootScope.site.apiServer + $scope.getUrl,
				params: $scope.dataParams.Params
			});
			promise.then(function (res, status, config, headers) {
				$rootScope.loadingState = false;
				growl.addSuccessMessage("数据加载完毕。。。");
			});
		};//获取初始数据
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
		$scope.pageChanged = function () {//翻页
			if($stateParams.data){
				$rootScope.pagination.currentPage = $stateParams.data.data.Params.currentPage;
			}
			$scope.search();
		};//翻页
		$scope.refresh = function(){ //刷新
			$scope.searchName = '';
			$scope.searchSn = '';
			$scope.searchKw = '';
			$scope.searchShop = '';
			$scope.searchCheckStatus = -1;
			$scope.searchOnSaleStatus = -1;
			$scope.orgName = '';
			$scope.search();
		};//刷新
		if ($state.$current.name == "items.search") {//初始化
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
		}//初始化


	}
}

export default GoodsListController;
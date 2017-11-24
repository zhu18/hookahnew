class CarouselController {
	constructor($scope, $rootScope, $http, $state, $uibModal,$location, usSpinnerService, growl) {
		$scope.imgType = '';
		$scope.pageName = '';
		if($location.path() == '/carousel/search'){
			$scope.imgType = '0';
			$scope.pageName = '轮播图';
		}else if($location.path() == '/carousel/ad'){
			$scope.imgType = '1';
			$scope.pageName = '商详页广告';
		}
		$scope.search = function () {
			var promise = $http({
				method: 'GET',
				url: $rootScope.site.websiteServer + "/api/image/list",
				params:{
					imgType:$scope.imgType
				}
			});
			promise.then(function (res, status, config, headers) {
				$rootScope.loadingState = false;
				if(res.data.code == 1){
					$scope.imgList = res.data.data;
					growl.addSuccessMessage("数据加载完毕。。。");
				}else{

				}

			});
		};
		$scope.delImg = function (id) {
			var confirm = $rootScope.openConfirmDialogModal("确认要删除此内容吗？");
			confirm.result.then(function () {
				var promise = $http({
					method: 'GET',
					url: $rootScope.site.apiServer + "/api/image/del",
					params:{
						imgId:id
					}
				});
				promise.then(function (res, status, config, headers) {
					$rootScope.loadingState = false;
					if(res.data.code == 1){
						$scope.search();
						growl.addSuccessMessage("数据加载完毕。。。");
					}else{

					}

				});
			});
		};
		$scope.sortImg = function (id,flg) {
			var promise = $http({
				method: 'GET',
				url: $rootScope.site.apiServer + "/api/image/sort",
				params:{
					imgId:id,
					flg:flg
				}
			});
			promise.then(function (res, status, config, headers) {
				$rootScope.loadingState = false;
				if(res.data.code == 1){
					$scope.search();
					growl.addSuccessMessage("数据加载完毕。。。");
				}else{

				}

			});
		};






		$scope.search();





	}
}

export default CarouselController;
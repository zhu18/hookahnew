class CarouselController {
	constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
		$scope.search = function () {
			var promise = $http({
				method: 'GET',
				url: $rootScope.site.websiteServer + "/api/image/list",
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
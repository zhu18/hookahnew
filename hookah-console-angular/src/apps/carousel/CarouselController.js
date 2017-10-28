class CarouselController {
	constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
		console.log('qweqweqweqweqw')
		$scope.search = function (initCurrentPage) {
			var promise = $http({
				method: 'GET',
				url: $rootScope.site.apiServer + "/api/shelf/all",
				params: {
					currentPage: initCurrentPage == 'true' ? 1 : $rootScope.pagination.currentPage,
					pageSize: $rootScope.pagination.pageSize,
					shelfName: $scope.shelfName
				}
			});
			promise.then(function (res, status, config, headers) {
				$rootScope.loadingState = false;
				growl.addSuccessMessage("数据加载完毕。。。");
			});
		};




		$scope.pageChanged = function () {
			$scope.search();
			console.log('Page changed to: ' + $rootScope.pagination.currentPage);
		};

		$scope.search('true');

		$scope.updateStatus = function (item, flag) {
			console.log(item.shelvesId, item.shelvesStatus);
			var promise = $http({
				method: 'POST',
				url: $rootScope.site.apiServer + "/api/shelf/updateStatus",
				params: {shelvesId: item.shelvesId, shelvesStatus: flag}
			});
			promise.then(function (res, status, config, headers) {
				if (res.data.code == 1) {
					$scope.search();
				}
			});
		};




	}
}

export default CarouselController;
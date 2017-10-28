class ShelfController {
	constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
		$scope.search = function () {
			var promise = $http({
				method: 'GET',
				url: $rootScope.site.apiServer + "/api/storage/findAllStorages",
			});
			promise.then(function (res, status, config, headers) {
				$rootScope.loadingState = false;
				$scope.storages = res.data.data;
				growl.addSuccessMessage("数据加载完毕。。。");
			});
		};
		$scope.deleteStorage = function (id) {
			var confirm = $rootScope.openConfirmDialogModal("确认要将删除该货架吗？");
			confirm.result.then(function () {
				var promise = $http({
					method: 'GET',
					url: $rootScope.site.apiServer + "/api/storage/delete",
					params: {id: id}
				});
				promise.then(function (res, status, config, headers) {
					$scope.search();
					growl.addSuccessMessage("数据加载完毕。。。");
				});
			});
		};

		$scope.search();

	}
}

export default ShelfController;
class ShelfController {
	constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
		$scope.search = function (initCurrentPage) {
			var promise = $http({
				method: 'GET',
				url: $rootScope.site.apiServer + "/api/storage/findAllStorages",
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
		$scope.add = function () {
			var promise = $http({
				method: 'POST',
				url: $rootScope.site.apiServer + "/api/shelf/add",
				data: $("#shelfForm").serialize()
			});
			promise.then(function (res, status, config, headers) {
				console.log(res.data)
				if (res.data.code == "1") {
					growl.addSuccessMessage("数据添加完毕。。。");
					$state.go('shelf.search');
				}
			});
		};

		$scope.update = function () {
			var promise = $http({
				method: 'POST',
				url: $rootScope.site.apiServer + "/api/shelf/update",
				data: $("#shelfForm").serialize()
			});
			promise.then(function (res, status, config, headers) {
				console.log(res.data)
				if (res.data.code == "1") {
					growl.addSuccessMessage("数据修改完毕。。。");
					$state.go('shelf.search');
				}
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

		$scope.manageGoods = function (event, item) {
			console.log(item);
			// $state.go('shelf.manageGoods', {pageTitle: item});
			console.log("即将进货架管理……");
		};

		$scope.updateShelf = function (event, item) {
			console.log(item);
			$rootScope.editData = item;
			$state.go('shelf.update', {data: item});
		};

		$scope.img_upload = function (files, type, name) {//单次提交图片的函数
			var fileUrl = $rootScope.site.staticServer + '/upload/img';
			var fd = new FormData();
			var file = files[0];
			fd.append('filename', file);
			var promise = $http({
				method: 'POST',
				url: fileUrl,
				data: fd,
				headers: {'Content-Type': undefined},
				transformRequest: angular.identity
			});
			promise.then(function (res, status, config, headers) {
				$rootScope.loadingState = false;
				if (res.data.code == 1) {
					$scope.goodsImgView = res.data.data[0].absPath;
				} else {
					growl.addErrorMessage("上传失败");
				}
			});
		};

	}
}

export default ShelfController;
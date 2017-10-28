class EditController {
	constructor($scope, $rootScope, $http, $state,$stateParams, $uibModal, usSpinnerService, growl) {
		
		$scope.add = function () {
			var url = '';
			if($stateParams.type == 'add'){
				url = "/api/storage/create"
			}else{
				url = "/api/storage/update"
			}
			var promise = $http({
				method: 'POST',
				url: $rootScope.site.apiServer + url,
				data: $("#shelfForm").serialize()
			});
			promise.then(function (res, status, config, headers) {
				if (res.data.code == "1") {
					alert($scope.pageTitle+"成功");
					$state.go('shelf.search');
				}
			});
		};
		$scope.loadData = function (id) {
			var promise = $http({
				method: 'POST',
				url: $rootScope.site.apiServer + "/api/storage/findStorage",
				params: {
					id:id
				}
			});
			promise.then(function (res, status, config, headers) {
				if (res.data.code == "1") {
					$scope.name = res.data.data.name;
					$scope.id = res.data.data.id;
					$scope.describle = res.data.data.describle;
					$scope.img = res.data.data.img;
					$scope.goodsImgView = res.data.data.img;
					$scope.stOrder = res.data.data.stOrder;
				}
			});
		};
		if($stateParams.type == 'add'){
			$scope.pageTitle = '添加';
			$scope.isModify = false;
		}else{
			$scope.pageTitle = '修改';
			$scope.loadData($stateParams.id);
			$scope.isModify = true;
			$scope.storageId = $stateParams.id;
		}

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

export default EditController;
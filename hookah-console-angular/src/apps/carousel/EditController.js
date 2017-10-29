class EditController {
	constructor($scope, $rootScope, $http, $state,$stateParams, $uibModal, usSpinnerService, growl) {
		var url= '';
		$scope.add = function () {
			var promise = $http({
				method: 'POST',
				url: url,
				data: {homeImageVo:JSON.stringify($scope.pageData)},
				transformRequest: function (obj) {
					var str = [];
					for (var p in obj) {
						str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
					}
					return str.join("&");
				}
			});
			promise.then(function (res, status, config, headers) {
				if (res.data.code == "1") {
					alert($scope.pageTitle+"成功");
					$state.go('carousel.search');
				}
			});
		};
		$scope.loadData = function () {
			var promise = $http({
				method: 'POST',
				url: $rootScope.site.apiServer + "/api/image/findById",
				params: {imgId:$stateParams.id}
			});
			promise.then(function (res, status, config, headers) {
				if (res.data.code == "1") {
					$scope.pageData = res.data.data;
				}
			});
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
					$scope.pageData.imgUrl = res.data.data[0].absPath;
				} else {
					growl.addErrorMessage("上传失败");
				}
			});
		};



		if($stateParams.type == 'add'){
			$scope.pageTitle = '添加'
			url = $rootScope.site.apiServer + "/api/image/add";
		}else{
			$scope.pageTitle = '修改'
			url = $rootScope.site.apiServer + "/api/image/edit";
			$scope.loadData()
		}

	}
}

export default EditController;
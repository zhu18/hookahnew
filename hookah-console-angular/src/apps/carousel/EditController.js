class EditController {
	constructor($scope, $rootScope, $http, $state,$stateParams, $uibModal, usSpinnerService, growl) {
		if($stateParams.type == 'add'){
			$scope.pageTitle = '添加'
		}else{
			$scope.pageTitle = '修改'
		}

		$scope.add = function () {
			$scope.pageData.imgLink=$scope.goodsImgView;
			var promise = $http({
				method: 'POST',
				url: $rootScope.site.apiServer + "/api/image/add",
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
					alert("数据添加完毕。。。");
					$state.go('carousel.search');
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
				} else {
					growl.addErrorMessage("上传失败");
				}
			});
		};

	}
}

export default EditController;
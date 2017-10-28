class ManageGoodsController {
	constructor($scope, $rootScope, $stateParams, $http, $state, $uibModal, usSpinnerService, growl) {
		$scope.pageTitle = $stateParams.name;
		$scope.editTagBox = false;
		$scope.search = function () {
			var promise = $http({
				method: 'GET',
				url: $rootScope.site.apiServer + "/api/storage/findDetail",
				params:{storageId:$stateParams.id}
			});
			promise.then(function (res, status, config, headers) {
				$rootScope.loadingState = false;
				$scope.pageData = res.data.data;
			});
		};
		$scope.editTags = function(lists){  //点击编辑
			$scope.editTagBox = true; //显示编辑窗口
			$scope.tagsItem = lists;
			$scope.labelBox = false;
			$scope.labelSelected = [];
			$scope.goodsImgView = lists.img;
			if($scope.goodsImgView){
				$scope.goodsImgView = lists.img;
			}else{
				$('.fileImg').attr('src','');
			}
			if(lists.labels && lists.labelsName ){
				var ids = lists.labels.split(','),
					names = lists.labelsName.split(','),
					arr = [];
				for(var i=0; i<ids.length; i++){
					arr.push({
						id:ids[i],
						name:names[i]
					})
				}
				$scope.labelSelected = arr;
			}
			console.log($scope.labelSelected)
			$scope.selectLabel = function () {
				var arr = [];
				if ($scope.labelSelected) {
					$scope.labelSelected.forEach(function (item) {
						arr.push(item.id)
					})
				}

				var promise = $http({
					method: 'GET',
					url: $rootScope.site.apiServer + "/api/label/findAll",
				});
				promise.then(function (res, status, config, headers) {
					$rootScope.loadingState = false;
					if (res.data.code == 1) {
						if ($scope.labelSelected.length > 0) {
							$scope.labelAll = res.data.data;
							$scope.labelAll.forEach(function (data) {
								if (data.goodsLabels && data.goodsLabels.length > 0) {
									data.goodsLabels.forEach(function (item) {
										if (arr.join(",").indexOf(item.labId) >= 0) {
											item.isChecked = true;
										} else {
											item.isChecked = false;
										}
									})
								}
							});
						} else {
							$scope.labelAll = res.data.data;
							$scope.labelAll.forEach(function (data) {
								if (data.goodsLabels && data.goodsLabels.length > 0) {
									data.goodsLabels.forEach(function (item) {
										item.isChecked = false;
									})
								}
							});
						}
						$scope.labelBox = true;
					} else {
						growl.addErrorMessage(res.data.message);
					}
				});
			};
			$("[name=tagsCheck]:checkbox").click(function () { //选择标签提示
				var nums = 0;
				$("[name=tagsCheck]:checkbox").each(function () {
					if (this.checked) {
						nums += 1;
					}
				});
				if (nums <= 0) {
					$('.tags-tip').css('color', 'red');
				} else {
					$('.tags-tip').css('color', '#767676');
				}
			});
			$scope.selectLabelBtn = function () { //选择标签之后的确定按钮
				var tags = [];
				$("[name=tagsCheck]:checkbox").each(function () {
					if (this.checked) {
						tags.push(
							{
								id: $(this).val(),
								name: $(this).attr('tagname')
							}
						);
					}
				});
				if (tags.length <= 0) {
					$rootScope.openErrorDialogModal('最少选择1个标签');
				} else {
					$scope.labelSelected = tags;
					setLabel();
					$scope.labelBox = false;
				}
			};
			$scope.removeLabel = function (target) {
				$(target).parent('.tags-list').remove();
				var tags = [];
				$(".tags-list").each(function () {
					tags.push(
						{
							id: $(this).attr('labid'),
							name: $(this).attr('labname')
						}
					);
				});
				$scope.labelSelected = tags;
				setLabel()
			};
			function setLabel() {
				var ids = [],
					names = [];
				if ($scope.labelSelected.length > 0) {
					$scope.labelSelected.forEach(function (item) {
						ids.push(item.id);
						names.push(item.name);
					});
				}
				lists.labels = ids.join(',');
				lists.labelsName = names.join(',');
			}
			$scope.addTagsItem = function(){
				$scope.pageData.typeList.forEach(function (item) {
					if(item.typeId == $scope.tagsItem.typeId){
						item.typeName = $scope.tagsItem.typeName;
						item.describle = $scope.tagsItem.describle;
						item.img = $scope.goodsImgView;
						item.labels = lists.labels;
						item.labelsName = lists.labelsName;
					}
				});
				$scope.editTagBox = false;
			};
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
		$scope.getGoodsList = function () {
			var promise = $http({
				method: 'GET',
				url: $rootScope.site.apiServer + "/api/goods/allNotInStorage",
				params: {
					currentPage: $rootScope.pagination.currentPage,
					pageSize: $rootScope.pagination.pageSize,
					storageId:$stateParams.id
					}
			});
			promise.then(function (res, status, config, headers) {
				if(res.data.code == 1){

				}
			});
		};




		$scope.getGoodsList()
		$scope.search()


	}
}

export default ManageGoodsController;
class EditTagsController {
	constructor($scope, $rootScope, $stateParams, $http, $state, $uibModal, usSpinnerService, growl) {
		$scope.labelBox = false;
		$scope.labelSelected = [];
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
		}

		$("[name=tagsCheck]:checkbox").click(function () {
			var nums = 0;
			$("[name=tagsCheck]:checkbox").each(function () {
				if (this.checked) {
					nums += 1;
				}
			});
			if (nums > 3) {
				$('.tags-tip').css('color', 'red');
			} else {
				$('.tags-tip').css('color', '#767676');
			}
		});
		$('.checkTags-confirmBtn').click(function () {
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
			if (tags.length > 3) {
				$rootScope.openErrorDialogModal('最多选择3个标签');
			} else {

				$scope.labelSelected = tags;
				setLabel();
				$('.tags-box').hide();
			}
		});
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
		}
	}
}

export default EditTagsController;
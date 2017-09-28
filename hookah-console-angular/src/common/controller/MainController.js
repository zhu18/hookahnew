class MainController {
	constructor($scope, $rootScope, $uibModal, usSpinnerService) {

		$rootScope.openComponentModal = function (message) {
			var modalInstance = $uibModal.open({
				animation: true,
				template: require('../uploadModal.html'),
				controller: function ($scope, $sce, $uibModalInstance, message) {
					$scope.message = $sce.trustAsHtml(message);

					$scope.ok = function () {
						$uibModalInstance.close($scope.selected.item);
					};

					$scope.cancel = function () {
						$uibModalInstance.dismiss('cancel');
					};
				},
				// size: 'lg',
				backdrop: 'static',
				resolve: {
					message: function () {
						return message;
					}
				}
			});
			modalInstance.result.then(function () {
				//
			}, function () {
				// $log.info('Modal dismissed at: ' + new Date());
			});
		};

		$rootScope.openErrorDialogModal = function (message) {
			var modalInstance = $uibModal.open({
				animation: true,
				template: require('../errorDialogModal.html'),
				controller: function ($scope, $sce, $uibModalInstance, message) {
					$scope.message = $sce.trustAsHtml(message);

					$scope.ok = function () {
						$uibModalInstance.close($scope.selected.item);
					};

					$scope.cancel = function () {
						$uibModalInstance.dismiss('cancel');
					};
				},
				// size: 'lg',
				backdrop: 'static',
				resolve: {
					message: function () {
						return message;
					}
				}
			});
			modalInstance.result.then(function () {
				//
			}, function () {
				// $log.info('Modal dismissed at: ' + new Date());
			});
		};
		$rootScope.openJustShowDialogModal = function (message) {
			var modalInstance = $uibModal.open({
				animation: true,
				template: require('../justShowDialogModal.html'),
				controller: function ($scope, $sce, $uibModalInstance, message) {
					$scope.message = $sce.trustAsHtml(message);

					$scope.ok = function () {
						$uibModalInstance.close($scope.selected.item);
					};

					$scope.cancel = function () {
						$uibModalInstance.dismiss('cancel');
					};
				},
				// size: 'lg',
				backdrop: 'static',
				resolve: {
					message: function () {
						return message;
					}
				}
			});
			modalInstance.result.then(function () {
				//
			}, function () {
				// $log.info('Modal dismissed at: ' + new Date());
			});
		};

		$rootScope.openConfirmDialogModal = function (message) {
			return $uibModal.open({
				animation: true,
				template: require('../confirmDialogModal.html'),
				controller: function ($scope, $sce, $uibModalInstance, message) {
					$scope.message = $sce.trustAsHtml(message);
					$scope.ok = function () {
						$uibModalInstance.close($scope);
					};

					$scope.cancel = function () {
						$uibModalInstance.dismiss('cancel');
					};
				},
				// size: 'lg',
				backdrop: 'static',
				resolve: {
					message: function () {
						return message;
					}
				}
			});

		};
		$rootScope.openConfirmDialogModalSupplier = function (message) {
			return $uibModal.open({
				animation: true,
				template: require('../confirmDialogModalSupplier.html'),
				controller: function ($scope, $sce, $uibModalInstance, message) {
					$scope.message = $sce.trustAsHtml(message);
					$scope.ok = function () {
						$uibModalInstance.close($scope);
					};
					$scope.cancel = function () {
						$uibModalInstance.dismiss('cancel');
					};
				},
				// size: 'lg',
				backdrop: 'static',
				resolve: {
					message: function () {
						return message;
					}
				}
			});

		};
        $rootScope.openConfirmDialogModalCheck = function (message) {
            return $uibModal.open({
                animation: true,
                template: require('../confirmDialogModalCheck.html'),
                controller: function ($scope, $sce, $uibModalInstance, message) {
                    $scope.message = $sce.trustAsHtml(message);
                    $scope.ok = function () {
                        $uibModalInstance.close($scope);
                    };
                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };
                },
                // size: 'lg',
                backdrop: 'static',
                resolve: {
                    message: function () {
                        return message;
                    }
                }
            });

        };
		$rootScope.openConfirmDialogModel = function (title,content) {
			return $uibModal.open({
				animation: true,
				template: require('../confirmDialogModel.html'),
				controller: function ($scope, $sce, $uibModalInstance,title,content) {
					$scope.title = $sce.trustAsHtml(title);
					$scope.content = $sce.trustAsHtml(content);
					$scope.ok = function () {
						$uibModalInstance.close($scope);
					};
					$scope.cancel = function () {
						$uibModalInstance.dismiss('cancel');
					};
				},
				// size: 'lg',
				backdrop: 'static',
				resolve: {
					content: function () {
						return content;
					},
					title:function () {
						return title;
					}
				}
			});

		};
		/**
		 * 分页设置
		 */
		$rootScope.paginationSupport = true;
		$rootScope.pagination = {
			totalItems: 0,
			currentPage: 1,
			pageSize: 10,
			maxSize: 5,
			store: null
		};
		//loading提示
		$rootScope.loadingState = false;
		$rootScope.startSpin = function () {
			// console.log("spinner start");
			$rootScope.showSpinner = true;
			$rootScope.paginationSupport = false;
			$rootScope.loadingState = true;
			usSpinnerService.spin('showSpinner');
		};
		$rootScope.stopSpin = function () {
			// console.log("spinner stop");
			$rootScope.showSpinner = false;
			$rootScope.paginationSupport = true;
			$rootScope.loadingState = false;
			usSpinnerService.stop('showSpinner');
		};

	}
}

export default MainController;
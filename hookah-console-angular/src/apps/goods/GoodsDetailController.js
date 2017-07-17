class GoodsDetailController {
	constructor($scope, $rootScope, $http, $state,$stateParams, $uibModal, usSpinnerService, growl) {
		$scope.editData = $stateParams.data.data; //获取去审核传值-------------
		$scope.$flag = $stateParams.data.flag;
		$scope.packageApiInfo =  $scope.editData.packageApiInfo;
		$scope.current= "1";
		$scope.setCurrent = function (param) {
			$scope.current = param;
		};

	}
}

export default GoodsDetailController;
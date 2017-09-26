class MoveController {
    constructor($scope, $rootScope, $http, $state, $stateParams, $uibModal, usSpinnerService, growl) {
        $scope.getCategory = function(){
			var promise = $http({
				method: 'POST',
				url: $rootScope.site.apiServer + "/api/category/findOneGoodsType",
			});
			promise.then(function (res, status, config, headers) {
				$rootScope.loadingState = false;
				if (res.data.code == 1) {
					console.log(res.data.data);
					$scope.dataTemps = res.data.data;
					if(catData.goodsTypeId == null || catData.goodsTypeId == ""){
						$scope.dataTemp = $scope.dataTemps[0].typeId;
					}else {
						$scope.dataTemp = catData.goodsTypeId;
					}

				}
			});
        }


    }
}

export default MoveController;
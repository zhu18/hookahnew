class MoveController {
    constructor($scope, $rootScope, $http, $state, $stateParams, $uibModal, usSpinnerService, growl) {
        $scope.getCategory = function(num,type){
			var promise = $http({
				method: 'get',
				url: $rootScope.site.websiteServer + "/category/findByPId/1",
				params:{
					pid:num
				}
			});
			promise.then(function (res, status, config, headers) {
				$rootScope.loadingState = false;
				if (res.data.code == 1) {
					if(num == '-1'){
						$scope.dataTemp2 = null;
						$scope.dataTemp3 = null;
					}
					if(type == 'dataTemp1'){
						$scope.dataTemp2 = null;
						$scope.dataTemp3 = null;
						if(res.data.data.length > 0){
							$scope.dataTemp1 = res.data.data;
							$scope.dataTemp1v = '-1';
						}
					}else if(type == 'dataTemp2'){
						$scope.dataTemp3 = null;
						if(res.data.data.length > 0) {
							$scope.dataTemp2 = res.data.data;
							$scope.dataTemp2.unshift({catId: "-1", catName: "全部"})
							$scope.dataTemp2v = '-1';
						}
						$scope.getGoodsList(num)
					}else if(type == 'dataTemp3'){
						if(res.data.data.length > 0) {
							$scope.dataTemp3 = res.data.data;
							$scope.dataTemp3.unshift({catId: "-1", catName: "全部"})
							$scope.dataTemp3v = '-1';
						}
						$scope.getGoodsList(num)
					}else if(type == 'dataTemp4'){
						$scope.getGoodsList(num)
					}
					if(type == 'dataTemp1'){
						$scope.dataTempB = null;
						$scope.dataTempC = null;
						if(res.data.data.length > 0){
							$scope.dataTempA = res.data.data;
							$scope.dataTempA.unshift({catId: "-1", catName: "全部"})
							$scope.dataTempAv = '-1';
						}
					}else if(type == 'dataTempB'){
						$scope.dataTempC = null;
						if(res.data.data.length > 0) {
							$scope.dataTempB = res.data.data;
							$scope.dataTempB.unshift({catId: "-1", catName: "全部"})
							$scope.dataTempBv = '-1';
						}
					}else if(type == 'dataTempC'){
						if(res.data.data.length > 0) {
							$scope.dataTempC = res.data.data;
							$scope.dataTempC.unshift({catId: "-1", catName: "全部"})
							$scope.dataTempCv = '-1';
						}
					}else if(type == 'dataTempD'){
						console.log(res.data.data.length)
					}

				}
			});
        };
		$scope.getGoodsList = function(num){
			var promise = $http({
				method: 'get',
				url: $rootScope.site.websiteServer + "/category/findByCatId",
				params:{
					catId:num
				}
			});
			promise.then(function (res, status, config, headers) {
				$rootScope.loadingState = false;
				if (res.data.code == 1) {
					$scope.goodsArr = res.data.data;

				}
			});
		};

		$scope.getCategory(0,'dataTemp1');

    }
}

export default MoveController;
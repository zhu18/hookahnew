class MoveController {
    constructor($scope, $rootScope, $http, $state, $stateParams, $uibModal, usSpinnerService, growl) {
		$scope.goodsArrS = true;
		$scope.goodsArrS_txt = '请选择分类';
		$scope.evaluate = function(){
			if($scope.dataTemp3v){
				$scope.dataFrom = $scope.dataTemp3v
			}else if($scope.dataTemp2v){
				$scope.dataFrom = $scope.dataTemp2v
			}else if($scope.dataTemp1v){
				$scope.dataFrom = $scope.dataTemp1v
			}else{
				$scope.dataFrom = null;
			}
			if($scope.dataTempCv){
				$scope.dataEnd = $scope.dataTempCv
			}else if($scope.dataTempBv){
				$scope.dataEnd = $scope.dataTempBv
			}else if($scope.dataTempAv){
				$scope.dataEnd = $scope.dataTempAv
			}else{
				$scope.dataEnd = null;
			}
			if($scope.goodsV && $scope.goodsV.length > 0){
				$scope.goodsVd = true;
			}else{
				$scope.goodsVd = false;
			}

		}
        $scope.getCategory = function(num,type){
			$scope.goodsV = null;
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
							$scope.dataTemp1v = null;
						}
					}else if(type == 'dataTemp2'){
						$scope.dataTemp3 = null;
						if(res.data.data.length > 0) {
							$scope.dataTemp2 = res.data.data;
							$scope.dataTemp2.unshift({catId: null, catName: "全部"})
							$scope.dataTemp2v = null;
						}
						$scope.getGoodsList(num)
					}else if(type == 'dataTemp3'){
						if(res.data.data.length > 0) {
							$scope.dataTemp3 = res.data.data;
							$scope.dataTemp3.unshift({catId: null, catName: "全部"})
							$scope.dataTemp3v = null;
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
							$scope.dataTempA.unshift({catId: null, catName: "全部"})
							$scope.dataTempAv = null;
						}
					}else if(type == 'dataTempB'){
						$scope.dataTempC = null;
						if(res.data.data.length > 0) {
							$scope.dataTempB = res.data.data;
							$scope.dataTempB.unshift({catId: null, catName: "全部"})
							$scope.dataTempBv = null;
						}
					}else if(type == 'dataTempC'){
						if(res.data.data.length > 0) {
							$scope.dataTempC = res.data.data;
							$scope.dataTempC.unshift({catId: null, catName: "全部"})
							$scope.dataTempCv = null;
						}
					}else if(type == 'dataTempD'){
					}
					$scope.evaluate()
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
					if(res.data.data.length <= 0){
						$scope.goodsArrS = true;
					}else{
						$scope.goodsArrS = false;
						$scope.goodsArrS_txt = '暂无商品';
					}
				}
			});
		};

		$scope.getCategory(0,'dataTemp1');
		$scope.pushData = function(){
			var promise = $http({
				method: 'POST',
				url: $rootScope.site.websiteServer + "/category/confirmTransfer",
				params:{
					catId:$scope.dataEnd,
					goodsIds:$scope.goodsV.join(",")
				}
			});
			promise.then(function (res, status, config, headers) {
				$rootScope.loadingState = false;
				if (res.data.code == 1) {
					growl.addSuccessMessage("迁移成功...");
					if($scope.dataTemp3v){
						$scope.getCategory($scope.dataTemp3v,'dataTemp4');
					}else if($scope.dataTemp2v){
						$scope.getCategory($scope.dataTemp2v,'dataTemp3');
					}else if($scope.dataTemp1v){
						$scope.getCategory($scope.dataTemp1v,'dataTemp2');
					}else{
						$scope.getCategory(100000,'dataTemp1');
					}
				}
			});
		}

















    }
}

export default MoveController;
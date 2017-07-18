class GoodsDetailController {
	constructor($scope, $rootScope, $http, $state,$stateParams, $uibModal, usSpinnerService, growl) {
		$scope.editData = $stateParams.data.data; //获取去审核传值-------------
		$scope.$flag = $stateParams.data.flag;
		$scope.packageApiInfo =  $scope.editData.packageApiInfo;
		$scope.current= "1";
		$scope.setCurrent = function (param) {
			$scope.current = param;
		};


        $scope.submitCheck = function () {

            if ($('input[name="checkStatus"]:checked').val() == '2' && $("#checkContent").val().trim() == '') {
                $rootScope.openErrorDialogModal('请填写审核意见^_^');
            } else {
                if($scope.editData.goodsType == 1 && $scope.editData.isOffline == 0 ){

                    $scope.addData = {
                        goodsCheck: {
                            goodsId: $scope.editData.goodsId,//商品ID
                            goodsSn: $scope.editData.goodsSn,//商品编号
                            goodsName: $scope.editData.goodsName,//商品名称
                            checkStatus: $('input[name="checkStatus"]:checked').val(),
                            checkContent: $('#checkContent').val()
                        },
                        apiInfoBean: $scope.packageApiInfo
                    }
                    var promise = $http({
                        method: 'post',
                        url: $rootScope.site.apiServer + "/api/goodsCheck/addApi",
                        data: "voStr="+JSON.stringify($scope.addData)
                    });
                    promise.then(function (res, status, config, headers) {
                        if (res.data.code == "1") {
                            $state.go('items.check');
                        }
                    });
                }else{

                    $scope.dataGoods={};
                    $scope.dataGoods.goodsId = $('#goodsId').val();
                    $scope.dataGoods.goodsSn = $('#goodsSn').val();
                    $scope.dataGoods.goodsName = $('#goodsName').val();
                    $scope.dataGoods.checkStatus = $('input[name="checkStatus"]:checked').val();
                    $scope.dataGoods.checkContent = $('#checkContent').val();
                    var promise = $http({
                        method: 'post',
                        url: $rootScope.site.apiServer + "/api/goodsCheck/add",
                        data: "voStr="+JSON.stringify($scope.dataGoods)
                    });
                    promise.then(function (res, status, config, headers) {
                        if (res.data.code == "1") {
                            $state.go('items.check');
                        }
                    });
                }
            }

        }
	}
}

export default GoodsDetailController;
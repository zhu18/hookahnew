class GoodsDetailController {
	constructor($scope, $rootScope, $http, $state,$stateParams, $uibModal, usSpinnerService, growl) {
		if($stateParams.isEdit == 'true'){
			$scope.$flag = true;
			$scope.detailTitle = '商品审核';
        }else{
			$scope.$flag = false;
			$scope.detailTitle = '商品详情';
        }
        if($stateParams.id){   //商品详情
			var promise = $http({
				method: 'GET',
				url: $rootScope.site.apiServer + "/api/goods/getGoodsInfo",
				params: {goodsId: $stateParams.id}
			});
			promise.then(function (res, status, config, headers) {
				if(res.data.code == "1"){
					$scope.editData = res.data.data;
					$scope.packageApiInfo = res.data.data.packageApiInfo;
				}
			});
        }else{
            alert('此商品错误');
			$state.go('items.search',{data:null});
        }
		$scope.current= "1";
		$scope.setCurrent = function (param) {
			$scope.current = param;
		};
        $scope.submitCheck = function () { // 提交审核
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

        } //提交审核

        // $scope.returnPage = function () { //返回
        //
        //     // $scope.$item('data',$stateParams.data.searchCondition);
        //     $state.go('items.search', {
        //         data:{
        //             searchCd:$stateParams.data.searchCondition,
        //             flag:true
        //
        //         }
        //     });
        // } //返回
	}
}

export default GoodsDetailController;
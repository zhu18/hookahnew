class TagsListController {
	constructor($scope, $rootScope, $state, $stateParams, $http, $sce, $uibModal, usSpinnerService, growl) {
		$scope.delTags = function(item){ //移除标签
			var confirm = $rootScope.openConfirmDialogModal("确认要删除此标签吗？");
			confirm.result.then(function () {
				// var promise = $http({
				// 	method: 'POST',
				// 	url: $rootScope.site.apiServer + "/api/goods/off",
				// 	params: {goodsId: item.goodsId}
				// });
				// promise.then(function (res, status, config, headers) {
				// 	if (res.data.code == "1") {
				// 		growl.addSuccessMessage("操作成功");
				// 		$scope.refresh();
				// 	}
				// });
			})
		};//移除标签
		$scope.addTags = function(item){//添加标签
$scope.asdf='';
			winds(null);
			function winds(vals){
				var title1 = '新增标签信息';
				if(vals){
					var content = '<div> <label for="" class="col-sm-3">标签名称：</label> <textarea name="" id="checkContent" cols="60" rows="10"></textarea><br><span style="color:red; margin-left: 148px;">标签信息不能为空</span></div> ';
				}else{
					var content = '<div> <label for="" class="col-sm-3">标签名称：</label> <textarea name="" id="checkContent" cols="60" rows="10"></textarea> <p>ps：每个标签最多4个汉字，标签之间用英文‘,’进行分割</p></div> ';
				}
				var modalInstance = $rootScope.openConfirmDialogModel(title1,content);

				modalInstance.result.then(function () { //模态点提交
					if($('#checkContent').val().trim()){
						var testTags = /^([\u4E00-\u9FA5A-Za-z0-9]{1,10})(,([\u4E00-\u9FA5A-Za-z0-9]{1,10}))*$/;
						// var promise = $http({
						// 	method: 'POST',
						// 	url: $rootScope.site.apiServer + "/api/goods/forceOff",
						// 	params: {
						// 		goodsId: item.goodsId,
						// 		offReason:$('#checkContent').val()
						// 	}
						// });
						// promise.then(function (res, status, config, headers) {
						// 	if (res.data.code == "1") {
						// 		growl.addSuccessMessage("操作成功");
						// 		$scope.refresh();
						// 	}
						// });
					}else{
						winds('下架理由不能为空');
					}
				},function(){
				});
			}
		};//添加标签


	}
}

export default TagsListController;
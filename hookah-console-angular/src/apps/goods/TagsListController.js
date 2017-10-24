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
			winds(null,null);
			function winds(vals,tip){
				var title1 = '新增标签信息';
				var tips = '';
				if(tip == 'one'){
					tips = '开头和结尾是否存在‘,’，并且不能有连续两个以上';
				}else if(tip == 'two'){
					tips = '中文为4个字，英文和数字为8个字符，中文和英文、数字组合不能超过6个';
				}else if(tip == 'nul'){
					tips = '不能为空';
				}
				if(vals){
					var content = '<div> <label for="" class="col-sm-3">标签名称：</label> <textarea name="" id="checkContent" cols="60" rows="10">'+vals+'</textarea><br><span style="color:red; margin-left: 148px;">'+tips+'</span></div> ';
				}else{
					var content = '<div> <label for="" class="col-sm-3">标签名称：</label> <textarea name="" id="checkContent" cols="60" rows="10"></textarea> <p style=" margin-left: 148px;">ps：每个标签最多4个汉字，标签之间用英文‘,’进行分割</p></div> ';
				}
				var modalInstance = $rootScope.openConfirmDialogModel(title1,content);

				modalInstance.result.then(function () { //模态点提交
					if($('#checkContent').val().trim()){
						// var testTagsOne = /^[^,](?!.*?[,]{2,}).*[^,]$/;
						// var testTagsTwo = /^([\u4E00-\u9FA5]{1,4}|[\u4E00-\u9FA5A-Za-z0-9]{1,6}|[A-Za-z0-9]{1,8})(,([\u4E00-\u9FA5]{1,4}|[\u4E00-\u9FA5A-Za-z0-9]{1,6}|[A-Za-z0-9]{1,8}))*$/;
						var regex ={
							one : /^[^,](?!.*?[,]{2,}).*[^,]$/, //验证开头和结尾是否存在‘,’，并且不能有连续两个以上
							two : /^([\u4E00-\u9FA5]{1,4}|[\u4E00-\u9FA5A-Za-z0-9]{1,6}|[A-Za-z0-9]{1,8})(,([\u4E00-\u9FA5]{1,4}|[\u4E00-\u9FA5A-Za-z0-9]{1,6}|[A-Za-z0-9]{1,8}))*$/ //验证中文为4个字，英文和数字为8个字符，中文和英文、数字组合不能超过6个
						};
						if(regex.one.test($('#checkContent').val())){
							if(regex.two.test($('#checkContent').val())){
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
								winds($('#checkContent').val(),'two');
							}
						}else{
							winds($('#checkContent').val(),'one');
						}

					}else{
						winds('下架理由不能为空','nul');
					}
				},function(){
				});
			}
		};//添加标签
		$scope.search = function(){
			var promise = $http({
				method: 'GET',
				url: $rootScope.site.apiServer + "/api/label/findAll",
			});
			promise.then(function (res, status, config, headers) {
				if (res.data.code == "1") {
					
				}
			});
		}

	}
}

export default TagsListController;
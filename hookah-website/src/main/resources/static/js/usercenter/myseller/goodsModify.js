var goodsId = $.getUrlParam('id');
getGoodsDetails(); //根据id获取商品详情
function getGoodsDetails(){
	$.ajax({
		url:host.website+'/goods/back/findById',
		type:'get',
		data:{
			id:goodsId
		},
		success:function(data){
			if(data.code == 1){
				var data = data.data;

				renderData(data);
			}else{
				$.alert(data.message);
			}
		}
	})
}
function renderData(data){//渲染页面
	$('#J-goodsName').val(data.goodsName);
	$('#J-goodsBrief').val(data.goodsBrief);
	$('input[name="goodsTypes"]').each(function(){
		if($(this).val() == data.goodsType){
			$(this).attr('checked','true')
		}
	});
	getAttrFn(data.catId,data.attrTypeList);
}
function getAttrFn(catId,attrTypeList){ //获取商品属性
	$.ajax({
		url: host.website+'/category/findAttr',
		type: "get",
		data: {
			catId: catId.substring(0,3)
		},
		success: function (data) {
			if (data.code == 1) {
				// alert(JSON.stringify(data.data))
				renderselect(data.data,attrTypeList);//渲染商品属性
			} else {
				$.alert(data.message)
			}
		}
	});
}
function renderselect(data,attrTypeList) {
	if (data) {
		var html = '';
		var typeList = data.attrTypeList;
		for (var i = 0; i < typeList.length; i++) {
			$.each(attrTypeList,function(index,data){
				alert(index+":"+data.xx+":"+data["yy"]);
			})
			html += '<tr>';
			html += '<td>' + typeList[i].typeName + '：</td>';
			html += ' <td>';
			html += '<select data-placeholder="请选择' + typeList[i].typeName + '" name="' + typeList[i].typeName + '" typeid="' + typeList[i].typeId + '" style="width:560px;" multiple class="chosen-select chosen-select-' + i + '" tabindex="8">';
			for (var j = 0; j < typeList[i].attrList.length; j++) {
				html += '<option value="' + typeList[i].attrList[j].attrId + '">' + typeList[i].attrList[j].attrName + '</option>';
			}
			html += '</select>';
			html += '</td>';
			html += '</tr>';
		}
		$('.attribute-box table').append(html);
		$('.chosen-select').chosen(); //初始化select 多选插件
	} else {
		$('.attribute-container').remove();
	}
}

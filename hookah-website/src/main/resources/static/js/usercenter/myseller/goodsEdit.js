var regionParam = 100000;
var catId = $.getUrlParam('catId'),
	category = $.getUrlParam('category');
$(document).ready(function(){
	$("#goodsModifyForm").validate({
		rules: {
			goodsName:  {
				required: true,
				isGoodsName:true
			},
			goodsBrief:{
				required: true,
				isGoodsBrief:true
			},
			goodsImg:'required',
			priceBoxName:'required',
			priceBoxNumber:'required',
			priceBoxPrice:'required',
			goodsImges:'required',
			goodsImges2:'required',
			goodsDescBox:'required'
		},
		messages: {
			goodsName:  {
				required: '商品名称不能为空',
				isGoodsName:'长度为10-60个字符（每个汉字为2个字符）'
			},
			goodsBrief:  {
				required: '商品简介不能为空',
				isGoodsBrief:'长度为30-400个字符（每个汉字为2个字符）'
			},
			goodsImges:'图片必须上传',
			goodsImges2:'文件必须上传',
			goodsDescBox:'商品描述不能为空'

		},
		showErrors:function(errorMap,errorList) {
			if(errorList.length){
				errorList[0].element.focus();
			}
			this.defaultShowErrors();
		}
	});
	$('.category-title-box').text(category);//渲染分类
	loadData(); //加载商品属性数据
	if ($('input[name="goodsTypes"]').val() == 0) {
		$('.api-info-box').hide();
		$('.file-info-box').show();
	} else {
		$('.file-info-box').hide();
		$('.api-info-box').show();
	}
	getPriceBox();
	loadRegion('province', regionParam); //加载地域
	renderWangEdit(); // 渲染富文本
});
function renderWangEdit(){
	var editor = new wangEditor('textarea1');
	editor.config.uploadImgUrl = host.static+'/upload/wangeditor';//上传图片（举例）
	editor.config.uploadImgFileName = 'filename';
	editor.config.menuFixed = false;//关闭菜单栏fixed
	editor.config.menus = $.map(wangEditor.config.menus, function (item, key) {
		if (item === 'location') {
			return null;
		}
		if (item === 'video') {
			return null;
		}
		return item;
	});
	editor.create(); //初始化富文本
}
//加载地区
function loadRegion(id,regionParam) {
	var parentId = '';
	if(regionParam == 100000){
		parentId = 100000;
	}else{
		parentId = $(regionParam).val();
	}
	$(regionParam).nextAll().html('<option value="-1"></option>')
	$.ajax({
		type: "get",
		url: host.website+'/region/getRegionCodeByPid',
		data: {
			parentId: parentId
		},
		success: function (data) {
			if (data.code == 1) {
				if(data.data.length > 0){
					renderRegion(id,data.data)
				}
			} else {
				$.alert(data.message)
			}
		}
	});
}
function renderRegion(id,data){
	var html = '<option value="-1">全部</option>';
	data.forEach(function(e){
		html += '<option value="'+e.id+'">'+e.name+'</option>';
	});
	$('#'+id).html(html);
}
$('#fileupload').fileupload({   //图片上传
	url: host.static+'/upload/fileUpload',
	dataType: 'json',
	done: function (e, data) {
		if(data.result.code == 1){
			var obj = data.result.data[0];
			$("#preview-img").attr("src", obj.absPath);
			$('input[name="goodsImges"]').val(obj.absPath);
		}else{
			$.alert(data.result.message)
		}
	},
	progressall: function (e, data) {

	}
});
$('#fileupload2').fileupload({
	url: host.static+'/upload/fileUpload',
	dataType: 'json',
	done: function (e, data) {
		if(data.result.code == 1){
			var obj = data.result.data[0];
			$("#J_fileUploadSS").val(obj.filePath);
			$('.fileUploads span').html(data.files[0].name);
			$('input[name="goodsImges2"]').val(obj.absPath);
		}else{
			$.alert(data.result.message)
		}

	},
	progressall: function (e, data) {

	}
});
function loadData() {
	$.ajax({
		type: "get",
		url: host.website+'/category/findAttr',
		data: {
			catId: catId.substring(0,3)
		},
		success: function (msg) {
			if (msg.code == 1) {
				renderselect(msg.data)//渲染商品属性
			} else {
				$.alert(msg.message)
			}
		}
	});
}
function renderselect(data) {
	if (data) {
		var html = '';
		var typeList = data.attrTypeList;
		for (var i = 0; i < data.attrTypeList.length; i++) {
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
function goodsTypeChange(that) {
	if ($(that).val() == 0) {
		$('.api-info-box').hide();
		$('.file-info-box').show();
	} else {
		$('.file-info-box').hide();
		$('.api-info-box').show();
	}
}
var itemNum = 0;
function tablePlus(that) {
	if($(that).parents('.price-table').attr('d-type') == 'requestHtml'){
		itemNum = $(that).parents('tbody').children('.parent-tr').length;
	}
	var priceHtml = '';//规格与价格
	priceHtml += '<tr class="parent-tr">';
	priceHtml += '<td class="name-input"><div class="inputbox"><input type="text" datatype="name" placeholder="请输入名称"></div></td>';
	priceHtml += '<td class="number-input"><div class="inputbox"><input type="number" datatype="number" placeholder="请输入规格"></div></td>';
	priceHtml += '<td><div class="selectbox"><select name="format" ><option value="0">次</option><option value="1">天</option><option value="2">年</option></select></div></td>';
	priceHtml += '<td class="price-input"><div class="inputbox"><input type="number" datatype="price" placeholder="请输入价格"></div></td>';
	priceHtml += '<td><span class="table-plus-btn" onclick="tablePlus(this)">+</span></td>';
	priceHtml += '</tr>';
	var requestHtml = '';//请求接口
	requestHtml += '<tr class="parent-tr">';
	requestHtml += '<td class="name-input"><div class="inputbox"><input type="text" name="fieldName" placeholder="请输入名称"></div></td>';
	requestHtml += '<td class="type-input"><div class="selectbox"><select name="fieldType"><option value="String">String</option><option value="int">int</option></select></div></td>';
	requestHtml += '<td class="type-input"><div class="inputbox"><input type="text" name="fieldDefault" placeholder="请输入默认值"></div></td>';
	requestHtml += '<td><div class="radio-box"><label><input type="radio" name="isMust' + itemNum + '" checked value="0">否</label><label><input type="radio" name="isMust' + itemNum + '" value="1">是</label></div></td>';
	requestHtml += '<td><div class="inputbox"><input type="text" name="fieldSample" placeholder="请输入示例"></div></td>';
	requestHtml += '<td><div class="inputbox"><textarea  placeholder="请输入描述" name="describle"></textarea></div></td>';
	requestHtml += '<td><span class="table-plus-btn itemNum" onclick="tablePlus(this)">+</span></td>';
	requestHtml += '</tr>';
	var returnHtml = '';//返回借口
	returnHtml += '<tr class="parent-tr">';
	returnHtml += '<td class="errorNum-input"><div class="inputbox"><input type="text" placeholder="请输入错误码" name="fieldNames"></div></td>';
	returnHtml += '<td><div class="inputbox"><textarea placeholder="请输入说明" name="describles"></textarea></div></td>';
	returnHtml += '<td><span class="table-plus-btn" onclick="tablePlus(this)">+</span></td>';
	returnHtml += '</tr>';
	if ($(that).parents('.table-plus').attr('d-type') == 'priceHtml') {
		if ($(that).parent().siblings('.number-input').find('input').val() && $(that).parent().siblings('.price-input').find('input').val()) {
			$(that).parents('.table-plus tbody').append(priceHtml);
			addItem(that)
		} else {
			$.alert('请完善本条信息',true,function(){});
		}
	} else if ($(that).parents('.table-plus').attr('d-type') == 'requestHtml') {
		if ($(that).parent().siblings('.name-input').find('input').val() && $(that).parent().siblings('.type-input').find('input').val()) {
			$(that).parents('.table-plus tbody').append(requestHtml);
			addItem(that)
		} else {
			$.alert('请完善本条信息',true,function(){});
		}
	} else if ($(that).parents('.table-plus').attr('d-type') == 'returnHtml') {
		if ($(that).parent().siblings('.errorNum-input').find('input').val()) {
			$(that).parents('.table-plus tbody').append(returnHtml);
			addItem(that)
		} else {
			$.alert('请完善本条信息',true,function(){});
		}
	}
	function addItem(that) {
		$(that).text('-').attr('onclick', 'tableDelete(this)');
	}
}
function tableDelete(that) {
	$(that).parents('.parent-tr').remove();
	getPriceBox()
}
function getPriceBox(){
	var priceBox = $('table[d-type="priceHtml"] tbody >:first');
	priceBox.find('input[datatype="name"]').attr('name','priceBoxName');
	priceBox.find('input[datatype="number"]').attr('name','priceBoxNumber');
	priceBox.find('input[datatype="price"]').attr('name','priceBoxPrice');
}
function getLength(str){
	return str.replace(/[\u0391-\uFFE5]/g,"aa").length;
}
$('#J-goodsName').on('input onporpertychange',function () {
	$('#showcontent').html(getLength($(this).val()));
});
$('#J-goodsBrief').on('input onporpertychange',function () {
	$('#showcontent2').html(getLength($(this).val()));
});
$.validator.addMethod("isGoodsName", function(value, element) {
	var len = value.replace(/[\u0391-\uFFE5]/g,"aa").length;
	return this.optional(element) || (10 <= len && len <= 60);
}, "长度为10-60个字符（每个汉字为2个字符）");
$.validator.addMethod("isGoodsBrief", function(value, element) {
	var len = value.replace(/[\u0391-\uFFE5]/g,"aa").length;
	return this.optional(element) || (30 <= len && len <= 400);
}, "长度为30-400个字符（每个汉字为2个字符）");
$('#J_submitBtn').click(function(){
	if($("#goodsModifyForm").valid()){
		if($('#textarea1').val()){
			backAddFn(submitGoodsPublish())
		}else{
			$.alert('商品描述不能为空',true,function () {
			})
		}
	}
});
function backAddFn(data){
	Loading.start();
	$.ajax({
		type: 'POST',
		url: '/goods/back/add',
		data: JSON.stringify(data),
		dataType: 'json',
		contentType: 'application/json',
		success: function (data) {
			if (data.code == "1") {
				Loading.stop();
				$('.pusGoods-btn').addClass('trues');
				$.confirm('<h3 style="font-weight: 800">提交成功</h3><p>继续发布商品吗?</p>', null, function (type) {
					if (type == 'yes') {
						window.location.href = "/usercenter/goodsPublish";
					} else {
						window.location.href = "/usercenter/goodsWait";
					}
				});
			} else {
				Loading.stop()
				$.alert(data.message, true);
			}
		}
	});
}
function submitGoodsPublish(){
	var data = {};
	data.goodsName = $('input[name="goodsName"]').val();
	data.goodsBrief = $('textarea[name="goodsBrief"]').val();
	data.attrTypeList = [];
	$('.chosen-select').each(function () {
		var attrTypeList = {};
		attrTypeList.typeId = $(this).attr('typeid');
		attrTypeList.typeName = $(this).attr('name');
		var attrAs = $(this).val();
		var attrBs = [];
		for(var i=0;i<attrAs.length;i++){
			var json = {};
			json['attrId']=attrAs[i];
			attrBs.push(json)
		}
		attrTypeList.attrList =attrBs;
		data.attrTypeList.push(attrTypeList);
	});
	data.goodsImg = $('#preview-img').attr('src');
	data.formatList = [];

	$('table[d-type="priceHtml"] tbody tr').each(function () {
		var listData = {};
		listData.formatId = $(this).index();
		listData.formatName = $(this).find('input[datatype="name"]').val();
		listData.number = $(this).find('input[datatype="number"]').val();
		listData.format = $(this).find('select[name="format"]').val();
		listData.price = ($(this).find('input[datatype="price"]').val()) * 100;
		data.formatList.push(listData);

	});
	// alert(JSON.stringify(data.formatList));
	data.shopNumber = data.formatList[0].number;
	data.shopFormat = data.formatList[0].format;
	data.shopPrice = data.formatList[0].price;
	data.goodsType = $('input[name="goodsTypes"]:checked').val();
	data.goodsDesc = $('#textarea1').val();
	data.catId = catId; //此ID为url上的id
	data.isBook = $('input[name="isBook"]:checked').val();
	if (data.isBook == 1) {
		data.onsaleStartDate = $('#indate').val();
	}
	if($('select[name="city"]').val() > 0){
		data.goodsArea = $('select[name="city"]').val();
	}else if($('select[name="province"]').val() > 0){
		data.goodsArea = $('select[name="province"]').val();
	}else if($('select[name="country"]').val() > 0){
		data.goodsArea = $('select[name="country"]').val();
	}
	if (data.goodsType == 1) {
		data.apiInfo = {};
		data.apiInfo.apiUrl = $('.api-info-box').find('input[name="apiUrl"]').val();
		data.apiInfo.apiMethod = $('.api-info-box').find('input[name="apiMethod"]:checked').val();
		data.apiInfo.reqSample = $('.api-info-box').find('input[name="reqSample"]').val();
		data.apiInfo.apiDesc = $('.api-info-box').find('textarea[name="apiDesc"]').val();
		data.apiInfo.reqParamList = [];
		$('table[d-type="requestHtml"] tbody tr').each(function (i, item) {
			var listData = {};
			listData.fieldName = $(item).find('input[name="fieldName"]').val();
			listData.fieldType = $(item).find('select[name="fieldType"]').val();
			listData.isMust = $(item).find('input[name="isMust' + i + '"]:checked').val();
			listData.fieldSample = $(item).find('input[name="fieldSample"]').val();
			listData.fieldDefault = $(item).find('input[name="fieldDefault"]').val();
			listData.describle = $(item).find('textarea[name="describle"]').val();
			data.apiInfo.reqParamList.push(listData);
		});
		data.apiInfo.respParamList = [];
		$('table[d-type="returnHtml"] tbody tr').each(function () {
			var listData = {};
			listData.fieldName = $(this).find('input[name="fieldNames"]').val();
			listData.describle = $(this).find('textarea[name="describles"]').val();
			data.apiInfo.respParamList.push(listData);
		});
		data.apiInfo.respSample = $('.api-info-box').find('textarea[name="respSample"]').val();
	}else{
		data.uploadUrl = $('#J_fileUploadSS').val();
	}
	return data;
}
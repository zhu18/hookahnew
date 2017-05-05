var goodsId = $.getUrlParam('id');
var attrIds = [];
var regionParam = 100000;
var catId = '';
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
	catId = data.catId;
	$('.category-title-box').html(data.catFullName);
	$('#J-goodsName').val(data.goodsName);//商品名称
	$('#J-goodsBrief').val(data.goodsBrief);//简介
	$('input[name="goodsTypes"]').each(function(){
		if($(this).val() == data.goodsType){
			$(this).attr('checked','true')
		}
	});
	goodsTypes(data.goodsType,data.apiInfo,data.uploadUrl);
	getAttrFn(data.catId); //获取属性
	$.each(data.attrTypeList,function(index,items){
		$.each(items.attrList,function(index,item){
			attrIds.push(item.attrId);
		});
	});
	$('#preview-img').attr('src',data.goodsImg);//图片
	$('input[name="goodsImg"]').val(data.goodsImg);
	if(data.formatList && data.formatList.length > 0){
		renderFormatList(data.formatList);//渲染价格
	}
	if(data.goodsDesc){
		renderGoodsDeac(data.goodsDesc);
	}
	$('input[name="isBook"]').each(function(){
		if($(this).val() == data.isBook){
			$(this).attr('checked','true')
		}
	});
	renderIsBook(data.isBook, data.onsaleStartDate);
	$('#showcontent').html(getLength($('#J-goodsName').val()));
	$('#showcontent2').html(getLength($('#J-goodsBrief').val()));
	if(data.areaCountry > 0){
		loadCountry(data.areaCountry,data.areaProvince)
	}
	if(data.areaProvince > 0){
		loadCity(data.areaProvince,data.areaCity)
	}
	initialize(); // 初始化数据
}
//加载地区
function loadCountry(idCountry,idProvince) {
	$.ajax({
		type: "get",
		url: host.website+'/region/getRegionCodeByPid',
		data: {
			parentId: idCountry
		},
		success: function (data) {
			if (data.code == 1) {
				if(data.data.length > 0){
					renderOneRegion(idProvince,data.data)
				}
			} else {
				$.alert(data.message)
			}
		}
	});
}
function renderOneRegion(idProvince,data){
	var html = '<option value="-1">全部</option>';
	data.forEach(function(e){
		if(e.id == idProvince){
			html += '<option selected="selected" value="'+e.id+'">'+e.name+'</option>';
		}else{
			html += '<option value="'+e.id+'">'+e.name+'</option>';
		}
	});
	$('#province').html(html);
}
function loadCity(idProvince,idCity) {
	$.ajax({
		type: "get",
		url: host.website+'/region/getRegionCodeByPid',
		data: {
			parentId: idProvince
		},
		success: function (data) {
			if (data.code == 1) {
				if(data.data.length > 0){
					renderTwoRegion(idCity,data.data)
				}
			} else {
				$.alert(data.message)
			}
		}
	});
}
function renderTwoRegion(idCity,data){
	var html = '<option value="-1">全部</option>';
	data.forEach(function(e){
		if(e.id == idCity){
			html += '<option selected="selected" value="'+e.id+'">'+e.name+'</option>';
		}else{
			html += '<option value="'+e.id+'">'+e.name+'</option>';
		}
	});
	$('#city').html(html);
}
function renderIsBook(isBook, onsaleStartDate){
	if(isBook == 1){
		$('#indate').val(onsaleStartDate);
		$.jeDate("#indate", {
			format: "YYYY-MM-DD hh:mm:ss",
			isTime: true,
			minDate: $.nowDate(0)
		});
	}else{
		$.jeDate("#indate", {
			format: "YYYY-MM-DD hh:mm:ss",
			isTime: true,
			minDate: $.nowDate(0)
		});
	}
}
function isOnsaleFun(that) {
	if ($(that).val() == 1) {
		$('.isOnsale-box').show();
	} else {
		$('.isOnsale-box').hide();
	}
}
function renderGoodsDeac(goodsDesc){
	$('#textarea1').val(goodsDesc);
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
function renderFormatList(formatList){
	var html = '';
	var len = formatList.length;
	$.each(formatList,function(index,data){
		html += '<tr class="parent-tr">';
		html += '<td class="name-input"><div class="inputbox"><input type="text" datatype="name" value="'+data.formatName+'" placeholder="请输入名称"></div></td>';
		html += '<td class="number-input"><div class="inputbox"><input type="text" datatype="number" value="'+data.number+'" digits="true" placeholder="请输入规格" min="0"></div></td>';
		html += '<td><div class="selectbox"><select name="format">';
		if(data.format == 0){
			html += '<option value="0" selected="selected">次</option>';
		}else{
			html += '<option value="0">次</option>';
		}
		if(data.format == 1){
			html += '<option value="1" selected="selected">天</option>';
		}else{
			html += '<option value="1">天</option>';
		}
		if(data.format == 2){
			html += '<option value="2" selected="selected">年</option>';
		}else{
			html += '<option value="2">年</option>';
		}
		html += '</select></div></td>';
		html += '<td class="price-input"><div class="inputbox"><input class="price-input number " min="0" datatype="price" value="'+data.price / 100+'" type="text" placeholder="请输入价格"></div></td>';
		if(index === len-1){
			html += '<td><span class="table-plus-btn" onclick="tablePlus(this)">+</span></td>';
		}else{
			html += '<td><span class="table-plus-btn" onclick="tableDelete(this)">-</span></td>';
		}

		html += '</tr>';
	});
	$('table[d-type="priceHtml"] tbody').html(html);
}
function getAttrFn(catId){ //获取商品属性
	$.ajax({
		url: host.website+'/category/findAttr',
		type: "get",
		data: {
			catId: catId.substring(0,3)
		},
		success: function (data) {
			if (data.code == 1) {
				// alert(JSON.stringify(data.data))
				renderselect(data.data);//渲染商品属性
			} else {
				$.alert(data.message)
			}
		}
	});
}
function renderselect(data) {
	if (data) {
		var html = '';
		var typeList = data.attrTypeList;
		for (var i = 0; i < typeList.length; i++) {
			html += '<tr>';
			html += '<td>' + typeList[i].typeName + '：</td>';
			html += ' <td>';
			html += '<select data-placeholder="请选择' + typeList[i].typeName + '" name="' + typeList[i].typeName + '" typeid="' + typeList[i].typeId + '" style="width:560px;" multiple class="chosen-select chosen-select-' + i + '" tabindex="8">';
			for (var j = 0; j < typeList[i].attrList.length; j++) {
				if($.inArray(typeList[i].attrList[j].attrId, attrIds) != -1){
					html += '<option selected="selected" value="' + typeList[i].attrList[j].attrId + '">' + typeList[i].attrList[j].attrName + '</option>';
				}else{
					html += '<option value="' + typeList[i].attrList[j].attrId + '">' + typeList[i].attrList[j].attrName + '</option>';
				}
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
// loadRegion('province', regionParam);
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
$('#preview-div').hover(function () {
	if ($('#preview-img').attr('src')) {
		$('#replace-btn').toggle();
	}
});
var fileUploadUrl = host.static+'/upload/fileUpload'; //图片上传
$('#fileupload').fileupload({
	url: fileUploadUrl,
	dataType: 'json',
	done: function (e, data) {
		if(data.result.code == 1){
			var obj = data.result.data[0];
			$("#preview-img").attr("src", obj.absPath);
			$('input[name="goodsImg"]').val(obj.absPath);
			imgSrc = obj.absPath;
		}else{
			$.alert(data.result.message)
		}
	},
	progressall: function (e, data) {

	}
});
function goodsTypes(goodsType,apiInfo,uploadUrl) {
	if (goodsType == 0) {
		$('.api-info-box').hide();
		$('.file-info-box').show();
		if(uploadUrl){
			renderfileUploadInfo(uploadUrl);
		}
	} else {
		$('.file-info-box').hide();
		$('.api-info-box').show();
		if(apiInfo){
			renderApiInfo(apiInfo);
		}
	}
}
function goodsTypes2(that) {
	if ($(that).val() == 0) {
		$('.api-info-box').hide();
		$('.file-info-box').show();
	} else {
		$('.file-info-box').hide();
		$('.api-info-box').show();
	}
}
function renderfileUploadInfo(uploadUrl){
	$('#J_fileUploadSS').val(uploadUrl);
	$('#J_fileUploadName').html(uploadUrl);
}
function renderApiInfo(apiInfo){
	$('input[name="apiUrl"]').val(apiInfo.apiUrl);
	$('input[name="apiMethod"]').each(function(){
		if($(this).val() == apiInfo.apiMethod){
			$(this).attr('checked','true')
		}
	});
	$('input[name="reqSample"]').val(apiInfo.reqSample);
	$('textarea[name="apiDesc"]').val(apiInfo.apiDesc);
	var html = '';
	var reqLen = apiInfo.reqParamList.length;
	$.each(apiInfo.reqParamList,function (index,data) {
		html +='<tr class="parent-tr">';
		html +='<td class="name-input"><div class="inputbox "><input type="text" name="fieldName" placeholder="请输入名称" value="'+data.fieldName+'" required="required"></div></td>';
		html +='<td class="type-input"><div class="selectbox"><select name="fieldType">';
		if(data.fieldType == 'String'){
			html +='<option value="String" selected="selected">String</option>';
		}else{
			html +='<option value="String">String</option>';
		}
		if(data.fieldType == 'int'){
			html +='<option value="int" selected="selected">int</option>';
		}else{
			html +='<option value="int">int</option>';
		}
		html +='</select></div></td>';
		html +='<td class="type-input"><div class="inputbox"><input type="text" name="fieldDefault" placeholder="请输入默认值" value="'+data.fieldDefault+'" required="required"></div></td>';
		html +='<td>';
		html +='<div class="radio-box">';
		if(data.isMust == 0){
			html +='<label><input type="radio" name="isMust'+index+'" checked="checked" value="0">否</label>';
		}else{
			html +='<label><input type="radio" name="isMust'+index+'" value="0">否</label>';
		}
		if(data.isMust == 1){
			html +='<label><input type="radio" name="isMust'+index+'" checked="checked" value="1">是</label>';
		}else{
			html +='<label><input type="radio" name="isMust'+index+'" value="1">是</label>';
		}
		html +='</div>';
		html +='</td>';
		html +='<td><div class="inputbox"><input type="text" name="fieldSample" placeholder="请输入示例" value="'+data.fieldSample+'" required="required"></div></td>';
		html +='<td><div class="inputbox"><textarea name="describle" placeholder="请输入描述" required="required">'+data.describle+'</textarea></div></td>';
		if(index === reqLen-1){
			html +='<td><span class="table-plus-btn" onclick="tablePlus(this)">+</span></td>';
		}else{
			html +='<td><span class="table-plus-btn" onclick="tableDelete(this)">-</span></td>';
		}
		html +='</tr>';
	});
	$('table[d-type="requestHtml"] tbody').html(html);
	$('textarea[name="respSample"]').val(apiInfo.respSample);
	var html2 = '';
	var resqLen = apiInfo.respParamList.length;
	$.each(apiInfo.respParamList, function (index,data) {
		html2 += '<tr class="parent-tr">';
		html2 += '<td class="errorNum-input"><div class="inputbox"><input type="text" name="fieldName" value="'+data.fieldName+'" placeholder="请输入错误码"></div></td>';
		html2 += '<td><div class="inputbox"><textarea name="describle" placeholder="请输入说明">'+data.describle+'</textarea></div></td>';
		if(index === reqLen-1) {
			html2 += '<td><span class="table-plus-btn" onclick="tablePlus(this)">+</span></td>';
		}else{
			html2 += '<td><span class="table-plus-btn" onclick="tableDelete(this)">-</span></td>';
		}
		html2 += '</tr>';
	});
	$('table[d-type="returnHtml"] tbody').html(html2);
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
			alert('请输入规格和价格');
		}
	} else if ($(that).parents('.table-plus').attr('d-type') == 'requestHtml') {
		if ($(that).parent().siblings('.name-input').find('input').val() && $(that).parent().siblings('.type-input').find('input').val()) {
			$(that).parents('.table-plus tbody').append(requestHtml);
			addItem(that)
		} else {
			alert('请输入相关内容');
		}
	} else if ($(that).parents('.table-plus').attr('d-type') == 'returnHtml') {
		if ($(that).parent().siblings('.errorNum-input').find('input').val()) {
			$(that).parents('.table-plus tbody').append(returnHtml);
			addItem(that)
		} else {
			alert('请输入相关内容');
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
function initialize() {
	getPriceBox();
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
			priceBoxPrice:'required'
		},
		messages: {
			goodsName:  {
				required: '必填',
				isGoodsName:'长度为10-60个字符（每个汉字为2个字符）'
			},
			goodsBrief:  {
				required: '必填',
				isGoodsBrief:'长度为30-400个字符（每个汉字为2个字符）'
			},
			goodsImg:'图片必须上传',

		}
	});
	$('.price-input').focus(function(){
		$(this).css('color','#333');
	});
	$('.price-input').blur(function(){
		var num = $(this).val();
		if(!isNaN(num)){
			var dot = num.indexOf(".");
			if(dot != -1){
				var dotCnt = num.substring(dot+1,num.length);
				if(dotCnt.length > 2){
					$.alert("小数位最多2位！",true,function () {
					});
					$(this).css('color','red');
				}
			}
		}
	});
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
		backAddFn(submitGoodsPublish())
	}
});
function backAddFn(data){
	Loading.start();
	$.ajax({
		type: 'POST',
		url: '/goods/back/update',
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
	data.goodsId = goodsId;
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
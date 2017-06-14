var regionParam = 100000;
var catId = $.getUrlParam('catId'),
	category = $.getUrlParam('category');
$(document).ready(function(){
	if(!catId){
		window.location.href = '/usercenter/goodsManage';
	}
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
			priceBoxPrice:{
				required:true,
				isPricceData:true,
				isPricceB:true
			},
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
	// if ($('input[name="goodsTypes"]').val() == 0) {
	// 	$('.api-info-box').hide();
	// 	$('.file-info-box').show();
	// } else {
	// 	$('.file-info-box').hide();
	// 	$('.api-info-box').show();
	// }
	getPriceBox();
	loadRegion('province', regionParam); //加载地域
	renderWangEdit(); // 渲染富文本
	floorPrice();
	var goodsTypeVal = $('#parentSelect').val();
	selectGoodsTypes(goodsTypeVal);
	$('#preview-div').mouseover(function(){
		if($('#preview-img').attr('src')){
			$('#replace-btn').show()
		}
	});
	$('#preview-div').mouseout(function(){
		$('#replace-btn').hide()
	});
	$('.struct.selects').hide();
	$('.childrenSelect').hide();
	$('.file-info-box').hide();
	if(catId.substring(0,3) == 101){
		$('#parentSelect').val(100).attr('disabled','disabled');
		selectGoodsTypes(100)
	}else if(catId.substring(0,3) == 102){
		$('#parentSelect').val(2).attr('disabled','disabled');
		selectGoodsTypes(2)
	}else if(catId.substring(0,3) == 104){
		$('#parentSelect').val(300).attr('disabled','disabled');
		selectGoodsTypes(300)
	}else{
		$('#parentSelect').val(400).attr('disabled','disabled');
		selectGoodsTypes(400)
	}
});
function selectGoodsType(that){
	$('.struct.selects').hide();
	$('.childrenSelect').hide();
	$('.file-info-box').hide();
	var goodsTypeVal = $(that).val();
	selectGoodsTypes(goodsTypeVal)
}
function childrenSelects(that){
	$('.file-info-box').hide();
	$('.struct.selects').hide();
	var childVal = $(that).val();
	if(childVal == 0){
		$('.file-info-box').show();
	}else if(childVal == 1){
		$('.api-info-box').show();
	}else if(childVal == 4){
		$('.tool-info-box').show();
		$('.tool-saas-info').show();
	}else if(childVal == 5){
		$('.tool-info-box').show();
		$('.tool-saas-info').hide();
	}else if(childVal == 6){
		$('.app-info-box').show();
		$('.app-saas-info').show();
	}else if(childVal == 7){
		$('.app-info-box').show();
		$('.app-saas-info').hide();
	}
}
function selectGoodsTypes(goodsTypeVal){
	if(goodsTypeVal == 100){
		$('#childrenSelect1').show();
		if($('#childrenSelect1').val() == 0){
			$('.file-info-box').show();
		}else if($('#childrenSelect1').val() == 1){
			$('.api-info-box').show();
		}
	}else if(goodsTypeVal == 300){
		$('#childrenSelect2').show();
		$('.app-info-box').show();
		if($('#childrenSelect2').val() == 6){
			$('.app-saas-info').show();
		}else if($('#childrenSelect2').val() == 7){
			$('.app-saas-info').hide();
		}
	}else if(goodsTypeVal == 400){
		$('#childrenSelect3').show();
		$('.tool-info-box').show();
		if($('#childrenSelect3').val() == 4){
			$('.tool-info-box').show();
		}else if($('#childrenSelect3').val() == 5){
			$('.tool-saas-info').hide();
		}
	}else{
		$('.dataModel-info-box').show();
	}
}
var editor=null;
function renderWangEdit(){
	editor = new wangEditor('textarea1');
	editor.config.uploadImgUrl = host.static+'/upload/wangeditor';//上传图片（举例）
	editor.config.uploadImgFileName = 'filename';
	editor.config.menuFixed = false;//关闭菜单栏fixed
	// editor.config.pasteText = true;
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
	add: function (e, data) {
		var filesize = data.files[0].size;
		if(Math.ceil(filesize / 1024) > 1024*5){
			console.log('文件过大'+filesize);
			$.alert('文件过大');
			return;
		}
		data.submit();
	},
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
		var progress = parseInt(data.loaded / data.total * 100, 10);
		if(progress > 0 && progress < 100){
			$('#bars').show()
		}else{
			$('#bars').hide()
		}
		$('#barSmail').css('width',progress + '%');
		$('#barText').html(progress + '%');
	}
});
$('#fileupload2').fileupload({ //文件上传
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
var itemNum = 0;
function tablePlus(that) {
	if($(that).parents('.price-table').attr('d-type') == 'requestHtml'){
		itemNum = $(that).parents('tbody').children('.parent-tr').length;
	}
	var priceHtml = '';//规格与价格
	priceHtml += '<tr class="parent-tr">';
	priceHtml += '<td class="name-input"><div class="inputbox"><input type="text" datatype="name" placeholder="请输入名称"></div></td>';
	priceHtml += '<td class="number-input"><div class="inputbox"><input type="number" datatype="number" digits="true" placeholder="请输入规格"></div></td>';
	priceHtml += '<td><div class="selectbox"><select name="format" ><option value="0">次</option><option value="1">天</option><option value="2">年</option></select></div></td>';
	priceHtml += '<td class="price-input"><div class="inputbox"><input type="text" class="price-inputs number" datatype="price" placeholder="请输入价格" isPricceData="true"></div></td>';
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
	returnHtml += '<td class="type-input"><div class="selectbox"><select name="fieldType"><option value="String">String</option><option value="int">int</option></select></div></td>';
	returnHtml += '<td><div class="inputbox"><textarea placeholder="请输入说明" name="describle"></textarea></div></td>';
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
	floorPrice();
}
floorPrice();
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
$.validator.addMethod("isPricceB", function(value, element) {
	var isPricce = false;
	if(value > 0){
		isPricce = true;
	}else{
		isPricce = false;
	}
	return this.optional(element) || isPricce;
}, "价格必须为正数");
$.validator.addMethod("isPricceData", function(value, element) {
	var isPricce = false;
	var dot = value.indexOf(".");
	if(dot != -1){
		var dotCnt = value.substring(dot+1,value.length);
		if(dotCnt.length > 2){
			isPricce = false;
		}else{
			isPricce = true;
		}
	}else{
		isPricce = true;
	}
	var len = value.replace(/[\u0391-\uFFE5]/g,"aa").length;
	return this.optional(element) || isPricce;
}, "小数点不能超过2位");
$('#J_submitBtn').click(function(){
	if($("#goodsModifyForm").valid()){
		if($.trim(editor.$txt.text()).length>0){
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
	data.shopNumber = data.formatList[0].number;
	data.shopFormat = data.formatList[0].format;
	data.shopPrice = data.formatList[0].price;
	if($('select[name="parentSelect"]').val() == '100'){//------------------------
		data.goodsType = $('select[name="childrenSelect1"]').val();
	}else if($('select[name="parentSelect"]').val() == '2'){
		data.goodsType = $('select[name="parentSelect"]').val()
	}else if($('select[name="parentSelect"]').val() == '300'){
		data.goodsType = $('select[name="childrenSelect2"]').val();
	}else if($('select[name="parentSelect"]').val() == '400'){
		data.goodsType = $('select[name="childrenSelect3"]').val();
	}
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
	if(data.goodsType == 0){
		data.uploadUrl = $('#J_fileUploadSS').val();
	}else if (data.goodsType == 1) {//------------------------------
		data.apiInfo = {};
		data.apiInfo.apiUrl = $('.api-info-box').find('input[name="apiUrl"]').val();
		data.apiInfo.apiMethod = $('.api-info-box').find('input[name="apiMethod"]:checked').val();
		data.apiInfo.reqSample = $('.api-info-box').find('input[name="reqSample"]').val();
		data.apiInfo.apiDesc = $('.api-info-box').find('#apiDesc').val();
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
			listData.fieldType = $(this).find('select[name="fieldType"]').val();
			listData.describle = $(this).find('textarea[name="describle"]').val();
			data.apiInfo.respParamList.push(listData);
		});
		console.log(data.apiInfo.respParamList);//----------------------
		data.apiInfo.respSample = $('#respSample').val();
	}else if(data.goodsType == 2){
		data.dataModel = {};
		data.dataModel.complexity = $('input[name="complexity"]').val();
		data.dataModel.maturity = $('input[name="maturity"]').val();
		data.dataModel.aexp = $('input[name="aexp"]').val();
		data.dataModel.modelFile = $('input[name="modelFile"]').val();
		data.dataModel.configFile = $('input[name="configFile"]').val();
		data.dataModel.configParams = $('input[name="configParams"]').val();
		data.dataModel.otherDesc = $('.dataModel-info-box .otherDesc').val();
	}else if(data.goodsType == 4){
		data.atAloneSoftware = {};
		data.atAloneSoftware.aTAloneIndustryField = $('input[name="aTIndustryField"]').val();
		data.atAloneSoftware.aTAloneVersionDesc = $('input[name="aTVersionDesc"]').val();
		data.atAloneSoftware.aTAloneToolsIntroduce = $('#aTToolsIntroduce').val();
		data.atAloneSoftware.aTAloneCloudHardwareResource = $('#aTAloneCloudHardwareResource').val();
		data.atAloneSoftware.otherDesc = $('.tool-info-box .otherDesc').val();
	}else if(data.goodsType == 5){
		data.atSaaS = {};
		data.atSaaS.aTIndustryField = $('input[name="aTIndustryField"]').val();
		data.atSaaS.aTVersionDesc = $('input[name="aTVersionDesc"]').val();
		data.atSaaS.aTToolsIntroduce = $('#aTToolsIntroduce').val();
		data.atSaaS.otherDesc = $('.tool-info-box .otherDesc').val();
	}else if(data.goodsType == 6){
		data.asAloneSoftware = {};
		data.asAloneSoftware.aSComplexity = $('input[name="aSComplexity"]').val();
		data.asAloneSoftware.aSVersionDesc = $('input[name="aSVersionDesc"]').val();
		data.asAloneSoftware.aSServiceLevel = $('input[name="aSServiceLevel"]').val();
		data.asAloneSoftware.aSAexp = $('input[name="aSAexp"]').val();
		data.asAloneSoftware.aSAintroduce = $('#aSAintroduce').val();
		data.asAloneSoftware.aSCloudHardwareResource = $('#aSCloudHardwareResource').val();
		data.asAloneSoftware.dataAddress = $('input[name="dataAddress"]').val();
		data.asAloneSoftware.otherDesc = $('.app-info-box .otherDesc').val();
	}else if(data.goodsType == 7){
		data.asSaaS = {};
		data.asSaaS.sSComplexity = $('input[name="aSComplexity"]').val();
		data.asSaaS.sSVersionDesc = $('input[name="aSVersionDesc"]').val();
		data.asSaaS.sServiceLevel = $('input[name="aSServiceLevel"]').val();
		data.asSaaS.sSAexp = $('input[name="aSAexp"]').val();
		data.asSaaS.sSAintroduce = $('#aSAintroduce').val();
		data.asSaaS.dataAddress = $('input[name="dataAddress"]').val();
		data.asSaaS.otherDesc = $('.app-info-box .otherDesc').val();

	}
	// alert(JSON.stringify(data));
	return data;

}
function isOnsaleFun(that) {
	if ($(that).val() == 1) {
		$('.isOnsale-box').show();
	} else {
		$('.isOnsale-box').hide();
	}
}
$.jeDate("#indate", {
	format: "YYYY-MM-DD hh:mm:ss",
	isTime: true,
	minDate: $.nowDate(0),
	choosefun: function(val) {
		$('#indate_s').val(val)
	}
});
function floorPrice(){
	$('.price-inputs').on('input onporpertychange',function () {
		var that = $(this);
		var num = that.val();
		if(num == '.'){
			that.val('0.');
		}
	});
}
$('.fileUploadBtn').fileupload({
	url: host.static+'/upload/fileUpload',
	dataType: 'json',
	done: function (e, data) {
		if(data.result.code == 1){
			var obj = data.result.data[0];
			$(this).parent('.uploadFiles').siblings('.fileEndInput').val(obj.filePath);
			$(this).siblings('span').html('替换文件');
			$(this).parent('.uploadFiles').siblings('.fileEndInputs').val(data.files[0].name);
		}else{
			$.alert(data.result.message)
		}

	},
	progressall: function (e, data) {

	}
});

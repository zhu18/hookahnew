var regionParam = 100000;
var userId = $("input[name='userId']").val();
var catId = $.getUrlParam('catId'),
	category = $.getUrlParam('category'),
	goodsId = $.getUrlParam('id');
var urlPath = window.location.pathname;
var E = window.wangEditor; //初始化富文本
var itemNum = 0; //添加规格计数器
var goodsTypeVal = $('#parentSelect').val();
var categoryHtml = '';
var ajaxUrl = null;
E.config.uploadImgUrl = host.static+'/upload/wangeditor';//上传图片
E.config.uploadImgFileName = 'filename';
E.config.menuFixed = false;//关闭菜单栏fixed
E.config.menus = $.map(wangEditor.config.menus, function (item, key) {
	if (item === 'location') {
		return null;
	}
	if (item === 'video') {
		return null;
	}
	return item;
});
$(document).ready(function(){

	if(urlPath == '/usercenter/goodsEdit'){ //上传商品
		if(!catId){
			window.location.href = '/usercenter/goodsManage';
		}else{
			goodsEditFn();
		}
		ajaxUrl = host.website+'/goods/back/add';
	}else if(urlPath == '/usercenter/goodsModify'){ //修改商品
		if(!goodsId){
			window.location.href='/usercenter/goodsOffSale';
		}else{
			goodsModifyFn();
		}
		ajaxUrl = host.website+'/goods/back/update';
	}

});
//商品添加-------------------------------↓↓↓↓↓↓↓↓↓↓↓↓↓
function goodsEditFn(){
	$('.select-box.display-inline-block').text(category);//渲染分类
	loadRegion('province', regionParam); //加载地区
	renderWangEdit(); // 渲染富文本
	uploadGoodsImg(); //上传商品图片
	floorPrice();//监控价格输入为“.”时转换为“0.”
	validataFn();//表单验证
	$('#preview-div').mouseover(function(){
		if($('#preview-img').attr('src')){
			$('#replace-btn').show()
		}
	});
	$('#preview-div').mouseout(function(){
		$('#replace-btn').hide()
	});
	selectGoodsTypes(goodsTypeVal);
	selectGoodsTypeFn();
	$("input[name='typeId']").val(catId.substring(0,3));
}
function selectGoodsTypeFn(){ //选择商品类型
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
}
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
	$('#isOffline').val('0');
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
function selectLineInfo(that){
	var isOffLineVal = $(that).val();
	var p_val = $('.parentSelect').val();
	var c_val = null;
	console.log(c_val)
	if(isOffLineVal == 1){
		$('.struct.selects').hide();
		$('.isOffLine-info-box').show();
	}else{
		$('.struct.selects').hide();
		if(p_val == 100){
			c_val = $('#childrenSelect1').val();
			if(c_val == 0){
				$('.file-info-box').show()
			}else{
				$('.api-info-box').show()
			}
		}else if(p_val == 300){
			c_val = $('#childrenSelect2').val();
			if(c_val == 6){
				$('.app-info-box').show();
				$('.app-saas-info').show();
			}else if(c_val == 7){
				$('.app-info-box').show();
				$('.app-saas-info').hide();
			}
		}else if(p_val == 400){
			c_val = $('#childrenSelect3').val();
			if(c_val == 4){
				$('.tool-info-box').show();
				$('.tool-saas-info').show();
			}else if(c_val == 5){
				$('.tool-info-box').show();
				$('.tool-saas-info').hide();
			}
		}else if(p_val == 2){
			$('.dataModel-info-box').show()
		}
	}
}
function selector_offLine_fn(that){
	$('.selector_offLine').hide();
	var vals = $(that).val();
	if(vals == 0){
		$('.selector_offLine_upLoad').show();
	}else{
		$('.selector_offLine_input').show();
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
function validataFn(){
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
}
function loadRegion(id,regionParam) {//加载地区
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
function renderWangEdit(){ // 渲染富文本
	editor1 = new E('textarea1');
	editor2 = new E('textarea2');
	editor3 = new E('textarea3');
	editor4 = new E('textarea4');
	editor1.create();
	editor2.create();
	editor3.create();
	editor4.create();
}
function uploadGoodsImg(){ //上传商品图片
	$('#fileupload').fileupload({   //图片上传
		url: host.static+'/upload/img',
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
				$('input[name="goodsImges"]').val(obj.filePath);
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
}
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
	returnHtml += '<td class="errorNum-input"><div class="inputbox"><input type="text" placeholder="请输入错误码" name="fieldName"></div></td>';
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
function floorPrice(){ //监控价格输入为“.”时转换为“0.”
	$('.price-inputs').on('input onporpertychange',function () {
		var that = $(this);
		var num = that.val();
		if(num == '.'){
			that.val('0.');
		}
	});
}
function tableDelete(that) { //删除规格及价格
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
function backAddFn(data){
	Loading.start();
	$.ajax({
		type: 'POST',
		url: ajaxUrl,
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
	if(urlPath == '/usercenter/goodsModify'){ //修改商品
		data.goodsId = goodsId;
		data.ver = $('input[name="ver"]').val();
	}
	data.goodsName = $('input[name="goodsName"]').val();
	data.goodsBrief = $('textarea[name="goodsBrief"]').val();
	data.keywords = $('input[name="keywords"]').val();
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
	data.goodsImg = $("input[name='goodsImges']").val();
	data.dataSample = $("input[name='dataSample_s']").val();
	data.trialRange = $('textarea[name="trialRange"]').val();
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
	data.goodsDesc = $('#textarea1').val(); //商品详情
	data.goodsAdvantage = $('#textarea2').val(); //商品优势
	data.afterSaleService = $('#textarea3').val(); //售后服务
	data.appCase = $('#textarea4').val(); //应用案例
	data.catId = catId; //此ID为url上的id
	data.isBook = $('input[name="isBook"]:checked').val(); //上架方式
	data.isOffline = $('select[name="isOffline"]').val(); // 交付方式  0 线上支付；1 线下支付
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
	if(data.isOffline == 0) {
		if (data.goodsType == 0) {
			data.uploadUrl = $('#J_fileUploadSS').val();
			data.offLineData = {};
			data.offLineData.isOnline = $('select[name="isOnline"]').val();
			data.offLineData.dataPwd = $('input[name="dataPwd"]').val();
			if(data.offLineData.isOnline == 0){
				data.offLineData.localUrl = $('#J_fileUploadSS').val();
			}else{
				data.offLineData.onlineUrl = $('input[name="onlineUrl"]').val();
			}
		} else if (data.goodsType == 1) {//API------------------------------
			data.apiInfo = {};
			data.apiInfo.apiType = $('.api-info-box').find('input[name="apiType"]:checked').val();
			data.apiInfo.invokeMethod = $('.api-info-box').find('input[name="invokeMethod"]').val();
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
				listData.fieldName = $(this).find('input[name="fieldName"]').val();
				listData.fieldType = $(this).find('select[name="fieldType"]').val();
				listData.describle = $(this).find('textarea[name="describle"]').val();
				data.apiInfo.respParamList.push(listData);
			});
			console.log(data.apiInfo.respParamList);//----------------------
			data.apiInfo.respSample = $('#respSample').val();
			data.apiInfo.respDataFormat = $('.api-info-box').find('input[name="respDataFormat"]:checked').val();
			data.apiInfo.respDataMapping = {};
			data.apiInfo.respDataMapping.codeAttrBean = {};
			data.apiInfo.respDataMapping.codeAttrBean.codeAttr = $('input[name="codeAttr"]').val();
			data.apiInfo.respDataMapping.codeAttrBean.codeInfoBean = {};
			data.apiInfo.respDataMapping.codeAttrBean.codeInfoBean.successCode = $('input[name="successCode"]').val();
			data.apiInfo.respDataMapping.codeAttrBean.codeInfoBean.failedCode = $('input[name="failedCode"]').val();
			data.apiInfo.respDataMapping.codeAttrBean.codeInfoBean.successNoData = $('input[name="successNoData"]').val();
			data.apiInfo.respDataMapping.infoAttr = $('input[name="infoAttr"]').val();
			data.apiInfo.respDataMapping.dataAttr = $('input[name="dataAttr"]').val();
			data.apiInfo.respDataMapping.totalNumAttr = $('input[name="totalNumAttr"]').val();
			data.apiInfo.updateFreq = $('input[name="updateFreq"]').val();
			data.apiInfo.dataNumDivRowNum = $('input[name="dataNumDivRowNum"]').val();
			data.apiInfo.encryptInfo = {};
			data.apiInfo.encryptInfo.secretKeyName = $('.api-info-box').find('input[name="secretKeyName"]').val();
			data.apiInfo.encryptInfo.secretKeyValue = $('.api-info-box').find('input[name="secretKeyValue"]').val();
		} else if (data.goodsType == 2) {
			data.dataModel = {};
			data.dataModel.complexity = $('input[name="complexity"]').val();
			data.dataModel.maturity = $('input[name="maturity"]').val();
			data.dataModel.aexp = $('input[name="aexp"]').val();
			data.dataModel.modelFile = $('input[name="modelFile"]').val();
			data.dataModel.modelFilePwd = $('input[name="modelFilePwd"]').val();
			data.dataModel.configFile = $('input[name="configFile"]').val();
			data.dataModel.configFilePwd = $('input[name="configFilePwd"]').val();
			data.dataModel.configParams = $('input[name="configParams"]').val();
			data.dataModel.configParamsPwd = $('input[name="configParamsPwd"]').val();
			data.dataModel.otherDesc = $('.dataModel-info-box .otherDesc').val();
			data.dataModel.concatInfo = {};
			data.dataModel.concatInfo.concatName = $('.dataModel_concat input[name="concatName"]').val();
			data.dataModel.concatInfo.concatPhone = $('.dataModel_concat input[name="concatPhone"]').val();
			data.dataModel.concatInfo.concatEmail = $('.dataModel_concat input[name="concatEmail"]').val();
		} else if (data.goodsType == 4) {
			data.atAloneSoftware = {};
			data.atAloneSoftware.aTAloneIndustryField = $('input[name="aTIndustryField"]').val();
			data.atAloneSoftware.aTAloneVersionDesc = $('input[name="aTVersionDesc"]').val();
			data.atAloneSoftware.aTAloneToolsIntroduce = $('#aTToolsIntroduce').val();
			data.atAloneSoftware.aTAloneCloudHardwareResource = $('#aTAloneCloudHardwareResource').val();
			data.atAloneSoftware.otherDesc = $('.tool-info-box .otherDesc').val();
			data.atAloneSoftware.dataAddress = $('.tool-info-box input[name="dataAddress"]').val();
		} else if (data.goodsType == 5) {
			data.atSaaS = {};
			data.atSaaS.aTIndustryField = $('input[name="aTIndustryField"]').val();
			data.atSaaS.aTVersionDesc = $('input[name="aTVersionDesc"]').val();
			data.atSaaS.aTToolsIntroduce = $('#aTToolsIntroduce').val();
			data.atSaaS.otherDesc = $('.tool-info-box .otherDesc').val();
			data.atSaaS.dataAddress = $('.tool-info-box input[name="dataAddress"]').val();
		} else if (data.goodsType == 6) {
			data.asAloneSoftware = {};
			data.asAloneSoftware.aSComplexity = $('input[name="aSComplexity"]').val();
			data.asAloneSoftware.aSVersionDesc = $('input[name="aSVersionDesc"]').val();
			data.asAloneSoftware.aSServiceLevel = $('input[name="aSServiceLevel"]').val();
			data.asAloneSoftware.aSAexp = $('input[name="aSAexp"]').val();
			data.asAloneSoftware.aSAintroduce = $('#aSAintroduce').val();
			data.asAloneSoftware.aSCloudHardwareResource = $('#aSCloudHardwareResource').val();
			data.asAloneSoftware.dataAddress = $('.app-info-box input[name="dataAddress"]').val();
			data.asAloneSoftware.otherDesc = $('.app-info-box .otherDesc').val();
		} else if (data.goodsType == 7) {
			data.asSaaS = {};
			data.asSaaS.sSComplexity = $('input[name="aSComplexity"]').val();
			data.asSaaS.sSVersionDesc = $('input[name="aSVersionDesc"]').val();
			data.asSaaS.sServiceLevel = $('input[name="aSServiceLevel"]').val();
			data.asSaaS.sSAexp = $('input[name="aSAexp"]').val();
			data.asSaaS.sSAintroduce = $('#aSAintroduce').val();
			data.asSaaS.dataAddress = $('.app-info-box input[name="dataAddress"]').val();
			data.asSaaS.otherDesc = $('.app-info-box .otherDesc').val();

		}
	}else if(data.isOffline == 1){
		data.offLineInfo = {};
		data.offLineInfo.concatName= $('.isOffLine-info-box input[name="concatName"]').val();
		data.offLineInfo.concatPhone= $('.isOffLine-info-box input[name="concatPhone"]').val();
		data.offLineInfo.concatEmail= $('.isOffLine-info-box input[name="concatEmail"]').val();
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
$('.fileUploadBtn').fileupload({
	url: host.static+'/upload/other',
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
$('#fileupload11').fileupload({ //文件上传
	url: host.static+'/upload/other',
	dataType: 'json',
	done: function (e, data) {
		if(data.result.code == 1){
			var obj = data.result.data[0];
			$("#dataSample").val(obj.filePath);
			$('.fileUploads_j span').html(data.files[0].name);
			$('input[name="dataSample_s"]').val(obj.filePath);
		}else{
			$.alert(data.result.message)
		}

	},
	progressall: function (e, data) {

	}
});
$('#fileupload2').fileupload({ //文件上传
	url: host.static+'/upload/other',
	dataType: 'json',
	done: function (e, data) {
		if(data.result.code == 1){
			var obj = data.result.data[0];
			$("#J_fileUploadSS").val(obj.filePath);
			$('.fileUploads span').html(data.files[0].name);
			$('input[name="goodsImges2"]').val(obj.filePath);
		}else{
			$.alert(data.result.message)
		}

	},
	progressall: function (e, data) {

	}
});
//商品修改-------------------------------↓↓↓↓↓↓↓↓↓↓↓↓↓
function goodsModifyFn(){
	getGoodsDetails();
}
function getGoodsDetails(){ //获取商品信息
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
				loadFirstCategory(data.catId); //获取第一个分类
			}else{
				$.alert(data.message);
			}
		}
	})
}
function loadFirstCategory(catId){ //获取首个分类
	loadCategoryData($('#firstCategory'),0,catId.substring(0,3));
	if(catId.substring(0,3)){
		loadCategoryData($('#twoCategory'),catId.substring(0,3),catId.substring(0,6));
	}
	if(catId.substring(0,6)){
		loadCategoryData($('#lastCategory'),catId.substring(0,6),catId.substring(0,9));
	}
}
function loadLastChild(that){
	loadCategoryData($('#lastCategory'),$(that).val(),null);
}
function selectCatId(that){
	catId = $(that).val();
}
function loadCategoryData(that,pid,currentPid){

	$.ajax({
		type: "get",
		url: '/category/findByPId/1',
		data: {
			pid:pid
		},
		success: function(data){
			if(data.code == 1){
				datas = data.data;
				if(datas.length > 0){
					for(var i = 0; i < datas.length; i++){
						if(datas[i].catId == currentPid){
							categoryHtml += '<option value="'+datas[i].catId+'" selected="selected">'+datas[i].catName+'</option>';
						}else{
							categoryHtml += '<option value="'+datas[i].catId+'">'+datas[i].catName+'</option>';
						}
					}
					$(that).show().html(categoryHtml);
					categoryHtml = '';
				}
			}else{
				$.alert(data.message)
			}
		}
	});
}
function renderData(data){//渲染页面
	catId = data.catId;
	$('#J-ver').val(data.ver);//版本号
	$("input[name='typeId']").val(catId.substring(0,3));
	$('#J-ver').val(data.ver);//版本号
	$('#J-goodsName').val(data.goodsName);//商品名称
	$('#J-goodsBrief').val(data.goodsBrief);//简介
	$('#keywords').val(data.keywords);//标签
	$('#showcontent').html(getLength(data.goodsName));//商品名称长度
	$('#showcontent2').html(getLength(data.goodsBrief));//商品名称长度
	$('#trialRange').val(data.trialRange);//使用范围
	$('select[name="parentSelect"] option').each(function(){
		if(data.goodsType == 0 || data.goodsType == 1){
			$('#childrenSelect1').show();
			if($(this).attr('value') == 100){
				$(this).attr('selected','true')
			}
			$('select[name="childrenSelect1"] option').each(function(){
				if($(this).attr('value') == data.goodsType){
					$(this).attr('selected','true')
				}
			})
		}else if(data.goodsType == 2){
			if($(this).attr('value') == 2){
				$(this).attr('selected','true')
			}
		}else if(data.goodsType == 4 || data.goodsType == 5){
			$('#childrenSelect3').show();
			if($(this).attr('value') == 400){
				$(this).attr('selected','true')
			}
			$('select[name="childrenSelect3"] option').each(function(){
				if($(this).attr('value') == data.goodsType){
					$(this).attr('selected','true')
				}
			})
		}else if(data.goodsType == 6 || data.goodsType == 7){
			$('#childrenSelect2').show();
			if($(this).attr('value') == 300){
				$(this).attr('selected','true')
			}
			$('select[name="childrenSelect2"] option').each(function(){
				if($(this).attr('value') == data.goodsType){
					$(this).attr('selected','true')
				}
			})
		}
	});
	var goodsTypeVal = $('#parentSelect').val();
	selectGoodsTypes(goodsTypeVal);
	$('#isOffline').val(data.isOffline);
	if(data.isOffline == 0) {
		if (data.goodsType == 0) {
			// console.log(data.offLineData.isOnline)
			var S_isOnline = null;
			var S_dataPwd = null;
			if(data.offLineData){
				if(data.offLineData.isOnline){
					S_isOnline = data.offLineData.isOnline;
				}else{
					S_isOnline = 0;
				}
				if(data.offLineData.dataPwd){
					S_dataPwd = data.offLineData.dataPwd ;
				}else{
					S_dataPwd = '';
				}
			}else{
				S_isOnline = 0;
				S_dataPwd = '';
			}
			$('select[name="isOnline"]').val(S_isOnline);
			$('input[name="dataPwd"]').val(S_dataPwd);
			// console.log(data.offLineData.isOnline)
			if(data.offLineData && data.offLineData.isOnline == 1 ){
				$('.selector_offLine_input').show();
				$('input[name="onlineUrl"]').val(data.offLineData && data.offLineData.onlineUrl ? data.offLineData.onlineUrl : '');
				$('.selector_offLine_upLoad').hide();
			}else{
				$('#J_fileUploadSS').val(data.uploadUrl);
				$('.fileUploads span').html(data.uploadUrl);
				$('input[name="goodsImges2"]').val(data.uploadUrl);
				$('.selector_offLine_input').hide();
			}
		} else if (data.goodsType == 1) {
			renderApiInfo(data.apiInfo);
		} else if (data.goodsType == 2) {
			renderDataModel(data.dataModel);
		} else if (data.goodsType == 4) {
			renderToolInfo(data.atAloneSoftware);
		} else if (data.goodsType == 5) {
			renderToolSaasInfo(data.atSaaS);
		} else if (data.goodsType == 6) {
			renderAppInfo(data.asAloneSoftware)
		} else if (data.goodsType == 7) {
			renderAppSaasInfo(data.asSaaS)
		}
	}else if(data.isOffline == 1){
		$('.struct.selects').hide();
		$('.isOffLine-info-box').show();
		$('.isOffLine-info-box input[name="concatName"]').val(data.offLineInfo.concatName);
		$('.isOffLine-info-box input[name="concatPhone"]').val(data.offLineInfo.concatPhone);
		$('.isOffLine-info-box input[name="concatEmail"]').val(data.offLineInfo.concatEmail);
	}
	// goodsTypes(data.goodsType,data.apiInfo,data.uploadUrl);
	$.each(data.attrTypeList,function(index,items){
		$.each(items.attrList,function(index,item){
			attrIds.push(item.attrId);
		});
	});
	console.log(host.static+data.goodsImg);
	$('#preview-img').attr('src',host.static+'/'+data.goodsImg);//图片
	$('input[name="goodsImg"]').val(data.goodsImg);
	$('input[name="goodsImges"]').val(data.goodsImg);
	$('#preview-div').mouseover(function(){
		if($('#preview-img').attr('src')){
			$('#replace-btn').show()
		}
	});
	$('#preview-div').mouseout(function(){
		$('#replace-btn').hide()
	});
	$('.fileUploads_j span').html(data.dataSample);//数据样例
	$('input[name="dataSample_s"]').val(data.dataSample);
	$('#dataSample').val(data.dataSample);
	if(data.formatList && data.formatList.length > 0){
		renderFormatList(data.formatList);//渲染价格
	}
	renderGoodsDeac(data);
	$('input[name="isBook"]').each(function(){
		if($(this).val() == data.isBook){
			$(this).attr('checked','true')
		}
	});
	renderIsBook(data.isBook, data.onsaleStartDate);
	if(data.areaCountry > 0){
		loadCountry(data.areaCountry,data.areaProvince)
	}else{
		loadCountry(100000,data.areaProvince)
	}
	if(data.areaProvince > 0){
		loadCity(data.areaProvince,data.areaCity)
	}
	initialize(); // 初始化数据
	$('#parentSelect').attr('disabled','disabled');
	$('.childrenSelect').attr('disabled','disabled');
	uploadGoodsImg();
}
function renderApiInfo(apiInfo){ //渲染API ----- 1
	$('.api-info-box input[name="apiType"]').each(function(){
		if($(this).val() == apiInfo.apiType){
			$(this).attr('checked','checked');
		}else{
			$(this).removeAttr('checked');
		}
	});
	$('.api-info-box input[name="apiUrl"]').val(apiInfo.apiUrl);
	$('.api-info-box input[name="invokeMethod"]').val(apiInfo.invokeMethod);
	$('.api-info-box input[name="apiMethod"]').each(function(){
		if($(this).val() == apiInfo.apiMethod){
			$(this).attr('checked','checked');
		}else{
			$(this).removeAttr('checked');
		}
	});
	$('.api-info-box input[name="respDataFormat"]').each(function(){
		if($(this).val() == apiInfo.respDataFormat){
			$(this).attr('checked','checked');
		}else{
			$(this).removeAttr('checked');
		}
	});
	$('.api-info-box input[name="reqSample"]').val(apiInfo.reqSample);
	$('.api-info-box input[name="secretKeyName"]').val(apiInfo.encryptInfo.secretKeyName);
	$('.api-info-box input[name="secretKeyValue"]').val(apiInfo.encryptInfo.secretKeyValue);
	$('.api-info-box #apiDesc').val(apiInfo.apiDesc);
	$('.api-info-box #respSample').val(apiInfo.respSample);
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
		html +='<td class="type-input"><div class="inputbox"><input type="text" name="fieldDefault" placeholder="请输入默认值" value="'+data.fieldDefault+'"></div></td>';
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
		html +='<td><div class="inputbox"><input type="text" name="fieldSample" placeholder="请输入示例" value="'+data.fieldSample+'"></div></td>';
		html +='<td><div class="inputbox"><textarea name="describle" placeholder="请输入描述">'+data.describle+'</textarea></div></td>';
		if(index === reqLen-1){
			html +='<td><span class="table-plus-btn" onclick="tablePlus(this)">+</span></td>';
		}else{
			html +='<td><span class="table-plus-btn" onclick="tableDelete(this)">-</span></td>';
		}
		html +='</tr>';
	});
	$('table[d-type="requestHtml"] tbody').html(html);
	var html2 = '';
	var resqLen = apiInfo.respParamList.length;
	$.each(apiInfo.respParamList, function (index,data) {
		html2 += '<tr class="parent-tr">';
		html2 += '<td class="errorNum-input"><div class="inputbox"><input type="text" name="fieldName" value="'+data.fieldName+'" placeholder="请输入错误码"></div></td>';
		html2 +='<td class="type-input"><div class="selectbox"><select name="fieldType">';
		if(data.fieldType){
			if(data.fieldType == 'String'){
				html2 +='<option value="String" selected="selected">String</option>';
			}else{
				html2 +='<option value="String">String</option>';
			}
			if(data.fieldType == 'int'){
				html2 +='<option value="int" selected="selected">int</option>';
			}else{
				html2 +='<option value="int">int</option>';
			}
			console.log(111)
		}else{
			console.log(222)
			html2 +='<option value="String">String</option>';
			html2 +='<option value="int">int</option>';
		}
		html2 +='</select></div></td>';
		html2 += '<td><div class="inputbox"><textarea name="describle" placeholder="请输入说明">'+data.describle+'</textarea></div></td>';
		if(index === reqLen-1) {
			html2 += '<td><span class="table-plus-btn" onclick="tablePlus(this)">+</span></td>';
		}else{
			html2 += '<td><span class="table-plus-btn" onclick="tableDelete(this)">-</span></td>';
		}
		html2 += '</tr>';
	});
	$('table[d-type="returnHtml"] tbody').html(html2);
	$('.api-info-box input[name="codeAttr"]').val(apiInfo.respDataMapping.codeAttrBean.codeAttr);
	$('.api-info-box input[name="successCode"]').val(apiInfo.respDataMapping.codeAttrBean.codeInfoBean.successCode);
	$('.api-info-box input[name="failedCode"]').val(apiInfo.respDataMapping.codeAttrBean.codeInfoBean.failedCode);
	$('.api-info-box input[name="successNoData"]').val(apiInfo.respDataMapping.codeAttrBean.codeInfoBean.successNoData);
	$('.api-info-box input[name="infoAttr"]').val(apiInfo.respDataMapping.infoAttr);
	$('.api-info-box input[name="dataAttr"]').val(apiInfo.respDataMapping.dataAttr);
	$('.api-info-box input[name="totalNumAttr"]').val(apiInfo.respDataMapping.totalNumAttr);
	$('.api-info-box input[name="updateFreq"]').val(apiInfo.updateFreq);
	$('.api-info-box input[name="dataNumDivRowNum"]').val(apiInfo.dataNumDivRowNum);
}
function renderDataModel(dataModel){ //渲染数据模型---2
	$('.dataModel-info-box input[name="complexity"]').val(dataModel.complexity);
	$('.dataModel-info-box input[name="maturity"]').val(dataModel.maturity);
	$('.dataModel-info-box input[name="aexp"]').val(dataModel.aexp);
	$('.dataModel-info-box input[name="modelFile"]').val(dataModel.modelFile);
	$('.dataModel-info-box input[name="modelFiles"]').val(dataModel.modelFile);
	$('.dataModel-info-box input[name="configFile"]').val(dataModel.configFile);
	$('.dataModel-info-box input[name="configFiles"]').val(dataModel.configFile);
	$('.dataModel-info-box input[name="configParams"]').val(dataModel.configParams);
	$('.dataModel-info-box input[name="configParam"]').val(dataModel.configParams);
	$('.dataModel-info-box input[name="modelFilePwd"]').val(dataModel.modelFilePwd);
	$('.dataModel-info-box input[name="configFilePwd"]').val(dataModel.configFilePwd);
	$('.dataModel-info-box input[name="configParamsPwd"]').val(dataModel.configParamsPwd);
	$('.dataModel-info-box input[name="concatName"]').val(dataModel.concatInfo.concatName);
	$('.dataModel-info-box input[name="concatPhone"]').val(dataModel.concatInfo.concatPhone);
	$('.dataModel-info-box input[name="concatEmail"]').val(dataModel.concatInfo.concatEmail);
	$('.dataModel-info-box .otherDesc').val(dataModel.otherDesc);
}
function renderToolInfo(atAloneSoftware){
	$('.tool-info-box input[name="aTIndustryField"]').val(atAloneSoftware.aTAloneIndustryField);
	$('.tool-info-box input[name="aTVersionDesc"]').val(atAloneSoftware.aTAloneVersionDesc);
	$('.tool-info-box #aTToolsIntroduce').val(atAloneSoftware.aTAloneToolsIntroduce);
	$('.tool-info-box #aTAloneCloudHardwareResource').val(atAloneSoftware.aTAloneCloudHardwareResource);
	$('.tool-info-box .otherDesc').val(atAloneSoftware.otherDesc);
	$('.tool-info-box input[name="dataAddress"]').val(atAloneSoftware.dataAddress);
}
function renderToolSaasInfo(atSaaS){
	$('.tool-info-box input[name="aTIndustryField"]').val(atSaaS.aTIndustryField);
	$('.tool-info-box input[name="aTVersionDesc"]').val(atSaaS.aTVersionDesc);
	$('.tool-info-box #aTToolsIntroduce').val(atSaaS.aTToolsIntroduce);
	$('.tool-info-box .otherDesc').val(atSaaS.otherDesc);
	$('.tool-info-box input[name="dataAddress"]').val(atSaaS.dataAddress);
}
function renderAppInfo(asAloneSoftware){
	$('.app-info-box input[name="aSComplexity"]').val(asAloneSoftware.aSComplexity);
	$('.app-info-box input[name="aSVersionDesc"]').val(asAloneSoftware.aSVersionDesc);
	$('.app-info-box input[name="aSServiceLevel"]').val(asAloneSoftware.aSServiceLevel);
	$('.app-info-box input[name="aSAexp"]').val(asAloneSoftware.aSAexp);
	$('.app-info-box input[name="dataAddress"]').val(asAloneSoftware.dataAddress);
	$('.app-info-box #aSCloudHardwareResource').val(asAloneSoftware.aSCloudHardwareResource);
	$('.app-info-box #aSAintroduce').val(asAloneSoftware.aSAintroduce);
	$('.app-info-box .otherDesc').val(asAloneSoftware.otherDesc);
}
function renderAppSaasInfo(asSaaS){
	$('.app-info-box input[name="aSComplexity"]').val(asSaaS.sSComplexity);
	$('.app-info-box input[name="aSVersionDesc"]').val(asSaaS.sSVersionDesc);
	$('.app-info-box input[name="aSServiceLevel"]').val(asSaaS.sServiceLevel);
	$('.app-info-box input[name="aSAexp"]').val(asSaaS.sSAexp);
	$('.app-info-box input[name="dataAddress"]').val(asSaaS.dataAddress);
	$('.app-info-box #aSAintroduce').val(asSaaS.sSAintroduce);
	$('.app-info-box .otherDesc').val(asSaaS.otherDesc);
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
		html += '<td class="price-input"><div class="inputbox"><input class="price-inputs number " min="0" datatype="price" value="'+data.price / 100+'" type="text" placeholder="请输入价格"></div></td>';
		if(index === len-1){
			html += '<td><span class="table-plus-btn" onclick="tablePlus(this)">+</span></td>';
		}else{
			html += '<td><span class="table-plus-btn" onclick="tableDelete(this)">-</span></td>';
		}

		html += '</tr>';
	});
	$('table[d-type="priceHtml"] tbody').html(html);
}
function renderGoodsDeac(data){
	$('#textarea1').val(data.goodsDesc);
	$('#textarea2').val(data.goodsAdvantage);
	$('#textarea3').val(data.afterSaleService);
	$('#textarea4').val(data.appCase);
	renderWangEdit();
}
function renderIsBook(isBook, onsaleStartDate){
	if(isBook == 1){
		$('#indate').val(onsaleStartDate);
		$.jeDate("#indate", {
			format: "YYYY-MM-DD hh:mm:ss",
			isTime: true,
			minDate: $.nowDate(0),
			choosefun: function(val) {
				$('#indate_s').val(val)
			}
		});
	}else{
		$.jeDate("#indate", {
			format: "YYYY-MM-DD hh:mm:ss",
			isTime: true,
			minDate: $.nowDate(0),
			choosefun: function(val) {
				$('#indate_s').val(val)
			}
		});
	}
}
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
function initialize() {
	getPriceBox();
	floorPrice();
}





$('#J_submitBtn').click(function(){
	if($("#goodsModifyForm").valid()){
		if($.trim(editor1.$txt.text()).length > 0){
			if($.trim(editor2.$txt.text()).length > 0){
				if($.trim(editor3.$txt.text()).length > 0){
					if($.trim(editor4.$txt.text()).length > 0){
						backAddFn(submitGoodsPublish())
					}else{
						$.alert('商品描述不能为空',true,function () {})
					}
				}else{
					$.alert('商品优势不能为空',true,function () {})
				}
			}else{
				$.alert('售后服务不能为空',true,function () {})
			}
		}else{
			$.alert('应用案例不能为空',true,function () {})
		}
	}
});
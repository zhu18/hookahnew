var regionParam = 100000;
var userId = $("input[name='userId']").val();
var catId = $.getUrlParam('catId'),
	category = $.getUrlParam('category'),
	goodId = $.getUrlParam('id');
var urlPath = window.location.pathname;
var E = window.wangEditor; //初始化富文本
var itemNum = 0; //添加规格计数器
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
	editor1 = new E('textarea1');
	editor2 = new E('textarea2');
	editor3 = new E('textarea3');
	editor4 = new E('textarea4');
	if(urlPath == '/usercenter/goodsEdit'){ //上传商品
		if(!catId){
			window.location.href = '/usercenter/goodsManage';
		}else{
			goodsEditFn();
		}
	}else if(urlPath == '/usercenter/goodsModify'){ //修改商品
		if(!goodsId){
			window.location.href='/usercenter/goodsOffSale';
		}else{
			goodsModifyFn();
		}
	}

});
function goodsEditFn(){
	$('.category-title-box').text(category);//渲染分类
	loadRegion('province', regionParam); //加载地区
	renderWangEdit(); // 渲染富文本
	uploadGoodsImg(); //上传商品图片
	floorPrice();//监控价格输入为“.”时转换为“0.”
	validataFn();//表单验证
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
		} else if (data.goodsType == 1) {//------------------------------
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
				listData.fieldName = $(this).find('input[name="fieldNames"]').val();
				listData.fieldType = $(this).find('select[name="fieldType"]').val();
				listData.describle = $(this).find('textarea[name="describle"]').val();
				data.apiInfo.respParamList.push(listData);
			});
			console.log(data.apiInfo.respParamList);//----------------------
			data.apiInfo.respSample = $('#respSample').val();
			data.apiInfo.respDataFormat = $('.api-info-box').find('input[name="respDataFormat"]:checked').val();
			data.apiInfo.respDataMapping = {};
			data.apiInfo.respDataMapping.codeAttr = $('input[name="codeAttr"]').val();
			data.apiInfo.respDataMapping.successCode = $('input[name="successCode"]').val();
			data.apiInfo.respDataMapping.failedCode = $('input[name="failedCode"]').val();
			data.apiInfo.respDataMapping.successNoData = $('input[name="successNoData"]').val();
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
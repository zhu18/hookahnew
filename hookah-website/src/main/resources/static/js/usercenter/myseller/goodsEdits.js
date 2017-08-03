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
var goodsTypeId = null;
var regex = {  //手机号验证正则
	mobile: /^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/
};
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
//初始化商品类型
function initializeGoodsType(){
	var startGoodsVal = null;
	if(catId.substring(0,3) == 101){
		$('#parentSelect').val(100).attr('disabled','disabled');
		startGoodsVal = 100;
	}else if(catId.substring(0,3) == 102){
		$('#parentSelect').val(2).attr('disabled','disabled');
		startGoodsVal = 2;
	}else if(catId.substring(0,3) == 104){
		$('#parentSelect').val(300).attr('disabled','disabled');
		startGoodsVal = 300;
	}else{
		$('#parentSelect').val(400).attr('disabled','disabled');
		startGoodsVal = 400;
	}
	initializeGoodsTypeEnd(startGoodsVal,null);
}
function initializeGoodsTypeEnd(goodsTypeVal,endTypeVal){
	$('.edit-all').hide();
	$('.struct.selects').hide();
	$('.offline-isShow').hide();
	$('.isOnLine-info-box').show();
	if(goodsTypeVal == 100){
		$('#childrenSelect1').show();
		endTypeVal = $('#childrenSelect1');
		if($(endTypeVal).val() == 0){
			$('.goodsDesc-box').show(); //商品描述
			$('.goodsAdvantage-box').show();//商品优势
			$('.afterSaleService-box').show();//售后服务
			$('.dataSample-info-box').show(); //数据样例
			$('.isOffLine-select-box').show(); //交付方式
			$('.file-info-box').show(); //
			$('.offline-isOnLine-box').show(); //数据来源
			goodsTypeId = 0;
		}else if($(endTypeVal).val() == 1){
			$('.goodsDesc-box').show(); //商品描述
			$('.goodsAdvantage-box').show();//商品优势
			$('.afterSaleService-box').show();//售后服务
			$('.dataSample-info-box').show(); //数据样例
			$('.appCase-box').show(); //应用案例
			$('.api-info-box').show();
			goodsTypeId = 1;
		}
	}else if(goodsTypeVal == 300){
		$('#childrenSelect2').show();
		endTypeVal = $('#childrenSelect2');
		if($(endTypeVal).val() == 6){
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.appCase-box').show(); //应用案例
			$('.app-info-box').show(); //
			$('.downloadAddress-isOnLine-box').show(); //下载地址
			goodsTypeId = 6;
		}else if($(endTypeVal).val() == 7){
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.appCase-box').show(); //应用案例
			$('.app-info-box').show(); //
			$('.app-info-box .app-saas-info').hide(); //技术优势
			$('.downloadAddress-isOnLine-box').show(); //下载地址
			goodsTypeId = 7;
		}
	}else if(goodsTypeVal == 400){
		$('#childrenSelect3').show();
		endTypeVal = $('#childrenSelect3');
		if($(endTypeVal).val() == 4){
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.appCase-box').show(); //应用案例
			$('.tool-info-box').show(); //
			$('.visitOnline-isOnLine-box').show(); //下载地址
			goodsTypeId = 4;
		}else if($(endTypeVal).val() == 5){
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.appCase-box').show(); //应用案例
			$('.tool-info-box').show(); //
			$('.tool-info-box .app-saas-info').hide(); //技术优势
			$('.visitOnline-isOnLine-box').show(); //下载地址
			goodsTypeId = 5;
		}
	}else{
		$('.goodsDesc-box').show(); //商品描述
		$('.goodsAdvantage-box').show();//商品优势
		$('.dataModel-info-box').show();//
		$('.afterSaleService-box').show();//售后服务
		$('.isOffLine-select-box').show(); //交付方式
		$('.dataModel-isOnLine-box').show(); //交付方式
		goodsTypeId = 2;
	}
	renderFormatFn(goodsTypeId)
}//初始化商品类型
function renderFormatFn(goodsTypeId){
	if(goodsTypeId == 0 || goodsTypeId == 2 || goodsTypeId == 4 || goodsTypeId == 6){
		$('select[name="format"] option').each(function(){
			if($(this).val() == 3){
				$(this).attr('selected','selected')
			}else{
				$(this).removeAttrs('selected')
			}
			if($(this).val() != 3){
				$(this).attr('disabled','disabled')
			}else{
				$(this).removeAttrs('disabled');
			}


		})
	}else if(goodsTypeId == 1){
		$('select[name="format"] option').each(function() {
			if ($(this).val() == 3) {
				$(this).attr('disabled', 'disabled')
			}else{
				$(this).removeAttrs('disabled');
			}
			if($(this).val() == 0){
				$(this).attr('selected','selected')
			}else{
				$(this).removeAttrs('selected')
			}
		})
	}else if(goodsTypeId == 5 || goodsTypeId == 7){
		$('select[name="format"] option').each(function() {
			if ($(this).val() == 0 || $(this).val() == 3) {
				$(this).attr('disabled', 'disabled');
			}else{
				$(this).removeAttrs('disabled');
			}
			if ($(this).val() == 1) {
				$(this).attr('selected', 'selected')
			}else{
				$(this).removeAttrs('selected')
			}
		})
	}
}
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
	initializeGoodsType(null);
	$("input[name='typeId']").val(catId.substring(0,3));
	rederDateDL()
	getPriceBox()
}
function rederDateDL(){
	var start = {
		format: "YYYY-MM-DD hh:mm:ss",
		isTime: true,
		choosefun: function (elem,val, datas) {
			end.minDate = datas; //开始日选好后，重置结束日的最小日期
			$("#offLine_startDate_s").val(val);
		},
		okfun:function(elem,val, datas){
			end.minDate = datas; //开始日选好后，重置结束日的最小日期
			$("#offLine_startDate_s").val(val);
		},
		clearfun:function(elem, val) {
			$("#offLine_startDate_s").val();
		}
	};
	var end = {
		format: "YYYY-MM-DD hh:mm:ss",
		isTime: true,
		choosefun: function (elem,val, datas) {
			start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
			$("#offLine_endDate_s").val(val);
		},
		okfun:function(elem,val, datas){
			start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
			$("#offLine_endDate_s").val(val);
		},
		clearfun:function(elem, val) {
			$("#offLine_endDate_s").val();
		}
	};

	$.jeDate("#offLine_startDate", start);
	$.jeDate("#offLine_endDate", end);
}
function childrenSelects(that){
	initializeGoodsType($('#parentSelect').val(),that);
}
function selectLineInfo(that){
	var isOffLineVal = $(that).val();
	var p_val = $('.parentSelect').val();
	var c_val = null;
	if(isOffLineVal == 1){
		$('.offline-isShow').hide();
		$('.isOffLine-info-box').show();
		$('.isOnLine-info-box').hide();
	}else{
		$('.isOnLine-info-box').show();
		$('.isOffLine-info-box').hide();
		$('.offline-isShow').hide();
		if(p_val == 100){
			c_val = $('#childrenSelect1').val();
			if(c_val == 0){
				$('.offline-isOnLine-box').show()
			}
		}else if(p_val == 300){
			c_val = $('#childrenSelect2').val();
			if(c_val == 6){
				$('.downloadAddress-isOnLine-box').show(); //下载地址
			}else if(c_val == 7){
				$('.downloadAddress-isOnLine-box').show(); //下载地址
			}
		}else if(p_val == 400){
			c_val = $('#childrenSelect3').val();
			if(c_val == 4){
				$('.visitOnline-isOnLine-box').show(); //在线访问地址
			}else if(c_val == 5){
				$('.visitOnline-isOnLine-box').show(); //在线访问地址
			}
		}else if(p_val == 2){
			$('.dataModel-isOnLine-box').show()
		}
		console.log('p_val==:'+p_val+'----c_val==:'+c_val)
	}

} //选择交付方式
function selector_offLine_fn(that){
	$('.selector_offLine').hide();
	var vals = $(that).val();
	if(vals == 0){
		$('.selector_offLine_upLoad').show();
	}else{
		$('.selector_offLine_input').show();
	}
}//选择数据来源
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
			trialRange:{
				isGoodsBrief:true
			},
			goodsImg:'required',
			priceBoxName:'required',
			priceBoxNumber:'required',
			enddDate:'required',
			priceBoxPrice:{
				required:true,
				// isPricceData:true,
				isPricceB:true,
				lt:["#maxExp","最大经验值"],
				gt:["#minExp","最小经验值"]
			},
			priceBoxSettlementPrice:{
				required:true,
				// isPricceData:true,
				isPricceB:true,
				lt:["#maxExp","最大经验值"],
				gt:["#minExp","最小经验值"]
			},
			priceBoxAgencyPrice:{
				required:true,
				// isPricceData:true,
				// isPricceB:true,
				lt:["#maxExp","最大经验值"],
				gt:["#minExp","最小经验值"]
			},
			goodsImges:'required',
			goodsImges2:'required',
			goodsDescBox:'required',
			aexp:{
				isModelTool:true,
			},
			relationServ:{
				isModelTool:true,
			}
		},
		messages: {
			goodsName:  {
				required: '商品名称不能为空',
				isGoodsName:'长度为1-60个字符（每个汉字为2个字符）'
			},
			goodsBrief:  {
				required: '商品简介不能为空',
				isGoodsBrief:'长度为1-400个字符（每个汉字为2个字符）'
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
	if(parentId == '-1'){
		$('#city').html('<option value="-1">全部</option>')
	}
	$(regionParam).nextAll().html('<option value="-1">全部</option>')
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
	editorA = new E('textareaA');
	editorB = new E('textareaB');
	editorC = new E('textareaC');
	editorD = new E('textareaD');
	editorE = new E('textareaE');
	editorAs = new E('textareaAs');
	editorBs = new E('textareaBs');
	editorCs = new E('textareaCs');
	editorDs = new E('textareaDs');
	editorEs = new E('textareaEs');
	editor1.create();
	editor2.create();
	editor3.create();
	editor4.create();
	editorA.create();
	editorB.create();
	editorC.create();
	editorD.create();
	editorE.create();
	editorAs.create();
	editorBs.create();
	editorCs.create();
	editorDs.create();
	editorEs.create();
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
	priceHtml += '<td style="width: 40px;"><div class="selectbox"><select name="format" ><option value="0">次</option><option value="1">月</option><option value="2">年</option><option value="3">套</option></select></div></td>';
	priceHtml += '<td class="price-input price-input1"><div class="inputbox"><input type="text" class="price-inputs number" datatype="settlementPrice" placeholder="请输入结算价" isPricceData="true"></div></td>';
	priceHtml += '<td class="price-input price-input2"><div class="inputbox"><input type="text" class="price-inputs number" datatype="agencyPrice" placeholder="请输入代理价" isPricceData="true"></div></td>';
	priceHtml += '<td class="price-input price-input3"><div class="inputbox"><input type="text" class="price-inputs number" datatype="price" placeholder="请输入零售价" isPricceData="true"></div></td>';
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
	returnHtml += '<td class="errorNum-input"><div class="inputbox"><input type="text" placeholder="请输入字段" name="fieldName"></div></td>';
	returnHtml += '<td class="type-input"><div class="selectbox"><select name="fieldType"><option value="String">String</option><option value="int">int</option></select></div></td>';
	returnHtml += '<td><div class="inputbox"><textarea placeholder="请输入说明" name="describle"></textarea></div></td>';
	returnHtml += '<td><span class="table-plus-btn" onclick="tablePlus(this)">+</span></td>';
	returnHtml += '</tr>';
	if ($(that).parents('.table-plus').attr('d-type') == 'priceHtml') {
		if ($(that).parent().siblings('.number-input').find('input').val() && $(that).parent().siblings('.price-input1').find('input').val() && $(that).parent().siblings('.price-input2').find('input').val() && $(that).parent().siblings('.price-input3').find('input').val()) {
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
	renderLastFormatFn(goodsTypeId)
}
function renderLastFormatFn(goodsTypeId){
	if(goodsTypeId == 0 || goodsTypeId == 2 || goodsTypeId == 4 || goodsTypeId == 6){
		$('table[d-type="priceHtml"] tbody tr:last select[name="format"] option').each(function(){
			if($(this).val() == 3){
				$(this).attr('selected','selected')
			}else{
				$(this).removeAttrs('selected')
			}
			if($(this).val() != 3){
				$(this).attr('disabled','disabled')
			}else{
				$(this).removeAttrs('disabled');
			}
		})
	}else if(goodsTypeId == 1){
		$('table[d-type="priceHtml"] tbody tr:last select[name="format"] option').each(function() {
			if ($(this).val() == 3) {
				$(this).attr('disabled', 'disabled')
			}else{
				$(this).removeAttrs('disabled');
			}
			if($(this).val() == 0){
				$(this).attr('selected','selected')
			}else{
				$(this).removeAttrs('selected')
			}
		})
	}else if(goodsTypeId == 5 || goodsTypeId == 7){
		$('table[d-type="priceHtml"] tbody tr:last select[name="format"] option').each(function() {
			if ($(this).val() == 0 || $(this).val() == 3) {
				$(this).attr('disabled', 'disabled');
			}else{
				$(this).removeAttrs('disabled');
			}
			if ($(this).val() == 1) {
				$(this).attr('selected', 'selected')
			}else{
				$(this).removeAttrs('selected')
			}
		})
	}
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
	priceBox.find('input[datatype="settlementPrice"]').attr('name','priceBoxSettlementPrice');
	priceBox.find('input[datatype="agencyPrice"]').attr('name','priceBoxAgencyPrice');
}
function getLength(str){
	return str.replace(/[\u0391-\uFFE5]/g,"aa").length;
}
$('#J-goodsName').on('input onporpertychange',function () {
	$('#showcontent').html(getLength($(this).val()));
});
$('#goodsBriefes').on('input onporpertychange',function () {
	$('.showcontentes').html(getLength($(this).val()));
});
$('#trialRange').on('input onporpertychange',function () {
	$('#trialRange_s').html(getLength($(this).val()));
});
$('#aexp').on('input onporpertychange',function () {
	$('#aexp_s').html(getLength($(this).val()));
});
$('#relationServ').on('input onporpertychange',function () {
	$('#relationServ_s').html(getLength($(this).val()));
});
$.validator.addMethod("isGoodsName", function(value, element) {
	var len = value.replace(/[\u0391-\uFFE5]/g,"aa").length;
	return this.optional(element) || (1 <= len && len <= 60);
}, "长度为1-60个字符（每个汉字为2个字符）");
$.validator.addMethod("isGoodsBrief", function(value, element) {
	var len = value.replace(/[\u0391-\uFFE5]/g,"aa").length;
	return this.optional(element) || (1 <= len && len <= 400);
}, "长度为1-400个字符（每个汉字为2个字符）");
$.validator.addMethod("isModelTool", function(value, element) {
	var len = value.replace(/[\u0391-\uFFE5]/g,"aa").length;
	return this.optional(element) || (1 <= len && len <= 100);
}, "长度为1-100个字符（每个汉字为2个字符）");
$.validator.addMethod("isMobile", function(value, element) {
	var mobile = regex.mobile.test(value);
	return this.optional(element) || (mobile);
}, "请填写有效的手机号");
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
//大于
$.validator.addMethod("gt", function(value, element,param) {
	return this.optional(element) || value >=0.01;
}, $.validator.format("输入值必须大于{0.01}!"));
//小于
$.validator.addMethod("lt", function(value, element,param) {
	return this.optional(element) || value <= 9999999.99;
}, $.validator.format("输入值必须小于{9999999.99}!"));
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
				Loading.stop();
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
		listData.settlementPrice = ($(this).find('input[datatype="settlementPrice"]').val()) * 100;
		listData.agencyPrice = ($(this).find('input[datatype="agencyPrice"]').val()) * 100;
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

	if(urlPath == '/usercenter/goodsModify'){ //修改商品
		if($('#lastCategory').val()){
			data.catId = $('#lastCategory').val();
		}else{
			data.catId = $('#twoCategory').val();
		}
	}else{
		data.catId = catId; //此ID为url上的id
	}
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
	if (data.goodsType == 0) {
		data.offLineData = {};
		data.goodsDesc = $('#textarea1').val(); //商品详情
		data.goodsAdvantage = $('#textarea2').val(); //商品优势
		data.afterSaleService = $('#textarea3').val(); //售后服务
		data.offLineData = {};
		data.offLineData.timeFrame = {};
		data.offLineData.timeFrame.startDate = $('#offLine_startDate').val();
		data.offLineData.timeFrame.endDate = $('#offLine_endDate').val();
		data.offLineData.dataRows = $('input[name="dataRows"]').val();
		data.offLineData.dataCapacity = $('input[name="dataCapacity"]').val();
		data.offLineData.dataFormat = $('input[name="dataFormat"]:checked').val();
		data.offLineData.isOnline = $('select[name="isOnline"]').val();
		data.offLineData.dataPwd = $('input[name="dataPwd"]').val();
		if(data.offLineData.isOnline == 0){
			data.offLineData.localUrl = $('#J_fileUploadSS').val();
		}else{
			data.offLineData.onlineUrl = $('input[name="onlineUrl"]').val();
		}
	} else if (data.goodsType == 1) {//API------------------------------
		data.goodsDesc = $('#textarea1').val(); //商品详情
		data.goodsAdvantage = $('#textarea2').val(); //商品优势
		data.afterSaleService = $('#textarea3').val(); //售后服务
		data.appCase = $('#textarea4').val(); //应用案例
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
		data.goodsDesc = $('#textarea1').val(); //商品详情
		data.goodsAdvantage = $('#textarea2').val(); //商品优势
		data.afterSaleService = $('#textarea3').val(); //售后服务
		data.dataModel = {};
		data.dataModel.modelType = $('select[name="modelType"]').val();
		data.dataModel.complexity = $('select[name="complexity"]').val();
		data.dataModel.maturity = $('select[name="maturity"]').val();
		data.dataModel.aexp = $('textarea[name="aexp"]').val();
		data.dataModel.relationServ = $('textarea[name="relationServ"]').val();
		data.dataModel.modelFile = {};
		data.dataModel.modelFile.fileAddress = $('input[name="modelFile"]').val();
		data.dataModel.modelFile.filePwd = $('input[name="modelFilePwd"]').val();
		data.dataModel.configFile = {};
		data.dataModel.configFile.fileAddress = $('input[name="configFile"]').val();
		data.dataModel.configFile.filePwd = $('input[name="configFilePwd"]').val();
		data.dataModel.paramFile = {};
		data.dataModel.paramFile.fileAddress = $('.dataModel-isOnLine-box input[name="configParams"]').val();
		data.dataModel.paramFile.filePwd = $('input[name="configParamsPwd"]').val();
		data.dataModel.concatInfo = {};
		data.dataModel.concatInfo.concatName = $('.dataModel_concat input[name="concatName"]').val();
		data.dataModel.concatInfo.concatPhone = $('.dataModel_concat input[name="concatPhone"]').val();
		data.dataModel.concatInfo.concatEmail = $('.dataModel_concat input[name="concatEmail"]').val();
	} else if (data.goodsType == 4) {
		data.afterSaleService = $('#textarea3').val(); //售后服务
		data.appCase = $('#textarea4').val(); //应用案例
		data.atAloneSoftware = {};
		data.atAloneSoftware.coreFunction = $('#textareaAs').val();
		data.atAloneSoftware.technologicalSuperiority = $('#textareaBs').val();
		data.atAloneSoftware.teamAdvantage = $('#textareaCs').val();
		data.atAloneSoftware.desiredEnvironment = $('#textareaDs').val();
		data.atAloneSoftware.dataNeeded = $('#textareaEs').val();
		data.atAloneSoftware.dataAddress = $('.visitOnline-isOnLine-box input[name="dataAddress"]').val();
	} else if (data.goodsType == 5) {
		data.afterSaleService = $('#textarea3').val(); //售后服务
		data.appCase = $('#textarea4').val(); //应用案例
		data.atSaaS = {};
		data.atSaaS.coreFunction = $('#textareaAs').val();
		data.atSaaS.teamAdvantage = $('#textareaCs').val();
		data.atSaaS.desiredEnvironment = $('#textareaDs').val();
		data.atSaaS.dataNeeded = $('#textareaEs').val();
		data.atSaaS.dataAddress = $('.visitOnline-isOnLine-box input[name="dataAddress"]').val();
	} else if (data.goodsType == 6) {
		data.afterSaleService = $('#textarea3').val(); //售后服务
		data.appCase = $('#textarea4').val(); //应用案例
		data.asAloneSoftware = {};
		data.asAloneSoftware.coreFunction = $('#textareaA').val();
		data.asAloneSoftware.technologicalSuperiority = $('#textareaB').val();
		data.asAloneSoftware.teamAdvantage = $('#textareaC').val();
		data.asAloneSoftware.desiredEnvironment = $('#textareaD').val();
		data.asAloneSoftware.dataNeeded = $('#textareaE').val();
		data.asAloneSoftware.dataAddress = $('.downloadAddress-isOnLine-box input[name="dataAddress"]').val();
	} else if (data.goodsType == 7) {
		data.afterSaleService = $('#textarea3').val(); //售后服务
		data.appCase = $('#textarea4').val(); //应用案例
		data.asSaaS = {};
		data.asSaaS.coreFunction = $('#textareaA').val();
		data.asSaaS.teamAdvantage = $('#textareaC').val();
		data.asSaaS.desiredEnvironment = $('#textareaD').val();
		data.asSaaS.dataNeeded = $('#textareaE').val();
		data.asSaaS.dataAddress = $('.downloadAddress-isOnLine-box input[name="dataAddress"]').val();

	}
	data.offLineInfo = {};
	data.offLineInfo.concatName= $('.isOffLine-info-box input[name="concatName"]').val();
	data.offLineInfo.concatPhone= $('.isOffLine-info-box input[name="concatPhone"]').val();
	data.offLineInfo.concatEmail= $('.isOffLine-info-box input[name="concatEmail"]').val();

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
	Loading.start();
	$.ajax({
		url:host.website+'/goods/back/findById',
		type:'get',
		data:{
			id:goodsId
		},
		success:function(data){
			Loading.stop();
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
function renderData(data){//渲染页面
	catId = data.catId;
	$('#J-ver').val(data.ver);//版本号
	$("input[name='typeId']").val(catId.substring(0,3));
	$('#J-ver').val(data.ver);//版本号
	$('#J-goodsName').val(data.goodsName);//商品名称
	$('#goodsBriefes').val(data.goodsBrief);//简介
	$('#keywords').val(data.keywords);//标签
	$('#showcontent').html(getLength(data.goodsName));//商品名称长度
	$('.showcontentes').html(getLength(data.goodsBrief));//商品名称长度
	$('#trialRange').val(data.trialRange);//使用范围
	$('#trialRange_s').html(getLength(data.trialRange));//使用范围
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
	$('#isOffline').val(data.isOffline);
	if(data.isOffline == 0) {
		if (data.goodsType == 0) {
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
				$('#J_fileUploadSS').val(data.offLineData.localUrl);
				$('.fileUploads span').text(data.offLineData.localUrl);
				$('input[name="goodsImges2"]').val(data.offLineData.localUrl);
				$('.selector_offLine_input').hide();
			}
			$('#offLine_startDate').val(data.offLineData.timeFrame.startDate);
			$('#offLine_startDate_s').val(data.offLineData.timeFrame.startDate);
			$('#offLine_endDate').val(data.offLineData.timeFrame.endDate);
			$('#offLine_endDate_s').val(data.offLineData.timeFrame.endDate);
			$('input[name="dataRows"]').val(data.offLineData.dataRows);
			$('input[name="dataCapacity"]').val(data.offLineData.dataCapacity);
			$('.file-info-box input[name="dataFormat"]').each(function(){
				if($(this).val() == data.offLineData.dataFormat){
					$(this).attr('checked','checked');
				}else{
					$(this).removeAttr('checked');
				}
			});
			rederDateDL()
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
		if (data.goodsType == 0) {
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
				$('#J_fileUploadSS').val(data.offLineData.localUrl);
				$('.fileUploads span').text(data.offLineData.localUrl);
				$('input[name="goodsImges2"]').val(data.offLineData.localUrl);
				$('.selector_offLine_input').hide();
			}
			$('#offLine_startDate').val(data.offLineData.timeFrame.startDate);
			$('#offLine_startDate_s').val(data.offLineData.timeFrame.startDate);
			$('#offLine_endDate').val(data.offLineData.timeFrame.endDate);
			$('#offLine_endDate_s').val(data.offLineData.timeFrame.endDate);
			$('input[name="dataRows"]').val(data.offLineData.dataRows);
			$('input[name="dataCapacity"]').val(data.offLineData.dataCapacity);
			$('.file-info-box input[name="dataFormat"]').each(function(){
				if($(this).val() == data.offLineData.dataFormat){
					$(this).attr('checked','checked');
				}else{
					$(this).removeAttr('checked');
				}
			});
			rederDateDL()
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
		$('.struct.selects').hide();
		$('.struct.isOffLine-info-box').show();
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
	initializeGoodsTypeEnd(goodsTypeVal,null);
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
	validataFn();
	ifGoodsTypeRender(data);
}
function ifGoodsTypeRender(data){ //根据类型渲染商品类型相关的html
	$('.edit-all').hide();
	$('.struct.selects').hide();
	$('.offline-isShow').hide();
	var goodsType = data.goodsType;
	var isOffline = data.isOffline;
	if(isOffline == 0){
		$('.isOnLine-info-box').show();
		if(goodsType == 0){
			$('.goodsDesc-box').show(); //商品描述
			$('.goodsAdvantage-box').show();//商品优势
			$('.afterSaleService-box').show();//售后服务
			$('.dataSample-info-box').show(); //数据样例
			$('.isOffLine-select-box').show(); //交付方式
			$('.file-info-box').show(); //
			$('.offline-isOnLine-box').show(); //数据来源
		}else if(goodsType == 1){
			$('.goodsDesc-box').show(); //商品描述
			$('.goodsAdvantage-box').show();//商品优势
			$('.afterSaleService-box').show();//售后服务
			$('.dataSample-info-box').show(); //数据样例
			$('.appCase-box').show(); //应用案例
			$('.api-info-box').show();
		}else if(goodsType == 2){
			$('.goodsDesc-box').show(); //商品描述
			$('.goodsAdvantage-box').show();//商品优势
			$('.dataModel-info-box').show();//
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.dataModel-isOnLine-box').show(); //交付方式
		}else if(goodsType == 4){
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.appCase-box').show(); //应用案例
			$('.tool-info-box').show(); //
			$('.visitOnline-isOnLine-box').show(); //下载地址
		}else if(goodsType == 5){
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.appCase-box').show(); //应用案例
			$('.tool-info-box').show(); //
			$('.tool-info-box .app-saas-info').hide(); //技术优势
			$('.visitOnline-isOnLine-box').show(); //下载地址
		}else if(goodsType == 6){
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.appCase-box').show(); //应用案例
			$('.app-info-box').show(); //
			$('.downloadAddress-isOnLine-box').show(); //下载地址
		}else if(goodsType == 7){
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.appCase-box').show(); //应用案例
			$('.app-info-box').show(); //
			$('.app-info-box .app-saas-info').hide(); //技术优势
			$('.downloadAddress-isOnLine-box').show(); //下载地址
		}
	}else{
		$('.struct.selects').hide();
		$('.isOffLine-info-box').show();
		if(goodsType == 0){
			$('.goodsDesc-box').show(); //商品描述
			$('.goodsAdvantage-box').show();//商品优势
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.file-info-box').show(); //
		}else if(goodsType == 1){
			$('.goodsDesc-box').show(); //商品描述
			$('.goodsAdvantage-box').show();//商品优势
			$('.afterSaleService-box').show();//售后服务
			$('.dataSample-info-box').show(); //数据样例
			$('.appCase-box').show(); //应用案例
		}else if(goodsType == 2){
			$('.goodsDesc-box').show(); //商品描述
			$('.goodsAdvantage-box').show();//商品优势
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.dataModel-isOnLine-box').show(); //交付方式
			$('.dataModel-info-box').show();//
		}else if(goodsType == 4){
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.appCase-box').show(); //应用案例
			$('.tool-info-box').show(); //技术优势
			$('.visitOnline-isOnLine-box').show(); //下载地址
		}else if(goodsType == 5){
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.appCase-box').show(); //应用案例
			$('.tool-info-box').show(); //技术优势
			$('.tool-info-box .app-saas-info').hide(); //技术优势
			$('.visitOnline-isOnLine-box').show(); //下载地址
		}else if(goodsType == 6){
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.appCase-box').show(); //应用案例
			$('.app-info-box').show(); //技术优势
			$('.downloadAddress-isOnLine-box').show(); //下载地址
		}else if(goodsType == 7){
			$('.afterSaleService-box').show();//售后服务
			$('.isOffLine-select-box').show(); //交付方式
			$('.appCase-box').show(); //应用案例
			$('.app-info-box').show(); //技术优势
			$('.app-info-box .app-saas-info').hide(); //技术优势
			$('.downloadAddress-isOnLine-box').show(); //下载地址
		}
	}
}//根据类型渲染商品类型相关的html
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
	if($('#lastCategory').html().length > 0){
		catId =$('#lastCategory').val();
	}else{
		catId = $(that).val();
	}
	console.log('22222222++++==='+catId)
}
function selectCatId(that){
	catId = $(that).val();
	console.log('33333333++++==='+catId)
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
		html2 += '<td class="errorNum-input"><div class="inputbox"><input type="text" name="fieldName" value="'+data.fieldName+'" placeholder="请输入字段"></div></td>';
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
	console.log(JSON.stringify(dataModel));
	$('.dataModel-info-box select[name="modelType"]').val(dataModel.modelType); //模型类型
	$('.dataModel-info-box select[name="complexity"]').val(dataModel.complexity);//复杂度
	$('.dataModel-info-box select[name="maturity"]').val(dataModel.maturity);//成熟度
	$('.dataModel-info-box textarea[name="aexp"]').val(dataModel.aexp);//应用经验
	$('#aexp_s').html(getLength(dataModel.aexp));//应用经验长度
	$('.dataModel-info-box textarea[name="relationServ"]').val(dataModel.relationServ);//配套服务
	$('#relationServ_s').html(getLength(dataModel.relationServ));//配套服务长度
	$('.dataModel-isOnLine-box input[name="modelFile"]').val(dataModel.modelFile.fileAddress);//模型类型
	$('.dataModel-isOnLine-box input[name="modelFiles"]').val(dataModel.modelFile.fileAddress);//模型类型
	$('.dataModel-isOnLine-box input[name="configFile"]').val(dataModel.configFile.fileAddress);//模型类型
	$('.dataModel-isOnLine-box input[name="configFiles"]').val(dataModel.configFile.fileAddress);//模型类型
	$('.dataModel-isOnLine-box input[name="configParams"]').val(dataModel.paramFile.fileAddress);//模型类型
	$('.dataModel-isOnLine-box input[name="configParam"]').val(dataModel.paramFile.fileAddress);//模型类型
	$('.dataModel-isOnLine-box input[name="modelFilePwd"]').val(dataModel.modelFile.filePwd);//模型文件密码
	$('.dataModel-isOnLine-box input[name="configFilePwd"]').val(dataModel.configFile.filePwd);//配置文件密码
	$('.dataModel-isOnLine-box input[name="configParamsPwd"]').val(dataModel.paramFile.filePwd);//配置参数
	$('.dataModel-isOnLine-box input[name="concatName"]').val(dataModel.concatInfo.concatName);//联系名称
	$('.dataModel-isOnLine-box input[name="concatPhone"]').val(dataModel.concatInfo.concatPhone);//联系电话
	$('.dataModel-isOnLine-box input[name="concatEmail"]').val(dataModel.concatInfo.concatEmail);//联系邮箱
}
function renderToolInfo(atAloneSoftware){
	$('.tool-info-box #textareaAs').val(atAloneSoftware.coreFunction);
	$('.tool-info-box #textareaBs').val(atAloneSoftware.technologicalSuperiority);
	$('.tool-info-box #textareaCs').val(atAloneSoftware.teamAdvantage);
	$('.tool-info-box #textareaDs').val(atAloneSoftware.desiredEnvironment);
	$('.tool-info-box #textareaEs').val(atAloneSoftware.dataNeeded);
	$('.visitOnline-isOnLine-box input[name="dataAddress"]').val(atAloneSoftware.dataAddress);
}
function renderToolSaasInfo(atSaaS){
	$('.tool-info-box #textareaAs').val(atSaaS.coreFunction);
	$('.tool-info-box #textareaBs').val(atSaaS.technologicalSuperiority);
	$('.tool-info-box #textareaCs').val(atSaaS.teamAdvantage);
	$('.tool-info-box #textareaDs').val(atSaaS.desiredEnvironment);
	$('.tool-info-box #textareaEs').val(atSaaS.dataNeeded);
	$('.visitOnline-isOnLine-box input[name="dataAddress"]').val(atSaaS.dataAddress);
}
function renderAppInfo(asAloneSoftware){
	console.log(asAloneSoftware.coreFunction);
	$('.downloadAddress-isOnLine-box input[name="dataAddress"]').val(asAloneSoftware.dataAddress);
	$('.app-info-box #textareaA').val(asAloneSoftware.coreFunction);
	$('.app-info-box #textareaB').val(asAloneSoftware.technologicalSuperiority);
	$('.app-info-box #textareaC').val(asAloneSoftware.teamAdvantage);
	$('.app-info-box #textareaD').val(asAloneSoftware.desiredEnvironment);
	$('.app-info-box #textareaE').val(asAloneSoftware.dataNeeded);
}
function renderAppSaasInfo(asSaaS){
	$('.downloadAddress-isOnLine-box input[name="dataAddress"]').val(asSaaS.dataAddress);
	$('.app-info-box #textareaA').val(asSaaS.coreFunction);
	$('.app-info-box #textareaB').val(asSaaS.technologicalSuperiority);
	$('.app-info-box #textareaC').val(asSaaS.teamAdvantage);
	$('.app-info-box #textareaD').val(asSaaS.desiredEnvironment);
	$('.app-info-box #textareaE').val(asSaaS.dataNeeded);
}
function renderFormatList(formatList){
	var html = '';
	var len = formatList.length;
	$.each(formatList,function(index,data){
		html += '<tr class="parent-tr">';
		html += '<td class="name-input"><div class="inputbox"><input type="text" datatype="name" value="'+data.formatName+'" placeholder="请输入名称"></div></td>';
		html += '<td class="number-input"><div class="inputbox"><input type="text" datatype="number" value="'+data.number+'" digits="true" placeholder="请输入规格" min="0"></div></td>';
		html += '<td style="width: 40px;"><div class="selectbox"><select name="format">';
		if(data.format == 0){
			html += '<option value="0" selected="selected">次</option>';
		}else{
			html += '<option value="0">次</option>';
		}
		if(data.format == 1){
			html += '<option value="1" selected="selected">月</option>';
		}else{
			html += '<option value="1">月</option>';
		}
		if(data.format == 2){
			html += '<option value="2" selected="selected">年</option>';
		}else{
			html += '<option value="2">年</option>';
		}
		if(data.format == 3){
			html += '<option value="3" selected="selected">套</option>';
		}else{
			html += '<option value="3">套</option>';
		}
		html += '</select></div></td>';
		html += '<td class="price-input price-input1"><div class="inputbox"><input class="price-inputs number " min="0" datatype="settlementPrice" value="'+data.settlementPrice / 100+'" type="text" placeholder="请输入结算价"></div></td>';
		html += '<td class="price-input price-input2"><div class="inputbox"><input class="price-inputs number " min="0" datatype="agencyPrice" value="'+data.agencyPrice / 100+'" type="text" placeholder="请输入代理价"></div></td>';
		html += '<td class="price-input price-input3"><div class="inputbox"><input class="price-inputs number " min="0" datatype="price" value="'+data.price / 100+'" type="text" placeholder="请输入零售价"></div></td>';
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
		$('.isOnsale-box').show();
		$('#indate').val(onsaleStartDate);
		$('#indate_s').val(onsaleStartDate);
	}
}
$.jeDate("#indate", {
	format: "YYYY-MM-DD hh:mm:ss",
	isTime: true,
	minDate: $.nowDate(0),
	choosefun: function(elem, val, date) {
		$('#indate_s').val(val)
	},
	okfun:function(elem, val, date){
		$('#indate_s').val(val)
	},
	clearfun:function(elem, val) {
		$('#indate_s').val('');
	}
});
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
		var lastChild = $('table[d-type="priceHtml"] tbody tr').last();
		if ($(lastChild).children('.number-input').find('input').val() && $(lastChild).children('.price-input1').find('input').val() && $(lastChild).children('.price-input2').find('input').val() && $(lastChild).children('.price-input3').find('input').val()) {
			if($('[name=isBook]:checked').val() == 1){
				if(!$('#indate_s').val()){
					$.alert('上架时间不能为空',true,function(){});
					return;
				}
			}
			if(goodsTypeId == 0 || goodsTypeId == 1 || goodsTypeId == 2){
				if($.trim(editor1.$txt.text()).length > 0){
					if($.trim(editor2.$txt.text()).length > 0){
						if($.trim(editor3.$txt.text()).length > 0){
							backAddFn(submitGoodsPublish())
						}else{
							$.alert('售后服务不能为空',true,function () {})
						}
					}else{
						$.alert('商品优势不能为空',true,function () {})
					}
				}else{
					$.alert('商品描述不能为空',true,function () {})
				}
			}else if(goodsTypeId == 4){
				if($.trim(editor3.$txt.text()).length > 0){
					if($.trim(editorAs.$txt.text()).length > 0){
						if($.trim(editorBs.$txt.text()).length > 0){
							if($.trim(editorCs.$txt.text()).length > 0){
								if($.trim(editorDs.$txt.text()).length > 0){
									backAddFn(submitGoodsPublish())
								}else{
									$.alert('所需环境不能为空',true,function () {})
								}
							}else{
								$.alert('团队优势不能为空',true,function () {})
							}
						}else{
							$.alert('技术优势不能为空',true,function () {})
						}
					}else{
						$.alert('核心功能不能为空',true,function () {})
					}
				}else{
					$.alert('售后服务不能为空',true,function () {})
				}
			}else if(goodsTypeId == 5){
				if($.trim(editor3.$txt.text()).length > 0){
					if($.trim(editorAs.$txt.text()).length > 0){
						if($.trim(editorCs.$txt.text()).length > 0){
							if($.trim(editorDs.$txt.text()).length > 0){
								backAddFn(submitGoodsPublish())
							}else{
								$.alert('所需环境不能为空',true,function () {})
							}
						}else{
							$.alert('团队优势不能为空',true,function () {})
						}
					}else{
						$.alert('核心功能不能为空',true,function () {})
					}
				}else{
					$.alert('售后服务不能为空',true,function () {})
				}
			}else if(goodsTypeId == 6){
				if($.trim(editor3.$txt.text()).length > 0){
					if($.trim(editorA.$txt.text()).length > 0){
						if($.trim(editorB.$txt.text()).length > 0){
							if($.trim(editorC.$txt.text()).length > 0){
								if($.trim(editorD.$txt.text()).length > 0){
									backAddFn(submitGoodsPublish())
								}else{
									$.alert('所需环境不能为空',true,function () {})
								}
							}else{
								$.alert('团队优势不能为空',true,function () {})
							}
						}else{
							$.alert('技术优势不能为空',true,function () {})
						}
					}else{
						$.alert('核心功能不能为空',true,function () {})
					}
				}else{
					$.alert('售后服务不能为空',true,function () {})
				}
			}else if(goodsTypeId == 7){
				if($.trim(editor3.$txt.text()).length > 0){
					if($.trim(editorA.$txt.text()).length > 0){
						if($.trim(editorC.$txt.text()).length > 0){
							if($.trim(editorD.$txt.text()).length > 0){
								backAddFn(submitGoodsPublish())
							}else{
								$.alert('所需环境不能为空',true,function () {})
							}
						}else{
							$.alert('团队优势不能为空',true,function () {})
						}
					}else{
						$.alert('核心功能不能为空',true,function () {})
					}
				}else{
					$.alert('售后服务不能为空',true,function () {})
				}
			}
		} else {
			$.alert('请完善价格及规格信息',true,function(){});
		}
	}
});


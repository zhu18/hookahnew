var imgSrc = "";
var regionParam = 100000;
$.getUrlParam = function (key) {
	var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
	var result = window.location.search.substr(1).match(reg);
	return result ? decodeURIComponent(result[2]) : null;
}

var catId = $.getUrlParam('catId'),
	category = $.getUrlParam('category'),
	url = '/category/findAttr';
$(document).ready(function () {
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
	$('.search-select-box').click(function () {
		$(this).children('.search-option').show();
	});
	$('.search-option').mouseleave(function () {
		$(this).hide();
	})

	$('.category-title-box').text(category);

	loadData(url, catId); //加载商品属性数据
	function loadData(url, catId) {
		$.ajax({
			type: "get",
			url: url,
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
});

function tableDelete(that) {
	$(that).parents('.parent-tr').remove();
}
var itemNum = 0;
function tablePlus(that) {
	if ($(that).hasClass('itemNum')) {
		itemNum += 1;
	}
	var priceHtml = '';//规格与价格
	priceHtml += '<tr class="parent-tr">';
	priceHtml += '<td class="name-input"><div class="inputbox"><input type="text" datatype="name" placeholder="请输入名称"></div></td>';
	priceHtml += '<td class="number-input"><div class="inputbox"><input type="number" datatype="number" placeholder="请输入规格"></div></td>';
	priceHtml += '<td><div class="selectbox"><select name="format"><option value="0">次</option><option value="1">天</option><option value="2">年</option></select></div></td>';
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
	returnHtml += '<td class="errorNum-input"><div class="inputbox"><input type="text" placeholder="请输入错误码" name="fieldName"></div></td>';
	returnHtml += '<td><div class="inputbox"><textarea placeholder="请输入说明" name="describle"></textarea></div></td>';
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
		$(that).addClass('table-delete-btn').removeClass('table-plus-btn').text('-').attr('onclick', 'tableDelete(this)');
	}
};
$('#preview-div').hover(function () {
	if ($('#preview-img').attr('src')) {
		$('#replace-btn').toggle();
	}
});
$('#J-goodsName').blur(function () {
	count(this)
});
var countnums=(function(){
	var trim=function(strings){
		return (strings||"").replace(/^(\s|\u00A0)+|(\s|\u00A0)+$/g,"");//+表示匹配一次或多次，|表示或者，\s和\u00A0匹配空白字符，/^以……开头，$以……结尾，/g全局匹配,/i忽略大小写
	}
	return function(_str){
		_str=trim(_str);   //去除字符串的左右两边空格
		var strlength=_str.length;
		if(!strlength){   //如果字符串长度为零，返回零
			return 0;
		}
		var chinese=_str.match(/[\u4e00-\u9fa5]/g); //匹配中文，match返回包含中文的数组
		return strlength+(chinese?chinese.length:0); //计算字符个数
	}
})();
function count(tThis){
	var charnum=countnums(tThis.value)
	var showid=$("#showcontent");
	if(charnum > 30){
		showid.addClass('color-red')
		showid.html(charnum - 30);
		$(tThis).parent().siblings('.errors').show();
	}else{
		showid.removeClass('color-red')
		showid.html(charnum);
		$(tThis).parent().siblings('.errors').hide();
	}
}
$('#J-goodsName').on('input onporpertychange',function () {
	count(this)
});
$('#J-goodsName').on('focus',function () {
	count(this)
});
$('#J-goodsBrief').blur(function () {
	count2(this)
});
function count2(tThis){
	var charnum=countnums(tThis.value)
	var showid=$("#showcontent2");
	if(charnum > 200){
		showid.addClass('color-red')
		showid.html(charnum - 200);
		$(tThis).parent().siblings('.errors').show();
	}else{
		showid.removeClass('color-red')
		showid.html(charnum);
		$(tThis).parent().siblings('.errors').hide();
	}
}
$('#J-goodsBrief').on('input onporpertychange',function () {
	count2(this)
});
$('#J-goodsBrief').on('focus',function () {
	count2(this)
});
$('.pusGoods-btn').click(function () {
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
	data.goodsImg = imgSrc;
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
			listData.fieldName = $(this).find('input[name="fieldName"]').val();
			listData.describle = $(this).find('textarea[name="describle"]').val();
			data.apiInfo.respParamList.push(listData);
		});
		data.apiInfo.respSample = $('.api-info-box').find('textarea[name="respSample"]').val();
	}else{
		data.uploadUrl = $('#J_fileUploadSS').val();
	}

	if (!!!data.goodsName) {
		alert("请输入商品名称");
	} else if (!!!data.goodsBrief) {
		alert("请输入商品简介");
	} else if (!!!data.goodsImg) {
		alert("请上传图片");
	} else {
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
					$.confirm('<h3>发布成功</h3><p>继续发布商品吗?</p>', null, function (type) {
						if (type == 'yes') {
							window.location.href = "/usercenter/goodsPublish";
						} else {
							window.location.href = "/usercenter/goodsManage";
						}
					});
				} else {
					Loading.stop()
					$.alert(data.message, true);
				}
			}
		});
	}

});

function goodsTypes(that) {
	if ($(that).val() == 0) {
		$('.api-info-box').hide();
		$('.file-info-box').show();
	} else {
		$('.file-info-box').hide();
		$('.api-info-box').show();
	}
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
	minDate: $.nowDate(0)
});

// window.onbeforeunload = function () {
// 	if ($('.pusGoods-btn').hasClass('trues')) {
// 		return;
// 	} else {
// 		return '确定要离开吗';
// 	}
// };
loadRegion('province', regionParam);
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

var fileUploadUrl = host.static+'/upload/fileUpload';
$('#fileupload').fileupload({
	url: fileUploadUrl,
	dataType: 'json',
	done: function (e, data) {
		if(data.result.code == 1){
			var obj = data.result.data[0];
			$("#preview-img").attr("src", obj.absPath);
			imgSrc = obj.absPath;
		}else{
			$.alert(data.result.message)
		}

	},
	progressall: function (e, data) {

	}
});
var filenames = '';
$('#fileupload2').fileupload({
	url: fileUploadUrl,
	dataType: 'json',
	done: function (e, data) {
		if(data.result.code == 1){
			var obj = data.result.data[0];
			filenames = data.files[0].name;
			$("#J_fileUploadSS").val(obj.absPath);
			$('.fileUploads span').html(filenames);
		}else{
			$.alert(data.result.message)
		}

	},
	progressall: function (e, data) {

	}
});





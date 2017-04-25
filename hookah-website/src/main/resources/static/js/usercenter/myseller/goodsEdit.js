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
			var typeList = data.attrTypelist;
			for (var i = 0; i < data.attrTypelist.length; i++) {
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
})

function tableDelete(that) {
	$(that).parents('.parent-tr').remove();
};
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
function onfocusFn(that) {
	$(that).parent().siblings('.errors').show();
};
$('#J-goodsName').blur(function () {
	if ($(this).val() && $(this).val().length <= 30) {
		$(this).parents('.text-box').siblings('.errors').hide();
	}
});
$('#J-goodsBrief').blur(function () {
	if ($(this).val() && $(this).val().length <= 200) {
		$(this).parent().siblings('.errors').hide();
	}
});
$('#J-goodsBrief').on('input onporpertychange',function () {
	if ($(this).val() && $(this).val().length <= 200) {
		$(this).parent().siblings('.errors').hide();
	}
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
		attrTypeList.attrlist = $(this).val();
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
	data.isOnsale = $('input[name="isOnsale"]:checked').val();
	if (data.isOnsale == 1) {
		data.onsaleStartDate = $('#indate').val();
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
		data.goodsArea = $('select[name="city"]').val();
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
	} else {
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
})





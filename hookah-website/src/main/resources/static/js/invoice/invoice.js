var subType = null;//定义发票添加或修改类型
var editTitleId = null;
var isLoadZ = false; //
var isLoadAddress = false;
var invoiceStatus = ''; //专用发票状态
var regionParam = 100000;
var isAddAddress = 'add';
var eidtAId = '';
var oTitleId = '';
var oAddressId = '';
var oInvoiceInfo = '';
var oInfo = '';
var isEditSpecial = 'add';
var editSpecialTitleId = '';
var invoiceValShow = null;


$('.translate-close-btn').click(function () { //关闭浮层
	$('.translate-bg').hide();
});
$('.add-title').click(function () {//显示添加发票抬头
	subType = 'add';
	$('.add-title-box').show();
});
$('.tab-btn a').click(function () {
	var index = $(this).index();
	$(this).addClass('hover').siblings().removeClass('hover');
	$('.tab-box table').eq(index).addClass('hover').siblings().removeClass('hover');
	$('.info-t div').eq(index).addClass('hover').siblings().removeClass('hover');
});
var regex = {
	titleName: /^[\u4e00-\u9fa5]{1,50}$/,    //发票抬头
	taxpayerIdentifyNo: /^[0-9A-Z]{15,20}$/,     //纳税人识别号
	regAddress: /[\u4e00-\u9fa50-9a-zA-Z]{1,50}/,    //发票--注册地址
	regTel: /[0-9]{1,15}$/,    //发票--注册电话
	openBank: /[\u4e00-\u9fa5]{1,50}$/,    //开户银行
	bankAccount: /^[0-9]{1,50}$/,    //银行账号
	invoiceName: /^[a-zA-Z]{1,15}$|^[\u4e00-\u9fa5]{1,15}$/,    //收票姓名
	mobile: /^0?(13[0-9]|14[5-9]|15[012356789]|66|17[0-9]|18[0-9]|19[8-9])[0-9]{8}$/,    //收票手机
	address: /[\u4e00-\u9fa5a-zA-Z0-9]{1,150}/,    //收票地址
	fixedLine1: /^[0-9]{3,4}$/,    //固话一
	fixedLine2: /^[0-9]{1,10}$/,    //固话二
	postCode: /^[0-9]{6,6}$/,    //邮编
};
function getInvoiceInfo() {
	$.ajax({
		url: host.website + '/api/userInvoiceTitle/findAll',
		type: 'get',
		data: {
			userInvoiceType: 0
		},
		success: function (data) {
			if (data.code == 1) {
				var html = '';
				html += '<div class="input-invoice hover" invoiceid="" titleids=""><input type="text" readonly="readonly" value="个人"></div>';
				if (data.data.length > 0) {
					for (var i = 0; i < data.data.length; i++) {
						html += '<div class="input-invoice" invoiceid="' + data.data[i].taxpayerIdentifyNo + '" titleids="' + data.data[i].titleId + '"><input type="text" readonly="readonly" value="' + data.data[i].titleName + '"><a href="javascript:void(0)" class="editTitle" titleid="' + data.data[i].titleId + '" titleName="' + data.data[i].titleName + '" taxpayerIdentifyNo="' + data.data[i].taxpayerIdentifyNo + '">编辑</a><a href="javascript:void(0);" class="delTitle" titleid="' + data.data[i].titleId + '">删除</a></div>';
					}
				}
				$('.title-boxes').html(html);
				selectThisTitle();
				deleteInvoice();
				EditInvoice();
				var invoicePriceAA = null;
				if(window.location.pathname == '/order/directInfo' || window.location.pathname == '/order/orderInfo'){
					invoicePriceAA = $('#pay-money').html();
				}else if(window.location.pathname == '/usercenter/myInvoice'){
					invoicePriceAA = invoicePriceAB;
				}
				$('.J_invoicePriceBox').html(invoicePriceAA);
				$('.J_nsrsbh').hide();
			} else {
				$.alert(data.message)
			}
		}
	})
}
function cancelAddTitle() {//取消添加发票抬头
	$('.add-title-box').hide();
	$('input[name=titleName_1]').val('')
	$('input[name=taxpayerIdentifyNo_1]').val('');
}
function submitAddTitle() {//添加发票title userInvoiceTitle
	var titleName = $('input[name=titleName_1]').val();
	var taxpayerIdentifyNo = $('input[name=taxpayerIdentifyNo_1]').val();
	var data = {
		userInvoiceType: 0,
		titleName: titleName,
		taxpayerIdentifyNo: taxpayerIdentifyNo
	};
	if (subType == 'add') {
		url = '/api/userInvoiceTitle/save'
	} else {
		url = '/api/userInvoiceTitle/edit'
		data.titleId = editTitleId;
	}
	if (regex.titleName.test(titleName)) {
		if (regex.taxpayerIdentifyNo.test(taxpayerIdentifyNo)) {
			$.ajax({
				url: host.website + url,
				type: 'post',
				data: {
					userInvoiceTitle: JSON.stringify(data)
				},
				success: function (data) {
					if (data.code == 1) {
						$('.add-title-box').hide();
						getInvoiceInfo()
						$('input[name=titleName_1]').val('')
						$('input[name=taxpayerIdentifyNo_1]').val('');
						$.alert('保存成功')
					} else {
						$.alert(data.message)
					}
				}
			})
		} else {
			$.alert('请按要求输入纳税人识别号');
		}
	} else {
		$.alert('请按要求输入发票抬头');
	}
}
function selectThisTitle(event) { //选择发票信息
	$('.input-invoice').click(function (event) {
		$(this).addClass('hover').siblings().removeClass('hover');
		var invoiceid = $(this).attr('invoiceid');
		invoiceValShow = $(this).children('input').val();
		// $('.invoceVal').html(invoiceValShow);
		$('.p_input-exclusive').val(invoiceid);
		if($(this).attr('invoiceid')){
			$('.J_nsrsbh').show();
		}else{
			$('.J_nsrsbh').hide();
		}
		event.stopPropagation()
	})
}
$('input[name=taxpayerIdentifyNo_1]').on('input onporpertychange', function () {
	$(this).val($(this).val().toUpperCase());
});
$('input[name=Z_taxpayerIdentifyNo]').on('input onporpertychange', function () {
	$(this).val($(this).val().toUpperCase());
});
function deleteInvoice() { //删除发票抬头
	$('.delTitle').click(function (event) {
		var num = $(this).attr('titleid');
		var that = $(this)
		$.confirm('你确定要删除此条信息吗? ', null, function (type) {
			if (type == 'yes') {
				this.hide();
				$.ajax({
					url: host.website + '/api/userInvoiceTitle/del',
					type: 'get',
					data: {
						titleId: num
					},
					success: function (data) {
						if (data.code == 1) {
							// getInvoiceInfo();
							$.alert('删除成功');
							that.parent('.input-invoice').remove();
						} else {
							$.alert(data.message)
						}
					}
				})
			} else {
				this.hide();
			}
		});
		event.stopPropagation()
	})
}
function EditInvoice() { //修改发票抬头
	$('.editTitle').click(function (event) {
		editTitleId = $(this).attr('titleid');
		var titleName = $(this).attr('titleName');
		var taxpayerIdentifyNo = $(this).attr('taxpayerIdentifyNo');
		subType = 'edit';
		$('input[name=titleName_1]').val(titleName)
		$('input[name=taxpayerIdentifyNo_1]').val(taxpayerIdentifyNo)
		$('.add-title-box').show();
		event.stopPropagation()
	})
}
$('#J_expertBtn').click(function () { //切换到专用发票
	if (!isLoadZ) {
		getExpert()
	}
});
var resetEditSpecialTitle = null;

function getExpert() {//获取专用发票信息
	$.ajax({
		url: host.website + '/api/userInvoiceTitle/findAll',
		type: 'get',
		data: {
			userInvoiceType: 1
		},
		success: function (data) {
			if (data.code == 1) {
				isLoadZ = true;
				resetEditSpecialTitle = data;
				$('.J_edit_invoice').attr('tid',data.data.tid);
				switch (data.data.invoiceStatus) {
					case 0:
						invoiceStatus = '未添加';
						$('.Z_set_btn').hide();//设置按钮
						$('.add-go-address-special').hide();//跳转到地址管理页面
						break;
					case 1:
						invoiceStatus = '<span style="color:#fff000">审核中</span>';
						$('.submit-invoice').hide();
						$('.cancel-edit').hide();
						setInvoiceVal(data);
						$('.add-go-address-special').attr('tid',data.data.titleId);
						$('.Z_invoice_item_bot').hide();  //增票资质确认书
						$('.Z_set_btn').hide();//设置按钮
						$('.add-go-address-special').show();//跳转到地址管理页面
						$('.Z_ssac .text-input').css({'border': 'none'}).attr('readonly', 'readonly');
						break;
					case 2:
						invoiceStatus = '<span style="color:#0f0">已添加</span>';
						setInvoiceVal(data);
						$('.J_edit_invoice').attr('tid',data.data.titleId);
						$('.submit-invoice').hide();
						$('.Z_invoice_item_bot').hide();  //增票资质确认书
						$('.add-go-address-special').show();//跳转到地址管理页面
						$('.Z_ssac .text-input').css({'border': 'none'}).attr('readonly', 'readonly');
						$('.Z_set_btn.J_reset_invoice').hide();
						$('.J_del_invoice').attr('tid',data.data.titleId);
						break;
					case 3:
						invoiceStatus = '<span style="color:#f00">未通过</span>';
						$('.Z_set_btn').hide();
						setInvoiceVal(data);
						$('.J_edit_invoice').attr('tid',data.data.titleId);
						$('.Z_ssac .text-input').css({'border': 'none'}).attr('readonly', 'readonly');
						$('.Z_set_btn.J_reset_invoice').show();
						$('.Z_set_btn.J_edit_invoice').show();
						$('.Z_invoice_item_bot').hide();  //增票资质确认书
						$('.add-go-address-special').show();//跳转到地址管理页面
						$('.submit-invoice').hide();
						$('.cancel-edit').hide();
						break;
				}
				$('.Z_ssac .invoiceStatus').html(invoiceStatus)
			} else {
				$.alert(data.message);
				isLoadZ = false;
			}
		}
	})
}
$('.J_edit_invoice').click(function(){
	setInvoiceVal(resetEditSpecialTitle);
	$('.add-go-address-special').hide();
	$('.submit-invoice').show();
	$('.cancel-edit').show();
	isEditSpecial = 'modify';
	editSpecialTitleId = $(this).attr('tid');
	$('.Z_invoice_item_bot').show();  //增票资质确认书
	$('.Z_ssac .text-input').css({'border': '1px solid #e5e5e5'}).removeAttr('readonly');
});
$('.J_reset_invoice').click(function(){
	removeInvoiceVal();
	$('.add-go-address-special').hide();
	$('.submit-invoice').show();
	$('.cancel-edit').show();
	isEditSpecial = 'modify';
	editSpecialTitleId = $(this).attr('tid');
	$('.Z_invoice_item_bot').show();  //增票资质确认书
	$('.Z_ssac .text-input').css({'border': '1px solid #e5e5e5'}).removeAttr('readonly');
});
$('.J_del_invoice').click(function (event) {
	var tid = $(this).attr('tid');
	console.log(tid)
	$.confirm('你确定要删除此条信息吗? ', null, function (type) {
		if (type == 'yes') {
			this.hide();
			$.ajax({
				url: host.website + '/api/userInvoiceTitle/del',
				type: 'get',
				data: {
					titleId: tid
				},
				success: function (data) {
					if (data.code == 1) {
						$.alert('删除成功');
						removeInvoiceVal();
						$('.invoiceStatus').html('未添加')
						$('.Z_ssac .text-input').css({'border': '1px solid #e5e5e5'}).removeAttr('readonly');
						$('.submit-invoice').show();
						$('.cancel-edit').show();
						$('.Z_invoice_item_bot').show();
						$('.add-go-address-special').hide();
					} else {
						$.alert(data.message)
					}
				}
			})
		} else {
			this.hide();
		}
	});
});
$('.cancel-edit').click(function(){
	setInvoiceVal(resetEditSpecialTitle);
	$('.add-go-address-special').show();
	$('.submit-invoice').hide();
	$('.cancel-edit').hide();
	$('.Z_invoice_item_bot').hide();  //增票资质确认书
	isEditSpecial = 'add';
	$('.Z_ssac .text-input').css({'border': 'none'}).attr('readonly', 'readonly');
});
function setInvoiceVal(data) {
	$('.tab-box input[name=Z_titleName]').val(data.data.titleName);
	$('.tab-box input[name=Z_taxpayerIdentifyNo]').val(data.data.taxpayerIdentifyNo);
	$('.tab-box input[name=Z_regAddress]').val(data.data.regAddress);
	$('.tab-box input[name=Z_regTel]').val(data.data.regTel);
	$('.tab-box input[name=Z_openBank]').val(data.data.openBank);
	$('.tab-box input[name=Z_bankAccount]').val(data.data.bankAccount);
}
function removeInvoiceVal(data) {
	$('.tab-box input[name=Z_titleName]').val('');
	$('.tab-box input[name=Z_taxpayerIdentifyNo]').val('');
	$('.tab-box input[name=Z_regAddress]').val('');
	$('.tab-box input[name=Z_regTel]').val('');
	$('.tab-box input[name=Z_openBank]').val('');
	$('.tab-box input[name=Z_bankAccount]').val('');
}
function testInvoiceInfo(titleName, taxpayerIdentifyNo, regAddress, regTel, openBank, bankAccount) { //验证专用发票
	if (regex.titleName.test(titleName)) {
		$('input[name=Z_titleName]').siblings('.must-tip').hide();
		if (regex.taxpayerIdentifyNo.test(taxpayerIdentifyNo)) {
			$('input[name=Z_taxpayerIdentifyNo]').siblings('.must-tip').hide();
			if (regex.regAddress.test(regAddress)) {
				$('input[name=Z_regAddress]').siblings('.must-tip').hide();
				if (regex.regTel.test(regTel)) {
					$('input[name=Z_regTel]').siblings('.must-tip').hide();
					if (regex.openBank.test(openBank)) {
						$('input[name=Z_openBank]').siblings('.must-tip').hide();

						if (regex.bankAccount.test(bankAccount)) {
							$('input[name=Z_bankAccount]').siblings('.must-tip').hide();
							return true;
						} else {
							$('input[name=Z_bankAccount]').siblings('.must-tip').show();
							return false;
						}
					} else {
						$('input[name=Z_openBank]').siblings('.must-tip').show();
						return false;
					}
				} else {
					$('input[name=Z_regTel]').siblings('.must-tip').show();
					return false;
				}
			} else {
				$('input[name=Z_regAddress]').siblings('.must-tip').show();
				return false;
			}
		} else {
			$('input[name=Z_taxpayerIdentifyNo]').siblings('.must-tip').show();
			return false;
		}
	} else {
		$('input[name=Z_titleName]').siblings('.must-tip').show();
		return false;
	}
}
$('.submit-invoice').click(function () {
	if(!$(".J-invoiceZZ").is(':checked')){
		$.alert('请阅读并同意《增票资质确认书》');
		return;
	}
	var titleName = $('input[name=Z_titleName]').val();
	var taxpayerIdentifyNo = $('input[name=Z_taxpayerIdentifyNo]').val();
	var regAddress = $('input[name=Z_regAddress]').val();
	var regTel = $('input[name=Z_regTel]').val();
	var openBank = $('input[name=Z_openBank]').val();
	var bankAccount = $('input[name=Z_bankAccount]').val();
	if ($('.Z_ssac').hasClass('hover')) {
		// if (invoiceStatus == '未添加') {
			if (testInvoiceInfo(titleName, taxpayerIdentifyNo, regAddress, regTel, openBank, bankAccount)) {
				var data = {
					userInvoiceType: 1,
					titleName: titleName,
					taxpayerIdentifyNo: taxpayerIdentifyNo,
					regAddress: regAddress,
					regTel: regTel,
					openBank: openBank,
					bankAccount: bankAccount,
					invoiceStatus: 1
				};
				var url = '';
				if(isEditSpecial == 'add'){
					url = '/api/userInvoiceTitle/save';
				}else if(isEditSpecial == 'modify'){
					url = '/api/userInvoiceTitle/edit';
					data.titleId = editSpecialTitleId;
					console.log(editSpecialTitleId)
				}
				$.ajax({
					url: host.website + url,
					type: 'post',
					data: {
						userInvoiceTitle: JSON.stringify(data)
					},
					success: function (data) {
						if (data.code == 1) {
							$.alert('提交成功，请选择售票地址');
							console.log(123123);
							getExpert()
						} else {
							$.alert(data.message);
						}
					}
				})
			}
		// } else {
		// 	console.log('已添加');
		// }
	} else {
		//普通发票

	}
});
$('.add-go-address-common').click(function(){
	if ($('.title-boxes').html()) {//判断是否有抬头信息 //true
		var titleIds = null;
		var nums = 0;
		$('.title-boxes .input-invoice').each(function () {
			if ($(this).hasClass('hover')) {
				nums += 1;
				titleIds = $(this).attr('titleids');
			}
		});
		if (nums > 0) {//判断是否选择抬头  //true     --------------------------------------待做赋值处理
			console.log(titleIds);
			oTitleId = titleIds;
			$('.invoiceInfo').hide();
			$('.invoiceAddress').show();
			// oInvoiceInfo='普通发票 办公用品 个人';
			if (!isLoadAddress) {
				getInvoiceAddress()
			}
		} else {
			$.alert('请选择抬头信息')
		}
	} else {
		$.alert('请添加抬头信息');
	}
});
$('.add-go-address-special').click(function () {
	$('.invoiceInfo').hide();
	$('.invoiceAddress').show();
	// console.log($(this).attr('tid'));
	// oInvoiceInfo = '专用发票 办公用品 单位';
	oTitleId = $(this).attr('tid');
	if (!isLoadAddress) {
		getInvoiceAddress()
	}
});
$('.J_gotoInvoiceInfo').click(function () {
	$('.invoiceInfo').show();
	$('.invoiceAddress').hide();
	$('.editAddress').hide();
});
function loadRegion(id, regionParam, display) {//加载地区
	var parentId = '';
	if (regionParam == 100000) {
		parentId = 100000;
	} else {
		parentId = $(regionParam).val();
	}
	// if (parentId == '-1') {
	// 	$('#city').html('<option value="-1">全部</option>')
	// }
	$(regionParam).nextAll('select').html('<option value="-1">全部</option>')
	$.ajax({
		type: "get",
		url: host.website + '/region/getRegionCodeByPid',
		data: {
			parentId: parentId
		},
		success: function (data) {
			if (data.code == 1) {
				if (data.data.length > 0) {
					renderRegion(id, data.data,display);
					$(regionParam).nextAll('select').show()
				}else{
					$(regionParam).nextAll('select').html('<option value="-1">全部</option>').hide()
				}
			} else {
				$.alert(data.message)
			}
		}
	});
}
function loadRegion(id, regionParam, display) {//加载地区
	var parentId = '';
	if (regionParam == 100000) {
		parentId = 100000;
	} else {
		if(isAddAddress == 'add'){
			parentId = $(regionParam).val();
		}else if(isAddAddress == 'modify'){
			parentId = regionParam
		}

	}
	$(regionParam).nextAll('select').html('<option value="-1">全部</option>')
	$.ajax({
		type: "get",
		url: host.website + '/region/getRegionCodeByPid',
		data: {
			parentId: parentId
		},
		success: function (data) {
			if (data.code == 1) {
				if (data.data.length > 0) {
					renderRegion(id, data.data,display);
					$(regionParam).nextAll('select').show()
				}else{
					$(regionParam).nextAll('select').html('<option value="-1">全部</option>').hide()
				}
			} else {
				$.alert(data.message)
			}
		}
	});
}
function renderRegion(id, data, display) {
	var html = '<option value="-1">全部</option>';
	data.forEach(function (e) {
		if (e.id == display) {
			html += '<option selected="selected" value="' + e.id + '">' + e.name + '</option>';
		} else {
			html += '<option value="' + e.id + '">' + e.name + '</option>';
		}
	});
	$('#' + id).html(html);
}
function getInvoiceAddress() {
	Loading.start();
	$.ajax({
		type: "get",
		url: host.website + '/api/userInvoiceAddress/findAll',
		success: function (data) {
			Loading.stop();
			if (data.code == 1) {
				if (data.data.length > 0) {
					isLoadAddress = true;
					var html = '';
					for (var i = 0; i < data.data.length; i++) {
						var isDefault = data.data[i].defaultStatus == 1 ? 'hover': i == 0 ? 'hover':'';
						html += '<div class="addressInfo-item '+isDefault+'" aid="' + data.data[i].id + '" ainfo="'+ data.data[i].receiveAddress + data.data[i].address + '，'+ data.data[i].invoiceName + '，'+ data.data[i].mobile +'">';
						html += '<div class="info-t">';
						html += '<span>收票人：' + data.data[i].invoiceName + '</span>';
						html += '<span>手机号：' + data.data[i].mobile + '</span>';
						if(data.data[i].defaultStatus == 0){
							html += '<span class="address-seting address-default" aid="' + data.data[i].id + '">设为默认</span>';
						}else{
							html += '<span class="address-seting" style="color:#666; display: block">默认地址</span>';
						}
						html += '<span class="address-seting address-editor" aid="' + data.data[i].id + '">修改</span>';
						html += '<span class="address-seting address-del" aid="' + data.data[i].id + '">删除</span>';
						html += '</div>';
						html += '<div class="info-b">收票地址：' + data.data[i].receiveAddress + data.data[i].address + '</div>';
						html += '</div>';
					}
					html += '<div style="text-align: center;" class="form-btn"><a style="display: inline-block;float: none;background: #237ee8;color:#fff;margin: 0 15px;" href="javascript:void(0)" class="ok-invoice">确定</a><a  style="display: inline-block;float: none;margin: 0 15px;" href="javascript:void(0)" class="no-invoice">取消</a></div>';
					$('.addressInfo').html(html);
					$('.addAddress').show();
					$('.address-default').click(function (event) {//删除收票地址
						var id = $(this).attr('aid');
						$.confirm('确定要设为默认收票地址? ', null, function (type) {
							if (type == 'yes') {
								this.hide();
								$.ajax({
									type: "get",
									url: host.website + '/api/userInvoiceAddress/updateDefalutAddr',
									data:{id:id},
									success: function (data) {
										if (data.code == 1) {
											getInvoiceAddress()
										} else {
											$.alert(data.message)
										}
									}
								});
							} else {
								this.hide();
							}
						});
						event.stopPropagation()
					});
					$('.addressInfo-item').click(function (event) { //选择收票地址
						$(this).addClass('hover').siblings().removeClass('hover');
						event.stopPropagation()
					});
					$('.address-editor').click(function (event) {//修改收票地址
						isAddAddress = 'modify';
						$('.J_gotoInvoiceInfo').hide();
						var id = $(this).attr('aid');
						eidtAId = id;
						$.ajax({
							type: "get",
							url: host.website + '/api/userInvoiceAddress/findById',
							data:{id:id},
							success: function (data) {
								if (data.code == 1) {
									$('.editAddress input[name=invoiceName]').val(data.data.invoiceName);   //收票姓名
									$('.editAddress input[name=mobile]').val(data.data.mobile);    //收票手机
									$('.editAddress input[name=province]').val(data.data.areaProvince);    //省
									$('.editAddress input[name=city]').val(data.data.areaCountry);    //市
									$('.editAddress input[name=region]').val(data.data.region);    //区、县
									$('.editAddress input[name=address]').val(data.data.address);    //收票地址
									$('.editAddress input[name=fixedLine1]').val(data.data.areaCode);    //固话一
									$('.editAddress input[name=fixedLine2]').val(data.data.fixedLine);    //固话二
									$('.editAddress input[name=postCode]').val(data.data.postCode); //邮编
									if(data.data.areaProvince > 0){
										loadRegion('province',regionParam , data.data.areaProvince); //加载地区
									}
									if(data.data.areaCity > 0){
										loadRegion('city', data.data.areaProvince, data.data.areaCity); //加载地区
									}
									if(data.data.region > 0){
										loadRegion('region', data.data.areaCity, data.data.region); //加载地区
									}
									$('.editAddress').show();
									$('.addressInfo').hide();
								} else {
									$.alert(data.message)
								}
							}
						});
						event.stopPropagation()
					});
					$('.address-del').click(function (event) {//删除收票地址
						var id = $(this).attr('aid');
						$.confirm('你确定要删除此条信息吗? ', null, function (type) {
							if (type == 'yes') {
								this.hide();
								$.ajax({
									type: "get",
									url: host.website + '/api/userInvoiceAddress/del',
									data:{id:id},
									success: function (data) {
										if (data.code == 1) {
											getInvoiceAddress()
										} else {
											$.alert(data.message)
										}
									}
								});
							} else {
								this.hide();
							}
						});
						event.stopPropagation()
					});
					endSetting()
				} else {
					isLoadAddress = false;
					$('.addAddress').show();
					$('.addressInfo').html('<p style="text-align: center;">暂无收票地址</p>');
				}
			} else {
				$.alert(data.message)
			}
		}
	});
}
$('.addAddressBtn').click(function () {
	$('.editAddress').show();
	$('.addAddress').hide();
	$('.addressInfo').hide();
	$('.J_gotoInvoiceInfo').hide();
	isAddAddress = 'add';
	loadRegion('province', regionParam); //加载地区
});
$('.cancel-add').click(function () {
	$('.editAddress').hide();
	$('.addAddress').show();
	$('.addressInfo').show();
	$('.J_gotoInvoiceInfo').show();
	resetAddAddress();
});
$('.submit-add').click(function () {
	var invoiceName = $('.editAddress input[name=invoiceName]').val(),    //收票姓名
		mobile = $('.editAddress input[name=mobile]').val(),    //收票手机
		address = $('.editAddress input[name=address]').val(),    //收票地址
		fixedLine1 = $('.editAddress input[name=fixedLine1]').val(),    //固话一
		fixedLine2 = $('.editAddress input[name=fixedLine2]').val(),    //固话二
		postCode = $('.editAddress input[name=postCode]').val();
	if (regex.invoiceName.test(invoiceName)) {
		$('.editAddress input[name=invoiceName]').siblings('.must-tip').hide();
		if (regex.mobile.test(mobile)) {
			$('.editAddress input[name=mobile]').siblings('.must-tip').hide();
			if (address.length > 1 && address.length < 150) { //地址验证
				$('.editAddress input[name=address]').siblings('.must-tip').hide();
				if (is_forbid(address)) {
					$('.editAddress input[name=address]').siblings('.must-tip').hide();
					if($('select[name="province"]').val() < 0){
						$('#province').siblings('.must-tip').show();
					}else{
						if($('select[name="province"]').val() >= 710000){
							$('#province').siblings('.must-tip').hide();
							region = $('select[name="region"]').val();
							setInvoiceAddress(invoiceName,mobile,region,address,fixedLine1,fixedLine2,postCode)
						}else{
							if($('select[name="region"]').val() < 0){
								$('#province').siblings('.must-tip').show();
							}else{
								region = $('select[name="region"]').val();
								$('#province').siblings('.must-tip').hide();
								$('.editAddress input[name=postCode]').siblings('.must-tip').hide();
								if(fixedLine1){ //判断电话区号
									if(testPhoneNum(fixedLine1,fixedLine2)){
										if(postCode){
											if(regex.postCode.test(postCode)){
												setInvoiceAddress(invoiceName,mobile,region,address,fixedLine1,fixedLine2,postCode)
											}else{
												$('.editAddress input[name=postCode]').siblings('.must-tip').show();
											}
										}else{
											setInvoiceAddress(invoiceName,mobile,region,address,fixedLine1,fixedLine2,postCode)
										}
									}
								}else{
									if(fixedLine2){//判断电话区号
										if(testPhoneNum(fixedLine1,fixedLine2)){
											if(postCode){
												if(regex.postCode.test(postCode)){
													setInvoiceAddress(invoiceName,mobile,region,address,fixedLine1,fixedLine2,postCode)
												}else{
													$('.editAddress input[name=postCode]').siblings('.must-tip').show();
												}
											}else{
												setInvoiceAddress(invoiceName,mobile,region,address,fixedLine1,fixedLine2,postCode)
											}
										}
									}else{
										if(postCode){
											if(regex.postCode.test(postCode)){
												setInvoiceAddress(invoiceName,mobile,region,address,fixedLine1,fixedLine2,postCode)
											}else{
												$('.editAddress input[name=postCode]').siblings('.must-tip').show();
											}
										}else{
											setInvoiceAddress(invoiceName,mobile,region,address,fixedLine1,fixedLine2,postCode)
										}
									}
								}


								//_____________----------------------------****************
							}
						}
					}
				}else{
					$('.editAddress input[name=address]').siblings('.must-tip2').show();
					return false;
				}

			} else {
				$('.editAddress input[name=address]').siblings('.must-tip1').show();
				return false;
			}
		} else {
			$('.editAddress input[name=mobile]').siblings('.must-tip').show();
			return false;
		}
	} else {
		$('.editAddress input[name=invoiceName]').siblings('.must-tip').show();
		return false;
	}
});
function testPhoneNum(fixedLine1,fixedLine2){ //验证电话
	$('.editAddress input[name=fixedLine1]').siblings('.must-tip').hide();
	if(regex.fixedLine1.test(fixedLine1)){
		if(regex.fixedLine2.test(fixedLine2)){
			return true;
		}else{
			$('.editAddress input[name=fixedLine1]').siblings('.must-tip').show().html('电话为10位以内数字');
			return false;
		}
	}else{
		$('.editAddress input[name=fixedLine1]').siblings('.must-tip').show().html('区号最多4位数字');
		return false;
	}
}
/**
 * 检查是否含有非法字符
 * @param temp_str
 * @returns {Boolean}
 */
function is_forbid(temp_str){
	temp_str = temp_str.replace(/(^\s*)|(\s*$)/g, "");
	temp_str = temp_str.replace('--',"@");
	temp_str = temp_str.replace('/',"@");
	temp_str = temp_str.replace('+',"@");
	temp_str = temp_str.replace('\'',"@");
	temp_str = temp_str.replace('\\',"@");
	temp_str = temp_str.replace('$',"@");
	temp_str = temp_str.replace('^',"@");
	temp_str = temp_str.replace('.',"@");
	temp_str = temp_str.replace(';',"@");
	temp_str = temp_str.replace('<',"@");
	temp_str = temp_str.replace('>',"@");
	temp_str = temp_str.replace('"',"@");
	temp_str = temp_str.replace('=',"@");
	temp_str = temp_str.replace('{',"@");
	temp_str = temp_str.replace('}',"@");
	var forbid_str = new String('@,%,~,&');
	var forbid_array = new Array();
	forbid_array = forbid_str.split(',');
	for(i=0;i<forbid_array.length;i++){
		if(temp_str.search(new RegExp(forbid_array[i])) != -1)
			return false;
	}
	return true;
}
function setInvoiceAddress(invoiceName,mobile,region,address,fixedLine1,fixedLine2,postCode) {
	var data = {
		invoiceName : invoiceName,    //收票姓名
		mobile : mobile,    //收票手机
		region:region,
		address : address,    //收票地址
		areaCode : fixedLine1,    //固话一
		fixedLine : fixedLine2,    //固话二
		postCode : postCode
	};
	var url = '';
	if(isAddAddress == 'add'){
		url='/api/userInvoiceAddress/save'
	}else if(isAddAddress == 'modify'){
		url='/api/userInvoiceAddress/edit'
		data.id = eidtAId;
		console.log(eidtAId);
	}
	$.ajax({
		type: "post",
		url: host.website + url,
		data:{userInvoiceAddress:JSON.stringify(data)},
		success: function (data) {
			if (data.code == 1) {
				getInvoiceAddress();
				resetAddAddress();
				$.alert('保存成功')
				$('.J_gotoInvoiceInfo').show();
			} else {
				$.alert(data.message)
			}
		}
	});
}
function resetAddAddress(){
	$('.editAddress input[name=invoiceName]').val('');   //收票姓名
	$('.editAddress input[name=mobile]').val('');    //收票手机
	$('.editAddress input[name=province]').val('-1');    //省
	$('.editAddress input[name=city]').val('-1');    //市
	$('.editAddress input[name=region]').val('-1');    //区、县
	$('.editAddress input[name=address]').val('');    //收票地址
	$('.editAddress input[name=fixedLine1]').val('');    //固话一
	$('.editAddress input[name=fixedLine2]').val('');    //固话二
	$('.editAddress input[name=postCode]').val(''); //邮编
	$('.editAddress').hide();
	$('.addressInfo').show();
}
function endSetting(){
	$('.ok-invoice').click(function(){
		if ($('.addressInfo').html()) {//判断是否有抬头信息 //true
			var addressIds = null;
			var numsD = 0;
			$('.addressInfo .addressInfo-item').each(function () {
				if ($(this).hasClass('hover')) {
					numsD += 1;
					addressIds = $(this).attr('aid');
					oInfo = $(this).attr('ainfo');
				}
			});
			if (numsD > 0) {//判断是否选择抬头  //true     --------------------------------------待做赋值处理
				oAddressId = addressIds;
				oInvoiceInfo = $('.info-t div.hover').html();
				if(window.location.pathname == '/order/directInfo' || window.location.pathname == '/order/orderInfo' ){
					$('.titleIdO').val(oTitleId);
					$('.addressIdO').val(oAddressId);
					$('.translateInfo span').html('开发票');
					$('.translateInfo-g').html('<p>'+oInvoiceInfo+'</p><p>'+oInfo+'</p>');
					$('#J-noTranslate').show();
					$('.translate-bg').hide();
					$('.invoceVal').html(invoiceValShow);
					console.log(13132123)
				}else if(window.location.pathname == '/usercenter/myInvoice'){
					console.log('qeqweqweqwe')
					if(orderIds){
						var datas = null;
						if(editType == 'add'){
							datas={
								orderIds:orderIds,
								titleId:oTitleId,
								addressId:oAddressId
							};
						}else if(editType == 'modify'){
							datas={
								orderIds:orderIds,
								titleId:oTitleId,
								addressId:oAddressId,
								invoiceId:invoiceId
							};
						}
						$.ajax({
							type: "post",
							url: host.website + '/api/invoice/upsert',
							data:datas,
							success: function (data) {
								if (data.code == 1) {
									$('.translate-bg').hide();
									goPage(1);
									$.alert('保存成功')
								} else {
									$.alert(data.message)
								}
							}
						});
					}
				}
			} else {
				$.alert('请选择收票地址');
			}
		} else {
			$.alert('请添加收票地址');
		}
	});
	$('.no-invoice').click(function(){
		$('.translate-bg').hide();
	})
}




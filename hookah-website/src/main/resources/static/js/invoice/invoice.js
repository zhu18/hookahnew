var subType = null;//定义发票添加或修改类型
var editTitleId = null;
var isLoadZ = false;
var invoiceStatus = ''; //专用发票状态
$('.translate-close-btn').click(function(){ //关闭浮层
	$('.translate-bg').hide();
});
$('.add-title').click(function(){//显示添加发票抬头
	subType = 'add';
	$('.add-title-box').show();
});
var regex = {
	titleName:/[\u4e00-\u9fa5]{1,50}/,    //发票抬头
	taxpayerIdentifyNo:/^[0-9A-Z]{15,20}$/,     //纳税人识别号
	regAddress:/[\u4e00-\u9fa50-9a-zA-Z]{1,50}/,    //发票--注册地址
	regTel:/[0-9]{1,15}/,    //发票--注册电话
	openBank:/[\u4e00-\u9fa5]{1,50}/,    //开户银行
	bankAccount:/[0-9]{1,50}/,    //银行账号
};
function getInvoiceInfo(){
	$.ajax({
		url:host.website+'/api/userInvoiceTitle/findAll',
		type:'get',
		data:{
			userInvoiceType:0
		},
		success:function(data){
			if(data.code==1){
				var html=''
				if(data.data.length > 0){
					for(var i=0;i<data.data.length;i++){
						html+='<div class="input-invoice" invoiceid="'+data.data[i].taxpayerIdentifyNo+'" titleids="'+data.data[i].titleId+'"><input type="text" readonly="readonly" value="'+data.data[i].titleName+'"><a href="javascript:void(0)" class="editTitle" titleid="'+data.data[i].titleId+'" titleName="'+data.data[i].titleName+'" taxpayerIdentifyNo="'+data.data[i].taxpayerIdentifyNo+'">编辑</a><a href="javascript:void(0);" class="delTitle" titleid="'+data.data[i].titleId+'">删除</a></div>';
					}
				}
				$('.title-boxes').html(html);
				selectThisTitle();
				deleteInvoice();
				EditInvoice();
			}else{
				$.alert(data.message)
			}
		}
	})
}
function cancelAddTitle(){//取消添加发票抬头
	$('.add-title-box').hide();
	$('input[name=titleName_1]').val('')
	$('input[name=taxpayerIdentifyNo_1]').val('');
}
function submitAddTitle(){//添加发票title userInvoiceTitle
	var titleName = $('input[name=titleName_1]').val();
	var taxpayerIdentifyNo = $('input[name=taxpayerIdentifyNo_1]').val();
	var data = {
		userInvoiceType:0,
		titleName:titleName,
		taxpayerIdentifyNo:taxpayerIdentifyNo
	}
	if(subType == 'add'){
		url='/api/userInvoiceTitle/save'
	}else{
		url = '/api/userInvoiceTitle/edit'
		data.titleId = editTitleId;
	}
	if(regex.titleName.test(titleName)){
		if(regex.taxpayerIdentifyNo.test(taxpayerIdentifyNo)){
			$.ajax({
				url:host.website+url,
				type:'post',
				data:{
					userInvoiceTitle:JSON.stringify(data)
				},
				success:function(data){
					if(data.code==1){
						$('.add-title-box').hide();
						getInvoiceInfo()
						$('input[name=titleName_1]').val('')
						$('input[name=taxpayerIdentifyNo_1]').val('');
						$.alert('保存成功')
					}else{
						$.alert(data.message)
					}
				}
			})
		}else{
			$.alert('请按要求输入纳税人识别号');
		}
	}else{
		$.alert('请按要求输入发票抬头');
	}
}
function selectThisTitle(event){ //选择发票信息
	$('.input-invoice').click(function(event){
		$(this).addClass('hover').siblings().removeClass('hover');
		var invoiceid = $(this).attr('invoiceid');
		$('.p_input-exclusive').val(invoiceid);
		event.stopPropagation()
	})
}
function deleteInvoice(){ //删除发票抬头
	$('.delTitle').click(function(event){
		var num = $(this).attr('titleid');
		var that = $(this)
		$.confirm('你确定要删除此条信息吗? ', null, function (type) {
			if (type == 'yes') {
				this.hide();
				$.ajax({
					url:host.website+'/api/userInvoiceTitle/del',
					type:'get',
					data:{
						titleId:num
					},
					success:function(data){
						if(data.code==1){
							// getInvoiceInfo();
							$.alert('删除成功');
							that.parent('.input-invoice').remove();
						}else{
							$.alert(data.message)
						}
					}
				})
			}else{
				this.hide();
			}
		});
		event.stopPropagation()
	})

}
function EditInvoice(){ //修改发票抬头
	$('.editTitle').click(function(event){
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
$('#J_expertBtn').click(function(){ //关闭浮层
	if(!isLoadZ){
		getExpert()
	}
});
function getExpert(){
	$.ajax({
		url:host.website+'/api/userInvoiceTitle/findAll',
		type:'get',
		data:{
			userInvoiceType:1
		},
		success:function(data){
			if(data.code==1){
				isLoadZ = true;
				switch(data.data.invoiceStatus)
				{
					case 0:
						invoiceStatus = '未添加';
						$('.Z_set_btn').hide();
						break;
					case 1:
						invoiceStatus = '审核中';
						break;
					case 2:
						invoiceStatus = '已添加';
						break;
					case 3:
						invoiceStatus = '未通过';
						break;
				}
				$('.Z_ssac .invoiceStatus').html(invoiceStatus)
			}else{
				$.alert(data.message);
				isLoadZ = false;
			}
		}
	})
}
function testInvoiceInfo(titleName,taxpayerIdentifyNo,regAddress,regTel,openBank,bankAccount){ //验证专用发票
	if(regex.titleName.test(titleName)){
		$('input[name=Z_titleName]').siblings('.must-tip').hide();
		if(regex.taxpayerIdentifyNo.test(taxpayerIdentifyNo)){
			$('input[name=Z_taxpayerIdentifyNo]').siblings('.must-tip').hide();
			if(regex.regAddress.test(regAddress)){
				$('input[name=Z_regAddress]').siblings('.must-tip').hide();
				if(regex.regTel.test(regTel)){
					$('input[name=Z_regTel]').siblings('.must-tip').hide();
					if(regex.openBank.test(openBank)){
						$('input[name=Z_openBank]').siblings('.must-tip').hide();
						if(regex.bankAccount.test(bankAccount)){
							$('input[name=Z_bankAccount]').siblings('.must-tip').hide();
							return true;
						}else{
							$('input[name=Z_bankAccount]').siblings('.must-tip').show();
							return false;
						}
					}else{
						$('input[name=Z_openBank]').siblings('.must-tip').show();
						return false;
					}
				}else{
					$('input[name=Z_regTel]').siblings('.must-tip').show();
					return false;
				}
			}else{
				$('input[name=Z_regAddress]').siblings('.must-tip').show();
				return false;
			}
		}else{
			$('input[name=Z_taxpayerIdentifyNo]').siblings('.must-tip').show();
			return false;
		}
	}else{
		$('input[name=Z_titleName]').siblings('.must-tip').show();
		return false;
	}
}
$('.submit-invoice').click(function () {
	var titleName = $('input[name=Z_titleName]').val();
	var taxpayerIdentifyNo = $('input[name=Z_taxpayerIdentifyNo]').val();
	var regAddress = $('input[name=Z_regAddress]').val();
	var regTel = $('input[name=Z_regTel]').val();
	var openBank = $('input[name=Z_openBank]').val();
	var bankAccount = $('input[name=Z_bankAccount]').val();
	if($('.Z_ssac').hasClass('hover')){
		if(invoiceStatus == '未添加'){
			if(testInvoiceInfo(titleName,taxpayerIdentifyNo,regAddress,regTel,openBank,bankAccount)){
				var data = {
					userInvoiceType:1,
					titleName:titleName,
					taxpayerIdentifyNo:taxpayerIdentifyNo,
					regAddress:regAddress,
					regTel:regTel,
					openBank:openBank,
					bankAccount:bankAccount
				}
				$.ajax({
					url:host.website+'/api/userInvoiceTitle/save',
					type:'get',
					data:{
						userInvoiceTitle:JSON.stringify(data)
					},
					success:function(data){
						if(data.code==1){
							$('.invoiceInfo').hide();
							$('.invoiceAddress').show();
							$.alert('提交成功，请选择售票地址')
						}else{
							$.alert(data.message);
						}
					}
				})
			}
		}else{
			console.log('已添加');
		}
	}else{
		//普通发票
		if($('.title-boxes').html()){//判断是否有抬头信息 //true
			var titleIds = null;
			var nums = 0;
			$('.title-boxes .input-invoice').each(function(){
				if($(this).hasClass('hover')){
					nums +=1
					titleIds = $(this).attr('titleids');
				}
			})
			if(nums > 0){//判断是否选择抬头  //true     --------------------------------------待做赋值处理
				console.log(titleIds);
			}else{
				$.alert('请选择抬头信息')
			}
		}else{
			$.alert('请添加抬头信息');
		}
	}
})


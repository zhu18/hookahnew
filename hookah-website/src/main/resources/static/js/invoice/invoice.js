var subType = null;//定义发票添加或修改类型
var editTitleId = null;
var isLoadZ = false;
$('.translate-close-btn').click(function(){ //关闭浮层
	$('.translate-bg').hide();
});
$('.add-title').click(function(){//显示添加发票抬头
	subType = 'add';
	$('.add-title-box').show();
});
var regex = {
	titleName:/[\u4e00-\u9fa5]/,
	taxpayerIdentifyNo:/^[0-9A-Z]+$/
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
						html+='<div class="input-invoice" invoiceid="'+data.data[i].taxpayerIdentifyNo+'"><input type="text" readonly="readonly" value="'+data.data[i].titleName+'"><a href="javascript:void(0)" class="editTitle" titleid="'+data.data[i].titleId+'" titleName="'+data.data[i].titleName+'" taxpayerIdentifyNo="'+data.data[i].taxpayerIdentifyNo+'">编辑</a><a href="javascript:void(0);" class="delTitle" titleid="'+data.data[i].titleId+'">删除</a></div>';
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
	if(regex.titleName.test(titleName) && titleName.length < 50){
		if(regex.taxpayerIdentifyNo.test(taxpayerIdentifyNo) && taxpayerIdentifyNo.length > 14 && taxpayerIdentifyNo.length < 21){
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
			$.alert('请输入纳税人识别号');
		}
	}else{
		$.alert('请输入发票抬头');
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
	console.log(isLoadZ);
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
				var invoiceStatus = '';
				switch(data.data.invoiceStatus)
				{
					case 0:
						invoiceStatus = '未添加';
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
				$.alert(data.message)
				isLoadZ = false;
			}
		}
	})
}


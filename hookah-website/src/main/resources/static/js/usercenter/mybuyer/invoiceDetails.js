$(function(){
	var id = $.getUrlParam('id');
	if(id){
		getInvoiceDetails(id);
	}else{
		window.location.href = '/usercenter/myInvoice';
	}
});
function getInvoiceDetails(id){
	Loading.start();
	$.ajax({
		type: "get",
		url: host.website + '/api/invoice/findById',
		data:{
			invoiceId:id
		},
		success: function (data) {
			Loading.stop();
			if (data.code == 1) {
				var invoiceData = data.data;
				var html='';
				html+='<ul>';
				var html11 =null;
				if(invoiceData.invoiceOrderList.length == 1){
					html11='（独立）'
				}else{
					html11='（合并）'
				}
				html+='<li>申请开票金额：￥'+(invoiceData.invoiceAmount / 100).toFixed(2)+html11+'</li>';
				if(invoiceData.invoiceOrderList.length == 1){
					html+='<li>关联订单号：'+invoiceData.invoiceOrderList[0].orderSn+'</li>';
				}else{
					html+='<li>';
					html+='<table class="invoiceDetailsTable">';
					html+='<tr><td>关联订单号</td><td>申请金额（￥）</td></tr>';
					for(var i=0;i<invoiceData.invoiceOrderList.length;i++){
						html+='<tr><td>'+invoiceData.invoiceOrderList[i].orderSn+'</td><td>'+(invoiceData.invoiceOrderList[i].orderAmount/ 100).toFixed(2)+'</td></tr>';
					}

					html+='</table>';

					html+='</li>';
				}

				var invoiceType = null;
				if(invoiceData.invoiceType == 0){
					invoiceType = '普通发票';
				}else if(invoiceData.invoiceType == 1){
					invoiceType = '专用发票';
				}else if(invoiceData.invoiceType == 2){
					invoiceType = '个人发票';
				}
				html+='<li>发票类型：'+invoiceType+'</li>';
				html+='<li>发票介质：纸质</li>';
				if(invoiceData.invoiceType == 2){
					html+='<li>发票抬头：个人</li>';
				}else{
					html+='<li>发票抬头：'+invoiceData.userInvoiceTitle.titleName+'</li>';
				}

				html+='<li>发票内容：'+invoiceData.invoiceContent+'</li>';
				html+='<li>发票税号：'+invoiceData.taxpayerIdentifyNo+'</li>';
				if(invoiceData.invoiceType == 1){
					html+='<li>注册地址：'+invoiceData.userInvoiceTitle.regAddress+'</li>';
					html+='<li>注册电话：'+invoiceData.userInvoiceTitle.regTel+'</li>';
					html+='<li>开户银行：'+invoiceData.userInvoiceTitle.openBank+'</li>';
					html+='<li>银行账户：'+invoiceData.userInvoiceTitle.bankAccount+'</li>';
				}
				html+='<li>申请时间：'+invoiceData.addTime+'</li>';
				html+='<li>收票人姓名：'+invoiceData.userInvoiceAddress.invoiceName+'</li>';
				html+='<li>收票人手机号：'+invoiceData.userInvoiceAddress.mobile+'</li>';
				html+='<li>收票地址：'+invoiceData.userInvoiceAddress.receiveAddress+'</li>';
				html+='</ul>';
				var invoiceStatusC = null;
				switch (invoiceData.invoiceStatus) {
					case 0:
						invoiceStatusC = '未开发票';
						break;
					case 1:
						invoiceStatusC = '<span style="color:#eb9c03;">已申请</span>（待审核）';
						break;
					case 2:
						invoiceStatusC = '<span style="color:#eb9c03;">待邮寄</span>（审核通过）';
						break;
					case 3:
						invoiceStatusC = '<span style="color: #E34F4F;">未通过</span>';
						break;
					case 4:
						invoiceStatusC = '<span style="color: #0eca33;">已开票</span>';
						break;
				}
				html+='<dl>';
				html+='<dt>审核结果：'+invoiceStatusC+'</dt>';
				if(invoiceData.invoiceStatus == 3){
					html+='<dd>原因：'+invoiceData.auditOpinion+'</dd>';
				}
				html+='</dl>';
				if(invoiceData.invoiceStatus == 4){
					html+='<dl>';
					html+='<dt>邮寄信息：</dt>';
					html+='<dd>快递公司：'+invoiceData.expressInfo.expressName+'</dd>';
					html+='<dd>快递单号：'+invoiceData.expressInfo.expressNo+'</dd>';
					html+='<dd>寄出日期：'+invoiceData.expressInfo.addTime+'</dd>';
					html+='</dl>';
				}
				$('.invoiceTetails').html(html)

			} else {
				$.alert(data.message)
			}
		}
	});
}
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
				html+='<li>申请开票金额：￥'+invoiceData.invoiceAmount+'</li>';
				html+='<li>关联订单号：'+invoiceData.relationOrderSn+'</li>';
				var invoiceType = null;
				if(invoiceData.invoiceType == 0){
					invoiceType = '普通发票';
				}else{
					invoiceType = '专用发票';
				}
				html+='<li>发票类型：'+invoiceType+'</li>';
				html+='<li>发票介质：纸质</li>';
				html+='<li>发票抬头：'+invoiceData.userInvoiceTitle.titleName+'</li>';
				html+='<li>发票内容：'+invoiceData.invoiceContent+'</li>';
				html+='<li>发票税号：'+invoiceData.taxpayerIdentifyNo+'</li>';
				html+='<li>申请时间：'+invoiceData.addTime+'</li>';
				html+='<li>收票人姓名：'+invoiceData.userInvoiceAddress.invoiceName+'</li>';
				html+='<li>收票人手机号：'+invoiceData.userInvoiceAddress.mobile+'</li>';
				html+='<li>收票地址：'+invoiceData.userInvoiceAddress.receiveAddress+'</li>';
				html+='</ul>';
				// if(invoiceData.invoiceStatus == 4){
					html+='<dl>';
					html+='<dt>邮寄信息：</dt>';
					html+='<dd>快递公司：'+invoiceData.expressInfo.expressName+'</dd>';
					html+='<dd>快递单号：'+invoiceData.expressInfo.expressNo+'</dd>';
					html+='<dd>寄出日期：'+invoiceData.expressInfo.addTime+'</dd>';
					html+='</dl>';
				// }
				$('.invoiceTetails').html(html)

			} else {
				$.alert(data.message)
			}
		}
	});
}
var orderIds = null;
var editType = 'add';
var invoiceId = null;
function loadPageData(data) {
	$("#J_allOrder").html(data.data.totalCount);
	$("#payCount").html(data.data.paidCount);
	$("#noPayCount").html(data.data.unpaidCount);
	$("#J_cancelOrder").html(data.data.deletedCount);
	if (data.data.list.length > 0) {
		var list = data.data.list;
		var html = '';
		for (var i = 0; i < list.length; i++) {
			html += '<table>';
			html += '<thead>';
			html += '<tr>';
			html += '<th></th>';
			html += '<th class="" style="width: 280px;">' + '订单号:' + list[i].orderSn + '</th>';
			html += '<th class="text-align-left"  colspan=2 style="position: relative;">创建时间:' + list[i].addTime + '</th>';
			html += '<th colspan="2" style="width:190px;">总额:￥' + (list[i].orderAmount / 100).toFixed(2) + '</th>';
			html += '</tr>';
			html += '</thead>';
			html += '<tbody>';
			var goods = list[i].mgOrderGoodsList;
			for (var ii = 0; ii < goods.length; ii++) {
				var mMat = null;
				switch (goods[ii].goodsFormat) {
					case(0):
						mMat = '次';
						break;
					case(1):
						mMat = '天';
						break;
					case(2):
						mMat = '年';
						break;
					case(3):
						mMat = '套';
						break;
				}
				var catidS = (goods[ii].catId).substring(0, 3);
				var isOfflineInfo = '';
				var goodsTypeInfo = '';
				if (goods[ii].goodsType == 0) {
					goodsTypeInfo = "数据源-离线数据"
				} else if (goods[ii].goodsType == 1) {
					goodsTypeInfo = "数据源-API"
				} else if (goods[ii].goodsType == 2) {
					goodsTypeInfo = "模型"
				} else if (goods[ii].goodsType == 4) {
					goodsTypeInfo = "分析工具-独立部署软件"
				} else if (goods[ii].goodsType == 5) {
					goodsTypeInfo = "分析工具-SaaS"
				} else if (goods[ii].goodsType == 6) {
					goodsTypeInfo = "应用场景-独立部署软件"
				} else if (goods[ii].goodsType == 7) {
					goodsTypeInfo = "应用场景-SaaS"
				}
				if (goods[ii].isOffline == 1) {
					isOfflineInfo = "交付方式：线下"
				} else if (goods[ii].isOffline == 0) {
					isOfflineInfo = "交付方式：线上"
				}
				html += '<tr class="content border-bottom">';
				if (list[i].invoiceStatus == 0) {
					html += '<td><input type="checkbox" name="selectInvoice" value="' + list[i].orderId + '"></td>';
				} else {
					html += '<td></td>';
				}

				html += '<td class="text-align-center" style="width: 280px;">';
				html += '<div class="p-img">';
				html += '<a href="/exchange/details?id=' + goods[ii].goodsId + '" target="_blank">';
				html += '<img src="' + goods[ii].goodsImg + '" alt="">';
				html += '</a>';
				html += '</div>';
				html += '<div class="desc margin-top-10 marign-bottom-10" >';
				html += '<a href="/exchange/details?id=' + goods[ii].goodsId + '" target="_blank">' + goods[ii].goodsName + '</a>';
				html += '</div>';
				html += '</td>';
				if (goods[ii].isDiscussPrice == 1) {
					html += '<td class="text-align-left">x' + goods[ii].goodsNumber + '<br/>' + '面议参考价:' + (goods[ii].goodsPrice / 100).toFixed(2) + '元<br />' + goodsTypeInfo + '<br />' + isOfflineInfo + '</td>';
				} else {
					html += '<td class="text-align-left">x' + goods[ii].goodsNumber + '<br/>' + '规格:' + (goods[ii].goodsPrice / 100).toFixed(2) + '/' + mMat + '<br />' + goodsTypeInfo + '<br />' + isOfflineInfo + '</td>';
				}
				var invoiceStatus = null;
				switch (list[i].invoiceStatus) {
					case 0:
						invoiceStatus = '未开发票';
						break;
					case 1:
						invoiceStatus = '<span style="color:#eb9c03;">已申请</span>（待审核）';
						break;
					case 2:
						invoiceStatus = '<span style="color:#eb9c03;">待邮寄</span>（审核通过）';
						break;
					case 3:
						invoiceStatus = '<span style="color: #E34F4F;">未通过</span>';
						break;
					case 4:
						invoiceStatus = '<span style="color: #0eca33;">已开票</span>';
						break;
				}
				html += '<td>' + invoiceStatus + '</td>';
				var invoiceType = null;
				switch (list[i].invoiceType) {
					case 0:
						invoiceType = '普通发票';
						break;
					case 1:
						invoiceType = '专用发票';
						break;
				}
				html += '<td style="width:190px;" class="">' + invoiceType + '</td>';//发票类型
				html += '<td rowspan="' + goods.length + '" class="border-left" style="width:190px;">';

				switch (list[i].invoiceStatus) {
					case 0:
						// invoiceStatus = '未开发票';
						html += '<a href="javascript:void(0)" class="btn invoice-page-btn J_editInvoice" invoiceid="' + list[i].invoiceId + '" orderId="' + list[i].orderId + '" type="add">开发票</a>';
						break;
					case 1:
						// invoiceStatus = '已申请（待审核）';
						html += '<a href="/usercenter/invoiceDetails?id=' + list[i].invoiceId + '" class="invoice-page-btn-d">发票详情</a>';
						break;
					case 2:
						// invoiceStatus = '待邮寄（审核通过）';
						html += '<a href="/usercenter/invoiceDetails?id=' + list[i].invoiceId + '" class="invoice-page-btn-d">发票详情</a>';
						break;
					case 3:
						// invoiceStatus = '未通过';
						html += '<a href="javascript:void(0)" class="btn invoice-page-btn J_editInvoice" invoiceid="' + list[i].invoiceId + '" orderId="' + list[i].orderId + '" type="modify">修改</a>';
						html += '<a href="/usercenter/invoiceDetails?id=' + list[i].invoiceId + '" class="invoice-page-btn-d">发票详情</a>';
						break;
					case 4:
						// invoiceStatus = '已开票';
						html += '<a href="javascript:void(0)" class="btn invoice-page-btn J_editInvoice" invoiceid="' + list[i].invoiceId + '" orderId="' + list[i].orderId + '" type="modify">换开发票</a>';
						html += '<a href="/usercenter/invoiceDetails?id=' + list[i].invoiceId + '" class="invoice-page-btn-d">发票详情</a>';
						break;
				}
				html += '</td>';
				html += '</tr>';

			}
			html += '</tbody>';
			html += '</table>';
		}
		$('.order').html(html);
		$('.J_editInvoice').click(function () {//点击添加发票按钮
			orderIds = $(this).attr('orderid');
			editType = $(this).attr('type');
			invoiceId = $(this).attr('invoiceid');
			$('.translate-bg').show();
			$('.invoiceInfo').show();
			getInvoiceInfo()
		});
	} else {
		$('.order').html('<tr class="noData"><td colspan="5">暂时无订单！</td></tr>');
	}

}

function filterInvoice(that){
	// console.log($(that).val());
	dataParm.invocieStatus = $(that).val();
	goPage(1);
}
























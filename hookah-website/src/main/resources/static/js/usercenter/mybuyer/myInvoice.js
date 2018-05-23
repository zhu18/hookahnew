var orderIds = null;
var editType = 'add';
var invoiceId = null;
var selectNum = 0;
var selectOrderId = null;
var invoicePriceAB = null;
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
				html += '<tr class="content border-bottom"">';
				if(ii == 0){
					if (list[i].invoiceStatus == 0) {
						html += '<td rowspan="' + goods.length + '" class="border-right" style="width: 50px;">';
						if((list[i].orderAmount / 100) > 0){
							html+='<input type="checkbox" name="selectInvoice" value="' + list[i].orderId + '" price="'+list[i].orderAmount+'"></td>';
						}
					} else {
						html += '<td rowspan="' + goods.length + '" class="border-right" style="width: 50px;"></td>';
					}
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
					html += '<td class="text-align-left">x' + goods[ii].goodsNumber + '<br/>' + '咨询服务参考价:' + (goods[ii].goodsPrice / 100).toFixed(2) + '元<br />' + goodsTypeInfo + '<br />' + isOfflineInfo + '</td>';
				} else {
					html += '<td class="text-align-left">x' + goods[ii].goodsNumber + '<br/>' + '规格:' + (goods[ii].goodsPrice / 100).toFixed(2) + '/' + mMat + '<br />' + goodsTypeInfo + '<br />' + isOfflineInfo + '</td>';
				}
				var invoiceStatusS = null;
				switch (list[i].invoiceStatus) {
					case 0:
						invoiceStatusS = '未开发票';
						break;
					case 1:
						invoiceStatusS = '<span style="color:#eb9c03;">已申请</span>（待审核）';
						break;
					case 2:
						invoiceStatusS = '<span style="color:#eb9c03;">待邮寄</span>（审核通过）';
						break;
					case 3:
						invoiceStatusS = '<span style="color: #E34F4F;">未通过</span>';
						break;
					case 4:
						invoiceStatusS = '<span style="color: #0eca33;">已开票</span>';
						break;
				}
				var qualificationStatus = null;
				switch (list[i].qualificationStatus) {
					case 0:
						qualificationStatus = '资质未添加';
						break;
					case 1:
						qualificationStatus = '资质审核中';
						break;
					case 2:
						qualificationStatus = '资质已添加';
						break;
					case 3:
						qualificationStatus = '资质审核未通过';
						break;
					default:
						qualificationStatus = '';
				}
				if(ii == 0){
					html += '<td rowspan="' + goods.length + '" class="border-left" style="width:120px;">' + invoiceStatusS +'<br>'+qualificationStatus+'</td>';
				}
				var invoiceType = null;
				switch (list[i].invoiceType) {
					case 0:
						invoiceType = '普通发票';
						break;
					case 1:
						invoiceType = '专用发票';
						break;
					case 2:
						invoiceType = '个人发票';
						break;
					default:
						invoiceType = '-';
				}
				if(ii == 0){
					html += '<td style="width:190px;" class="border-left" rowspan="' + goods.length + '">' + invoiceType + '</td>';//发票类型
				}
				if(ii == 0) {
					html += '<td class="border-left" style="width:190px;" rowspan="' + goods.length + '">';
					switch (list[i].invoiceStatus) {
						case 0:
							// invoiceStatus = '未开发票';
							if((list[i].orderAmount / 100) > 0) {
								html += '<a href="javascript:void(0)" class="btn invoice-page-btn J_editInvoice" invoiceid="' + list[i].invoiceId + '" orderId="' + list[i].orderId + '" type="add" price="' + (list[i].orderAmount / 100).toFixed(2) + '">开发票</a>';
							}
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
							html += '<a href="javascript:void(0)" class="btn invoice-page-btn J_editInvoice" invoiceid="' + list[i].invoiceId + '" orderId="' + list[i].orderId + '" type="modify" price="'+(list[i].orderAmount / 100).toFixed(2)+'">修改</a>';
							html += '<a href="/usercenter/invoiceDetails?id=' + list[i].invoiceId + '" class="invoice-page-btn-d">发票详情</a>';
							break;
						case 4:
							// invoiceStatus = '已开票';
							html += '<a href="javascript:void(0)" class="btn invoice-page-btn J_editInvoice" invoiceid="' + list[i].invoiceId + '" orderId="' + list[i].orderId + '" type="modify" price="'+(list[i].orderAmount / 100).toFixed(2)+'">换开发票</a>';
							html += '<a href="/usercenter/invoiceDetails?id=' + list[i].invoiceId + '" class="invoice-page-btn-d">发票详情</a>';
							break;
					}
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
			invoicePriceAB = $(this).attr('price');
			getInvoiceInfo()
		});
		$("[name=selectInvoice]:checkbox").click(function () {
			if($(this).prop('checked')){
				$(this).parent('td').siblings().children('.J_editInvoice').hide();
			}else{
				$(this).parent('td').siblings().children('.J_editInvoice').show();
			}
			totalAmountFn();
		});
	} else {
		$('.order').html('<tr class="noData"><td colspan="5">暂时无订单！</td></tr>');
	}

}

function filterInvoice(that){
	dataParm.invoiceStatus = $(that).val();
	goPage(1);
}
function totalAmountFn() {
	var totalAmount = 0.00;
	selectOrderId = [];
	selectNum = 0;
	$("[name=selectInvoice]:checkbox:checked").each(function () {
		totalAmount += Number($(this).attr('price'));
		selectNum +=1;
		selectOrderId.push($(this).val())
	});
	$('.J_selectInvoicePrice').html((totalAmount / 100).toFixed(2));
}
$('.select-invoice-btn').click(function(){
	if(selectNum > 0){
		orderIds = selectOrderId.join(',');
		editType = $(this).attr('type');
		$('.translate-bg').show();
		$('.invoiceInfo').show();
		invoicePriceAB = $('.J_selectInvoicePrice').html();
		getInvoiceInfo()
	}else{
		$.alert('请选择要开的发票')
	}
});





















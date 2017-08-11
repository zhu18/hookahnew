function loadPageData(data) {
	if (data.data.list.length > 0) {
		var list = data.data.list;
		var html = '';
		for (var i = 0; i < list.length; i++) {
			html += '<tr>';
			html += '<td class="text-center">';
			html += '<a target="_blank" href="'+host.website+'/exchange/details?id='+list[i].goodsId+'">';
			html += '<img src="' +host.static+'/'+ list[i].goodsImg + '" alt="">';
			html += '<p>' + list[i].goodsName + '</p>';
			html += '</a>';
			html += '</td>';
			html += '<td class="text-align-center">' + list[i].catName + '</td>';
			html += '<td class="text-right">' + (list[i].shopPrice / 100).toFixed(2) + '</td>';
			html += '<td class="text-center">' + list[i].onsaleStartDate + '</td>';
			// if (list[i].checkStatus == 0) {
			// 	html += '<td class="text-center">审核中</td>';
			// } else if (list[i].checkStatus == 1) {
			// 	html += '<td class="text-center">通过</td>';
			// } else if (list[i].checkStatus == 2) {
			// 	html += '<td class="text-center">不通过</td>';
			// }
			html += '<td>';
			html += '<a href="javascript:offSale(\'' + list[i].goodsId + '\');">下架</a>';
			html += '</td>';
			html += '</tr>';
		}
		$('.trade-box tbody').html(html);
	} else {
		$('.trade-box tbody').html('<tr><td colspan="10"><div class="noData">暂无数据</div></td></tr>');
	}
}
function offSale(id) {
	$.confirm('你确定要下架此商品吗? ', null, function (type) {
		if (type == 'yes') {
			this.hide();
			$.ajax({
				url: host.website + '/goods/back/status/offSale',
				type: 'post',
				data: {
					goodsId: id
				},
				success: function (data) {
					if (data.code == 1) {
						$.alert('操作成功', true, function () {
							location.reload();
						});
					} else {
						$.alert(data.message)
					}
				}
			})
		} else {
			this.hide()
		}
	})
}
$('#J_goodsNameSearch').on('focus',function () {
	$(this).siblings('.tips').hide();
});
$('#J_goodsNameSearch').on('blur',function () {
	if($(this).val()){
		$(this).siblings('.tips').hide();
	}else{
		$(this).siblings('.tips').show();
	}
})
$('#J_goodsNameSearch').hover(function () {
	if($(this).val()){
		$('.cleanBtn').show();
	}else{
		$('.cleanBtn').hide();
	}
});
$('.cleanBtn').click(function(){
	$('#J_goodsNameSearch').val('');
	delete dataParm.goodsName;
	goPage(1);
});
function change(){
	var vals = $('#J_goodsNameSearch').val();
	if (vals) {
		dataParm.goodsName = vals;
		goPage(1);
	} else {
		$('#J_goodsNameSearch').siblings('.tips').show();
	}
	return false;
}
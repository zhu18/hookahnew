function loadPageData(data) {
	if (data.data.list.length > 0) {
		var list = data.data.list;
		var html = '';
		for (var i = 0; i < list.length; i++) {
			html += '<tr>';
			html += '<td class="text-center">';
			html += '<a href="javascript:void(0)">';
			html += '<img src="' + list[i].goodsImg + '" alt="">';
			html += '<p>' +host.static+'/'+ list[i].goodsName + '</p>';
			html += '</a>';
			html += '</td>';
			html += '<td class="text-align-center">' + list[i].catName + '</td>';
			html += '<td class="text-right">' + (list[i].shopPrice / 100).toFixed(2) + '</td>';
			html += '<td class="text-center">' + list[i].addTime + '</td>';
			html += '<td class="text-center" style="width:180px; word-break: break-all">' + list[i].offReason + '</td>';
			html += '<td>';
			html += '<a href="javascript:deleteGoods(\'' + list[i].goodsId + '\');">删除</a>';
			html += '</td>';
			html += '</tr>';
		}
		$('.trade-box tbody').html(html);
	} else {
		$('.trade-box tbody').html('<tr><td colspan="10"><div class="noData">暂无数据</div></td></tr>');
	}
}
function deleteGoods(id) {
	$.confirm('你确定要删除这条消息吗? ', null, function (type) {
		if (type == 'yes') {
			this.hide();
			$.ajax({
				url: host.website + '/goods/back/status/del',
				type: 'post',
				data: {
					goodsId: id
				},
				success: function (data) {
					if (data.code == 1) {
						$.alert('删除成功', true, function () {
							location.reload();
						});
					} else {
						$.alert(data.message)
					}
				}
			})
		} else {
			this.hide();
		}
	});

}
$('#J_goodsNameSearch').on('focus',function () {
	$(this).siblings('.tips').hide();
})
$('#J_goodsNameSearch').on('blur',function () {
	if($(this).val()){
		$(this).siblings('.tips').hide();
	}else{
		$(this).siblings('.tips').show();
	}
})
$(".searchInput").mouseover(function (){
	if($('#J_goodsNameSearch').val()){
		$('.cleanBtn').show();
	}
}).mouseout(function (){
	$(".cleanBtn").hide();
})
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
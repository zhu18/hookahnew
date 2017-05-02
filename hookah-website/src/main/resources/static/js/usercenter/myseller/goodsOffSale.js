function loadPageData(data) {
	if (data.data.list.length > 0) {
		var list = data.data.list;
		var html = '';
		for (var i = 0; i < list.length; i++) {
			html += '<tr>';
			html += '<td class="text-center">';
			html += '<a href="javascript:void(0)">';
			html += '<img src="http://' + list[i].goodsImg + '" alt="">';
			html += '<p>' + list[i].goodsName + '</p>';
			html += '</a>';
			html += '</td>';
			html += '<td>' + list[i].catId + '</td>';
			html += '<td class="text-right">' + list[i].shopNumber + '</td>';
			html += '<td class="text-center">' + format(list[i].addTime) + '</td>';
			if (list[i].checkStatus == 0) {
				html += '<td class="text-center">审核中</td>';
			} else if (list[i].checkStatus == 1) {
				html += '<td class="text-center">通过</td>';
			} else if (list[i].checkStatus == 2) {
				html += '<td class="text-center">不通过</td>';
			}
			html += '<td>';
			html += '<a href="javascript:onsale(\'' + list[i].goodsId + '\');">上架</a>';
			html += '<a href="javascript:void(0)">修改</a>';
			html += '<a href="javascript:deleteGoods(\'' + list[i].goodsId + '\');">删除</a>';
			html += '</td>';
			html += '</tr>';
		}
		$('.trade-box tbody').html(html);
	} else {
		$('.trade-box').html('<div class="noData">暂无数据</div>');
	}
}
function deleteGoods(id) {
	$.confirm('你确定要删除这条消息吗? ',null,function(type){
		if(type == 'yes'){
			this.hide();
			$.ajax({
				url: host.website + '/goods/back/status/del',
				type: 'post',
				data: {
					goodsId: id
				},
				success: function (data) {
					if (data.code == 1) {
						$.alert('删除成功',true,function(){
							location.reload();
						});
					} else {
						$.alert(data.message)
					}
				}
			})
		}else{
			this.hide();
		}
	});

}
function onsale(id) {
	$.confirm('你确定要上架此商品吗? ',null,function(type){
		if(type == 'yes'){
			this.hide();
			$.ajax({
				url: host.website + '/goods/back/onsale',
				type: 'post',
				data: {
					goodsId: id
				},
				success: function (data) {
					if (data.code == 1) {
						$.alert('操作成功',true,function(){
							location.reload();
						});
					} else {
						$.alert(data.message)
					}
				}
			})
		}else{
			this.hide();
		}
	});

}
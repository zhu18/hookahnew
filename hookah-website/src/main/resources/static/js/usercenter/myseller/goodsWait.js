function loadPageData(data){
    if(data.data.list.length > 0){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            html += '<tr>';
            html += '<td class="text-center">';
            html += '<a href="javascript:void(0)">';
            html += '<img src="'+list[i].goodsImg+'" alt="">';
            html += '<p>'+list[i].goodsName+'</p>';
            html += '</a>';
            html += '</td>';
            html += '<td class="text-align-center">'+list[i].catName+'</td>';
            html += '<td class="text-right">'+ (list[i].shopPrice / 100).toFixed(2) +'</td>';
            html += '<td class="text-center">'+list[i].addTime+'</td>';
            if(list[i].checkStatus == 0){
                html += '<td class="text-center color-orange">审核中</td>';
            }else if(list[i].checkStatus == 1){
                html += '<td class="text-center">通过</td>';
            }else if(list[i].checkStatus == 2){
                html += '<td class="text-center" style="width: 160px;"><span class="color-red">不通过</span><br>（'+list[i].checkReason+'）</td>';
            }
			html += '<td class="text-right">'+list[i].onsaleStartDate+'</td>';
			html += '<td style="min-width: 100px;">';
			html += '<a style="padding: 0;margin:5px 0;" href="javascript:offSale(\'' + list[i].goodsId + '\');">取消上架</a>';
			if(list[i].checkStatus == 2){
				html += '<a style="padding: 0;margin:5px 0;" href="'+host.website+'/usercenter/goodsModify?id=' + list[i].goodsId + '">修改</a>';
			}
            html += '</td>';
            html += '</tr>';
        }
        $('.trade-box tbody').html(html);
    }else{
		$('.trade-box tbody').html('<tr><td colspan="10"><div class="noData">暂无数据</div></td></tr>');
    }
}
function offSale(id) {
	$.confirm('你确定取消此商品上架吗? ', null, function (type) {
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
})
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
	var goodsName = $('#J_goodsNameSearch').val();
	var checkStatus = $('#J_checkStatus').val();
	var isBook = $('#J_isBook').val();

	if(goodsName){
		dataParm.goodsName = goodsName;
	}else{
		delete dataParm.goodsName;
	}
	if(checkStatus != -1){
		dataParm.checkStatus = checkStatus;
	}else{
		delete dataParm.checkStatus;
	}
	if(isBook != -1){
		dataParm.isBook = isBook;
	}else{
		delete dataParm.isBook;
	}
	goPage(1);
	return false;
}
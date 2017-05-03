function loadPageData(data){
    if(data.data.list){
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
            html += '<td>'+list[i].catId+'</td>';
            html += '<td class="text-right">'+ (list[i].shopPrice / 100).toFixed(2) +'</td>';
            html += '<td class="text-center">'+format(list[i].addTime)+'</td>';
            if(list[i].checkStatus == 0){
                html += '<td class="text-center">审核中</td>';
            }else if(list[i].checkStatus == 1){
                html += '<td class="text-center">通过</td>';
            }else if(list[i].checkStatus == 2){
                html += '<td class="text-center">不通过</td>';
            }
            html += '<td>';
            html += '<a href="javascript:void(0)">下架</a>';
            html += '</td>';
            html += '</tr>';
        }
        $('.trade-box tbody').html(html);
    }else{
		$('.trade-box tbody').html('<tr><td colspan="10"><div class="noData">暂无数据</div></td></tr>');
    }
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
		dataParm.goodsName = goodsName
	}else{
		delete dataParm.goodsName;
	}
	if(checkStatus){
		dataParm.checkStatus = checkStatus
	}else{
		delete dataParm.checkStatus;
	}
	if(isBook){
		dataParm.isBook = isBook
	}else{
		delete dataParm.isBook;
	}
	goPage(1);
	return false;
}
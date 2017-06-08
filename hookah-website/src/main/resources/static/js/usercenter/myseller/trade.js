function loadPageData(data){
    if(data.data.list.length > 0){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
        	var item = list[i].mgOrderGoodsList;
			for(var j=0; j < item.length; j++){
				html += '<tr>';
				html += '<td class="text-center">';
				html += '<a href="/exchange/details?id='+item[j].goodsId+'">';
				html += '<img src="'+item[j].goodsImg+'" alt="">';
				html += '<p>'+item[j].goodsName+'</p>';
				html += '</a>';
				html += '</td>';
				html += '<td>'+list[i].orderSn+'</td>';
				html += '<td>'+list[i].shippingTime+'</td>';
				html += '<td class="text-left">'+(item[j].goodsPrice / 100).toFixed(2)+'</td>';
				html += '<td class="text-center">'+item[j].goodsNumber+'</td>';
				html += '<td class="text-left">'+((item[j].goodsPrice * item[j].goodsNumber) / 100).toFixed(2)+'</td>';
				html += '</tr>';
			}
        }
        $('.trade-box tbody').html(html);
    }else{
		$('.trade-box tbody').html('<tr><td colspan="10"><div class="noData">暂无数据</div></td></tr>');
    }
}

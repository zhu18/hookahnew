function loadPageData(data){
    if(data.data.list){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            html += '<tr>';
            html += '<td class="text-center">';
            html += '<a href="javascript:void(0)">';
            html += '<img src="http://'+list[i].goodsImg+'" alt="">';
            html += '<p>'+list[i].goodsName+'</p>';
            html += '</a>';
            html += '</td>';
            html += '<td>'+list[i].catId+'</td>';
            html += '<td class="text-right">'+list[i].shopNumber+'</td>';
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
		$('.trade-box').html('<div class="noData">暂无数据</div>');
    }
}
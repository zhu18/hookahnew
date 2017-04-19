function loadPageData(data){ //渲染页面数据
	// return alert(JSON.stringify(data))
	if(data.data.goods.length > 0){
        var list = data.data.goods;
        var html = '';
        for(var i=0; i<list.length; i++){
            var shopFormat = '';
            if(list[i].shopFormat == 0 ){
                shopFormat = '次';
            }else if(list[i].shopFormat == 1 ){
                shopFormat = '月';
            }else if(list[i].shopFormat == 2 ){
                shopFormat = '年';
            }
            html += '<li>';
            html += '<a class="item-top" href="/exchange/details?id='+list[i].goodsId+'">';
            html += '<p class="goods-img"><img src="'+list[i].goodsImg+'" alt=""/></p>';
            html += '<p class="goods-name" title="'+list[i].goodsName+'">'+list[i].goodsName+'</p>';
            html += '<p class="goods-brief" title="'+list[i].goodsBrief+'">'+list[i].goodsBrief+'</p>';
            html += '</a>';
            html += '<div class="item-down">';
            html += '<span class="grid-left goods-price"><span>'+Number(list[i].shopPrice)/100+'</span>/'+(list[i].shopNumber == 1 ? '':list[i].shopNumber)+shopFormat+'</span>';
            html += '<a class="grid-right" href="javascript:void(0)">加入购物车</a>';
            html += '</div>';
            html += '</li>';
        }
        $('.order-list ul').html(html);
    }else{
		$('.order-list ul').html('<div class="noData">暂无数据</div>');
	}

}





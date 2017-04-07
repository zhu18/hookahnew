function loadPageData(data){ //渲染页面数据
    if(data.data.list){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            html += '<li>';
            html += '<a class="item-top" href="/exchange/details?goodsId='+list[i].goodsId+'">';
            html += '<p class="goods-img"><img src="'+list[i].goodsImg+'" alt=""/></p>';
            html += '<p class="goods-name">'+list[i].goodsName+'</p>';
            html += '<p class="goods-brief">'+list[i].goodsBrief+'</p>';
            html += '</a>';
            html += '<div class="item-down">';
            html += '<span class="grid-left goods-price"><span>'+Number(list[i].shopPrice)/100+'</span>/'+list[i].shopNumber == 1 ? '':list[i].shopNumber+list[i].shopFormat+'</span>';
            html += '<a class="grid-right" href="javascript:void(0)">加入购物车</a>';
            html += '</div>';
            html += '</li>';
        }
        $('.order-list ul').html(html);
    }
}



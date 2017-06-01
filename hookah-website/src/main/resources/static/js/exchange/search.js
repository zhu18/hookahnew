function loadPageData(data){ //渲染页面数据
	if(data.data.list.length>0){
        var list = data.data.list;
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
            html += '<a class="item-top" target="_blank" href="/exchange/details?id='+list[i].goodsId+'">';
            html += '<p class="goods-img"><img src="'+list[i].goodsImg+'" alt=""/></p>';
            html += '<p class="goods-name">'+list[i].goodsName+'</p>';
            html += '<p class="goods-brief">'+list[i].goodsBrief+'</p>';
            html += '</a>';
            html += '<div class="item-down clearfix">';
            html += '<span class="grid-left goods-price">￥<span>'+Number(list[i].shopPrice)/100+'</span>/'+(list[i].shopNumber == 1 ? '':list[i].shopNumber)+shopFormat+'</span>';
            html += '<a class="grid-right goods-cart btn btn-full-red padding-5 font-size-12 margin-top-10" target="_blank" href="/exchange/details?id='+list[i].goodsId+'">立即购买</a>';
            html += '</div>';
            html += '</li>';
        }
        $('.order-list ul').html(html);
    }else {
        $('.order-list ul').html('<div class="noData">暂无数据</div>');
    }
}
$('#J_searchInput').val(names);


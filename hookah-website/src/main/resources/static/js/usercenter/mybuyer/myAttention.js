function loadPageData(data){
    if(data.data.list.length > 0){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
			html += '<li>';
			html += '<a class="item-top" href="/exchange/details?id='+list[i].goodsId+'">';
			html += '<p class="goods-img">';
			html += '<img src="http://static.hookah.app/20170414/1851337921dcd56e2c413cba36a2515248549e.png" alt="">';
			html += '</p>';
			html += '<p class="goods-name">测试商品标题--api测试商品标题--api</p>';
			html += '<p class="goods-brief">测试商品标题--api测试商品标题--api测试商品标题--api测试商品标题--api测试商品标题--api测试商品标题--api测试商品标题--api测试商品标题--api测试商品标题--api测试商品标题--api</p>';
			html += '</a>';
			html += '<div class="item-down">';
			html += '<span class="grid-left goods-price">￥<span>10</span>/次';
			html += '</span>';
			html += '<a class="grid-right" href="javascript:void(0)">加入购物车</a>';
			html += '</div>';
			html += '<div class="cancel"><a href="javascript:;">取消关注</a></div>';
			html += '</li>';
        }
        function add(m){ return m < 10 ? '0'+ m:m };
        function format(time){
            var date = new Date(time);
            var year = date.getFullYear() ;
            var month = date.getMonth()+1;
            var date1 = date.getDate() ;
            var hours = date.getHours();
            var minutes = date.getMinutes();
            var seconds = date.getSeconds();
            return year+'-'+add(month)+'-'+add(date1)+' '+add(hours)+':'+add(minutes)+':'+add(seconds);
        };
        $('.order-list ul').html(html);
    }else{
		$('.order-list').html('<div class="noData">暂无数据</div>');
    }
}
$(function(){
    var goodsBrief = $(".item-top p:nth-last-child(1)");
    $(goodsBrief).each(function(index,n){
        var text = $(this).text();
        if(text.length>=65){
            $(this).text(text.slice(0,65)+'...');
        }
    });
})





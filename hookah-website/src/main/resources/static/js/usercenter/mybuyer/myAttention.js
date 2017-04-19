
// 购物车列表
function loadPageData(data){
    if(data.data.list.length > 0){
        Loading.stop();
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            console.log("goodsName:"+list[i].goodsName);
            console.log("goodsBrief:"+list[i].goodsBrief);
            console.log("shopPrice:"+list[i].shopPrice);
            console.log("goodsImg:"+list[i].goodsImg);
            html += '<li>';
			html += '<a class="item-top" href="/exchange/details?id='+list[i].goodsId+'">';
			html += '<p class="goods-img">';
			html += '<img src="http://'+list[i].goodsImg+'" alt="">';
			html += '</p>';
			html += '<p class="goods-name">'+list[i].goodsName+'</p>';
			html += '<p class="goods-brief">'+list[i].goodsBrief+'</p>';
			html += '</a>';
			html += '<div class="item-down">';
			html += '<span class="grid-left goods-price">￥<span>'+list[i].shopPrice/100+'</span>/次';
			html += '</span>';
			html += '<a class="grid-right" href="javascript:addCart('+list[i].goodsId+','+list[i].shop_format+','+list[i]+shopNumbe+')">加入购物车</a>';
			html += '</div>';
			html += '<div class="cancel" onclick="cancelAttention('+list[i].goodsId+')"><a href="javascript:void(0)">取消关注</a></div>';
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
};

function addCart(goodsId,formatId,goodsNumber) {
    $.ajax({
        url: '/cart/add',
        type: 'post',
        data: {
            goodsId: goodsId,
            formatId: formatId,
            goodsNumber: 1
        },
        success: function (data) {
            if (data.code == "1") {
                window.location.href = "/exchange/addToCart?goodsId=" + goodsId + "&number=" + goodsNumber;
            } else {
                $.alert(data.message);
            }
        }
    });
}
function cancelAttention(id){
    $.ajax({
        url:'/goodsFavorite/del',
        type:'get',
        data:{
            id:id
        },
        success:function(data){
            if(data.code=="1"){
                loadPageData(data);
            }else{
                $.alert(data.message);
            }
        }
    })
}



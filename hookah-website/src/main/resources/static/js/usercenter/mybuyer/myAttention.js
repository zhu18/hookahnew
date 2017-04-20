
// 购物车列表
function loadPageData(data){
    if(data.data.list.length > 0){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            html += '<li>';
			html += '<a class="item-top" href="/exchange/details?id='+list[i].goodsId+'">';
			html += '<p class="goods-img">';
			html += '<img src="http://'+list[i].goodsImg+'" alt="">';
			html += '</p>';
			html += '<p class="goods-name">'+list[i].goodsName+'</p>';
			html += '<p class="goods-brief">'+list[i].goodsBrief+'</p>';
			html += '</a>';
			html += '<div class="item-down">';
			html += '<span class="grid-left goods-price">￥<span>'+Number(list[i].shopPrice/100)+'</span>/次';
			html += '</span>';
			html += '<a class="grid-right" href="javascript:addCart('+list[i].goodsId+','+list[i].shopFormat+','+list[i].shopNumber+')">加入购物车</a>';
			html += '</div>';
			html += '<div class="cancel" onclick="cancelAttention('+list[i].goodsId+')">取消关注</div>';
			html += '</li>';
        }
        $('.order-list ul').html(html);
    }else{
		$('.order-list').html('<div class="noData">暂无数据</div>');
    }
    sliceString();
};

//解决浏览器中多行文字溢出时，省略号代替的兼容性问题
function sliceString(){
    var goodsBrief = $(".item-top p:nth-last-child(1)");
    $(goodsBrief).each(function(index,n){
        var text = $(this).html();
        if(text.length>=65){
            $(this).html(text.slice(0,65)+'...');
        }
    });
}

function addCart(goodsId,formatId,goodsNumber) {
    $.ajax({
        url: '/cart/add',
        type: 'post',
        data: {
            goodsId: goodsId,
            formatId: formatId,
            goodsNumber: '1'
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
                location.reload();
            }else{
                $.alert(data.message);
            }
        }
    })
}



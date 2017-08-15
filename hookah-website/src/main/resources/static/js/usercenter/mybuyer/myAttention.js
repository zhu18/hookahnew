
// 购物车列表
function loadPageData(data){
    if(data.data.list.length > 0){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
			var mMat = null;
			switch(list[i].shopFormat){
                case(0):
                    mMat = '次';
                    break;
                case(1):
                    mMat = '天';
                    break;
                case(2):
                    mMat = '年';
                    break;
                case(3):
                    mMat = '套';
                    break;
			}
            html += '<li>';
			html += '<a target="_blank" title="'+list[i].goodsName+'" class="item-top" href="/exchange/details?id='+list[i].goodsId+'">';
			html += '<div class="goods-img">\
                 <img src="http://static.qddata.com.cn/'+list[i].goodsImg+'" alt="">\
                </div>';
            if(list[i].isDiscussPrice == 1){
                html += '<div class="goods-name">'+list[i].goodsName+'<div class="goods-price text-align-center color-red font-size-16">价格面议</div></div>';
            }else {
                var d=Transformation(list[i].shopPrice,"10000")
                html += '<div class="goods-name">'+list[i].goodsName+'<div class="goods-price">价格：<span>￥'+d+'/'+mMat+'</span></div></div>';
            }

			// html += '<p class="goods-brief">'+list[i].goodsBrief+'</p>';
			html += '</a>';
			html += '';
			if(list[i].isOnSale == 0){
				html +='<span class="color-red">该商品已下架</span>';
			}
			// html += '<a class="grid-right" href="javascript:addCart(\''+list[i].goodsId+'\','+list[i].shopFormat+','+list[i].shopNumber+');">加入购物车</a>';
			html += '<span class="cancel" onclick="cancelAttention(\''+list[i].goodsId+'\')">取消关注</span>';
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
        if(text.length>=32){
            $(this).html(text.slice(0,32)+'...');
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
            goodsNumber:1
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
        cache:false,
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



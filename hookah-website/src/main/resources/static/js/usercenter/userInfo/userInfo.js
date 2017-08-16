/**
 * Created by lss on 2017/7/17 0017.
 */
function loadPageData(data) {
    if (data.data.list.length > 0) {
        var list = data.data.list;
        var html = '';
        if(pagePath==(host.website + '/goods/back/sale/list')){//我上架的数据
            for (var i = 0; i < list.length; i++) {
                html +='<div class="order-list-item grid-left">';
                html +='<div class="order-list-top clearfix">';
                html +='<a href="/exchange/details?id=' + list[i].goodsId + '" target="_blank">';
                html +='<img class="grid-left" src="'+host.static+'/'+list[i].goodsImg+'" alt="">';
                html +='<div class="order-list-top-info grid-left">';
                html +='<h4>'+list[i].goodsName+'</h4>';
                if(list[i].isDiscussPrice==1){
                    html +='<p>价格面议</span></p>';

                }else {
                    var d=Transformation(list[i].shopPrice,"10000")
                    html +='<p>价格：<span>￥'+d+'</span></p>';
                }
                html +='</div></a></div>';
                html +='<div class="order-list-down">上架时间: <span class="buy-time">' + list[i].onsaleStartDate + '</span></div>';
                html +='</div>';
            }
            $('.my-order-list-two ').html(html);

        }else {//我购买的数据
            for (var i = 0; i < list.length; i++) {
                html +='<div class="order-list-item grid-left">';
                html +='<div class="order-list-top clearfix">';
                html +='<a href="/exchange/details?id=' + list[i].goodsId + '" target="_blank">';
                html +='<img class="grid-left" src="'+host.static+'/'+list[i].goodsImg+'" alt="">';
                html +='<div class="order-list-top-info grid-left">';
                html +='<h4>'+list[i].goodsName+'</h4>';
                if(list[i].isDiscussPrice==1){
                    html +='<p>价格面议</span></p>';

                }else {
                    var d=Transformation(list[i].goodsPrice,"10000")
                    html +='<p>价格：<span>￥'+d+'</span></p>';
                }
                html +='</div></a></div>';
                html +='<div class="order-list-down">购买时间: <span class="buy-time">' + list[i].payTime + '</span></div>';
                html +='</div>';
            }
            $('.my-order-list-one ').html(html);
        }
    } else {
        $('.my-order-list-one ').html('<tr class="noData"><td colspan="5" style="width:978px; text-align:center;line-height:100px;">暂无已购买数据！</td></tr>');
        $('.my-order-list-two ').html('<tr class="noData"><td colspan="5" style="width:978px; text-align:center;line-height:100px;">暂无上架商品！</td></tr>');
    }

}
$(".recharge").click(function () {
    window.location.href = '/usercenter/recharge';
});
$(".my-order-list-two").hide();
$(".basic-information-down .header h4").on("click",function () {
    $(this).addClass("active").siblings().removeClass("active");
    if($(this).attr('data-tab-id')=="one"){
        $(".my-order-list-one").show();
        $(".my-order-list-two").hide();
        pagePath = host.website + '/order/goodsList';
        goPage("1");
    }else {
        $(".my-order-list-one").hide();
        $(".my-order-list-two").show();
        pagePath = host.website + '/goods/back/sale/list';
        goPage("1");
    }
});

function splitK(num) {
    var decimal = String(num).split('.')[1] || '';//小数部分
    var tempArr = [];
    var revNumArr = String(num).split('.')[0].split("").reverse();//倒序
    for (i in revNumArr){
        tempArr.push(revNumArr[i]);
        if((i+1)%3 === 0 && i != revNumArr.length-1){
            tempArr.push(',');
        }
    }
    var zs = tempArr.reverse().join('');//整数部分
    return decimal?zs+'.'+decimal:zs;
}

$(".account-balance").html(splitK($(".account-balance").html()));
$(".available-balance").html(splitK($(".available-balance").html()));
$(".frozen-balance").html(splitK($(".account-balance").html()));

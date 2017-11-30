/**
 * Created by lss on 2017/7/17 0017.
 */
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
$(".frozen-balance").html(splitK($(".frozen-balance").html()));

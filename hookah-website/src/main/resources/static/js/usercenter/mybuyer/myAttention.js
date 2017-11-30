
// 购物车列表

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



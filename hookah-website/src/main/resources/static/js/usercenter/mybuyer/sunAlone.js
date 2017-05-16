/**
 * Created by ki on 2017/4/24.
 */
$(function(){
    $.fn.raty.defaults.path = '/static/images';
    var len=$('.sunContent').length;
    for (var i=1;i<=len;i++){
        $('#function-demo-'+i+'').raty({
            number: 5, //多少个星星设置
            targetType: 'hint', //类型选择，number是数字值，hint，是设置的数组值
            path: '/static/images/',
            hints: ['差', '一般', '好', '非常好', '全五星'],
            size: 24,
            starOff: 'starOff.png',
            starOn: 'starOn.png',
            target: '#function-hint-'+i+'',
            cancel: false,
            targetKeep: true,
            targetText: '请选择评分'
            // click: function(score, evt) {
            //     console.log('ID: ' + $(this).attr('id') + "\nscore: " + score + "\nevent: " + evt.type);
            // }
        });
    }



})
var goodsId=[];
$('input[name="goodsId"]').each(function () {
    goodsId.push($(this).val())
})
function check(orderId){
    $.ajax({
        type: "post",
        url: '/comment/add',
        data:{
            orderId:orderId,
            goodsId:$('input[name="goodsId"]').val(),
            commentContent:$("#area").val(),
            commentLevel:$("input[name='score']").val()
        },
        success: function(msg) {
            if (msg.code == 1) {
                $.alert('提交成功');
               window.location.href="/usercenter/buyer/orderManagement";
            } else {
                $.alert(msg.message);
            }
        }
    })
}
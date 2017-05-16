/**
 * Created by ki on 2017/4/24.
 */
$(function(){
    $.fn.raty.defaults.path = '/static/images';
    var len=$('.sunContent').length;
    for (var i=1;i<=len;i++){
        $('.item-'+i+' #function-demo').raty({
            number: 5, //多少个星星设置
            targetType: 'hint', //类型选择，number是数字值，hint，是设置的数组值
            path: '/static/images/',
            hints: ['差', '一般', '好', '非常好', '全五星'],
            size: 24,
            starOff: 'starOff.png',
            starOn: 'starOn.png',
            target: '.item-'+i+' #function-hint',
            cancel: false,
            targetKeep: true,
            targetText: '请选择评分'
            // click: function(score, evt) {
            //     console.log('ID: ' + $(this).attr('id') + "\nscore: " + score + "\nevent: " + evt.type);
            // }
        });
    }
})

function check(orderId){
    var data=[];
    var len=$('.sunContent').length;
    for (var i=1;i<=len;i++){
        data.push({
            "orderId":orderId,
            "goodsId":$('.item-' + i + ' .pic a').attr('name'),
            "commentContent":$('.item-' + i + ' #area').val(),
            "commentLevel":$('.item-' + i + ' #function-demo input').val()
        })

    }
    $.ajax({
        type: "post",
        url: '/comment/add',
        data:JSON.stringify(data),
        contentType:'application/json ',
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
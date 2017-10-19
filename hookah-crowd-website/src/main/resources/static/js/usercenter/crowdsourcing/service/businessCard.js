/**
 * Created by lss on 2017/10/18 0018.
 */

for(var  i=0;i<5;i++){
    // (function (i) {
    //     var score=5-i;
        $('.function-demo-'+i).raty({
            score: 5-i,
            number: 5, //多少个星星设置
            targetType: 'hint', //类型选择，number是数字值，hint，是设置的数组值
            path: '/static/images/',
            // hints: ['差', '一般', '好', '非常好', '全五星'],
            size: 24,
            starOff: 'star-off.png',
            starOn: 'star-on.png',
            // target: ' .function-hint',
            readOnly: true,
            cancel: false,
            targetKeep: true,
            targetText: '请选择评分'
            // click: function(score, evt) {
            //     console.log('ID: ' + $(this).attr('id') + "\nscore: " + score + "\nevent: " + evt.type);
            // }
        });
    // })(i)

}
$('.businessCard-down-header ul li').on('click',function () {
    $(this).addClass('active').siblings().removeClass('active');
    if($(this).attr('data-flag')=='tab-trade'){
        $('.tab-trade').show();
        $('.tab-comment').hide();
    }else {
        $('.tab-trade').hide();
        $('.tab-comment').show();
    }

});
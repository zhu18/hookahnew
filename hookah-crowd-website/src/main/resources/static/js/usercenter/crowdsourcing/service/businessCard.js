/**
 * Created by lss on 2017/10/18 0018.
 */
function loadPageData(data){
    console.log(222222);
    console.log(data.data.list);
    var list=data.data.list
    if(pagePath==(host.crowd + '/api/getCommentRecord')){//评价
        console.log("评价");
        var html='';
        for (var i = 0; i < list.length; i++) {
            var item=list[i];
            html +='<tr data-level="'+item.level+'">';
            html +='<td><div class="demo"><div class="function-'+i+'" ></div></div></td>';
            html +='<td>'+item.content+'</td>';
            html +='<td>'+item.addTime+'</td>';
            html +='</tr>'
        }
        $('.tab-comment-list').html(html);
        $('.tab-comment-list tr').each(function (index) {
            console.log($(this).attr("data-level"));
            $('.function-'+index).raty({
                score: $(this).attr("data-level"),
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
        })
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
}
$(function () {
    for(var  i=0;i<5;i++){
        // (function (i) {
        //     var score=5-i;

        // })(i)

    }
    $('.businessCard-down-header ul li').on('click',function () {
        $(this).addClass('active').siblings().removeClass('active');
        if($(this).attr('data-flag')=='tab-trade'){
            $('.tab-trade').show();
            $('.tab-comment').hide();
            // pagePath = host.website + '/order/goodsList';
            // goPage("1");
        }else {
            $('.tab-trade').hide();
            $('.tab-comment').show();
            pagePath = host.crowd + '/api/getCommentRecord';
            goPage("1");
        }

    });
    function reader() {
        $.ajax({
            type: 'get',
            url: "/api/auth/providerCard",
            success: function (data) {
                console.log(data);
                if(data.code==1){
                    var specialSkills=data.data.specialSkills;
                    var educationsExpList=data.data.educationsExpList;
                   $('.upname').html(data.data.upname);
                   $('.ucity').html(data.data.ucity);
                   $('.providerDesc').html(data.data.providerDesc);
                   if(data.data.providerDesc.length>68){
                      $('.providerDesc').addClass('active');
                      $('.card-content').mouseover(function () {

                          $('.providerDesc').removeClass('active');
                      })
                      $('.card-content').mouseout(function () {

                          $('.providerDesc').addClass('active');
                      })

                   }

                   if(specialSkills){
                       var html=''
                       for (var i=0,len=specialSkills.length;i<len;i++){
                           var item=specialSkills[i];
                            html +='<span>'+item+'</span>'
                       }
                       $('.tag-box').html(html)
                   }
                   if(educationsExpList){
                       for (var i=0,len=educationsExpList.length;i<len;i++){
                           var item=educationsExpList[i];
                           console.log(item);
                           $('.education .school').html(item.schoolName);

                           $('.education .major').html(item.major);
                           $('.education .edu').html(item.edu);
                       }
                   }
                   if(data.data2){
                       var html='';
                       var tatlo=''
                       for (var i=0,len=data.data2.length;i<len;i++){
                            var item =data.data2[i];

                           $('.comment-content .demo .num-'+i).html('('+item.commentNum+')');
                           // $('.comment-content .demo .bar-progress-'+i).html(item.commentNum);
                           $('.function-demo-'+i).raty({
                               score: 5-i,
                               number: 5, //多少个星星设置
                               targetType: 'hint', //类型选择，number是数字值，hint，是设置的数组值
                               path: '/static/images/',
                               // hints: ['差', '一般', '好', '非常好', '全五星'],
                               size: 26,
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
                       }
                   }
                }
            }
        });
    }
    reader()
});

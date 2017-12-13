/**
 * Created by lss on 2017/10/18 0018.
 */
function loadPageData(data){
    console.log(data.data.list);
    var list=data.data.list;
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
    }else {//交易记录
        var html='';
        var status='';
        var type='';
        for (var i = 0; i < list.length; i++) {
            var item=list[i];
            switch (item.status) {
                case(2):
                    status = '参与报名';
                    break;
                case(7):
                    status = '驳回失败';
                    break;
                case(8):
                    status = '交易成功';
                    break;
                case(9):
                    status = '违约失败';
                    break;
            }

            switch (item.type) {
                case(1):
                    type = '数据采集';
                    break;
                case(2):
                    type = '数据清洗';
                    break;
                case(3):
                    type = '数据分析';
                    break;
                case(4):
                    type = '数据模型';
                    break;
                case(5):
                    type = '数据应用';
                    break;
                case(6):
                    type = '其他类型';
                    break;
            }

            html +='<tr> '
            html +='<td>'+item.title+'</td> '
            html +='<td>'+type+'</td> '
            html +='<td>'+(item.rewardMoney/ 100).toFixed(2)+'</td> '
            html +='<td>'+item.pressTime+'</td> '
            html +='<td>'+status+'</td> '
            html +='</tr>';
        }
        $('.tab-trade-list ').html(html);
    }
}
$(function () {

    $('.businessCard-down-header ul li').on('click',function () {
        $(this).addClass('active').siblings().removeClass('active');
        if($(this).attr('data-flag')=='tab-trade'){
            $('.tab-trade').show();
            $('.tab-comment').hide();
            pagePath = host.crowd + '/api/getTradeRecord';
            goPage("1");
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

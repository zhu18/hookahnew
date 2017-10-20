/**
 * Created by Dajun on 2017-9-19.
 */


let crowdSourcingId = GetUrlValue('id');
let userType = null;


function recommendTasks() { //推荐任务
  $.ajax({
    type: 'get',
    url: "/require/recommendTasks",
    success: function (data) {
      console.log(data);
      if(data.code==1){
        initRecommendTasksDOM('.similarMissionBottomBoxRight',data.data.zbRecommendVos)
      }
    }
  })
}
function initRecommendTasksDOM(selector,data){//推荐任务渲染函数
  let initRecommendTasksDOM='';
  for(let i=0;i < data.length;i++){
    initRecommendTasksDOM+='\
   <li>\
      <div class="similarMissionDemandName">'+data[i].title+'</div>\
    <div class="similarMissionDeadData">交付截止日期：'+data[i].deliveryDeadline+'</div>\
    <div class="similarMissionLastTime">报名剩余时间：'+data[i].applyLastTime+'</div>\
    <div class="similarMissionMoney">￥'+ (data[i].rewardMoney / 100 ) +'元  </div>\
    <div class="similarMissionHasApply">已报名：'+data[i].count+'人</div>\
    <a class="applyBtn similarMissionApply" href="/crowdsourcing/demandGuide?id='+data[i].requirementId+'">我要报名</a>\
      </li>'
  }
  $(selector).html(initRecommendTasksDOM).css('width',data.length*(215+34));
}

function demandDetail() { //需求详情
  $.ajax({
    type: 'get',
    url: "/front/getRequirementDetail?reqId=" + crowdSourcingId,
    cache: false,
    success: function (data) {
      console.log(data);
      if(data.data.zbRequirementSPVo.status==6){
        console.log($('.demandStatus').addClass('bgc-a7a7a7').children('span').eq(0).html('报名结束'));
        $('.signUp').addClass('bgc-a7a7a7').attr('href','javascript:void(0)');
      }
      userType=data.data.userType;
      console.log(data.data.zbRequirementSPVo.status);
      $('.demandTitle').html(data.data.zbRequirementSPVo.title);
      $('.demandDes').html(data.data.zbRequirementSPVo.description.substring(0,45)+'...').attr({'shotStr':(data.data.zbRequirementSPVo.description.substring(0,45)+'...'),'allStr':data.data.zbRequirementSPVo.description});
      $('.j_deliveryDeadline').html(data.data.zbRequirementSPVo.deliveryDeadline);
      $('.j_applyDeadline').html(data.data.zbRequirementSPVo.applyDeadline);
      $('.rewardMoney span').html(data.data.zbRequirementSPVo.rewardMoney/100);
      $('.j_checkRemark').html(data.data.zbRequirementSPVo.checkRemark.substring(0,45)+'...').attr({'shotStr':(data.data.zbRequirementSPVo.checkRemark.substring(0,45)+'...'),'allStr':data.data.zbRequirementSPVo.checkRemark});
      $('.demandType').html(data.data.zbRequirementSPVo.typeName);

    }
  });
}

recommendTasks();//推荐任务
demandDetail();//6大类数据请求

let box=$('.similarMissionBottomBoxRight');
$('.prev').on('click',function () {
  let boxLeft=parseFloat(box.css('left'));
  let leftVal=(parseFloat($('.similarMissionBottomBoxRight').css('width'))-(215+34)*4)*-1;
  if(boxLeft>leftVal){
    box.css('left',boxLeft-=215+34)
  }
});
$('.next').on('click',function () {
  let boxLeft=parseFloat(box.css('left'));
  if(boxLeft<0){
    box.css('left',boxLeft+=215+34)
  }
});

$('.des').on('mouseenter',function () {
  $(this).html($(this).attr('allStr'));
}).on('mouseleave',function () {
  $(this).html($(this).attr('shotStr'));
});

$(document).on('click','.signUp',function () {

  if(userType !== -1){ //已经登录
    isLogin()
  }else{ //未登录
    window.location.href = host.loginUrl + encodeURIComponent(host.crowd+'/crowdsourcing/demandGuide?id='+crowdSourcingId);
  }


});

function isLogin() {  //已经登录
  $.ajax({
    url: '/api/isAuthProvider',
    type: 'post',
    success: function (data) {
      if(data){ //已经认证

        window.location.href = host.loginUrl + encodeURIComponent(host.crowd+'/usercenter/missionApply?id='+crowdSourcingId);

      }else{ //未认证
        window.location.href = host.loginUrl + encodeURIComponent(host.crowd+'/usercenter/missionApply?id='+crowdSourcingId);
        // TODO:认证还没做好，等做好时候把上面一行删了，下面的注释解开
        /*        $.confirm('<div style="padding: 15px; text-align:left;">您好！<span style="color:red">您还不是服务商</span>，不能参加需求任务报名，<br>如想报名请点击 【确定】 申请成为服务商，<br>按要求提交信息即可通过服务商认证。 </div>',null,function(type){
                  if(type == 'yes'){
                    if(userType==2){
                      window.location.href = host.loginUrl + encodeURIComponent(host.crowd+'/usercenter/pspAuthentication');
                    }else{
                      window.location.href = host.loginUrl + encodeURIComponent(host.crowd+'/usercenter/epAuthentication');
                    }
                    this.hide();
                  }else{
                    this.hide();

                  }
                })*/

      }
    }
  });
}








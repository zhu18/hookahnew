/**
 * Created by Dajun on 2017-9-19.
 */


let crowdSourcingId = GetUrlValue('id');


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
      $('.demandTitle').html(data.data.zbRequirementSPVo.title);
      $('.demandDes').html(data.data.zbRequirementSPVo.description.substring(0,45)+'...').attr({'shotStr':(data.data.zbRequirementSPVo.description.substring(0,45)+'...'),'allStr':data.data.zbRequirementSPVo.description});
      $('.j_deliveryDeadline').html(data.data.zbRequirementSPVo.deliveryDeadline);
      $('.j_applyDeadline').html(data.data.zbRequirementSPVo.applyDeadline);
      $('.rewardMoney span').html(data.data.zbRequirementSPVo.rewardMoney/100);
      $('.j_checkRemark').html(data.data.zbRequirementSPVo.checkRemark.substring(0,45)+'...').attr({'shotStr':(data.data.zbRequirementSPVo.checkRemark.substring(0,45)+'...'),'allStr':data.data.zbRequirementSPVo.checkRemark});


      switch (Number(data.data.zbRequirementSPVo.type)) {
        case 1 : {
          $('.demandType').html('数据采集');
          break;
        }
        case 2 : {
          $('.demandType').html('数据清洗');
          break;
        }
        case 3 : {
          $('.demandType').html('数据分析');
          break;
        }
        case 4 : {
          $('.demandType').html('数据模型');
          break;
        }
        case 5 : {
          $('.demandType').html('数据应用');
          break;
        }
        case 6 : {
          $('.demandType').html('其他');
          break;
        }
      }
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


























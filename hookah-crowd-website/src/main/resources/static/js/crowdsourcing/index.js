/**
 * Created by Dajun on 2017-9-19.
 */

/*
 1.数据采集--dataCollection
 2.数据清洗--dataProcess
 3.数据分析--dataCleansing
 4.数据模型--dataModel
 5.数据应用--datApplication
 6.其他--otherData

*/





function recommendTasks() { //推荐任务
  $.ajax({
    type: 'get',
    url: "/require/recommendTasks",
    success: function (data) {
      console.log(data);
      console.log('推荐任务');
      if(data.code==1){
        initRecommendTasksDOMFn('.toBeServiceBottomBoxRight',data.data.zbRecommendVos)
      }
    }
  })
}
function initRecommendTasksDOMFn(selector,data){ //推荐任务
  var  initRecommendTasksDOM='';
  var tempLength=null;
  if(data.length>4){
    tempLength=4;
  }else{
    tempLength=data.length;
  }
  for(var i=0;i < tempLength;i++){
    initRecommendTasksDOM+='\
   <li>\
      <div class="toBeServiceDemandName">'+data[i].title+'</div>\
    <div class="toBeServiceDeadData">交付截止日期：'+data[i].deliveryDeadline+'</div>\
    <div class="toBeServiceLastTime">报名剩余时间：'+data[i].applyLastTime+'</div>\
    <div class="toBeServiceMoney">￥'+ (data[i].rewardMoney / 100 ) +'元  </div>\
    <div class="toBeServiceHasApply">已报名：'+data[i].count+'人</div>\
    <a class="applyBtn toBeServiceApply" href="/crowdsourcing/demandGuide?id='+data[i].requirementId+'">我要报名</a>\
      </li>'
  }
  $(selector).html(initRecommendTasksDOM);
}


function renderDOM() { //请求数据
  $.ajax({
    type: 'get',
    url: "/require/requirementTypeInfo",
    success: function (data) {
      console.log(data);
      if(data.code==1){
        requirementTypeInfo('.j_dataCollection',data.data.dataCollection);
        requirementTypeInfo('.j_dataProcess',data.data.dataProcess);
        initOneDataRequirementTypeInfoDOM('.j_dataCleansing',data.data.dataCleansing);
        initOneDataRequirementTypeInfoDOM('.j_dataModel',data.data.dataModel);
        initOneDataRequirementTypeInfoDOM('.j_datApplication',data.data.datApplication);
        initOneDataRequirementTypeInfoDOM('.j_otherData',data.data.otherData);
      }
    }
  })
}


function initOneDataRequirementTypeInfoDOM(selector,data){
  if(data.length){
    var initOneDataRequirementTypeInfoDOM='';
    initOneDataRequirementTypeInfoDOM+='\
          <div class="toBeServiceDemandName">'+data[0].title+'</div>\
        <div class="toBeServiceDeadData">交付截止日期：'+data[0].deliveryDeadline+'</div>\
        <div class="toBeServiceLastTime">报名剩余时间：'+data[0].applyLastTime+'</div>\
        <div class="toBeServiceMoney">￥'+ (data[0].rewardMoney / 100 ) +'元  </div>\
        <div class="toBeServiceHasApply">已报名：'+data[0].count+'人</div>\
        <a class="applyBtn toBeServiceApply" href="/crowdsourcing/demandGuide?id='+data[0].id+'">我要报名</a>';
    $(selector).html(initOneDataRequirementTypeInfoDOM);
  }else{
    $(selector).prev().html('找数据<br>就上数据星河').addClass('mt90');
    $(selector).next().remove();
    $("<a href='/usercenter/crowdsourcingRelease' class='noDataButton'>立即发需求</a>").insertAfter(selector);
    $(selector).remove();
  }
}


function requirementTypeInfo(selector,data) {
  var tempLiDom='';
  for(var i=0;i<data.length;i++){

    tempLiDom+='\
    <li>\
      <div class="demandDetailShow">\
        <div class="demandDetailTitle">'+data[i].title+'</div>\
        <img class="demandTimeIco" src="/static/images/crowdsourcing/index/demandDetail-ico.png" alt="">\
        <div class="demandDeadData">交付截止日期：'+data[i].deliveryDeadline+'</div>\
        <div class="demandLastTime">报名剩余时间：'+data[i].applyLastTime+'</div>\
        <div class="demandMoney">￥'+ (data[i].rewardMoney / 100 ) +'元  </div>\
        <div class="demandHasApply">已报名：'+data[i].count+'人</div>\
        </div>\
      <div class="demandDetailHide ">\
        <div class="demandDetailCon">'+data[i].description+'</div>\
        <a class="applyBtn demandApply" href="/crowdsourcing/demandGuide?id='+data[i].id+'">我要报名</a>\
          </div>\
      </li>'
  }
  $(selector).html(tempLiDom);
}
recommendTasks();//推荐任务
renderDOM();//6大类数据请求
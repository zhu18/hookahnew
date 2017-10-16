/**
 * Created by Dajun on 2017-9-19.
 */

/*
 1.数据采集--dataCollection
 2.数据加工--dataProcess
 3.数据分析--dataCleansing
 4.数据模型--dataModel
 5.数据应用--datApplication
 6.其他--otherData

*/
function renderDOM() {
  $.ajax({
    type: 'get',
    url: "/api/require/requirementTypeInfo",
    success: function (data) {
      console.log(data);
      if(data.code==1){
        initDOM('.j_dataCollection',data.data.dataCollection);
        initDOM('.j_dataProcess',data.data.dataProcess);
        initDOM('.j_dataCleansing',data.data.dataCleansing);
        initDOM('.j_dataModel',data.data.dataModel);
        initDOM('.j_datApplication',data.data.datApplication);
        initDOM('.j_otherData',data.data.otherData);
      }
    }
  })
}
function initDOM(selector,data) {
  let tempLiDom='';
  for(let i=0;i<data.length;i++){
    tempLiDom+='\
    <li>\
      <div class="demandDetailShow">\
        <div class="demandDetailTitle">'+data[i].title+'</div>\
        <img class="demandTimeIco" src="/static/images/crowdsourcing/index/demandDetail-ico.png" alt="">\
        <div class="demandDeadData">交付截止日期：'+data[i].deliveryDeadline+'</div>\
        <div class="demandLastTime">报名剩余时间：'+data[i].applyLastTime+'</div>\
        <div class="demandMoney">￥'+data[i].rewardMoney+'元  </div>\
        <div class="demandHasApply">已报名：'+data[i].count+'人</div>\
        </div>\
      <div class="demandDetailHide ">\
        <div class="demandDetailCon">'+data[i].description+'</div>\
        <a class="applyBtn demandApply" href="'+data[i].id+'">我要报名</a>\
          </div>\
      </li>'
  }
  $(selector).html(tempLiDom);
}
renderDOM();
/**
 * Created by Dajun on 2017-9-19.
 */
function loadPageData(data) { //渲染页面数据
  data = data.data.list; console.log(data)
  let demandList = '';
  for (let i = 0; i < data.length; i++) {
    let tempState = '';
    let tempType = '';
    let tempClass = '';
    let tempButtonHtml = '';
    switch (data[i].status) {
      case 5:
        tempButtonHtml = '<span class="signUp">我要报名</span>';
        tempState = '我要报名';
        tempClass = '';

        break;
      case 6:
        tempButtonHtml = '<span class="signUp timeover">报名结束</span>';
        tempState = '报名结束';
        tempClass = 'timeOver';
        break;
    }
    demandList+='<li>\
            <div class="demandDetailTitle">' + data[i].title + '</div>\
            <img class="demandTimeIco mt15" src="/static/images/crowdsourcing/index/demandDetail-ico.png" alt="">\
            <div class="demandDeadData mt6">交付截止日期：' + data[i].deliveryDeadline + '</div>\
            <div class="demandLastTime">报名剩余时间：'+data[i].remainTime+'</div>\            \
            <img class="demandTimeIco mt33" src="/static/images/crowdsourcing/crowdsourcing-list/demandDetail-ico02.png" alt="">\
            <div class="demandDeadData demandDeadDes mt15">' + data[i].checkRemark + '</div>\
            <div class="demandMoney">￥' + data[i].rewardMoney/100 + ' 元  <span class="demandHasApply">已报名：' + data[i].count + '人</span></div>\
            \
            <a class="applyBtn demandApply ' + tempState + '" href="6">' + tempState + '</a>\
          </li>\
      ';
  }

  if (!data.length) {
    demandList += '<li style="padding: 70px; width:89%;height: 20px;text-align:center;">----无数据----</li>'
  }
  $('.demandList').html(demandList)

}

$('.j_searchLi li').on('click', function () {
  $(this).addClass('active').siblings().removeClass('active').parent().attr('value', $(this).attr('value'));
  searchFn();
});
$('.searchBtn').on('click', function () {
  searchFn();
});
function searchFn() {
  dataParm.status = $('#status').attr('value');
  dataParm.type = $('#type').attr('value');
  dataParm.timeType = $('#timeType').attr('value');
  console.log(dataParm);
  goPage("1");
}

$(document).on('click','.signUp',function () {
  check()
});
function check() {
  $.ajax({
    url: '/islogin',
    type: 'post',
    success: function (data) {
      if(data){
        $.confirm('您好！您还不是服务商，不能参加需求任务报名，如想报名请点击 【确定】 申请成为服务商，按要求提交信息即可通过服务商认证。 ',null,function(type){
          if(type == 'yes'){
            this.hide();
          }else{
            this.hide();

          }
        });
      }else{
        window.location.href = host.loginUrl + encodeURIComponent(host.crowd+'/crowdsourcing-list');
      }
    }
  });
}
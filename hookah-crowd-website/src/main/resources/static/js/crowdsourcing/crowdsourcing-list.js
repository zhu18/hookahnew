/**
 * Created by Dajun on 2017-9-19.
 */
var reqTypeId = GetUrlValue('typeId');
$('#type li').removeClass('active').eq(reqTypeId).addClass('active');
var userType=null;
function loadPageData(data) { //渲染页面数据
  userType=data.data.userType;
  data = data.data.list;
  console.log(data);
  var demandList = '';
  for (var i = 0; i < data.length; i++) {
    var tempState = '';
    var tempType = '';
    var tempClass = '';
    var tempButtonHtml = '';
    var aHref = '';
    switch (data[i].status) {
      case 5:
        tempButtonHtml = '<span class="signUp">我要报名</span>';
        tempState = '我要报名';
        tempClass = '';
        aHref='/crowdsourcing/demandGuide?id='+ data[i].id;
        break;
      case 6:
        tempButtonHtml = '<span class="signUp timeOver">报名结束</span>';
        tempState = '报名结束';
        tempClass = 'timeOver';
        aHref = 'javascript:void(0)';
        break;
    }
    demandList+='<li>\
            <div class="demandDetailTitle">' + data[i].title + '</div>\
            <img class="demandTimeIco mt15" src="/static/images/crowdsourcing/index/demandDetail-ico.png" alt="">\
            <div class="demandDeadData mt6">交付截止日期：' + data[i].deliveryDeadline + '</div>\
            <div class="demandLastTime">报名剩余时间：'+data[i].remainTime+'</div>\            \
            <img class="demandTimeIco mt33" src="/static/images/crowdsourcing/crowdsourcing-list/demandDetail-ico02.png" alt="">\
            <div class="demandDeadData demandDeadDes mt15">' + data[i].checkRemark.substring(0,60)+'...' + '</div>\
            <div class="demandMoney">￥' + data[i].rewardMoney/100 + ' 元  <span class="demandHasApply">已报名：' + data[i].count + '人</span></div>\
            \
            <a class="applyBtn demandApply ' + tempClass + '" href="'+aHref+'">' + tempState + '</a>\
          </li>';
  }

  if (!data.length) {
    demandList += '<li style="padding: 70px; width:89%;height: 20px;text-align:center;">----无数据----</li>'
  }
  $('.demandList').html(demandList);

  if(userType == 2){
    $('.bannerServerApply').attr('href',"/usercenter/pspAuthentication")
  }else if(userType == 4){
    $('.bannerServerApply').attr('href',"/usercenter/epAuthentication")
  }else{
    $('.bannerServerApply').attr('href',"/usercenter/authentication")

  }

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

$(document).on('click','.timeOver',function () {
  $.alert('报名已截止！ 您可以尝试其他任务需求。')
});
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

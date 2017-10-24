/**
 * Created by Dajun on 2017-9-19.
 */
function loadPageData(data) { //渲染页面数据
  data = data.data.list;
  console.log(data)
  var tempHtml = '\
        <thead>\
          <tr style="background-color:#fff;">\
            <th width="50">序号</th>\
            <th>需求标题</th>\
            <th>需求类型</th>\
            <th>悬赏金额</th>\
            <th>状态</th>\
            <th>发布日期</th>\
            <th>报名剩余时间</th>\
            <th>操作</th>\
          </tr>\
          </thead>\
          <tbody>';
  for (var i = 0; i < data.length; i++) {
    var tempState = '';
    var tempEdit = '';
    if(data[i].isApplyDeadline == 1){ //报名未结束
      tempState = '报名中';
      switch (data[i].operStatus) {
        case 0:
          tempEdit = '<a href="/usercenter/missionApply?id=' + data[i].id + '" class="signUp ">我要报名</a>';
          break;
        case 1:
          tempEdit = '<a href="/usercenter/missionApply?id=' + data[i].id + '" class="signUp">已报名</a>';
          break;
      }
    }else{ //报名结束
      tempState = '报名结束';
      switch (data[i].operStatus) {
        case 0:
          tempEdit = '<a href="javascript:void(0)" class="signUp timeover">我要报名</a>';
          break;
        case 1:
          tempEdit = '<a href="/usercenter/missionApply?id=' + data[i].id + '" class="signUp">已报名</a>';
          break;
      }

    }

    tempHtml += '<tr>\
          <td>' + (i + 1) + '</td>\
          <td><div class="titleLineHeight">' + data[i].title + '</div></td>\
          <td>' + data[i].typeName + '</td>\
          <td>' + data[i].rewardMoney / 100 + '</td>\
          <td>' + tempState + '</td>\
          <td>' + data[i].pressTime + '</td>\
          <td>' + data[i].remainTime + '</td>\
          <td>' + tempEdit + '</td>\
          </tr>'
  }
  if (!data.length) {
    tempHtml += '<tr><td style="padding: 70px;" colspan="8">----无数据----</td></tr>'
  }
  tempHtml += '</tbody></table>';
  $('.crowdsourcing-table').html(tempHtml)

}


$('#type li').on('click', function () { //切换状态
  $(this).addClass('active').siblings().removeClass('active');
  $('#typeInput').val($(this).attr('value'));
  searchFn();
});
$('#time li').on('click', function () { //切换状态
  $(this).addClass('active').siblings().removeClass('active');
  $('#timeInput').val($(this).attr('value'));
  searchFn();
});
$('.searchBtn').on('click', function () { //搜索我的发布
  searchFn();
});


dataParm.type = '';
dataParm.timeType = '';
dataParm.requireTitle = '';

function searchFn() {
  dataParm.type = $('#typeInput').val();
  dataParm.timeType = $('#timeInput').val();
  dataParm.requireTitle = $('#requireTitle').val();
  goPage("1");
}
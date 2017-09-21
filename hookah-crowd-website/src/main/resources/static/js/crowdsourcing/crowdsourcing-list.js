/**
 * Created by Dajun on 2017-9-19.
 */
function loadPageData(data) { //渲染页面数据
  console.log(data)
  data = data.data.list;
  var tempHtml = '\
        <thead>\
          <tr>\
          <th>需求标题</th>\
          <th>需求类型</th>\
          <th>悬赏金额</th>\
          <th>状态</th>\
          <th>发布时间</th>\
          <th>交付截止时间</th>\
          <th>报名剩余名额</th>\
          </tr>\
          </thead>\
          <tbody>';
  for (var i = 0; i < data.length; i++) {
    let tempState = '';
    let tempType = '';
    let tempButtonHtml = '';
    switch (data[i].status) {
      case 5:
        tempState = '报名中';
        tempButtonHtml = '<span class="signUp">报名中</span>';
        break;
      default:
        tempState = '报名结束';
        tempButtonHtml = '<span class="signUp timeover">报名结束</span>'
    }
    switch (data[i].type) {
      case 1:
        tempType = '数据采集';
        break;
      case 2:
        tempType = '数据模型';
        break;
      case 3:
        tempType = '数据应用';
        break;
      case 4:
        tempType = '数据清洗';
        break;
      case 5:
        tempType = '其他';
        break;

    }
    tempHtml += '<tr>\
      <td><a href="javascript:void(0)">' + data[i].title + '</a></td>\
      <td>' + tempType + '</td>\
      <td>' + data[i].rewardMoney + '</td>\
      <td>' + tempState + '</td>\
      <td>' + data[i].addTime + '</td>\
      <td>' + data[i].applyDeadline + '</td>\
      <td><span class="lastTime">'+data[i].remainTime+'</span>' + tempButtonHtml + '</td>\
      </tr>'
  }
  if (!data.length) {
    tempHtml += '<tr><td style="padding: 70px;" colspan="8">----无数据----</td></tr>'
  }
  tempHtml += '</tbody></table>';
  $('.crowdsourcing-table').html(tempHtml)

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
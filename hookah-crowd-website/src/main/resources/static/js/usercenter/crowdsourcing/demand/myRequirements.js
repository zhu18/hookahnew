/**
 * Created by Dajun on 2017-9-19.
 */

function loadPageData(data) { //渲染页面数据
  noRealName();//是否实名认证，未实名认证跳转
  data = data.data.list;
  var tempHtml = '\
        <thead>\
          <tr style="background-color:#fff;">\
          <th style="width:50px;">序号</th>\
          <th>需求编号</th>\
          <th>需求标题</th>\
          <th>悬赏金额</th>\
          <th>状态</th>\
          <th>发布时间</th>\
          <th>交付截止时间</th>\
          <th style="width:140px;"  >操作</th>\
          </tr>\
          </thead>\
          <tbody>';
  for (var i = 0; i < data.length; i++) {
    var tempState = '';
    var tempEdit = '';
    switch (data[i].status) {
      case 1:
        tempState = '待审核';
        tempEdit = '<a href="/usercenter/requirementDetail?id=' + data[i].id + '" class="signUp">查看</a>';
        break;
      case 2:
        tempState = '审核未通过';
        tempEdit = '<a href="/usercenter/crowdsourcingRelease?id=' + data[i].id + '" class="signUp">修改</a><span class="signUp j_del" id="' + data[i].id + '">取消</span>';

        break;
      case 3:
        tempState = '待托管赏金';
        tempEdit = '<a href="/usercenter/requirementDetail?id=' + data[i].id + '" class="signUp">托管资金</a>';
        break;
      case 5:
      case 6:
        tempState = '已发布';
        tempEdit = '<a href="/usercenter/requirementDetail?id=' + data[i].id + '" class="signUp">查看</a>';
        break;
      case 7:
        tempState = '待二次托管';
        tempEdit = '<a href="/usercenter/requirementDetail?id=' + data[i].id + '" class="signUp">托管资金</a>';
        break;
      case 8:
      case 12:
        tempState = '工作中';
        tempEdit = '<a href="/usercenter/requirementDetail?id=' + data[i].id + '" class="signUp">查看</a>';
        break;
      case 10:
        tempState = '待验收付款';
        tempEdit = '<a href="/usercenter/requirementDetail?id=' + data[i].id + '" class="signUp">验收</a>';
        break;
      case 13:
        tempState = '待评价';
        tempEdit = '<a href="/usercenter/requirementDetail?id=' + data[i].id + '" class="signUp">去评价</a>';
        break;
      case 14:
        tempState = '交易取消';
        tempEdit = '<a href="/usercenter/requirementDetail?id=' + data[i].id + '" class="signUp">查看</a>';
        break;
      case 15:
        tempState = '交易完成';
        tempEdit = '<a href="/usercenter/requirementDetail?id=' + data[i].id + '" class="signUp">查看</a>';
        break;
      case 16:
        tempState = '待退款';
        tempEdit = '<a href="/usercenter/requirementDetail?id=' + data[i].id + '" class="signUp">查看</a>';
        break;
      case 19:
        tempState = '交易取消';
        tempEdit = '<a href="/usercenter/requirementDetail?id=' + data[i].id + '" class="signUp">查看</a>';
        break;


    }
    tempHtml += '<tr>\
          <td>' + (i + 1) + '</td>\
          <td>' + data[i].requireSn + '</td>\
          <td>' + data[i].title + '</td>\
          <td>' + data[i].rewardMoney / 100 + '</td>\
          <td>' + tempState + '</td>\
          <td>' + data[i].addTime + '</td>\
          <td>' + data[i].deliveryDeadline + '</td>\
          <td>' + tempEdit + '</td>\
          </tr>'
  }
  if (!data.length) {
    tempHtml += '<tr><td style="padding: 70px;" colspan="8">----无数据----</td></tr>'
  }
  tempHtml += '</tbody></table>';
  $('.crowdsourcing-table').html(tempHtml)

}
$(document).on('click', '.j_del', function () { //搜索我的发布
  var tempId=$(this)[0].id;
  // searchFn();
  $.confirm('确定删除？', null, function (type) {
    if (type == 'yes') {
      $.ajax({
        url: '/api/release/deleteRequirement?id='+tempId,
        type: 'get',
        success: function (data) {
          console.log(data)
          if (data.data) {
            console.log('删除成功！');
            searchFn();

          } else {
            $.alert('删除失败!')
          }
        }
      });
      this.hide();
    } else {
      this.hide();

    }
  });
});

$('#status li').on('click', function () { //切换状态
  $(this).addClass('active').siblings().removeClass('active');
  $('#statusInput').val($(this).attr('value'));
  searchFn();
});
$('.searchBtn').on('click', function () { //搜索我的发布
  searchFn();
});
function searchFn() {
  dataParm.status = $('#statusInput').val();
  dataParm.title = $('#title').val();
  dataParm.requireSn = $('#requireSn').val();
  goPage("1");
}
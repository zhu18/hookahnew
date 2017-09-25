/**
 * Created by Dajun on 2017-9-19.
 */
function loadPageData(data) { //渲染页面数据
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
  for (let i = 0; i < data.length; i++) {
    let tempState='';
    let tempEdit='';
    switch(data[i].status)
    {
      case 1:
        tempState='待审核';
        tempEdit='<span class="signUp">修改</span><span class="signUp">删除</span>';
        break;
      case 2:
        tempState='审核未通过';
        tempEdit='<span class="signUp">修改</span><span class="signUp">删除</span>';

        break;
      case 3:
        tempState='待托管赏金';
        tempEdit='<span class="signUp">托管资金</span>';
        break;
      case 7:
        tempState='待二次托管';
        tempEdit='<span class="signUp">托管资金</span>';
        break;
      case 8:
        tempState='工作中';
        tempEdit='<span class="signUp">查看</span>';
        break;
      case 10:
        tempState='待验收付款';
        tempEdit='<span class="signUp">验收</span>';
        break;
      case 13:
        tempState='待评价';
        tempEdit='<span class="signUp">去评价</span>';
        break;
      case 14:
        tempState='交易取消';
        tempEdit='<span class="signUp">查看</span>';
        break;
      case 15:
        tempState='交易完成';
        tempEdit='<span class="signUp">查看</span>';
        break;
      case 16:
        tempState='待退款';
        tempEdit='<span class="signUp">查看</span>';
        break;

    }
    tempHtml += '<tr>\
          <td>'+(i+1)+'</td>\
          <td>'+data[i].requireSn+'</td>\
          <td>'+data[i].title+'</td>\
          <td>'+data[i].rewardMoney/100+'</td>\
          <td>'+tempState+'</td>\
          <td>'+data[i].addTime+'</td>\
          <td>'+data[i].deliveryDeadline+'</td>\
          <td>'+tempEdit+'</td>\
          </tr>'
  }
  if(!data.length){
    tempHtml += '<tr><td style="padding: 70px;" colspan="8">----无数据----</td></tr>'  }
  tempHtml += '</tbody></table>';
  $('.crowdsourcing-table').html(tempHtml)

}

$('#status li').on('click', function () {
  $(this).addClass('active').siblings().removeClass('active');
  $('#statusInput').val($(this).attr('value'));
  searchFn();
});
$('.searchBtn').on('click', function () {
  searchFn();
});
function searchFn(){
  dataParm.status = $('#statusInput').val();
  dataParm.title = $('#title').val();
  dataParm.requireSn = $('#requireSn').val();
  goPage("1");
}
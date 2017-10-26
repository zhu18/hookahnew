/**
 * Created by Dajun on 2017-9-19.
 */


let crowdSourcingId = GetUrlValue('id');
let rewardMoney = null;

// 获取需求类型
function getRequirementType() {
  $.ajax({
    type: 'get',
    url: "/zbType/requirementsType",
    dataType: 'json',
    contentType: 'application/json',
    success: function (data) {
      let list = data.data;
      console.log(data);
      let tempHtml = '';
      for (let i = 0; i < list.length; i++) {
        tempHtml += '<span class="type-span" value="' + list[i].id + '">' + list[i].typeName + '</span>'
      }
      $('.requirement-type').html(tempHtml);
      showDetail()
    }
  });
}
getRequirementType();


function showDetail() { //修改，从我的发布点击'查看'调转过来的
  $.ajax({
    type: 'get',
    url: "/api/release/releaseStatus?id=" + crowdSourcingId,
    cache: false,
    success: function (data) {
      console.log(data);
      renderPage(data.data);
    }
  });
}




function renderPage(data) {
  let insertRequirementsData = data;
  $('.j_title').html(insertRequirementsData.zbRequirement.title);
  $('.j_username').html(insertRequirementsData.zbRequirement.contactName);
  $('.j_phone').html(insertRequirementsData.zbRequirement.contactPhone);
  $('.j_description').html(insertRequirementsData.zbRequirement.description);
  $('.moneyHow').html(insertRequirementsData.zbRequirement.trusteePercent);
  $('.j_date').html(insertRequirementsData.zbRequirement.deliveryDeadline);
  $('.j_money').html('￥ <i>' + insertRequirementsData.zbRequirement.rewardMoney / 100 + '</i> 元');
  let temTagHtml = '';
  let temTagArr = insertRequirementsData.zbRequirement.tag.split(',');
  if (temTagArr[0]) {
    for (let t = 0; t < temTagArr.length; t++) {
      temTagHtml += '<i class="type-span">' + temTagArr[t] + '</i>'
    }
  }
  $('.j_tag').html(temTagHtml);
  $('.j_checkRemark').html(insertRequirementsData.zbRequirement.checkRemark);
  let tempTypeHtml = '';
  switch (Number(insertRequirementsData.zbRequirement.type)) { //需求标签
    case 1 : {
      $('.requirement-type-active span').html('数据采集');
      break;
    }
    case 2 : {
      $('.requirement-type-active span').html('数据清洗');
      break;
    }
    case 3 : {
      $('.requirement-type-active span').html('数据分析');
      break;
    }
    case 4 : {
      $('.requirement-type-active span').html('数据模型');
      break;
    }
    case 5 : {
      $('.requirement-type-active span').html('数据应用');
      break;
    }
    case 6 : {
      $('.requirement-type-active span').html('其他');
      break;
    }
  }

  let loadfileHtml=renderLoadFile(data.zbRequirementFiles);
  $('.j_load-file-list').append(loadfileHtml);


  let domModel = $('.crowdsourcing-status span');
  switch (insertRequirementsData.zbRequirement.status) {
    case 1:
      domModel.html('待审核');
      break;
    /*case 2:
     domModel.html('审核未通过');
     break;*/
    case 3:

      domModel.html('审核通过<br>待托管赏金');
      $('.detailMoneyBox').show();
      $('.release-first-btnbox div').append('<a id="J_goPay" requirementId="' + insertRequirementsData.zbRequirement.id + '" href="javascript:void(0)">去托管赏金</a>');
      break;
    case 7: //二次托管
      domModel.html('报名结束<br>待托管赏金');
      $('.managedMoneySpanText').html('已托管比例');
      $('.detailMoneyBox,.otherDetailBox').show();
      $('.managedMoneyNotice').hide();
      $('.moneyAdd,.moneySub').hide(); //百分比
      $('.missionResult').hide().prev().show(); //百分比
      missionApplyInfo(data); //任务报名信息显示
      $('.release-first-btnbox div').append('<a id="J_goPay" requirementId="' + insertRequirementsData.zbRequirement.id + '" href="javascript:void(0)">去托管剩余'+(100-insertRequirementsData.zbRequirement.trusteePercent)+'% 赏金</a>');
      $('.moneyHow').html(insertRequirementsData.zbRequirement.trusteePercent);

      break;
    case 8: //工作中
    case 12: //二次工作中
      domModel.html('工作中');
      missionApplyInfo(data); //任务报名信息显示
      break;
    case 10:
      domModel.html('待验收');//TODO:验收要根据成果验收的三个状态显示
      missionApplyInfo(data); //任务报名信息显示

      $('.release-first-btnbox div').append('<a class="j_checkMission" href="javascript:void(0)">成果验收</a>');


      break;
    case 13:
      domModel.html('已付款<br>待评价');
      missionApplyInfo(data); //任务报名信息显示
      $('.missionStatus').show();

      $.fn.raty.defaults.path = '/static/images/crowdsourcing';//初始化星星图标位置

      if(insertRequirementsData.zbComment){
        $('.serviceCommentStar').raty({ readOnly: true, score: insertRequirementsData.zbComment.level });
        $('.serviceCommentDetail').html(insertRequirementsData.zbComment.content);
        $('.serviceCommentTime').html(insertRequirementsData.zbComment.addTime);
      }
      $('.missionStatusResult').html('验收通过且已付款，待评价！');
      $('.checkAdviceDetailBox').html(insertRequirementsData.zbProgram.checkAdvice);//验收意见
      $('.release-first-btnbox div').append('<a class="j_commentBtn" href="javascript:void(0)">评价</a>');
      break;
    case 14:
      domModel.html('交易取消');
      $('.missionStatusResult').html('方案不符合需求方要求，验收驳回！');
      $('.checkAdviceDetailBox').html(insertRequirementsData.zbProgram.checkAdvice);//验收意见
      $('.canNotSelect').show();//禁止选择
      $('.missionStatus').show();//验收结果和查看验收意见按钮
      $('.cancleStatusNotice').show();// 悬赏金额 最右侧提示
      $('.cancleStatusNoticeContent').html('需方驳回，交易取消已退款');//悬赏金额 最右侧提示内容

      missionApplyInfo(data); //任务报名信息显示

      break;
    case 15:
      domModel.html('交易成功');
      $.fn.raty.defaults.path = '/static/images/crowdsourcing';//初始化星星图标位置


      $('.myCommentStar').raty({ readOnly: true, score: insertRequirementsData.zbDemandComment.level });
      $('.myCommentDetail').html(insertRequirementsData.zbDemandComment.content);
      $('.myCommentTime').html(insertRequirementsData.zbDemandComment.addTime);

      $('.serviceCommentStar').raty({ readOnly: true, score: insertRequirementsData.zbComment.level });
      $('.serviceCommentDetail').html(insertRequirementsData.zbComment.content);
      $('.serviceCommentTime').html(insertRequirementsData.zbComment.addTime);
      $('.commentBox').show(); //显示评价模块


      missionApplyInfo(data); //任务报名信息显示


      break;
    case 16://待退款
      domModel.html('失败待退款');
      $('.missionStatus').html('方案不符合需求方要求验收驳回，交易失败').show();
      $('.checkAdviceDetailBox').html(insertRequirementsData.zbProgram.checkAdvice);//验收意见
      $('.canNotSelect').show();//禁止选择
      $('.crowdsourcing-table-edit').remove();//删除下载模块
      // $('.cancleStatusNotice').show();// 悬赏金额 最右侧提示
      // $('.cancleStatusNoticeContent').html('需方驳回，交易取消已退款');//悬赏金额 最右侧提示内容

      missionApplyInfo(data); //任务报名信息显示

      break;
    case 19: //流标
      domModel.html('交易取消');
      $('.cancleStatusNotice').show().prev().hide();// 悬赏金额 最右侧提示
      $('.cancleStatusNoticeContent').html('无报名流标，交易取消已退款 。');//悬赏金额 最右侧提示内容
      $('.detailMoneyBox').show();
      break;
  }
  rewardMoney = insertRequirementsData.zbRequirement.rewardMoney;
  $('.moneyManageMoeny').html(rewardMoney * $('.moneyHow').text() / 10000);


  //时间显示
  if(data.byRequirementSn!==null){
    $('.j_addTime').html(data.byRequirementSn.addTime);
    $('.j_checkTime').html(data.byRequirementSn.checkTime);
    $('.j_trusteeTime').html(data.byRequirementSn.trusteeTime);
    $('.j_pressTime').html(data.byRequirementSn.pressTime);
    $('.j_workingTime').html(data.byRequirementSn.workingTime);
    $('.j_payTime').html(data.byRequirementSn.payTime);
    $('.j_commentTime').html(data.byRequirementSn.commentTime);  }

}

let commentData={};
$(document).on('click', '.j_commentBtn', function () { // 评价
  $.confirm('\
  <div class="checkMissionBox">\
    <h5>对服务商的评价</h5>\
    <span>请您根据本次交易，给予真实客观评价。您的评价将影响服务商的信用。</span>\
    <table>\
      <tr>\
        <th>验收结果：</th>\
        <td>\
          <div class="j_starBox"></div>\
        </td>\
      </tr>\
      <tr>\
        <th valign="top">评价内容：</th>\
        <td>\
          <textarea placeholder="写下您对服务商的评价吧，100个字以内。" id="commentContent" cols="30" rows="10" maxlength="100"></textarea>\
        </td>\
      </tr>\
    </table>\
  </div>', null, function (type) {

    if (type == 'yes') {
      let confirmThis=this;
      commentData.programId=$('.missionTitle').attr('acceptanceAdviceId');
      commentData.content=$("#commentContent").val();
      console.log(commentData);

      if(commentData.level && commentData.programId && commentData.content){
        console.log(1);
        $.ajax({
          type: 'post',
          url: "/api/release/insertEvaluation",
          cache: false,
          data:commentData,
          success: function (data) {
            console.log(data);
            if(data.code==1){
              $('.myCommentStar').raty({ readOnly: true, score: commentData.level });
              $('.myCommentDetail').html(commentData.content);
              $('.myCommentTime').html(data.data.addTime);
              $('.commentBox').show(); //显示评价模块


              confirmThis.hide();// 隐藏弹出框
              $('.j_commentBtn').remove();//删除评价按钮

            }else{
              $.alert(data.message)
            }
          }
        });


      }else{
        $.alert('请对服务商评价!')
      }

    } else {
      this.hide();

    }
  });

  $('.j_starBox').raty({
    click: function(score, evt) {
      // alert('ID: ' + this.id + "\nscore: " + score + "\nevent: " + evt);
      commentData.level=score;
      console.log(score)

    }
  });

});



$(document).on('click', '.j_checkMission', function () { // 成果验收
   let missionTitle=$('.missionTitle').html();
  $.confirm('\
  <div class="checkMissionBox">\
    <h5>需求方验收-' + missionTitle + '</h5>\
    <table>\
      <tr>\
        <th>验收结果：</th>\
        <td>\
          <label><input type="radio" name="resultStatus" value="3" checked> 通过</label>\
          <label><input type="radio" name="resultStatus" value="4"> 不通过,退回修改</label>\
          <label><input type="radio" name="resultStatus" value="5"> 方案不符合要求，驳回</label>\
        </td>\
      </tr>\
      <tr>\
        <th valign="top">验收意见：</th>\
        <td>\
          <textarea id="checkAdvice" cols="30" rows="10" maxlength="100"></textarea>\
        </td>\
      </tr>\
    </table>\
  </div>', null, function (type) {
    if (type == 'yes') {
      let confirmThis=this;
      let acceptanceAdvice={};
      acceptanceAdvice.status=$("input[name='resultStatus']:checked").val();
      acceptanceAdvice.id=$('.missionTitle').attr('acceptanceAdviceId');
      acceptanceAdvice.checkAdvice=$("#checkAdvice").val();
      console.log(acceptanceAdvice);

      if(acceptanceAdvice.status && acceptanceAdvice.id && acceptanceAdvice.checkAdvice){
        console.log(1);
        $.ajax({
          type: 'post',
          url: "/api/release/insertAcceptanceAdvice",
          cache: false,
          data:acceptanceAdvice,
          success: function (data) {
            console.log(data);
            if(data.code==1){
              if(acceptanceAdvice.status == 3){
                $('.missionStatusResult').html('验收通过，待付款！');
              }else if(acceptanceAdvice.status == 4){
                $('.missionStatusResult').html('验收不通过，待修改！');
              }else if(acceptanceAdvice.status == 5){
                $('.missionStatusResult').html('方案不符合需求方要求，验收驳回！');
                $('.canNotSelect').show();
                $('.j_missionResult-load-file-list .crowdsourcing-table-edit').remove();
              }
              $('.checkAdviceDetailBox').html(acceptanceAdvice.checkAdvice);//验收意见
              $('.missionStatus').show();

              confirmThis.hide();
              $('.j_checkMission').remove();

            }
          }
        });


      }else{
        $.alert('请填写验收意见!')
      }

    } else {
      this.hide();

    }
  });
});



function missionApplyInfo(data) { //任务报名信息显示
  $('.detailMoneyBox,.otherDetailBox,.applyDeadlineBox').show();//显示下方tab
  $('.managedMoneyNotice,.moneyAdd,.moneySub').hide();//隐藏托管30%提示 隐藏调整托管比例
  $('.otherDetailBoxNav li').removeClass('active').eq(1).addClass('active').parent().next().children().removeClass('active').eq(1).addClass('active');//选中第二个tab 显示

  //任务报名内容
  $('.j_peopleCount').html(data.count);
  if(data.user!==undefined){
    $('.j_companyName').html(data.user.orgName);
    $('.j_contentName').html(data.user.contactName);
    $('.j_contentPhone').html(data.user.contactPhone);
  }
  $('.j_SignUpTime').html(data.applyTime);
  //任务成果内容
  $('.j_applyDeadline').html(data.zbRequirement.applyDeadline);
  if(data.zbProgram!==undefined) {
    $('.missionTitle').html(data.zbProgram.title).attr('acceptanceAdviceId',data.zbProgram.id);
    $('.missionResultDes').html(data.zbProgram.content);
  }
  //方案附件列表
  if(data.programFiles!==undefined){
    let missionResultLoadfileHtml=renderLoadFile(data.programFiles);
    $('.j_missionResult-load-file-list').append(missionResultLoadfileHtml);
  }
}
$('.j_checkAdviceDetail').on('mouseover', function () { //鼠标离开描述显示工具栏
  $('.checkAdviceDetailBox').show();
}).on('mouseout', function () { //鼠标离开描述显示工具栏
  $('.checkAdviceDetailBox').hide();
});




$(document).on('click', '.moneyAdd', function () { //托管资金点击增加 托管金额百分比
  let percentage = Number($('.moneyHow').html());
  if (30 <= percentage && percentage < 100) {
    percentage += 1;
    $('.moneyHow').html(Number(percentage));
    $('.moneyManageMoeny').html(rewardMoney * percentage / 10000);

  }
});

$(document).on('click', '.moneySub', function () { //托管资金点击增加 托管金额百分比
  let percentage = Number($('.moneyHow').html());
  if (30 < percentage && percentage < 100) {
    percentage -= 1;
    $('.moneyHow').html(Number(percentage));
    $('.moneyManageMoeny').html(rewardMoney * percentage / 10000);

  }
});


$(document).on('mouseenter', '.load-file', function () { //鼠标滑过描述显示工具栏
  $(this).children().find('.crowdsourcing-table-edit').css({'display': 'block'}).stop().animate({
    'opacity': 1,
    'top': 0
  }, 300);
});

$(document).on('click', '.otherDetailBoxNav li', function () { //需求详情下面的tab
    $(this).addClass('active').siblings().removeClass('active').parent().next().children().removeClass('active').eq($(this).index()).addClass('active');
});

$(document).on('click', '#J_goPay', function () { //
    location.href= "/api/release/managedMoney?requirementId=" + $(this).attr('requirementId') + "&trusteePercent=" + $("#trusteePercent1").html();

});


$(document).on('mouseleave', '.load-file', function () { //鼠标离开描述显示工具栏
  $(this).children().find('.crowdsourcing-table-edit').stop().animate({'opacity': 0}, 300, function () {
    $(this).css({'display': 'none', 'top': '5px'})
  });
});
$('.tagNotice').on('mouseover', function () { //鼠标离开描述显示工具栏
  $(this).next().show();
}).on('mouseout', function () { //鼠标离开描述显示工具栏
  $(this).next().hide();
});


function renderLoadFile(loadFileList) { //渲染附件列表
  let tempHtml = '';
  for (let c = 0; c < loadFileList.length; c++) { //渲染附件

    let className = fileTypeClassName(loadFileList[c].filePath);
    tempHtml += '\
        <dl fileName="' + loadFileList[c].fileName + '" filePath="' + loadFileList[c].filePath + '" class="load-file ' + className + '">\
          <dt><a href="javascript:void(0)" title=""><img src="' + loadFileList[c].filePath + '"></a></dt>\
          <dd>\
          <span class="overflowpoint">' + loadFileList[c].fileName + '</span>\
          <div class="crowdsourcing-table-edit">\
          <a href="' + loadFileList[c].filePath + '" target="_blank" class="download"><img src="/static/images/crowdsourcing/download.png" alt=""></a>\
          </div>\
          </dd>\
        </dl>';
  }
  return tempHtml;
}

function fileTypeClassName(fileName) { //返回class
  let fileTypeReg = /[^.]*$/;
  let fileType = fileTypeReg.exec(fileName)[0];

  let fileTypeObj = {
    image: {
      'gif': 'gif',
      'jpg': 'jpg',
      'jpeg': 'jpeg',
      'png': 'png',
      'bmp': 'bmp'
    },
    file: {
      'doc': 'doc',
      'docx': 'docx',
      'xls': 'xls',
      'xlsx': 'xlsx',
      'ppt': 'ppt',
      'pptx': 'pptx',
      'htm': 'htm',
      'txt': 'txt',
      'zip': 'zip',
      'rar': 'rar',
      'gz': 'gz',
      'bz2': 'bz2'

    }
  };
  let attachmentListClassName = '';
  switch (fileType) {
    case fileTypeObj.file.doc:
    case fileTypeObj.file.docx:
      //console.log('上传的是word文档');
      attachmentListClassName = 'word';
      break;
    case fileTypeObj.file.xls:
    case fileTypeObj.file.xlsx:
      //console.log('上传的是excel文档');
      attachmentListClassName = 'excel';
      break;
    case fileTypeObj.file.ppt:
    case fileTypeObj.file.pptx:
      //console.log('上传的是ppt文档');
      attachmentListClassName = 'ppt';
      break;
    case fileTypeObj.file.zip:
    case fileTypeObj.file.rar:
    case fileTypeObj.file.gz:
    case fileTypeObj.file.bz2:
      //console.log('上传的是压缩包');
      attachmentListClassName = 'zip';

      break;
    case fileTypeObj.file.txt:
      //console.log('上传的是text文档');
      attachmentListClassName = 'text';
      break;
    case fileTypeObj.image.gif:
    case fileTypeObj.image.jpeg:
    case fileTypeObj.image.jpg:
    case fileTypeObj.image.png:
    case fileTypeObj.image.bmp:
      //console.log('上传的是图片');
      attachmentListClassName = 'img';
      break;
    default:
      attachmentListClassName = 'others';
    //console.log('未知类型')
  }
  return attachmentListClassName;
}


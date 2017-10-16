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
    url: "/api/myTask/getRequirementDetail?reqId=" + crowdSourcingId,
    cache: false,
    success: function (data) {
      console.log(data);
      renderPage(data.data);
    }
  });
}




function renderPage(data) {
  let insertRequirementsData = data;
  $('.j_title').html(insertRequirementsData.zbRequirementSPVo.title);
  $('.j_username').html(insertRequirementsData.zbRequirementSPVo.contactName);
  $('.j_phone').html(insertRequirementsData.zbRequirementSPVo.contactPhone);
  $('.j_description').html(insertRequirementsData.zbRequirementSPVo.description);
  $('.j_date').html(insertRequirementsData.zbRequirementSPVo.deliveryDeadline);
  $('.j_money').html('￥ <i>' + insertRequirementsData.zbRequirementSPVo.rewardMoney / 100 + '</i> 元');

  $('.j_checkRemark').html(insertRequirementsData.zbRequirementSPVo.checkRemark);
  let tempTypeHtml = '';
  switch (Number(insertRequirementsData.zbRequirementSPVo.type)) { //需求标签
    case 1 : {
      $('.requirement-type-active span').html('数据采集');
      break;
    }
    case 2 : {
      $('.requirement-type-active span').html('数据加工');
      break;
    }
    case 3 : {
      $('.requirement-type-active span').html('数据模型');
      break;
    }
    case 4 : {
      $('.requirement-type-active span').html('数据应用');
      break;
    }
    case 5 : {
      $('.requirement-type-active span').html('数据清洗');
      break;
    }
    case 6 : {
      $('.requirement-type-active span').html('其他');
      break;
    }
  }

  let loadfileHtml=renderLoadFile(insertRequirementsData.zbRequirementSPVo.annex,'true');
  $('.j_load-file-list').append(loadfileHtml);


  let domModel = $('.crowdsourcing-status span');
  switch (insertRequirementsData.reqStatus) {
    case 1://工作中
      domModel.html('工作中');
      $('.j_myMissionStatus').html('已选中').show();
      $('.release-first-btnbox div').append('<a class="j_submitResult" href="javascript:void(0)">已选中！提交成果</a>');
      $('.j_myMissionResult').attr({"requirementId":insertRequirementsData.zbRequirementSPVo.id,"applyId":insertRequirementsData.zbRequirementApplyVo.id}).hide().prev().show();

      missionApplyInfo(data);


      break;
    case 2: //未中标
     domModel.html('未中标');
     $('.j_myMissionStatus').html('未中标').show();
      $('.release-first-btnbox div').append('<a class="" href="javascript:void(0)">报名未被选中</a>');
      missionApplyInfo(data);
      $('.j_myMissionResult').hide().prev().show();

     break;
    case 3:

      domModel.html('审核通过<br>待托管赏金');
      $('.detailMoneyBox').show();
      $('.release-first-btnbox div').append('<a href="' + host.website + '/payAccount/userRecharge?money=' + insertRequirementsData.managedMoney / 10000 + '">去托管赏金</a>');
      break;
    case 7: //二次托管
      domModel.html('报名结束<br>待托管赏金');
      $('.managedMoneySpanText').html('已托管比例');
      $('.detailMoneyBox,.otherDetailBox').show();
      $('.managedMoneyNotice').hide();
      $('.moneyAdd,.moneySub').hide(); //百分比
      $('.missionResult').hide().prev().show(); //百分比
      missionApplyInfo(data); //任务报名信息显示
      $('.release-first-btnbox div').append('<a class="j_goTwiceMoney" href="' + host.website + '/payAccount/userRecharge?money=' + insertRequirementsData.zbRequirement.rewardMoney*(100-insertRequirementsData.zbRequirement.trusteePercent)/100/100 + '">去托管剩余'+(100-insertRequirementsData.zbRequirement.trusteePercent)+'% 赏金</a>');
      $('.moneyHow').html(insertRequirementsData.zbRequirement.trusteePercent);

      break;
    case 8: //工作中
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
    case 16:
      domModel.html('待退款');
      break;
    case 19: //流标
      domModel.html('交易取消');
      $('.cancleStatusNotice').show().prev().hide();// 悬赏金额 最右侧提示
      $('.cancleStatusNoticeContent').html('无报名流标，交易取消已退款 。');//悬赏金额 最右侧提示内容
      $('.detailMoneyBox').show();



      break;
  }
  rewardMoney = insertRequirementsData.zbRequirementSPVo.rewardMoney;
  $('.moneyManageMoeny').html(rewardMoney * $('.moneyHow').text() / 10000);

}

let commentData={};
$(document).on('click', '.j_submitResult', function () {

  // 提交成果
  $.confirm('\
  <div class="checkMissionBox submitResultBox">\
    <h5>提交成果</h5>\
    <table class="myRequirement-table crowdsourcing-table">\
      <tr>\
        <td>方案标题：</td>\
        <td>\
          <input class="j_resultTitle" type="text" maxlength="20" placeholder="一句话描述您的方案（要求简洁全面，20个汉字以内）">\
        </td>\
      </tr>\
      <tr>\
        <td valign="top">方案描述：</td>\
        <td>\
          <textarea placeholder="请详情描述您的方案，1000汉字以内。" id="resultContent" cols="30" rows="10" maxlength="1000"></textarea>\
        </td>\
      </tr>\
      <tr>\
        <td valign="top">方案描述：</td>\
        <td style="padding:0;">\
          <div class="upload-box">\
            <input type="file" name="filename" class="fileUploadBtn j_firstPage">\
            <span class="falseBen j_firstPage">上传附件</span>\
            <span class="upload-file-notice j_firstPage">最多可上传5个附件，每个附件大小不超过10M。</span>\
          </div>\
          <div class="load-file-list j_resultLoadFile">\
          </div>\
        </td>\
      </tr>\
    </table>\
  </div>', null, function (type) {

    if (type == 'yes') {
      let confirmThis=this;
      let annexList = [];//附件列表
      let list = $('.j_resultLoadFile dl.load-file');
      for (let i = 0; i < list.length; i++) {
        let tempObj = {
          fileName: list.eq(i).attr('fileName'),
          filePath: list.eq(i).attr('filePath')
        };
        annexList.push(tempObj);
      }
      let resultData={
        title:$('.j_resultTitle').val(),
        applyId:$('.j_myMissionResult').attr("applyId"),
        requirementId:$('.j_myMissionResult').attr("requirementId"),
        content:$("#resultContent").val(),
        zbAnnexes:annexList

    };
      //TODO：上传成功之后 请求过来的 还是之前的内容
      console.log(resultData);

      if(resultData.title && resultData.applyId && resultData.requirementId && resultData.content){
        console.log(resultData);
        $.ajax({
          type: 'post',
          url: "/api/program/add",
          cache: false,
          contentType: 'application/json',
          data: JSON.stringify(resultData),
          success: function (data) {
            console.log(data);
            if(data.code==1){
              $('.missionTitle').html(resultData.title);
              $('.missionResultDes').html(resultData.content);
              $('.j_missionResult-load-file-list').html($('.j_resultLoadFile').html());
              $('.j_myMissionResult').show().prev().hide(); //显示评价模块
              $('.j_resultStatus').html('待预评').show();
              confirmThis.hide();// 隐藏弹出框
              $('.j_submitResult').html('重新上传');//上传按钮
              $('.otherDetailBoxNav li').removeClass('active').eq(1).addClass('active').parent().next().children().removeClass('active').eq(1).addClass('active');//选中第二个tab 显示

            }else{
              $.alert(data.message)
            }
          }
        });


      }else{
        $.alert('请填写提交成果内容!')
      }

    } else {
      this.hide();

    }

  });

  $('.fileUploadBtn').fileupload(
    {//激活上传附件功能
      url: host.static + '/upload/other',
      dataType: 'json',
      maxFileSize: 10240000,
      add: function (e, data) {
        var filesize = data.files[0].size;
        if(Math.ceil(filesize / 1024) > 1024*10){
          console.log('文件过大'+filesize);
          $.alert('附件大小不得超过10M！');
          return;
        }
        data.submit();
      },
      done: function (e, data) {
        console.log('上传完毕');
        if ($('.j_resultLoadFile dl').length == 5) {
          $('.upload-file-notice').addClass('color-red');
          setTimeout(function () {
            $('.upload-file-notice').removeClass('color-red');
          }, 2000)
          return;
        } else {
          $('.upload-file-notice').removeClass('color-red');
        }
        if (data.result.code == 1) {
          var obj = data.result.data[0];
          console.log(data);
          console.log(obj.filePath);
          console.log(data.files[0].name);
          var className = fileTypeClassName(data.files[0].name);
          console.log(className);
          var tempHtml = '\
        <dl fileName="' + data.files[0].name + '" filePath="' + obj.absPath + '" class="load-file ' + className + '">\
          <dt><a href="javascript:void(0)" title=""><img src="' + obj.absPath + '"></a></dt>\
          <dd>\
          <span class="overflowpoint">' + data.files[0].name + '</span>\
          <div class="crowdsourcing-table-edit">\
          <a href="' + obj.absPath + '" target="_blank" class="download"><img src="/static/images/crowdsourcing/download.png" alt=""></a>\
          <a href="javascript:void (0)" class="del j_firstPage" ><img src="/static/images/crowdsourcing/del.png" alt=""></a>\
          </div>\
          </dd>\
        </dl>';

          $('.j_resultLoadFile').append(tempHtml)

        } else {
          $.alert(data.result.message)
        }
      },
      progressall: function (e, data) {
      }
    });


});
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
  // $('.otherDetailBoxNav li').removeClass('active').eq(1).addClass('active').parent().next().children().removeClass('active').eq(1).addClass('active');//选中第二个tab 显示

  //任务报名内容
  $('.addTime').html(data.zbRequirementApplyVo.addTime);
  $('.applyContent').html(data.zbRequirementApplyVo.applyContent);
  $('.j_applyPhone').html(data.zbRequirementApplyVo.mobile);
  $('.j_hasApply').html(data.zbRequirementApplyVo.applyNumber);

  //任务成果内容
  $('.j_applyDeadline').html(data.zbRequirementSPVo.deliveryDeadline);
  $('.missionTitle').html(data.zbProgramVo.title).attr('acceptanceAdviceId',data.zbProgramVo.id);

  $('.missionResultDes').html(data.zbProgramVo.content);
  //方案附件列表
  let missionResultLoadfileHtml=renderLoadFile(data.zbProgramVo.zbAnnexes);


  $('.j_missionResult-load-file-list').append(missionResultLoadfileHtml);
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


function renderLoadFile(loadFileList,noDownloadIco='false') { //渲染附件列表
  let tempHtml = '';
  for (let c = 0; c < loadFileList.length; c++) { //渲染附件
    let NoDownLoadIcoDom='';
    if(noDownloadIco !== 'true'){
      NoDownLoadIcoDom='<div class="crowdsourcing-table-edit">\
            <a href="' + loadFileList[c].filePath + '" target="_blank" class="download"><img src="/static/images/crowdsourcing/download.png" alt=""></a>\
          </div>';
    }

    let className = fileTypeClassName(loadFileList[c].filePath);
    tempHtml += '\
        <dl fileName="' + loadFileList[c].fileName + '" filePath="' + loadFileList[c].filePath + '" class="load-file ' + className + '">\
          <dt><a href="javascript:void(0)" title=""><img src="' + loadFileList[c].filePath + '"></a></dt>\
          <dd>\
          <span class="overflowpoint">' + loadFileList[c].fileName + '</span>\
          '+NoDownLoadIcoDom+'\
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



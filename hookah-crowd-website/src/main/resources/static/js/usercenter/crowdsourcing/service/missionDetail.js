/**
 * Created by Dajun on 2017-9-19.
 */

var crowdSourcingId = GetUrlValue('id');
var rewardMoney = null;
var commentData={};

// 获取需求类型
function getRequirementType() {
  $.ajax({
    type: 'get',
    url: "/zbType/requirementsType",
    dataType: 'json',
    contentType: 'application/json',
    success: function (data) {
      var list = data.data;
      console.log(data);
      var tempHtml = '';
      for (var i = 0; i < list.length; i++) {
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
  var insertRequirementsData = data;
  $('.j_title').html(insertRequirementsData.zbRequirementSPVo.title);
  $('.j_username').html(insertRequirementsData.zbRequirementSPVo.contactName);
  $('.j_phone').html(insertRequirementsData.zbRequirementSPVo.contactPhone);
  $('.j_description').html(insertRequirementsData.zbRequirementSPVo.description);
  $('.j_date').html(insertRequirementsData.zbRequirementSPVo.deliveryDeadline);
  $('.j_money').html('￥ <i>' + insertRequirementsData.zbRequirementSPVo.rewardMoney / 100 + '</i> 元');
  $('.j_applyDeadline').html(data.zbRequirementSPVo.applyDeadline);

  $('.j_checkRemark').html(insertRequirementsData.zbRequirementSPVo.checkRemark);
  var tempTypeHtml = '';
  switch (Number(insertRequirementsData.zbRequirementSPVo.type)) { //需求标签
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
  var loadfileHtml=null;


  var domModel = $('.crowdsourcing-status span');
  switch (insertRequirementsData.reqStatus) {
    case 1://工作中
      domModel.html('工作中');
      $(".crowdsourcing-progress-box li:gt(2) .step").addClass('active');

      $('.j_myMissionStatus').html('已选中').show();
      $('.release-first-btnbox div').append('<a class="j_submitResult" resultType="firstLoadResult" href="javascript:void(0)">已选中！提交成果</a>');
      $('.j_myMissionResult').attr({"requirementId":insertRequirementsData.zbRequirementSPVo.id,"applyId":insertRequirementsData.zbRequirementApplyVo.id}).hide().prev().show();

      loadfileHtml=renderLoadFile(insertRequirementsData.zbRequirementSPVo,'false');//有下载按钮的附件列表

      missionApplyInfo(data);


      break;
    case 2: //未中标
      domModel.html('未中标');
      $(".crowdsourcing-progress-box li:gt(1) .step").addClass('active');
      $('.j_myMissionStatus').html('未中标').show();
      $('.release-first-btnbox div').append('<a class="" href="javascript:void(0)">报名未被选中</a>');
      missionApplyInfo(data);
      $('.j_myMissionResult').hide().prev().show();

      loadfileHtml=renderLoadFile(insertRequirementsData.zbRequirementSPVo,'true');//没有下载按钮的附件列表

      break;
    case 3: //预评中
      domModel.html('预评中');
      $(".crowdsourcing-progress-box li:gt(3) .step").addClass('active');

      missionApplyInfo(data);
      $('.j_myMissionStatus').html('已选中').show();

      loadfileHtml=renderLoadFile(insertRequirementsData.zbRequirementSPVo,'false');//有下载按钮的附件列表

      switch (data.zbProgramVo.status){
        case 0:
          $('.j_resultStatus').html('待预评').show();
          break;
        case 2:
          $('.missionStatusResult').html('预评不通过，待修改');
          $('.checkAdviceDetailBox').html(data.zbProgramVo.checkAdvice);
          $('.j_resultStatus').show();
          $('.release-first-btnbox div').append('<a class="j_submitResult" resultType="reloadResult" href="javascript:void(0)">重新提交成果</a>');
          $('.j_myMissionResult').attr({"requirementId":insertRequirementsData.zbRequirementSPVo.id,"applyId":insertRequirementsData.zbRequirementApplyVo.id});

          break;
      }

      break;
    case 4: //验收中
      domModel.html('验收中')
      $(".crowdsourcing-progress-box li:gt(4) .step").addClass('active');
      missionApplyInfo(data);
      $('.j_myMissionStatus').html('已选中').show();

      loadfileHtml=renderLoadFile(insertRequirementsData.zbRequirementSPVo,'false');//有下载按钮的附件列表

      switch (data.zbProgramVo.status){
        case 1:
          $('.missionStatusResult').html('预评通过，待验收');
          $('.checkAdviceDetailBox').html(data.zbProgramVo.checkAdvice);
          $('.j_resultStatus').show();

          break;
        case 4:
          $('.missionStatusResult').html('验收不通过，待修改');
          $('.checkAdviceDetailBox').html(data.zbProgramVo.checkAdvice);
          $('.j_resultStatus').show();
          $('.release-first-btnbox div').append('<a class="j_submitResult" resultType="reloadResult" href="javascript:void(0)">重新提交成果</a>');
          $('.j_myMissionResult').attr({"requirementId":insertRequirementsData.zbRequirementSPVo.id,"applyId":insertRequirementsData.zbRequirementApplyVo.id});

          break;
      }


      break;
    case 5://待付款
      domModel.html('待付款');//
      $(".crowdsourcing-progress-box li:gt(5) .step").addClass('active');
      missionApplyInfo(data);
      $('.j_myMissionStatus').html('已选中').show();

      loadfileHtml=renderLoadFile(insertRequirementsData.zbRequirementSPVo,'false');//有下载按钮的附件列表

      $('.missionStatusResult').html('预评通过，待验收');
      $('.checkAdviceDetailBox').html(data.zbProgramVo.checkAdvice);
      $('.j_resultStatus').show();
      $('.hasPayMoney').append('<span class="signUp">待付款</span>');

      break;
    case 6:
      domModel.html('待评价');

      missionApplyInfo(data); //任务报名信息显示
      $('.missionStatus').show();
      $('.hasPayMoney').append('<span class="signUp">已付款</span>');
      $('.j_myMissionResult').attr("requirementId",insertRequirementsData.zbRequirementSPVo.id);
      loadfileHtml=renderLoadFile(insertRequirementsData.zbRequirementSPVo,'false');//有下载按钮的附件列表

      $.fn.raty.defaults.path = '/static/images/crowdsourcing';//初始化星星图标位置

      if(insertRequirementsData.zbCommentVo !== null && insertRequirementsData.zbCommentVo.servicerComment!==null){
        $('.serviceCommentStar').raty({ readOnly: true, score: insertRequirementsData.zbCommentVo.servicerComment.level });
        $('.serviceCommentDetail').html(insertRequirementsData.zbCommentVo.servicerComment.content);
        $('.serviceCommentTime').html(insertRequirementsData.zbCommentVo.servicerComment.addTime);
      }
      $('.missionStatusResult').html('验收通过且已付款，待评价！');
      $('.checkAdviceDetailBox').html(insertRequirementsData.zbProgramVo.checkAdvice);//验收意见
      $('.release-first-btnbox div').append('<a class="j_commentBtn" href="javascript:void(0)">评价</a>');



      break;
    case 7:
      domModel.html('交易取消');
      $(".crowdsourcing-progress-box li:gt(4) .step").addClass('active');

      missionApplyInfo(data);
      $('.j_myMissionStatus').html('已选中').show();

      loadfileHtml=renderLoadFile(insertRequirementsData.zbRequirementSPVo,'false');//有下载按钮的附件列表

      $('.missionStatusResult').html('方案不符合需求方要求，驳回交易失败');
      if(data.zbProgramVo!==null){

        $('.checkAdviceDetailBox').html(data.zbProgramVo.checkAdvice);
        $('.j_resultStatus').show();
      }

      break;
    case 8:
      domModel.html('交易完成');
      loadfileHtml=renderLoadFile(insertRequirementsData.zbRequirementSPVo,'false');//有下载按钮的附件列表


      $.fn.raty.defaults.path = '/static/images/crowdsourcing';//初始化星星图标位置

      $('.myCommentStar').raty({ readOnly: true, score: insertRequirementsData.zbCommentVo.requireZbComment.level });
      $('.myCommentDetail').html(insertRequirementsData.zbCommentVo.requireZbComment.content);
      $('.myCommentTime').html(insertRequirementsData.zbCommentVo.requireZbComment.addTime);

      $('.serviceCommentStar').raty({ readOnly: true, score: insertRequirementsData.zbCommentVo.servicerComment.level });
      $('.serviceCommentDetail').html(insertRequirementsData.zbCommentVo.servicerComment.content);
      $('.serviceCommentTime').html(insertRequirementsData.zbCommentVo.servicerComment.addTime);
      $('.commentBox').show(); //显示评价模块
      $('.j_resultStatus').html('需求方验收通过').show();

      missionApplyInfo(data); //任务报名信息显示
      break;
   }
  $('.j_load-file-list').append(loadfileHtml);//需求附件
  if(insertRequirementsData.zbProgramVo !== null){
    var programLoadfileHtml=renderLoadFile(insertRequirementsData.zbProgramVo,'false');//有下载按钮的附件列表
    $('.j_missionResult-load-file-list').append(programLoadfileHtml);
  }

  rewardMoney = insertRequirementsData.zbRequirementSPVo.rewardMoney;
  $('.moneyManageMoeny').html(rewardMoney * $('.moneyHow').text() / 10000);


  //时间显示
  if(insertRequirementsData.mgZbRequireStatus!==null){
    $('.j_applyTime').html(data.mgZbRequireStatus.applyTime);
    $('.j_selectTime').html(data.mgZbRequireStatus.selectTime);
    $('.j_submitTime').html(data.mgZbRequireStatus.submitTime);
    $('.j_platevalTime').html(data.mgZbRequireStatus.platevalTime);
    $('.j_requiredAcceptTime').html(data.mgZbRequireStatus.requiredAcceptTime);
    $('.j_payTime').html(data.mgZbRequireStatus.payTime);
    $('.j_requireCommentTime').html(data.mgZbRequireStatus.requireCommentTime);
  }

}



$(document).on('click', '.j_submitResult', function () {
  console.log($(this).attr('resultType'));
  var resultDOM='';
  if($(this).attr('resultType')=='firstLoadResult'){
    resultDOM='待预评。'
  }else{
    resultDOM='预评通过，待验收。'
  }
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
      var confirmThis=this;
      var annexList = [];//附件列表
      var list = $('.j_resultLoadFile dl.load-file');
      for (var i = 0; i < list.length; i++) {
        var tempObj = {
          fileName: list.eq(i).attr('fileName'),
          filePath: list.eq(i).attr('filePath')
        };
        annexList.push(tempObj);
      }
      var resultData={
        title:$('.j_resultTitle').val(),
        applyId:$('.j_myMissionResult').attr("applyId"),
        requirementId:$('.j_myMissionResult').attr("requirementId"),
        content:$("#resultContent").val(),
        annex:annexList

    };
      //上传成功之后 请求过来的 还是之前的内容
      console.log(resultData);
      if(resultData.title && resultData.applyId && resultData.requirementId && resultData.content){
        $.ajax({
          type: 'post',
          url: "/api/program/save",
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
              $('.j_resultStatus').html(resultDOM).show();
              // location.reload();
              confirmThis.hide();// 隐藏弹出框
              $('.j_submitResult').remove();//上传按钮
              $('.otherDetailBoxNav li').removeClass('active').eq(1).addClass('active').parent().next().children().removeClass('active').eq(1).addClass('active');//选中第二个tab 显示
              location.reload()
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


$(document).on('click', '.del', function () { //鼠标离开描述显示工具栏
  $(this).parent().parent().parent().remove();
});

$(document).on('click', '.j_commentBtn', function () { // 评价
  $.confirm('\
  <div class="checkMissionBox">\
    <h5>对需求方的评价</h5>\
    <span>请您根据本次交易，给予真实客观评价。您的评价将影响需求方的信用</span>\
    <table class="crowdsourcing-table myRequirement-table">\
      <tr>\
        <td>验收结果：</td>\
        <td>\
          <div class="j_starBox"></div>\
        </td>\
      </tr>\
      <tr>\
        <td valign="top">评价内容：</td>\
        <td>\
          <textarea placeholder="写下您对需求方的评价吧，100个字以内。" id="commentContent" cols="30" rows="10" maxlength="100"></textarea>\
        </td>\
      </tr>\
    </table>\
  </div>', null, function (type) {

    if (type == 'yes') {
      var confirmThis=this;
      commentData.programId=$('.missionTitle').attr('acceptanceAdviceId');
      commentData.content=$("#commentContent").val();
      commentData.requirementId=$('.j_myMissionResult').attr("requirementId");

      console.log(commentData);

      if(commentData.level && commentData.programId && commentData.content){
        $.ajax({
          type: 'post',
          url: "/api/program/addComment",
          cache: false,
          data:commentData,
          success: function (data) {
            console.log(data);
            if(data.code==1){
              $('.myCommentStar').raty({ readOnly: true, score: commentData.level });
              $('.myCommentDetail').html(commentData.content);
              $('.myCommentTime').html(data.data.addTime);//TODO:评价添加时间要检测一下
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
   var missionTitle=$('.missionTitle').html();
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
      var confirmThis=this;
      var acceptanceAdvice={};
      acceptanceAdvice.status=$("input[name='resultStatus']:checked").val();
      acceptanceAdvice.id=$('.missionTitle').attr('acceptanceAdviceId');
      acceptanceAdvice.checkAdvice=$("#checkAdvice").val();
      console.log(acceptanceAdvice);

      if(acceptanceAdvice.status && acceptanceAdvice.id && acceptanceAdvice.checkAdvice){
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
              location.reload()
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
  if(data.zbRequirementApplyVo !==null) {
    $('.addTime').html(data.zbRequirementApplyVo.addTime);
    $('.applyContent').html(data.zbRequirementApplyVo.applyContent);
    $('.j_applyPhone').html(data.zbRequirementApplyVo.mobile);
    $('.j_hasApply').html(data.zbRequirementApplyVo.applyNumber);
  }

  //任务成果内容
  if(data.zbProgramVo !==null){
    $('.missionTitle').html(data.zbProgramVo.title).attr('acceptanceAdviceId',data.zbProgramVo.id);
    $('.missionResultDes').html(data.zbProgramVo.content);
  }



}
$('.j_checkAdviceDetail').on('mouseover', function () { //鼠标离开描述显示工具栏
  $('.checkAdviceDetailBox').show();
}).on('mouseout', function () { //鼠标离开描述显示工具栏
  $('.checkAdviceDetailBox').hide();
});




$(document).on('click', '.moneyAdd', function () { //托管资金点击增加 托管金额百分比
  var percentage = Number($('.moneyHow').html());
  if (30 <= percentage && percentage < 100) {
    percentage += 1;
    $('.moneyHow').html(Number(percentage));
    $('.moneyManageMoeny').html(rewardMoney * percentage / 10000);

  }
});

$(document).on('click', '.moneySub', function () { //托管资金点击增加 托管金额百分比
  var percentage = Number($('.moneyHow').html());
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
  var loadFileList=loadFileList.annex;
  if(loadFileList==null){
    return;
  }

  var tempHtml = '';
  for (var c = 0; c < loadFileList.length; c++) { //渲染附件
    var NoDownLoadIcoDom='';
    if(noDownloadIco !== 'true'){
      NoDownLoadIcoDom='<div class="crowdsourcing-table-edit">\
            <a href="' + loadFileList[c].filePath + '" target="_blank" class="download"><img src="/static/images/crowdsourcing/download.png" alt=""></a>\
          </div>';
    }

    var className = fileTypeClassName(loadFileList[c].filePath);
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
  var fileTypeReg = /[^.]*$/;
  var fileType = fileTypeReg.exec(fileName)[0];

  var fileTypeObj = {
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
  var attachmentListClassName = '';
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



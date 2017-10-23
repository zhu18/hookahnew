/**
 * Created by Dajun on 2017-9-19.
 */

let crowdSourcingId = GetUrlValue('id');
let rewardMoney = null;
let applyData={};

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
  $('.j_title').html(insertRequirementsData.zbRequirementSPVo.title).attr('requirementid',insertRequirementsData.zbRequirementSPVo.id);
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
  let loadfileHtml=null;


  let domModel = $('.crowdsourcing-status span');
  switch (insertRequirementsData.reqStatus) {
    case -1://报名中
      domModel.html('报名中');
      $('.j_myMissionStatus').html('待评选').show();
      $('.release-first-btnbox div').append('<a class="j_commentBtn" href="javascript:void(0)">我要报名</a>');

      loadfileHtml=renderLoadFile(insertRequirementsData.zbRequirementSPVo.annex,'false');//有下载按钮的附件列表
      $('.applyDeadlineBox .j_applyDeadline').html(data.zbRequirementSPVo.applyDeadline).parent().parent().show();
      break;

    case 0://报名中
      domModel.html('报名中');
      $('.j_myMissionStatus').html('待评选').show();
      $('.release-first-btnbox div').append('<a href="javascript:void(0)">报名成功，待评选</a>');

      loadfileHtml=renderLoadFile(insertRequirementsData.zbRequirementSPVo.annex,'false');//有下载按钮的附件列表
      $('.applyDeadlineBox .j_applyDeadline').html(data.zbRequirementSPVo.applyDeadline).parent().parent().show();

      $('.addTime').html(data.zbRequirementApplyVo.addTime);
      $('.applyContent').html(data.zbRequirementApplyVo.applyContent);
      $('.j_applyPhone').html(data.zbRequirementApplyVo.mobile);
      $('.j_hasApply').html(data.zbRequirementApplyVo.applyNumber);
      $('.otherDetailBox').show();

      break;

   }
  $('.j_load-file-list').append(loadfileHtml);

  rewardMoney = insertRequirementsData.zbRequirementSPVo.rewardMoney;
  $('.moneyManageMoeny').html(rewardMoney * $('.moneyHow').text() / 10000);

}

$(document).on('click', '.j_commentBtn', function () { // 我要报名
  $.confirm('\
  <div class="checkMissionBox applyBox">\
    <h5>我要报名</h5>\
    <span>请您根据本次交易，给予真实客观评价。您的评价将影响需求方的信用</span>\
    <table class="crowdsourcing-table myRequirement-table">\
      <tr>\
        <td valign="top">自荐理由：</td>\
        <td>\
          <textarea placeholder="请输入自荐理由，字数在100字以内。" id="commentContent" cols="30" rows="10" maxlength="100"></textarea>\
        </td>\
      </tr>\
    </table>\
    <div class="applyNotice">\
    <span>您确认承接该需求？（报名平台不收到任何佣金）</span>\
    <p>报名成功后，平台将在报名截止后对您的资格进行评选，请耐心等候。</p>\</div>\
  </div>', null, function (type) {

    if (type == 'yes') {
      var confirmThis=this;


        $.ajax({
          url: '/api/isAuthProvider',
          type: 'post',
          success: function (data) {
            if(data){ //已经认证
              applyData.requirementId=$('.j_title').attr('requirementid');
              applyData.applyContent=$("#commentContent").val();
              console.log(applyData);

              if(applyData.applyContent && applyData.requirementId){
                $.ajax({
                  type: 'post',
                  url: " /api/apply/add",
                  cache: false,
                  data:applyData,
                  success: function (data) {
                    console.log(data);

                    console.log(data);
                    if(data.code==1){

                      $('.addTime').html(data.data.addTime);
                      $('.applyContent').html(data.data.applyContent);
                      $('.j_applyPhone').html(data.data.mobile);
                      $('.j_hasApply').html(data.data.applyNumber);
                      $('.otherDetailBox').show();

                      confirmThis.hide();// 隐藏弹出框
                      $('.j_commentBtn').html('报名成功，待评选').removeClass('j_commentBtn')
                    }else{
                      $.alert(data.message)
                    }
                  }
                });


              }else{
                $.alert('请对服务商评价!')
              }

            }else{ //未认证
              window.location.href = host.loginUrl + encodeURIComponent(host.crowd+'/usercenter/missionApply?id='+crowdSourcingId);
              $.confirm('<div style="padding: 15px; text-align:left;">您好！<span style="color:red">您还不是服务商</span>，不能参加需求任务报名，<br>如想报名请点击 【确定】 申请成为服务商，<br>按要求提交信息即可通过服务商认证。 </div>',null,function(type){
                 if(type == 'yes'){
                   if(userType==2){
                    window.location.href = host.loginUrl + encodeURIComponent(host.crowd+'/usercenter/pspAuthentication');
                   }else{
                    window.location.href = host.loginUrl + encodeURIComponent(host.crowd+'/usercenter/epAuthentication');
                   }
                   this.hide();
                 }else{
                  this.hide();

                 }
               })

            }
          }
        });


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


function missionApplyInfo(data) { //任务报名信息显示
  $('.detailMoneyBox,.otherDetailBox,.applyDeadlineBox').show();//显示下方tab


  //任务报名内容
/*
  $('.addTime').html(data.zbRequirementApplyVo.addTime);
  $('.applyContent').html(data.zbRequirementApplyVo.applyContent);
  $('.j_applyPhone').html(data.zbRequirementApplyVo.mobile);
  $('.j_hasApply').html(data.zbRequirementApplyVo.applyNumber);
*/

}

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



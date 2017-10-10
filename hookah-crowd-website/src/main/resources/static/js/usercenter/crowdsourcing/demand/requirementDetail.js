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
      $('.release-first-btnbox div').append('<a class="j_commentBtn" href="javascript:void(0)">评价</a>');


      break;
    case 14:
      domModel.html('交易取消');
      break;
    case 15:
      domModel.html('交易成功');
      break;
    case 16:
      domModel.html('待退款');
      break;
  }
  rewardMoney = insertRequirementsData.zbRequirement.rewardMoney;
  $('.moneyManageMoeny').html(rewardMoney * $('.moneyHow').text() / 10000);

}

$(document).on('click', '.j_checkMission', function () { // 成果验收
   let missionTitle=$('.missonTitle').html();
  $.confirm('\
  <div class="checkMissionBox">\
    <h5>需求方验收-' + missionTitle + '</h5>\
    <table>\
      <tr>\
        <th>验收结果：</th>\
        <td>\
          <label><input type="radio" name="resultStatus" value="1" checked> 通过</label>\
          <label><input type="radio" name="resultStatus" value="2"> 不通过,退回修改</label>\
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
      acceptanceAdvice.id=$('.missonTitle').attr('acceptanceAdviceId');
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
              if(acceptanceAdvice.status == 1){
                $('.missionStatus').html('验收通过，待付款！').show();
              }else if(acceptanceAdvice.status == 2){
                $('.missionStatus').html('验收不通过，待修改！').show();
              }else if(acceptanceAdvice.status == 5){
                $('.missionStatus').html('方案不符合需求方要求，验收驳回！').show();
                $('.canNotSelect').show();
                $('.j_missionResult-load-file-list .crowdsourcing-table-edit').remove();
              }
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
  $('.j_companyName').html(data.user.orgName);
  $('.j_SignUpTime').html(data.applyTime);
  $('.j_contentName').html(data.user.contactName);

  $('.j_contentPhone').html(data.user.contactPhone);
  //任务成果内容
  $('.j_applyDeadline').html(data.zbRequirement.applyDeadline);
  $('.missonTitle').html(data.zbProgram.title).attr('acceptanceAdviceId',data.zbProgram.id);
  $('.missionStatus').html(data.zbProgram.checkAdvice);

  $('.missionResultDes').html(data.zbProgram.content);
  //方案附件列表
  let missionResultLoadfileHtml=renderLoadFile(data.programFiles);


  $('.j_missionResult-load-file-list').append(missionResultLoadfileHtml);
}



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


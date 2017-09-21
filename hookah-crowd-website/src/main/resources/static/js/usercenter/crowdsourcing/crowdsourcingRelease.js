/**
 * Created by Dajun on 2017-9-19.
 */

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
      crowdsourcingRelease();//放这里的原因是 要等到需求类型渲染出来才可以 请求草稿数据（草稿数据里有选中的需求类型）

    }
  });

}
getRequirementType();

let crowdSourcingId = null;
function crowdsourcingRelease() {
  $.ajax({
    type: 'get',
    url: "/api/release/requirementInfo",
    success: function (data) {
      console.log(data);
      if (data.data.hasOwnProperty('zbRequirement')) {
        if(data.data.zbRequirement.id){
          crowdSourcingId=data.data.zbRequirement.id;
        }else{
          crowdSourcingId=null;
        }
        $('#J_title').val(data.data.zbRequirement.title);
        $('#J_username').val(data.data.zbRequirement.contactName);
        $('.requirement-type').attr('value', data.data.zbRequirement.type);
        $('#J_phone').val(data.data.zbRequirement.contactPhone);
        $('#J_tag').val(data.data.zbRequirement.tag);
        $('#J_description').val(data.data.zbRequirement.description);
        $('#J_date').val(data.data.zbRequirement.deliveryDeadline);
        $('#J_money').val(data.data.zbRequirement.rewardMoney/100);
        $('#J_checkRemark').val(data.data.zbRequirement.checkRemark);
        let spanList = $('.requirement-type span');
        for (let i = 0; i < spanList.length; i++) {
          if (spanList.eq(i).attr('value') == data.data.zbRequirement.type) {
            spanList.eq(i).addClass('active');
          }
        }
        let tempHtml = '';

        for (let c = 0; c < data.data.zbRequirementFiles.length; c++) {

          var className = fileTypeClassName(data.data.zbRequirementFiles[c].fileName);
          tempHtml += '\
        <dl fileName="' + data.data.zbRequirementFiles[c].fileName + '" filePath="' + data.data.zbRequirementFiles[c].filePath + '" class="load-file ' + className + '">\
          <dt><a href="javascript:void(0)" title=""><img src="' + data.data.zbRequirementFiles[c].filePath + '"></a></dt>\
          <dd>\
          <span class="overflowpoint">' + data.data.zbRequirementFiles[c].fileName + '</span>\
          <div class="crowdsourcing-table-edit">\
          <a href="' + data.data.zbRequirementFiles[c].filePath + '" target="_blank" class="download"><img src="/static/images/crowdsourcing/download.png" alt=""></a>\
          <a href="javascript:void (0)" class="del j_firstPage" ><img src="/static/images/crowdsourcing/del.png" alt=""></a>\
          </div>\
          </dd>\
        </dl>';

        }

        $('.load-file-list').append(tempHtml)


      }
    }
  });
}


var end = {
  format: "YYYY-MM-DD hh:mm:ss",
  isTime: true,
  maxDate: $.nowDate(30),
  choosefun: function (elem, datas) {
  }

};
$.jeDate("#J_date", end);
$(document).on('mouseenter', '.load-file', function () { //鼠标滑过描述显示工具栏
  $(this).children().find('.crowdsourcing-table-edit').css({'display': 'block'}).stop().animate({
    'opacity': 1,
    'top': 0
  }, 300);
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


//上传页面

$('.fileUploadBtn').fileupload(
  {
    url: host.static + '/upload/other',
    dataType: 'json',
    maxFileSize: 10240000,
    done: function (e, data) {
      console.log('上传完毕')
      if ($('.load-file-list dl').length == 5) {
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

        $('.load-file-list').append(tempHtml)

      } else {
        $.alert(data.result.message)
      }
    },
    progressall: function (e, data) {

    }
  });

$(document).on('click', '.del', function () { //鼠标离开描述显示工具栏
  $(this).parent().parent().parent().remove();
});

$(document).on('click', '.requirement-type span', function () { //鼠标离开描述显示工具栏
  $(this).addClass('active').siblings().removeClass('active').parent().attr('value', $(this).attr('value'))
});


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


$(document).on('click', '#J_nextPage', function () { //鼠标离开描述显示工具栏

  let annexList = [];//附件列表
  let list = $('dl.load-file');
  for (let i = 0; i < list.length; i++) {
    let tempObj = {
      fileName: list.eq(i).attr('fileName'),
      filePath: list.eq(i).attr('filePath')
    };
    annexList.push(tempObj);
  }

  let insertRequirementsData = {
    "zbRequirement": {
      "id": crowdSourcingId,//id
      "title": $('#J_title').val(),//标题
      "contactName": $('#J_username').val(),//联系人姓名
      "contactPhone": $('#J_phone').val(),//联系人电话
      "type": $('.requirement-type').attr('value'),//需求类型
      "tag": $('#J_tag').val(),//标签
      "description": $('#J_description').val(),//描述
      "deliveryDeadline": $('#J_date').val(),//交付截止日期
      "rewardMoney": $('#J_money').val(),//悬赏金额
      "checkRemark": $('#J_checkRemark').val()//交付验收要求
    },
    "annex": annexList
  };


  if (insertRequirementsData.zbRequirement.title && insertRequirementsData.zbRequirement.type && insertRequirementsData.zbRequirement.description && insertRequirementsData.zbRequirement.deliveryDeadline && insertRequirementsData.zbRequirement.rewardMoney && insertRequirementsData.zbRequirement.checkRemark) {
    $.ajax({
      type: 'post',
      url: "/api/release/insertRequirements",
      dataType: 'json',
      contentType: 'application/json',
      data: JSON.stringify(insertRequirementsData),
      success: function (data) {
        console.log(data);
        if (data.data) {
          crowdSourcingId = data.data.zbRequirement.id;
          $('.j_title').html(insertRequirementsData.zbRequirement.title);
          $('.j_username').html(insertRequirementsData.zbRequirement.contactName);
          $('.j_phone').html(insertRequirementsData.zbRequirement.contactPhone);
          $('.j_description').html(insertRequirementsData.zbRequirement.description);
          $('.j_date').html(insertRequirementsData.zbRequirement.deliveryDeadline);
          $('.j_money').html(insertRequirementsData.zbRequirement.rewardMoney);
          let temTagHtml = '';
          let temTagArr = insertRequirementsData.zbRequirement.tag.split(',');
          if(temTagArr[0]){
            for (let t = 0; t < temTagArr.length; t++) {
              temTagHtml += '<i class="type-span">' + temTagArr[t] + '</i>'
            }
          }
          $('.j_tag').html(temTagHtml);

          $('.j_checkRemark').html(insertRequirementsData.zbRequirement.checkRemark);

          let tempTypeHtml = '';
          switch (Number(insertRequirementsData.zbRequirement.type)) {
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
          $('.j_firstPage').hide();
          $('.secondPage').show();
        }
      }
    })


  }
  else {
    $.alert('带 * 为必填项，请按要求输入！')
  }

})
;


$(document).on('click', '#J_prevPage', function () { //鼠标离开描述显示工具栏
  $('.j_firstPage').show();
  $('.secondPage,.tagNoticeContent').hide()

});


$(document).on('click', '#J_release', function () { //鼠标离开描述显示工具栏
  $.ajax({
    type: 'get',
    url: "/api/release/requirementSubmit?id=" + crowdSourcingId,
    success: function (data) {
      console.log(data);
      if (data.data) {
        $.confirm('需求提交成功，等待平台审核！',null,function(type){
          if(type == 'yes'){
            this.hide();
            window.location.href =  host.crowd+'/usercenter/myRequirement';

          }else{
            this.hide();
            window.location.href =  host.crowd+'/usercenter/myRequirement';

          }
        });
      }
    }
  })


});



















/**
 * Created by Dajun on 2017-9-19.
 */


let crowdSourcingId = GetUrlValue('id');
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
      renderPage(data.data[0]);
    }
  });
}


function renderPage(data) {
  let insertRequirementsData=data;
  $('.j_title').html(insertRequirementsData.zbRequirement.title);
  $('.j_username').html(insertRequirementsData.zbRequirement.contactName);
  $('.j_phone').html(insertRequirementsData.zbRequirement.contactPhone);
  $('.j_description').html(insertRequirementsData.zbRequirement.description);
  $('.j_date').html(insertRequirementsData.zbRequirement.deliveryDeadline);
  $('.j_money').html('￥ '+insertRequirementsData.zbRequirement.rewardMoney+' 元');
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

  let tempHtml = '';
  for (let c = 0; c < data.zbRequirementFiles.length; c++) {

    let className = fileTypeClassName(data.zbRequirementFiles[c].fileName);
    tempHtml += '\
        <dl fileName="' + data.zbRequirementFiles[c].fileName + '" filePath="' + data.zbRequirementFiles[c].filePath + '" class="load-file ' + className + '">\
          <dt><a href="javascript:void(0)" title=""><img src="' + data.zbRequirementFiles[c].filePath + '"></a></dt>\
          <dd>\
          <span class="overflowpoint">' + data.zbRequirementFiles[c].fileName + '</span>\
          <div class="crowdsourcing-table-edit">\
          <a href="' + data.zbRequirementFiles[c].filePath + '" target="_blank" class="download"><img src="/static/images/crowdsourcing/download.png" alt=""></a>\
          </div>\
          </dd>\
        </dl>';
  }
  $('.load-file-list').append(tempHtml)
}



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


/**
 * Created by Dajun on 2017-9-19.
 */


var end = {
  format: "YYYY-MM-DD hh:mm:ss",
  isTime: true,
  maxDate: $.nowDate(30),
  choosefun: function (elem, datas) {
  }

};
$.jeDate("#endDate", end);
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
$('.tagNotice').on('mouseover',  function () { //鼠标离开描述显示工具栏
  $(this).next().show();
}).on('mouseout' , function () { //鼠标离开描述显示工具栏
  $(this).next().hide();
});


//上传页面

$('.fileUploadBtn').fileupload(
  {
  url: host.static + '/upload/other',
  dataType: 'json',
  maxFileSize:10240000,
  done: function (e, data) {
    console.log('上传完毕')
    if ($('.load-file-list dl').length == 5 ) {
      $('.upload-file-notice').addClass('color-red');
      setTimeout(function () {
        $('.upload-file-notice').removeClass('color-red');
      },2000)
      return;
    }else{
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
        <dl class="load-file ' + className + '">\
          <dt><a href="javascript:void(0)" title=""><img src="' + obj.absPath + '"></a></dt>\
          <dd>\
          <span class="overflowpoint">' + data.files[0].name + '</span>\
          <div class="crowdsourcing-table-edit">\
          <a href="' + obj.absPath + '" target="_blank" class="download"><img src="/static/images/crowdsourcing/download.png" alt=""></a>\
          <a href="javascript:void (0)" class="del" ><img src="/static/images/crowdsourcing/del.png" alt=""></a>\
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
  $(this).addClass('active').siblings().removeClass('active')
});






//需求类型

function getRequirementType() {
  $.ajax({
    type: 'get',
    url: "/zbType/requirementsType",
    dataType: 'json',
    contentType: 'application/json',
    success: function (data) {
      let list=data.data;
      console.log(data);
      let  tempHtml=''
      for(let i=0;i<list.length;i++){
        tempHtml+='<span value="'+list[i].id+'">'+list[i].typeName+'</span>'

      }
      $('.requirement-type').html(tempHtml)
    }
  });
}
getRequirementType()

















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























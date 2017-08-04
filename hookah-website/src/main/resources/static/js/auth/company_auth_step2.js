/**
 * Created by wcq on 2017/5/8.
 */
$(function () {
  //地域加载
  var regionParam = 100000;
  loadRegion('registerProvince', regionParam); //加载地域
  loadRegion('workProvince', regionParam); //加载地域
  supplier()
});
$('.upLoad').mouseover(function () {
  $(this).children('.upLoad_warp').show();
});
$('.upLoad').mouseleave(function () {
  $(this).children('.upLoad_warp').hide();
});
//地域加载
function loadRegion(id, regionParam) {
  var parentId = '';
  if (regionParam == 100000) {
    parentId = 100000;
  } else if (typeof regionParam == "string") {
    parentId = parseInt(regionParam);
  }
  else {
    parentId = $(regionParam).val();
  }
  $(regionParam).nextAll().html('<option value="-1"></option>');
  $.ajax({
    type: "get",
    url: "/region/getRegionCodeByPid",
    async:false,
    data: {
      parentId: parentId
    },
    success: function (data) {
      if (data.code == 1) {
        if (data.data.length > 0) {
          renderRegion(id, data.data)
        }
      } else {
        $.alert(data.message)
      }
    }
  });
}
function renderRegion(id, data, flog) {
  var html = '<option value="">全部</option>';
  data.forEach(function (e) {
    html += '<option value="' + e.id + '">' + e.name + '</option>';
  });
  $('#' + id).html(html);
}

//上传图片
var fileUploadUrl = host.static + '/upload/fileUpload';
$('.unloadBtn').fileupload({
  url: fileUploadUrl,
  dataType: 'json',
  add: function (e, data) {
    var filesize = data.files[0].size;
    if (Math.ceil(filesize / 1024) > 1024 * 5) {
      console.log('文件过大' + filesize);
      $.alert('文件过大');
      return;
    }
    data.submit();
  },
  done: function (e, data) {
    if (data.result.code == 1) {
      console.log(2);
      var obj = data.result.data[0];
      $(this).parent().siblings("img").attr("src", obj.absPath);
      $(this).parent().siblings(".input_hide").val(obj.filePath);
      $(this).parent().siblings(".input_op").val(obj.filePath);
      $(this).parent().siblings(".upLoad_warp").html('点击替换');
    } else {
      $.alert(data.result.message)
    }

  },
  progressall: function (e, data) {

  }
});
function companyAuth() {

$.ajax({
   url : "/auth/orgAuth",
   cache:false,
   data : {
   "orgName":$("input[name='governmentName']").val(),//单位名称
   "creditCode":$("input[name='creditCode']").val(),//统一社会信用代码
   "industry":$("input[name='mainBusiness']").val(),//经营范围
   "lawPersonName":$("input[name='companyLegal']").val(),//法定代表人
   "lawPersonCategory":"0",//类别
   "lawPersonNum":$("input[name='lawPersonNum']").val(),//企业法人代表证件编号
   "lawPersonPositivePath":$("input[name='lawPersonPositivePath']").val(),//身份证正面
   "lawPersonNegativePath":$("input[name='lawPersonNegativePath']").val(),//身份证反面
   "region":$('select[name="registerCity"] option:selected').val(),//所在地
   "contactAddress":$("input[name='address']").val(),//详细地址
   "officeRegionId":$('select[name="workCity"] option:selected').val(),//所在地
   "officeAddress":$("input[name='workAddress']").val(),//详细地址
   "orgPhone":$("input[name='tel']").val(),//联系电话
   "taxCode":$("input[name='taxRegCertificate']").val(),//税务登记编号
   "taxPath":$("input[name='taxPath']").val(),//税务登记存放路径
   "licenseCode":$("input[name='businessLicence']").val(),//营业执照编号
   "licensePath":$("input[name='licensePath']").val(),//营业执照存放路径
   "certificateCode":$("input[name='certificateCode']").val(),//信用代码
   "certifictePath":$("input[name='certifictePath']").val(),//企业代码存放路径
   "isSupplier":$("input[name='fruit']:checked").val()?$("input[name='fruit']:checked").val():"0"//是否成功供应商
   },
   type:"post",
   success : function(data) {
   if (data.code == 1) {
   window.location.href = './company_auth_init_step3.html';
   } else {
   alert(data.errMsg);
   }
   }
   });
}
// 验证插件开始
$("#companyForm").validate({
  rules: {
    governmentName: 'required',
    // creditCode:'required',
    // businessLicence:'required',
    // taxRegCertificate:'required',
    companyLegal: 'required',
    mainBusiness: 'required',
    // province:'required',
    city: 'required',
    address: 'required',
    // licensePath:'required',
    // taxPath:'required',
    // certifictePath:'required',
    tel: {
      required: true,
      isMobile: true
    }
  },
  messages: {
    governmentName: '单位名称不能为空',
    creditCode: '信用代码不能为空',
    businessLicence: '营业执照不能为空',
    taxRegCertificate: '不能为空',
    companyLegal: '法定代表人不能为空',
    mainBusiness: '主营业务不能为空',
    // province:'所在地区不能为空',
    city: '所在地区不能为空',
    address: '详细地址不能为空',
    licensePath: '营业执照必须上传',
    taxPath: '税务登记证必须上传',
    certifictePath: '企业代码证必须上传',
    tel: {
      required: '电话不能为空',
      isMobile: '电话格式不正确'
    }
  },
  showErrors: function (errorMap, errorList) {
    if (errorList.length) {
      errorList[0].element.focus();
    }
    this.defaultShowErrors();
  }
});
$.validator.addMethod("isMobile", function (value, element) {
  var reg = /(^\d{3,4}-)?\d{7,8}/;
  var tel = value;
  return this.optional(element) || (reg.test(tel));
}, "长度为10-60个字符（每个汉字为2个字符）");
// 验证插件结束
$('#verifyBtn').click(function () {
  if ($("#companyForm").valid()) {
    companyAuth();
  }
});

// 我要成为供应商点击事件
function supplier() {
  $(".supplier-info").hide();
  $(".inputBoxs .supplier").on("click", function () {
    if ($(this).is(":checked")) {
      $(".supplier-info").show()
    } else {
      $(".supplier-info").hide()
    }
  })
}

function showRegion(dataID,province,city) {// 返现的地域值
    var dataID=dataID?dataID:"";
    var province=province?province:"";
    var city=city?city:"";
    if(dataID && province && city && (typeof dataID == "string")&&(typeof province == "string")&&(typeof city == "string")){
        var reg=dataID.slice(0,2);
        $("#registerProvince option").each(function () {
            var val=$(this).val();
            if(val.indexOf(reg)==0){
                $("#"+province+" option[value="+val+"]").attr("selected","selected");
                loadRegion(city,val);
                $("#"+city+" option[value="+parseInt(dataID)+"]").attr("selected","selected");
            }
        })
    }

}
//认证修改跳转页面，反现值
if ($.getUrlParam("isAuth") == "3") {
  $.ajax({
    url: host.website + '/regInfo/verifiedInfo',
    data: {},
    cache:false,
    type: 'get',
    success: function (data) {
        $("input[name='governmentName']").val(data.data.organization.orgName?data.data.organization.orgName:"");//单位名称
        $("input[name='creditCode']").val(data.data.organization.creditCode?data.data.organization.creditCode:"");//统一社会信用代码
        $("input[name='mainBusiness']").val(data.data.organization.industry?data.data.organization.industry:"")//主营业务
        $("input[name='companyLegal']").val(data.data.organization.lawPersonName?data.data.organization.lawPersonName:"")//企业法人代表
        $("input[name='lawPersonNum']").val(data.data.organization.lawPersonNum?data.data.organization.lawPersonNum:"")//法定代表人证件编号
        $('#lawPersonPositivePath').attr({"src":host.static+'/' + data.data.organization.lawPersonPositivePath});//法定代表人证件照正
        $("input[name='lawPersonPositivePath']").val(data.data.organization.lawPersonPositivePath);//法定代表人证件照正
        $('#lawPersonNegativePath').attr({"src":host.static+'/' + data.data.organization.lawPersonNegativePath});//法定代表人证件照反
        $("input[name='lawPersonNegativePath']").val(data.data.organization.lawPersonNegativePath);//法定代表人证件照正

        //地域
        showRegion(data.data.organization.region,"registerProvince","registerCity");
        showRegion(data.data.organization.officeRegionId,"workProvince","workCity");
        $("input[name='address']").val(data.data.organization.contactAddress?data.data.organization.contactAddress:"");//详细地址
        $("input[name='workAddress']").val(data.data.organization.officeAddress?data.data.organization.officeAddress:"");//详细地址
        $("input[name='tel']").val(data.data.organization.orgPhone?data.data.organization.orgPhone:"")//联系电话

        //营业执照编号
        $("input[name='businessLicence']").val(data.data.organization.licenseCode?data.data.organization.licenseCode:"");

        $("#licensePath").attr({"src":host.static+'/' +data.data.organization.licensePath});//企业代码存放路径
        $("#licensePathSrc").val(data.data.organization.licensePath);//企业代码存放路径
        //税务
        $("input[name='taxRegCertificate']").val(data.data.organization.taxCode?data.data.organization.taxCode:"");
        $("#taxPath").attr({"src":host.static+'/' +data.data.organization.taxPath});
        $("#taxPathSrc").val(data.data.organization.taxPath);
        //组织代码
        $("input[name='certificateCode']").val(data.data.organization.certificateCode?data.data.organization.certificateCode:"");
        $("#certifictePath").attr({"src":host.static+'/' +data.data.organization.certifictePath});
        $("#certifictePathSrc").val(data.data.organization.certifictePath);

        // 我要成为供应商
        if(data.data.organization.isSupplier=="1"){
            $("input[name='fruit']").attr("checked","checked");
            $("input[name='fruit']").attr("disabled","disabled");
            $(".supplier-info").show()
        }
    }
  });
}

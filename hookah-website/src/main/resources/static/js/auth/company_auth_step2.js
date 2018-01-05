/**
 * Created by lss on 2017/5/8.
 * 认证接口： /auth/orgAuth
 * 由于各种原因，企业需要人工审核，
 * 所以在接口加了一个isCheck（0：自定完成，1：人工审核）字段 来判断是人工审核还是自定完成
 */
supplier();

$('#verifyBtn').click(function () {
    if ($("#companyForm").valid()) {
        $('#verifyBtn').attr("disabled",true);
        $('#verifyBtn').addClass("disabled-gray");

        companyAuth();
    }
});
function companyAuth() {
  $.ajax({
   url : "/auth/orgAuth",
   cache:false, //为了解决ie下的缓存问题，最后都带上
   data : {
       "orgName":$("input[name='governmentName']").val(),//单位名称
       "creditCode":$("input[name='creditCode']").val(),//统一社会信用代码
       "isSupplier":$("input[name='fruit']:checked").val()?$("input[name='fruit']:checked").val():"0"//是否成功供应商
   },
   type:"post",
   success : function(data) {
       if (data.code == 1) {
           console.log(data);
           $('#verifyBtn').attr("disabled",false);
           $('#verifyBtn').removeClass("disabled-gray");
           if(data.data.isCheck == "1"){
               window.location.href = './company_auth_init_step3.html';
           }else {
               window.location.href = './company_auth_init_step4.html';
           }
       } else {
          alert(data.message);
           $('#verifyBtn').attr("disabled",false);
           $('#verifyBtn').removeClass("disabled-gray");

       }
   }
   });
}
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
        // 我要成为供应商
        if(data.data.organization.isSupplier=="1"){
            $("input[name='fruit']").attr("checked","checked");
            $("input[name='fruit']").attr("disabled","disabled");
            $(".supplier-info").show()
        }
    }
  });
}
// 表格验证开始
var regex = {  //手机号验证正则
    mobile: /^0?(13[0-9]|14[5-9]|15[012356789]|66|17[0-9]|18[0-9]|19[8-9])[0-9]{8}$/,
    zip:/^[1-9][0-9]{5}$/,
    num:/^[0-9]*$/
};
$("#companyForm").validate({
    rules: {
        governmentName: {
            required:true,
            isNum:true
        },
        creditCode:'required',
    },
    messages: {
        governmentName:{
            required:"单位名称不能为空",
            isMobile:"*请输入正确格式的单位名称"
        },
        creditCode: '信用代码不能为空',
    }
});
$.validator.addMethod("isNum", function(value, element) {
    var mobile = regex.num.test(value);
    return this.optional(element) || (!mobile);
}, "*请填写有效的单位名称");
// 表格验证结束
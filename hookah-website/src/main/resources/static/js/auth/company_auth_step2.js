/**
 * Created by lss on 2017/5/8.
 */
supplier();

$('#verifyBtn').click(function () {
    if ($("#companyForm").valid()) {
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
          window.location.href = './company_auth_init_step4.html';
       } else {
          alert(data.message);
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
// 验证插件开始
$("#companyForm").validate({
    rules: {
        governmentName: 'required',
        creditCode:'required',
    },
    messages: {
        governmentName: '单位名称不能为空',
        creditCode: '信用代码不能为空',
    }
});
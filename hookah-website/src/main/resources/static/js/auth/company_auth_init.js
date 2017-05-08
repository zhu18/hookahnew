/**
 * Created by ki on 2017/4/15.
 */
//认证协议页面：点击下一步，跳转到company_auth_init_step2页面
$(function(){
    judge();
    function judge(){
        if(!$("#check").is(":checked")){
            $(".agreement-btn a").attr('href','javascript:;')
            $('.gray-btn2').css({
                "background-color":"#efefef",
                "cursor": "not-allowed"
            })
        }else{
            $('.gray-btn2').css({
                "background-color":"#0781e8",
                "cursor": "pointer"
            })
        }
    }
    $("#check").click(function(){
        if($("#check").is(":checked")){
            $(".gray-btn2").css({
                "background-color":"#0781e8",
                "cursor": "pointer"
            });
            $("#next").attr("href","./company_auth_init_step2");
        }else{
            judge();
        }

    })
    // 点击立即认证按钮，进行认证操作
    $("#verifyBtn").on("click",companyAuth());

});
//检查政府全称是否填写
function governmentName(){
    var governmentName = $("#governmentName").val();
    if(!governmentName){
        alert("请输入政府全称");
    }
}
//检查信用代码是否为空
function creditCode(){
    var creditCode = $("#creditCode").val();
    if(!creditCode){
        alert("请输入信用代码");
    }
}
//验证营业执照到期时间是否为空
function businessLicence(){
    var businessLicence = $("#businessLicence").val();
    if(!businessLicence){
        alert("请输入营业执照到期时间");
    }
}
//验证税务登记证到期时间是否为空
function taxRegCertificate(){
    var taxRegCertificate = $("#taxRegCertificate").val();
    if(!taxRegCertificate){
        alert("请输入税务登记证到期时间");
    }
}
//验证企业法人是否为空
function companyLegal(){
    var companyLegal = $("#companyLegal").val();
    if(!companyLegal){
        alert("请输入公司的法人代表");
    }
}
//验证主营业务是否为空
function mainBusiness(){
    var mainBusiness = $("#mainBusiness").val();
    if(!mainBusiness){
        alert("请输入您企业的主营业务");
    }
}
//验证详细地址是否为空
function address(){
    var address = $("#address").val();
    if(!address){
        alert("请输入企业的详细地址");
    }
}
function companyAuth(){

}



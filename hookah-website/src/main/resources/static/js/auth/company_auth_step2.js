/**
 * Created by wcq on 2017/5/8.
 */
$(function(){
    $("#verifyBtn").on("click",companyAuth);
    //验证政府全称是否填写
    $("#governmentName").on("blur",governmentName);
    //检查信用代码是否为空
    $("#creditCode").on("blur",creditCode);
    //验证营业执照到期时间是否为空
    $("#businessLicence").on("blur",businessLicence);
    //验证税务登记证到期时间是否为空
    $("#taxRegCertificate").on("blur",taxRegCertificate);
    //验证企业法人是否为空
    $("#companyLegal").on("blur",companyLegal);
    //验证主营业务是否为空
    $("#mainBusiness").on("blur",mainBusiness);
    //验证详细地址是否为空
    $("#address").on("blur",address);
    //验证联系电话是否为空
    $("#tel").on("blur",tel);
    //以下是上传图片
    $("#creditImg").on("click", upPhoto);
    $("#businessImg").on("click", upPhoto);
    $("#taxRegImg").on("click", upPhoto);

})
//检查政府全称是否填写
function governmentName(){
    var governmentName = $("#governmentName").val();
    if(!governmentName){
        swal("请输入政府全称");
    }
}
//检查信用代码是否为空
function creditCode(){
    var creditCode = $("#creditCode").val();
    if(!creditCode){
        swal("请输入信用代码");
    }
}
//验证营业执照到期时间是否为空
function businessLicence(){
    var businessLicence = $("#businessLicence").val();
    if(!businessLicence){
        swal("请输入营业执照到期时间");
    }
}
//验证税务登记证到期时间是否为空
function taxRegCertificate(){
    var taxRegCertificate = $("#taxRegCertificate").val();
    if(!taxRegCertificate){
        swal("请输入税务登记证到期时间");
    }
}
//验证企业法人是否为空
function companyLegal(){
    var companyLegal = $("#companyLegal").val();
    if(!companyLegal){
        swal("请输入公司的法人代表");
    }
}
//验证主营业务是否为空
function mainBusiness(){
    var mainBusiness = $("#mainBusiness").val();
    if(!mainBusiness){
        swal("请输入您企业的主营业务");
    }
}
//验证详细地址是否为空
function address(){
    var address = $("#address").val();
    if(!address){
        swal("请输入企业的详细地址");
    }
}
//验证联系电话是否为空
function tel(){
    var tel = $("#tel").val();
    if(!tel){
        swal("请输入联系电话");
    }
}
//上传图片
var imgSrc = '';
var fileUploadUrl = host.static+'/upload/fileUpload';
function upPhoto(){
    console.log($(this));
    $(this).find("input").fileupload({
        url: fileUploadUrl,
        dataType: 'json',
        done: function (e, data) {
            if (data.result.code == 1) {
                var obj = data.result.data[0];
                $(this).siblings("img").attr("src", obj.absPath);
                imgSrc = obj.absPath;
            } else {
                $.alert(data.result.message)
            }

        },
        progressall: function (e, data) {

        }
    })
}
function companyAuth(){

}

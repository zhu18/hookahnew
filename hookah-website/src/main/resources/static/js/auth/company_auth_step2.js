/**
 * Created by wcq on 2017/5/8.
 */
$(function(){
    //地域加载
    var regionParam = 100000;
    loadRegion('province', regionParam); //加载地域
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
    $("#verifyBtn").on("click",companyAuth);

});
//地域加载
function loadRegion(id,regionParam) {
    var parentId = '';
    if(regionParam == 100000){
        parentId = 100000;
    }else{
        parentId = $(regionParam).val();
    }
    $(regionParam).nextAll().html('<option value="-1"></option>')
    $.ajax({
        type: "get",
        url: host.website+'/region/getRegionCodeByPid',
        data: {
            parentId: parentId
        },
        success: function (data) {
            if (data.code == 1) {
                if(data.data.length > 0){
                    renderRegion(id,data.data)
                }
            } else {
                $.alert(data.message)
            }
        }
    });
}
function renderRegion(id,data){
    var html = '<option value="-1">全部</option>';
    data.forEach(function(e){
        html += '<option value="'+e.id+'">'+e.name+'</option>';
    });
    $('#'+id).html(html);
}
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
    var reg = /^1[3|4|5|7|8][0-9]{9}$/; //验证规则
    var flag = reg.test(tel); //true
    if(!tel){
        swal("请输入联系电话");
    }
    if(!flag){
        swal("请输入正确的联系电话");
    }
}

//上传图片
var imgSrc = '';
var fileUploadUrl = host.static+'/upload/fileUpload';
function upPhoto(){
    console.log(1);
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
    if($("#governmentName").val()==''){
        swal("请输入政府全称！");
        return;
    }
    if($("#creditCode").val()==''){
        swal("请输入信用代码！");
        return;
    }
    if($("#businessLicence").val()==''){
        swal("请输入营业执照到期时间！");
        return;
    }
    if($("#taxRegCertificate").val()==''){
        swal("请输入税务登记证到期时间！");
        return;
    }
    if( $("#companyLegal").val()==''){
        swal("请输入公司的法人代表！");
        return;
    }

    if( $("#mainBusiness").val()==""){
        swal("请输入您企业的主营业务！");
        return;
    }
    if( $("#address").val()==""){
        swal("请输入企业的详细地址！");
        return;
    }
    if($("#tel").val()==""){
        swal("请输入联系电话！");
        return;
    }
    $.ajax({
        url : "/auth/orgAuth",
        data : {
            "orgName":$("#governmentName").val(),//政府全称
            "taxCode":$("#taxCode").val(),//税务登记编号
            "licenseCode":$("#businessLicence").val(),//营业执照编号
            "certificateCode":$("#creditCode").val(),//信用代码
            "taxPath":$("#taxPath").attr("src"),//税务登记存放路径
            "licensePath":$("#licensePath").attr("src"),//营业执照存放路径
            "certifictePath":$("#certifictePath").attr("src"),//企业代码存放路径
            "lawPersonName":$("#companyLegal").val(),//企业法人代表
            "region":$('select[name="province"] option:selected').text()+$('select[name="city"] option:selected').text(),//所在地
            "contactAddress":$("#address").val(),//详细地址
            "orgPhone":$("#tel").val(),//联系电话
            "industry":$("#mainBusiness").val(),//行业
        },
        type:"post",
        success : function(data) {
            if (data.code == 1) {
                window.location.href = './company_auth_init_step4.html';
            } else {
                alert(data.errMsg);
            }
        }
    });
}


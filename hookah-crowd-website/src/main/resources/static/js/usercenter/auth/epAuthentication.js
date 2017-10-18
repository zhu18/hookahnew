/**
 * Created by lss on 2017/10/12 0012.
 */
$(function () {
    isAuthProvider();
    tagBax();
    reader();
});
$("#infoAdd").on('click',function () {
    $('.info-edit').show();
    $('.info-show').hide();
    $(this).hide()
});

$('#infoAddSave').on('click',function () {
    var tabList=[];
    $('.info .info-edit .tag-box span').each(function () {
        if($(this).hasClass('active')){
            tabList.push($(this).html())
        }
    });
    var data=JSON.stringify({
        "providerDesc":$('#providerDesc').val(),
        "specialSkills":tabList
    });
    $.ajax({
        type: 'POST',
        url: "/api/auth/optAuthInfo",
        dataType: 'json',
        contentType: 'application/json',
        data:data,
        async: false,
        success: function (data) {
            if(data.code=='1'){
                $('.info-edit').hide();
                $('.info-show').show();
                $("#infoAdd").show();
                reader();
            }
        }
    });

});

// 应用案例添加

$("#appCaseAdd").on('click',function () {
    var html='<dl> ' +
        '<form action="" id="form">' +
        ' <table id="education" class="from"> ' +
        '<tr> ' +
        '<td>' +
        ' <div> ' +
        '<label for="schoolName"><span class="color-red">*</span>学校名称</label> ' +
        '<input type="text" id="schoolName" name="schoolName" placeholder="请输入学校名称" required>' +
        ' </div> ' +
        '</td>' +
        ' <td> ' +
        '<div> ' +
        '<label for="major"><span class="color-red">*</span>专业名称</label> ' +
        '<input type="text" id="major" name="major" placeholder="请输入专业名称" required> </div> ' +
        '</td> ' +
        '</tr>' +
        ' <tr> ' +
        '<td> ' +
        '<div> <label for=""><span class="color-red">*</span>就学时间</label> ' +
        '<div class="display-inline-block"> <input id="startDate"  name="startDate" type="text" placeholder="请选择开始时间" readonly="" required >至 <input id="endDate"  name="endDate" type="text" placeholder="请选择结束时间" readonly="" required> </div> </div> </td> ' +
        '<td> <div> <label for="edu"><span class="color-red">*</span>学历</label> <select name="edu" id="edu" required > <option value="大专">大专</option> <option value="本科">本科</option> <option value="硕士">硕士</option> <option value="博士">博士</option> </select> </div> ' +
        '</td> </tr>' +
        ' <tr> <td> <span class="color-red">*</span> <input type="checkbox" name="orExam" value="n" id="orExam" required/>是否统招 </td> <td> <div><label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename" multiple="multiple" class="fileUploadBtn j_firstPage"> <span class="falseBen j_firstPage">上传附件</span> <span class="fileTip"></span> <input type="hidden" name="filename" value="" id="file"> </div> </div> </td> </tr>' +
        ' <tr> <td colspan="2"> <button class="btn btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="educationAddSave" data-sn="null">保存</button> </td>' +
        ' </tr> </table> </form> </dl>'

         $(".content-appCase").append(html);
         $("#educationAddSave").on('click',educationAddSave);
         $("#educationAdd").hide();
         jeDate();
         fileUpload();
         validate();
});
// $("#workAdd").on('click',function () {
//     var html='<dl> <form action="" id="form"> ' +
//         '<table id="work" class="from"> ' +
//         '<tr> ' +
//         '<td> ' +
//         '<div> ' +
//         '<label for="companyName"><span class="color-red">*</span>公司名称</label> ' +
//         '<input type="text" id="companyName" name="companyName" placeholder="请输入公司名称" required> ' +
//         '</div> </td>' +
//         ' <td> <div> ' +
//         '<label for="departName"><span class="color-red">*</span>部门名称</label>' +
//         ' <input type="text" id="departName" name="departName" placeholder="请输入部门名称" required> </div> </td> </tr>' +
//         ' <tr> <td> <div>' +
//         ' <label for=""><span class="color-red">*</span>工作时间</label> ' +
//         '<div class="display-inline-block"> <input id="startDate"  name="startDate" type="text" placeholder="请选择开始时间" readonly="" required >至 <input id="endDate"  name="endDate" type="text" placeholder="请选择结束时间" readonly="" required> </div> </div> </td>' +
//         ' <td> <div>' +
//         ' <label for="position"><span class="color-red">*</span>职位</label> ' +
//         '<input type="text" name="position" id="position"> </div> </td> </tr> ' +
//         '<tr> <td> <div>' +
//         '<span class="color-red">*</span>'+
//         '<label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename" multiple="multiple" class="fileUploadBtn j_firstPage"> <span class="falseBen j_firstPage">上传附件</span> <span class="fileTip"></span> <input type="hidden" name="filename" value="" id="file"> </div> </div> </td> </tr> ' +
//         '<tr> <td colspan="2"> ' +
//         '<button class="btn btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="workAddSave" data-sn="null">保存</button> </td> </tr> </table> </form> </dl>'
//     $(".pspAuthentication-work-content").append(html);
//     $("#workAddSave").on('click',workAddSave);
//     $("#workAdd").hide();
//     jeDate();
//     fileUpload();
//     validate();
// });
// $("#projectAdd").on('click',function () {
//     var html='<dl>' +
//         '<form action="" id="form">' +
//         '<table id="project" class="from"> ' +
//         '<tr> ' +
//         '<td> ' +
//         '<div> ' +
//         '<label for="projectName"><span class="color-red">*</span>项目名称</label> ' +
//         '<input type="text" id="projectName" name="projectName"placeholder="请输入项目名称" required> </div> </td>' +
//         '<td> <div>' +
//         '<label for="projectDuty"><span class="color-red">*</span>项目职责</label> ' +
//         '<input type="text" id="projectDuty" name="projectDuty" placeholder="请输入项目职责"required> </div> </td>' +
//         ' </tr> <tr> <td colspan="2"> <div> ' +
//         '<label for=""><span class="color-red">*</span>项目时间</label> ' +
//         '<div class="display-inline-block"> <input id="startDate" name="startDate" type="text"placeholder="请选择开始时间" readonly="" required>至 <input id="endDate" name="endDate" type="text"placeholder="请选择结束时间" readonly="" required> </div> </div> </td> </tr>' +
//         ' <tr> <td colspan="2"> ' +
//         '<label for="projectDesc">项目描述</label> ' +
//         '<textarea name="projectDesc" id="projectDesc" cols="100" rows="10"></textarea> </td> </tr> <tr> ' +
//         '<td colspan="2"> <button class="btn btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="projectAddSave" data-sn="null"' +
//         '>保存 </button> </td> </tr> </table> </form> </dl>';
//     $(".pspAuthentication-project-content").append(html);
//     $("#projectAddSave").on('click',projectAddSave);
//     $("#projectAdd").hide();
//     jeDate();
//     fileUpload();
//     validate();
// });
//删除
function itemDelete(r,optAuthType) {
    console.log("删除");
    console.log(r);
    console.log(optAuthType);
    $.confirm('确定要删除该项吗？', null, function (type) {
        if (type == 'yes') {
            $.ajax({
                type: 'post',
                url: "/api/auth/optAuthInfo",
                dataType: 'json',
                contentType: 'application/json',
                data:JSON.stringify({
                    optArrAySn:r.currentTarget.attributes[2].nodeValue,
                    optType: "2",
                    optAuthType: optAuthType
                }),
                success: function (data) {
                    console.log(data);
                    if(data.code==1){

                        r.target.parentNode.parentNode.parentNode.parentNode.remove();
                        reader();
                        $.alert("删除成功！")
                    }
                }
            });
            this.hide();
        } else {
            this.hide();
        }
    });
}
//保存按钮
function addSave(data) {
    // var sn=$(r)[0].target.attributes[2].nodeValue;
    if($("#form").valid()){
        $.ajax({
            type: 'post',
            url: "/api/auth/optAuthInfo",
            dataType: 'json',
            contentType: 'application/json',
            data:data.data,
            success: function (data) {
                if(data.code==1){
                    $(data.dom).parent().parent().remove();
                    reader();
                }else {
                    $.alert(data.message);
                }
            }
        });
    }else {

    }


};

function reader() {
    $.ajax({
        type: 'get',
        url: "/api/auth/getAuthInfo",
        async: false,
        success: function (data) {
            console.log(data);
            if(data.code==1){
                var specialSkills=data.data.specialSkills;
                var educationsExpList=data.data.educationsExpList;
                var worksExpList=data.data.worksExpList;
                var projectsExpList=data.data.projectsExpList;
                var appCaseList=data.data.appCaseList;
                var swpList=data.data.swpList;
                var inPatentsList=data.data.inPatentsList;

                $('.providerDesc').val(data.data.providerDesc);
                $('.providerDesc').html(data.data.providerDesc);
                if(specialSkills){
                    var html='';
                    for (var i=0,len=specialSkills.length;i<len;i++){
                        html +='<span class="active">'+specialSkills[i]+'</span>'
                    }
                    $('.info .info-show .tag-box').html(html);
                }



            }
        }
    });
}


//获得时间戳
function time() {
    var data=new Date();
    return data.getTime()
}

function formatDate(d){
    var   data=new Date();
    var   now=new Date(data.getTime(d));
    var   year=now.getFullYear();
    var   month=now.getMonth()+1;
    return year+"/"+month;
}

// 日历插件开始
function jeDate() {
    var start = {
        format: "YYYY-MM-DD hh:mm:ss",
        isTime: true,
        maxDate: $.nowDate(0),
        choosefun: function (elem, datas) { //日历选择完毕回调函数
            end.minDate = datas; //开始日选好后，重置结束日的最小日期
        }
    };
    var end = {
        format: "YYYY-MM-DD hh:mm:ss",
        isTime: true,
        maxDate: $.nowDate(0),
        choosefun: function (elem, datas) {
            start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
        }
    };
    $.jeDate("#startDate", start);
    $.jeDate("#endDate", end);
}

// 日历插件结束
function fileUpload() {
    $('.fileUploadBtn').fileupload(
        {
            url: host.static + '/upload/other',
            dataType: 'json',
            maxFileSize: 10240000,
            add: function (e, data) {
                // var filesize = data.files[0].size;
                // if(Math.ceil(filesize / 1024) > 1024*10){
                //     console.log('文件过大'+filesize);
                //     $.alert('附件大小不得超过10M！');
                //     return;
                // }
                // data.submit();
            },
            done: function (e, data) {
                console.log('上传完毕');
                var obj = data.result.data[0];
                $('input[type="hidden"]').val(obj.filePath);
                $('.fileTip').html(data.files[0].name);
                console.log(data);
                console.log(obj.filePath);
                console.log(data.files[0].name);

            },
            progressall: function (e, data) {
            }
        });
}
function validate() {
    // 表格验证开始
    var regex = {  //手机号验证正则
        mobile: /^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/,
        zip:/^[1-9][0-9]{5}$/,
        bank: /^\d{16}|\d{19}$/
    };
//让当前表单调用validate方法，实现表单验证功能
    $("#form").validate({
        debug:true, //调试模式，即使验证成功也不会跳转到目标页面
        rules:{     //配置验证规则，key就是被验证的dom对象，value就是调用验证的方法(也是json格式)
            schoolName:{  //学校名称
                required:true,  //必填。如果验证方法不需要参数，则配置为true
                rangelength:[2,12]
            },
            major:{ //专业名称
                required:true
            },
            edu:{ //学历
                required:true
            },
            orExam:{//是否统招
                required:true
            },
            startDate:{
                required:true,

            },
            endDate:{
                required:true,
                isVerificationCode:true
            }
        },
        messages:{
            schoolName:{
                required:"*请输入学校名称",
                rangelength:$.validator.format("*用户名长度为{0}-{1}个字符")
            },
            major:{
                required:"*请输入专业名称"
            },
            edu:{
                required:"*请输入您的学历!"
            },
            orExam:{
                required:"*请选择是否统招!"
            },
            startDate:{
                required:"*请输入银行账户"
            },
            endDate:{
                required:"*请选择银行名称"
            }
        }
    });
// $.validator.addMethod("isMobile", function(value, element) {
//     var mobile = regex.mobile.test(value);
//     return this.optional(element) || (mobile);
// }, "*请填写有效的手机号");
}
// 标签渲染
function tagBax() {
    $.ajax({
        type: 'get',
        url: "/zbType/requirementsType",
        async: false,
        success: function (data) {
            if(data.code=='1'){
                var list=data.data;
                var html='';
                for (var i=0,len=list.length;i<len;i++){
                    var item=list[i];
                    html +='<span>'+item.typeName+'</span>'
                }
                $('.info .info-edit .tag-box ').html(html);
                $('.info .info-edit .tag-box  span').on('click',function () {
                    if ($(this).hasClass('active')){
                        $(this).removeClass('active')
                    }else {
                        $(this).addClass('active')
                    }
                })
            }
            console.log(data);

        }
    });
}
// 判断是不是认证
function isAuthProvider() {
    $.ajax({
        type: 'get',
        url: "/api/isAuthProvider",
        async: false,
        success: function (data) {
            console.log(data);
            $('#AuthProvider').html(data?'已认证':'未认证');

        }
    });
}

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
        "authType": "1",
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
//六个模块添加按钮
// 添加教育经历
$("#educationAdd").on('click',function () {
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
        '<div> <label for="">就学时间</label> ' +
        '<div class="display-inline-block"> <input id="startDate"  name="startDate" type="text" placeholder="请选择开始时间" readonly=""  >至 <input id="endDate"  name="endDate" type="text" placeholder="请选择结束时间" readonly="" > </div> </div> </td> ' +
        '<td> <div> <label for="edu">学历</label> <select name="edu" id="edu"  > <option value="大专">大专</option> <option value="本科">本科</option> <option value="硕士">硕士</option> <option value="博士">博士</option> </select> </div> ' +
        '</td> </tr>' +
        ' <tr> <td> <input type="checkbox" name="orExam" value="n" id="orExam" />是否统招 </td> <td> <div><label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename"  class="fileUploadBtn j_firstPage"> <span class="falseBen j_firstPage">上传附件</span> <span class="fileTip"></span> <input type="hidden" name="filename" value="" id="file"> </div> </div> </td> </tr>' +
        ' <tr> <td colspan="2"> <button class="btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="educationAddSave" data-sn="null">保存</button> </td>' +
        ' </tr> </table> </form> </dl>'

         $(".content-education").append(html);
         $('.content-education-noData').hide();
         $("#educationAddSave").on('click',function (r) {
             var data=JSON.stringify({
                 educationsExpList:[{
                     "sn": "SN_1_"+time(),
                     "schoolName": $("#schoolName").val(),
                     "major": $("#major").val(),
                     "startTime": $("#startDate").val() || null,
                     "endTime": $("#endDate").val() || null,
                     "edu": $('#edu').val() || "",
                     "orExam": $('#orExam').is(':checked')?"1":"0",
                     "certPathsList":[
                         {
                             "certName":$('.fileTip').html(),
                             "certPath":$('input[type="hidden"]').val()
                         }
                     ]
                 }]
             });
             addSave({
                 data:data,
                 dom:'#education'
             });
         });
         $("#educationAdd").hide();
         $('.content-education-noData').hide();
         jeDate();
         fileUpload();
         validate();
});
$("#workAdd").on('click',function () {
    var html='<dl> <form action="" id="form"> ' +
        '<table id="work" class="from"> ' +
        '<tr> ' +
        '<td> ' +
        '<div> ' +
        '<label for="companyName"><span class="color-red">*</span>公司名称</label> ' +
        '<input type="text" id="companyName" name="companyName" placeholder="请输入公司名称" required> ' +
        '</div> </td>' +
        ' <td> <div> ' +
        '<label for="departName"><span class="color-red">*</span>部门名称</label>' +
        ' <input type="text" id="departName" name="departName" placeholder="请输入部门名称" required> </div> </td> </tr>' +
        ' <tr> <td> <div>' +
        ' <label for="">工作时间</label> ' +
        '<div class="display-inline-block"> <input id="startDate"  name="startDate" type="text" placeholder="请选择开始时间" readonly="" required >至 <input id="endDate"  name="endDate" type="text" placeholder="请选择结束时间" readonly="" required> </div> </div> </td>' +
        ' <td> <div>' +
        ' <label for="position">职位</label> ' +
        '<input type="text" name="position" id="position"> </div> </td> </tr> ' +
        '<tr> <td> <div>' +
        '<label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename"  class="fileUploadBtn j_firstPage"> <span class="falseBen j_firstPage">上传附件</span> <span class="fileTip"></span> <input type="hidden" name="filename" value="" id="file"> </div> </div> </td> </tr> ' +
        '<tr> <td colspan="2"> ' +
        '<button class="btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="workAddSave" data-sn="null">保存</button> </td> </tr> </table> </form> </dl>'
    $(".content-work").append(html);
    $('.content-work-noData').hide();
    $("#workAddSave").on('click',function () {
        var data=JSON.stringify({
            worksExpList:[{
                "sn": "SN_2_"+time(),
                "companyName": $("#companyName").val(),
                "departName": $("#departName").val(),
                "startTime": $("#startDate").val() || null,
                "endTime": $("#endDate").val() || null,
                "position": $('#position').val() || "",
                "certPathsList":[
                    {
                        "certName":$('.fileTip').html(),
                        "certPath":$('input[type="hidden"]').val()
                    }
                ]
            }]
        });
        addSave({
            data:data,
            dom:'#work'
        })
    });
    $("#workAdd").hide();
    jeDate();
    fileUpload();
    validate();
});
$("#projectAdd").on('click',function () {
    var html='<dl>' +
        '<form action="" id="form">' +
        '<table id="project" class="from"> ' +
        '<tr> ' +
        '<td> ' +
        '<div> ' +
        '<label for="projectName"><span class="color-red">*</span>项目名称</label> ' +
        '<input type="text" id="projectName" name="projectName"placeholder="请输入项目名称" required> </div> </td>' +
        '<td> <div>' +
        '<label for="projectDuty"><span class="color-red">*</span>项目职责</label> ' +
        '<input type="text" id="projectDuty" name="projectDuty" placeholder="请输入项目职责"required> </div> </td>' +
        ' </tr> <tr> <td colspan="2"> <div> ' +
        '<label for="">项目时间</label> ' +
        '<div class="display-inline-block"> <input id="startDate" name="startDate" type="text"placeholder="请选择开始时间" readonly="" required>至 <input id="endDate" name="endDate" type="text"placeholder="请选择结束时间" readonly="" required> </div> </div> </td> </tr>' +
        ' <tr> <td colspan="2"> ' +
        '<label for="projectDesc">项目描述</label> ' +
        '<textarea name="projectDesc" id="projectDesc" cols="100" rows="10"></textarea> </td> </tr> <tr> ' +
        '<td colspan="2"> <button class="btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="projectAddSave" data-sn="null"' +
        '>保存 </button> </td> </tr> </table> </form> </dl>';
    $(".content-project").append(html);
    $('.content-project-noData').hide();
    $("#projectAddSave").on('click',function () {
        var data=JSON.stringify({
            projectsExpList:[{
                "sn": "SN_3_"+time(),
                "projectName": $("#projectName").val(),
                "projectDuty": $("#projectDuty").val(),
                "startTime": $("#startDate").val() || null,
                "endTime": $("#endDate").val() || null,
                "projectDesc": $('#projectDesc').val() || ""
            }]
        })
        addSave({
            data:data,
            dom:'#project'
        })
    });
    $("#projectAdd").hide();
    jeDate();
    fileUpload();
    validate();
});
$("#appCaseAdd").on('click',function () {
    var html='<dl>' +
        '<form action="" id="form">' +
        '<table id="appCase" class="from"> ' +
        '<tr> ' +
        '<td> ' +
        '<div> ' +
        '<label for="caseName"><span class="color-red">*</span>案例名称</label> ' +
        '<input type="text" id="caseName" name="caseName" placeholder="请输入案例名称" required> </div> </td>' +
        '<td> <div>' +
        '<label for="caseView"><span class="color-red">*</span>案例概述</label> ' +
        '<input type="text" id="caseView" name="caseView" placeholder="请输入案例概述"required> </div> </td>' +
        ' </tr> <tr> ' +
        '<td> <div> ' +
        '<label for=""><span class="color-red">*</span>项目时间</label> ' +
        '<div class="display-inline-block"> <input id="startDate" name="startDate" type="text"placeholder="请选择开始时间" readonly="" required>至 <input id="endDate" name="endDate" type="text"placeholder="请选择结束时间" readonly="" required> </div> </div> </td> ' +
        '<td> <div><label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename"  class="fileUploadBtn j_firstPage"> <span class="falseBen j_firstPage">上传附件</span> <span class="fileTip"></span> <input type="hidden" name="filename" value="" id="file"> </div> </div> </td></tr>' +
        ' <tr> <td colspan="2"> ' +
        '<label for="caseDesc">解决方案</label> ' +
        '<textarea name="caseDesc" id="caseDesc" cols="100" rows="10"></textarea> </td> </tr> <tr> ' +
        '<td colspan="2"> <button class="btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="appCaseAddSave" data-sn="null"' +
        '>保存 </button> </td> </tr> </table> </form> </dl>';

    $(".content-appCase").append(html);
    $('.content-appCase-noData').hide();
    $("#appCaseAddSave").on('click',function () {
        var data=JSON.stringify({
            appCaseList:[{
                "sn": "SN_4_"+time(),
                "caseName": $("#caseName").val(),
                "caseView": $("#caseView").val(),
                "startTime": $("#startDate").val() || null,
                "endTime": $("#endDate").val() || null,
                "caseDesc": $('#caseDesc').val() || "",
                "certPathsList":[
                    {
                        "certName":$('.fileTip').html(),
                        "certPath":$('input[type="hidden"]').val()
                    }
                ]
            }]
        })
        addSave({
            data:data,
            dom:'#appCase'
        })
    });
    $("#appCaseAdd").hide();
    jeDate();
    fileUpload();
    validate();
});
$("#swpAdd").on('click',function () {
    var html='<dl>' +
        '<form action="" id="form">' +
        '<table id="swp" class="from"> ' +
        '<tr>' +
        '<td>' +
        '<div>' +
        '<label for="softWareName"><span class="color-red">*</span>软件名称</label> ' +
        '<input type="text" id="softWareName" name="softWareName" placeholder="请输入软件名称" required> </div> </td>' +
        '<td><div>' +
        '<label for="registerNum"><span class="color-red">*</span>登记号</label> ' +
        '<input type="text" id="registerNum" name="registerNum" placeholder="请输入登记号" required> </div> </td>' +
        ' </tr> <tr> ' +
        '<td> <div> ' +
        '<label for=""><span class="color-red">*</span>首次发表日期</label> ' +
        '<div class="display-inline-block"> <input id="startDate" name="startDate" type="text"placeholder="请选择首次发表日期" readonly="" required></div> </div> </td> ' +
        '<td> <div><label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename"  class="fileUploadBtn j_firstPage"> <span class="falseBen j_firstPage">上传附件</span> <span class="fileTip"></span> <input type="hidden" name="filename" value="" id="file"> </div> </div> </td></tr>' +
        ' <tr> <td colspan="2"> ' +
        '<label for="purpose">软件用途</label> ' +
        '<textarea name="purpose" id="purpose" cols="100" rows="10"></textarea> </td> </tr> <tr> ' +
        '<td colspan="2"> <button class="btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="swpAddSave" data-sn="null"' +
        '>保存 </button> </td> </tr> </table> </form> </dl>';

    $(".content-swp").append(html);
    $('.content-swp-noData').hide();
    $("#swpAddSave").on('click',function () {
        var data=JSON.stringify({
            swpList:[{
                "sn": "SN_5_"+time(),
                "softWareName": $("#softWareName").val(),
                "registerNum": $("#registerNum").val(),
                "publicTime": $("#startDate").val() || null,
                "purpose": $('#purpose').val() || "",
                "certPathsList":[
                    {
                        "certName":$('.fileTip').html(),
                        "certPath":$('input[type="hidden"]').val()
                    }
                ]
            }]
        })
        addSave({
            data:data,
            dom:'#swp'
        })
    });
    $("#swpCaseAdd").hide();
    jeDate();
    fileUpload();
    validate();
});
$("#inPatentsAdd").on('click',function () {
    var html='<dl>' +
        '<form action="" id="form">' +
        '<table id="inPatents" class="from"> ' +
        '<tr>' +
        '<td>' +
        '<div>' +
        '<label for="patentName"><span class="color-red">*</span>发明名称</label> ' +
        '<input type="text" id="patentName" name="patentName" placeholder="请输入软件名称" required> </div> </td>' +
        '<td><div>' +
        '<label for="PatentNum"><span class="color-red">*</span>专利号</label> ' +
        '<input type="text" id="PatentNum" name="PatentNum" placeholder="请输入登记号" required> </div> </td>' +
        ' </tr> <tr> ' +
        '<td> <div> ' +
        '<label for=""><span class="color-red">*</span>申请日期</label> ' +
        '<div class="display-inline-block"> <input id="startDate" name="startDate" type="text" placeholder="请选择首次发表日期" readonly="" required></div> </div> </td> ' +
        '<td> <div><label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename"  class="fileUploadBtn j_firstPage"> <span class="falseBen j_firstPage">上传附件</span> <span class="fileTip"></span> <input type="hidden" name="filename" value="" id="file"> </div> </div> </td></tr>' +
        ' <tr> <td colspan="2"> ' +
        '<label for="PatentDesc">专利概述</label> ' +
        '<textarea name="PatentDesc" id="PatentDesc" cols="100" rows="10"></textarea> </td> </tr> <tr> ' +
        '<td colspan="2"> <button class="btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="inPatentsAddSave" data-sn="null"' +
        '>保存 </button> </td> </tr> </table> </form> </dl>';

    $(".content-inPatents").append(html);
    $('.content-inPatents-noData').hide();
    $("#inPatentsAddSave").on('click',function () {
        var data=JSON.stringify({
            inPatentsList:[{
                "sn": "SN_6_"+time(),
                "patentName": $("#patentName").val(),
                "patentNum": $("#PatentNum").val(),
                "applyTime": $("#startDate").val()  ,
                "patentDesc": $('#PatentDesc').val() ||"",
                "certPathsList":[
                    {
                        "certName":$('.fileTip').html(),
                        "certPath":$('input[type="hidden"]').val()
                    }
                ]
            }]
        })
        addSave({
            data:data,
            dom:'#inPatents'
        })
    });
    $("#inPatentsAdd").hide();
    jeDate();
    fileUpload();
    validate();
});
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

//教育经历单个修改
function educationEdit(r) {
    console.log("修改");
    var _this=$(this);
    $.ajax({
        type: 'get',
        url: "/api/auth/getAuthInfo",
        data:{
            optArrAySn:r.currentTarget.attributes[2].nodeValue,
            optAuthType: "1"
        },
        success: function (data) {
            console.log(data);
            if(data.code==1){
                var html= '<form action="" id="form">' +
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
                    '<td> <div> <label for="edu"><span class="color-red">*</span>学历</label> <select name="edu" id="edu" required > <option value="1">大专</option> <option value="2">本科</option> <option value="3">硕士</option> <option value="4">博士</option> </select> </div> ' +
                    '</td> </tr>' +
                    ' <tr> <td> <span class="color-red">*</span> <input type="checkbox" name="orExam" value="n" id="orExam" required/>是否统招 </td> <td> <div><label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename" class="fileUploadBtn j_firstPage"> <span class="falseBen j_firstPage">上传附件</span> <span class="fileTip"></span> <input type="hidden" name="filename" value="" id="file"> </div> </div> </td> </tr>' +
                    ' <tr> <td colspan="2"> <button class=" btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="educationAddSave" data-sn="'+r.currentTarget.attributes[2].nodeValue+'">保存</button> </td>' +
                    ' </tr> </table> </form>'
                // var index =r.currentTarget.parentNode.parentNode.parentNode.rowIndex;
                // $(".education tr").eq(index).html(html);
                _this.parent().parent().parent().html(html);

                $('#schoolName').val(data.data.schoolName);
                $('#major').val(data.data.major);
                $('#startDate').val(data.data.startTime);
                $('#endDate').val(data.data.endTime);
                $('#orExam').attr("checked",data.data.orExam==0?false:true);
                $('#edu').val(data.data.edu);
                $('.fileTip').html(data.data.certPathsList[0].certName);
                $('#file').val(data.data.certPathsList[0].certPath);
                console.log($('#educationAddSave').attr('data-sn'));

                $("#educationAddSave").on('click',function () {
                    var data=JSON.stringify({
                        optArrAySn:$('#educationAddSave').attr('data-sn'),
                        optType: "3",
                        optAuthType: "1",
                        educationsExpList:[{
                            "sn":$('#educationAddSave').attr('data-sn'),
                            "schoolName": $("#schoolName").val(),
                            "major": $("#major").val(),
                            "startTime": $("#startDate").val() || null,
                            "endTime": $("#endDate").val() || null,
                            "edu": $('#edu').val() || null,
                            "orExam": $('#orExam').is(':checked')?"1":"0",
                            "certPathsList":[
                                {
                                    "certName":$('.fileTip').html(),
                                    "certPath":$('input[type="hidden"]').val()
                                }
                            ]

                        }]
                    });
                    addSave({
                        data:data,
                        dom:'#education'
                    });
                });
                $("#educationAdd").hide();
                jeDate();
                fileUpload();
                validate();
            }
        }
    });

}
function workEdit(r) {
    var _this=$(this);
    $.ajax({
        type: 'get',
        url: "/api/auth/getAuthInfo",
        data:{
            optArrAySn:r.currentTarget.attributes[2].nodeValue,
            optAuthType: "2"
        },
        success: function (data) {
            console.log();
            if(data.code==1){
                var html='<dl> <form action="" id="form"> ' +
                    '<table id="work" class="from"> ' +
                    '<tr> ' +
                    '<td> ' +
                    '<div> ' +
                    '<label for="companyName"><span class="color-red">*</span>公司名称</label> ' +
                    '<input type="text" id="companyName" name="companyName" placeholder="请输入公司名称" required> ' +
                    '</div> </td>' +
                    ' <td> <div> ' +
                    '<label for="departName"><span class="color-red">*</span>部门名称</label>' +
                    ' <input type="text" id="departName" name="departName" placeholder="请输入部门名称" required> </div> </td> </tr>' +
                    ' <tr> <td> <div>' +
                    ' <label for=""><span class="color-red">*</span>工作时间</label> ' +
                    '<div class="display-inline-block"> <input id="startDate"  name="startDate" type="text" placeholder="请选择开始时间" readonly="" required >至 <input id="endDate"  name="endDate" type="text" placeholder="请选择结束时间" readonly="" required> </div> </div> </td>' +
                    ' <td> <div>' +
                    ' <label for="position"><span class="color-red">*</span>职位</label> ' +
                    '<input type="text" name="position" id="position"> </div> </td> </tr> ' +
                    '<tr> <td> <div>' +
                    '<span class="color-red">*</span>'+
                    '<label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename"  class="fileUploadBtn j_firstPage"> <span class="falseBen j_firstPage">上传附件</span> <span class="fileTip"></span> <input type="hidden" name="filename" value="" id="file"> </div> </div> </td> </tr> ' +
                    '<tr> <td colspan="2"> ' +
                    '<button class="btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="workAddSave" data-sn="'+r.currentTarget.attributes[2].nodeValue+'">保存</button> </td> </tr> </table> </form> </dl>'
                // var index =r.currentTarget.parentNode.parentNode.parentNode.rowIndex;
                // $(".education tr").eq(index).html(html);
                _this.parent().parent().parent().html(html);

                $('#companyName').val(data.data.companyName);
                $('#departName').val(data.data.departName);
                $('#startDate').val(data.data.startTime);
                $('#endDate').val(data.data.endTime);
                $('#position').val(data.data.position);
                $('.fileTip').html(data.data.certPathsList[0].certName);
                $('#file').val(data.data.certPathsList[0].certPath);

                $("#workAddSave").on('click',function () {
                    var data=JSON.stringify({
                        optArrAySn:$('#workAddSave').attr('data-sn'),
                        optType: "3",
                        optAuthType: "2",
                        worksExpList:[{
                            "sn":$('#workAddSave').attr('data-sn'),
                            "companyName": $("#companyName").val(),
                            "departName": $("#departName").val(),
                            "startTime": $("#startDate").val() || null,
                            "endTime": $("#endDate").val() || null,
                            "position": $('#position').val() || null,
                            "certPathsList":[
                                {
                                    "certName":$('.fileTip').html(),
                                    "certPath":$('input[type="hidden"]').val()
                                }
                            ]
                        }]
                    })
                    addSave({
                        data:data,
                        dom:'#work'
                    });
                });
                $("#workAdd").hide();
                jeDate();
                fileUpload();
                validate();
            }
        }
    });

}
function projectEdit(r) {
    console.log("修改");
    var _this=$(this);
    $.ajax({
        type: 'get',
        url: "/api/auth/getAuthInfo",
        data:{
            optArrAySn:r.currentTarget.attributes[2].nodeValue,
            optAuthType: "3"
        },
        success: function (data) {
            console.log();
            if(data.code==1){
                // var index =r.currentTarget.parentNode.parentNode.parentNode.rowIndex;
                // $(".education tr").eq(index).html(html);

                var html='<dl>' +
                    '<form action="" id="form">' +
                    '<table id="project" class="from"> ' +
                    '<tr> ' +
                    '<td> ' +
                    '<div> ' +
                    '<label for="projectName"><span class="color-red">*</span>项目名称</label> ' +
                    '<input type="text" id="projectName" name="projectName"placeholder="请输入项目名称" required> </div> </td>' +
                    '<td> <div>' +
                    '<label for="projectDuty"><span class="color-red">*</span>项目职责</label> ' +
                    '<input type="text" id="projectDuty" name="projectDuty" placeholder="请输入项目职责"required> </div> </td>' +
                    ' </tr> <tr> <td colspan="2"> <div> ' +
                    '<label for=""><span class="color-red">*</span>项目时间</label> ' +
                    '<div class="display-inline-block"> <input id="startDate" name="startDate" type="text"placeholder="请选择开始时间" readonly="" required>至 <input id="endDate" name="endDate" type="text"placeholder="请选择结束时间" readonly="" required> </div> </div> </td> </tr>' +
                    ' <tr> <td colspan="2"> ' +
                    '<label for="projectDesc">项目描述</label> ' +
                    '<textarea name="projectDesc" id="projectDesc" cols="100" rows="10"></textarea> </td> </tr> <tr> ' +
                    '<td colspan="2"> <button class="btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="projectAddSave" data-sn="'+r.currentTarget.attributes[2].nodeValue+'" >保存 </button> </td> </tr> </table> </form> </dl>';
                _this.parent().parent().parent().html(html);

                $('#projectName').val(data.data.projectName);
                $('#projectDuty').val(data.data.projectDuty);
                $('#startDate').val(data.data.startTime);
                $('#endDate').val(data.data.endTime);
                $('#projectDesc').val(data.data.projectDesc);

                $("#projectAddSave").on('click',function () {
                    var data=JSON.stringify({
                        optArrAySn:$('#projectAddSave').attr('data-sn'),
                        optType: "3",
                        optAuthType: "3",
                        projectsExpList:[{
                            "sn":$('#projectAddSave').attr('data-sn'),
                            "projectName": $("#projectName").val(),
                            "projectDuty": $("#projectDuty").val(),
                            "startTime": $("#startDate").val() || null,
                            "endTime": $("#endDate").val() || null,
                            "projectDesc": $('#projectDesc').val() || null
                        }]
                    })
                    addSave({
                        data:data,
                        dom:'#project'
                    })
                });
                $("#projectAdd").hide();
                jeDate();
                fileUpload();
                validate();
            }
        }
    });

}
function appCaseEdit(r) {
    console.log("修改");
    var _this=$(this);
    $.ajax({
        type: 'get',
        url: "/api/auth/getAuthInfo",
        data:{
            optArrAySn:r.currentTarget.attributes[2].nodeValue,
            optAuthType: "4"
        },
        success: function (data) {
            console.log();
            if(data.code==1){
                // var index =r.currentTarget.parentNode.parentNode.parentNode.rowIndex;
                // $(".education tr").eq(index).html(html);
                var html='<dl>' +
                    '<form action="" id="form">' +
                    '<table id="appCase" class="from"> ' +
                    '<tr> ' +
                    '<td> ' +
                    '<div> ' +
                    '<label for="caseName"><span class="color-red">*</span>案例名称</label> ' +
                    '<input type="text" id="caseName" name="caseName" placeholder="请输入案例名称" required> </div> </td>' +
                    '<td> <div>' +
                    '<label for="caseView"><span class="color-red">*</span>案例概述</label> ' +
                    '<input type="text" id="caseView" name="caseView" placeholder="请输入案例概述"required> </div> </td>' +
                    ' </tr> <tr> ' +
                    '<td> <div> ' +
                    '<label for=""><span class="color-red">*</span>项目时间</label> ' +
                    '<div class="display-inline-block"> <input id="startDate" name="startDate" type="text"placeholder="请选择开始时间" readonly="" required>至 <input id="endDate" name="endDate" type="text"placeholder="请选择结束时间" readonly="" required> </div> </div> </td> ' +
                    '<td> <div><label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename"  class="fileUploadBtn j_firstPage"> <span class="falseBen j_firstPage">上传附件</span> <span class="fileTip"></span> <input type="hidden" name="filename" value="" id="file"> </div> </div> </td></tr>' +
                    ' <tr> <td colspan="2"> ' +
                    '<label for="caseDesc">解决方案</label> ' +
                    '<textarea name="caseDesc" id="caseDesc" cols="100" rows="10"></textarea> </td> </tr> <tr> ' +
                    '<td colspan="2"> <button class=" btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="appCaseAddSave" data-sn="'+r.currentTarget.attributes[2].nodeValue+'"' +
                    '>保存 </button> </td> </tr> </table> </form> </dl>';

                _this.parent().parent().parent().html(html);

                $('#caseName').val(data.data.caseName);
                $('#caseView').val(data.data.caseView);
                $('#startDate').val(data.data.startTime);
                $('#endDate').val(data.data.endTime);
                $('#caseDesc').val(data.data.caseDesc);
                $('.fileTip').html(data.data.certPathsList[0].certName);
                $('#file').val(data.data.certPathsList[0].certPath);

                $("#appCaseAddSave").on('click',function () {
                    var data=JSON.stringify({
                        optArrAySn:$('#appCaseAddSave').attr('data-sn'),
                        optType: "3",
                        optAuthType: "4",
                        appCaseList:[{
                            "sn":$('#appCaseAddSave').attr('data-sn'),
                            "caseName": $("#caseName").val(),
                            "caseView": $("#caseView").val(),
                            "startTime": $("#startDate").val() ,
                            "endTime": $("#endDate").val() ,
                            "caseDesc": $('#caseDesc').val() || "",
                            "certPathsList":[
                                {
                                    "certName":$('.fileTip').html(),
                                    "certPath":$('input[type="hidden"]').val()
                                }
                            ]
                        }],

                    })
                    addSave({
                        data:data,
                        dom:'#appCase'
                    })
                });
                $("#appCaseAdd").hide();
                jeDate();
                fileUpload();
                validate();
            }
        }
    });

}

function swpEdit(r) {
    console.log("修改");
    var _this=$(this);
    $.ajax({
        type: 'get',
        url: "/api/auth/getAuthInfo",
        data:{
            optArrAySn:r.currentTarget.attributes[2].nodeValue,
            optAuthType: "5"
        },
        success: function (data) {
            console.log();
            if(data.code==1){
                // var index =r.currentTarget.parentNode.parentNode.parentNode.rowIndex;
                // $(".education tr").eq(index).html(html);
                var html='<dl>' +
                    '<form action="" id="form">' +
                    '<table id="swp" class="from"> ' +
                    '<tr>' +
                    '<td>' +
                    '<div>' +
                    '<label for="softWareName"><span class="color-red">*</span>软件名称</label> ' +
                    '<input type="text" id="softWareName" name="softWareName" placeholder="请输入软件名称" required> </div> </td>' +
                    '<td><div>' +
                    '<label for="registerNum"><span class="color-red">*</span>登记号</label> ' +
                    '<input type="text" id="registerNum" name="registerNum" placeholder="请输入登记号" required> </div> </td>' +
                    ' </tr> <tr> ' +
                    '<td> <div> ' +
                    '<label for=""><span class="color-red">*</span>首次发表日期</label> ' +
                    '<div class="display-inline-block"> <input id="startDate" name="startDate" type="text"placeholder="请选择首次发表日期" readonly="" required></div> </div> </td> ' +
                    '<td> <div><label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename"  class="fileUploadBtn j_firstPage"> <span class="falseBen j_firstPage">上传附件</span> <span class="fileTip"></span> <input type="hidden" name="filename" value="" id="file"> </div> </div> </td></tr>' +
                    ' <tr> <td colspan="2"> ' +
                    '<label for="purpose">软件用途</label> ' +
                    '<textarea name="purpose" id="purpose" cols="100" rows="10"></textarea> </td> </tr> <tr> ' +
                    '<td colspan="2"> <button class=" btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="swpAddSave" data-sn="'+r.currentTarget.attributes[2].nodeValue+'"' +
                    '>保存 </button> </td> </tr> </table> </form> </dl>';

                _this.parent().parent().parent().html(html);

                $('#softWareName').val(data.data.softWareName);
                $('#registerNum').val(data.data.registerNum);
                $('#startDate').val(data.data.publicTime);
                $('#purpose').val(data.data.purpose);
                $('.fileTip').html(data.data.certPathsList[0].certName);
                $('#file').val(data.data.certPathsList[0].certPath);

                $("#swpAddSave").on('click',function () {
                    var data=JSON.stringify({
                        optArrAySn:$('#swpAddSave').attr('data-sn'),
                        optType: "3",
                        optAuthType: "5",
                        swpList:[{
                            "sn":$('#swpAddSave').attr('data-sn'),
                            "softWareName": $("#softWareName").val(),
                            "registerNum": $("#registerNum").val(),
                            "publicTime": $("#startDate").val() ,
                            "purpose": $('#purpose').val() || "",
                            "certPathsList":[
                                {
                                    "certName":$('.fileTip').html(),
                                    "certPath":$('input[type="hidden"]').val()
                                }
                            ]
                        }]
                    })
                    addSave({
                        data:data,
                        dom:'#swp'
                    })
                });
                $("#swpAdd").hide();
                jeDate();
                fileUpload();
                validate();
            }
        }
    });

}
function inPatentsEdit(r) {
    console.log("修改");
    var _this=$(this);
    $.ajax({
        type: 'get',
        url: "/api/auth/getAuthInfo",
        data:{
            optArrAySn:r.currentTarget.attributes[2].nodeValue,
            optAuthType: "6"
        },
        success: function (data) {
            console.log();
            if(data.code==1){
                // var index =r.currentTarget.parentNode.parentNode.parentNode.rowIndex;
                // $(".education tr").eq(index).html(html);

                var html='<dl>' +
                    '<form action="" id="form">' +
                    '<table id="inPatents" class="from"> ' +
                    '<tr>' +
                    '<td>' +
                    '<div>' +
                    '<label for="patentName"><span class="color-red">*</span>发明名称</label> ' +
                    '<input type="text" id="patentName" name="patentName" placeholder="请输入软件名称" required> </div> </td>' +
                    '<td><div>' +
                    '<label for="patentNum"><span class="color-red">*</span>专利号</label> ' +
                    '<input type="text" id="patentNum" name="patentNum" placeholder="请输入登记号" required> </div> </td>' +
                    ' </tr> <tr> ' +
                    '<td> <div> ' +
                    '<label for=""><span class="color-red">*</span>申请日期</label> ' +
                    '<div class="display-inline-block"> <input id="startDate" name="startDate" type="text"placeholder="请选择首次发表日期" readonly="" required></div> </div> </td> ' +
                    '<td> <div><label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename"  class="fileUploadBtn j_firstPage"> <span class="falseBen j_firstPage">上传附件</span> <span class="fileTip"></span> <input type="hidden" name="filename" value="" id="file"> </div> </div> </td></tr>' +
                    ' <tr> <td colspan="2"> ' +
                    '<label for="patentDesc">专利概述</label> ' +
                    '<textarea name="patentDesc" id="patentDesc" cols="100" rows="10"></textarea> </td> </tr> <tr> ' +
                    '<td colspan="2"> <button class="btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " id="inPatentsAddSave" data-sn="'+r.currentTarget.attributes[2].nodeValue+'"' +
                    '>保存 </button> </td> </tr> </table> </form> </dl>';

                _this.parent().parent().parent().html(html);

                $('#patentName').val(data.data.patentName);
                $('#patentNum').val(data.data.patentNum);
                $('#startDate').val(data.data.applyTime);
                $('#patentDesc').val(data.data.patentDesc);
                $('.fileTip').html(data.data.certPathsList[0].certName);
                $('#file').val(data.data.certPathsList[0].certPath);

                $("#inPatentsAddSave").on('click',function () {
                    var data=JSON.stringify({
                        optArrAySn:$('#inPatentsAddSave').attr('data-sn'),
                        optType: "3",
                        optAuthType: "6",
                        inPatentsList:[{
                            "sn":$('#inPatentsAddSave').attr('data-sn'),
                            "patentName": $("#patentName").val(),
                            "patentNum": $("#patentNum").val(),
                            "applyTime": $("#startDate").val() ,
                            "patentDesc": $('#patentDesc').val() || "",
                            "certPathsList":[
                                {
                                    "certName":$('.fileTip').html(),
                                    "certPath":$('input[type="hidden"]').val()
                                }
                            ]
                        }]
                    })
                    addSave({
                        data:data,
                        dom:'#inPatents'
                    })
                });

                jeDate();
                fileUpload();
                validate();
            }
        }
    });

}
//渲染页面
function reader() {
    $.ajax({
        type: 'get',
        url: "/api/auth/getAuthInfo",
        async: false,
        success: function (data) {
            console.log(data);
            if(data.code==1){
                var educationsExpList=data.data.educationsExpList || null;
                var worksExpList=data.data.worksExpList;
                var projectsExpList=data.data.projectsExpList;
                var appCaseList=data.data.appCaseList;
                var swpList=data.data.swpList;
                var inPatentsList=data.data.inPatentsList;
                var specialSkills=data.data.specialSkills;
                $('.providerDesc').val(data.data.providerDesc);
                $('.providerDesc').html(data.data.providerDesc);
                if(specialSkills){  //擅长领域
                    var html='';
                    for (var i=0,len=specialSkills.length;i<len;i++){
                        html +='<span class="active">'+specialSkills[i]+'</span>'
                    }
                    $('.info .info-show .tag-box').html(html);
                }
                if( educationsExpList && educationsExpList.length>0){ //教育
                    $('.content-education-noData').hide();
                    var html='';
                    var edu='';
                    for (var i=0,len=educationsExpList.length;i<len;i++){
                        var item=educationsExpList[i];
                        console.log(item);
                        switch(item.edu){
                            case('1'):
                                edu = '大专';
                                break;
                            case('2'):
                                edu = '本科';
                                break;
                            case('3'):
                                edu = '硕士';
                                break;
                            case('4'):
                                edu = '博士';
                                break;
                        }
                        html+='<dl> '
                        html+='<dt class="clearfix"> '
                        html+='<div class="grid-left">'
                        html+='<span class="schoolName sub-title">'+item.schoolName+'</span> '
                        html+='<span class="">('+formatDate(item.startTime)+'-'+formatDate(item.endTime)+')</span></div> '
                        html+='<div class="grid-right operation btn"> '
                        html+='<a href="javascript:void (0)" class="educationEdit"   data-sn="'+item.sn+'"> <i class="fa fa-pencil-square-o font-size-20" aria-hidden="true"></i></a> '
                        html+='<a href="javascript:void (0)" class="educationDelete" data-sn="'+item.sn+'"><i class="fa fa-trash-o font-size-20" aria-hidden="true"></i></a> </div> '
                        html+='</dt><dd>'
                        html+='<span class="major">专业名称：'+item.major+'</span>'
                        html+='<span class="edu">学历：'+edu+'</span>'
                        html+='<span class="orExam">是否统招：'+(item.orExam=="1"?"是":"否")+'</span> '
                        if(item.certPathsList && item.certPathsList.length>0) {
                            html += '<span class="certPath"><i class="fa fa-download" aria-hidden="true"></i><a href="'+item.certPathsList[0].certPath+'">材料证明</a> </span> '
                        }
                        html+= '</dd></dl>'

                    }
                    $('.content-education').html(html);
                    $(".educationEdit").on('click',educationEdit);
                    $(".educationDelete").on('click',function (r) {
                        itemDelete(r,"1")
                    });
                    $("#educationAdd").show();
                }else {
                    $('.content-education-noData').show();
                }
                if(worksExpList && worksExpList.length>0){ //工作经历
                    $('.content-work-noData').hide();
                    var html=''
                    for (var i=0,len=worksExpList.length;i<len;i++){
                        var item=worksExpList[i];
                        html+='<dl> '
                        html+='<dt class="clearfix"> '
                        html+='<div class="grid-left">'
                        html+='<span class="companyName sub-title">'+item.companyName+'</span> '
                        html+='<span class="">('+formatDate(item.startTime)+'-'+formatDate(item.endTime)+')</span></div> '
                        html+='<div class="grid-right btn"> '
                        html+='<a href="javascript:void (0)" class="workEdit"   data-sn="'+item.sn+'"> <i class="fa fa-pencil-square-o font-size-20" aria-hidden="true"></i></a> '
                        html+='<a href="javascript:void (0)" class="workDelete" data-sn="'+item.sn+'"><i class="fa fa-trash-o font-size-20" aria-hidden="true"></i></a> </div> '
                        html+='</dt> '
                        html+='<dd> '
                        html+='<span class="departName">部门名称：'+item.departName+'</span>'
                        html+=' <span class="position">职位：'+item.position+'</span>'
                        if(item.certPathsList && item.certPathsList.length>0) {
                            html += '<span class="certPath"><i class="fa fa-download" aria-hidden="true"></i><a href="'+item.certPathsList[0].certPath+'">材料证明</a> </span> '
                        }
                        html+='</dd> </dl>'
                    }
                    $('.content-work').html(html);
                    $(".workEdit").on('click',workEdit);
                    $(".workDelete").on('click',function (r) {
                        itemDelete(r,"2")
                    });
                    $("#workAdd").show();

                }else {
                    $('.content-work-noData').show()
                }
                if(projectsExpList && projectsExpList.length>0){ //项目经历
                    $('.content-project-noData').hide()
                    var html=''
                    for (var i=0,len=projectsExpList.length;i<len;i++){
                        var item=projectsExpList[i];
                        html+='<dl> ' +
                            '<dt class="clearfix"> ' +
                            '<div class="grid-left">' +
                            '<span class="projectName sub-title">'+item.projectName+'</span> ' +
                            '<span class="">('+formatDate(item.startTime)+'-'+formatDate(item.endTime)+')</span> </div> ' +
                            '<div class="grid-right btn"> ' +
                            '<a href="javascript:void (0)" class="projectEdit"   data-sn="'+item.sn+'"> <i class="fa fa-pencil-square-o font-size-20" aria-hidden="true"></i></a> ' +
                            '<a href="javascript:void (0)" class="projectDelete" data-sn="'+item.sn+'"><i class="fa fa-trash-o font-size-20" aria-hidden="true"></i></a> </div> </dt> ' +
                            '<dd> ' +
                            '<label for="">项目职责:</label> ' +
                            '<div>'+item.projectDuty+'</div> </dd> ' +
                            '<dd> ' +
                            '<label for="">项目描述:</label> ' +
                            '<div>'+item.projectDesc+'</div> </dd> </dl>'
                    }
                    $('.content-project').html(html);
                    $(".projectEdit").on('click',projectEdit);
                    $(".projectDelete").on('click',function (r) {
                        itemDelete(r,"3")
                    });
                    $("#projectAdd").show();
                }else {
                    $('.content-project-noData').show()
                }

                if(appCaseList && appCaseList.length>0){ //应用案例
                    $('.content-appCase-noData').hide();
                    var html=''
                    for (var i=0,len=appCaseList.length;i<len;i++){
                        var item=appCaseList[i];
                        html +='<dl> '
                        html +='<dt class="clearfix"> '
                        html +='<div class="grid-left"> '
                        html +='<span class="caseName sub-title">'+item.caseName+'</span> '
                        html +='<span class="">('+formatDate(item.startTime)+'-'+formatDate(item.endTime)+')</span> </div> '
                        html +='<div class="grid-right btn"> '
                        html +='<a href="javascript:void (0)" class="appCaseEdit"   data-sn="'+item.sn+'"> <i class="fa fa-pencil-square-o font-size-20" aria-hidden="true"></i></a> '
                        html +='<a href="javascript:void (0)" class="appCaseDelete" data-sn="'+item.sn+'"><i class="fa fa-trash-o font-size-20" aria-hidden="true"></i></a> </div> </dt> '
                        html +='<dd> '
                        html +='<label>案例概述:</label> '
                        html +='<div >'+item.caseView+'</div> </dd> '
                        html +='<dd>'
                        html +=' <label>解决方案:</label> '
                        html +='<div>'+item.caseDesc+'</div> </dd> <dd> '
                        html +='<label>证明材料:</label> '
                        html +='<div>'
                        if(item.certPathsList && item.certPathsList.length>0) {
                            html +='<span class="certPath"><i class="fa fa-download" aria-hidden="true"></i><a href="'+item.certPathsList[0].certPath+'">材料证明</a> </span> '
                        }
                        html +='</div></dd></dl>'

                    }
                    $('.content-appCase').html(html);
                    $(".appCaseEdit").on('click',appCaseEdit);
                    $(".appCaseDelete").on('click',function (r) {
                        itemDelete(r,'4')
                    });
                    $("#appCaseAdd").show();
                }else {
                    $('.content-appCase-noData').show()

                }
                if(swpList && swpList.length>0){
                    $('.content-swp-noData').hide();
                    var html=''
                    for (var i=0,len=swpList.length;i<len;i++){
                        var item=swpList[i];
                        html +='<dl> '
                        html +='<dt class="clearfix"> '
                        html +='<div class="grid-left"> '
                        html +='<span class="caseName sub-title">'+item.softWareName+'</span> '
                        html +='<span class="">(首次发表日期:'+item.publicTime+')</span> </div> '
                        html +='<div class="grid-right btn"> '
                        html +='<a href="javascript:void (0)" class="swpEdit"   data-sn="'+item.sn+'"> <i class="fa fa-pencil-square-o font-size-20" aria-hidden="true"></i></a> '
                        html +='<a href="javascript:void (0)" class="swpDelete" data-sn="'+item.sn+'"><i class="fa fa-trash-o font-size-20" aria-hidden="true"></i></a> </div> </dt> '
                        html +='<dd> '
                        html +='<label>登记号:</label>'
                        html +='<div >'+item.registerNum+'</div> </dd> '
                        html +='<dd>'
                        html +='<label>软件用途:</label> '
                        html +='<div>'+item.purpose+'</div> </dd> <dd> '
                        html +='<label>证明材料:</label> '
                        html +='<div>'
                        if(item.certPathsList && item.certPathsList.length>0) {
                            html +='<span class="certPath"><i class="fa fa-download" aria-hidden="true"></i><a href="'+item.certPathsList[0].certPath+'">材料证明</a> </span> '
                        }
                        html +='</div></dd></dl>'

                    }
                    $('.content-swp').html(html);
                    $(".swpEdit").on('click',swpEdit);
                    $(".swpDelete").on('click',function (r) {
                        itemDelete(r,'5')
                    });
                    $("#swpAdd").show();
                }else {
                    $('.content-swp-noData').show()

                }
                if(inPatentsList && inPatentsList.length>0){
                    $('.content-inPatents-noData').hide();
                    var html=''
                    for (var i=0,len=inPatentsList.length;i<len;i++){
                        var item=inPatentsList[i];
                        html +='<dl> '
                        html +='<dt class="clearfix"> '
                        html +='<div class="grid-left"> '
                        html +='<span class="caseName sub-title">'+item.patentName+'</span> '
                        html +='<span class="">(首次发表日期:'+item.applyTime+')</span> </div> '
                        html +='<div class="grid-right btn"> '
                        html +='<a href="javascript:void (0)" class="inPatentsEdit"   data-sn="'+item.sn+'"> <i class="fa fa-pencil-square-o font-size-20" aria-hidden="true"></i></a> '
                        html +='<a href="javascript:void (0)" class="inPatentsDelete" data-sn="'+item.sn+'"><i class="fa fa-trash-o font-size-20" aria-hidden="true"></i></a> </div> </dt> '
                        html +='<dd> '
                        html +='<label>专利号:</label> '
                        html +='<div >'+item.patentNum+'</div> </dd> '
                        html +='<dd>'
                        html +=' <label>专利概述:</label> '
                        html +='<div>'+item.patentDesc+'</div> </dd> <dd> '
                        html +='<label>证明材料:</label> '
                        html +='<div>'
                        if(item.certPathsList && item.certPathsList.length>0) {
                            html +='<span class="certPath"><i class="fa fa-download" aria-hidden="true"></i><a href="'+item.certPathsList[0].certPath+'">材料证明</a> </span> '
                        }
                        html +='</div></dd></dl>'

                    }
                    $('.content-inPatents').html(html);
                    $(".inPatentsEdit").on('click',inPatentsEdit);
                    $(".inPatentsDelete").on('click',function (r) {
                        itemDelete(r,'6');
                    });
                    $("#inPatentsAdd").show();
                }else {
                    $('.content-inPatents-noData').show()
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
    var   data=new Date(d);
    var   now=new Date(data.getTime(d));
    var   year=now.getFullYear();
    var   month=now.getMonth()+1;
    return year+"/"+month;
}

// 日历插件
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
// 上传插件
function fileUpload() {
    $('.fileUploadBtn').fileupload(
        {
            url: host.static + '/upload/other',
            dataType: 'json',
            maxFileSize: 10240000,
            add: function (e, data) {
                var filesize = data.files[0].size;
                if(Math.ceil(filesize / 1024) > 1024*10){
                    console.log('文件过大'+filesize);
                    $.alert('附件大小不得超过10M！');
                    return;
                }
                data.submit();
            },
            done: function (e, data) {
                console.log('上传完毕');
                var obj = data.result.data[0];
                $('input[type="hidden"]').val(obj.absPath);
                $('.fileTip').html(data.files[0].name);
                $('.j_firstPage').html('更换附件');
                console.log(data);
                console.log(obj.filePath);
                console.log(data.files[0].name);

            },
            progressall: function (e, data) {
            }
        });
}
// 表单验证
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
                required:true  //必填。如果验证方法不需要参数，则配置为true
            },
            major:{ //专业名称
                required:true
            },
            applyTime:{
                required:true,
            }
        },
        messages:{
            schoolName:{
                required:"*请输入学校名称"
            },
            major:{
                required:"*请输入专业名称"
            },
            applyTime:{
                required:true,
            }

        }
    });
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

/**
 * Created by lss on 2017/10/12 0012.
 */
$(function () {
    reader();
    // 教育经历单个修改
    $(".educationEdit").on('click',function (r) {
        console.log(r.currentTarget.attributes[2].nodeValue);
        var sn=r.currentTarget.attributes[2].nodeValue;
        console.log($(this));
        var html='<td colspan="7">' +
            '<table id="education"> ' +
            '<tr> ' +
            '<td> ' +
            '<div> ' +
            '<label for="">学校名称</label> ' +
            '<input type="text"> ' +
            '</div> ' +
            '</td> ' +
            '<td> ' +
            '<div> ' +
            '<label for="">专业名称</label>' +
            '<input type="text"> </div>' +
            '</td>' +
            '</tr> ' +
            '<tr> <td> <div > ' +
            '<label for="">就学时间</label> ' +
            '<div class="display-inline-block">' +
            ' <input id="startDate" type="text" placeholder="请选择开始时间" readonly=""> 至 <input id="endDate" type="text" placeholder="请选择结束时间" readonly=""> </div> </div> </td> ' +
            '<td> <div> <label for="">学校名称</label> <select name="" id=""> <option value="">大专</option> <option value="">本科</option> <option value="">硕士</option> <option value="">博士</option> </select> </div> </td> </tr> <tr> <td> <input type="checkbox" name="sex" value="n" />是否统招 </td> <td> <div > <label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename" class="fileUploadBtn j_firstPage"> </div> </div> </td> </tr> ' +
            '<tr> <td colspan="2"> ' +
            '<button class="btn btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5" onclick="educationAddSave(132456)" >保存</button> </td> </tr> </table> </td>'

        var index =r.currentTarget.parentNode.parentNode.parentNode.rowIndex;
        $(".education tr").eq(index).html(html);
        jeDate();


    });

    // 教育经历单个删除
    $(".educationDelete").on('click',function (r) {
        console.log(r);
        r.target.parentNode.parentNode.parentNode.parentNode.remove();
        $.ajax({
            type: 'post',
            url: "/api/auth/optAuthInfo",
            dataType: 'json',
            contentType: 'application/json',
            data:JSON.stringify({
                optArrAySn:r.currentTarget.attributes[2].nodeValue,
                optType: "2",
                optAuthType: "1"
            }),
            success: function (data) {
                console.log(data);
                if(data.code==1){
                   $("#education").parent().parent().remove();
                   $.alert("删除成功！")

                }
            }
        });
    });
});
// 添加教育经历
$("#educationAdd").on('click',function () {
    var html='<tr><td colspan="7">' +
        '<table id="education"> ' +
        '<tr> ' +
        '<td> ' +
        '<div> ' +
        '<label for="">学校名称</label> ' +
        '<input type="text" id="schoolName"> ' +
        '</div> ' +
        '</td> ' +
        '<td> ' +
        '<div> ' +
        '<label for="">专业名称</label>' +
        ' <input type="text" id="major"> </div>' +
        ' </td>' +
        ' </tr> ' +
        '<tr> <td> <div > ' +
        '<label for="">就学时间</label> ' +
        '<div class="display-inline-block">' +
        ' <input id="startDate" type="text" placeholder="请选择开始时间" readonly=""> 至 <input id="endDate" type="text" placeholder="请选择结束时间" readonly=""> </div> </div> </td> ' +
        '<td> <div> <label for="">学历</label> <select name="" id="edu"> <option value="大专">大专</option> <option value="本科">本科</option> <option value="硕士">硕士</option> <option value="博士">博士</option> </select> </div> </td> </tr> ' +
        '<tr> ' +
        '<td> <input type="checkbox" name="sex" value="n" id="orExam" />是否统招 </td> <td> <div > <label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename" class="fileUploadBtn j_firstPage"> <input type="hidden" name="filename" value="" id="file"> </div> </div> </td> </tr> ' +
        '<tr> <td colspan="2"> ' +
        '<button class="btn btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5 " onclick="educationAddSave()">保存</button> </td> </tr> </table> </td> </tr>'

         $(".education tbody").append(html);
         $(".education tfoot").hide();
         jeDate();
         var file=''
         fileUpload();


});
// 教育经历单个保存
function educationAddSave(sn) {
    if(sn){
        var data=JSON.stringify({
            optArrAySn:sn,
            optType: "2",
            optAuthType: "1",
            educationsExpList:[{
                "sn":sn,
                "schoolName": $("#schoolName").val(),
                "major": $("#major").val(),
                "startTime": $("#startDate").val() || null,
                "endTime": $("#endDate").val() || null,
                "edu": $('#edu').val() || null,
                "orExam": $('#orExam').attr('checked')?"1":"0",
                "certPath":$('input[type="hidden"]').val()

    }]
        })
    }else {
        var data=JSON.stringify({
            educationsExpList:[{
                "sn": "SN_1_"+time(),
                "schoolName": $("#schoolName").val(),
                "major": $("#major").val(),
                "startTime": $("#startDate").val() || null,
                "endTime": $("#endDate").val() || null,
                "edu": $('#edu').val() || null,
                "orExam": $('#orExam').attr('checked')?"1":"0",
                "certPath":$('input[type="hidden"]').val()
            }]
        })
    }
    $.ajax({
        type: 'post',
        url: "/api/auth/optAuthInfo",
        dataType: 'json',
        contentType: 'application/json',
        data:data,
        success: function (data) {
            if(data.code==1){
                $("#education").parent().parent().remove();
                reader();
                $(".education tfoot").show()


            }else {
                $.alert(data.message)
            }
        }
    });

};



function reader() {
    $.ajax({
        type: 'get',
        url: "/api/auth/getAuthInfo",
        // dataType: 'json',
        // contentType: 'application/json',
        async: false,
        success: function (data) {
            console.log(data);
            if(data.code==1){
                var educationsExpList=data.data.educationsExpList;
                var html='';
                if(educationsExpList){
                    for (var i=0,len=educationsExpList.length;i<len;i++){
                        var item=educationsExpList[i];
                        html+=' <tr> ' +
                            '<td>'+item.schoolName+'</td>' +
                            ' <td>('+formatDate(item.startTime)+'-'+formatDate(item.endTime)+')</td>' +
                            ' <td>'+item.major+'</td> ' +
                            '<td>'+item.edu+'</td> ' +
                            '<td>'+(item.orExam=="1"?"统招":"666")+'</td> ' +
                            '<td></td> ' +
                            '<td> ' +
                            '<div> ' +
                            '<a href="javascript:void (0)" class="educationEdit" data-sn="'+item.sn+'"> <i class="fa fa-pencil-square-o font-size-20" aria-hidden="true"></i></a> ' +
                            '<a href="javascript:void (0)" class="educationDelete" data-sn="'+item.sn+'"><i class="fa fa-trash-o font-size-20" aria-hidden="true"></i></a>' +
                            ' </div> ' +
                            '</td> ' +
                            '</tr>'
                    }
                    $('.education tbody').html(html)
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
                $('input[type="hidden"]').val(obj.filePath)
                console.log(data);
                console.log(obj.filePath);
                console.log(data.files[0].name);

            },
            progressall: function (e, data) {
            }
        });
}

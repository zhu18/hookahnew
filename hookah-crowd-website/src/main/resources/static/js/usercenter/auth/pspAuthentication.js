/**
 * Created by lss on 2017/10/12 0012.
 */
    // 日历插件开始
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
// 日历插件结束
// 添加教育经历
$("#educationAdd").on('click',function () {
    console.log(1);
    var html='<tr><td colspan="7">' +
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
        ' <input type="text"> </div>' +
        ' </td>' +
        ' </tr> ' +
        '<tr> <td> <div > ' +
        '<label for="">就学时间</label> ' +
        '<div class="display-inline-block">' +
        ' <input id="startDate" type="text" placeholder="请选择开始时间" readonly=""> 至 <input id="endDate" type="text" placeholder="请选择结束时间" readonly=""> </div> </div> </td> ' +
        '<td> <div> <label for="">学校名称</label> <select name="" id=""> <option value="">大专</option> <option value="">本科</option> <option value="">硕士</option> <option value="">博士</option> </select> </div> </td> </tr> <tr> <td> <input type="checkbox" name="sex" value="n" />是否统招 </td> <td> <div > <label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename" class="fileUploadBtn j_firstPage"> </div> </div> </td> </tr> ' +
        '<tr> <td colspan="2"> ' +
        '<button class="btn btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5" onclick="educationAddSave(this)" >保存</button> </td> </tr> </table> </td> </tr>'

         $(".education tbody").append(html)


});
// 教育经历单个保存
function educationAddSave(item) {
    console.log(item);
    $("#education").parent().parent().remove();

}
// 教育经历单个修改
$(".educationEdit").on('click',function (r) {
    console.log(r);
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
        ' <input type="text"> </div>' +
        ' </td>' +
        ' </tr> ' +
        '<tr> <td> <div > ' +
        '<label for="">就学时间</label> ' +
        '<div class="display-inline-block">' +
        ' <input id="startDate" type="text" placeholder="请选择开始时间" readonly=""> 至 <input id="endDate" type="text" placeholder="请选择结束时间" readonly=""> </div> </div> </td> ' +
        '<td> <div> <label for="">学校名称</label> <select name="" id=""> <option value="">大专</option> <option value="">本科</option> <option value="">硕士</option> <option value="">博士</option> </select> </div> </td> </tr> <tr> <td> <input type="checkbox" name="sex" value="n" />是否统招 </td> <td> <div > <label for="">证明材料</label> <div class="upload-box display-inline-block"> <input type="file" name="filename" class="fileUploadBtn j_firstPage"> </div> </div> </td> </tr> ' +
        '<tr> <td colspan="2"> ' +
        '<button class="btn btn-full-blue padding-top-5 padding-right-10 padding-left-10 padding-bottom-5" onclick="educationAddSave(this)" >保存</button> </td> </tr> </table> </td>'
         console.log(r.currentTarget.parentNode.parentNode.parentNode.rowIndex);
         var index =r.currentTarget.parentNode.parentNode.parentNode.rowIndex;
         console.log($(".education tr").eq(index));
         $(".education tr").eq(index).html(html)
});

// 教育经历单个删除
$(".educationDelete").on('click',function (r) {
    console.log(r);
    r.target.parentNode.parentNode.parentNode.parentNode.remove();
});


$.ajax({
    type: 'get',
    url: "/api/auth/getAuthInfo",
    // dataType: 'json',
    // contentType: 'application/json',
    success: function (data) {
        console.log(data);
    }
});

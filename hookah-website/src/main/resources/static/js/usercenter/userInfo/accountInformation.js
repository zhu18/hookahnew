/**
 * Created by lss on 2017/7/14 0014.
 */
$(function () {
    $.ajax({
        url:host.website+'/regInfo/selectAccountInfo',
        data:{},
        type:'get',
        success:function (data) {
            console.log(data.data.user);
            var html="";
            html += "<li><span class='info-title'>登录账号:</span><span >"+ data.data.user.userName+"</span></li>";
            html += "<li><span class='info-title'>手机号码:</span><span >"+data.data.user.mobile+"</span></li>";
            html += "<li><span class='info-title'>邮箱:</span><span>"+data.data.user.email+"</span></li>";
            html += "<li><span class='info-title'>注册时间:</span><span>"+data.data.user.addTime+"</span></li>";
            html += "<li><span class='info-title'>最后登录IP:</span><span>"+data.data.user.lastLoginIp+"</span></li>";
            html += "<li><span class='info-title'>最后登录时间:</span><span>"+data.data.user.lastLoginTime+"</span></li>";
            $('.accountInformation-top-list').html(html);

            var htmlTabel="";
            var list=data.data.loginLogs;
            console.log(list);
            for (var i=0;i<list.length;i++){
                if(!list[i].remark){
                    list[i].remark=""
                }
                htmlTabel +="<tr>";
                htmlTabel +="<td>" +list[i].addTime+"</td>";
                htmlTabel +="<td>"+list[i].clientIp+"</td>";
                htmlTabel +="<td>"+list[i].remark+"</td>";
                htmlTabel +="</tr>";
            }
            $('.accountInformation-down table tbody').html(htmlTabel);
        }
    });
})

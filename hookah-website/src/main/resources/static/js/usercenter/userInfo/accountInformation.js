/**
 * Created by lss on 2017/7/14 0014.
 */
$(function () {
    Loading.start();
    $.ajax({
        url:host.website+'/regInfo/selectAccountInfo',
        type:'get',
        cache:false,
        success:function (data) {
            Loading.stop();
            if(data.code == "1"){
                var userName=data.data.user.userName?data.data.user.userName:'';
                var mobile=data.data.user.mobile?data.data.user.mobile:'';
                var email=data.data.user.email?data.data.user.email:'';
                var addTime=data.data.user.addTime?data.data.user.addTime:'';
                var lastLoginIp=data.data.user.lastLoginIp?data.data.user.lastLoginIp:'';
                var lastLoginTime=data.data.user.lastLoginTime?data.data.user.lastLoginTime:'';
                var html="";
                html += "<li><span class='info-title'>登录帐号:</span><span >"+userName+"</span></li>";
                html += "<li><span class='info-title'>手机号码:</span><span >"+mobile+"</span></li>";
                html += "<li><span class='info-title'>邮箱:</span><span>"+email+"</span></li>";
                html += "<li><span class='info-title'>注册时间:</span><span>"+addTime+"</span></li>";
                html += "<li><span class='info-title'>最后登录IP:</span><span>"+lastLoginIp+"</span></li>";
                html += "<li><span class='info-title'>最后登录时间:</span><span>"+lastLoginTime+"</span></li>";
                $('.accountInformation-top-list').html(html);

                var htmlTabel="";
                var list=data.data.loginLogs;
                console.log(list);
                for (var i=0;i<list.length;i++){
                    if(!list[i].remark){
                        list[i].remark=""
                    }
                    htmlTabel +="<tr>";
                    htmlTabel +="<td>"+list[i].addTime+"</td>";
                    htmlTabel +="<td>"+list[i].clientIp+"</td>";
                    htmlTabel +="<td>"+list[i].remark+"</td>";
                    htmlTabel +="</tr>";
                }
                $('.accountInformation-down table tbody').html(htmlTabel);
            }else {
                $.alert(data.message);
            }

        }
    });
})

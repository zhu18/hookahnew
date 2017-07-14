/**
 * Created by lss on 2017/7/14 0014.
 */
$(function () {
    $.ajax({
        url:host.website+'/regInfo/selectAccountInfo',
        data:{},
        type:'get',
        success:function (data) {
            console.log(data.data);
            var html=null;
            html += "<li><span class='info-title'>登录账号:</span><span > '++'  </span></li>";
            html += "<li><span class='info-title'>手机号码:</span><span > '++'  </span></li>";
            html += "<li><span class='info-title'>邮箱:</span><span>      '++'    </span></li>";
            html += "<li><span class='info-title'>注册时间:</span><span>   '++'  </span></li>";
            html += "<li><span class='info-title'>最后登录IP:</span><span> '++'  </span></li>";
            html += "<li><span class='info-title'>最后登录时间:</span><span>'++' </span></li>";
            $('.accountInformation-top-list').html(html);

            var htmlTabel=null;
            htmlTabel +="<tr>";
            htmlTabel +="<td>2017-07-04 16:50:27</td>";
            htmlTabel +="<td>192.168.15.90</td>";
            htmlTabel +="<td>********</td>";
            htmlTabel +="</tr>";
            $('.accountInformation-down table tbody').html(htmlTabel);
        }
    });
})

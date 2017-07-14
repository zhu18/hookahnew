/**
 * Created by lss on 2017/7/14 0014.
 */
$(function () {
    $.ajax({
        url:host.website+'/regInfo/verifiedInfo',
        data:{},
        type:'get',
        success:function (data) {
            console.log(data.data);
            var html="";
            html +="<li><label>用户类型</label><p>单位会员</p></li>";
            html +="<li><label>认证状态</label><p class='position-relative'><span>已认证</span><a href='' class='repeat-revise-btn'>重新修改</a></p></li>";
            html +="<li><label>审核意见</label><p>通过</p></li>";
            $('.ul1').html(html);

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
            $('.ul2').html(htmlTabel);
        }
    });
})

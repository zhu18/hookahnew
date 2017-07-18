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
            if (data.data.userType =="0"){
                html +="<li><label>用户类型</label><p>个人会员</p></li>";
            }else {
                html +="<li><label>用户类型</label><p>单位会员</p></li>";
            }
            if(!data.data.checkContent){
                data.data.checkContent="无"
            }
            if(data.data.isAuth == '3'){
                html +="<li><label>认证状态</label><p class='position-relative'><span>未通过</span><a href='' class='repeat-revise-btn'>重新修改</a></p></li>";
                html +="<li><label>审核意见</label><p>"+data.data.checkContent+"</p></li>";
            }else if(data.data.isAuth == '2'){
                html +="<li><label>认证状态</label><p class='position-relative'><span>已认证</span><a href='' class='revise-btn'>修改</a></p></li>";
                html +="<li><label>审核意见</label><p>"+data.data.checkContent+"</p></li>";
            }else {
                html +="<li><label>认证状态</label><p class='position-relative'><span>未认证</span><a href='' class='repeat-revise-btn'>立即认证</a></p></li>";
                html +="<li><label>审核意见</label><p>"+data.data.checkContent+"</p></li>";
            }
            $('.ul1').html(html);

            if(data.data.isAuth == '3'){
                data.data.isAuth="未通过"
            }else if(data.data.isAuth == '2'){
                data.data.isAuth="已认证"
            }else {
                data.data.isAuth="未认证"

            }
            // $('#orgName').html(data.data.orgName);
            // $('#isAuth').html( data.data.isAuth);
            // $('#certificateCode').html( data.data.certificateCode);
            // $('#licenseCode').html( data.data.licenseCode);
            // $('#taxCode').html( data.data.taxCode);
            // $('#industry').html( data.data.industry);
            // $('#lawPersonName').html( data.data.lawPersonName);
            // $('#region').html( data.data.region);
            // $('#contactAddress').html( data.data.contactAddress);
            // $('#orgPhone').html( data.data.orgPhone);

        }
    });
})

/**
 * Created by lss on 2017/7/14 0014.
 */
$(function () {
    $.ajax({
        url:host.website+'/regInfo/verifiedInfo',
        data:{},
        type:'get',
        success:function (data) {
            var html="";
            if (data.data.userType =="0"){
                html +="<li><label>用户类型</label><p>个人会员</p></li>";
            }else {
                html +="<li><label>用户类型</label><p>单位会员</p></li>";
            }
            if(data.data.checkStatus == '2'){
                html +="<li><label>认证状态</label><p class='position-relative'><span>未通过</span><a href='/auth/company_auth_init_step2?isAuth=3' class='repeat-revise-btn'>重新修改</a></p></li>";
                html +="<li><label>审核意见</label><p>"+data.data.checkContent?data.data.checkContent:"无"+"</p></li>";
            }else if(data.data.checkStatus == '1'){
                html +="<li><label>认证状态</label><p class='position-relative'><span>已认证</span></p></li>";
            }else if(data.data.checkStatus == '0'){
                html +="<li><label>认证状态</label><p class='position-relative'><span>认证中</span></p></li>";
            }else {
                html +="<li><label>认证状态</label><p class='position-relative'><span>未认证</span><a href='/auth/company_auth_init_step2?isAuth=1' class='repeat-revise-btn'>立即认证</a></p></li>";
            }
            $('.ul1').html(html);

            // if(data.data.isAuth == '3'){
            //     data.data.isAuth="未通过"
            // }else if(data.data.isAuth == '2'){
            //     data.data.isAuth="已认证"
            // }else {
            //     data.data.isAuth="未认证"
            // }
            // $('#isAuth').html( data.data.isAuth?data.data.isAuth:"无");

            $('#orgName').html(data.data.organization.orgName?data.data.organization.orgName:"无");//单位名称
            $('#creditCode').html(data.data.organization.creditCode?data.data.organization.creditCode:"无");//统一社会信用代码
            $('#industry').html( data.data.organization.industry?data.data.organization.industry:"无");//经营范围
            $('#lawPersonName').html( data.data.organization.lawPersonName?data.data.organization.lawPersonName:"无");//法定代表人
            $('#lawPersonCategory').html( data.data.organization.lawPersonCategory=="0"?"居民身份证":"无");//法定代表人证件类别
            $('#lawPersonNum').html( data.data.organization.lawPersonNum?data.data.organization.lawPersonNum:"无");//法定代表人证件编号
            $('#lawPersonPositivePath').attr({"src":data.data.organization.lawPersonPositivePath});//法定代表人证件照正
            $('#lawPersonNegativePath').attr({"src":data.data.organization.lawPersonNegativePath});//法定代表人证件照反
             //注册地址
            $('#contactAddress').html( data.data.organization.contactAddress?data.data.organization.contactAddress:"无");
            // 办公地址
            $('#officeAddress').html( data.data.organization.officeAddress?data.data.organization.officeAddress:"无");
            $('#orgPhone').html( data.data.organization.orgPhone?data.data.organization.orgPhone:"无");//联系电话

            $('#certificateCode').html( data.data.organization.certificateCode?data.data.organization.certificateCode:"无");
            $('#certifictePath').attr({"src":data.data.organization.certifictePath});
            $('#licenseCode').html( data.data.organization.licenseCode?data.data.organization.licenseCode:"无");
            $('#licensePath').attr({"src":data.data.organization.licensePath});

            $('#taxCode').html( data.data.organization.taxCode?data.data.organization.taxCode:"无");
            $('#taxPath').attr({"src":data.data.organization.taxPath});

        }
    });
})

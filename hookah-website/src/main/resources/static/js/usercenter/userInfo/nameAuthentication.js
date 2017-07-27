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
            if(!data.data.checkContent){
                data.data.checkContent="无"
            }
            if(data.data.isAuth == '3'){
                html +="<li><label>认证状态</label><p class='position-relative'><span>未通过</span><a href='/auth/company_auth_init_step2?isAuth=3' class='repeat-revise-btn'>重新修改</a></p></li>";
                html +="<li><label>审核意见</label><p>"+data.data.checkContent+"</p></li>";
            }else if(data.data.isAuth == '2'){
                html +="<li><label>认证状态</label><p class='position-relative'><span>已认证</span></p></li>";
            }else if(data.data.isAuth == '1'){
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

            $('#orgName').html(data.data.orgName?data.data.orgName:"无");//单位名称
            $('#creditCode').html(data.data.creditCode?data.data.creditCode:"无");//统一社会信用代码
            $('#industry').html( data.data.industry?data.data.industry:"无");//经营范围
            $('#lawPersonName').html( data.data.lawPersonName?data.data.lawPersonName:"无");//法定代表人
            $('#lawPersonCategory').html( data.data.lawPersonCategory?data.data.lawPersonCategory:"无");//法定代表人证件类别
            $('#lawPersonNum').html( data.data.lawPersonNum?data.data.lawPersonNum:"无");//法定代表人证件编号
            $('#lawPersonPositivePath').html( data.data.lawPersonPositivePath?data.data.lawPersonPositivePath:"无");//法定代表人证件照正
            $('#lawPersonNegativePath').html( data.data.lawPersonNegativePath?data.data.lawPersonNegativePath:"无");//法定代表人证件照反
             //注册地址
            $('#region').html( data.data.region?data.data.region:"无");
            $('#contactAddress').html( data.data.contactAddress?data.data.contactAddress:"无");
            // 办公地址
            $('#region').html( data.data.region?data.data.region:"无");
            $('#officeAddress').html( data.data.contactAddress?data.data.contactAddress:"无");
            $('#orgPhone').html( data.data.orgPhone?data.data.orgPhone:"无");//联系电话

            $('#certificateCode').html( data.data.certificateCode?data.data.certificateCode:"无");
            $('#certifictePath').attr({"src":data.data.certifictePath});
            $('#licenseCode').html( data.data.licenseCode?data.data.licenseCode:"无");
            $('#licensePath').attr({"src":data.data.licensePath});

            $('#taxCode').html( data.data.taxCode?data.data.taxCode:"无");
            $('#taxPath').html( data.data.taxPath?data.data.taxPath:"无");

        }
    });
})

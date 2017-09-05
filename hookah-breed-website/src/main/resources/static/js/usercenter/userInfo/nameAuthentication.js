/**
 * Created by lss on 2017/7/14 0014.
 */
$(function () {
    $.ajax({
        url:host.website+'/regInfo/verifiedInfo',
        data:{},
        cache:false,
        type:'get',
        success:function (data) {
            if(data.code == "1" && typeof data.data =="object"){
                var html="";
                var content=data.data.checkContent?data.data.checkContent:"无";
                html +="<li><label>用户类型</label><p>单位用户</p></li>";
                if(data.data.userType == '7'){
                    html +="<li><label>认证状态</label><p class='position-relative'><span>未通过</span><a href='/auth/company_auth_init_step2?isAuth=3' class='repeat-revise-btn repeat-revise-btn-cov'>重新修改</a></p></li>";
                    html +="<li><label>审核意见</label><p>"+content+"</p></li>";
                }else if(data.data.userType == '4'){
                    html +="<li><label>认证状态</label><p class='position-relative'><span>已认证</span></p></li>";
                    html +="<li><label>审核意见</label><p>"+content+"</p></li>";
                }else if(data.data.userType == '5'){
                    html +="<li><label>认证状态</label><p class='position-relative'><span>认证中</span></p></li>";
                }else if(data.data.userType == '1'){
                    html +="<li><label>认证状态</label><p class='position-relative'><span>未认证</span><a href='/auth/company_auth_init_step2?isAuth=1' class='repeat-revise-btn repeat-revise-btn-cov'>立即认证</a></p></li>";
                }
                $('.ul1').html(html);
                if(data.data.organization){
                    $('#orgName').html(data.data.organization.orgName?data.data.organization.orgName:"无");//单位名称
                    $('#creditCode').html(data.data.organization.creditCode?data.data.organization.creditCode:"无");//统一社会信用代码
                    $('#industry').html( data.data.organization.industry?data.data.organization.industry:"无");//经营范围
                    $('#lawPersonName').html( data.data.organization.lawPersonName?data.data.organization.lawPersonName:"无");//法定代表人
                    $('#lawPersonCategory').html( data.data.organization.lawPersonCategory=="0"?"居民身份证":"无");//法定代表人证件类别
                    $('#lawPersonNum').html( data.data.organization.lawPersonNum?data.data.organization.lawPersonNum:"无");//法定代表人证件编号
                    $('#lawPersonPositivePath').attr({"src":host.static+'/' + data.data.organization.lawPersonPositivePath});//法定代表人证件照正
                    $('#lawPersonNegativePath').attr({"src":host.static+'/' + data.data.organization.lawPersonNegativePath});//法定代表人证件照反
                    //注册地址
                    var contactAddress=data.data.organization.contactAddress?data.data.organization.contactAddress:"无";
                    var regionProvince=data.data.regionProvince?data.data.regionProvince:"";
                    var region=data.data.region?data.data.region:"";
                    $('#contactAddress').html(regionProvince+region+contactAddress);
                    // 办公地址
                    var officeAddress=data.data.organization.officeAddress?data.data.organization.officeAddress:"无";
                    var officeRegionProvince=data.data.officeRegionProvince?data.data.officeRegionProvince:"";
                    var officeRegion=data.data.officeRegion?data.data.officeRegion:"";
                    $('#officeAddress').html(officeRegionProvince+ officeRegion+officeAddress);
                    $('#orgPhone').html( data.data.organization.orgPhone?data.data.organization.orgPhone:"无");//联系电话

                    $('#certificateCode').html( data.data.organization.certificateCode?data.data.organization.certificateCode:"无");
                    $('#certifictePath').attr({"src":host.static+'/' + data.data.organization.certifictePath});
                    $('#licenseCode').html( data.data.organization.licenseCode?data.data.organization.licenseCode:"无");
                    $('#licensePath').attr({"src":host.static+'/' + data.data.organization.licensePath});

                    $('#taxCode').html( data.data.organization.taxCode?data.data.organization.taxCode:"无");
                    $('#taxPath').attr({"src":host.static+'/' + data.data.organization.taxPath});
                }
            }else {
                var html="";
                html +="<li><label>用户类型</label><p>单位用户</p></li>";
                html +="<li><label>认证状态</label><p class='position-relative'><span>未认证</span><a href='/auth/company_auth_init_step2?isAuth=1' class='repeat-revise-btn'>立即认证</a></p></li>"
                $('.ul1').html(html);
            }
        }
    });
})

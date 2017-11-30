/**
 * Created by lss on 2017/7/14 0014.
 * userTypey 根据这个字段判断用户的状态，一共七中状态
 *   1：未认证 2：个人用户已认证 3：个人用户认证中 4：单位用户已认证 5：单位用户认证中 6：个人用户未通过 7：单位用户未通过
 * organization   单位用户信息
 * userDetail     个人用户信息
 */
$(function () {
    $('.personalInfo').hide();
    $('.enterpriseInfo').hide();
    Loading.start();
    $.ajax({
        url:host.website+'/regInfo/verifiedInfo',
        data:{},
        cache:false,
        type:'get',
        success:function (data) {
            Loading.stop();
            if(data.code == "1" && typeof data.data =="object"){
                var html="";
                var content=data.data.checkContent?data.data.checkContent:"无";
                if(data.data.userType == '7'){
                    html +="<li><label>用户类型</label><p>单位用户</p></li>";
                    html +="<li><label>认证状态</label><p class='position-relative'><span>未通过</span><a href='/auth/company_auth_init_step2?isAuth=3' class='repeat-revise-btn'>重新修改</a></p></li>";
                    html +="<li><label>审核意见</label><p>"+content+"</p></li>";
                }else if(data.data.userType == '4'){
                    html +="<li><label>用户类型</label><p>单位用户</p></li>";
                    html +="<li><label>认证状态</label><p class='position-relative'><span>已认证</span></p></li>";
                    html +="<li><label>审核意见</label><p>"+content+"</p></li>";
                }else if(data.data.userType == '5'){
                    html +="<li><label>用户类型</label><p>单位用户</p></li>";
                    html +="<li><label>认证状态</label><p class='position-relative'><span>认证中</span></p></li>";
                }else if(data.data.userType == '1'){
                    html +="<li style='position: relative;'><label>认证状态</label><p class='position-relative'><span>未认证</span><a href='/auth/company_auth_init_step2?isAuth=1' class='repeat-revise-btn'>立即认证</a></p><div class='forPoints'>成功认证送积分 <span></span></div></li>";
                }else if(data.data.userType == '6'){
                    html +="<li><label>用户类型</label><p>个人用户</p></li>";
                    html +="<li><label>认证状态</label><p class='position-relative'><span>未通过</span><a href='/auth/company_auth_init_step2?isAuth=3' class='repeat-revise-btn'>重新修改</a></p></li>";
                    html +="<li><label>审核意见</label><p>"+content+"</p></li>";
                }else if(data.data.userType == '3'){
                    html +="<li><label>用户类型</label><p>个人用户</p></li>";
                    html +="<li><label>认证状态</label><p class='position-relative'><span>认证中</span></p></li>";
                }else if(data.data.userType == '2'){
                    html +="<li><label>用户类型</label><p>个人用户</p></li>";
                    html +="<li><label>认证状态</label><p class='position-relative'><span>已认证</span></p></li>";
                    html +="<li><label>审核意见</label><p>"+content+"</p></li>";
                }
                $('.authenticationStatus').html(html);
                if(data.data.organization){ //单位用户信息
                    $('.enterpriseInfo').show();
                    $('#orgName').html(data.data.organization.orgName?data.data.organization.orgName:"无");//单位名称
                    $('#lawPersonName').html( data.data.organization.lawPersonName?data.data.organization.lawPersonName:"无");//法定代表人
                    $('#lawPersonNum').html( data.data.organization.lawPersonNum?data.data.organization.lawPersonNum:"无");//法定代表人证件编号
                    $('#licensePath').attr({"src":host.static+'/' + data.data.organization.licensePath});
                }
                if(data.data.userDetail){//个人用户信息
                    $('.personalInfo').show();
                    $('#realName').html(data.data.userDetail.realName?data.data.userDetail.realName:"无");
                    $('#cardNum').html(data.data.userDetail.cardNum?data.data.userDetail.cardNum:"无");
                }
            }else {
                var html="";
                html +="<li style='position: relative;'><label>认证状态</label><p class='position-relative'><span>未认证</span><a href='/auth/company_auth_init_step2?isAuth=1' class='repeat-revise-btn'>立即认证</a></p><div class='forPoints'>成功认证送积分 <span></span></div></li>"
                $('.authenticationStatus').html(html);
            }
          $(document).on('click','.forPoints span',function(){//点击移出积分
            $(this).parent().remove()
          })

        }
    });
})

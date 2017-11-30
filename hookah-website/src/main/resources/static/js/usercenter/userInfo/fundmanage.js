/**
 * Created by lss on 2017/7/19 0019.
 */
$(function () {
    bankName();
});
function bankName() {
    $.ajax({ //各个银行列表
        url:host.website+'/payBankCard/searchBankInfo',
        data:{},
        cache:false,
        type:'get',
        success:function (data) {
            if(data.code == "1"){
                var list=data.data.bank;
                accountInfo(list)//获取用户的账户信息（余额，绑定银行...）
            }else {
                $(".account-funds-content-left .money").html("￥0.00");
                $(".freeze").html("￥0.00");
                $(".useBalance").html("￥0.00");
                $(".bank-card").hide()
            }
        }
    });

}//获取银行列表
function accountInfo(list) {
    Loading.start();
    $.ajax({
        url:host.website+'/payBankCard/userFunds',
        data:{},
        cache:false,
        type:'get',
        success:function (data) {
            Loading.stop();
            if(data.code == "1"){
                var useBalance=data.data.useBalance?"￥"+splitK((data.data.useBalance / 100).toFixed(2)):"￥0.00";
                var frozenBalance=data.data.frozenBalance?"￥"+splitK((data.data.frozenBalance / 100).toFixed(2)):"￥0.00";
                var balance=data.data.balance?"￥"+splitK((data.data.balance / 100).toFixed(2)):"￥0.00";
                $(".account-funds-content-left .money").html(useBalance);
                $(".freeze").html(frozenBalance);
                $(".useBalance").html(balance);

                if(data.data.userType == 4 || data.data.userType == 2){
                    for(var i=0;i<list.length;i++){
                        if(list[i].id==data.data.bankId){
                            data.data.bankId=list[i].bankName;
                        }
                    }
                    var html='';
                    if(data.data.bindFlag=="0"){
                        html +='<div class="show-card">';
                        html +='<h4 class="card-name">'+data.data.bankId+'</h4><p>';
                        html +='<span class="margin-right-5">银行账户:</span>';
                        html +='<span class="card-number">'+data.data.cardCode+'</span></p><p>';
                        html +='<span class="margin-right-5">账户名:</span><span class="account-name">'+data.data.cardOwner+'</span></p><a href="javascript:void(0)" class="delete">删除</a></div>';
                    }else {
                        html +='<a href="/usercenter/bindBankCard" class="add-card">';
                        html +='<p class="Plus margin-top-20">+</p>';
                        html +='<p>添加银行账户</p>';
                        $(".operation-btn").append('<span class="color-red margin-left-10">您还未绑定银行账户，<a href="/usercenter/bindBankCard" class="color-red">立即绑定</a></span>');

                    }
                    $(".bank-card-content").html(html);
                    $(".withdrawals-btn").on("click",function () {
                        if(data.data.bindFlag=="0"){
                            window.location.href= host.website+'/withdrawRecord/getUserInfo';
                        }else {
                            $.alert("请先绑定银行账户！")
                        }
                    })
                    deleteBank()
                }else {
                    $(".account-funds-content-left .money").html("￥0.00");
                    $(".freeze").html("￥0.00");
                    $(".useBalance").html("￥0.00");
                    $(".bank-card").hide()
                }
            }else {
              $.alert(data.message);
            }


        }
    });
}//获取个人账户信息
function deleteBank() {// 删除银行卡
    $(".bank-card-content .delete").on("click",function () {
        $.alert('您确定要删除已绑定的银行账户吗？',true,function(){
            $.ajax({
                url:host.website+'/payBankCard/updateBankInfo',
                data:{},
                cache:false,
                type:'get',
                success:function (data) {
                    if (data.code=="1"){
                        $.alert('解绑银行账户成功！');
                        bankName()
                    }else {
                        $.alert(data.message);
                    }
                }
            });
        });

    })
}
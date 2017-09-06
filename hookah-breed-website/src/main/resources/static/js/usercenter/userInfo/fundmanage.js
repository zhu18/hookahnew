/**
 * Created by lss on 2017/7/19 0019.
 */
$(function () {
    render();
});
function render() {
    $.ajax({
        url:host.website+'/payBankCard/searchBankInfo',
        data:{},
        cache:false,
        type:'get',
        success:function (data) {
            if(data.code == "1"){
                var list=data.data.bank;
                $.ajax({
                    url:host.website+'/payBankCard/userFunds',
                    data:{},
                    cache:false,
                    type:'get',
                    success:function (data) {
                        $(".account-funds-content-left .money").html("￥"+splitK((data.data.useBalance / 100).toFixed(2)));
                        $(".freeze").html("￥"+splitK((data.data.frozenBalance / 100).toFixed(2)));
                        $(".useBalance").html("￥"+splitK((data.data.balance / 100).toFixed(2)));

                        if(data.data.userType == 4){
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
                                html +='<a href="javascript:void(0)" class="add-card">';
                                html +='<p class="Plus margin-top-20">+</p>';
                                html +='<p>添加银行账户</p>';
                                $(".operation-btn").append('<span class="color-red margin-left-10">您还未绑定银行账户，<a href="javascript:void(0)" class="color-red bindBankCard-btn">立即绑定</a></span>');

                            }
                            $(".bank-card-content").html(html);
                            $(".withdrawals-btn").on("click",function () {
                                var myData=new Date();
                                judgeTime({
                                    beginTime:'08:30',
                                    endTime:'19:30',
                                    nowTime:myData.getHours()+':'+myData.getMinutes(),
                                    info:"请在指定的时间段充值！",
                                    callback:function () {
                                        if(data.data.bindFlag=="0"){
                                            window.location.href='/withdrawRecord/getUserInfo'
                                        }else {
                                            $.alert({
                                                content:"请先绑定银行账户！"
                                            })
                                        }
                                    }
                                });
                            });
                            $(".recharge-btn").on("click",function () {
                                var data=new Date();
                                judgeTime({
                                    beginTime:'08:30',
                                    endTime:'19:30',
                                    nowTime:data.getHours()+':'+data.getMinutes(),
                                    info:"请在指定的时间段充值！",
                                    callback:function () {
                                      window.location.href='/usercenter/recharge'
                                    }
                                });
                            });
                            $(".add-card").on("click",function () {
                                var data=new Date();
                                judgeTime({
                                    beginTime:'08:30',
                                    endTime:'19:30',
                                    nowTime:data.getHours()+':'+data.getMinutes(),
                                    info:"请在指定的时间段充值！",
                                    callback:function () {
                                      window.location.href='/usercenter/bindBankCard'
                                    }
                                });
                            });
                            $(".bindBankCard-btn").on("click",function () {
                                var data=new Date();
                                judgeTime({
                                    beginTime:'08:30',
                                    endTime:'19:30',
                                    nowTime:data.getHours()+':'+data.getMinutes(),
                                    info:"请在指定的时间段充值！",
                                    callback:function () {
                                      window.location.href='/usercenter/bindBankCard'
                                    }
                                });
                            });
                            // 删除银行卡
                            $(".bank-card-content .delete").on("click",function () {
                                var data=new Date();
                                judgeTime({
                                    beginTime:'08:30',
                                    endTime:'19:30',
                                    nowTime:data.getHours()+':'+data.getMinutes(),
                                    info:"请在指定的时间段充值！",
                                    callback:function () {
                                        $.confirm({
                                            content:'您确定要删除已绑定的银行账户吗',
                                            callback:function (type) {
                                                if(type == 'yes'){
                                                    this.hide();
                                                    $.ajax({
                                                        url:host.website+'/payBankCard/updateBankInfo',
                                                        data:{

                                                        },
                                                        cache:false,
                                                        type:'get',
                                                        success:function (data) {
                                                            if (data.code=="1"){
                                                                $.alert({
                                                                    content:'解绑银行账户成功！',
                                                                    header:'提示'
                                                                });
                                                                render()
                                                            }
                                                        }
                                                    });
                                                }else{
                                                    this.hide();
                                                }
                                            }
                                        })
                                    }
                                });

                            })
                        }else {
                            $(".account-funds-content-left .money").html("￥0.00");
                            $(".freeze").html("￥0.00");
                            $(".useBalance").html("￥0.00");
                            $(".bank-card").hide()
                        }

                    }
                });
            }else {
                $(".account-funds-content-left .money").html("￥0.00");
                $(".freeze").html("￥0.00");
                $(".useBalance").html("￥0.00");
                $(".bank-card").hide()
            }
        }
    });



}

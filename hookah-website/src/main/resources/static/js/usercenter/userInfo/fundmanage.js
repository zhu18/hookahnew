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
        type:'get',
        success:function (data) {
            var list=data.data.bank;
            $.ajax({
                url:host.website+'/payBankCard/userFunds',
                data:{},
                type:'get',
                success:function (data) {
                    $(".account-funds-content-left .money").html("￥"+(data.data.useBalance / 100).toFixed(2));
                    $(".freeze").html("￥"+(data.data.freeze / 100).toFixed(2));
                    $(".useBalance").html("￥"+(data.data.useBalance / 100).toFixed(2));
                    for(var i=0;i<list.length;i++){
                        if(list[i].id==data.data.bankId){
                            data.data.bankId=list[i].bankName;
                        }
                    }
                    var html='';
                    if(data.data.bindFlag=="0"){
                        html +='<div class="show-card">';
                        html +='<h4 class="card-name">'+data.data.bankId+'</h4><p>';
                        html +='<span>银行卡号:</span>';
                        html +='<span class="card-number">'+data.data.cardCode+'</span></p><p>';
                        html +='<span>账户名:</span><span class="account-name">'+data.data.cardOwner+'</span></p><a href="javascript:void(0)" class="delete">删除</a></div>';
                    }else {
                        html +='<a href="/usercenter/bindBankCard" class="add-card">';
                        html +='<p class="Plus margin-top-20">+</p>';
                        html +='<p>添加银行</p>';
                        html +='</a><p class="margin-left-15 margin-top-20 tip">提示：只能用ie浏览器......</p>';
                    }
                    $(".bank-card-content").html(html);
                    // 删除银行卡
                    $(".bank-card-content .delete").on("click",function () {
                        $.alert('确定解绑银行卡？',true,function(){
                            $.ajax({
                                url:host.website+'/payBankCard/updateBankInfo',
                                data:{},
                                type:'get',
                                success:function (data) {
                                    if (data.code=="1"){
                                        $.alert('解绑银行卡成功！');
                                         render()
                                    }
                                }
                            });
                        });

                    })
                }
            });
        }
    });

}

$('.order-ext-trigger').click(function(){
	$('.order-details').slideToggle();
})
$('.manage-more').click(function(){
	// $(this).hide();
	$('.saved-card-list .channel-balance').siblings().slideToggle();
})
$('.saved-card-list>.row-container').click(function(){
	var that = $(this);
	if(that.hasClass('row-container-disabled')){
		return;
	}else{
		that.addClass('row-container-focus').siblings().removeClass('row-container-focus');
	}
});
getPayPwdStatus();
function getPayPwdStatus(){
	$.ajax({
		url:host.website+'/usercenter/payPassSta',
		type:'get',
		success:function(data){
			if(data.code == 1){
				console.log(1231231231231)
			}else if(data.code == 0){
				$.confirm('你还没有设置支付密码<br/>现在去设置？',null,function(type){
					if(type == 'yes'){
						this.hide();
						setPayPwdCon();
						window.location.href=host.auth+'/verify?type=setPayPassword';
					}else{
						this.hide();
					}
				})
			}else{
				$.alert(data.message)
			}
		}
	});
}
function setPayPwdCon(){
	$.confirm('已完成支付密码设置？',null,function(type){
		if(type == 'yes'){
			this.hide();
			getPayPwdStatus()
		}else{

		}
	})
}
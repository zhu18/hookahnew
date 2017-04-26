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
var testData = {
	pay: {
		password: /^[0-9]{6}$/
	},
	payPassword: function (val) {
		return this.pay.password.test(val)
	}
};
getPayPwdStatus();
function getPayPwdStatus(){
	$.ajax({
		url:host.website+'/usercenter/payPassSta',
		type:'get',
		success:function(data){
			if(data.code == 1){
				if(data.data == 0){
					$.confirm('你还没有设置支付密码<br/>现在去设置？',null,function(type){
						if(type == 'yes'){
							this.hide();
							window.open(host.auth+'/verify?type=setPayPassword');
							setPayPwdCon();
						}else{
							this.hide();
						}
					})
				}else{

				}
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
			this.hide();
		}
	})
}
var goodsAmount = $('#goodsAmount').val();
var moneyBalance = $('#moneyBalance').val();
getCheckVal();
function getCheckVal(){
	$("[name=apiCode]:radio").each(function () {
		if (this.checked) {
			if(this.value == 1){
				if(moneyBalance < goodsAmount){
					$('#J-security').hide();
					$('#J-rcSubmit').hide();
				}else{
					$('#J-balanceNt').hide();
				}
			}else{
				$('#J-security').hide();
				$('#J-balanceNt').hide();
			}
			showPayAmount(this)
		}
	});
}
$("[name=apiCode]:radio").click(function () {
	if(this.value == 1){
		if(moneyBalance < goodsAmount){
			$('#J-security').hide();
			$('#J-rcSubmit').hide();
			$('#J-balanceNt').show();
		}else{
			$('#J-balanceNt').hide();
			$('#J-security').show();
			$('#J-rcSubmit').show();
		}
	}else{
		$('#J-security').hide();
		$('#J-balanceNt').hide();
		$('#J-rcSubmit').show();
	}
	showPayAmount(this)
});
function showPayAmount(that){
	$('.pay-amount').hide();
	$(that).siblings('.pay-amount').show();
}
function check(){
	$("[name=apiCode]:radio").each(function () {
		if (this.checked) {
			if(this.value == 1){
				if($('#payPassword_rsainput').val()){
					if(testData.payPassword($('#payPassword_rsainput').val()))
					$.alert('支付成功了');
					return false;
				}else{
					$('.ui-form-explain').hide();
					$('.ui-form-error').show();
				}
			}else{
				$.alert('暂不支持该支付方式');
				return false;
			}
		}
	});
}
$('#payPassword_rsainput').on('focus',function () {
	$('.ui-form-explain').show();
	$('.ui-form-error').hide();
})
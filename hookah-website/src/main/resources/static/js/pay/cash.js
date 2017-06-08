var oldPwd = false;
$('.order-ext-trigger').click(function () {
	$('.order-details').slideToggle();
});
$('.manage-more').click(function () {
	if($(this).hasClass('manage-colse')){
		$('.saved-card-list .channel-balance').siblings().slideUp();
		$(this).removeClass('manage-colse').html('显示其他支付方式');
	}else{
		$('.saved-card-list .channel-balance').siblings().slideDown();
		$(this).addClass('manage-colse').html('隐藏其他支付方式');
	}
});
$('.saved-card-list>.row-container').click(function () {
	var that = $(this);
	if (that.hasClass('row-container-disabled')) {
		return;
	} else {
		that.addClass('row-container-focus').siblings().removeClass('row-container-focus');
	}
});
getPayPwdStatus();
function getPayPwdStatus() {
	$.ajax({
		url: host.website + '/usercenter/payPassSta',
		type: 'get',
		success: function (data) {
			if (data.code == 1) {
				if (data.data == 0) {
					$.confirm('你还没有设置支付密码<br/>现在去设置？', null, function (type) {
						if (type == 'yes') {
							this.hide();
							window.open(host.auth + '/verify?type=setPayPassword');
							setPayPwdCon();
						} else {
							this.hide();
							$('#J_authSubmit').attr('disabled','disabled').css({'background':'#ccc','cursor':'no-drop'});
						}
					})
				} else {

				}
			} else {
				$.alert(data.message)
			}
		}
	});
}
function setPayPwdCon() {
	$.confirm('已完成支付密码设置？', null, function (type) {
		if (type == 'yes') {
			this.hide();
			getPayPwdStatus()
		} else {
			this.hide();
			$('#J_authSubmit').attr('disabled','disabled').css({'background':'#ccc','cursor':'no-drop'});
		}
	})
}
var goodsAmount = parseInt($('#goodsAmount').val());
var moneyBalance = parseInt($('#moneyBalance').val());
console.log('商品价格goodsAmount------'+goodsAmount+'账户余额moneyBalance------'+moneyBalance);
getCheckVal();
function getCheckVal() {
	$("input[name=apiCode]:radio").each(function () {
		if (this.checked) {
			if (this.value == 1) {
				console.log('======1')
				if (goodsAmount > moneyBalance) {
					$('#J-security').hide();
					$('#J-rcSubmit').hide();
				} else {
					$('#J-balanceNt').hide();
				}
			} else {
				$('#J-security').hide();
				$('#J-balanceNt').hide();
			}
			showPayAmount(this)
		}
	});
}
$("[name=apiCode]:radio").click(function () {
	if (this.value == 1) {
		if (goodsAmount > moneyBalance ) {
			$('#J-security').hide();
			$('#J-rcSubmit').hide();
			$('#J-balanceNt').show();
		} else {
			$('#J-balanceNt').hide();
			$('#J-security').show();
			$('#J-rcSubmit').show();
		}
	} else {
		$('#J-security').hide();
		$('#J-balanceNt').hide();
		$('#J-rcSubmit').show();
	}
	showPayAmount(this)
});
function showPayAmount(that) {
	$('.pay-amount').hide();
	$(that).siblings('.pay-amount').show();
}
function check() {
	$("[name=apiCode]:radio").each(function () {
		if (this.checked) {
			if (this.value == 1) {
				if ($('#paymentPassword').val() && $('#paymentPassword').val().length == 6) {
					testPayPassword($('#paymentPassword').val());
				} else {
					$('.ui-form-error').show().children('p').html('支付密码不符合要求');
				}
			} else {
				$.alert('暂不支持该支付方式');
				return false;
			}
		}
	});
}
function testPayPassword(pwd){
	$.ajax({
		url:host.website+'/usercenter/verifyPayPassword',
		data:{
			paymentPassword:pwd
		},
		type:'get',
		success:function (data) {
			if(data.code == 1){
				$('#form_paypsw').submit();
				return true;
			}else if(data.code == 0){
                $('.ui-form-error').show().children('p').html('支付密码不正确');
                $('#payPassword_container').attr('data-busy','0');
                $('#paymentPassword').val('');//清空input值
                $('.sixDigitPassword-box b').css('visibility','hidden');//样式消失
                // $('.sixDigitPassword-box i').eq(0).addClass('active');
                $('#cardwrap').css({'left':'0px'});
                k=0;//让光标自动归位
                return false;
			}else{
				$.alert(data.message);
				return false;
			}
		}
	});
}
$('#paymentPassword').on('focus', function () {
	$('.ui-form-error').hide();
});
var _formPay = $('#form_paypsw'); //支付密码输入框单个输入效果
_formPay.validate({
	rules: {
		'paymentPassword': {
			'minlength': 6,
			'maxlength': 6,
			required: true,
			digits: true,
			// numPassword: true,
			// echoNum: true
		}
	},
	messages: {
		'paymentPassword': {
			'required': '<i class="icon icon-attention icon-lg"></i>&nbsp;请填写支付密码',
			'maxlength': '<i class="icon icon-attention icon-lg"></i>&nbsp;密码最多为{0}个字符',
			'minlength': '<i class="icon icon-attention icon-lg"></i>&nbsp;密码最少为{0}个字符',
			'digits': '<i class="icon icon-attention icon-lg"></i>&nbsp;密码只能为数字',
			'numPassword': '<i class="icon icon-attention icon-lg"></i>&nbsp;连号不可用，相同数字不可用（如：123456，11111）',
			'echoNum': '<i class="icon icon-attention icon-lg"></i>&nbsp;连号不可用，相同数字不可用（如：123456，11111）'
		}
	},
	errorPlacement: function (error, element) {
        console.log(1);
        element.closest('div[data-error="i_error"]').append(error);
	},
	submitHandler: function (form) {
		var _form = $(form);
		form.submit();

	}
});
var payPassword = $("#payPassword_container"),
	_this = payPassword.find('i'),
	k = 0, j = 0,
	password = '',
	_cardwrap = $('#cardwrap');
//点击隐藏的input密码框,在6个显示的密码框的第一个框显示光标
payPassword.on('focus', "input[name='paymentPassword']", function () {
    var _this = payPassword.find('i');
	if (payPassword.attr('data-busy') === '0') {
		//在第一个密码框中添加光标样式
		_this.eq(k).addClass("active");
		_cardwrap.css('visibility', 'visible');
		payPassword.attr('data-busy', '1');
	}
});
//change时去除输入框的高亮，用户再次输入密码时需再次点击
payPassword.on('change', "input[name='paymentPassword']", function () {
	_cardwrap.css('visibility', 'hidden');
	_this.eq(k).removeClass("active");
	payPassword.attr('data-busy', '0');
}).on('blur', "input[name='paymentPassword']", function () {
	_cardwrap.css('visibility', 'hidden');
	_this.eq(k).removeClass("active");
	payPassword.attr('data-busy', '0');
});
//使用keyup事件，绑定键盘上的数字按键和backspace按键
payPassword.on('keyup', "input[name='paymentPassword']", function (e) {
	var e = (e) ? e : window.event;
	//键盘上的数字键按下才可以输入
	if (e.keyCode == 8 || (e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 96 && e.keyCode <= 105)) {
		k = this.value.length;//输入框里面的密码长度
		l = _this.length;//6
		for (; l--;) {
			//输入到第几个密码框，第几个密码框就显示高亮和光标（在输入框内有2个数字密码，第三个密码框要显示高亮和光标，之前的显示黑点后面的显示空白，输入和删除都一样）
			if (l === k) {
				_this.eq(l).addClass("active");
				_this.eq(l).find('b').css('visibility', 'hidden');
			} else {
				_this.eq(l).removeClass("active");
				_this.eq(l).find('b').css('visibility', l < k ? 'visible' : 'hidden');
			}
			if (k === 6) {
				j = 5;
			} else {
				j = k;
			}
			$('#cardwrap').css('left', j * 30 + 'px');
		}
	} else {
		//输入其他字符，直接清空
		var _val = this.value;
		this.value = _val.replace(/\D/g, '');
	}
});
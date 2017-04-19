$(".checkall").click(function () {
	$("[name=items]:checkbox").prop("checked", this.checked);
	$("[name=checkall]:checkbox").prop("checked", this.checked);
	totalAmountFn()
});
function totalAmountFn() {
	var totalAmount = 0.00;
	$("[name=items]:checkbox:checked").each(function () {
		var number = $(this).parent().siblings('.number').children('div').children('.numberInput').val();
		totalAmount += Number($(this).attr('price')) * Number(number);
	});
	$('#J_totalAmount').html((totalAmount / 100).toFixed(2))
}
$("[name=items]:checkbox").click(function () {
	var flag = true;
	$("[name=items]:checkbox").each(function () {
		if (!this.checked) {
			flag = false;
		}
	});
	totalAmountFn();
	$("[name=checkall]:checkbox").prop("checked", flag);
});
//删除选中
$("#delBtn").click(function () {
	var str = [];
	$("[name=items]:checkbox:checked").each(function () {
		str.push($(this).val());
	});
	if (str.length >= 1) {
		$.ajax({
			type: "POST",
			url: '/cart/deleteAll',
			data: JSON.stringify(str),
			dataType: "json",
			contentType: 'application/json',
			success: function (msg) {
				if (msg.code == 1) {
					$.alert('删除成功')
					$("[name=items]:checkbox:checked").each(function () {
						$(this).parents('.order-body').remove()
					});
					if ($("[name=checkall]:checkbox").prop("checked")) {
						$('.cart-box').html('<div class="noDataBox">购物车没有商品 >>><a href="/">去购物</a></div>');
					}
				} else {
					$.alert(msg.message)
				}
			}
		});
	} else {
		$.alert('至少选择一件商品')
	}
});
function reduceFn(that) { //点击按钮事件
	var goodsNumber = Number($(that).siblings('input').val());
	if (goodsNumber > 1) {
		goodsNumber -= 1;
		moneyFn(that, goodsNumber);
	}
	if (goodsNumber <= 1) {
		$(that).addClass('testcalss')
	}
	$(that).siblings('input').val(goodsNumber)

}
function plusFn(that) { //点击加按钮事件
	var goodsNumber = Number($(that).siblings('input').val());
	goodsNumber += 1;
	$(that).siblings('input').val(goodsNumber)
	if (goodsNumber > 1) {
		$(that).siblings('.reduce-btn').removeClass('testcalss');
	}
	moneyFn(that, goodsNumber);
}
function moneyFn(that, goodsNumber) { //点击加减按钮之后进行操作
	var price = Number($(that).parents('.number').siblings('.price').html());
	var money = (price * goodsNumber).toFixed(2);
	var recId = $(that).parents('.number').attr('recId');
	$(that).parents('.number').siblings('.money').html(money);
	editAjax(recId, goodsNumber)
}
function editAjax(recId, goodsNumber) { //请求
	$.ajax({
		type: "POST",
		url: '/cart/edit',
		data: {
			recId: recId,
			goodsNumber: goodsNumber
		},
		success: function (msg) {
			if (msg.code == 1) {
				totalAmountFn();
			} else {
				$.alert(msg.message)
			}
		}
	});
}
var inputVal = "";
function numFocusFn(that) {
	if ($(that).val()) {
		return inputVal = $(that).val();
	}
}
$('.numberInput').blur(function () {
	var goodsNumber = Number($(this).val());
	var recId = $(this).parents('.number').attr('recId');
	var price = Number($(this).parents('.number').siblings('.price').html());
	if ($(this).val()) {
		if ($(this).val() % 1 == 0) {
			if (inputVal != $(this).val()) {
				editAjax(recId, goodsNumber)
				$(this).parents('.number').siblings('.money').html(price * goodsNumber);
			} else {
				console.log('数据一样的')
			}
		} else {
			alert('请输入整数')
		}
	} else {
		alert('不能为空')
	}

});
function kkdown(that, e) {
	var keynum = window.event ? e.keyCode : e.which;
	if (keynum == 13) {
		$(that).blur()
	}

}
function delThis(id,that) {
	$.ajax({
		type: "get",
		url: '/cart/delete/'+id,
		success: function (msg) {
			if (msg.code == 1) {
				$.alert('删除成功');
				$(that).parents('.order-body').remove()
			} else {
				$.alert(msg.message);
			}
		}
	});
}
function check() {
	var flag = 0;
	var cartIds = [];
	$("[name=items]:checkbox").each(function () {
		if (this.checked) {
			flag += 1;
			cartIds.push($(this).attr('recId'))
		}
	});
	if (flag > 0) {
		$('#J_cartIdStr').val(cartIds);
		$('#J_cart').submit();
		return true;
	} else {
		$.alert('请选择要结算的商品')
		return false;
	}
}




$('#J_goodsNameSearch').on('focus',function () {
	$(this).siblings('.tips').hide();
})
$('#J_goodsNameSearch').on('blur',function () {
	if($(this).val()){
		$(this).siblings('.tips').hide();
	}else{
		$(this).siblings('.tips').show();
	}
})
$(".searchInput").mouseover(function (){
	if($('#J_goodsNameSearch').val()){
		$('.cleanBtn').show();
	}
}).mouseout(function (){
	$(".cleanBtn").hide();
})
$('.cleanBtn').click(function(){
	$('#J_goodsNameSearch').val('');
	delete dataParm.goodsName;
	goPage(1);
});
function change(){
	var vals = $('#J_goodsNameSearch').val();
	if (vals) {
		dataParm.goodsName = vals;
		goPage(1);
	} else {
		$('#J_goodsNameSearch').siblings('.tips').show();
	}
	return false;
}
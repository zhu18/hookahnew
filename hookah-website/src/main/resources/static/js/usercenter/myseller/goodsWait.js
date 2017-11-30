
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
$('#J_goodsNameSearch').hover(function () {
	if($(this).val()){
		$('.cleanBtn').show();
	}else{
		$('.cleanBtn').hide();
	}
});
$('.cleanBtn').click(function(){
	$('#J_goodsNameSearch').val('');
	delete dataParm.goodsName;
	goPage(1);
});
function change(){
	var goodsName = $('#J_goodsNameSearch').val();
	var checkStatus = $('#J_checkStatus').val();
	var isBook = $('#J_isBook').val();

	if(goodsName){
		dataParm.goodsName = goodsName;
	}else{
		delete dataParm.goodsName;
	}
	if(checkStatus != -1){
		dataParm.checkStatus = checkStatus;
	}else{
		delete dataParm.checkStatus;
	}
	if(isBook != -1){
		dataParm.isBook = isBook;
	}else{
		delete dataParm.isBook;
	}
	goPage(1);
	return false;
}
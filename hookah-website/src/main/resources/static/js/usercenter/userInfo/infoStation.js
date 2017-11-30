// 消息数量
    num();
    function num() {
        $.ajax({
            url: '/message/countMessageNumber',
            type: 'get',
            cache:false,
            success: function (data) {
                if (data.code == 1) {
                    $("#infoOne").html(data.data.allMessage);
                    $("#infoTwo").html(data.data.noReadMessage)
                }
            }
        })
    }

    function getInfo(that, id) {
	$.ajax({
		url: '/message/detail/' + id,
		type: 'get',
		success: function (data) {
			if (data.code == 1) {
			    var sendUser = data.data.sendUser;
			    console.log(sendUser);
			    if(sendUser =='sys'){
			        sendUser = '系统管理员';
			    }
				var html = '';
				html += '<div class="confirmKey"><h4>消息：</h4>';
				html += '<div>';
				html += '<h5>&nbsp;&nbsp;标题：<span>' + data.data.sendHeader + '</span></h5>';
				html += '<h5>&nbsp;&nbsp;内容：<span>' + data.data.sendContent + '</span></h5>';
				html += '<h5>&nbsp;&nbsp;时间：<span>' + data.data.sendTime + '</span></h5>';
				html += '<h5>&nbsp;&nbsp;发送人：<span>' + sendUser + '</span></h5>';
				html += '</div></div>';
				num();
				$.confirm(html, [{close: '关闭'}], function () {
					this.hide();
				}, {width: "500"});
				$(that).css('color', '#666');
			} else {
				$.alert('请求失败', true, function () {
					location.reload();
				});
			}
		}
	})
}
    $("[name='checkall']:checkbox").click(function () {
	$("[name='items']:checkbox").prop("checked", this.checked);
	$("[name='checkall']:checkbox").prop("checked", this.checked);
	if(this.checked){
		$('#flag').show();
	}else{
		$('#flag').hide();
	}
});






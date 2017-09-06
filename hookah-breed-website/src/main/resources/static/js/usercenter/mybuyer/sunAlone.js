/**
 * Created by ki on 2017/4/24.
 */
$(function(){
    $.fn.raty.defaults.path = '/static/images';
    var len=$('.sunContent').length;
    for (var i=1;i<=len;i++){
        $('.item-'+i+' .function-demo').raty({
            number: 5, //多少个星星设置
            targetType: 'hint', //类型选择，number是数字值，hint，是设置的数组值
            path: '/static/images/',
            hints: ['差', '一般', '好', '非常好', '全五星'],
            size: 24,
            starOff: 'starOff.png',
            starOn: 'starOn.png',
            target: '.item-'+i+' .function-hint',
            cancel: false,
            targetKeep: true,
            targetText: '请选择评分'
            // click: function(score, evt) {
            //     console.log('ID: ' + $(this).attr('id') + "\nscore: " + score + "\nevent: " + evt.type);
            // }
        });
    }

	$("#sunContentForm").validate({
		rules: {
			aloneCon:  {
				isAloneCon:true
			}
		},
		messages: {
			aloneCon:  {
				isAloneCon:'长度为10-100个字符（每个汉字为2个字符）'
			}

		},
		showErrors:function(errorMap,errorList) {
			if(errorList.length){
				errorList[0].element.focus();
			}
			this.defaultShowErrors();
		}
	});
});
function checkFirst(orderId){
	if($("#sunContentForm").valid()){
		var isTrues = false;
	    $('.sunContent').each(function(){
			var thisVal = $(this).children('.rcontent').find('input[name="score"]').val();
			if(!thisVal){
				$(this).children().find('.function-hint').css('color','#A61615');
				isTrues = false;
			}else{
				$(this).children().find('.function-hint').css('color','#333');
				isTrues = true;
			}
		});
	    if(isTrues){
	    	console.log('true')
			check(orderId);
		}else{
			console.log('false')
		}
	}
}
function getLength(str){
	return str.replace(/[\u0391-\uFFE5]/g,"aa").length;
}
$('.area').on('input onporpertychange',function () {
	$('#showcontent').html(getLength($(this).val()));
});
$.validator.addMethod("isAloneCon", function(value, element) {
	var len = value.replace(/[\u0391-\uFFE5]/g,"aa").length;
	return this.optional(element) || (10 <= len && len <= 100);
}, "长度为10-100个字符（每个汉字为2个字符）");
function check(orderId){
    var data=[];
    var len=$('.sunContent').length;
    for (var i=1;i<=len;i++){
        data.push({
            "orderId":orderId,
            "goodsId":$('.item-' + i + ' .pic a').attr('name'),
            "commentContent":$('.item-' + i + ' .area').val(),
            "goodsCommentGrade":$('.item-' + i + ' .function-demo input').val()
        })
    }
    $.ajax({
        type: "post",
        url: '/comment/add',
        data:JSON.stringify(data),
        contentType:'application/json ',
        success: function(msg) {
            if (msg.code == 1) {
                $.alert({
                    content:'提交成功'
                });

                window.location.href="/usercenter/buyer/orderManagement";
            } else {
                $.alert({
                    content:msg.message
                });
            }
        }
    })
}
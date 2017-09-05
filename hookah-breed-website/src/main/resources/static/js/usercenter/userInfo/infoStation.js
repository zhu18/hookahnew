function loadPageData(data) {
    var ids = [];
    if (data.data.list.length > 0) {
        console.log(data.data.list);
        var list = data.data.list;
        var html = "";
        for (var i = 0; i < list.length; i++) {
            ids.push(list[i].id);
            var listStr = "'" + list[i].id + "'";
            html += '<tr>';
            html += '<td><input type="checkbox" name="items" value="' + list[i].id + '"></td>';
            if (list[i].isRead == '1') {
                html += '<td class="text-align-left"><a href="javascript:void(0);" onclick="getInfo(this,' + listStr + ')">' + list[i].sendHeader + '</a></td>';
            } else {
                html += '<td class="text-align-left isRead"><a href="javascript:void(0);" onclick="getInfo(this,' + listStr + ')">' + list[i].sendHeader + '</a></td>';
            }
            html += '<td>' + list[i].sendTime + '</td>';
            html += '<td>' + list[i].sendUserName + '</td>';
            html += '</tr>';
        }
        $(".order table tbody").html(html);
    } else {
        $(".list").html("<div class='listNone'>暂无信息</div>");
    }
    // 删除
    $("#delete").click(function () {
        var str = [];
        $("[name=items]:checkbox:checked").each(function () {
            str.push($(this).val());
        });
        if (str.length >= 1) {
            $.ajax({
                url: "/message/delMessage",
                type: 'post',
                dataType: 'json',
                data: JSON.stringify(str),
                contentType: 'application/json',
                success: function (data) {
                    if (data.code == 1) {
                        $.alert('删除成功', true, function () {
                            location.reload()
                        });
                    } else {
                        console.log("删除失败！");
                    }
                }
            })
        } else {
            $.alert('至少选择一条消息')
        }
    });
    // 标记
    $("#flag").click(function () {
        var list = [];
        $("input[name='items']:checked").each(function () {
            $("input[name='items']:checked").parents("tr").css({"color": "#000"});
            list.push($(this).val());
        });
        $.ajax({
            url: "/message/markToReadMessage",
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(list),
            success: function (data) {
                if (data.code == 1) {
                    $.alert('标记成功', true, function () {
                        location.reload()
                    });
                } else {
                    console.log("标记失败！");
                }
            }
        })
    });
    $("[name='items']:checkbox").click(function () {
        var flag = true;
        var num = 0;
        $("[name='items']:checkbox").each(function () {
            if (!this.checked) {
                flag = false;
            }else{
                num+=1;
            }
        });
        $("[name='checkall']:checkbox").prop("checked", flag);
        if(num > 0){
            $('#flag').show();
        }else{
            $('#flag').hide();
        }
    });
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
				html += '<div class="confirmKey" >';
				html += '<div>';
				html += '<h5>&nbsp;&nbsp;标题：<span>' + data.data.sendHeader + '</span></h5>';
				html += '<h5>&nbsp;&nbsp;内容：<span>' + data.data.sendContent + '</span></h5>';
				html += '<h5>&nbsp;&nbsp;时间：<span>' + data.data.sendTime + '</span></h5>';
				html += '<h5>&nbsp;&nbsp;发送人：<span>' + sendUser + '</span></h5>';
				html += '</div></div>';
				num();
				$.confirm(html, [{close: '确定'}], function () {
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


// 消息数量
num();
function num() {
        $.ajax({
            url: '/message/countMessageNumber',
            type: 'get',
            cache:false,
            success: function (data) {
                if (data.code == 1) {
                    $("#infoOne").html('('+data.data.allMessage+')');
                    $("#infoTwo").html('('+data.data.noReadMessage+')')
                }
            }
        })
}







function loadPageData(data){ //渲染页面数据
    if(data.data.list){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            html += '<tr>';
            html += '<td>'+(i+1)+'</td>';
            html += '<td>'+list[i].newsTitle+'</td>';
            html += '<td class="imgs"><div><img src="'+list[i].pictureUrl+'"></div></td>';
            html += '<td>'+list[i].sytTime+'</td>';
            html += '<td>'+judge(list[i].creatName)+'</td>';
            html += '<td>';
            html += '<a href="javascript:deleteData(\''+list[i].newsId+'\');">删除</a>';
            html += '<a href="/admin/articleModify?id='+list[i].newsId+'">修改</a>';
            html += '</td>';
            html += '</tr>';
        }
        function judge(user){
            if(user == ""||user == null){
                return "-";
            }
            return user;
        }
        $('.article-box .order-items tbody').html(html);
    }
}
function changes(that){ //筛选状态改变
    var thisVal = $(that).val();
    $('.select-hide').hide();
    // if(thisVal == 'information' || that == 'information'){
    //     $('#newsSonGroup').show();
    //     $('.select-line').show();
    //     if(thisVal){
    //         dataParm.newsGroup = thisVal;
    //     }else{
    //         dataParm.newsGroup = that;
    //     }
    //     dataParm.newsSonGroup = $("#newsSonGroup").val();
    //     goPage("1");
    // }else{
    //     $('#newsSonGroup').hide();
    //     $('.select-line').hide();
    //     if(thisVal){
    //         dataParm.newsGroup = thisVal;
    //     }else{
    //         dataParm.newsGroup = that;
    //     }
    //     dataParm.newsSonGroup = "";
    //     goPage("1");
    // }
    console.log(thisVal)
	if(!thisVal){
		$('#newsSonGroup').hide();
		$('.select-line').hide();
		delete dataParm.newsGroup;
		delete dataParm.newsSonGroup;
		goPage("1");
	}else if(thisVal == 'information'){
		$('#newsSonGroup').show();
        $('.select-line').show();
		dataParm.newsGroup = thisVal;
		dataParm.newsSonGroup = $("#newsSonGroup").val();
		goPage("1");
    }else{
		$('#newsSonGroup').hide();
		$('.select-line').hide();
		dataParm.newsGroup = thisVal;
		delete dataParm.newsSonGroup;
		goPage("1");
    }
}
function second(that){
    dataParm.newsSonGroup=$(that).val();
    goPage("1");
}

//        var data = {};
//        function changes(that){
//            var thisVal = $(that).val();
//            $('.select-hide').hide();
//            if(thisVal == 'information'){
//                $('#newsSonGroup').show();
//                $('.select-line').show();
//            }else{
//                $('#newsSonGroup').hide();
//                $('.select-line').hide();
//            }
//            if(thisVal!=='information'){
//                data.newsSonGroup="";
//            }
//            data.pageNumber = 1;
//            data.pageSize = pageSize;
//            data.newsGroup = thisVal;
//            if(data.newsGroup == 'information'){
//                data.newsSonGroup = $("#newsSonGroup").val();;
//            };
//            loadData(data);
//        }
//        function second(that){
//            data.newsSonGroup=$(that).val();
//            loadData(data);
//        }
//    获取列表信息
//        function loadData(data){
//            $(".article-box > .order-items > tbody").html('');
//            $.ajax({
//                type: "get",
//                url: "/sysNews/list",
//                data: data,
//                success: function(data){
//                    console.log(data);
//                    for(var i=0;i<data.list.length;i++){
//                        $(".article-box > .order-items > tbody").append(
//                            "<tr>"
//                            +"<td>"+(i+1)+"</td>"
//                            +"<td>"+data.list[i].newsTitle+"</td>"
//                            +"<td class='imgs'>"+"<div>"+"<img src="+data.list[i].pictureUrl+">"+"</div>"+"</td>"
//                            +"<td>"+format(data.list[i].sytTime)+"</td>"
//                            +"<td>"+judge(data.list[i].sysUser)+"</td>"
//                            +"<td>"
//                            +"<a href='javascript:onclick=deleteData("+JSON.stringify(data.list[i].newsId)+");' >删除</a>"
//                            +"<a href='/usercenter/publishArticle?id="+data.list[i].newsId+"'>修改</a>"
//                            +"</td>"
//                            +"</tr>"
//                        );
//                    }
//                    function judge(user){
//                        if(user == ""||user == null){
//                            return "-";
//                        }
//                        return user;
//                    }
//                    function add(m){ return m < 10 ? '0'+ m:m };
//                    function format(time){
//                        var date = new Date(time);
//                        var year = date.getFullYear() ;
//                        var month = date.getMonth()+1;
//                        var date1 = date.getDate() ;
//                        var hours = date.getHours();
//                        var minutes = date.getMinutes();
//                        var seconds = date.getSeconds();
//                        return year+'-'+add(month)+'-'+add(date1)+' '+add(hours)+':'+add(minutes)+':'+add(seconds);
//                    };
//                },
//                error:function(e){
//                    alert(e);
//                }
//            });
//        }
function deleteData(id) {
    $.confirm('你确定要删除这条文章吗? ',null,function(type){
        if(type == 'yes'){
            deleteLoad(id);
            this.hide();
        }else{
            this.hide();
        }
    });
}
function deleteLoad(id){
    var arr=[];
    arr.push(id);
    $.ajax({
        type:"post",
        url:"/sysNews/deleteInfo",
        dataType: "json",
        data: JSON.stringify(arr),
        contentType: 'application/json',
        success:function(data){
            if(data.code == "1") {
				data.newsGroup = $("#newsGroup").val();
				changes(data.newsGroup);
                $.alert('删除成功', true, function () {
					location.reload()
				});

            }
        },
        error:function(e){
            alert(e);
        }
    });
}

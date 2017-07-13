/**
 * Created by ki on 2017/7/12.
 */


    function loadPageData(data){
    var ids = [];
        if(data.data.list.length>0){
            var list = data.data.list;
            var html="";
            html +='<table>';
            html +='<thead>';
            html +='<tr>';
            html +='<th class="check">';
            html +='<input type="checkbox" class="checkbox-title">全选';
            html +='</th>';
            html +='<th class="title">标题</th>';
            html +='<th class="timer">时间</th>';
            html +='<th class="sender">发送人</th>';
            html +='</tr>';
            html +='</thead>';
            html +='<tbody>';
            for(var i=0;i<list.length;i++){
                ids.push(list[i].id);
                html +='<tr>';
                html +='<td><input type="checkbox" name="checkbox" value="'+list[i].id+'"></td>';
                html +='<td><a href="javascript:getInfo('+list[i].id+')">'+list[i].sendHeader+'</a></td>';
                html +='<td>'+list[i].sendTime+'</td>';
                html +='<td>'+list[i].sendUserName+'</td>';
                html +='</tr>';
            }
            html +='<tbody>';
            html +='<table>';
            $(".order").html(html);
        }else{
            $(".list").html("<div class='listNone'>暂无信息</div>");
        }


        // 删除
        $("#delete").click(function(){
            $.ajax({
                url:"/message/delMessage",
                type:'post',
                dataType:'json',
                data:JSON.stringify(ids),
                contentType:'application/json',
                success:function(data){
                    if(data.code==1){
                        $.alert('删除成功', true, function () {
                            location.reload()
                        });
                    }else{
                        console.log("删除失败！");
                    }
                }
            })
        })

        // 标记
        $("#flag").click(function(){
            var list=[];
            $("input[name='checkbox']:checked").each(function(){
                $("input[name='checkbox']:checked").parents("tr").css({"color":"#000"});
                list.push($(this).val());
            })
            $.ajax({
                url:"/message/markToReadMessage",
                type:'post',
                dataType:'json',
                contentType:'application/json',
                data:JSON.stringify(list),
                success:function(data){
                    if(data.code==1){
                        $.alert('标记成功', true, function () {
                            location.reload()
                            // $("input[name='checkbox']:checked").parents("tr").css({"color":"#000"});
                        });
                    }else{
                        console.log("标记失败！");
                    }
                }
            })
        })
    }

    // 消息数量
    num();
    function num(){
        $.ajax({
            url:'/message/countMessageNumber',
            type: 'get',
            success:function(data){
                if(data.code == 1){
                    $("#infoOne").html(data.data.allMessage);
                    $("#infoTwo").html(data.data.noReadMessage)
                }
            }
        })
    }

function getInfo(id){
    console.log("id:"+id);
    $.ajax({
        url:'/message/detail/'+id,
        type:'get',

        success:function(data){
            if(data.code==1){
                var html =null;
                html = '<div class="confirmKey"><h4>消息：</h4>\
                        <div>\
                        <h5>&nbsp;&nbsp;标题：<span>' + data.data.sendHeader + '</span></h5>\
                        <h5>&nbsp;&nbsp;内容：<span>' + data.data.sendContent + '</span></h5>\
                        <h5>&nbsp;&nbsp;时间：<span>' + data.data.sendTime + '</span></h5>\
                        <h5>&nbsp;&nbsp;发送人：<span>' + data.data.sendUserName + '</span></h5>\
                        </div></div>'

                $.confirm(html, [{close: '关闭'}],
                    function () {
                        this.hide();
                    }, {width: "500"});
            }
        }
    })
}


    // 全选
    $("body").on("click", ".checkbox-title", function () {
        $("input[name='checkbox']").prop('checked', $(this).is(':checked'));
        $(".checkbox-title").prop("checked", $(this).is(':checked'));
    });
    $("body").on("click", ".check-all", function () {
        $("input[name='checkbox']").prop('checked', $(this).is(':checked'));
        $(".check-all").prop("checked", $(this).is(':checked'));
    });

    // $("#flag").click(function(){
    //     var item = $(".list tbody tr");
    //     item.each(function(){
    //         if($("input[name='checkbox']:checked")){
    //             $("input[name='checkbox']:checked").parents("tr").css({"color":"#000"});
    //             $("input[name='checkbox']").prop('checked', false);
    //         }else {
    //             $.confirm("请选择",true,function(){});
    //         }
    //     });
    // })

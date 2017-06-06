/**
 * Created by lss on 2017/4/28 0028.
 */

$(function () {
   // $('.co-organization .grid-row ul li a').hover(function () {
   //
   //     $(this).css({
   //         "background-position":"0 -37px"
   //     }).mouseleave(function () {
   //         $(this).css({
   //             "background-position":"0 0"
   //         })
   //     })
   // })
    sliceString(".infos-info",85);
});
function sliceString(className,number){
    var goodsBrief = $(className);
    var number=parseInt(number);
    $(goodsBrief).each(function(){
        var text = $(this).html();
        if(text.length>=number){
            $(this).html(text.slice(0,number)+'...');
        }
    });
}
loadIndexData()
function loadIndexData(){
    $.ajax({
        url:host.website+'/sysNews/listByGroup',
        type:'get',
        data:{
			pageNumber:1,
			pageSize:5,
            newsGroup:'information',
			newsSonGroup:1
        },
        success:function(data){
            if(data.code == 1){
                var dataL = data.data.list[0];
                var list = data.data.list;
                $('#J_dataL a').attr('href','/sysNews/details?id='+dataL.newsId);
                $('#dataL_img').attr('src',dataL.pictureUrl);
                $('#dataL_dataTime').html(dataL.updateTime);
                $('#dataL_dataTitle').html(dataL.newsTitle);
                $('#dataL_contentValidity').html(dataL.contentValidity);
                var html = '';
				for(var i = 1 ; i < list.length ; i++){
					html += '<li class="margin-bottom-10">';
					html += '<a href="/sysNews/details?id='+list[i].newsId+'" target="_blank">';
					html += '<img src="'+list[i].pictureUrl+'" class="grid-left" alt="">';
					html += '<div class="infos grid-right">';
					html += '<h3>'+list[i].newsTitle+'</h3>';
					html += '<p class="infos-info">'+list[i].contentValidity+'</p>';
					html += '</div>';
					html += '</a>';
					html += '</li>';
                }
                $('#J_dataR').html(html);
            }
        }

    })
}
loadIndexData2()
function loadIndexData2(){
	$.ajax({
		url:host.website+'/sysNews/listByGroup',
		type:'get',
		data:{
			pageNumber:1,
			pageSize:10,
			newsGroup:'information',
			newsSonGroup:3
		},
		success:function(data){
			if(data.code == 1){
				var list = data.data.list;
				var html = '';
				for(var i = 0 ; i < list.length ; i++){
					html += '<li><span class="serial" style="display:inline-block;float:left;">'+(i+1)+'.</span><a style="width: 150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;display:inline-block;float:left;" target="_blank" href="/sysNews/details?id='+list[i].newsId+'">'+list[i].newsTitle+'</a><span class="grid-right" style="width:72px;height:40px;overflow:hidden;">'+list[i].updateTime+'</span></li>';
				}
				$('#J_dataRR').html(html);
			}
		}

	})
}
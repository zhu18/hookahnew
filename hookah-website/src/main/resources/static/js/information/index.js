function loadPageData(data) { //渲染页面数据
	// return console.log(JSON.stringify(data))
	var html = '';
	if (data.data.list.length > 0) {
		var list = data.data.list;
		for (var i = 0; i < list.length; i++) {
			html += '<div class="public padding-left-10 padding-top-30 padding-bottom-30 ">';
			html += '<a href="/sysNews/details?newsId=' + list[i].id + '" target="_blank">';
			html += '<img src="' + list[i].pictureUrl + '" alt="" class="margin-right-20 news-img">';
			html += '</a>';
			html += '<a href="/sysNews/details?newsId=' + list[i].id + '" target="_blank">';
			html += '<p class="margin-bottom-10 padding-right-50">' + list[i].newsTitle + '</p>';
			html += '</a>';
			html += '<a href="/sysNews/details?newsId=' + list[i].id + '" target="_blank">';
			html += '<p class="padding-right-50 margin-bottom-10" >'+judg(list[i].contentValidity)+'</p>';
			html += '</a>';
			html += '<div class="auth">';
			// html += '<img src="' + list[i].headImg + '" alt="">&nbsp;&nbsp;&nbsp;<span style="color:#B2B2B2;" class="padding-right-20">'+'管理员'+ '</span>';
			html += '<span ><svg class="icon" style="color:#F07D17;width: 1.0068359375em; height: 1em;vertical-align: middle;fill: currentColor;overflow: hidden;" viewBox="0 0 1031 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="15339"><path d="M18.2784 61.97248 432.4096 61.97248 432.4096 156.78464 386.37056 156.78464 570.40896 583.04512 754.58048 156.78464 662.49216 156.78464 662.49216 62.09536 1030.70208 62.09536 1030.70208 156.78464 892.70272 156.78464 524.48768 962.02752 432.4096 962.02752 110.3616 156.78464 18.2784 156.78464 18.2784 61.97248Z" p-id="15340"></path></svg></span>&nbsp;&nbsp;<span style="color:#B2B2B2;" class="padding-right-20">'+'管理员'+ '</span>';
			html += '<span class="padding-right-10"><svg class="icon" style="color:#D6D6D6;width: 1.4em; height: 1.4em;vertical-align: middle;fill: currentColor;overflow: hidden;" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="14461"><path d="M512 64C264.96 64 64 264.96 64 512s200.96 448 448 448 448-200.96 448-448S759.04 64 512 64z m159.264 512H480.16c-17.664 0-32.16-14.464-32.16-32.16V289.088c0-17.664 14.336-32 32-32s32 14.336 32 32V512h159.264c17.696 0 32 14.336 32 32 0 17.696-14.336 32-32 32z" p-id="14462"></path></svg></span>';
			html += '<span style="color:#B2B2B2;">' + list[i].sytTime + '</span>';
			html += '</div>';
			html += '<div class="line"></div>';
			html += '</div>';
		}
		$('.public-box').html(html);
	}else{
		html += '<div class="noData">暂无数据</div>';
		$('.public-box').html(html);
	}
}

function judg(text){
	if(text==null){
		return "暂无简介"
	}else{
		return text
	}
}

function renderChange(that, num) {
    console.log($(that));
    $(that).addClass('active').siblings().removeClass('active');
	var textT = $(that).children().children('.page-title').html();
	$('#J_pageTitle').html(textT);
	dataParm.newsSonGroup = num;
	goPage("1");
}

if(dataParm.newsSonGroup){
	var id=dataParm.newsSonGroup;
	if(id=="1"){
        $("#one").addClass('active').siblings().removeClass('active');
	}else if(id=="2"){
        $("#two").addClass('active').siblings().removeClass('active');
	}else if(id=="3"){
        $("#three").addClass('active').siblings().removeClass('active');
	};
    $("#J_pageTitle").html($("li.active .page-title").html());
}

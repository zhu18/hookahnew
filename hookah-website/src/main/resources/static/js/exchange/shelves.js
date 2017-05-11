function loadPageData(data){ //渲染页面数据
	// return alert(JSON.stringify(data))
	if(data.data.list.length > 0){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            var shopFormat = '';
            if(list[i].shopFormat == 0 ){
                shopFormat = '次';
            }else if(list[i].shopFormat == 1 ){
                shopFormat = '月';
            }else if(list[i].shopFormat == 2 ){
                shopFormat = '年';
            }
            html += '<li>';
            html += '<a class="item-top" href="/exchange/details?id='+list[i].goodsId+'">';
            html += '<p class="goods-img"><img src="'+list[i].goodsImg+'" alt=""/></p>';
            html += '<p class="goods-name">'+list[i].goodsName+'</p>';
            html += '<p class="goods-brief">'+list[i].goodsBrief+'</p>';
            html += '</a>';
            html += '<div class="item-down">';
            html += '<span class="grid-left goods-price">￥<span>'+Number(list[i].shopPrice)/100+'</span>/'+(list[i].shopNumber == 1 ? '':list[i].shopNumber)+shopFormat+'</span>';
            html += '<a class="grid-right btn btn-full-red padding-5 font-size-12 margin-top-10" href="/exchange/details?id='+list[i].goodsId+'">立即购买</a>';
            html += '</div>';
            html += '</li>';
        }
        $('.order-list ul').html(html);
    }else{
		$('.order-list ul').html('<div class="noData">暂无数据</div>');
	}

}

//四大排序
function sort() {//四类排序
    function flog() {//记录双击
        var flog=1;
        return function () {
            if(flog==1){
                dataParm.order='asc';
                flog=0;
                $(".arrow-box").css({
                    'transform':'rotateZ(180deg)'
                });
            }else {
                dataParm.order='desc';
                flog=1;
                $(".arrow-box").css({
                    'transform':'rotateZ(0deg)'
                });
            }
        }

    }
    var s=flog();
    var d=flog();
    var g=flog();
    var m=flog();
    $("#shopPrice").parent().prevAll().on('click',function () {
        $(this).find('a').addClass('active').parent().siblings().find('a').removeClass('active');
        $(this).find('a').find('.arrow-box').show().parent().parent().siblings().find('a').find('.arrow-box').hide()
        if($(this).find('a').attr('type')==='onSaleDate'){
            dataParm.orderField='onsaleStartDate';
            if(s==null){
                s=flog();
            }
            s();
            d=null;
            g=null;
            m=null;
        }else if ($(this).find('a').attr('type')==='orders'){
            dataParm.orderField='orders';
            if(d==null){
                d=flog();
            }
            d();
            s=null;
            g=null;
            m=null;
        }else if($(this).find('a').attr('type')==='commentRank'){
            dataParm.orderField='commentRank';
            if(g==null){
                g=flog();
            }
            g();
            s=null;
            d=null;
            m=null;
        }else {
            dataParm.orderField='';
            if(m==null){
                m=flog();
            }
            m();
            s=null;
            d=null;
            g=null;
        }
        goPage(1);
    })
    $("#shopPrice").parent().prevAll().find('a').find('.arrow-box').hide()
}
// 价格搜索
function price() {//价格排序 输入值*100处理
    dataParm.range.priceFrom='';
    dataParm.range.priceTo='';
    $('.ensure').on('click',function () {
        dataParm.range.priceFrom=$('#priceFrom').val()*100;
        dataParm.range.priceTo= $('#priceTo').val()*100;
        goPage(1);
    });
    $('.empty').on('click',function () {
        $('#priceFrom').val('');
        $('#priceTo').val('');
        dataParm.range.priceFrom="";
        dataParm.range.priceTo= "";
        goPage(1);
    });

}
sort();
price();






function loadPageData(data){
    if(data.data.list.length > 0){
        var list = data.data.list;
        console.log(list);
        var html = '';
        for(var i=0; i<list.length; i++){
        	// var item = list[i].mgOrderGoodsList;
            html += '<table>';
            html += '<thead>';
            html += '<tr>';
            html += '<th class="" style="width: 280px;">' + '订单号:' + list[i].orderSn + '</th>';
            html += '<th class="text-align-left"  colspan=2 style="position: relative;">创建时间:' + list[i].addTime + '</th>';
            // html += '<th></th>';
            html += '<th colspan="2" style="width:190px;">总额:￥' + (list[i].orderAmount / 100).toFixed(2) + '</th>';
            html += '</tr>';
            html += '</thead>';
            html += '<tbody>';
            var goods = list[i].mgOrderGoodsList;
            for (var ii = 0; ii < goods.length; ii++) {
                var mMat = null;
                switch (goods[ii].goodsFormat) {
                    case(0):
                        mMat = '次';
                        break;
                    case(1):
                        mMat = '天';
                        break;
                    case(2):
                        mMat = '年';
                        break;
                }
                var catidS = (goods[ii].catId).substring(0, 3);
                var isOfflineInfo='';
                var goodsTypeInfo='';
                if(goods[ii].goodsType==0){
                    goodsTypeInfo="数据源-离线数据"
                }else if(goods[ii].goodsType==1){
                    goodsTypeInfo="数据源-API"
                }else if(goods[ii].goodsType==2){
                    goodsTypeInfo="模型"
                }else if(goods[ii].goodsType==4){
                    goodsTypeInfo="分析工具-独立部署软件"
                }else if(goods[ii].goodsType==5){
                    goodsTypeInfo="分析工具-SaaS"
                }else if(goods[ii].goodsType==6){
                    goodsTypeInfo="应用场景-独立部署软件"
                }else if(goods[ii].goodsType==7){
                    goodsTypeInfo="应用场景-SaaS"
                }
                if(goods[ii].isOffline==1){
                    isOfflineInfo="交付方式：线下"
                }else if(goods[ii].isOffline==0){
                    isOfflineInfo="交付方式：线上"
                }
				/*case 0:
				 return '常规商品'; //离线数据包    数据源-离线数据
				 case 1:
				 return 'API'; //不做修改  下载     数据源-API
				 case 2:
				 return '数据模型';//模型       模型
				 case 4:
				 return '分析工具--独立软件';//独立部署软件     分析工具-独立部署软件
				 case 5:
				 return '分析工具--SaaS';//saas          分析工具-SaaS
				 case 6:
				 return '应用场景--独立软件';//独立部署软件     应用场景-独立部署软件
				 case 7:
				 return '应用场景--SaaS'; //saas    应用场景-SaaS
				 break;*/
                html += '<tr class="content border-bottom">';
                html += '<td class="text-align-center" style="width: 280px;">';
                html += '<div class="p-img">';
                html += '<a href="/exchange/details?id=' + goods[ii].goodsId + '" target="_blank">';
                html += '<img src="' + goods[ii].goodsImg + '" alt="">';
                html += '</a>';
                html += '</div>';
                html += '<div class="desc margin-top-10 marign-bottom-10" >';
                html += '<a href="/exchange/details?id=' + goods[ii].goodsId + '" target="_blank">' + goods[ii].goodsName + '</a>';
                html += '</div>';
                html += '</td>';
                html += '<td class="text-align-left">x' + goods[ii].goodsNumber +
                    '<br/>' + '规格:' + (goods[ii].goodsPrice / 100).toFixed(2) + '/' + mMat + '<br />'+ goodsTypeInfo+ '<br />'+ isOfflineInfo+'</td>';
                // html += '<td><a href="/exchange/orderEndDetails?id='+goods[ii].goodsId+'&orderSn='+list[i].orderSn+'">下载<br/><span class="fa fa-download font-size-18"></span></a></td>';
				/*
				 if (catidS == '104') {
				 html += '<td><a target="_blank" href="javascript:getDataPackageD(\'' + goods[ii].goodsId + '\' , \'' + goods[ii].sourceId + '\',\'' + list[i].orderSn + '\');">查看<br/><span class="fa fa-eye font-size-18"></span></a></td>';
				 } else {
				 */
                // html += '<td><a href="javascript:getDataPackageD(\'' + goods[ii].goodsId + '\' , \'' + goods[ii].sourceId + '\',\'' + list[i].orderSn + '\');">获取密钥<br/><span class="fa fa-download font-size-18"></span></a></td>';
                // if(goods[ii].goodsType == 1){ //如果是API产品 点击下载
                //     html += '<td><a href="javascript:getDataPackageD(\'' + goods[ii].goodsId + '\' , \'' + goods[ii].sourceId + '\',\'' + list[i].orderSn + '\');">交付信息<br/><span class="fa fa-download font-size-18"></span></a></td>';
                //
                // }else{
                //     html += '<td><a href="javascript:getKey(\'' + goods[ii].goodsId + '\' , \'' + goods[ii].sourceId + '\',\'' + list[i].orderId + '\',\'' + goods[ii].goodsType + '\',\'' + goods[ii].isOffline + '\');">交付信息<br/><span class="fa fa-download font-size-18"></span></a></td>';
                //
                // }

                // }
                html += '<td style="width:190px;" class="">金额:￥&nbsp;' + ((goods[ii].goodsPrice / 100) * goods[ii].goodsNumber).toFixed(2) + '<br/><br/>' + list[i].payName + '</td>';//订单总金额
                // html += '<td class="text-align-center">';
                // if (list[i].commentFlag == 0) {
                //     html += '<a target="_blank" href="/order/sunAlone?orderId=' + list[i].orderId + '" class="display-block">评价晒单</a>';
                // } else if (list[i].commentFlag == 1) {
                //     html += '<span class="display-block">已评价</span>';
                // }
                // html += '<br><a href="/exchange/details?id='+goods[ii].goodsId+'" class="display-inline-block goPay btn btn-full-orange">再次购买</a>';
                // html += '</td>';
                if (ii == 0) {
                    html += '<td rowspan="' + goods.length + '" class="border-left" style="width:190px;">';
                    html += '<span>已完成</span>';
                    // if (list[i].commentFlag == 0) {
                    //     html += '<a target="_blank" href="/order/sunAlone?orderId=' + list[i].orderId + '" class="display-block margin-top-5 margin-bottom-5">评价晒单</a>';
                    // } else if (list[i].commentFlag == 1) {
                    //     html += '<span class="display-block margin-top-5 margin-bottom-5">已评价</span>';
                    // }
                    // html += '<a href="/exchange/details?id=' + goods[ii].goodsId + '" class="display-inline-block goPay btn btn-full-orange margin-top-5 margin-bottom-5">再次购买</a>';
                    // html += '<a target="_blank" href="/order/viewDetails?orderId=' + list[i].orderId + '&num=1" class="display-block color-blue margin-top-5 margin-bottom-5">订单详情</a>';
                    html += '</td>';
                }
                html += '</tr>';
            }
            html += '</tbody>';
            html += '</table>';
			// for(var j=0; j < item.length; j++){
			// 	html += '<tr>';
			// 	html += '<td class="text-center">';
			// 	html += '<a href="/exchange/details?id='+item[j].goodsId+'">';
			// 	html += '<img src="'+item[j].goodsImg+'" alt="">';
			// 	html += '<p>'+item[j].goodsName+'</p>';
			// 	html += '</a>';
			// 	html += '</td>';
			// 	html += '<td>'+list[i].orderSn+'</td>';
			// 	html += '<td>'+list[i].shippingTime+'</td>';
			// 	html += '<td class="text-left">'+(item[j].goodsPrice / 100).toFixed(2)+'</td>';
			// 	html += '<td class="text-center">'+item[j].goodsNumber+'</td>';
			// 	html += '<td class="text-left">'+((item[j].goodsPrice * item[j].goodsNumber) / 100).toFixed(2)+'</td>';
			// 	html += '</tr>';
			// }
        }
        $('.trade-box').html(html);
    }else{
		$('.trade-box').html('<tr><td colspan="10"><div class="noData">暂无数据</div></td></tr>');
    }
}


var start = {
    format: "YYYY-MM-DD hh:mm:ss",
    isTime: true,
    maxDate: $.nowDate(0),
    choosefun: function (elem, datas) {
        end.minDate = datas; //开始日选好后，重置结束日的最小日期
    }
};
var end = {
    format: "YYYY-MM-DD hh:mm:ss",
    isTime: true,
    maxDate: $.nowDate(0),
    choosefun: function (elem, datas) {
        start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
    }
};
$.jeDate("#startDate", start);
$.jeDate("#endDate", end);


$("#search-btn").on('click',function () {
    console.log(1);
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    dataParm.startDate = startDate ? startDate : null;
    dataParm.endDate = endDate ? endDate : null;
    goPage(1);
});
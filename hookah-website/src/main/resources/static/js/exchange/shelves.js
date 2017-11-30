function loadPageData(data){ //渲染页面数据
        // return alert(JSON.stringify(data))
        if(data.data.list.length > 0){
            var list = data.data.list;
            var html = '';
			var goodsTypeName = '';
			var goodsTypeAllName = '';
			var purchaseLimitName = '';
			var purchaseLimitAllName = '';
            for(var i=0; i<list.length; i++){
                var shopFormat = '';
                if(list[i].shopFormat == 0 ){
                    shopFormat = '次';
                }else if(list[i].shopFormat == 1 ){
                    shopFormat = '天';
                }else if(list[i].shopFormat == 2 ){
                    shopFormat = '年';
                }else if(list[i].shopFormat == 3 ){
					shopFormat = '套';
				}
				switch(list[i].purchaseLimit)
				{
					case 0:
						purchaseLimitName = '通用';
						purchaseLimitAllName = '所有用户可买';
						break;
					case 1:
						purchaseLimitName = '个人';
						purchaseLimitAllName = '仅限个人用户';
						break;
					case 2:
						purchaseLimitName = '企业';
						purchaseLimitAllName = '仅限企业用户';
						break;
				}
				switch(list[i].goodsType)
				{
					case 0:
						goodsTypeName = '离线';
						goodsTypeAllName = '离线数据源';
						break;
					case 1:
						goodsTypeName = 'API';
						goodsTypeAllName = 'API数据';
						break;
					case 2:
						goodsTypeName = '模型';
						goodsTypeAllName = '模型算法';
						break;
					case 4:
						goodsTypeName = '软件';
						goodsTypeAllName = '独立平台';
						break;
					case 5:
						goodsTypeName = 'SaaS';
						goodsTypeAllName = 'SaaS应用';
						break;
					case 6:
						goodsTypeName = '软件';
						goodsTypeAllName = '独立平台';
						break;
					case 7:
						goodsTypeName = 'SaaS';
						goodsTypeAllName = 'SaaS应用';
						break;
				}
				var shopPrice = null;
				if(Number(list[i].shopPrice) >= 1000000){
					shopPrice = (Number(list[i].shopPrice) / 1000000)+'万';
				}else{
					shopPrice = Number(list[i].shopPrice) / 100
				}
                html += '<li>';
                html += '<a class="item-top" href="/exchange/details?id='+list[i].goodsId+'">';
                html += '<p class="goods-img"><img src="'+list[i].goodsImg+'" alt=""/></p>';
                html += '<p class="goods-name">'+list[i].goodsName+'</p>';
				html += '<p class="goods-tag"><span class="item-tag"><span class="tag-in"><span class="in-nol">'+goodsTypeAllName+'</span></span>'+goodsTypeName+'</span><span class="item-tag"><span class="tag-in"><span class="in-nol">'+purchaseLimitAllName+'</span></span>'+purchaseLimitName+'</span></p>';
                html += '</a>';
                html += '<div class="item-down">';
                // html += '<span class="grid-left goods-price">￥<span>'+shopPrice+'</span>/'+(list[i].shopNumber == 1 ? '':list[i].shopNumber)+shopFormat+'</span>';
				if(list[i].isDiscussPrice == 1){
					// html += '<span class="grid-left goods-price">面议参考价￥<span>'+shopPrice+'</span></span>';
					html += '<span class="grid-left goods-price">价格：<span>面议</span></span>';
				}else{
					html += '<span class="grid-left goods-price">￥<span>'+shopPrice+'</span>/'+(list[i].shopNumber == 1 ? '':list[i].shopNumber)+shopFormat+'</span>';
				}
				html += '<a class="grid-right btn btn-full-red padding-5 font-size-12 margin-top-10" target="_blank" href="/exchange/details?id='+list[i].goodsId+'">查看详情</a>';
                html += '</div>';
                html += '</li>';
            }
            $('.order-list ul').html(html);
        }else{
			$('.order-list ul').html('<div class="noData"><p><img src="/static/images/no_data_img.png" alt=""></p><p>商品正在上架中，敬请期待！</p></div>');
        }

    }



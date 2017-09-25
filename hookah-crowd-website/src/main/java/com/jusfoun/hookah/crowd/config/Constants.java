package com.jusfoun.hookah.crowd.config;


import com.jusfoun.hookah.crowd.util.PropertiesManager;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-17
 * <p>Version: 1.0
 */
public class Constants {

    public static String RESOURCE_SERVER_NAME = "";
    public static final String INVALID_CLIENT_DESCRIPTION = "客户端验证失败，如错误的client_id/client_secret。";

    /** ES 配置 */
    // 商品
    public static final String GOODS_INDEX = PropertiesManager.getInstance().getProperty("goods.index");
    public static final String GOODS_TYPE = PropertiesManager.getInstance().getProperty("goods.type");
    public static final Integer GOODS_SHARDS = Integer.valueOf(PropertiesManager.getInstance().getProperty("goods.index.shards"));
    public static final Integer GOODS_REPLICAS = Integer.valueOf(PropertiesManager.getInstance().getProperty("goods.index.replicas"));
    // 商品分类
    public static final String GOODS_CATEGORY_INDEX = PropertiesManager.getInstance().getProperty("goods.category.index");
    public static final String GOODS_CATEGORY_TYPE = PropertiesManager.getInstance().getProperty("goods.category.type");
    public static final Integer GOODS_CATEGORY_SHARDS = Integer.valueOf(PropertiesManager.getInstance().getProperty("goods.category.index.shards"));
    public static final Integer GOODS_CATEGORY_REPLICAS = Integer.valueOf(PropertiesManager.getInstance().getProperty("goods.category.index.replicas"));
}

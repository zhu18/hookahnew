package com.jusfoun.hookah.console.util;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by wangjl on 2017-3-13.
 */
public class ESClientUtil {
    public static TransportClient client = null;
    /**
     * 获取客户端
     * @return
     */
    public static  TransportClient getClient() throws UnknownHostException {
        if(client!=null){
            return client;
        }
        Settings settings = Settings.builder()
                .put("client.transport.sniff", PropertiesManager.getInstance().getProperty("es.client.transport.sniff"))
                .put("cluster.name", PropertiesManager.getInstance().getProperty("es.cluster.name"))
                .build();
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(PropertiesManager.getInstance().getProperty("es.ip")),
                        Integer.valueOf(PropertiesManager.getInstance().getProperty("es.port"))));
        return client;
    }
}

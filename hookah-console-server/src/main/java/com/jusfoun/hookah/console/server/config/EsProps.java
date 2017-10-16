package com.jusfoun.hookah.console.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjl on 2017-10-16.
 */
@Component
@ConfigurationProperties(prefix = "myconf.es")
public class EsProps {
    private String ipPort;
    private Map<String, String> cluster = new HashMap<>();
    private Map<String, String> clientTransport = new HashMap<>();
    private Map<String, String> goods = new HashMap<>();
    private Map<String, String> category = new HashMap<>();

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }

    public Map<String, String> getCluster() {
        return cluster;
    }

    public void setCluster(Map<String, String> cluster) {
        this.cluster = cluster;
    }

    public Map<String, String> getClientTransport() {
        return clientTransport;
    }

    public void setClientTransport(Map<String, String> clientTransport) {
        this.clientTransport = clientTransport;
    }

    public Map<String, String> getGoods() {
        return goods;
    }

    public void setGoods(Map<String, String> goods) {
        this.goods = goods;
    }

    public Map<String, String> getCategory() {
        return category;
    }

    public void setCategory(Map<String, String> category) {
        this.category = category;
    }
}

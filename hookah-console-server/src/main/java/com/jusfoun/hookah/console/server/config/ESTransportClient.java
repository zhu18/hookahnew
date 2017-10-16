package com.jusfoun.hookah.console.server.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.*;

@Component
public class ESTransportClient implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(ESTransportClient.class);
    private TransportClient client;
    private Properties properties;
    static final String COLON = ":";
    static final String COMMA = ",";
    @Resource
    EsProps esProps;


    @Override
    public void destroy() throws Exception {
        try {
            logger.info("Closing elasticSearch  client");
            if (client != null) {
                client.close();
            }
        } catch (final Exception e) {
            logger.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public TransportClient getObject() throws Exception {
        return client;
    }

    @Override
    public Class<TransportClient> getObjectType() {
        return TransportClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        buildClient();
    }

    protected void buildClient() throws Exception {
        client = new PreBuiltTransportClient(settings());
        for (String clusterNode : split(esProps.getIpPort(), COMMA)) {
            String hostName = substringBeforeLast(clusterNode, COLON);
            String port = substringAfterLast(clusterNode, COLON);
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port)));
        }
        client.connectedNodes();
    }

    private Settings settings() {
        if (properties != null) {
            return Settings.builder().put(properties).build();
        }
        return Settings.builder()
                .put("cluster.name", esProps.getCluster().get("name"))
                .put("client.transport.sniff", esProps.getClientTransport().get("sniff"))
                .put("client.transport.ignore_cluster_name", esProps.getClientTransport().get("ignore_cluster_name"))
                .put("client.transport.ping_timeout", esProps.getClientTransport().get("ping_timeout"))
                .put("client.transport.nodes_sampler_interval", esProps.getClientTransport().get("nodes_sampler_interval"))
                .build();
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
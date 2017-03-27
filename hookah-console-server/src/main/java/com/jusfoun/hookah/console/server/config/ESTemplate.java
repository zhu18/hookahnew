package com.jusfoun.hookah.console.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangjl on 2017-3-27.
 */
@Component
public class ESTemplate {
    @Autowired
    ESTransportClient esTransportClient;


}

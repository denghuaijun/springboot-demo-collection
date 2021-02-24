package com.dhj.demo.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 开启websocket支持
 * @author dhj
 */
@Component
public class WebSocketConfig {
    /**
     * 扫描并注入带有@ServerEndPoint注解的所有服务端
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}


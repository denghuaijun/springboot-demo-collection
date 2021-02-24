package com.dhj.demo.websocket.config;

/**
 * websocket的配置类
 * @ClassName: CustomWebSocketConfig
 * @Description: TODO
 *
 */
//@Configuration
//@EnableWebSocket
//public class CustomWebSocketConfig implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(customWebSocketHandler(), "/webSocketBySpring/customWebSocketHandler").addInterceptors(new CustomWebSocketInterceptor()).setAllowedOrigins("*");
//        registry.addHandler(customWebSocketHandler(), "/sockjs/webSocketBySpring/customWebSocketHandler").addInterceptors(new CustomWebSocketInterceptor()).setAllowedOrigins("*").withSockJS();
//    }
//
//    @Bean
//    public WebSocketHandler customWebSocketHandler() {
//        return new CustomWebSocketHandler();
//    }
//}
package com.dhj.demo.websocket;

import com.dhj.demo.websocket.client.CustomWebSocketClient;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.drafts.Draft_6455;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;

@Slf4j
@SpringBootTest
public class WebSocketTest {


    @Test
    public void testWebSocket()  {
       // URI uri = new URI("ws://10.200.10.162:8080/webSocketBySpring/customWebSocketHandler?mchNo="+123);
        CustomWebSocketClient socketClient = null;
        try {
            socketClient = new CustomWebSocketClient(new URI("ws://10.200.10.162:8888/websocket"),new Draft_6455());
            socketClient.connect();
            /*while (!socketClient.getReadyState().equals(ReadyState.OPEN)) {
                System.out.println("连接中···请稍后");
            }*/
            socketClient.send("这是测试");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

package com.dhj.demo.websocket.client;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * 自定义websocket客户端
 */
public class CustomWebSocketClient extends WebSocketClient {
    public CustomWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public CustomWebSocketClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("###########打开websocket客户端########");
    }

    @Override
    public void onMessage(String s) {
        System.out.println("###########打开websocket客户端发送消息########");

    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("###########打开websocket客户端关闭########");

    }

    @Override
    public void onError(Exception e) {
        System.out.println("错误信息："+e.getMessage());
        System.out.println("###########打开websocket客户端错误########");

    }
}

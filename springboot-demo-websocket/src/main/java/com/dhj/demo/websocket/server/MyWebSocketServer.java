package com.dhj.demo.websocket.server;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint(value = "/websocket/{name}")
public class MyWebSocketServer {
    private Logger logger = LoggerFactory.getLogger(MyWebSocketServer.class);
    /** 用来记录当前在线连接数。设计成线程安全的。*/
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    /** 用于保存uri对应的连接服务，{uri:WebSocketServer}，设计成线程安全的 */
    private static ConcurrentHashMap<String, MyWebSocketServer> webSocketServerMAP = new ConcurrentHashMap<>();
    private Session session;
    /**
     * 标识当前连接客户端的用户名
     */
    private String name;

    /**
 　　* 连接建立后触发的方法
 　　 */
    @OnOpen
    public void onOpen(Session session){
    this.session = session;
    logger.info("onOpen"+session.getId());
    webSocketServerMAP.put(session.getId(),this);
    }


    /**
 　　* 连接关闭后触发的方法
 　　*/
    @OnClose
    public void onClose(){
        //从map中删除
        webSocketServerMAP.remove(session.getId());
        logger.info("====== onClose:"+session.getId()+" ======");
    }


    /**
 　　 * 接收到客户端消息时触发的方法
 　　 */
    @OnMessage
    public void onMessage(String params,Session session) throws Exception{
        //获取服务端到客户端的通道
        MyWebSocketServer myWebSocket = webSocketServerMAP.get(session.getId());
        logger.info("收到来自"+session.getId()+"的消息"+params);
        String result = "收到来自"+session.getId()+"的消息"+params;
        //返回消息给Web Socket客户端（浏览器）
        myWebSocket.sendMessage(1,"success",result);

    }


    /**
 　　 * 发生错误时触发的方法
 　　*/
    @OnError
    public void onError(Session session,Throwable error){
        logger.info(session.getId()+"连接发生错误"+error.getMessage());
        error.printStackTrace();
    }

  public void sendMessage(int status,String message,Object datas) throws IOException {
        JSONObject result = new JSONObject();
        result.put("status", status);
        result.put("message", message);
        result.put("datas", datas);
        this.session.getBasicRemote().sendText(result.toString());
    }

}

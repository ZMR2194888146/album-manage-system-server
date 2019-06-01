package site.aboy.album.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/ws/{sid}")
@Component
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    private static CopyOnWriteArraySet<WebSocketServer> webSocketServers = new CopyOnWriteArraySet<>();

    private Session session;
    private String sid;

    public static void sendMessage(String sid, String message) {
        for (WebSocketServer server : webSocketServers) {
            if (server.sid.equals(sid)) {
                logger.info("sendMessage:" + message);
                try {
                    server.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.sid = sid;
        this.session = session;
        webSocketServers.add(this);     //加入set中
        logger.info("have new connect: sid====> " + sid);
    }

    @OnClose
    public void onClose() {
        webSocketServers.remove(this);  //从set中删除
        System.out.println("有一连接关闭！");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }
}
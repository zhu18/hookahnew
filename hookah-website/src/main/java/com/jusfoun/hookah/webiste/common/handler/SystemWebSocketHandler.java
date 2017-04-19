package com.jusfoun.hookah.webiste.common.handler;
/**
 * @author:jsshao
 * @date: 2017-4-14
 */

import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.SysMessage;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author dayu
 */

@Component
public class SystemWebSocketHandler extends TextWebSocketHandler {
    protected Logger logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);
    private static long count = 0;
    private static Map<String, WebSocketSession> sessionMap = new HashedMap();

    @Autowired
    MqSenderService senderService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionMap.put(session.getPrincipal().getName(), session);
        super.afterConnectionEstablished(session);

        System.out.println("connect to the websocket success......");
        session.sendMessage(new TextMessage("Server:connected OK!"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionMap.remove(session.getPrincipal().getName());
        super.afterConnectionClosed(session, status);

        System.out.println("connect closed......");
    }


    @Override
    public void handleMessage(WebSocketSession wss, WebSocketMessage<?> wsm) throws Exception {
        TextMessage returnMessage = new TextMessage(wsm.getPayload()
                + " received at server");
        System.out.println(wss.getPrincipal().getName());
        SysMessage message = new SysMessage();
        message.setSenderId(wss.getPrincipal().getName());
        message.setMsgText(returnMessage.getPayload());
        message.setType((short)1);
        message.setCreateTime(new Date());

        senderService.sendDirect(RabbitmqQueue.CONTRACE_MESSAGE,message);
    }


    /**
     * 发送消息
     *
     * @author xiaojf 2017/3/2 11:43
     */
    public static void sendMessage(String username, String message) throws IOException {
        sendMessage(Arrays.asList(username), Arrays.asList(message));
    }

    /**
     * 发送消息
     *
     * @author xiaojf 2017/3/2 11:43
     */
    public static void sendMessage(Collection<String> acceptorList, String message) throws IOException {
        sendMessage(acceptorList, Arrays.asList(message));
    }

    /**
     * 发送消息，p2p 群发都支持
     *
     * @author xiaojf 2017/3/2 11:43
     */
    public static void sendMessage(Collection<String> acceptorList, Collection<String> msgList) throws IOException {
        if (acceptorList != null && msgList != null) {
            for (String acceptor : acceptorList) {
                WebSocketSession session = sessionMap.get(acceptor);
                if (session != null) {
                    for (String msg : msgList) {
                        session.sendMessage(new TextMessage(msg.getBytes()));
                    }
                }
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession wss, Throwable thrwbl) throws Exception {
        if(wss.isOpen()){
            wss.close();
        }
        System.out.println("websocket connection closed......");
    }



    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
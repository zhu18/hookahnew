package com.jusfoun.hookah.console.server.common.endpoint;

/**
 * @author:jsshao
 * @date: 2017-4-14
 */

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class InfoSocketEndPoint extends TextWebSocketHandler {
    public InfoSocketEndPoint() {
    }

    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        TextMessage returnMessage = new TextMessage(message.getPayload()
                + " received at server");
        session.sendMessage(returnMessage);
    }
}
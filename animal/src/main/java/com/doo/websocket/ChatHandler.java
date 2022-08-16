package com.doo.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatHandler extends TextWebSocketHandler {

	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionList.add(session);
//		for (WebSocketSession sess : sessionList) {
//			sess.sendMessage(new TextMessage(sessionList.size()+""));
//		}
	}
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
		String str=message.getPayload();
		//sessionList.add(session);
		for (WebSocketSession sess : sessionList) {
			if(sess!=session) {
				sess.sendMessage(new TextMessage(str));
			}
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessionList.remove(session);
//		for (WebSocketSession sess : sessionList) {
//			sess.sendMessage(new TextMessage(sessionList.size()+""));
//		}
	}
}

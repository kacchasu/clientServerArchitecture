package com.example.aksp5;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
@EnableScheduling
@Component
public class ServerWebSocketHandler extends TextWebSocketHandler {

    private final ChatMessageService chatMessageService;
    private final Queue<WebSocketSession> sessions = new ConcurrentLinkedQueue<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        chatMessageService.save(
                ChatMessage.builder()
                        .senderSessionId(session.getId())
                        .content(message.getPayload())
                        .creationTimestamp(now)
                        .isSent(false)
                        .build());
    }

    @Scheduled(fixedDelay = 10000)
    void sendPeriodicMessages() {
        chatMessageService.clearSentMessages();

        List<ChatMessage> messages = chatMessageService.findAll();
        if (messages.isEmpty()) return;
        String messagesBatch = getConcatenatedMessage(messages);

        sessions.stream()
                .filter(WebSocketSession::isOpen)
                .forEach(webSocketSession -> {
                    try {
                        webSocketSession.sendMessage(new TextMessage(messagesBatch));
                    } catch (IOException e) {
                        //TODO: logger.log(e);
                    }
                });

        messages.forEach(chatMessage -> chatMessage.setIsSent(true));
        chatMessageService.saveAllAndFlush(messages);
    }

    private String getConcatenatedMessage(Iterable<ChatMessage> messages) {
        StringBuilder stringBuilder = new StringBuilder();
        messages.forEach(m -> stringBuilder
                .append(m.toString())
                .append("\n"));

        return stringBuilder.toString();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        super.afterConnectionClosed(session, status);
    }
}

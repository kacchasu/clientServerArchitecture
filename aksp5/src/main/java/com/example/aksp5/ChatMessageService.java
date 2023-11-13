package com.example.aksp5;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public void save(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findAll() {
        return chatMessageRepository.findAll();
    }

    @Transactional
    public void clearSentMessages() {
        chatMessageRepository.deleteAllByIsSent(true);
    }

    public void saveAllAndFlush(List<ChatMessage> messages) {
        chatMessageRepository.saveAllAndFlush(messages);
    }
}

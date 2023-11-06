package com.example.fitness.config;

import com.example.fitness.exception.UserNotFoundException;
import com.example.fitness.model.Message;
import com.example.fitness.model.User;
import com.example.fitness.model.request.ChatMessage;
import com.example.fitness.repository.MessageRepository;
import com.example.fitness.repository.UserRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
@Controller
public class WebSocketController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(MessageRepository messageRepository, UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendPrivateMessage")
    @SendTo("/topic/public")
    public Message sendPrivateMessage(@Payload ChatMessage chatMessage) {
        System.out.println(chatMessage.getDateTime());
        User sender = userRepository.findById(chatMessage.getSenderUserId()).orElse(null);
        User receiver = userRepository.findById(chatMessage.getReceiverUserId()).orElse(null);

        if (sender == null) {
            throw new UserNotFoundException("A küldő felhasználó nem található!");
        }
        if (receiver == null) {
            throw new UserNotFoundException("A fogadó felhasználó nem található!");
        }
        // Mentés az adatbázisba
        Message entity = new Message();
        entity.setSenderUser(sender);
        entity.setReceiverUser(receiver);
        entity.setMessage(chatMessage.getMessage());
        entity.setDateTime(chatMessage.getDateTime());
        entity.setReaded(false);
        messageRepository.save(entity);

        // Üzenet küldése a megfelelő címzettnek (receiverUserId)
        return entity;
    }
}

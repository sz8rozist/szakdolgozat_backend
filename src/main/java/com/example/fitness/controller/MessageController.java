package com.example.fitness.controller;


import com.example.fitness.exception.UserNotFoundException;
import com.example.fitness.model.Message;
import com.example.fitness.model.User;
import com.example.fitness.model.request.ChatMessage;
import com.example.fitness.repository.MessageRepository;
import com.example.fitness.repository.UserRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/message")
@CrossOrigin(value = "*", maxAge = 0)
public class MessageController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;
    public MessageController(MessageRepository messageRepository, UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }



}

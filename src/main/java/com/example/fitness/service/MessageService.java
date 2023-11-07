package com.example.fitness.service;

import com.example.fitness.model.Message;
import com.example.fitness.repository.MessageRepository;
import com.example.fitness.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<Message> getAllMessage(Integer senderUserId, Integer receiverUserId) {
        List<Message> messages = messageRepository.findAllBySenderUserIdAndReceiverUserId(senderUserId,receiverUserId);
        List<Message> messages1 = messageRepository.findAllBySenderUserIdAndReceiverUserId(receiverUserId, senderUserId);
        messages.addAll(messages1);
        messages.sort(Comparator.comparing(Message::getId));
        return messages;
    }
}

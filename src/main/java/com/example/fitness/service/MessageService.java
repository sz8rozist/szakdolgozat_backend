package com.example.fitness.service;

import com.example.fitness.model.Message;
import com.example.fitness.model.Trainer;
import com.example.fitness.model.dto.MessageDto;
import com.example.fitness.repository.MessageRepository;
import com.example.fitness.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<MessageDto> getAllMessage(Integer senderUserId, Integer receiverUserId) {
        List<Message> messages = messageRepository.findAllBySenderUserIdAndReceiverUserId(senderUserId,receiverUserId);
        List<Message> messages1 = messageRepository.findAllBySenderUserIdAndReceiverUserId(receiverUserId, senderUserId);
        messages.addAll(messages1);
        messages.sort(Comparator.comparing(Message::getId));
        List<MessageDto> messageDtoList = messages.stream()
                .sorted(Comparator.comparing(Message::getId))
                .map(m -> {
                    MessageDto messageDto = new MessageDto();
                    messageDto.setId(m.getId());
                    messageDto.setMessage(m.getMessage());
                    messageDto.setReaded(m.getReaded());
                    messageDto.setDateTime(m.getDateTime());
                    messageDto.setSenderUserId(m.getSenderUser().getId());
                    messageDto.setReceiverUserId(m.getReceiverUser().getId());
                    messageDto.setSenderUserProfilePicture(m.getSenderUser().getProfilePictureName());
                    messageDto.setReceiverUserProfilePicture(m.getReceiverUser().getProfilePictureName());

                    m.getSenderUser().getTrainer().ifPresent(trainer -> {
                        messageDto.setSenderUserFirstName(trainer.getFirst_name());
                        messageDto.setSenderUserLastName(trainer.getLast_name());
                    });
                    m.getSenderUser().getGuest().ifPresent(guest -> {
                        messageDto.setSenderUserFirstName(guest.getFirst_name());
                        messageDto.setSenderUserLastName(guest.getLast_name());
                    });

                    m.getReceiverUser().getTrainer().ifPresent(trainer -> {
                        messageDto.setReceiverUserFirstName(trainer.getFirst_name());
                        messageDto.setReceiverUserLastName(trainer.getLast_name());
                    });
                    m.getReceiverUser().getGuest().ifPresent(guest -> {
                        messageDto.setReceiverUserFirstName(guest.getFirst_name());
                        messageDto.setReceiverUserLastName(guest.getLast_name());
                    });
                    return messageDto;
                })
                .collect(Collectors.toList());

        return messageDtoList;
    }
}

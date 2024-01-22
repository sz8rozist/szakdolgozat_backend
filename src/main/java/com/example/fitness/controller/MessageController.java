package com.example.fitness.controller;


import com.example.fitness.model.Message;
import com.example.fitness.model.dto.MessageDto;
import com.example.fitness.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@CrossOrigin(value = "*", maxAge = 0)
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{senderUserId}/{receiverUserId}")
    public List<MessageDto> getMessages(@PathVariable Integer senderUserId, @PathVariable Integer receiverUserId){
        return messageService.getAllMessage(senderUserId, receiverUserId);
    }

    @PostMapping("/{messageId}/markAsRead")
    @ResponseStatus(HttpStatus.OK)
    public void markAsRead(@PathVariable Integer messageId){
        messageService.markAsRead(messageId);
    }
}

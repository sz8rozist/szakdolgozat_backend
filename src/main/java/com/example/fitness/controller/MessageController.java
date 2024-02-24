package com.example.fitness.controller;


import com.example.fitness.model.Message;
import com.example.fitness.model.dto.MessageDto;
import com.example.fitness.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('GUEST', 'TRAINER')")
    public List<MessageDto> getMessages(@PathVariable Integer senderUserId, @PathVariable Integer receiverUserId){
        return messageService.getAllMessage(senderUserId, receiverUserId);
    }

    @PostMapping("/{messageId}/markAsRead")
    @PreAuthorize("hasAnyAuthority('GUEST', 'TRAINER')")
    @ResponseStatus(HttpStatus.OK)
    public void markAsRead(@PathVariable Integer messageId){
        messageService.markAsRead(messageId);
    }
}

package com.example.fitness.model.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ChatMessage {
    private Integer senderUserId;
    private Integer receiverUserId;
    private String message;
    private LocalDateTime dateTime;
}

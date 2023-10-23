package com.example.fitness.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "message")
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String message;
    @Temporal(TemporalType.TIMESTAMP)
    private String dateTime;

    private Boolean readed;

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private User senderUser;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id")
    private User receiverUser;

}

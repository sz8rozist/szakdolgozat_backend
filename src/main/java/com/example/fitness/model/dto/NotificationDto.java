package com.example.fitness.model.dto;

import com.example.fitness.model.NotificationType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class NotificationDto {
    private int notificationId;
    private String message;
    private String trainerFirstName;
    private String trainerLastName;
    private String guestFirstName;
    private String guestLastName;
    private boolean viewed;
    private NotificationType notificationType;
    private LocalDate date;
}

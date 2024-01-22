package com.example.fitness.model.dto;

import com.example.fitness.model.NotificationType;
import lombok.*;

import java.time.LocalDate;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private Integer notificationId;
    private String message;
    private NotificationType type;
    private boolean viewed;
    private LocalDate date;
    private String trainerFirstName;
    private String trainerLastName;
    private String guestFirstName;
    private String guestLastName;
}



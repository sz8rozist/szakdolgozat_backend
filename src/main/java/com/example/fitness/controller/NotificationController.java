package com.example.fitness.controller;

import com.example.fitness.model.dto.NotificationDto;
import com.example.fitness.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/notification")
@CrossOrigin(value = "*", maxAge = 0)
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
@GetMapping("/{trainerId}")
    public List<NotificationDto> getLastFiveDietNotificationForTrainer(@PathVariable Integer trainerId){
        return notificationService.getLastFiveDietNotificationForTrainer(trainerId);
    }

}

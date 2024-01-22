package com.example.fitness.controller;

import com.example.fitness.model.Notification;
import com.example.fitness.model.dto.NotificationDto;
import com.example.fitness.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
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
    @GetMapping("/{userId}")
    public List<NotificationDto> getAll(@PathVariable Integer userId){
        return notificationService.getAllNotification(userId);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        notificationService.delete(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markAsViewed(@PathVariable Integer id){
        notificationService.markAsViewed(id);
    }
    @PutMapping("/all/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markAllAsViewed(@PathVariable Integer userId){
        notificationService.markAllAsViewed(userId);
    }
}

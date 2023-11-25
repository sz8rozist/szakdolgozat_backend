package com.example.fitness.service;

import com.example.fitness.model.Notification;
import com.example.fitness.model.NotificationType;
import com.example.fitness.model.dto.NotificationDto;
import com.example.fitness.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationDto> getLastFiveDietNotificationForTrainer(Integer trainerId) {
        return notificationRepository.findAllByTrainerIdAndTypeOrderByIdAsc(trainerId, NotificationType.DIET)
                .stream()
                .map(n -> {
                    NotificationDto notificationDto = new NotificationDto();
                    notificationDto.setNotificationId(n.getId());
                    notificationDto.setMessage(n.getMessage());
                    notificationDto.setNotificationType(n.getType());
                    notificationDto.setViewed(n.isViewed());
                    notificationDto.setTrainerFirstName(n.getTrainer().getFirst_name());
                    notificationDto.setTrainerLastName(n.getTrainer().getLast_name());
                    notificationDto.setGuestFirstName(n.getGuest().getFirst_name());
                    notificationDto.setGuestLastName(n.getGuest().getLast_name());
                    notificationDto.setDate(n.getDate());
                    return notificationDto;
                })
                .collect(Collectors.toList());
    }
}

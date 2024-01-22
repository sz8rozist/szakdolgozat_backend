package com.example.fitness.service;

import com.example.fitness.exception.NotificationNotFoundException;
import com.example.fitness.exception.UserNotFoundException;
import com.example.fitness.model.Notification;
import com.example.fitness.model.Role;
import com.example.fitness.model.RoleEnumType;
import com.example.fitness.model.User;
import com.example.fitness.model.dto.NotificationDto;
import com.example.fitness.repository.NotificationRepository;
import com.example.fitness.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public List<NotificationDto> getAllNotification(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Nem található felhasználó!"));
        List<Role> roles = user.getRoles();

        if (roles.stream().anyMatch(role -> role.getRole() == RoleEnumType.GUEST)) {
            return notificationRepository.findAllGuestNotification(user.getGuest().orElseThrow().getId());
        } else if (roles.stream().anyMatch(role -> role.getRole() == RoleEnumType.TRAINER)) {
            return notificationRepository.findAllTrainerNotifications(user.getTrainer().orElseThrow().getId());
        } else {
            return new ArrayList<>();
        }
    }

    public void delete(Integer id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new NotificationNotFoundException("Nem található értesítés."));
        notificationRepository.delete(notification);
    }

    public void markAsViewed(Integer id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new NotificationNotFoundException("Nem található értesítés."));
        notification.setViewed(true);
        notificationRepository.save(notification);
    }

    public void markAllAsViewed(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Nem található felhasználó!"));
        List<Role> roles = user.getRoles();

        if (roles.stream().anyMatch(role -> role.getRole() == RoleEnumType.GUEST)) {
            List<NotificationDto> notificationList = notificationRepository.findAllGuestNotification(user.getGuest().get().getId());
            createNotificationList(notificationList);
        } else if (roles.stream().anyMatch(role -> role.getRole() == RoleEnumType.TRAINER)) {
            List<NotificationDto> notificationList = notificationRepository.findAllTrainerNotifications(user.getTrainer().get().getId());
            createNotificationList(notificationList);
        }
    }

    private void createNotificationList(List<NotificationDto> notificationList) {
        List<Notification> notifications = new ArrayList<>();
        for (NotificationDto dto : notificationList) {
            Notification notification = notificationRepository.findById(dto.getNotificationId()).orElseThrow(() -> new NotificationNotFoundException("Nem található értesítés: " + dto.getNotificationId()));
            if(!notification.isViewed()){
                notification.setViewed(true);
                notifications.add(notification);
            }
        }
        notificationRepository.saveAll(notifications);
    }
}

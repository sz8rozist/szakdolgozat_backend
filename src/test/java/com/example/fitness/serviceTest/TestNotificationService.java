package com.example.fitness.serviceTest;

import com.example.fitness.model.Notification;
import com.example.fitness.model.Role;
import com.example.fitness.model.RoleEnumType;
import com.example.fitness.model.User;
import com.example.fitness.model.dto.NotificationDto;
import com.example.fitness.repository.NotificationRepository;
import com.example.fitness.repository.UserRepository;
import com.example.fitness.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class TestNotificationService {
    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testDeleteNotification() {
        // Arrange
        when(notificationRepository.findById(anyInt())).thenReturn(Optional.of(new Notification()));

        // Act
        assertDoesNotThrow(() -> notificationService.delete(1));

        // Assert
        verify(notificationRepository, times(1)).delete(any());
    }

    @Test
    public void testMarkAsViewed() {
        // Arrange
        Notification notification = new Notification();
        when(notificationRepository.findById(anyInt())).thenReturn(Optional.of(notification));

        // Act
        assertDoesNotThrow(() -> notificationService.markAsViewed(1));

        // Assert
        assertTrue(notification.isViewed());
        verify(notificationRepository, times(1)).save(notification);
    }
}

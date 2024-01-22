package com.example.fitness.repository;

import com.example.fitness.model.Notification;
import com.example.fitness.model.NotificationType;
import com.example.fitness.model.dto.NotificationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("SELECT new com.example.fitness.model.dto.NotificationDto(n.id,n.message, n.type, n.viewed, n.date, t.first_name, t.last_name, g.first_name, g.last_name) FROM Notification n JOIN n.trainer t JOIN n.guest g WHERE t.id = :trainerId AND (n.type = 'DIET' OR n.type = 'EXERCISE') ORDER BY n.id DESC")
    List<NotificationDto> findAllTrainerNotifications(@Param("trainerId") Integer trainerId);
    @Query("SELECT new com.example.fitness.model.dto.NotificationDto(n.id,n.message, n.type, n.viewed, n.date, t.first_name, t.last_name, g.first_name, g.last_name) FROM Notification n JOIN n.trainer t JOIN n.guest g WHERE g.id = :guestId AND n.type = 'FEEDBACK' ORDER BY n.id DESC")
    List<NotificationDto> findAllGuestNotification(@Param("guestId") Integer guestId);
}
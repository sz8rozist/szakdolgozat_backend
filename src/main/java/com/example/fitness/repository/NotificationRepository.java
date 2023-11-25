package com.example.fitness.repository;

import com.example.fitness.model.Notification;
import com.example.fitness.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllByTrainerIdAndTypeOrderByIdAsc(Integer trainer_id, NotificationType type);
}
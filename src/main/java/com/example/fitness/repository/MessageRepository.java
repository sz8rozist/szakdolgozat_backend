package com.example.fitness.repository;

import com.example.fitness.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllBySenderUserIdAndReceiverUserId(Integer senderUserId, Integer receiverUserId);
}

package com.example.fitness.repository;

import com.example.fitness.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllBySenderUserIdAndReceiverUserId(Integer senderUserId, Integer receiverUserId);
    @Query("SELECT m FROM Message m WHERE m.senderUser.id = :senderUserId AND m.receiverUser.id = :receiverUserId AND m.readed = false ORDER BY m.id DESC LIMIT 1")
    Message getLastMessage(Integer receiverUserId, Integer senderUserId);
}

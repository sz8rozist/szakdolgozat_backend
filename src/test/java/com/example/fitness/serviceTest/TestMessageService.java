package com.example.fitness.serviceTest;
import com.example.fitness.model.Message;
import com.example.fitness.model.User;
import com.example.fitness.model.dto.MessageDto;
import com.example.fitness.repository.MessageRepository;
import com.example.fitness.repository.UserRepository;
import com.example.fitness.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TestMessageService {
    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMessage() {
        Integer senderUserId = 1;
        Integer receiverUserId = 2;

        User senderUser = new User();
        senderUser.setId(senderUserId);
        senderUser.setProfilePictureName("sender_profile.jpg");

        User receiverUser = new User();
        receiverUser.setId(receiverUserId);
        receiverUser.setProfilePictureName("receiver_profile.jpg");

        Message message1 = new Message();
        message1.setId(1);
        message1.setMessage("Hello");
        message1.setReaded(false);
        message1.setDateTime(String.valueOf(LocalDate.now()));
        message1.setSenderUser(senderUser);
        message1.setReceiverUser(receiverUser);

        Message message2 = new Message();
        message2.setId(2);
        message2.setMessage("Hi");
        message2.setReaded(true);
        message2.setDateTime(String.valueOf(LocalDate.now()));
        message2.setSenderUser(receiverUser);
        message2.setReceiverUser(senderUser);

        List<Message> messages = Arrays.asList(message1, message2);

        when(messageRepository.findAllBySenderUserIdAndReceiverUserId(senderUserId, receiverUserId)).thenReturn(messages);
        when(messageRepository.findAllBySenderUserIdAndReceiverUserId(receiverUserId, senderUserId)).thenReturn(new ArrayList<>());

        when(userRepository.findById(senderUserId)).thenReturn(Optional.of(senderUser));
        when(userRepository.findById(receiverUserId)).thenReturn(Optional.of(receiverUser));

        List<MessageDto> result = messageService.getAllMessage(senderUserId, receiverUserId);

        assertNotNull(result);
        assertEquals(2, result.size());

    }

    @Test
    public void testMarkAsRead() {
        Integer messageId = 1;

        Message message = new Message();
        message.setId(messageId);
        message.setMessage("Hello");
        message.setReaded(false);

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

        assertDoesNotThrow(() -> messageService.markAsRead(messageId));

        assertTrue(message.getReaded());
    }
}

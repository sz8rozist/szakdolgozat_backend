package com.example.fitness.config;

import com.example.fitness.model.*;
import com.example.fitness.model.dto.GuestFeedbackDto;
import com.example.fitness.model.dto.MessageDto;
import com.example.fitness.model.dto.NotificationDto;
import com.example.fitness.model.dto.TrainerDietNotificationRequestDto;
import com.example.fitness.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestWebsocketController {
    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private DietRepository dietRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private WebSocketController webSocketController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void sendPrivateMessageTest() {
        // Mock data
        MessageDto messageDto = new MessageDto();
        messageDto.setSenderUserId(1);
        messageDto.setReceiverUserId(2);
        messageDto.setMessage("Test message");
        messageDto.setDateTime(String.valueOf(LocalDateTime.now()));
        messageDto.setReaded(false);

        User sender = new User();
        sender.setId(1);
        User receiver = new User();
        receiver.setId(2);

        when(userRepository.findById(1)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2)).thenReturn(Optional.of(receiver));
        when(messageRepository.save(any(Message.class))).thenReturn(new Message());

        // Execute the method
        webSocketController.sendPrivateMessage(2, messageDto);

        // Verify that the messageRepository.save method was called
        verify(messageRepository, times(1)).save(any(Message.class));

        // Verify that messagingTemplate.convertAndSend method was called
        verify(messagingTemplate, times(1)).convertAndSend(eq("/queue/private/2"), any(MessageDto.class));
    }

    @Test
    void getNotificationDtoTest() {
        // Mock data
        Notification notification = new Notification();
        notification.setId(1);
        notification.setDate(LocalDate.now());
        notification.setViewed(false);
        notification.setMessage("Test Message");
        notification.setType(NotificationType.DIET);

        Guest guest = new Guest();
        guest.setFirst_name("John");
        guest.setLast_name("Doe");

        Trainer trainer = new Trainer();
        trainer.setFirst_name("Jane");
        trainer.setLast_name("Smith");

        // Execute the method
        NotificationDto notificationDto = WebSocketController.getNotificationDto(notification, guest, trainer);

        // Assertions
        assertEquals(1, notificationDto.getNotificationId());
        assertEquals(LocalDate.now(), notificationDto.getDate());
        assertFalse(notificationDto.isViewed());
        assertEquals("Test Message", notificationDto.getMessage());
        assertEquals(NotificationType.DIET, notificationDto.getType());
        assertEquals("John", notificationDto.getGuestFirstName());
        assertEquals("Doe", notificationDto.getGuestLastName());
        assertEquals("Jane", notificationDto.getTrainerFirstName());
        assertEquals("Smith", notificationDto.getTrainerLastName());
    }
    @Test
    void sendFeedbackToGuestTest() {
        // Mock data
        GuestFeedbackDto feedbackDto = new GuestFeedbackDto();
        feedbackDto.setFeedback("Test Feedback");

        Notification notification = new Notification();
        notification.setId(1);

        Guest guest = new Guest();
        guest.setId(2);
        notification.setGuest(guest);

        Trainer trainer = new Trainer();
        trainer.setId(3);
        notification.setTrainer(trainer);
        when(notificationRepository.findById(1)).thenReturn(Optional.of(notification));
        when(guestRepository.findById(2)).thenReturn(Optional.of(guest));
        when(trainerRepository.findById(3)).thenReturn(Optional.of(trainer));
        when(notificationRepository.save(any(Notification.class))).thenReturn(new Notification());

        // Execute the method
        webSocketController.sendFeedbackToGuest(1, feedbackDto);

        // Verify that the notificationRepository.save method was called
        verify(notificationRepository, times(1)).save(any(Notification.class));

        // Verify that messagingTemplate.convertAndSend method was called
        verify(messagingTemplate, times(1)).convertAndSend(eq("/queue/guestFeedback/2"), any(NotificationDto.class));

        // Verify that the saved notification has the correct properties
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(notificationCaptor.capture());

        Notification savedNotification = notificationCaptor.getValue();
        assertEquals("Test Feedback", savedNotification.getMessage());
        assertEquals(guest, savedNotification.getGuest());
        assertEquals(trainer, savedNotification.getTrainer());
        assertEquals(NotificationType.FEEDBACK, savedNotification.getType());
        assertEquals(LocalDate.now(), savedNotification.getDate());
    }
}

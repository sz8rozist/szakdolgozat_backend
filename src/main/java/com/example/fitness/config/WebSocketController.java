package com.example.fitness.config;

import com.example.fitness.exception.GuestNotFoundException;
import com.example.fitness.exception.TrainerNotFoundException;
import com.example.fitness.exception.UserNotFoundException;
import com.example.fitness.model.*;
import com.example.fitness.model.dto.MessageDto;
import com.example.fitness.model.dto.TrainerDietNotificationDto;
import com.example.fitness.repository.*;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final GuestRepository guestRepository;
    private final TrainerRepository trainerRepository;
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(MessageRepository messageRepository, UserRepository userRepository, GuestRepository guestRepository, TrainerRepository trainerRepository, NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.guestRepository = guestRepository;
        this.trainerRepository = trainerRepository;
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendPrivateMessage/{receiverId}")
    public void sendPrivateMessage(@DestinationVariable Integer receiverId, @Payload MessageDto chatMessage) {
        User sender = userRepository.findById(chatMessage.getSenderUserId()).orElse(null);
        User receiver = userRepository.findById(chatMessage.getReceiverUserId()).orElse(null);

        if (sender == null) {
            throw new UserNotFoundException("A küldő felhasználó nem található!");
        }
        if (receiver == null) {
            throw new UserNotFoundException("A fogadó felhasználó nem található!");
        }
        // Mentés az adatbázisba
        Message entity = new Message();
        entity.setSenderUser(sender);
        entity.setReceiverUser(receiver);
        entity.setMessage(chatMessage.getMessage());
        entity.setDateTime(chatMessage.getDateTime());
        entity.setReaded(chatMessage.isReaded());
        System.out.println(entity.getMessage() + " " + entity.getReceiverUser().getUsername());
        messageRepository.save(entity);

        // Üzenet küldése a megfelelő címzettnek (receiverUserId)
        messagingTemplate.convertAndSend("/queue/private/" + receiverId, chatMessage);
    }
    @MessageMapping("/trainer.diet/{trainerId}")
    public void sendNotificationToTrainer(@DestinationVariable Integer trainerId, @Payload TrainerDietNotificationDto trainerDietNotificationDto){
        Guest guest = guestRepository.findById(trainerDietNotificationDto.getGuestId()).orElse(null);
        Trainer trainer = trainerRepository.findById(trainerId).orElse(null);
        if(guest == null){
            throw new GuestNotFoundException("Nem található vendég!");
        }
        if(trainer == null){
            throw new TrainerNotFoundException("Nem található edző!");
        }
        // ITT még az ételnél az eated mezőt truera kell rakni
        Notification notification = new Notification();
        notification.setMessage(trainerDietNotificationDto.getMessage());
        notification.setGuest(guest);
        notification.setTrainer(trainer);
        notification.setType(NotificationType.FEEDBACK);
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/queue/trainerNotification/" + trainer.getUser().getId(), trainerDietNotificationDto);

    }
}

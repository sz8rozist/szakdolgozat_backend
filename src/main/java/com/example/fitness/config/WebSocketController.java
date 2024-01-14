package com.example.fitness.config;

import com.example.fitness.exception.*;
import com.example.fitness.model.*;
import com.example.fitness.model.dto.MessageDto;
import com.example.fitness.model.dto.TrainerDietNotificationRequestDto;
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
    private final FoodRepository foodRepository;
    private final DietRepository dietRepository;

    public WebSocketController(MessageRepository messageRepository, UserRepository userRepository, GuestRepository guestRepository, TrainerRepository trainerRepository, NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate, FoodRepository foodRepository, DietRepository dietRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.guestRepository = guestRepository;
        this.trainerRepository = trainerRepository;
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
        this.foodRepository = foodRepository;
        this.dietRepository = dietRepository;
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
        messageRepository.save(entity);

        // Üzenet küldése a megfelelő címzettnek (receiverUserId)
        messagingTemplate.convertAndSend("/queue/private/" + receiverId, chatMessage);
    }
    @MessageMapping("/trainer.diet/{trainerId}")
    public void sendNotificationToTrainer(@DestinationVariable Integer trainerId, @Payload TrainerDietNotificationRequestDto trainerDietNotificationDto){
        Guest guest = guestRepository.findById(trainerDietNotificationDto.getGuestId()).orElse(null);
        Trainer trainer = trainerRepository.findById(trainerId).orElse(null);
        Food food = foodRepository.findById(trainerDietNotificationDto.getFoodId()).orElse(null);
        Diet diet = dietRepository.findById(trainerDietNotificationDto.getDietId()).orElse(null);
        if(diet == null){
            throw new DietNotFouncException("Az étrend nem található!");
        }
        if(food == null){
            throw new FoodNotFoundException("Az étel nem található!");
        }
        if(guest == null){
            throw new GuestNotFoundException("Nem található vendég!");
        }
        if(trainer == null){
            throw new TrainerNotFoundException("Nem található edző!");
        }
        // ITT még az ételnél az eated mezőt truera kell rakni
        Notification notification = new Notification();
        notification.setMessage(guest.getFirst_name() + " " + guest.getLast_name() + " elfogyasztotta a " + food.getName() + " ételt.");
        notification.setGuest(guest);
        notification.setTrainer(trainer);
        notification.setType(NotificationType.DIET);
        diet.setEated(true);
        dietRepository.save(diet);
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/queue/trainerNotification/" + trainer.getUser().getId(), notification);

    }
}

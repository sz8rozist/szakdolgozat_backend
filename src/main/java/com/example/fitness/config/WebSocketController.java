package com.example.fitness.config;

import com.example.fitness.exception.*;
import com.example.fitness.model.*;
import com.example.fitness.model.dto.*;
import com.example.fitness.repository.*;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

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
    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;

    public WebSocketController(MessageRepository messageRepository, UserRepository userRepository, GuestRepository guestRepository, TrainerRepository trainerRepository, NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate, FoodRepository foodRepository, DietRepository dietRepository, ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.guestRepository = guestRepository;
        this.trainerRepository = trainerRepository;
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
        this.foodRepository = foodRepository;
        this.dietRepository = dietRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
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
        Guest guest = guestRepository.findById(trainerDietNotificationDto.getGuestId()).orElseThrow(()-> new GuestNotFoundException("Nem található vendég"));
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(() -> new TrainerNotFoundException("Nem található edző"));
        Food food = foodRepository.findById(trainerDietNotificationDto.getFoodId()).orElseThrow(() -> new FoodNotFoundException("Nem található étel"));
        Diet diet = dietRepository.findById(trainerDietNotificationDto.getDietId()).orElseThrow(() -> new DietNotFouncException("Nem található étrend"));
        Notification notification = new Notification();
        notification.setMessage(guest.getFirst_name() + " " + guest.getLast_name() + " elfogyasztotta a " + food.getName() + " ételt.");
        notification.setGuest(guest);
        notification.setTrainer(trainer);
        notification.setType(NotificationType.DIET);
        notification.setDate(LocalDate.now());
        diet.setEated(true);
        dietRepository.save(diet);
        notificationRepository.save(notification);
        NotificationDto notificationDto = getNotificationDto(notification, guest, trainer);
        messagingTemplate.convertAndSend("/queue/trainerNotification/" + trainer.getUser().getId(), notificationDto);
    }

    public static NotificationDto getNotificationDto(Notification notification, Guest guest, Trainer trainer) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNotificationId(notification.getId());
        notificationDto.setDate(notification.getDate());
        notificationDto.setViewed(notification.isViewed());
        notificationDto.setMessage(notification.getMessage());
        notificationDto.setType(notification.getType());
        notificationDto.setGuestFirstName(guest.getFirst_name());
        notificationDto.setGuestLastName(guest.getLast_name());
        notificationDto.setTrainerFirstName(trainer.getFirst_name());
        notificationDto.setTrainerLastName(trainer.getLast_name());
        return notificationDto;
    }

    @MessageMapping("/trainer.workout/{trainerId}")
    public void sendWorkoutNotificationToTrainer(@DestinationVariable Integer trainerId, @Payload TrainerWorkoutNotificationDto trainerWorkoutNotificationDto){
        Guest guest = guestRepository.findById(trainerWorkoutNotificationDto.getGuestId()).orElseThrow(()-> new GuestNotFoundException("Nem található vendég"));
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(() -> new TrainerNotFoundException("Nem található edző"));
        Exercise exercise = exerciseRepository.findById(trainerWorkoutNotificationDto.getExerciseId()).orElseThrow(() -> new ExerciseNotFoundException("Nem található gyakorlat"));
        Workout workout = workoutRepository.findById(trainerWorkoutNotificationDto.getWorkoutId()).orElseThrow(() -> new WorkoutNotFoundException("Nem található edzésterv"));

        Notification notification = new Notification();
        notification.setMessage(guest.getFirst_name() + " " + guest.getLast_name() + " elvégezte a " + exercise.getName() + " gyakorlatot.");
        notification.setGuest(guest);
        notification.setTrainer(trainer);
        notification.setType(NotificationType.EXERCISE);
        notification.setDate(LocalDate.now());
        workout.setDone(true);
        workoutRepository.save(workout);
        notificationRepository.save(notification);
        NotificationDto notificationDto = getNotificationDto(notification, guest, trainer);
        messagingTemplate.convertAndSend("/queue/trainerWorkoutNotification/" + trainer.getUser().getId(), notificationDto);
    }

    @MessageMapping("/guest.feedback/{notificationId}")
    public void sendFeedbackToGuest(@DestinationVariable Integer notificationId, @Payload GuestFeedbackDto guestFeedbackDto){
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new NotificationNotFoundException("Nem található értesítés"));
        Guest guest = guestRepository.findById(notification.getGuest().getId()).orElseThrow(()-> new GuestNotFoundException("Nem található vendég"));
        Trainer trainer = trainerRepository.findById(notification.getTrainer().getId()).orElseThrow(() -> new TrainerNotFoundException("Nem található edző"));
        Notification notification1 = new Notification();
        notification1.setMessage(guestFeedbackDto.getFeedback());
        notification1.setGuest(guest);
        notification1.setTrainer(trainer);
        notification1.setType(NotificationType.FEEDBACK);
        notification1.setDate(LocalDate.now());
        notificationRepository.save(notification1);
        NotificationDto notificationDto = getNotificationDto(notification1, guest, trainer);
        messagingTemplate.convertAndSend("/queue/guestFeedback/" + notification.getGuest().getId(), notificationDto);

    }

}

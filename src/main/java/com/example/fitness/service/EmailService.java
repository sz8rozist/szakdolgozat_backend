package com.example.fitness.service;

import com.example.fitness.config.SecurityContextUtil;
import com.example.fitness.exception.UserNotFoundException;
import com.example.fitness.model.Diet;
import com.example.fitness.model.User;
import com.example.fitness.model.dto.WorkoutDto;
import com.example.fitness.repository.DietRepository;
import com.example.fitness.repository.UserRepository;
import com.example.fitness.repository.WorkoutRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private DietRepository dietRepository;

    @Value(value = "${spring.mail.username}")
    private String from;
    private final Set<String> sentEmails = new HashSet<>();
    @Scheduled(cron = "0 0 4 * * *")
    public void sendEmailMorning() throws MessagingException, IOException {
        System.out.println("sendEmailMorning metódus elindult: " + LocalDateTime.now());
        if (!sentEmails.contains("morning")) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            Authentication authentication = SecurityContextUtil.getAuthentication();
            String username =  ((UserDetails) authentication.getPrincipal()).getUsername();
            System.out.println(username);
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Nem található felhasználó."));
            if(user.getGuest().isPresent()){
                int guestId = user.getGuest().get().getId();
                List<WorkoutDto> workoutDtoList = workoutRepository.findWorkoutsByGuestIdAndWorkoutDate(guestId, LocalDate.now());
                List<Diet> diets = dietRepository.findDietsByGuestIdAndDietDate(guestId, LocalDate.now());
                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setTo("ristvan98@gmail.com");
                mimeMessageHelper.setSubject("Mozgás Mester emlékeztető email");
                StringBuilder messageBuilder = new StringBuilder();
                messageBuilder.append("<html><body>");
                messageBuilder.append("<p style='font-size:16px;'>Kedves ").append("Rózsa ").append(" ").append("István").append("!</p>");
                messageBuilder.append("<p>A mai nap a következő teendők várnak rád </p>");
                if(!workoutDtoList.isEmpty()){
                    messageBuilder.append("<p>Edzés: </p>");
                    messageBuilder.append("<table style='width:100%; border-collapse: collapse;'><tr><th>Gyakorlat</th><th>Sorozatszám</th><th>Ismétlésszám</th></tr>");
                    for(WorkoutDto workoutDto : workoutDtoList){
                        messageBuilder.append("<tr><td>").append(workoutDto.getExercise().getName()).append("</td><td>").append(workoutDto.getSets()).append("</td><td>").append(workoutDto.getRepetitions()).append("</td></tr>");
                    }
                    messageBuilder.append("</table>");
                }else{
                    messageBuilder.append("<p>A mai napra nincs edzés rögzítve.</p>");
                }
                if(!diets.isEmpty()){
                    messageBuilder.append("<p>Étrend: </p>");
                    messageBuilder.append("<table style='width:100%; border-collapse: collapse;'><tr><th>Étel</th><th>Típusa</th></tr>");
                    for(Diet diet : diets){
                        messageBuilder.append("<tr><td>").append(diet.getFood().getName()).append("</td><td>").append(translateFoodName(diet.getType().name())).append("</td></tr>");
                    }
                    messageBuilder.append("</table>");
                }else{
                    messageBuilder.append("<p>A mai napra nincs étrend rögzítve.</p>");
                }
                messageBuilder.append("</body></html>");

                String message = messageBuilder.toString();
                mimeMessageHelper.setText(message, true);

                String signature = "<p style='font-style: italic; margin-top: 20px;'>Üdvözlettel,<br/>Rózsa István</p>";
                mimeMessageHelper.setText(message + signature, true);
                String path = "src/img/logo-removebg-preview.png";
                File file = new File(path);
                if(!file.exists()){
                    throw new FileNotFoundException("A fájl nem található.");
                }
                Resource image = new FileSystemResource(file);
                mimeMessageHelper.addInline("logo-removebg-preview.png", image);

                javaMailSender.send(mimeMessage);
                System.out.println("Sikeres email kiküldés!");
                // Tárold el, hogy már elküldted
                sentEmails.add("morning");
            }
        }
    }

    private String translateFoodName(String type){
        return switch (type) {
            case "LUNCH" -> "Ebéd";
            case "BREAKFAST" -> "Reggeli";
            case "DINNER" -> "Vacsora";
            case "SNACK" -> "Snack";
            default -> "";
        };
    }
}

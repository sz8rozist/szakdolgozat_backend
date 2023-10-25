package com.example.fitness.service;

import com.example.fitness.exception.TrainerNotFoundException;
import com.example.fitness.exception.UserExsistException;
import com.example.fitness.model.Guest;
import com.example.fitness.model.Trainer;
import com.example.fitness.model.User;
import com.example.fitness.model.request.ChooseTrainerRequest;
import com.example.fitness.model.response.TrainerResponse;
import com.example.fitness.repository.GuestRepository;
import com.example.fitness.repository.TrainerRepository;
import com.example.fitness.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainerService {
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GuestRepository guestRepository;
    public List<TrainerResponse> getTrainersWithProfileImage() {
        List<Object[]> queryResult = trainerRepository.findTrainersWithProfileImage();
        List<TrainerResponse> responseList = new ArrayList<>();

        for (Object[] result : queryResult) {
            Trainer trainer = (Trainer) result[0];
            String userProfileImage = (String) result[1];

            TrainerResponse response = new TrainerResponse(trainer, userProfileImage);
            responseList.add(response);
        }

        return responseList;
    }

    public void choose(ChooseTrainerRequest chooseTrainerRequest) {
        User user = userRepository.findById(chooseTrainerRequest.getUserId()).orElse(null);
        if(user == null){
            throw new UserExsistException("Nem található felhasználó!");
        }
        Guest guest = guestRepository.findByUserId(user.getId());
        Trainer trainer = trainerRepository.findById(chooseTrainerRequest.getTrainerId()).orElse(null);
        if(trainer == null){
            throw new TrainerNotFoundException("Nem található edző!");
        }
        guest.setTrainer(trainer);
        guestRepository.save(guest);
    }
}

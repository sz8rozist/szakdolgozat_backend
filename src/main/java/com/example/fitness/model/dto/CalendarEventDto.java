package com.example.fitness.model.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEventDto {
    public String title;
    public LocalDate date;
    public String color;
    public Boolean isTrainer;
    public Boolean isDiet;
    public int guestId;
}

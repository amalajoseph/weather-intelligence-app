package com.amala.weatherapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "weather_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private LocalDate date;
    private String weather;
    private double temperature;
}

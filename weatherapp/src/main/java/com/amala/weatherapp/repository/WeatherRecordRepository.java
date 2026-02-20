package com.amala.weatherapp.repository;

import com.amala.weatherapp.entity.WeatherRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRecordRepository extends JpaRepository<WeatherRecord, Long> {

    List<WeatherRecord> findByLocationAndDateBetween(String location, LocalDate start, LocalDate end);

    // This is needed to avoid duplicates in 5-day forecast
    Optional<WeatherRecord> findByLocationAndDate(String location, LocalDate date);
}
package com.amala.weatherapp.service;

import com.amala.weatherapp.entity.WeatherRecord;
import com.amala.weatherapp.repository.WeatherRecordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;

@Service
public class WeatherService {

    private final WeatherRecordRepository repository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherService(WeatherRecordRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    // -------------------
    // CREATE / FETCH
    // -------------------
    public WeatherRecord getWeatherByLocation(String location) {
        // Call OpenWeatherMap API
        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + location + "&units=metric&appid=" + apiKey;

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || response.isEmpty()) {
            throw new RuntimeException("Weather API returned no data for location: " + location);
        }

        // Extract temperature
        Map<String, Object> main = (Map<String, Object>) response.get("main");
        double temp = main != null ? ((Number) main.get("temp")).doubleValue() : 0.0;

        // Extract weather description
        String weatherDesc = "Unknown";
        if (response.get("weather") instanceof List list && !list.isEmpty()) {
            Map<String, Object> w = (Map<String, Object>) list.get(0);
            weatherDesc = (String) w.get("main");
        }

        // Save to database
        WeatherRecord record = new WeatherRecord();
        record.setLocation(location);
        record.setWeather(weatherDesc);
        record.setTemperature(temp);
        record.setDate(LocalDate.now());

        return repository.save(record);
    }

    // -------------------
    // READ
    // -------------------
    public List<WeatherRecord> getAllWeather() {
        return repository.findAll();
    }

    public List<WeatherRecord> getWeatherByLocationAndDate(String location, LocalDate start, LocalDate end) {
        return repository.findByLocationAndDateBetween(location, start, end);
    }

    // -------------------
    // UPDATE
    // -------------------
    public WeatherRecord updateWeather(Long id, WeatherRecord record) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setWeather(record.getWeather());
                    existing.setTemperature(record.getTemperature());
                    existing.setDate(record.getDate());
                    existing.setLocation(record.getLocation());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }

    // -------------------
    // DELETE
    // -------------------
    public void deleteWeather(Long id) {
        repository.deleteById(id);
    }

    // -------------------
    // EXPORT (CSV or JSON)
    // -------------------
    public void exportData(String format, HttpServletResponse response) throws IOException {
        List<WeatherRecord> records = repository.findAll();
        if ("csv".equalsIgnoreCase(format)) {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=weather.csv");
            PrintWriter writer = response.getWriter();
            writer.println("id,location,date,weather,temperature");
            for (WeatherRecord r : records) {
                writer.println(r.getId() + "," + r.getLocation() + "," + r.getDate() + ","
                        + r.getWeather() + "," + r.getTemperature());
            }
            writer.flush();
            writer.close();
        } else if ("json".equalsIgnoreCase(format)) {
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(records));
        } else {
            throw new RuntimeException("Unsupported format: " + format);
        }
    }

    // -------------------
    // 5-Day Forecast
    // -------------------
    public List<WeatherRecord> get5DayForecast(String location) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="
                + location + "&units=metric&appid=" + apiKey;

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || response.isEmpty()) {
            throw new RuntimeException("Forecast API returned no data for location: " + location);
        }

        List<Map<String, Object>> list = (List<Map<String, Object>>) response.get("list");
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("No forecast data available");
        }

        Map<String, WeatherRecord> dailyForecasts = new LinkedHashMap<>();
        List<WeatherRecord> savedForecasts = new ArrayList<>();

        for (Map<String, Object> entry : list) {
            String dt_txt = (String) entry.get("dt_txt"); // e.g., "2026-02-21 12:00:00"
            String dateStr = dt_txt.split(" ")[0];
            LocalDate date = LocalDate.parse(dateStr);

            // Skip if forecast already exists
            boolean exists = repository.findByLocationAndDate(location, date).isPresent();
            if (exists) continue;

            Map<String, Object> main = (Map<String, Object>) entry.get("main");
            double temp = main != null ? ((Number) main.get("temp")).doubleValue() : 0.0;

            String weatherDesc = "Unknown";
            List<Map<String, Object>> weatherList = (List<Map<String, Object>>) entry.get("weather");
            if (weatherList != null && !weatherList.isEmpty()) {
                weatherDesc = (String) weatherList.get(0).get("main");
            }

            WeatherRecord record = new WeatherRecord();
            record.setLocation(location);
            record.setDate(date);
            record.setTemperature(temp);
            record.setWeather(weatherDesc);

            WeatherRecord saved = repository.save(record);
            savedForecasts.add(saved);
            dailyForecasts.put(dateStr, saved);
        }

        return savedForecasts;
    }
}
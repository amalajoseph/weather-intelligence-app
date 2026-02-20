package com.amala.weatherapp.controller;

import com.amala.weatherapp.entity.WeatherRecord;
import com.amala.weatherapp.service.WeatherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    // GET forecast stub (5 days forecast)
    @GetMapping("/forecast")
    public List<WeatherRecord> getForecast(@RequestParam String location) {
        return service.get5DayForecast(location);
    }
    // CREATE
    @PostMapping("/fetch")
    public WeatherRecord fetchWeather(@RequestParam String location) {
        return service.getWeatherByLocation(location);
    }

    // READ
    @GetMapping("/all")
    public List<WeatherRecord> getAll() {
        return service.getAllWeather();
    }

    @GetMapping
    public List<WeatherRecord> getByLocationAndDate(
            @RequestParam String location,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return service.getWeatherByLocationAndDate(location, start, end);
    }

    // UPDATE
    @PutMapping("/{id}")
    public WeatherRecord update(@PathVariable Long id, @RequestBody WeatherRecord record) {
        return service.updateWeather(id, record);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteWeather(id);
    }

    // EXPORT
    @GetMapping("/export")
    public void export(@RequestParam String format, HttpServletResponse response) throws IOException {
        service.exportData(format, response);
    }
}
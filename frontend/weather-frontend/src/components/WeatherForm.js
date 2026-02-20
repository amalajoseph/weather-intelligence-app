import { useState } from "react";
import { fetchWeather, fetchFiveDayForecast } from "../services/api";

export default function WeatherForm({ setWeather, setForecast }) {
  const [location, setLocation] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await fetchWeather(location);
      setWeather(res.data);

      const forecastRes = await fetchFiveDayForecast(location);
      setForecast(forecastRes.data);
    } catch (err) {
      alert("Location not found or API error");
      setWeather(null);
      setForecast([]);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="weather-form">
      <input
        type="text"
        placeholder="Enter city or zip"
        value={location}
        onChange={(e) => setLocation(e.target.value)}
        required
      />
      <button type="submit">Get Weather</button>
    </form>
  );
}

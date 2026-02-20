import axios from "axios";

// Backend base URL
const BASE_URL = "http://localhost:8080/api/weather";

// Fetch weather from backend API
export const fetchWeather = (location) => {
  return axios.post(`${BASE_URL}/fetch?location=${location}`);
};

// Get all weather records from backend
export const getAllWeather = () => {
  return axios.get(`${BASE_URL}/all`);
};

export const fetchFiveDayForecast = (location) => {
  return axios.get(`${BASE_URL}/forecast?location=${location}`);
};
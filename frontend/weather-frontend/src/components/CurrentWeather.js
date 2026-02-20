export default function CurrentWeather({ weather }) {
  if (!weather) return null;

   return (
      <div className="current-weather">
        <h2>{weather.location}</h2>
        <p>Temperature: {weather.temperature} °C</p>
        <p>Condition: {weather.weather}</p>
        <p>Date: {weather.date}</p>
      </div>
    );
  }

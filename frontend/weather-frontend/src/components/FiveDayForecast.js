export default function FiveDayForecast({ forecast }) {
  if (!forecast || forecast.length === 0) {
    return null;
  }

  return (
    <div className="forecast">
      <h2>5-Day Forecast</h2>
      <ul>
        {forecast.map((day, index) => (
          <li key={index}>
            <p>{day.date}</p>
            <p>Temp: {day.temp}°C</p>
            <p>{day.description}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}

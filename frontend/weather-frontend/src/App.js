import { useState } from "react";
import WeatherForm from "./components/WeatherForm";
import CurrentWeather from "./components/CurrentWeather";
import FiveDayForecast from "./components/FiveDayForecast";

function App() {
  const [weather, setWeather] = useState(null);
  const [forecast, setForecast] = useState([]);

  return (
    <div className="App">
      <h1>Weather App</h1>

      <WeatherForm
        setWeather={setWeather}
        setForecast={setForecast}
      />

      <CurrentWeather weather={weather} />
      <FiveDayForecast forecast={forecast} />
    </div>
  );
}

export default App;

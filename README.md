# Weather Intelligence App 

A full-stack Weather Application built using Spring Boot (Backend) and React.js (Frontend).

This app allows users to:

Search weather by location (city, zip, or coordinates)

View current weather details

View a 5-day weather forecast

Store, update, and delete weather records in a PostgreSQL database

Export weather data in CSV or JSON format

# Tech Stack
Backend

Java 17+

Spring Boot

Spring Data JPA

PostgreSQL

REST APIs

# OpenWeatherMap API integration

Frontend

React.js

Axios for API calls

CSS / Responsive UI

# Project Structure
weather-intelligence-app/
│
├── backend/                 # Spring Boot Application
│   ├── controller/
│   ├── service/
│   ├── repository/
│   └── entity/
│
└── frontend/
└── weather-frontend/    # React Application
#️ Backend Setup

Navigate to the backend folder:

cd backend

# Configure PostgreSQL in application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/weatherdb
spring.datasource.username=postgres
spring.datasource.password=<YOUR_PASSWORD>

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

Add your OpenWeatherMap API key in application.properties:

weather.api.key=<YOUR_API_KEY>

Run the backend application:

mvn spring-boot:run

# Backend will be accessible at:
http://localhost:8080

# Frontend Setup

Navigate to frontend folder:

cd frontend/weather-frontend

Install dependencies:

npm install

Start the React app:

npm start

# Frontend will be available at:
http://localhost:3000

# API Endpoints
Action	Endpoint	Method	Description
Fetch Current Weather	/api/weather/fetch?location={city}	POST	Saves current weather for the specified location
5-Day Forecast	/api/weather/forecast?location={city}	GET	Returns a 5-day weather forecast for the location
Read All Records	/api/weather/all	GET	Retrieves all stored weather records
Read Records by Date & Location	/api/weather?location={city}&start={yyyy-MM-dd}&end={yyyy-MM-dd}	GET	Filter records by location and date range
Update Record	/api/weather/{id}	PUT	Updates weather record by ID
Delete Record	/api/weather/{id}	DELETE	Deletes weather record by ID
Export Data	/api/weather/export?format=csv/json	GET	Exports weather records in CSV or JSON
# Database Structure

Table: weather_record

Column	Type	Description
id	BIGINT	Primary key, auto-generated
location	VARCHAR	City name, zip code, or coordinates
date	DATE	Date of weather data
weather	VARCHAR	Weather description (Sunny, Rain, etc.)
temperature	DOUBLE	Temperature in Celsius
# Frontend Implementation

React Components:

WeatherSearch.js – Input for location, fetches current weather

ForecastTable.js – Displays 5-day forecast in table format

WeatherRecordList.js – Shows stored records from database with update/delete

ExportButton.js – Allows exporting CSV/JSON

API Integration: Axios is used to call backend endpoints and update the UI dynamically.

CORS Enabled: Backend allows requests from React frontend (@CrossOrigin(origins="*")).

State Management: React useState and useEffect hooks manage API responses and UI updates.

# Features

Full-stack integration: React frontend + Spring Boot backend

Real-time weather fetching from OpenWeatherMap API

5-day weather forecast table

CRUD operations with PostgreSQL persistence

Export records to CSV/JSON

Clean, responsive, and user-friendly UI

# Future Enhancements

Add weather icons based on condition

Integrate Google Maps / YouTube API for location insights

Authentication with JWT

Dockerize backend and frontend for easy deployment

Deploy to cloud platforms (AWS / Azure / GCP)

👩 Author

Amala Joseph
Senior Java Developer | Spring Boot | Microservices | React

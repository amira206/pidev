package GestionEvenement3a16.Services;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class WeatherService {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "aabfbea559b7b05e2c379fbea38811a7";

    public JSONObject getWeatherByCity(String city) {
        HttpResponse<JsonNode> response = Unirest.get(BASE_URL)
                .queryString("q", city)
                .queryString("appid", API_KEY)
                .asJson();

        if (response.getStatus() == 200) {
            return response.getBody().getObject();
        } else {
            return null;
        }
    }
}
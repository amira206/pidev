package piDev.GestionLIvariason.Livraison.entities;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
public class DistanceCalculator {

    // Function that takes two city names and returns the distance between them
    public static int getDistanceBetweenCities(String city1, String city2, String apiKey) throws Exception {
        // Get the coordinates for city1
        JSONObject city1Data = getCityCoordinates(city1, apiKey);
        double lat1 = city1Data.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getDouble("lat");
        double lon1 = city1Data.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getDouble("lng");

        // Get the coordinates for city2
        JSONObject city2Data = getCityCoordinates(city2, apiKey);
        double lat2 = city2Data.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getDouble("lat");
        double lon2 = city2Data.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getDouble("lng");

        // Calculate and return the distance between the two cities
        return calculateDistance(lat1, lon1, lat2, lon2);
    }

    // Function to make the API request to OpenCage API and get the coordinates of the city
    public static JSONObject getCityCoordinates(String city, String apiKey) throws Exception {
        String encodedCity = java.net.URLEncoder.encode(city, "UTF-8");
        String urlString = "https://api.opencagedata.com/geocode/v1/json?q=" + encodedCity + "&key=" + apiKey;
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        // Check for successful HTTP response code (200)
        int responseCode = con.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("HTTP error occurred: " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parse the JSON response
        JSONObject jsonResponse = new JSONObject(response.toString());

        // Check if 'results' array is empty (city not found)
        if (jsonResponse.getJSONArray("results").length() == 0) {
            throw new Exception("City not found: " + city);
        }

        return jsonResponse;
    }

    // Function to calculate the distance using the Haversine formula and return it as an integer
    public static int calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the Earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // returns the distance in km (as a double)

        // Return the distance as an integer (rounding to the nearest whole number)
        return (int) Math.round(distance);
    }
}

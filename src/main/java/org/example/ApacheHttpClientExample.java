package org.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApacheHttpClientExample {

    public static void main(String[] args) throws IOException, InterruptedException, JSONException {
        String json = getJsonResponse();
        parseJson(json);
    }

    public static String getJsonResponse() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhYjZhODZkOWQyYTRhMDk4NzQ5ZWM3MWI3ODI1NjJiMCIsInN1YiI6IjY0Yzk4ZTM3YmYwOWQxMDBjNzE5ZjlmYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.sL_eryJ7qDwrjKpzXXXvLM3buMNvYDo0Hjaz3GsxCns")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static void parseJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray resultsArray = jsonObject.getJSONArray("results");

        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject movieObject = resultsArray.getJSONObject(i);
            String originalTitle = movieObject.getString("original_title");
            System.out.println("Original Title: " + originalTitle);
        }
    }
}

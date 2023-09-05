package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class OkHttpClientExample {

    public static void main(String[] args) {
        List<Movie> movies = getMovies();

        if (movies != null && !movies.isEmpty()) {
            String htmlContent = generateMovieListHtml(movies);
            String outputPath = "generatedPage.html";
            saveHtmlToFile(htmlContent, outputPath);
            openInBrowser(outputPath);
        } else {
            System.out.println("Não foi possível obter a lista de filmes.");
        }
    }

    private static List<Movie> getMovies() {
        try {
            Response response = getJsonResponse();
            String json = response.body().string();
            return parseJson(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void saveHtmlToFile(String content, String filePath) {
        try {
            Files.write(Paths.get(filePath), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void openInBrowser(String filePath) {
        try {
            Desktop.getDesktop().browse(new File(filePath).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Response getJsonResponse() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/popular?language=en-US&page=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhYjZhODZkOWQyYTRhMDk4NzQ5ZWM3MWI3ODI1NjJiMCIsInN1YiI6IjY0Yzk4ZTM3YmYwOWQxMDBjNzE5ZjlmYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.sL_eryJ7qDwrjKpzXXXvLM3buMNvYDo0Hjaz3GsxCns")  // Certifique-se de substituir ... pelo seu token real
                .build();

        return client.newCall(request).execute();
    }

    private static List<Movie> parseJson(String json) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray resultsArray = jsonObject.getJSONArray("results");
        System.out.println(resultsArray);

        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject movieObject = resultsArray.getJSONObject(i);
            String originalTitle = movieObject.getString("original_title");
            double voteAverage = movieObject.getDouble("vote_average");
            String imageUrl = movieObject.optString("poster_path");

            movies.add(new Movie(originalTitle, voteAverage, imageUrl));

        }

        /*System.out.println(movies);*/
        return movies;
    }

    private static String generateMovieListHtml(List<Movie> movies) {
        StringBuilder html = new StringBuilder();

        // Adicionando um estilo básico para a lista e as imagens
        html.append("<html><head><style>");
        html.append("ul { list-style-type: none; padding: 0; }");
        html.append("li { margin: 10px 0; border-bottom: 1px solid #ddd; padding-bottom: 10px; }");
        html.append("img { max-width: 200px; margin-right: 20px; }");
        html.append(".movie-info { display: flex; align-items: center; }");
        html.append("</style></head><body><ul>");

        for (Movie movie : movies) {
            html.append("<li class='movie-info'>")
                    .append("<img src='").append(movie.getImageUrl()).append("' alt='").append(movie.getOriginalTitle()).append("'/>")
                    .append("<span>")
                    .append(movie.getOriginalTitle())
                    .append(" - ")
                    .append(movie.getVote_average())
                    .append("</span>")
                    .append("</li>");
        }

        html.append("</ul></body></html>");
        return html.toString();
    }
}
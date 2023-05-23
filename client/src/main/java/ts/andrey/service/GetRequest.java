package ts.andrey.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public final class GetRequest {

    private GetRequest() {
    }

    public static String getHTML(String urlToRead) throws IOException {
        var url = new URL(urlToRead);
        var connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try (var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.lines().collect(Collectors.joining());
        }
    }

}

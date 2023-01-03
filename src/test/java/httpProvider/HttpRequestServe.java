package httpProvider;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpRequest.BodyPublishers.ofString;

public class HttpRequestServe<T> {

    private final Gson gson = new Gson();

    public T post(Object body, String uri, Class<T> responseType) {
        HttpRequest httpRequest = null;
        try {
            httpRequest = httpRequestBuilder(uri).POST(ofString(toJson(body))).build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return getResponse(httpRequest, responseType);
    }

    public T get(String uri,Class<T> responseType) {
        HttpRequest httpRequest = null;
        try {
            httpRequest = httpRequestBuilder(uri).GET().build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return getResponse(httpRequest, responseType);
    }


    private HttpRequest.Builder httpRequestBuilder(String uri) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(uri));
    }


    private String toJson(Object body){
        return gson.toJson(body);
    }

    private T getResponse(HttpRequest request, Class<T> responseType) {
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), responseType);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;

    }


}

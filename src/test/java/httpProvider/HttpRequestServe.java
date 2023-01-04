package httpProvider;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

public class HttpRequestServe {

    private final Gson gson = new Gson();

    public HttpResponse<String> post(String uri, Object obj) {
        HttpRequest httpRequest = null;
        try {
            httpRequest = httpRequestBuilder(uri).POST(BodyPublishers.ofString(toJson(obj))).build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return getHttpResponse(httpRequest);
    }

    public HttpResponse<String> get(String uri) {
        HttpRequest httpRequest = null;
        try {
            httpRequest = httpRequestBuilder(uri).GET().build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return getHttpResponse(httpRequest);
    }

    public HttpResponse<String> put(String uri, Object obj) {
        HttpRequest httpRequest = null;
        try {
            httpRequest = httpRequestBuilder(uri).PUT(BodyPublishers.ofString(toJson(obj))).build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return getHttpResponse(httpRequest);
    }


    public HttpResponse<String> delete(String url) {
        HttpRequest httpRequest = null;
        try {
            httpRequest = httpRequestBuilder(url).DELETE().build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return getHttpResponse(httpRequest);
    }

    private HttpRequest.Builder httpRequestBuilder(String uri) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(uri))
                .header("content-type", "application/json");
    }


    private String toJson(Object body) {
        return gson.toJson(body);
    }

    public <T> T getGsonResponse(HttpResponse<String> response, Class<T> responseType) {
        return gson.fromJson(response.body(), responseType);

    }

    private static HttpResponse<String> getHttpResponse(HttpRequest request) {
        try {
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

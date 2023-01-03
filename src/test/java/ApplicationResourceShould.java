import com.example.webservice.Application;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationResourceShould {

    private static final String BASE_URL = "http://localhost:8080/webservice-1.0-SNAPSHOT/rest/applications/";
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final int HTTP_200 = 200;
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new Gson();
    }

    @Test
    void send_data_by_post() throws URISyntaxException, IOException, InterruptedException {
        Application application = new Application(1, "amir rahmani", "my desc");
        String json = gson.toJson(application);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("content-type", JSON_CONTENT_TYPE)
                .build();

        HttpResponse<String> response = getHttpResponse(request);

        Assertions.assertEquals(response.statusCode(), HTTP_200);
        Application result = gson.fromJson(response.body(), Application.class);

        assertThat(result).isEqualTo(application);
    }

    @Test
    void get_data() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .GET()
                .header("content-type", JSON_CONTENT_TYPE)
                .build();

        HttpResponse<String> response = getHttpResponse(request);


        Assertions.assertEquals(HTTP_200, response.statusCode());
    }

    @Test
    void send_parameter_to_url() throws URISyntaxException, IOException, InterruptedException {
        int id = 141;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + id))
                .GET()
                .header("content-type", JSON_CONTENT_TYPE)
                .build();

        HttpResponse<String> response = getHttpResponse(request);
        Application result = gson.fromJson(response.body(), Application.class);

        Assertions.assertEquals(response.statusCode(), HTTP_200);
        assertResponse(result, 141, "name 141", "description 141");

    }

    @Test
    void send_multiple_param_to_url() throws URISyntaxException, IOException, InterruptedException {
        int id = 10;
        String name = "mariam";
        String uri = format(BASE_URL + "%d/%s", id, name);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri))
                .GET()
                .header("content-type", JSON_CONTENT_TYPE)
                .build();

        HttpResponse<String> response = getHttpResponse(request);
        Application result = gson.fromJson(response.body(), Application.class);

        Assertions.assertEquals(response.statusCode(), HTTP_200);
        assertResponse(result, 10, "mariam10", "description for mariam");
    }

    private static HttpResponse<String> getHttpResponse(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static void assertResponse(Application actual, int expId, String expName, String expDescription) {
        assertThat(actual.getId()).isEqualTo(expId);
        assertThat(actual.getName()).isEqualTo(expName);
        assertThat(actual.getDescription()).isEqualTo(expDescription);
    }
}
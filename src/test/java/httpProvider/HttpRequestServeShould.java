package httpProvider;

import com.example.webservice.Application;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HttpRequestServeShould {

/*    @Test
    void send_get_request_and_received_suitable_response() {
        HttpRequestServe<List<Application>> request = new HttpRequestServe<>();
        final Application application = request.get("http://localhost:8080/webservice-1.0-SNAPSHOT/rest/applications/",Application.class);

        assertThat(application.getPlayers()).contains(new Player("Cristiano Ronaldo",117),new Player("Lionel Messi",98));
    }

    @Test
    void send_post_request_and_received_suitable_response() {
        HttpRequestServe<ResponseStatus> requestServe = new HttpRequestServe<>();
        final ResponseStatus responseStatus = requestServe
                .post(new Player("Mehdi Taremi", 25), "http://localhost:3001/players/add",ResponseStatus.class);
        Assertions.assertThat(responseStatus.getStatus()).isEqualTo(200);


    }*/
}

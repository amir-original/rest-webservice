import com.example.webservice.Application;
import com.example.webservice.dao.ApplicationDAOImpl;
import com.example.webservice.service.ApplicationServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import httpProvider.HttpRequestServe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.GenericType;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationResourceShould {

    private static final String BASE_URL = "http://localhost:8080/webservice-1.0-SNAPSHOT/rest/applications/";
    private static final int HTTP_200 = 200;
    private Gson gson;
    private HttpRequestServe httpRequest;
    private ApplicationDAOImpl dao;
    private ApplicationServiceImpl service;

    @BeforeEach
    void setUp() {
        gson = new Gson();
        httpRequest = new HttpRequestServe();
        dao = new ApplicationDAOImpl();
        service = new ApplicationServiceImpl(dao);
    }

    @Test
    void send_data_by_post() {
        int id = 103;
        Application application = getApp(id, "lockdown", "baby, calm down");
        HttpResponse<String> response = httpRequest.post(BASE_URL, application);
        Assertions.assertEquals(201, response.statusCode());
        new ApplicationDAOImpl().delete(id);
    }

    @Test
    void get_http_409_when_add_already_exist_record() {
        int id = 101;
        Application application = getApp(id, "lockdown", "baby, calm down");


        service.addApplication(application);
        HttpResponse<String> response = httpRequest.post(BASE_URL, application);

        Assertions.assertEquals(409, response.statusCode());
        dao.delete(id);
    }

    @Test
    void get_data() {
        HttpResponse<String> response = httpRequest.get(BASE_URL);
        Assertions.assertEquals(HTTP_200, response.statusCode());
    }

    @Test
    void get_date_by_send_id_parameter() {
        int id = 70;
        String uri = BASE_URL + id;
        HttpResponse<String> response = httpRequest.get(uri);
        Application result = httpRequest.getGsonResponse(response, Application.class);

        Assertions.assertEquals(response.statusCode(), HTTP_200);
        assertResponse(result, 70,"Channel list","TV Guide type app");

    }

    @Test
    void send_multiple_param_to_url() {
        int id = 45;
        String name = "timeClock";
        String uri = format(BASE_URL + "%d/%s", id, name);
        HttpResponse<String> response = httpRequest.get(uri);
        Application result = httpRequest.getGsonResponse(response,Application.class);
        Assertions.assertEquals(response.statusCode(), HTTP_200);
        assertResponse(result, 45, "timeClock", "track work hours for employees");
    }

    @Test
    void get_all_applications() {
        HttpResponse<String> response = httpRequest.get(BASE_URL);
        //how to convert array of object to collection
        TypeToken<List<Application>> collectionType = new TypeToken<>() {};
        List<Application> applications = gson.fromJson(response.body(), collectionType.getType());

        Optional<Application> first = applications.stream().filter(application -> application.getId() == 70).findFirst();

        Application expApp = getApp(70, "Channel list", "TV Guide type app");
        assertThat(first.get()).isEqualTo(expApp);
        assertThat(response.statusCode()).isEqualTo(HTTP_200);
    }

    @Test
    void send_put_request_for_update_application_and_get_http_200() {
        int id = 101;
        Application application = getApp(id, "update: lockdown", "update: baby, calm down");

        HttpResponse<String> response = httpRequest.put(BASE_URL, application);

        Assertions.assertEquals(response.statusCode(), HTTP_200);

    }

    @Test
    void send_delete_request_and_get_204_http_response() {
        int id = 47;
        Application application = getApp(id,"name t","description");
        service.addApplication(application);
        HttpResponse<String> response = httpRequest.delete(BASE_URL+id);

        assertThat(response.statusCode()).isEqualTo(204);

    }

    private static void assertResponse(Application actual, int expId, String expName, String expDescription) {
        assertThat(actual.getId()).isEqualTo(expId);
        assertThat(actual.getName()).isEqualTo(expName);
        assertThat(actual.getDescription()).isEqualTo(expDescription);
    }



    private static Application getApp(int id, String name, String description) {
        return new Application(id, name, description);
    }
}

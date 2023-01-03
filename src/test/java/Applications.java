import com.example.webservice.Application;

import java.util.Arrays;
import java.util.List;

public class Applications {

    private final Application[] applications;

    public Applications(Application[] applications) {
        this.applications = applications;
    }

    public List<Application> getApplications() {
        return Arrays.asList(applications);
    }

    @Override
    public String toString() {
        return "Applications{" +
                "applications=" + applications +
                '}';
    }
}

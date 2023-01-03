import com.example.webservice.Application;
import com.example.webservice.dao.ApplicationDAO;
import com.example.webservice.dao.ApplicationDAOImpl;
import com.example.webservice.dao.RuntimeSQLIntegrityException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ApplicationDAOShould {
    private static final String HOST = "jdbc:mysql://localhost:3306/trackzilla_schema";
    private static final String USER = "amir";
    private static final String PASSWORD = "@wsrmp1378";
    private static final String SELECT_SQL = "select * from tza_application order by id desc limit 1";

    @Test
    void find_applications_from_tza_ticket_table() {
        List<Application> applications = new LinkedList<>();

        try (Connection connection = DriverManager.getConnection(HOST, USER, PASSWORD);) {
            PreparedStatement select = connection.prepareStatement(SELECT_SQL);
            ResultSet res = select.executeQuery();
            while (res.next()) {
                fillApplication(applications, res);

                assertThat(applications).containsOnly(new Application(70, "Channel list", "TV Guide type app"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void find_application_from_tza_ticket_table_by_id() {
        ApplicationDAO dao = new ApplicationDAOImpl();
        Application result = dao.findById(70);
        assertThat(result).isEqualTo(new Application(70, "Channel list", "TV Guide type app"));

    }

    @Test
    void find_application_from_tza_ticket_table_by_id_and_name() {
        ApplicationDAO dao = new ApplicationDAOImpl();
        Application result = dao.findByIdAndName(70, "Channel list");
        assertThat(result).isEqualTo(new Application(70, "Channel list", "TV Guide type app"));

    }

    @Test
    void add_new_application_in_tza_ticket() {
        ApplicationDAO dao = new ApplicationDAOImpl();

        Executable exe = () -> {
            String name = "timeClock";
            String description = "track work hours for employees";
            Application application = new Application(45, name, description);
            dao.addApplication(application);
        };
        Application app = dao.findById(45);
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(exe);
        assertThat(app.getName()).isEqualTo("timeClock");
    }

    @Test
    void throw_Runtime_SQL_Integrity_Exception_when_created_already_exist_record() {
        ApplicationDAO dao = new ApplicationDAOImpl();
        String name = "timeClock";
        String description = "track work hours for employees";
        Application application = new Application(45, name, description);

        assertThatThrownBy(()-> dao.addApplication(application)).isInstanceOf(RuntimeSQLIntegrityException.class);
        Application app = dao.findById(45);
        assertThat(app.getName()).isEqualTo("timeClock");
    }

    private static void fillApplication(List<Application> applications, ResultSet res) throws SQLException {
        int id = res.getInt("id");
        String name = res.getString("name");
        String description = res.getString("description");
        applications.add(new Application(id, name, description));
    }
}

import com.example.webservice.Application;
import com.example.webservice.dao.ApplicationDAO;
import com.example.webservice.dao.ApplicationDAOImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

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

                Assertions.assertThat(applications).containsOnly(new Application(70,"Channel list","TV Guide type app"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void fillApplication(List<Application> applications, ResultSet res) throws SQLException {
        int id = res.getInt("id");
        String name = res.getString("name");
        String description = res.getString("description");
        applications.add(new Application(id, name, description));
    }
}

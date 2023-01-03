package com.example.webservice.dao;

import com.example.webservice.Application;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ApplicationDAOImpl implements ApplicationDAO {


    private static final String HOST = "jdbc:mysql://localhost:3306/trackzilla_schema";
    private static final String USER = "amir";
    private static final String PASSWORD = "@wsrmp1378";

    @Override
    public List<Application> findAll() {
        List<Application> applications = new LinkedList<>();

        try (Connection connection = DriverManager.getConnection(HOST, USER, PASSWORD);) {
            String sql = "select * from tza_application";
            PreparedStatement select = connection.prepareStatement(sql);
            ResultSet res = select.executeQuery();
            while (res.next()) {
                fillApplication(applications, res);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return applications;
    }

    @Override
    public Application findById(int id) {
        Application application = null;
        try (Connection connection = DriverManager.getConnection(HOST, USER, PASSWORD);) {
            String sql = "select * from tza_application where id =?";
            PreparedStatement select = connection.prepareStatement(sql);
            select.setInt(1, id);
            application = getApplication(select);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return application;
    }

    @Override
    public Application findByIdAndName(int id, String name) {
        Application application;
        try (Connection connection = DriverManager.getConnection(HOST, USER, PASSWORD);) {
            String sql = "select * from tza_application where id =? and name=?";
            PreparedStatement select = connection.prepareStatement(sql);
            select.setInt(1, id);
            select.setString(2, name);
            application = getApplication(select);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return application;
    }

    @Override
    public void addApplication(Application application) {

        try (Connection connection = DriverManager.getConnection(HOST, USER, PASSWORD);) {
            String sql = "insert into tza_application values (?,?,?)";
            PreparedStatement insert = connection.prepareStatement(sql);
            insert.setInt(1, application.getId());
            insert.setString(2, application.getName());
            insert.setString(3, application.getDescription());
            insert.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeSQLIntegrityException(e);
        }catch (SQLException e){
            throw new RuntimeSQLException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DriverManager.getConnection(HOST, USER, PASSWORD);) {
            String sql = "delete from tza_application where id=?";
            PreparedStatement delete = connection.prepareStatement(sql);
            delete.setInt(1, id);
            delete.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeSQLException(e);
        }
    }

    private Application getApplication(PreparedStatement statement) throws SQLException {
        Application result = null;
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            int t_id = res.getInt("id");
            String t_name = res.getString("name");
            String description = res.getString("description");
            result = new Application(t_id, t_name, description);
        }
        return result;
    }

    private static void fillApplication(List<Application> applications, ResultSet res) throws SQLException {
        int id = res.getInt("id");
        String name = res.getString("name");
        String description = res.getString("description");
        applications.add(new Application(id, name, description));
    }
}

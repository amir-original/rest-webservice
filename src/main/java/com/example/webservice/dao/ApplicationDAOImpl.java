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

    private static void fillApplication(List<Application> applications, ResultSet res) throws SQLException {
        int id = res.getInt("id");
        String name = res.getString("name");
        String description = res.getString("description");
        applications.add(new Application(id, name, description));
    }

    @Override
    public Application findById(int id) {
        return null;
    }

    @Override
    public Application findByIdAndName(int id, String name) {
        return null;
    }
}

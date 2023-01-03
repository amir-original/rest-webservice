package com.example.webservice.service;

import com.example.webservice.Application;
import com.example.webservice.dao.ApplicationDAO;
import com.example.webservice.dao.ApplicationDAOImpl;

import java.util.List;

public class ApplicationServiceImpl implements ApplicationService {
    @Override
    public List<Application> findAll() {
        ApplicationDAO dao = new ApplicationDAOImpl();
        return dao.findAll();
    }

    @Override
    public Application findById(int id) {
        ApplicationDAO dao = new ApplicationDAOImpl();
        return dao.findById(id);
    }

    @Override
    public Application findByIdAndName(int id, String name) {
        ApplicationDAO dao = new ApplicationDAOImpl();
        return dao.findByIdAndName(id,name);
    }

    @Override
    public void addApplication(Application application) {
        ApplicationDAO dao = new ApplicationDAOImpl();
        dao.addApplication(application);
    }
}

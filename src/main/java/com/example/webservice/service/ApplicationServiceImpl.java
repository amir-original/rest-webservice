package com.example.webservice.service;

import com.example.webservice.Application;
import com.example.webservice.dao.ApplicationDAO;
import com.example.webservice.dao.ApplicationDAOImpl;

import java.util.List;

public class ApplicationServiceImpl implements ApplicationService {

    private ApplicationDAO dao;

    public ApplicationServiceImpl(ApplicationDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Application> findAll() {
        return dao.findAll();
    }

    @Override
    public Application findById(int id) {
        return dao.findById(id);
    }

    @Override
    public Application findByIdAndName(int id, String name) {
        return dao.findByIdAndName(id,name);
    }

    @Override
    public void addApplication(Application application) {
        dao.addApplication(application);
    }

    @Override
    public void updateApplication(Application application) {
        dao.updateApplication(application);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
}

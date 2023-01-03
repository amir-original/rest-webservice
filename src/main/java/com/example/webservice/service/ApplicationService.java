package com.example.webservice.service;

import com.example.webservice.Application;

import java.util.List;

public interface ApplicationService {
    List<Application> findAll();

    Application findById(int id);

    Application findByIdAndName(int id,String name);

    void addApplication(Application application);

    void updateApplication(Application application);
}

package com.example.webservice.dao;

import com.example.webservice.Application;

import java.util.List;

public interface ApplicationDAO {

    List<Application> findAll();

    Application findById(int id);

    Application findByIdAndName(int id,String name);

}

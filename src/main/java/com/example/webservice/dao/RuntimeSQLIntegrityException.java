package com.example.webservice.dao;

import java.sql.SQLIntegrityConstraintViolationException;

public class RuntimeSQLIntegrityException extends RuntimeException {
    public RuntimeSQLIntegrityException(SQLIntegrityConstraintViolationException e) {
        super(e);
    }
}

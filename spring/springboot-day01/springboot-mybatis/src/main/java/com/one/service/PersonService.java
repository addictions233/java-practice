package com.one.service;

import com.one.pojo.Person;

import java.sql.SQLException;
import java.util.List;

public interface PersonService {

    void insertPersonBatch(List<Person> list) throws SQLException;
}

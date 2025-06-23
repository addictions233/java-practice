package com.one.es.dao;

import com.one.es.bean.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends ElasticsearchRepository<Employee, Long> {

    List<Employee> findByName(String name);

}
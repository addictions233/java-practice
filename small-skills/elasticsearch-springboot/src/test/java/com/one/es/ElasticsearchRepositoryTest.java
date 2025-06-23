package com.one.es;

import com.one.es.bean.Employee;
import com.one.es.dao.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * 直接使用 ElasticsearchRepository 进行ES操作
 */
@Slf4j
public class ElasticsearchRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void testDocument() {

        Employee employee = new Employee(1L, "fox666", 1, 32, "长沙麓谷", "java architect");
        //插入文档
        employeeRepository.save(employee);

        //根据id查询
        Optional<Employee> result = employeeRepository.findById(1L);
        log.info(String.valueOf(result.get()));

        //根据name查询
        List<Employee> list = employeeRepository.findByName("fox666");
        log.info(String.valueOf(list.get(0)));

    }

}

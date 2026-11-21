package com.one.webflux;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

/**
 * 使用 spring data r2dbc 进行数据库操作
 */
@Repository
public interface StudentRepository extends R2dbcRepository<Student, Integer> {
}

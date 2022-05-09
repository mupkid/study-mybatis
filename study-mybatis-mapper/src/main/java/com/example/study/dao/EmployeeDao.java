package com.example.study.dao;

import com.example.study.entity.Employee;
import io.mybatis.mapper.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author haoxian, ou
 * @date 2022/1/11 23:23
 */
@Repository
@org.apache.ibatis.annotations.Mapper
public interface EmployeeDao extends Mapper<Employee, Long> {
}

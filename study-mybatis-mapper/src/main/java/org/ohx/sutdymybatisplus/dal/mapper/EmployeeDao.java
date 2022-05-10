package org.ohx.sutdymybatisplus.dal.mapper;

import io.mybatis.mapper.Mapper;
import org.ohx.sutdymybatisplus.dal.model.dataobject.Employee;

/**
 * @author haoxian, ou
 * @date 2022/1/11 23:23
 */
@org.apache.ibatis.annotations.Mapper
public interface EmployeeDao extends Mapper<Employee, Long> {
}

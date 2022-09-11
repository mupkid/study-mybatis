package org.ohx.sutdymybatisplus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author haoxian, ou
 * @date 2022/1/11 23:24
 */
@SpringBootTest(classes = StudyMybatisMapperAppliction.class)
public class MapperTest {
    @Autowired
    private EmployeeDao employeeDao;

    @Test
    void MapperMethodTest() throws Exception {
        System.out.println(employeeDao.selectList(new Employee()));
    }
}

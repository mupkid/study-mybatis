package org.ohx.studymybatisspring;

import org.junit.jupiter.api.Test;
import org.ohx.studymybatisspring.config.SpringConfig;
import org.ohx.studymybatisspring.dal.mapper.UserMapper;
import org.ohx.studymybatisspring.dal.model.dataobject.UserDO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author mudkip
 * @date 2022/9/11
 */
public class SelectTest {
    @Test
    public void selectTest() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserMapper userMapper = applicationContext.getBean(UserMapper.class);
        UserDO userById = userMapper.getUserById(1);
        System.out.println(userById);
    }
}

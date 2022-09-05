package org.ohx.studymybatisplus;

import org.junit.jupiter.api.Test;
import org.ohx.studymybatisplus.dal.common.enums.SexEnum;
import org.ohx.studymybatisplus.dal.mapper.UserMapper;
import org.ohx.studymybatisplus.dal.model.dataobject.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * BaseMapper Insert方法测试类
 *
 * @author mudkip
 * @date 2022/5/10
 */
@SpringBootTest
public class InsertTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        UserDO user = new UserDO();
        user.setUsername("geats");
        user.setPassword("123456");
        user.setAge(25);
        user.setSex(SexEnum.MALE);

        int result = userMapper.insert(user);
        System.out.println("Result: " + result);
        System.out.println(user.getId());
    }
}

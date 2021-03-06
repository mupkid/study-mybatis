package org.ohx.studymybatisplus;

import org.junit.jupiter.api.Test;
import org.ohx.studymybatisplus.dal.mapper.UserMapper;
import org.ohx.studymybatisplus.dal.model.dataobject.User;
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
    public void testSelectList() {
        User user = new User();
        user.setUsername("李四");
        user.setPassword("123456");
        user.setAge(23);

        int result = userMapper.insert(user);
        System.out.println("Result: " + result);
        System.out.println(user.getId());
    }
}

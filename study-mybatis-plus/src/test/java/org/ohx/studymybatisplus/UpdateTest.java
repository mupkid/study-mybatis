package org.ohx.studymybatisplus;

import org.junit.jupiter.api.Test;
import org.ohx.studymybatisplus.dal.mapper.UserMapper;
import org.ohx.studymybatisplus.dal.model.dataobject.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * BaseMapper Update方法测试类
 *
 * @author mudkip
 * @date 2022/5/29
 */
@SpringBootTest
public class UpdateTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUpdateById() {
        UserDO user = new UserDO();
        user.setId(2L);
        user.setUsername("李四");
        int result = userMapper.updateById(user);
        System.out.println("Result: " + result);
    }
}

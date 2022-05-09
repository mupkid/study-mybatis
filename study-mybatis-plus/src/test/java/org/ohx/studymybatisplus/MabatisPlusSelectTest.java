package org.ohx.studymybatisplus;

import org.junit.jupiter.api.Test;
import org.ohx.studymybatisplus.dal.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * MyBatis-Plus 查询测试类
 *
 * @author mudkip
 * @date 2022/5/10
 */
@SpringBootTest
public class MabatisPlusSelectTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList() {
        System.out.println(userMapper.selectList(null));
    }
}

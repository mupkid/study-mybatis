package org.ohx.studymybatisplus;

import org.junit.jupiter.api.Test;
import org.ohx.studymybatisplus.dal.mapper.UserMapper;
import org.ohx.studymybatisplus.dal.model.dataobject.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BaseMapper Select方法测试类
 *
 * @author mudkip
 * @date 2022/5/10
 */
@SpringBootTest
public class SelectTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList() {
        System.out.println(userMapper.selectList(null));
    }

    @Test
    public void testSelectById() {
        UserDO user = userMapper.selectById(1L);
        System.out.println(user);
    }

    @Test
    public void testSelectBatchIds() {
        List<Long> list = Arrays.asList(1L, 2L, 3L);
        List<UserDO> users = userMapper.selectBatchIds(list);
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "Zhangsan");
        map.put("age", 20);
        List<UserDO> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    /**
     * 自定义方法测试
     */
    @Test
    public void testSelectMapById() {
        Map<String, UserDO> map = userMapper.getMapById(1L);
        System.out.println(map);
    }
}

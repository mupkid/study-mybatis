package org.ohx.studymybatisplus;

import org.junit.jupiter.api.Test;
import org.ohx.studymybatisplus.dal.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BaseMapper Delete测试类
 *
 * @author mudkip
 * @date 2022/5/11
 */
@SpringBootTest
public class DeleteTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testDeleteById() {
        // 通过主键ID删除数据
        int result = userMapper.deleteById(1524043329611849729L);
        System.out.println(result);
    }

    @Test
    public void testDeleteByMap() {
        // 根据map集合中设置的条件删除数据
        Map<String, Object> map = new HashMap<>();
        map.put("username", "张三");
        map.put("age", 23);
        int result = userMapper.deleteByMap(map);
        System.out.println("Result: " + result);
    }

    @Test
    public void testDeleteBatchIds() {
        // 根据多个主键id批量删除
        List<Long> list = Arrays.asList(11L, 12L, 13L);
        int result = userMapper.deleteBatchIds(list);
        System.out.println("Result: " + result);
    }
}

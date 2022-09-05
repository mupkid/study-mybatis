package org.ohx.studymybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.ohx.studymybatisplus.dal.mapper.UserMapper;
import org.ohx.studymybatisplus.dal.model.dataobject.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 分页插件功能测试
 *
 * @author mudkip
 * @date 2022/9/4
 */
@SpringBootTest
public class MyBatisPlusPluginTest {
    @Autowired
    private UserMapper userMapper;

    /**
     * 测试 BaseMapper 中的 page 方法
     */
    @Test
    public void testSelectPage() {
        Page<User> userPage = userMapper.selectPage(new Page<>(1, 3), null);
        System.out.println(userPage);
    }

    /**
     * 测试自定义的分页方法
     */
    @Test
    public void testPageUserOverAge() {
        IPage<User> userPage = userMapper.pageUserOverAge(new Page<>(1, 3), 15);
        System.out.println(userPage);
        userPage = userMapper.pageUserOverAge(null, 15);
        System.out.println(userPage);
    }

    @Test
    public void testPageUserBelowAge() {
        List<User> userPage = userMapper.pageUserBelowAge(new Page<>(1, 3), 25);
        System.out.println(userPage);
        userPage = userMapper.pageUserBelowAge(null, 25);
        System.out.println(userPage);
    }
}

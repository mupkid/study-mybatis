package org.ohx.studymybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.ohx.studymybatis.dal.mapper.UserMapper;
import org.ohx.studymybatis.dal.model.dataobject.UserDO;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mudkip
 * @date 2022/9/5
 */
public class SelectTest {

    @Test
    public void testSelectById() throws IOException {
        // 加载配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");

        // 获取 SqlSessionFactory
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        // 获取 SqlSession，即与数据库的连接会话，true 代表自动提交事务
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 获取 Mapper 接口对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 功能测试
        UserDO user = mapper.getUserById(1);
        System.out.println(user);
    }
}

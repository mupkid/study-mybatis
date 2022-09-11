package org.ohx.studymybatisspring.config;

import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 相当于 MyBatis 的 XML 配置文件
 * <p>
 * 使用{@link MapperScan}扫描 Mapper 包获得所有 Mapper，底层与{@link MapperScannerRegistrar}有关，所以也可以自定义
 * {@link MapperScannerConfigurer}来自定义规则。
 *
 * @author mudkip
 * @date 2022/9/11
 */
@Configuration
@MapperScan("org.ohx.studymybatisspring.dal.mapper")
public class MyBatisConfig {
    @Bean
    public SqlSessionFactory getSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        // 相当于 XML 配置中的 setting 标签
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(Slf4jImpl.class);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLazyLoadingEnabled(true);
        sqlSessionFactoryBean.setConfiguration(configuration);

        return sqlSessionFactoryBean.getObject();
    }
}

# Spring 项目整合 MyBatis

## 1 搭项目
先把常规的 Spring 项目搭起来，配置好数据源，这里使用 Druid。

## 2 引入 MyBatis

然后引入 MyBatis 的依赖，建好 DO 和 对应的 Mapper。

```xml
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
</dependency>
<dependency>
<groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.7</version>
</dependency>
```

mybatis-spring 是 MyBatis 提供的用来与 Spring 整合的包。

## 3 MyBatis 配置文件

用全注解方式配置 MyBatis。

```java
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
```

使用`@MapperScan`扫描 Mapper 包获得所有 Mapper，底层与`MapperScannerRegistrar`有关，所以也可以通过自定义`MapperScannerConfigurer`规则来扫描包。

## 4 测试类

然后就可以从 Spring 容器中获取 Mapper 了。


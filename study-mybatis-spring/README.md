# Spring 项目整合 MyBatis

## 1 搭项目
先把常规的 Spring 项目搭起来。

然后引入数据源和 MyBatis 的依赖，建好 DO 和 对应的 Mapper。

接下来的问题就是：应该怎么样才能把 Mapper 交给 Spring 去管理呢？

## 2 

普通项目引入 MyBatis 时，是在 mybatis-config.xml 的配置文件中配置所有的 Mapper，然后加载配置文件得到 SqlSessionFactory，
然后得到 SqlSession，最后得到需要的 Mapper，那么就首先把 SqlSessionFactory 加入到 Spring 中。

## Mapper扫描
有两种方法，一是在启动类上加上`@MapperScan`注解，扫描 Mapper 文件夹。
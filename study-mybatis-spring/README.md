# Spring 项目整合 MyBatis

## 1 搭项目
先把常规的 Spring 项目搭起来。

然后引入数据源和 MyBatis 的依赖，建好 DO 和 对应的 Mapper。

## 2 MyBatis 配置类



## Mapper扫描
有两种方法，一是在启动类上加上`@MapperScan`注解，扫描 Mapper 文件夹。
二是直接在类上加`@Mapper`注解。
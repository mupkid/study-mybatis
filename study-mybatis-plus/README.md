# MyBatis-Plus 的学习笔记

[MyBatis-Plus官网]([MyBatis-Plus (baomidou.com)](https://baomidou.com/))

## 入门

先搭建一个常规的 SSM 项目，数据库随意。

### 引入 MyBatis-Plus 包

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>最新版本</version>
</dependency>
```

包中已经引入了 MyBatis，不需额外引入。

### Dao 层

```java
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
```

Mapper 接口继承 BaseMapper\<T>，T 是对应的实体类，这样就获得了增强的CRUD接口了，不需要写 XML 文件。

### Service 层

```java
public interface UserService extends IService<User> {
}
```

```java
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
```

Service 接口继承 IService\<T>，实现类继承 ServiceImpl\<M extends BaseMapper\<T>, T>，这样就获得了增强的CRUD接口方法了。

### 测试

## 常用注解

### @TableName

### @TableId

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TableId {
    String value() default "";

    IdType type() default IdType.NONE;
}
```

MyBatis-Plus在实现CRUD时，会默认将id作为主键列，并在插入数据时，默认基于雪花算法的策略生成id。

如果表主键列不叫 id，则会报错。

这时可以在实体类的主键属性上使用`@TableId`，将该属性标识为主键列即可。

* value 属性。如果实体类的主键属性名字与表主键列的名字不一样时，可以使用 value 属性指明。
* type 属性。用来定义主键策略。
  * IdType.AUTO：数据库 ID 自增。
  * IdType.NONE：默认值，无状态，该类型为未设置主键类型（注解里等于跟随全局，全局里约等于 INPUT）。
  * IdType.INPUT：insert 前自行 set 主键值。该类型可以通过自己注册自动填充插件进行填充。
  * IdType.ASSIGN_ID：分配 ID，主键类型为 Number（Long 和 Integer）或 String（since 3.3.0），使用接口`IdentifierGenerator`的方法`nextId`（默认实现类为`DefaultIdentifierGenerator`雪花算法）。
  * IdType.ASSIGN_UUID：分配 UUID，主键类型为 String（since 3.3.0），使用接口`IdentifierGenerator`的方法`nextUUID`（默认 default 方法）。

---

**那么为什么默认是 NONE，却是默认基于雪花算法的策略呢？**

按官网的说法，NONE 约等于 INPUT，而 INPUT 是跟随系统中注册的填充插件进行填充的，既然没有自定义插件，就找找默认的填充插件是哪个。

从配置项开始，我们知道 mybatis-plus.global-config.db-config.id-type 这个属性是负责修改主键策略的，那么直接找到配置类`MybatisPlusProperties`

```java
public class MybatisPlusProperties {
    @NestedConfigurationProperty
    private GlobalConfig globalConfig = GlobalConfigUtils.defaults();
}
```

走进`GlobalConfigUtils.defaults();`发现里面是

```java
/**
 * 获取默认 MybatisGlobalConfig
 */
public static GlobalConfig defaults() {
    return new GlobalConfig().setDbConfig(new GlobalConfig.DbConfig());
}
```

再走到这个`GlobalConfig.DbConfig()`一看。

```java
public class GlobalConfig implements Serializable {
    public static class DbConfig {
        /**
         * 主键类型
         */
        private IdType idType = IdType.ASSIGN_ID;
    }
}
```

那就知道了，如果不配置，那么就会默认使用`IdType.ASSIGN_ID`作为主键策略，即雪花算法。

在 MybatisSqlSessionFactoryBuilder#build 中给 GlobalConfigUtils 创建默认的 ID 生成器。

```java
public class MybatisSqlSessionFactoryBuilder extends SqlSessionFactoryBuilder {
    @Override
    public SqlSessionFactory build(Configuration configuration) {
        GlobalConfig globalConfig = GlobalConfigUtils.getGlobalConfig(configuration);

        final IdentifierGenerator identifierGenerator;
        if (null == globalConfig.getIdentifierGenerator()) {
            identifierGenerator = new DefaultIdentifierGenerator();
            globalConfig.setIdentifierGenerator(identifierGenerator);
        } else {
            identifierGenerator = globalConfig.getIdentifierGenerator();
        }
        IdWorker.setIdentifierGenerator(identifierGenerator);
        ...
        return sqlSessionFactory;
    }
}
```

在`DefaultIdentifierGenerator`中，最核心的方法是`nextId()`，内部是调用了`Sequence#nextId()`方法，这个 Sequence 又是涉及到[别的项目](https://gitee.com/yu120/sequence)了，方法中就是生成雪花 ID 的算法。

在`MybatisParameterHandler#populateKeys`中会判断主键生成策略，如果是 ASSIGN 的，就会调用`DefaultIdentifierGenerator`获取雪花 ID并加到实例的主键字段上。

### @TableField

### @TableLogic

指明表字段是逻辑删除字段。默认 0 表示未删除，1 表示已删除。

## CRUD 接口

### Service CRUD

### DAO CRUD

## 条件构造器

* Wrapper：条件构造器抽象类。最顶层父类。
  * AbstractWrapper：查询条件封装，生成 SQL 的 where 条件。
    * QueryWrapper：查询条件封装。
    * UpdateWrapper：更新条件封装
    * AbstractLambdaWrapper：使用 Lambda 语法。
      * LambdaQueryWrapper：用于使用 Lambda 语法查询 Wrapper
      * LambdaUpdateWrapper：更新封装 Wrapper

## 分页插件

MyBatis-Plus 自带分页插件，只需要简单的配置即可实现分页功能。

添加配置类

```java
@Configuration
@MapperScan("org/ohx/studymybatisplus/dal/mapper")
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        return interceptor;
    }
}
```

分页查询有两种方式，一是调用 BaseMapper#selectPage 来分页。

```java
Page<User> userPage = userMapper.selectPage(new Page<>(1, 3), null);
```

二是自定义方法分页。XML 中的 SQL 正常写，要改的只有接口定义上。

```java
IPage<User> pageUserOverAge(@Param("page") IPage<User> page, @Param("age") Integer age);
```

如果返回类型是 IPage 则入参的 IPage 不能为 null，因为返回的 IPage 等于入参的 IPage。

如果想临时不分页，可以在初始化 IPage 时令 size < 0。

还可以这样

```
List<User> pageUserOverAge(@Param("page") IPage<User> page, @Param("age") Integer age);
```

如果返回类型是 List 则入场可以为 null，为 null 就不分页。但官网说需要自行设置 Page 中的 Records 属性（即返回的 List）？什么意思没看懂。

推荐第一个参数是 IPage，具体理由没找到，说是自定义的 IPage 实现类如果不放第一会有问题，没测试过不确定。

## 乐观锁插件

乐观锁实现方式：

1. 取出记录时，获取记录的 version 字段，称 oldVersion。

2. 更新记录时，用 oldVersion 作为条件，把 version 修改为 newVersion，即执行`set version = newVersion where version = oldVersion`。
3. 那么如果 version 不一致的话，更新就会失败。

配置过程，在配置类中加上乐观锁插件。

```java
@Configuration
@MapperScan("org/ohx/studymybatisplus/dal/mapper")
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        
        return interceptor;
    }
}
```

表和实体类肯定也得有一个 version 字段。

```java
@Version
private Integer version;
```

然后正常使用即可。

## 通用枚举

经常有这么的场景：数据有些属性只有特定的值，我们在代码中会使用枚举来提高可读性，但是在数据库中却是以整型的方式存储。那么就经常要对枚举进行转换，相当繁琐。

对此 MyBatis 本身也有对应的一套方案，但是配置起来比较繁琐。

MyBatis-Plus 就对这套方案进行了封装，提供了更简洁优雅的方式去转换枚举属性。

第一步，有两种方式，一是使用`@EnumValue`注解枚举属性。

```java
@Getter
public enum SexEnum {
    MALE(1, "男"),
    FEMALE(2, "女");

    // @EnumValue 表示数据库存的值
    @EnumValue
    private final Integer sex;
    private final String sexName;

    SexEnum(Integer sex, String sexName) {
        this.sex = sex;
        this.sexName = sexName;
    }
}
```

或者，令枚举属性实现`IEnum`接口，没必要，不写了。

然后把 DO 中对应的属性直接改成枚举类。

第二步，扫描通用枚举（3.5.2 版本后，这一步不需要再配置，所以赶快升级），也有两种方式。

一是指定枚举类包路径扫描。那么将使用`MybatisEnumTypeHandler`进行扫描。

```properties
mybatis-plus:
    # 支持统配符 * 或者 ; 分割
    typeEnumsPackage: com.baomidou.springboot.entity.enums
  ....
```

二是修改 MyBatis 使用的`EnumTypeHandler`，看不懂不写了。

## 代码生成器

[项目地址]([baomidou/generator: Any Code generator (github.com)](https://github.com/baomidou/generator))



## 多数据源

> 这是一个第三方 mybatis 扩展库，与 mybatis-plus 本身无关，属于组织参与者小锅盖个人发起的项目，任何行为与 baomidou 组织其它成员无关。

[项目地址](https://github.com/baomidou/dynamic-datasource-spring-boot-starter)

[项目文档](https://www.kancloud.cn/tracy5546/dynamic-datasource/2264611)

支持场景：纯粹多库、读写分离、一主多从、混合模式

1. 引入依赖

```xml
<dependency>
  <groupId>com.baomidou</groupId>
  <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
  <version>${version}</version>
</dependency>
```

2. 配置数据源

```properties
spring:
  datasource:
    dynamic:
      primary: master #设置默认的数据源或者数据源组,默认值即为master
      strict: false #严格匹配数据源,默认false. true未匹配到指定数据源时抛异常,false使用默认数据源
      datasource:
        master:
          url: jdbc:mysql://xx.xx.xx.xx:3306/dynamic
          username: root
          password: 123456
          driver-class-name: com.mysql.jdbc.Driver # 3.2.0开始支持SPI可省略此配置
        slave_1:
          url: jdbc:mysql://xx.xx.xx.xx:3307/dynamic
          username: root
          password: 123456
          driver-class-name: com.mysql.jdbc.Driver
        slave_2:
          url: ENC(xxxxx) # 内置加密,使用请查看详细文档
          username: ENC(xxxxx)
          password: ENC(xxxxx)
          driver-class-name: com.mysql.jdbc.Driver
       #......省略
       #以上会配置一个默认库master，一个组slave下有两个子库slave_1,slave_2
```

3. 使用`@DS`注解切换数据源

`@DS`可以作用在方法上和类上，同时存在就近原则。



## MyBatisX

MyBatisX 是一个 IDEA 的插件。

[项目地址](https://gitee.com/baomidou/MybatisX)

[MyBatisCodeHelper-Pro的文档](http://118.24.53.162/#/README)

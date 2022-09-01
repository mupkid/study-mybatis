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

再看 mybatis-plus 的 starter 包中源码，可以发现看到一个名为`IdentifierGeneratorAutoConfiguration`的类。

```java
@Lazy
@Configuration(proxyBeanMethods = false)
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class IdentifierGeneratorAutoConfiguration {
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(InetUtils.class)
    public static class InetUtilsAutoConfig {
        @Bean
        @ConditionalOnMissingBean
        public IdentifierGenerator identifierGenerator(InetUtils inetUtils) {
            return new DefaultIdentifierGenerator(inetUtils.findFirstNonLoopbackAddress());
        }
    }
}
```

如果用户没有自定义 ID 生成器，那么会返回`DefaultIdentifierGenerator`作为默认的 ID 生成器。

## CRUD 接口

### Service CRUD

### DAO CRUD




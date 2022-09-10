# MyBatis 学习笔记

[MyBatis 项目源码](https://github.com/mybatis/mybatis-3)

[Mybatis 官方文档](https://mybatis.org/mybatis-3/zh/index.html)

这个示例演示常规项目引入 MyBatis 后怎么使用 MyBatis，即演示 MyBatis 最原生的使用方法。

## 入门

### Mapper扫描
有两种方法，一是在启动类上加上`@MapperScan`注解，扫描 Mapper 文件夹。





## MyBatis 缓存

### MyBatis 一级缓存

MyBatis 的一级缓存是 SqlSession 级别的，通过同一个 SqlSession 查询的数据会被缓存，下次查询相同的数据，就会从缓存中直接获取，不会从数据库中访问。

MyBatis 的一级缓存默认是开启的。

一级缓存失效的情况：

1. SqlSession 不同。
2. SqlSession 相同，两次查询的条件不同。
3. SqlSession 相同，两次相同查询期间执行了任何一次增删改操作。
4. SqlSession 相同，两次相同查询期间手动清空了缓存。

### MyBatis 二级缓存

MyBatis 的二级缓存是 SqlSessionFactory 级别，通过同一个 SqlSessionFactory 创建的 SqlSession 查询的结果会被缓存，此后若执行相同的查询语句，就从缓存中返回结果。

MyBatis 的二级缓存默认是不开启的，需要自行配置。

开启二级缓存的条件：

1. 在核心配置文件中，设置全局配置属性 cacheEnabled = "true"，这个属性默认为 true，因此可以跳过。
2. 在映射文件中设置标签\<cache/>

3. 二级缓存必须在 SqlSession 关闭或提交之后才有效。

4. 查询的数据所转换的实体类类型必须实现`Serializable` 接口。

二级缓存失效情况：

1. 两次查询之间执行了任意的增删改。

在 mapper 文件中添加的 cache 标签可以设置一些属性：

* eviction：缓存回收策略
  * LRU：最近最少使用，移除最长时间不被使用的对象。默认值
  * FIFO：先进先出，按缓存的顺序来移除
  * SOFT：软引用，移除基于垃圾回收器状态和软引用规则的对象
  * WEAK：弱引用，更积极地移除基于垃圾回收器状态和弱引用规则的对象
* flushInterval：刷新间隔，单位毫秒。默认是不设置，即没有刷新间隔，缓存仅调用语句时刷新。
* size：引用数目，正整数。代表缓存最多可以存储多少个对项，太大容易导致内存溢出。
* readOnly：是否只读
  * true：只读缓存，会给所有调用者返回缓存对象的相同实例。因此这些对象不能被修改。这提供了很重要的性能优势。
  * false：读写缓存，会返回缓存对象的拷贝（通过序列化），这会慢一些，但是安全。默认值。

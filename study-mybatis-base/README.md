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

### 缓存的访问顺序

先查询二级缓存，因为二级缓存中可能会有其他程序已经查出来的数据，可以直接使用。

如果二级缓存没有命中，再查询一级缓存。

如果一级缓存也没有命中，则查询数据库。

SqlSession 关闭之后，一级缓存中的数据会写入二级缓存。

### 整合第三方缓存 EHCache

[mybatis-ehcache 文档](https://mybatis.org/ehcache-cache/index.html)

只能代替二级缓存，不能代替一级缓存。

EHCache 配置说明。

| 属性名                          | 是否必须 | 作用                                                         |
| ------------------------------- | -------- | ------------------------------------------------------------ |
| maxElementsInMemory             | 是       | 在内存中缓存的element的最大数目                              |
| maxElementsOnDisk               | 是       | 在磁盘上缓存的element的最大数目，若是0表示无 穷大            |
| eternal                         | 是       | 设定缓存的elements是否永远不过期。 如果为 true，则缓存的数据始终有效， 如果为false那么还 要根据timeToIdleSeconds、timeToLiveSeconds 判断 |
| overflowToDisk                  | 是       | 设定当内存缓存溢出的时候是否将过期的element 缓存到磁盘上     |
| timeToIdleSeconds               | 否       | 当缓存在EhCache中的数据前后两次访问的时间超 过timeToIdleSeconds的属性取值时， 这些数据便 会删除，默认值是0,也就是可闲置时间无穷大 |
| timeToLiveSeconds               | 否       | 缓存element的有效生命期，默认是0.,也就是 element存活时间无穷大 |
| diskSpoolBufferSizeMB           | 否       | DiskStore(磁盘缓存)的缓存区大小。默认是 30MB。每个Cache都应该有自己的一个缓冲区 |
| diskPersistent                  | 否       | 在VM重启的时候是否启用磁盘保存EhCache中的数 据，默认是false。 |
| diskExpiryThreadIntervalSeconds | 否       | 磁盘缓存的清理线程运行间隔，默认是120秒。每 个120s， 相应的线程会进行一次EhCache中数据的 清理工作 |
| memoryStoreEvictionPolicy       | 否       | 当内存缓存达到最大，有新的element加入的时 候， 移除缓存中element的策略。 默认是LRU（最 近最少使用），可选的有LFU（最不常使用）和 FIFO（先进先出） |


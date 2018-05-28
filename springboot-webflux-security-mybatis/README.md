
集成Spring和Mybatis 至少需要一个 SqlSessionFactory 和 一个Mapper 接口.

**mybatis-spring-boot-starter** 会执行下面的工作:

- 自动检测现有的 **DataSource**
- 创建和注册 **SqlSessionFactory** 实例
- 创建和注册 **SqlSessionTemplate** 实例
- 自动扫描 Mapper

> Bean 注入使用 Setter 注入, 方便脱离容器进行测试.

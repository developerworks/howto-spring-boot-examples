## Spring 事务

Spring 事务管理是由 **org.springframework:spring-tx.XXX.jar** 这个包提供的.

 

### 编程式事务

### 声明式事务

声明式事务是通过事务注解进入切面函数(通过AOP的方式)进行处理, 切面函数调用方法是通过代理调用的. 


事务定义: 由接口 **org.springframework.transaction.TransactionDefinition** 定义.

PROPAGATION_REQUIRES_NEW

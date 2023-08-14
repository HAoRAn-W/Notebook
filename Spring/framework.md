# Spring框架
## 什么是Spring
轻量级，IoC和AOP容器框架。开箱即用，简化企业应用的开发

## 主要模块
![模块](../assets/Spring/spring_framework_runtime.png)
### Core Container
spring-core：核心类库，提供IOC依赖注入功能的支持；

spring-beans：提供对Bean的创建，配置，管理等功能的支持；

spring-context：提供国际化，事件传播，资源加载等功能的支持；

spring-expression: 提供对SpEL（Spring Expression Language）的支持，只依赖于core模块

### AOP
spring-aspects: 为与AspectJ的集成提供支持

spring-aop: 提供AOP的编程实现

spring-instrument: 提供为JVM添加代理（agent）的功能，为tomcat传递类文件，就如同这些文件是被类加载器加载的一样（？）

### Data Access
spring-jdbc: 提供数据库访问的抽象jdbc。屏蔽了不同数据库不同API实现的复杂性，Java程序只需要与JDBC API交互

spring-tx：提供事务支持

spring-orm: 提供抽象层支撑Object to XML mapping

### Spring Web
spring-web：对web功能实现的最基础支持

spring-webmvc：Spring MVC实现

spring-websocket: 对WebSocket的支持，客户端和服务端可以双向通信

spring-webflux: 响应式框架，不需要Servlet API，完全异步

## Spring框架用到的设计模式
### 工厂模式
使用BeanFactory, ApplicationContext创建Bean对象
### 代理模式
Spring AOP通过代理完成切面的advise
### 单例模式
Bean默认都是单例
### 适配器模式
SpringMVC通过适配器来适配controller，提供给DispatcherServelet调用
### 模板方法模式
jdbcTemplate, hibernateTemplate等数据库操作类
### 包装器模式
### 观察者模式

## Spring事务（TODO）

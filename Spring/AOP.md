# AOP面向切面编程
## 什么是AOP
将一些与具体业务无关,但却是业务模块共同需要的逻辑(事务处理,日志管理,权限控制)封装起来,自动,统一调用.

减少重复代码, 降低耦合度,增加可扩展性,可维护性

## Spring AOP
基于动态代理: 实现了接口的类: JDK dynamic proxy, 没有实现接口的类: Cglib

## AOP术语
**Aspect切面**: 跨越多个类的关注点的模块化, 比如事务管理

**Join point连接点**: Spring AOP中,连接点就是方法的执行

**Advice通知**: 切面在某一个连接点上的动作. 有许多类型(around, before, after, AfterReturning, AfterThrowing),可以看为围绕连接点的一系列拦截器

**Pointcut切入点**: advice和一个pointcut表达式联系在一起,用来匹配符合条件的一系列join points

**Introduction**: 向被代理的对象提供一些额外的方法或者fields. SpringAOP可以让被代理对象加入一个新接口

**Target object目标对象**: 被一个或者多个切面包含的对象也叫advised object

**Weaving织入**: 将切面与被代理对象结合起来形成一个新的代理对象.AspectJ在编译期织入(字节码操作),Spring AOP在运行时织入

## AspectJ
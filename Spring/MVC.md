# MVC
## 什么是MVC
一种设计模式：
Model: 处理业务逻辑（entity, dao, servie...）

View: 展示界面

Controller: 接收用户请求，操作模型，提供给页面
![mvc](../assets/Spring/mvc.png)

## SpringMVC
是Spring框架的后续产品，在原有基础上提供Web应用的mvc模块。
![mvc](../assets/Spring/springmvc.png)
1. 用户发送请求至前端控制器DispatcherServlet。
2. DispatcherServlet收到请求调用HandlerMapping
3. HandlerMapping找到具体的处理器(可以根据xml配置、注解进行查找)，生成处理器对象及处理器拦截器(如果有则生成)一并返回给DispatcherServlet。
4. DispatcherServlet调用HandlerAdapter处理器适配器。
5. HandlerAdapter经过适配调用具体的处理器(Controller，也叫后端控制器)。
6. Controller执行完成返回ModelAndView。
7. HandlerAdapter将controller执行结果ModelAndView返回给DispatcherServlet。
8. DispatcherServlet将ModelAndView传给ViewReslover视图解析器。
9. ViewReslover解析后返回具体View。
10. DispatcherServlet根据View进行渲染视图（即将模型数据填充至视图中）。
11. DispatcherServlet响应用户。

### HandlerMapping
Map url to method
### HandlerAdapter
[link](https://www.baeldung.com/spring-mvc-handler-adapters)

The HandlerAdapter is basically an interface which facilitates the handling of HTTP requests in a very flexible manner in Spring MVC.

It's used in conjunction with the HandlerMapping, which maps a method to a specific URL.

The DispatcherServlet then uses a HandlerAdapter to invoke this method. The servlet doesn't invoke the method directly – it basically serves as a bridge between itself and the handler objects, leading to a loosely coupling design.
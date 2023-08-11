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

1、首先用户发送请求——>DispatcherServlet，前端控制器收到
请求后自己不进行处理，而是委托给其他的解析器进行处理，作为统一访问点，进行全局的流程控
制； 2、DispatcherServlet——>HandlerMapping， HandlerMapping 将会把请求映射为
HandlerExecutionChain 对象（包含一个Handler 处理器（页面控制器）对象、多个
HandlerInterceptor 拦截器）对象，通过这种策略模式，很容易添加新的映射策略； 3、
DispatcherServlet——>HandlerAdapter，HandlerAdapter 将会把处理器包装为适配器，从而支
持多种类型的处理器，即适配器设计模式的应用，从而很容易支持很多类型的处理器； 4、
HandlerAdapter——>处理器功能处理方法的调用，HandlerAdapter 将会根据适配的结果调用真
正的处理器的功能处理方法，完成功能处理；并返回一个ModelAndView 对象（包含模型数据、逻
辑视图名）； 5、ModelAndView的逻辑视图名——> ViewResolver， ViewResolver 将把逻辑视图
名解析为具体的View，通过这种策略模式，很容易更换其他视图技术； 6、View——>渲染，View
会根据传进来的Model模型数据进行渲染，此处的Model实际是一个Map数据结构，因此很容易支
持其他视图技术； 7、返回控制权给DispatcherServlet，由DispatcherServlet返回响应给用户，到
此一个流程结束。

### HandlerMapping
Map url to method
### HandlerAdapter
[link](https://www.baeldung.com/spring-mvc-handler-adapters)

The HandlerAdapter is basically an interface which facilitates the handling of HTTP requests in a very flexible manner in Spring MVC.

It's used in conjunction with the HandlerMapping, which maps a method to a specific URL.

The DispatcherServlet then uses a HandlerAdapter to invoke this method. The servlet doesn't invoke the method directly – it basically serves as a bridge between itself and the handler objects, leading to a loosely coupling design.

## 常用注解：
```@RequestMapping```: 将URL请求映射到**类**或**方法**上。用于类上，则表示类中
的所有响应请求的方法都是以该地址作为父路径

```@RequestBody```：将HTTP请求的JSON body转换为入参的对象

```@ResponseBody```：方法返回的对象作为JSON response body
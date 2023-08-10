# 依赖注入
## 依赖注入的几种方式
[出处：Spring选择哪种注入方式](https://juejin.cn/post/7021902992706109476)
### 1. 构造器注入
依赖不可变，使用final定义field，保证注入后的不可变

在传参时传入的是依赖对象，而该对象传入时会保证对象类已经完成了初始化，也同时保证了对象不为null

如果构造器注入时发生了循环依赖，在项目启动时就会报错BeanCurrentlyInCreationException，而Field注入只有在使用时才会报错

### 2. setter方法注入
可以重新配置或注入虽然方便，但是有些依赖需要不可变，使用此种方式产生不确定性

### 3. field注入
不调用依赖bean时，不会发现空指针的异常，在运行时调用会报异常

使用field注入可能会导致循环依赖，即A里面注入B，B里面又注入A

会造成依赖臃肿，职责过多，使用Filed注入时，添加数量不受限制（没有警告），可能会注入过多的依赖项，违反了单一职责原则。

## @Autowired与@Qualifier
@Autowired直接是byType方式注入的，如果有相同类型的多个bean，可以配合@Qualifier注解来指定名字，其他的注解如@Service, @Repository,也是支持传入名字的
```java
@Component("fooFormatter")
public class FooFormatter implements Formatter {
 
    public String format() {
        return "foo";
    }
}

@Component("barFormatter")
public class BarFormatter implements Formatter {
 
    public String format() {
        return "bar";
    }
}

@Component
public class FooService {
     
    @Autowired
    private Formatter formatter;
}
```
或者也可以在定义bean的时候用@Qualifier，和上面的效果是一样的：
```java
@Component
@Qualifier("fooFormatter")
public class FooFormatter implements Formatter {
    //...
}
```
如果我们在@Autowired的时候用了某一个bean的指定名字，也可以不用@Qualifier注解：
```java
public class FooService {
     
    @Autowired
    private Formatter fooFormatter;
}
```

## @Resource注解
注入顺序：
### 1. 名称匹配
如果没有指定```name="myMovieFinder"```, Spring默认按照field name/parameter name来寻找bean
```java
public class SimpleMovieLister {

	private MovieFinder movieFinder;

	@Resource(name="myMovieFinder") 
	public void setMovieFinder(MovieFinder movieFinder) {
		this.movieFinder = movieFinder;
	}
}
```
### 2. 类型匹配
先按照```name```名称查找，没找到就退回到用```CustomerPreferenceDao```类型去查找
```java
public class MovieRecommender {

	@Resource
	private CustomerPreferenceDao name;

	public MovieRecommender() {
	}

	// ...
}
```
## @Autowired与@Resource
### 相同：
1. 用于注入bean，都可以用于字段和方法
### 不同：
1. @Autowired还可以用在入参上
2. @Autowired为Spring提供的注解，@Resource由Jakarta EE提供
3. @Autowired默认先by-type，@Resource默认先by-name
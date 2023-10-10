# Java 语言特点

- 面向对象（封装，继承，多态）
  - 封装：把客观事物抽象成类
  - 继承：可以使用现有类的功能，无需编写原有的代码而对功能进行扩展
  - 多态：同一个方法在父类和子类中由不同的表现
- 平台无关性（JVM 虚拟机机制）
- 支持多线程
- 编译与解释并存
- 支持方便的网络编程

# Java 如何实现多态

## 编译时多态

overload 重载，同一个类中，方法名字相同但是参数不同。返回类型可能也不相同。

在编译的时候就已经确定。

## 运行时多态

三个条件：继承，override 重写（方法名称，参数不变，返回类型是同类或子类），向上转型。实现在同一个继承结构中使用统一的逻辑实现不同的行为。在多态中需要把子类的引用赋给父类对象。

# 字节码

Java 代码经过编译成为 JVM 可以执行的字节码（.class 文件）。无论在什么平台，编译出的字节码都是固定格式的。

# Java 数据类型

## 基本数据类型

布尔：boolean
字符：char（2 字节）
数值：byte, short, int, long, float, double

## 引用数据类型

类，接口，数组

# 访问修饰符

public, protected, default, private

当前类，同一包内，同一包下的子孙类，不同包下的子孙类，其他类

# final, finally, finalize

final 是一个修饰符：

- 修饰变量：变量的值不可以改变。必须初始化。成为一个常量
- 修饰方法：不可以被子类重写
- 修饰类：不可以被继承

finally 是异常处理的一部分：放在 try 或者 catch 之后，表示这段语句一定会被执行。

finalize 是 java Object 类里定义的方法，在对象被垃圾回收的时候调用。但是调用该方法不代表对象一定会被回收

# static 关键字

表明一个变量或者方法是属于类的，不需要一个实例就可以直接访问。

static 方法不能被覆盖，因为方法覆盖是基于运行时动态绑定的。static 方法是编译时静态绑定的。

不可以在 static 环境中访问非 static 的变量，方法，资源

# 代码块的执行顺序

静态代码块（类加载时运行，只运行一次） -> 构造代码块（每次创建新对象都会调用） -> 普通代码块（定义在方法体中）

继承时：父类静态代码块 ->子类静态代码块 -> 父类构造代码块 -> 父类构造器 -> 子类构造代码块 -> 子类构造器

# 抽象类和接口

- 抽象类的成员变量可以是任何类型，但是接口中的变量只能是 public static final
- 一个类只可以继承一个类，但是可以实现多个接口

## 设计层面上

- 抽象类是对一种事物的抽象，是对整体的抽象，是模板式的设计
- 接口是对行为的抽象，是辐射式的设计

# Java 创建对象的方式

## new

## 反射

反射就是在运行时，对于任意一个类，都可以知道这个类的所有属性和方法。对任意一个对象，都可以调用它的任意一个方法和属性。动态地获取信息，动态地调用对象的方法。

- 优点：灵活，可以与动态编译结合
- 缺点：性能低，需要解析字节码

### 获取类对象的方法：

```java
Class clz = Class.forName("java.lang.String")
Class clz = String.class;

String str = new String("hello");
Class clz = str.getClass();
```

### 反射 API

1. Class 类：核心类，可以获取类的属性，方法等信息
2. Field 类：表示类的成员变量。
3. Method 类：获取类的方法信息，执行相关方法
4. Constructor 类：获取类的构造器

### 反射机制的应用

1. 可以通过外部类的全路径名创建对象，实现一些扩展功能
2. 可以枚举出类的全部成员，帮助开发者写出正确的代码
3. 测试时可以利用反射 API 访问类的私有成员，保证覆盖率

例子：

1. JDBC 的数据库链接：使用 Class forName 加载数据库驱动程序，链接数据库
2. Spring 框架通过 XML 配置，加载 Bean，使用反射机制通过字符串获取类的 Class 实例，并动态配置类的属性

## clone

## 序列化

# hashCode()

用于获取一个对象的哈希码，返回一个 int 整数，未被重写的情况下，这个整数是对象的存储位置决定的。

如果两个对象 equals，那么他们的 hashCode 一定相等。反之不一定成立。因此重写 equals 方法的同时必须重写 hashCode 方法。否则在一些哈希容器中，会出现同一个对象被存储了两次，而无法覆盖原先记录的情况。

# String 为什么要设计为不可变

- 便于实现字符串池
- 避免安全问题
- 线程安全
- 可以缓存 hashCode 加快处理速度

# 包装类型

为每一个基本数据类型都提供了一个包装类型：

- 布尔：Boolean
- 字符：Character
- 数字：Byte, Short, Integer, Long, Float, Double

包装类型可以用于泛型。

## 自动装箱和拆箱

- 装箱：将基本数据类型自动转化为包装类型。
  - 除了 FLoat, Double 外，自动装箱都会对一定范围内的数据进行缓存。因此返回的时相同的引用
- 拆箱：将包装类型转化为基本类型

# 泛型

将类型参数化。在编译时才确定具体的类型。可以用在类，接口，方法的创建中。

## 泛型的好处

之前的版本，每次使用都需要强制转换；编译时编译器不知道类型是否正确，只有在运行时才知道

使用泛型之后：

1. 类型安全，编译器就可以检查出错误，减少出错机会
2. 消除强制类型转换的必要

## 原理

类型擦除。泛型只存在于编译阶段，不存在于运行阶段。使用泛型的时候我们提供的类型参数，在编译的时候会被去掉，变成了 Object 类型（如果使用了 extends 或者 super 会用子类或者父类），编译器会帮助我们进行插入时的泛型检查；在返回泛型的时候，会自动的插入强制类型转换的操作。

## 限定通配符

- extends T: 规定了类型的上界。必须是 T 的子类或者 T
- super T: 规定了类型的下届。必须是 T 的父类或者 T

"T extends E" is used when **defining** a type parameter for a generic class or method, allowing you to work with a specific type or its subtypes.

"? extends E" is used as a wildcard when you want to work with an unknown subtype of a given type, **often in method signatures** to make them more flexible.

"extends" is typically used when you want to read from a generic type parameter, and "super" is used when you want to write to it.

# 序列化和反序列化

Java 对象和有序字节流之间的转化过程（对象的保存和重建）：

- 实现分布式的对象，如 RPC
- 可以将对象持久化储存在数据库或文件中

序列化便于把 Java 对象在网络上传输，或者保存在本地文件中。

## 实现序列化的方式

1. 实现 Serializable 接口：仅用于表示可序列化的语义。
2. 实现 Externalizable 接口，重写 writeExternal 和 readExternal 方法。

## serialVersionUID

用于标识类不同版本间的兼容性。

Java 序列化机制会在运行时判断类的 serialVersionUID 来验证版本的一致性。在反序列化时，把字节流中的 serialVersionUID 和本地的类的进行比较。相同才可以进行反序列化。否则抛出版本不一致的异常。

如果不显式的指定 serialVersionUID 的值，JVM 在序列化的时候就会自动生成一个值。如果显式指定了，在开发过程中对类进行迭代的时候也不会出现反序列化的报错。

## transient

对于不想进行序列化的字段，加上 transient 关键字修饰。只可以修饰变量。

注意：静态变量不会被序列化。因为它是属于类的

注意：serialVersionUID 也是被 static 修饰的，它不会被序列化，只会在反序列化的时候自动生成，然后赋值成我们指定的值。

# Error 和 Exception 的区别

Exception 和 Error 都是 Throwable 的子类。

- Exception 异常：程序本身可以处理的异常。可以通过 catch 捕获。又分为 RuntimeException 和 CheckedException。
  - 非受检异常：RuntimeException 类及其子类，如 NullPointerException，IndexOutOfBoundsException，ArithmeticException
  - 受检异常：IOException，SQLException，ClassNotFoundException
- Error 错误：程序无法处理的错误。无法通过 catch 捕获。如系统崩溃，内存不足，堆栈溢出等。

## 非受检异常和受检异常的区别

非受检异常在编译期间不会检查，是在 JVM 运行期间可能会出现的异常。

受检异常会在编译期间检查调用者是否处理，受检异常是强制要求调用者处理的。

## NoClassDefError 和 ClassNotFoundException 的区别

NoClassDefError 是一个错误，是由于 JVM 或者 ClassLoader 尝试加载某个类的时候在内存中找不到该类的定义。该类在编译期存在，但是在运行时找不到了。

ClassNotFoundException 是受检异常，需要捕获处理。当尝试动态加载类到内存中的时候没有在指定的路径找到。或者一个类加载器已经加载了，另一个类加载器又尝试去加载。

## try catch finally 哪个可以省略

catch 或者 finally 但是不能两个同时省略。

## 如果在 catch 中 return 了，finally 还会执行吗

finally 会在 return 之前执行。同时，如果在 finally 中还有 return，会返回 finally 的 return（即程序会提前退出）。如果在 finally 中只是修改返回值，那么最后 catch 的值返回的还是原来的值（finally 中的修改并没有生效，但如果返回的是引用类型，而且 finally 对引用类型的属性做了更改，这个是会生效的）

## JVM 如何处理异常

方法发生异常，创建异常对象，转交给 JVM，即抛出异常。JVM 顺着调用栈往上寻找可以处理异常的代码。如果没有找到的化，就会交给默认异常处理器（JVM 的一部分）处理，打印异常信息并终止程序。

# Java 常见集合

主要由 Collection 和 Map 派生出来。Collection 派生出三个子接口：List，Set，Queue

# 线程安全的集合

- HashTable：比 HashMap 多了个线程安全
- ConcurrentHashMap：高效且线程安全
- Vector：比 ArrayList 多了一个同步机制。每个关键方法前面都加了 synchronized 关键字
- Stack: 线程安全

# 线程不安全的集合

HashMap，ArrayList，LinkedList，HashSet，TreeSet，TreeMap

# ArrayList 扩容机制

在插入之前检查剩余空间，如果空间不足，则默认扩容为之前的 1.5 倍，将之前的数据用 Arrays.copyof 拷贝到新的内存空间。

# HashMap 底层数据结构 (TODO)

- JDK 1.7 数组 + 链表（解决哈希冲突）
- JDK 1.8 数组 + 链表 + 红黑树（改善性能）

## 红黑树转换条件

- 链表长度超过 8，数组长度超过 64（未超过 64，先扩容数组），转换为红黑树

## HashMap 扩容

HashMap 容量超过负载因子，或者链表长度超过 8 但是数组长度没超过 64 的时候，就会扩容

扩容将原来的 size 扩大为 2\*size （保证 2 的幂次）再把原来的对象放进新的数组（JDK1.7 头插法会出现元素倒置，JDK 1.8 尾插法元素顺序任然保持）

## HashMap 线程不安全

1. JDK1.7 中，多线程扩容会导致形成环形链表，导致 get 一个不存在的值的时候，出现死循环
2. 多线程 PUT，如果索引位置相同，会导致后一个 key 覆盖前一个 key
3. put 和 get 并发，同时 put 触发了扩容，会导致 get 的值为 null

## 不直接使用红黑树的原因

维护红黑树的操作比较耗时。单链表操作更简单。元素小于 8 个的时候查询已经可以保证性能。元素大于 8 个，红黑树搜索复杂度就是 O(logn)而链表是 O(n)，这个时候使用红黑树来改善查询效率。但是插入的效率会变低。

## HashMap 加载因子

加载因子是表示 HashMap 中元素的填满程度。加载因子越大，填满的元素越多，空间利用率高，但是冲突的概率更大。加载因子小，冲突概率小，但是空间浪费大。

加载因子就是对于冲突和空间利用之间的折中。

## HashMap 下标计算

1. hash = hashCode ^ (hashCode >>> 16)
2. index = hash & (n - 1)，n 是数组长度

HashMap 的容量是 2 的幂次方，这样就可以用与操作来代替取余操作。同时，由于取余操作只收到低位的影响（1111111），所以需要将高位与地位先异或，这样来减小碰撞。

# ConcurrentHashMap

## 实现原理

1.7 中，使用 Segment 数组和 HashEntry 数组构成。每个 Segment（继承了 ReentrantLock）有一把可重入锁。

1.8 中，仍然沿用数组，链表，红黑树的结构；在锁的是线上抛弃了 Segement 分段锁，使用粒度更小的 CAS+synchronized。只需要锁住链表的头节点或者红黑树的根节点，不影响其他桶元素的读写。这样提高了并发度。

## PUT 操作

### 1.7

尝试自旋获取锁，重试次数超过 64 次就阻塞获取锁。

获取到锁之后：

1. 在 Segment 中通过 key 定位 HashEntry
2. 遍历 HashEntry，执行 PUT 操作
3. 释放 Segment 锁

### 1.8

1. 计算 key 的 hash 值
2. 定位到 Node，判断首节点：
   - 为 null，cas 的方式添加
   - 首节点的 hash 为 MOVED，说明其他线程在扩容，参与一起扩容
   - 都不满足。synchronized 锁住首节点，遍历插入

## GET 操作

不需要加锁。因为 Node 元素的 val 和 next 指针是 volatile 修饰的，多线程下的修改对另一个线程来说是可见的。

## ConcurrentHashMap 不支持 value 为 null

因为无法确定 get 到为 null 的值到底是 value 为 null 还是没有找到 key

## ConcurrentHashMap 的迭代器

是弱一致性的，已经遍历过的部分发生变化不会反映出来，为遍历过的部分发生变化会反映出来。

## HashTable

是对整个 table 加了一个锁，因此多线程的竞争下性能非常差

# 快速失败 fail-fast 和安全失败 fail-safe

## fail-fast

- 迭代器遍历的时候对集合对象修改，会立马抛出异常
- java.util 下的集合类都是快速失败的

## fail-safe

- 安全失败的容器使用迭代器实在原有内容的拷贝上遍历的
- java.util.concurrent 下的集合类都是安全失败的，可以在多线程下并发修改

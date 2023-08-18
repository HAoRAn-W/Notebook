# MySQL

## 数据库的三范式

### 第一范式

列的原子性，每一列是不可分割的原子数据项

### 第二范式

实体的属性完全依赖（不存在只依赖于主关键字的一部分的属性）于主关键字

### 第三范式

任何非主属性不依赖于其他非主属性

## 键

**主键**：对表中对象予以唯一和完整标识的列的组合，一个对象只有一主键，且不能为空值

**外键**：一个表中存在的另一个表的主键，为此表的外键

**超键**：在关系中可以唯一标识元组的属性集。包含候选键和主键

**候选键**：最小超键，没有冗余元素

## MySQL 支持的存储引擎

InnoDB, MyISAM, Memory, Archive

|          | InnoDB         | MyISAM     |
| -------- | -------------- | ---------- |
| 事务     | ✅             | ❌         |
| 外键     | ✅             | ❌         |
| 索引     | 聚集索引       | 非聚集索引 |
| 全文索引 | ❌             | ✅         |
| 锁       | 行级锁，表级锁 | 表级锁     |

## 约束

**NOT NULL**: 字段内容不为空

**UNIQUE**：字段内容不重复

**PRIMARY KEY**：主键

**FOREIGN KEY**：外键，可以预防表之间关系的破坏动作 and 非法插入外键，因为必须是另一个表的主键值之一

**CHECK**：控制字段值的范围

```sql
CREATE TABLE Persons (
-- Create the "students" table
CREATE TABLE students (
    student_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    CONSTRAINT uq_student_id UNIQUE (student_id)
);

-- Create the "courses" table
CREATE TABLE courses (
    course_id INT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    max_students INT NOT NULL,
    CONSTRAINT chk_max_students CHECK (max_students > 0),
    CONSTRAINT fk_student FOREIGN KEY (course_id) REFERENCES students(student_id)
);

```

## varchar 与 char

char 是定长字段，无论实际存储多少内容，都只占用固定的长度：

- 实际长度小于 char 的长度，填充

- 大于 char 的长度：Strict SQL Mode:报错，Non-Strict SQL Mode:自动截取

varchar 变长字段，申请的只是最大长度，实际占用空间是字符长度 + 1

检索效率上 char 由于 varchar，所以如果预先知道字段的长度，优先使用 char

## in 与 exists

[来源](https://blog.csdn.net/wqc19920906/article/details/79800374)

**in**：首先执行子查询，将结果与外表做 hash join （m \* n 次）

```sql
SELECT * FROM A
WHERE id in (SELECT id FROM B);
```

伪代码：

```
List A = select * from A
List B = select id from B
result = []

for (a in A) {
    for (id in B) {
        if (a.id == id) {
            result.add(a)
            break
        }
    }
}
```

**exists**：返回 bool 变量，强调的是**是否**返回结果集，而不要求知道返回了什么。首先执行主查询， 再根据查询出的每一条记录判断 EXISTS 内的语句是否成立

```sql
SELECT * FROM A a
WHERE EXISTS (SELECT 1 FROM B b where a.id = b.id)
```

伪代码

```
List A = SELECT * FROM A
result = []

for (a in A) {
    if exists(SELECT 1 FROM B b where a.id = b.id) {
        result.add(a)
    }
}

```

A 有 m 条数据，B 有 n 条数据：

1. 如果两个表大小相当，效率差不多
2. A 大 B 小，用 IN，在内存中遍历比较，而不需要反复查询数据库
3. A 小 B 大，用 EXISTS，只执行 m 次查询，n 越大效果越明显
4. not IN 会对内外表都执行全表扫描，而 not EXISTS 会对内表使用索引，所以 not EXISTS 会比 not IN 效率高

## DELETE，DROP，TRUNCATE

|          | DELETE                   | DROP                              | TRUNCATE                   |
| -------- | ------------------------ | --------------------------------- | -------------------------- |
| 类型     | DML                      | DDL                               | DDL                        |
| 回滚     | ✅                       | ❌                                | ❌                         |
| 删除内容 | 表结构还在，删除指定数据 | 删除表， 所有数据行，索索引，权限 | 表结构还在，所有数据行没了 |
| 速度     | 慢                       | 最快                              | 快                         |

## 存储过程

预编译的 SQL 语句，有一些 T-SQL 语句组成的代码块，像一个方法一样实现某些功能，之后可以直接调用。执行效率比较高，降低了通信量

但是复用性不好，不适合快速迭代的工程

## MySQL 执行查询的过程

1. **连接器**：客户端发起 TCP 连接请求，MySQL 连接器进行权限验证，资源分配
2. 直接使用 SQL 语句与客户端其他原始信息查询缓存（k-v 哈希表）（任何字符的不同如空格都会导致无法命中缓存）**MySQL 的 8.0 版本以后，缓存被官方删除掉了**（失效频繁）
3. **分析器**：语法分析，把语句给到预处理器，检查数据表，数据列是否存在，解析别名看是否存在歧义
4. **优化器**：优化 SQL，根据执行计划进行最优的选择，匹配合适的索引
5. **执行器**：调用存储引擎的 API，执行查询

## SQL 的执行顺序

```sql
select distinct s.id from
T t join S s on t.id=s.id
where t.name="Yrion"
group by t.mobile having count(*)>2
order by s.create_time
limit 5;
```

执行顺序：每个步骤都会产生一个虚拟表，该虚拟表被用作下一个步骤的输入

- FROM <left_table> ：组装来自不同数据源的数据

- <join_type> JOIN <right_table>

- ON <join_condition>

- WHERE <where_condition> ：基于指定的条件对记录行进行筛选

- GROUP BY <group_by_list> ：将数据划分为多个分组

- WITH {CUBE | ROLLUP}

- HAVING <having_condition> ：筛选分组

- SELECT

- DISTINCT

- ORDER BY <order_by_list> ：对结果集进行排序

## 什么是数据库事务

[参考](https://juejin.cn/post/7016165148020703246)

事务是一个**不可分割**的数据库操作序列，是并发控制的基本单位

事务的执行结果是数据库从一种**一致性状态**变为另一种**一致性状态**

四个特征：ACID

- A 原子性：不可分割，要么都做要么都不做
- C 一致性：执行的结果必须是从一个一致性状态到另一个一致性状态
- I 隔离性：并发执行的事务之间互不干扰
- D 永久性：事务一旦提交，对数据库的改变是永久的，后续的操作/故障不会对其执行结果有影响

ACD由redo log和undo log实现，I由锁实现

## MySQL 隔离级别

### Read uncommitted 读未提交

事务会看见其他未提交事务的执行结果，很少用于实际应用，会发生**脏读**

### Read committed 读已提交

事务只能看见已经提交的事务做出的改变，可以避免脏读，但是不可重复读

### Repeatable read 可重复读

MySQL 默认隔离级别。一个事务中多次读取一个数据行，会看到同样的值

### Serializable 可串行化

强制事务排序，在每个读的数据行上加共享锁。可能导致大量超时和锁竞争

**脏读**：事务 A 读取事务 B**未提交**的更新数据后，B 进行了回滚

**不可重复读**：事务 A 多次读取同一条数据，读取结果不一致。（因为别的事务在多次读取的间隙更新了数据并提交了结果）**侧重修改** 不可重复读是否是个问题取决与这个数据库和业务的特点，有的时候不可重复读是可以接受的

**幻读**：事务对数据多次查询，其他事务此时插入或删除数据行，导致多次读取出现了不一样的结果

|                  | 脏读 | 不可重复读 | 幻读 |
| ---------------- | ---- | ---------- | ---- |
| read uncommitted | ⭕   | ⭕         | ⭕   |
| read committed   | ✅   | ⭕         | ⭕   |
| repeatable read  | ✅   | ✅         | ⭕   |
| serializable     | ✅   | ✅         | ✅   |

## MVCC 多版本并发控制

数据库通过加锁来保证事务的隔离性，但是频繁的加锁会降低数据库性能。所以使用多版本并发控制 MVCC 来实现读取和修改可以同时进行：某一条记录有多个版本同时存在，某个事务对其操作的时候，会查看一条隐藏的**列事务版本 id**，比对事务 id，再根据事务隔离级别去判断读取哪个版本的数据

相对于加锁简单粗暴的方式，它用更好的方式去处理读写冲突，能有效提高数据库并发性能。

### 事务版本号（事务 id）

事务开启前从数据库获得一个自增的事务 id，可以通过比较大小判断事务执行的先后顺序

### 隐式字段

InnoDB 中每一条记录有两个隐藏字段：trx_id，roll_pointer，如果表中没有主键和非 NULL 的唯一键，还会有隐藏的主键列 row_id
| 字段 | 是否必须 | 描述 |
| ------ | ---- | ---------- |
| trx_id | 是 |记录操作该数据的事务的事务 id |
| roll_pointer | 是| 一个指针，指向回滚段的 undo log|
| row_id | 否 | 单调自增|

### undo log 回滚日志
记录数据被修改前的信息。

用于事务回滚的时候保证原子性和一致性；用于MVCC快照读

在表记录被修改前，先把数据拷贝到undo log，如果事务回滚，就用undo log来还原数据。e.g., delete数据，undo log中多一条insert记录，update数据，undo log中多一条反向update记录

### redo log 重做日志
实现D 持久性。在事务提交的时候，先把操作全部写到redo log，待事务的commit操作完成才算整个事务操作完成

redo logo, undo log都是以block 块为单位

### 版本链
多个事务并行操作某一行数据修改，会产生多个版本，通过回滚指针形成一个链表，即版本链：
![chain](../assets/Database/chain.png)
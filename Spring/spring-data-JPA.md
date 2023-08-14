# Spring Data JPA 和 Hibernate
## 非持久化一个字段：
```java
static String transient1; // static

final String transient2 = "transient2"; // final

transient String transient3; // transient

@Transient
String transient4; // @Transient
```

## 实体之间的关联关系注解
### ```@OneToOne```
users --- address, **mandatory**
```java
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
```

```java
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    //...

    @OneToOne(mappedBy = "address")
    private User user;
}
```
employee --- office, **optional**,. some employee may work from home

Use a join table: @JoinTable
```java
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "emp_workstation", 
      joinColumns = 
        { @JoinColumn(name = "employee_id", referencedColumnName = "id") },
      inverseJoinColumns = 
        { @JoinColumn(name = "workstation_id", referencedColumnName = "id") })
    private WorkStation workStation;
}
```

```java
@Entity
@Table(name = "workstation")
public class WorkStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "workStation")
    private Employee employee;

}
```
### ```@OneToMany```
Category --< Post, 一个分类下有许多文章

Category中用mappedBy来指明Post Entity下的Category成员名
```java
    @OneToMany(mappedBy = "category")
    @JsonIgnore  // prevent infinite loop.
    private List<Post> posts;
```

### ```@ManyToOne```
Category --< Post, 一个分类下有许多文章

Post中同时使用 ```@JoinColumn(name = "category_id")```来表示外键的那一列
```java
    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull
    private Category category;
```

### ```@ManyToMany```
Post >-< Tag, 一个Post可以有多个tag

Post下（owner side）
@JoinTable 不是必须的，JPA会自动生成表
```java
    @ManyToMany
    @JoinTable(name = "post_tags", 
        joinColumns = @JoinColumn(name = "post_id"), 
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
```
Tag下（target side）
```java
    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    Set<Post> posts;
```

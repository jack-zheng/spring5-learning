# Spring5 官方手册练习记录

参照 [官方文档 5.3.1](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-introduction) 将里面的练习全都做一遍，深入认识 Spring

## 1. The IoC Container

本章会介绍 Spring 的控制反转(IoC Inversion of Control)容器

### 1.1. Introduce to the Spring IoC Container and Beans

本章会介绍 Spring 框架实现 IoC 的一些准则。IoC 也叫依赖注入(Dependency Injection DI)。它是一个过程。

对象可以通过构造函数的参数，工厂函数参数，对象的 set 方法，工厂的返回方法等方式表明对外部的依赖。然后通过 IoC 的在对象创建的时候将前面描述的那些依赖 set 到对象中去。

This process is fundamentally the inverse (hence the name, Inversion of Control) of the bean itself controlling the instantiation or location of its dependencies by using direct construction of classes or a mechanism such as the Service Locator pattern.

`org.springframework.beans` 和 `org.springframework.context packages` 是 Spring IoC 容器的基础。`BeanFactory` 接口提供了一种先进的配置机制可以使他能够适配任何类型的对象。`ApplicationContext` 是 `BeanFactory` 的一个子接口。它提供了如下功能:

* 以更简单的方式和 AOP 功能做集成
* 消息资源处理（国际化）
* 消息分发
* 应用层的 context 定制，比如针对 web 应用定制的 WebApplicationContext

简而言之，`BeanFactory` 提供了框架层面的配置功能，而 `ApplicationContext` 则更多的是企业级应用方面的功能。`ApplicationContext` 是 `BeanFactory` 的一个超集，在 IoC 容器中会被频繁的提到。如果想要使用 BeanFactory 代替 ApplicationContext 可以参考 BeanFactory 章节。

在 Spring 中，我们把 application 中由 IoC 容器管理并生成出来的对象称为 beans。beans 和他们对应的依赖都是通过 container 的配置实现的。

### 1.2. Container Overview

`org.springframework.context.ApplicationContext` 接口代表了 IoC 容器，负责实例化，配置，转载 beans。容器通过读取配置元数据知道如果配置 beans。配置元数据可以是 xml, 也可以是注解抑或是春 Java 代码。他可以描述应用对象之间的复杂关系。

Spring 支持多种 `ApplicationContext` 接口的实现方式，对独立应用来说，通常可以创建一个 `ClassPathXmlApplicationContext` 或者 `FileSystemXmlApplicationContext`。把 XML 作为配置源文件是一种常见的方式，当然你也可以通过一些 xml 配置来支持注解或者 Java code 的配置方式。

通常情况下，你是不需要写 code 去做 IoC 容器的实例化的。比如，在配置 web 应用的时候，几行 web.xml 文件配置就可以实现。如果你用 Spring Tools for Eclipse 插件，那么你只需要几次点击或者敲几下键盘就能完成配置。

下图显示了 Spring 的工作方式，如果配置得当，当你启动服务器时，配置就自动完成了。

```txt
                                     |                            
                                     | The Business objects(Pojos)
                                     |                            
                                     |                            
                                     |                            
                                     |                            
                                     v                            
                       +---------------------------+              
                       |                           |              
 Configuration metadata|  The Spring Container     |              
  -------------------> |                           |              
                       |                           |              
                       |                           |              
                       |                           |              
                       +---------------------------+              
                                     |                            
                                     | Produces                   
                                     |                            
                                     |                            
                                     |                            
                                     v                            
                       +---------------------------+              
                       |                           |              
                       |  Fully configured system  |              
                       |                           |              
                       |      Ready for use        |              
                       |                           |              
                       |                           |              
                       +---------------------------+              
```

#### 1.2.1. Configuration Metadata

作为开发人员，我们可以通过配置文件告诉应用如何实例化，配置，装载系统中的对象。传统的配置方式是通过 xml，本章接下来就会通过这种方式来介绍 IoC 的一些核心概念。

其他配置方式参见：

* Annotation-based configuration: 参考 Spring 2.5 章内容
* Java-based configuration: Spring 3.0 开始, 很多 Spring 框架的核心功能都通过这种方式实现了，如果你也想用这种方式，参看注解 `@Configuration`, `@Bean`, `@Import` 和 `@DependsOn`。

Spring 配置通常由一个以上的 bean 定义组成，XML 通过定义 <bean/> 节点实现，纯 Java 方式则通过在带有 `@Configuration` 的类中配置 带有 `@Bean` 注解的方法来实现。

这些配置的 beans 和你系统中用到的是一一对应的，通常来说，你只需要定义服务层的对象，比如数据访问层，显示层，基础层(Hibernate)，队列层等的对象即可。更细节的配置对应的层都会自动配置好，当然你也可以通过 Spring 的 AspectJ 集成工具配置一些不在 IoC 容器范围内的 Bean, 参加 'Using AspectJ to dependency-inject domain objects with Spring' 章节。

配置文件模版

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="..." class="...">  
        <!-- collaborators and configuration for this bean go here -->
    </bean>

    <bean id="..." class="...">
        <!-- collaborators and configuration for this bean go here -->
    </bean>

    <!-- more bean definitions go here -->

</beans>
```

* id 是一个字符串，代表了一个 bean
* class 代表类定义，使用类的全路径

#### 1.2.2. Instantiating a Container

`ApplicationContext` 在构造函数中传入配置文件路径来加载外部配置

```java
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
```

如下是一个 service 层模版 (services.xml):

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- services -->

    <bean id="petStore" class="org.springframework.samples.jpetstore.services.PetStoreServiceImpl">
        <property name="accountDao" ref="accountDao"/>
        <property name="itemDao" ref="itemDao"/>
        <!-- additional collaborators and configuration for this bean go here -->
    </bean>

    <!-- more bean definitions for services go here -->

</beans>
```

dao 层模版 (daos.xml):

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="accountDao"
        class="org.springframework.samples.jpetstore.dao.jpa.JpaAccountDao">
        <!-- additional collaborators and configuration for this bean go here -->
    </bean>

    <bean id="itemDao" class="org.springframework.samples.jpetstore.dao.jpa.JpaItemDao">
        <!-- additional collaborators and configuration for this bean go here -->
    </bean>

    <!-- more bean definitions for data access objects go here -->

</beans>
```

上面的示例中，实例化了一个 service 层对象 `PetStoreServiceImpl` 和两个 dao 层对象 JpaAccountDao，JpaItemDao。`property name` 标签和 JavaBean 中的 field 对应，`ref` 标签则指向其他关联的 bean，详细用法参见 Dependencies 章节。

整合配置文件：

在多模块的项目中，可能存在多个 xml 配置文件，我们可以通过 <import/> 标签将他们整合到一个 xml 文件中方便管理：

```xml
<beans>
    <import resource="services.xml"/>
    <import resource="resources/messageSource.xml"/>
    <import resource="/resources/themeSource.xml"/>

    <bean id="bean1" class="..."/>
    <bean id="bean2" class="..."/>
</beans>
```

上面的例子中，我们将 services.xml, messageSource.xml 和 themeSource.xml 通过 <import/> 整合到一起。上面所有的路径都是相对路径，路径中开始的 '/' 会被忽略，推荐写法是不要在开头写 '/'。

PS：路径写法中不推荐用 `..` 指向上一层，这会破坏 Spring 文件分层规范。

The Groovy Bean Definition DSL - Pass

#### 1.2.3. Using the Container

通过 `ApplicationContext` 我们可以进一步配置 beans, 通过调用 T getBean(String name, Class<T> requiredType) 我们可以拿到配置好的 bean。

```java
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");

// retrieve configured instance
PetStoreService service = context.getBean("petStore", PetStoreService.class);

// use configured instance
List<String> userList = service.getUsernameList();
```

如果你是通过 groovy 配置的可以：

```java
ApplicationContext context = new GenericGroovyApplicationContext("services.groovy", "daos.groovy");
```
JavaKotlin

扩展性最高的接口是 `GenericApplicationContext`

```java
GenericApplicationContext context = new GenericApplicationContext();
new XmlBeanDefinitionReader(context).loadBeanDefinitions("services.xml", "daos.xml");
context.refresh();
```

你也可以使用 `GroovyBeanDefinitionReader` 解析 groovy:

```java
GenericApplicationContext context = new GenericApplicationContext();
new GroovyBeanDefinitionReader(context).loadBeanDefinitions("services.groovy", "daos.groovy");
context.refresh();
```

反正就是你能够通过多种途径拿到 context。

#### 练习 1.2

在 xml 中配置一个 bean, 通过 spring 将它读取出来

### 1.3. Bean Overview

Spring IoC 容器通过配置文件管理 beans, 以下以 xml 的 <bean/> 为例。在容器内部，我们管 bean 定义叫 BeanDefinition objects, 他包含了以下内容：

* A package-qualified class name: 一般就是 bean 的实现类.
* Bean behavioral configuration elements, 定义了 bean 在容器中的行为方式 (scope, lifecycle callbacks 等).
* 对其他 bean 的依赖. These references are also called collaborators or dependencies.
* 其他一些创建实例的配置 — for example, the size limit of the pool or the number of connections to use in a bean that manages a connection pool.

这些配置项会转化为一系列的 properties 塞到 bean definition中. 下表展示了这些 properties:

| Property                 | Explained in…​           |
| :----------------------- | :----------------------- |
| Class                    | Instantiating Beans      |
| Name                     | Naming Beans             |
| Scope                    | Bean Scopes              |
| Constructor arguments    | Dependency Injection     |
| Properties               | Dependency Injection     |
| Autowiring mode          | Autowiring Collaborators |
| Lazy initialization mode | Lazy-initialized Beans   |
| Initialization method    | Initialization Callbacks |
| Destruction method       | Destruction Callbacks    |

In addition to bean definitions that contain information on how to create a specific bean, the ApplicationContext implementations also permit the registration of existing objects that are created outside the container (by users). This is done by accessing the ApplicationContext’s BeanFactory through the getBeanFactory() method, which returns the BeanFactory DefaultListableBeanFactory implementation. DefaultListableBeanFactory supports this registration through the registerSingleton(..) and registerBeanDefinition(..) methods. However, typical applications work solely with beans defined through regular bean definition metadata.

上面描述的情况没用到过，不能很贴切的翻译粗来，先留着 （；￣ェ￣）

#### 1.3.1. Naming Beans

每个 bean 可以有一个或者多个标示符，在容器中这个标示符必须是唯一的。如果 bean 需要多个标示符，可以用 aliases(别名) 实现。

在 xml 配置中我们通过 id 或者 name 或者两者都用来指定标示符。id 是唯一的，name 则可以通过 `,`，`;` 或空格 来指定多个。Spring 3.1 以前，id 和 xml 中的 xsd:ID 绑定，会对一写字符有限制，3.1 以后，绑定 xsd:string 就没有这种限制了。

对一个 bean 来说，指定 id, name 并不是必要的，当你没有指定的时候，container 初始化 bean 时会替他随机生成一个唯一的 id。但是如果你在配置中要用到它，那你必须指定他。你只有在使用 inner beans 或者给 自动关联 bean 的功能时，可以不指定。

PS: Bean Naming Conventions: 首字母小写 + 驼峰标示

Aliasing a Bean outside the Bean Definition

通过唯一 id 或多个 name 的方式，我们就有了多种方式指代同一个对象。这种功能在多模块写作的场景下就很有用了，你可以为不同的 module 提供更合理的命名。

只在定义 bean 的地方提供别名功能是不够的，特别是在一些大型的项目中，配置文件往往分布在系统的各个子模块中，你可以通过 <alias/> 给外部引入的 bean 取别名:

```xml
<alias name="fromName" alias="toName"/>
```

通过上面的配置，我们可以将一个叫 fromName 的 bean 改名为 toName。

For example, the configuration metadata for subsystem A may refer to a DataSource by the name of subsystemA-dataSource. The configuration metadata for subsystem B may refer to a DataSource by the name of subsystemB-dataSource. When composing the main application that uses both these subsystems, the main application refers to the DataSource by the name of myApp-dataSource. To have all three names refer to the same object, you can add the following alias definitions to the configuration metadata:

```xml
<alias name="myApp-dataSource" alias="subsystemA-dataSource"/>
<alias name="myApp-dataSource" alias="subsystemB-dataSource"/>
```

Now each component and the main application can refer to the dataSource through a name that is unique and guaranteed not to clash with any other definition (effectively creating a namespace), yet they refer to the same bean.

##### 练习

通过 `,`, `;`, ` ` 给 bean 起别名

#### 1.3.2. Instantiating Beans

Bean 定义可以看作是创建对象的配方，当容器创建对象时，会根据 bean 定义时指定的配置信息创建一个对应的实例。

如果你使用 xml 配置文件，你可以在 <bean/> 元素的 class 属性中指定对象类型。class 属性一般是必填项。class 属性有一下两种使用方式：

1. 典型用法，容器通过反射调用的方式调用对象的构造函数，效果上等同于 new 关键字
2. 容器通过调用工厂方法生成对象

PS: **内部类** 你可以通过一下方式为一个内部静态类配置 bean 定义。在 com.example 包下有名为 SomeThing 的类，在这个类中有一个名为 OtherThing 的内部静态类，那么你可以把对应 class 属性设置为 `com.example.SomeThing$OtherThing` $ 符号起到分隔外部类和内部静态类的作用

##### Instantiation with a Constructor

Spring 可以很好的支持通过构造函数的方式创建对象，bean 无需任何特殊配置，只需要有一个默认的构造函数即可。

Spring IoC 容器可以很好的管理 bean, 即便是一些非典型的用法。大多是 Spring 用户会更倾向于给 JavaBean 提供一个无参构造函数，然后通过 getter/setter 配置容器信息。你也可以在容器中配置一些非典型的 class, 比如老式的 pool 链接信息。

在 xml 配置文件中配置 class 的例子如下：

```xml
<bean id="exampleBean" class="examples.ExampleBean"/>

<bean name="anotherExample" class="examples.ExampleBeanTwo"/>
```

##### Instantiation with a Static Factory Method

当使用静态工厂方法创建对象的时候，你需要把 class 属性指向对应的工厂类，factory-method 属性指向对应的创建方法。这种用法和静态工厂类相类似。

示例如下，bean 定义中 class 配置没有指定返回对象的类型而是指定了对象的工厂方法。`createInstance()` 是工厂中产生对象的方法。

```xml
<bean id="clientService"
    class="examples.ClientService"
    factory-method="createInstance"/>
```

```java
public class ClientService {
    private static ClientService clientService = new ClientService();
    private ClientService() {}

    public static ClientService createInstance() {
        return clientService;
    }
}
```

##### Instantiation by Using an Instance Factory Method

如果想要通过实体工厂类生成对象，做法和静态工厂类似，让 class 属性留空，在 factory-bean 属性里面设置工厂 class 的 bean, 然后在 factory-method 属性里写入需要调用的方法名称。

```xml
<!-- the factory bean, which contains a method called createInstance() -->
<bean id="serviceLocator" class="examples.DefaultServiceLocator">
    <!-- inject any dependencies required by this locator bean -->
</bean>

<!-- the bean to be created via the factory bean -->
<bean id="clientService"
    factory-bean="serviceLocator"
    factory-method="createClientServiceInstance"/>
```

```java
public class DefaultServiceLocator {

    private static ClientService clientService = new ClientServiceImpl();

    public ClientService createClientServiceInstance() {
        return clientService;
    }
}
```

一个工厂类可以有多个工厂方法

```xml
<bean id="serviceLocator" class="examples.DefaultServiceLocator">
    <!-- inject any dependencies required by this locator bean -->
</bean>

<bean id="clientService"
    factory-bean="serviceLocator"
    factory-method="createClientServiceInstance"/>

<bean id="accountService"
    factory-bean="serviceLocator"
    factory-method="createAccountServiceInstance"/>
```

```java
public class DefaultServiceLocator {

    private static ClientService clientService = new ClientServiceImpl();

    private static AccountService accountService = new AccountServiceImpl();

    public ClientService createClientServiceInstance() {
        return clientService;
    }

    public AccountService createAccountServiceInstance() {
        return accountService;
    }
}
```

##### Determining a Bean’s Runtime Type

The runtime type of a specific bean is non-trivial to determine. A specified class in the bean metadata definition is just an initial class reference, potentially combined with a declared factory method or being a FactoryBean class which may lead to a different runtime type of the bean, or not being set at all in case of an instance-level factory method (which is resolved via the specified factory-bean name instead). Additionally, AOP proxying may wrap a bean instance with an interface-based proxy with limited exposure of the target bean’s actual type (just its implemented interfaces).

The recommended way to find out about the actual runtime type of a particular bean is a BeanFactory.getType call for the specified bean name. This takes all of the above cases into account and returns the type of object that a BeanFactory.getBean call is going to return for the same bean name.

##### 练习

1. xml 调用构造函数创建对象
2. xml 调用静态工厂创建对象
3. xml 调用实体工厂创建对象
4. Bean 没有无参构造函数 - 创建失败

### 1.4 Dependencies

总结：这里的 dependencies 讲的其实是 bean 和 bean 之间的关系，比如一个 bean 定义时内部需要另一些 bean 做成员变量，在这种情况下需要怎么处理。

企业级应用都由多个 bean 组成，这个章节我们将想你展示如何让应用中的 bean 协同合作。

#### 1.4.1. Dependency Injection

依赖注入是通过带参构造函数，工厂方法参数，属性 setter 方法等为 bean 设置值的过程。容器会根据前面的定义为 bean 赋值，这种赋值方式达到了反向控制的目的。

通过 DI 这种形式，代码更简洁，对象的解偶程度更高。对象不需要自己管理依赖，写 UT 更简单，测试效率更高。

###### Constructor-based Dependency Injection

基于 构造函数 的 DI 是通过容器调用带参构造函数实现的，与之类似的是带参数的工厂方法，效果上两者等价。下面是一个只能通过构造函数实现注入的例子：

```java
public class SimpleMovieLister {

    // the SimpleMovieLister has a dependency on a MovieFinder
    private MovieFinder movieFinder;

    // a constructor so that the Spring container can inject a MovieFinder
    public SimpleMovieLister(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // business logic that actually uses the injected MovieFinder is omitted...
}
```

##### Constructor Argument Resolution

构造函数参数类型通过参数类型解析。一般情况下容器会根据构造器中参数顺序为他填充对应的值。示例如下：

```java
package x.y;

public class ThingOne {

    public ThingOne(ThingTwo thingTwo, ThingThree thingThree) {
        // ...
    }
}
```

假设 ThingTwo 和 ThingThree 之间没有继承关系，你可以直接使用下面的 xml 配置做初始化，不需要设置 index 或者类型等参数。

```xml
<beans>
    <bean id="beanOne" class="x.y.ThingOne">
        <constructor-arg ref="beanTwo"/>
        <constructor-arg ref="beanThree"/>
    </bean>

    <bean id="beanTwo" class="x.y.ThingTwo"/>

    <bean id="beanThree" class="x.y.ThingThree"/>
</beans>
```

当匹配内容是其他 bean 时，type 是确定的，那么匹配可以正确执行。但当匹配简单类型时，比如 <value>true</value>，Spring 不能确定值的类型，匹配就不能完成了。看下面的例子：

```java
package examples;

public class ExampleBean {

    // Number of years to calculate the Ultimate Answer
    private int years;

    // The Answer to Life, the Universe, and Everything
    private String ultimateAnswer;

    public ExampleBean(int years, String ultimateAnswer) {
        this.years = years;
        this.ultimateAnswer = ultimateAnswer;
    }
}
```

###### Constructor argument type matching

上面的例子中，Spring 可以通过参数类型来做匹配

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg type="int" value="7500000"/>
    <constructor-arg type="java.lang.String" value="42"/>
</bean>
```

###### Constructor argument index

你也可以通过 index 来指定匹配

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg index="0" value="7500000"/>
    <constructor-arg index="1" value="42"/>
</bean>
```

###### Constructor argument name

你还可以通过参数名称来消除歧义

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg name="years" value="7500000"/>
    <constructor-arg name="ultimateAnswer" value="42"/>
</bean>
```

这种用法需要你在编译代码的时候将 debug flag 设置为 true。如果不能的话，你可以用 JDK 的 `@ConstructorProperties` 注释实现同样的功能

```java
package examples;

public class ExampleBean {

    // Fields omitted

    @ConstructorProperties({"years", "ultimateAnswer"})
    public ExampleBean(int years, String ultimateAnswer) {
        this.years = years;
        this.ultimateAnswer = ultimateAnswer;
    }
}
```

##### Setter-based Dependency Injection

基于 Setter 的 DI 注入就是容器在调用无参构造或者静态工厂方法创建对象之后，调用 setter 方法对属性设值。下面是一个只能通过 setter 进行注入的例子：

```java
public class SimpleMovieLister {

    // the SimpleMovieLister has a dependency on the MovieFinder
    private MovieFinder movieFinder;

    // a setter method so that the Spring container can inject a MovieFinder
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // business logic that actually uses the injected MovieFinder is omitted...
}
```

`ApplicationContext` 支持基于构造函数和 setter 的注入，也支持两者混合的注入方式。常见的做法是通过 xml, component 注解 或者在带有 @Configuration 注解的 class 中 添加带 @Bean 注解的方法。

PS: 构造器注入 Vs Setter 注入， 对必要属性，通过构造器式注入实现，如果是一些可选属性，通过 setter 注入

###### Dependency Resolution Process

容器通过一下方式解决依赖问题：

* `ApplicationContext` 在创建是会携带所有 bean 的配置信息，配置信息可以是 xml 形式，也可以是 Java code 或者注解
* 对 bean 来说，他的依赖关系在 properties 配置，构造函数参数或者静态工厂方法中得以体现
* property 和 构造函数参数对应一组值定义或者其他 bean 的引用
* property 和构造函数参数都是由 Spring 从 String type 转化而来的

Spring 容器会在容器创建的时候做配置检测。但是 bean 的属性只有在 bean 真正被创建出来的时候才会被设置。单列模式或者 pre-instantiated 的 bean 会在容器创建的时候一起被创建出来，这些 scope 都是通过 Bean Scopes 定义的。否则，bean 只有在被访问的时候才会创建。创建一个 bean 时会潜在的将他所有关联的 bean 都创建出来。

PS: 循环依赖，这种情况下可以用 setter 注入来绕过

通常来说你可以完全信任 Spring, 他会帮你检测配置问题，比如配置了不存在的 bean 或者 循环依赖什么的，它都能帮你检测出来。Spring 会尽可能晚的设置属性，加载 dependencies, 这就可能在容器启动以后，加载 bean 的时候抛一些 exception，比如属性缺失或者属性不合法等。这就是问什么 Spring 默认会把 bean 都设置成 pre-instantiate, 在创建容器的时候将他们都一并创建好，你虽然会牺牲一些启动时间和内存，但是可以提前发现问题。

如果没有循环依赖问题，那么当一个 bean 被实例化的时候，他所依赖的 bean 也会被实例化，效果上和调用 bean 的构造函数等同。

##### Examples of Dependency Injection

以下是一个基于 xml 的 setter DI 的例子

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <!-- setter injection using the nested ref element -->
    <property name="beanOne">
        <ref bean="anotherExampleBean"/>
    </property>

    <!-- setter injection using the neater ref attribute -->
    <property name="beanTwo" ref="yetAnotherBean"/>
    <property name="integerProperty" value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

```java
public class ExampleBean {

    private AnotherBean beanOne;

    private YetAnotherBean beanTwo;

    private int i;

    public void setBeanOne(AnotherBean beanOne) {
        this.beanOne = beanOne;
    }

    public void setBeanTwo(YetAnotherBean beanTwo) {
        this.beanTwo = beanTwo;
    }

    public void setIntegerProperty(int i) {
        this.i = i;
    }
}
```

上面的例子里，我们通过 setter 来匹配 ExampleBean 的的属性，下面的例子，我们通过构造函数来配置

```xml
<bean id="exampleBean" class="examples.ExampleBean">
    <!-- constructor injection using the nested ref element -->
    <constructor-arg>
        <ref bean="anotherExampleBean"/>
    </constructor-arg>

    <!-- constructor injection using the neater ref attribute -->
    <constructor-arg ref="yetAnotherBean"/>

    <constructor-arg type="int" value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

```java
public class ExampleBean {

    private AnotherBean beanOne;

    private YetAnotherBean beanTwo;

    private int i;

    public ExampleBean(
        AnotherBean anotherBean, YetAnotherBean yetAnotherBean, int i) {
        this.beanOne = anotherBean;
        this.beanTwo = yetAnotherBean;
        this.i = i;
    }
}
```

下面是另一个变种，通过静态工厂方法返回实例

```xml
<bean id="exampleBean" class="examples.ExampleBean" factory-method="createInstance">
    <constructor-arg ref="anotherExampleBean"/>
    <constructor-arg ref="yetAnotherBean"/>
    <constructor-arg value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

```java
public class ExampleBean {

    // a private constructor
    private ExampleBean(...) {
        ...
    }

    // a static factory method; the arguments to this method can be
    // considered the dependencies of the bean that is returned,
    // regardless of how those arguments are actually used.
    public static ExampleBean createInstance (
        AnotherBean anotherBean, YetAnotherBean yetAnotherBean, int i) {

        ExampleBean eb = new ExampleBean (...);
        // some other operations...
        return eb;
    }
}
```

静态工厂方法和构造函数一样，使用的是 `<constructor-arg/>` 的 tag, 返回值和工厂类可以是不同类型。

##### 练习

将上面列举的三种类型的配置方法实践以下
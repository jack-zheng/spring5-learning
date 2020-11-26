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

#### 练习

通过 `,`, `;`, ` ` 给 bean 起别名

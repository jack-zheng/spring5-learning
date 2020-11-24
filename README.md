# Spring5 官方手册练习记录

参照 [官方文档 5.3.1](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-introduction) 将里面的练习全都做一遍，深入认识 Spring

## The IoC Container

本章会介绍 Spring 的控制反转(IoC Inversion of Control)容器

### Introduce to the Spring IoC Container and Beans

本章会介绍 Spring 框架实现 IoC 的一些准则。IoC 也叫依赖注入(Dependency Injection DI)。它是一个过程。

对象可以通过构造函数的参数，工厂函数参数，对象的 set 方法，工厂的返回方法等方式表明对外部的依赖。然后通过 IoC 的在对象创建的时候将前面描述的那些依赖 set 到对象中去。

This process is fundamentally the inverse (hence the name, Inversion of Control) of the bean itself controlling the instantiation or location of its dependencies by using direct construction of classes or a mechanism such as the Service Locator pattern.

`org.springframework.beans` 和 `org.springframework.context packages` 是 Spring IoC 容器的基础。`BeanFactory` `接口提供了一种先进的配置机制可以使他能够适配任何类型的对象。ApplicationContext` 是 `BeanFactory` 的一个子接口。它提供了如下功能:

* 以更简单的方式和 AOP 功能做集成
* 消息资源处理（国际化）
* 消息分发
* 应用层的 context 定制，比如针对 web 应用定制的 WebApplicationContext

简而言之，`BeanFactory` 提供了框架层面的配置功能，而 `ApplicationContext` 则更多的是企业级应用方面的功能。`ApplicationContext` 是 `BeanFactory` 的一个超集，在 IoC 容器中会被频繁的提到。如果想要使用 BeanFactory 代替 ApplicationContext 可以参考 BeanFactory 章节。

在 Spring 中，我们把 application 中由 IoC 容器管理并生成出来的对象称为 beans。beans 和他们对应的依赖都是通过 container 的配置实现的。


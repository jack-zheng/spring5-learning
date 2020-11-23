# Spring5 官方手册练习记录

参照 [官方文档 5.3.1](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-introduction) 将里面的练习全都做一遍，深入认识 Spring

## The IoC Container

本章会介绍 Spring 的控制反转(IoC Inversion of Control)容器

### Introduce to the Spring IoC Container and Beans

本章会介绍 Spring 框架实现 IoC 的一些准则。IoC 也叫依赖注入(Dependency Injection DI)。它是一个过程。

对象可以通过构造函数的参数，工厂函数参数，对象的 set 方法，工厂的返回方法等方式表明对外部的依赖。然后通过 IoC 的在对象创建的时候将前面描述的那些依赖 set 到对象中去。

This process is fundamentally the inverse (hence the name, Inversion of Control) of the bean itself controlling the instantiation or location of its dependencies by using direct construction of classes or a mechanism such as the Service Locator pattern.
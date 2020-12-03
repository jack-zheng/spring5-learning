package c1_3_2.pojo;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class User132Test {
    @Test
    public void test_init_bean_with_default_constructor() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("c1_3_2.xml");

        // retrieve configured instance
        User132 userBean = context.getBean("user132", User132.class);
        assertEquals(userBean.getAge(), 30);
        assertEquals(userBean.getName(), "Jack");
    }

    @Test
    public void test_init_bean_with_static_factory() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("c1_3_2.xml");

        // retrieve configured instance
        User132 userBean = context.getBean("userFromStaticFactory", User132.class);
        assertEquals(userBean.getAge(), 0);
        System.out.println(userBean);
    }

    @Test
    public void test_init_bean_with_isntance_factory() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("c1_3_2.xml");

        // retrieve configured instance
        User132 userBean = context.getBean("userFromInstanceFactory", User132.class);
        assertEquals(userBean.getAge(), 1);
        System.out.println(userBean);
    }

//    @Test
//    public void test_init_bean_with_no_default_constructor() {
//        // create and configure beans
//        ApplicationContext context = new ClassPathXmlApplicationContext("c1_3_2.xml");
//
//        // retrieve configured instance
//        User132 userBean = context.getBean("userNoDefConstructor", User132.class);
//        assertEquals(userBean.getAge(), 2);
//        System.out.println(userBean);
//    }
}
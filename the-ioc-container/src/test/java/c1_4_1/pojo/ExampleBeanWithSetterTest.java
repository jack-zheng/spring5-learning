package c1_4_1.pojo;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExampleBeanWithSetterTest {
    @Test
    public void test_setter_type() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("c1_4_1_setter.xml");

        // retrieve configured instance
        ExampleBeanWithSetter userBean = context.getBean("beanWithSetter", ExampleBeanWithSetter.class);
        assertEquals(userBean.getBeanOne().getName(), "anotherBean");
        assertEquals(userBean.getBeanTwo().getName(), "yetAnotherBean");
        assertEquals(userBean.getIntegerProperty(), 1);
    }

    @Test
    public void test_constructor_type() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("c1_4_1_constructor.xml");

        // retrieve configured instance
        ExampleBeanWithConstructor userBean = context.getBean("beanWithConstructor", ExampleBeanWithConstructor.class);
        assertEquals(userBean.getBeanOne().getName(), "anotherBean");
        assertEquals(userBean.getBeanTwo().getName(), "yetAnotherBean");
        assertEquals(userBean.getIntegerProperty(), 2);
    }

    @Test
    public void test_factory_type() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("c1_4_1_factory.xml");

        // retrieve configured instance
        ExampleBeanWithFactoryMethod userBean = context.getBean("beanWithFactory", ExampleBeanWithFactoryMethod.class);
        assertEquals(userBean.getBeanOne().getName(), "anotherBean");
        assertEquals(userBean.getBeanTwo().getName(), "yetAnotherBean");
        assertEquals(userBean.getIntegerProperty(), 3);
    }
}
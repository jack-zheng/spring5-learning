package c1_4_2.pojo;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class OuterBeanTest {
    @Test
    public void outer_static() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("1_4_2/innerBeans.xml");

        // retrieve configured instance
        OuterBeanWithStatic bean = context.getBean("outer", OuterBeanWithStatic.class);
        System.out.println(bean);
    }

    @Test
    public void outer_non_static() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("1_4_2/innerBeans.xml");

        // retrieve configured instance
        OuterBeanWithNonStatic bean = context.getBean("outer2", OuterBeanWithNonStatic.class);
        System.out.println(bean);
    }

    @Test
    public void init() {
        OuterBeanWithStatic.InnerBean innerBean = new OuterBeanWithStatic.InnerBean();
    }
}
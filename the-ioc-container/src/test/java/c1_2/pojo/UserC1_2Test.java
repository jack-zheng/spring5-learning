package c1_2.pojo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class UserC1_2Test {
    @Test
    public void test_init_bean() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("c1_2.xml");

        // retrieve configured instance
        UserC1_2 userBean = context.getBean("userC1_2", UserC1_2.class);
        Assertions.assertEquals(userBean.getAge(), 30);
        Assertions.assertEquals(userBean.getName(), "Jack");
    }

    @Test
    public void test_bean_name_aliases() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("c1_2.xml");

        // retrieve configured instance
        UserC1_2 userBean1 = context.getBean("cTest1_2", UserC1_2.class);
        Assertions.assertEquals(userBean1.getName(), "cTest1");

        UserC1_2 userBean2 = context.getBean("cTest2_2", UserC1_2.class);
        Assertions.assertEquals(userBean2.getName(), "cTest2");

        UserC1_2 userBean3 = context.getBean("cTest3_2", UserC1_2.class);
        Assertions.assertEquals(userBean3.getName(), "cTest3");
    }
}
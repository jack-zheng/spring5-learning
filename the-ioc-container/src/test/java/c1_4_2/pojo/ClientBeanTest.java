package c1_4_2.pojo;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class ClientBeanTest {
    @Test
    public void init_client() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("1_4_2/idref.xml");

        // retrieve configured instance
        ClientBean bean = context.getBean("client", ClientBean.class);
        System.out.println(bean.getMyTarget());
        // assertEquals(bean.getMyTarget().getId(), "123");
    }

    @Test
    public void init_client2() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("1_4_2/idref.xml");

        // retrieve configured instance
        ClientBean bean = context.getBean("client02", ClientBean.class);
        System.out.println(bean.getMyTarget());
        // assertEquals(bean.getMyTarget().getId(), "123");
    }

    @Test
    public void init_target() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("1_4_2/idref.xml");

        // retrieve configured instance
        TargetBean bean = context.getBean("target", TargetBean.class);
        assertEquals(bean.getAge(), "11");
    }
}
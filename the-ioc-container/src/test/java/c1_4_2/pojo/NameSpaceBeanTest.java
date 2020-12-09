package c1_4_2.pojo;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class NameSpaceBeanTest {
    @Test
    public void init_p_namespace() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("1_4_2/namespace.xml");

        // retrieve configured instance
        NameSpaceBean bean = context.getBean("pSpaceBean", NameSpaceBean.class);
        System.out.println(bean);

        NameSpaceBean bean2 = context.getBean("pSpaceBean02", NameSpaceBean.class);
        System.out.println(bean2);
    }

    @Test
    public void init_c_namespace() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("1_4_2/namespace.xml");

        // retrieve configured instance
        NameSpaceBean bean = context.getBean("cSpaceBean", NameSpaceBean.class);
        System.out.println(bean);
    }

    @Test
    public void test_compound_prorety() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("1_4_2/namespace.xml");

        // retrieve configured instance
        NameSpaceBean bean = context.getBean("compoundBean", NameSpaceBean.class);
        System.out.println(bean);
    }
}
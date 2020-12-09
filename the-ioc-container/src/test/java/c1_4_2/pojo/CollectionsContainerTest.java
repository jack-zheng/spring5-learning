package c1_4_2.pojo;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class CollectionsContainerTest {
    @Test
    public void test_init() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("1_4_2/collections.xml");

        // retrieve configured instance
        CollectionsContainer bean = context.getBean("collectionsContainer", CollectionsContainer.class);
        System.out.println(bean);

        assertEquals(bean.getTargetBeans().size(), 1);
        System.out.println(bean.getTargetBeans());
    }

    @Test
    public void test_merging() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("1_4_2/collectionMerging.xml");

        // retrieve configured instance
        CollectionsContainer bean = context.getBean("child", CollectionsContainer.class);
        assertEquals(bean.getAdminEmails().size(), 3);
        System.out.println(bean.getAdminEmails());
    }

    @Test
    public void test_emp_null() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("1_4_2/empStringAndNull.xml");

        // retrieve configured instance
        CollectionsContainer bean = context.getBean("empStrContainer", CollectionsContainer.class);
        assertEquals(bean.getTmpName(), "");

        CollectionsContainer bean2 = context.getBean("nullContainer", CollectionsContainer.class);
        assertNull(bean2.getTmpName());
    }
}
package c1_4_2.pojo;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertiesContainerTest {
    @Test
    public void test() {
        PropertiesContainer container = new PropertiesContainer();
        Properties properties = new Properties();
        properties.setProperty("name", "jack");
        container.setProperties(properties);
        System.out.println(container);
    }

    @Test
    public void test_properties() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("1_4_2/stringValues.xml");

        // retrieve configured instance
        PropertiesContainer userBean = context.getBean("myContainer", PropertiesContainer.class);
        assertEquals(userBean.getProperties().size(), 2);
        // jdbc.driver.className=com.mysql.jdbc.Driver
        // jdbc.url=jdbc:mysql://localhost:3306/mydb
        assertEquals(userBean.getProperties().get("jdbc.driver.className"), "com.mysql.jdbc.Driver");
        assertEquals(userBean.getProperties().get("jdbc.url"), "jdbc:mysql://localhost:3306/mydb");
    }
}
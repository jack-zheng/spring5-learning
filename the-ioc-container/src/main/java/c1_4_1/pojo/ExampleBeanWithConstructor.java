package c1_4_1.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ExampleBeanWithConstructor {

    private AnotherBean beanOne;

    private YetAnotherBean beanTwo;

    private int integerProperty;
}

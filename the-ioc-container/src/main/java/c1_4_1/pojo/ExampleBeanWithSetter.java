package c1_4_1.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExampleBeanWithSetter {

    private AnotherBean beanOne;

    private YetAnotherBean beanTwo;

    private int integerProperty;
}

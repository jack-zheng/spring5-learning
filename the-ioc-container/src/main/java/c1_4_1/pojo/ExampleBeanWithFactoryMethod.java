package c1_4_1.pojo;

public class ExampleBeanWithFactoryMethod {

    private AnotherBean beanOne;

    private YetAnotherBean beanTwo;

    private int integerProperty;

    private ExampleBeanWithFactoryMethod(AnotherBean beanOne, YetAnotherBean beanTwo, int integerProperty) {
        this.beanOne = beanOne;
        this.beanTwo = beanTwo;
        this.integerProperty = integerProperty;
    }

    public AnotherBean getBeanOne() {
        return beanOne;
    }

    public void setBeanOne(AnotherBean beanOne) {
        this.beanOne = beanOne;
    }

    public YetAnotherBean getBeanTwo() {
        return beanTwo;
    }

    public void setBeanTwo(YetAnotherBean beanTwo) {
        this.beanTwo = beanTwo;
    }

    public int getIntegerProperty() {
        return integerProperty;
    }

    public void setIntegerProperty(int integerProperty) {
        this.integerProperty = integerProperty;
    }

    public static ExampleBeanWithFactoryMethod createInstance(AnotherBean beanOne, YetAnotherBean beanTwo, int integerProperty) {
        return new ExampleBeanWithFactoryMethod(beanOne, beanTwo, integerProperty);
    }
}

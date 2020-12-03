package c1_3_2.utils;

import c1_3_2.pojo.User132;

public class InstanceUserFactory {
    public User132 createInstance() {
        User132 tmp = new User132();
        tmp.setName("TmpInstance" + System.currentTimeMillis());
        tmp.setAge(1);
        return tmp;
    }
}

package c1_3_2.utils;

import c1_3_2.pojo.User132;

public class StaticUserFactory {
    public static User132 createInstance(){
        User132 tmp = new User132();
        tmp.setName("TmpStatic" + System.currentTimeMillis());
        tmp.setAge(0);
        return tmp;
    }
}

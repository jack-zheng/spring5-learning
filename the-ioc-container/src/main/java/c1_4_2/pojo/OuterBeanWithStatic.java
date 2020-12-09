package c1_4_2.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OuterBeanWithStatic {
    private String name;

    private InnerBean innerBean;

    static class InnerBean {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "InnerBean{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}



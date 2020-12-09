package c1_4_2.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OuterBeanWithNonStatic {
    private String name;

    private InnerBean innerBean;

    class InnerBean {
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



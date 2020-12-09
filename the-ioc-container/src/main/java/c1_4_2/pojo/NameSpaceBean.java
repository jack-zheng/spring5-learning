package c1_4_2.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NameSpaceBean {
    private String name;
    private TargetBean targetBean;

    public NameSpaceBean(String name, TargetBean targetBean) {
        this.name = name;
        this.targetBean = targetBean;
    }
}

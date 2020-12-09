package c1_4_2.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionsContainer {
    private Properties adminEmails;
    private List someList;
    private Map someMap;
    private Set someSet;
    private List<TargetBean> targetBeans;
    private String tmpName;
}

package rh.study.knowledge.common.result;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by 18121254 on 2019/11/8.
 */
@Getter
@Setter
public class Column {
    private String title;
    private String dataIndex;
    // 固定列，值有 left，right
    private String fixed;
    private Integer width;
}

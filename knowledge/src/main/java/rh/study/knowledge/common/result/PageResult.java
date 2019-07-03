package rh.study.knowledge.common.result;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用分页查询返回类型
 */
@Getter
@Setter
public class PageResult {
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 总页数
     */
    private Integer pages;
    /**
     * 当前页
     */
    private Integer current;
    /**
     * 每页记录数
     */
    private Integer pageSize;
    /**
     * 当前页数据
     */
    private Object rows;

    /**
     *
     * @param total    总记录数
     * @param pages     总页数
     * @param rows      数据
     */
    public PageResult(Long total, Integer pages, Object rows) {
        this.rows = rows;
        this.total = total;
        this.pages = pages;
    }

    /**
     *
     * @param total    总记录数
     * @param pages     总页数
     * @param rows      数据
     * @param current    当前页
     * @param pageSize  每页页记录数
     */
    public PageResult(Long total, Integer pages, Object rows, Integer current, Integer pageSize) {
        this.total = total;
        this.pages = pages;
        this.rows = rows;
        this.current = current;
        this.pageSize = pageSize;
    }

}

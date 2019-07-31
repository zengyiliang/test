package com.xhg.ops.workflow.model;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.ToString;

/**
 * 分页参数
 */
@Data
@ToString
public class PageParam  {
    private Integer pageNo = 1; //当前页
    private Integer pageSize = 10; //每页大小

    private Integer startIndex = (pageNo-1)*this.pageSize;

    //获取分页的开始下标
    public Integer getStartIndex(){
        return (pageNo-1)*this.pageSize;
    }

}

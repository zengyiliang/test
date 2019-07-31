package com.xhg.ops.workorders.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;


@ApiModel(value = "web工作台统计")
@Data
public class WebWorkOrderPlatformStaticsVo {

    List<WebWorkOrderPlatformMainStateVo> list;
    
}

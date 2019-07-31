package com.xhg.ops.workorders.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;


@ApiModel(value = "web工作台主状态")
@Data
public class WebWorkOrderPlatformMainStateVo {

    String mainState;
    
    List<WebWorkOrderPlatformSubStateVo> list;
    
}

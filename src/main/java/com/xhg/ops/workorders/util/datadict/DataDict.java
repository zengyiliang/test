package com.xhg.ops.workorders.util.datadict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 数据字典对象
 * @author 刘涛
 * @date 2018年7月23日
 */
@Data
@ToString
@ApiModel(value = "数据字典key-value")
public class DataDict {

	@ApiModelProperty(value = "标识")
	private String code;

	@ApiModelProperty(value = "名称")
	private String name;

}

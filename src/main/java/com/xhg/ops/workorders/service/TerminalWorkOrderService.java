package com.xhg.ops.workorders.service;

import java.util.List;
import java.util.Map;

import com.xhg.core.web.vo.RsBody;
import com.xhg.ops.workorders.dto.TerminalWorkOrderSubmitDTO;

/**
 * 终端工单接口
 * @author 区涛
 * @date 2018年7月24日
 */
public interface TerminalWorkOrderService {

	/**
	 * 批量上报故障工单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> batchSubmitSiteFault(List<TerminalWorkOrderSubmitDTO> dtoList);
	
	/**
	 * 单个上报故障工单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> submitSiteFault(TerminalWorkOrderSubmitDTO dto);

	
}

package com.xhg.ops.workflow.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xhg.core.util.http.SimpleHttpClient;
import com.xhg.core.web.vo.RequestBean;
import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RqBody;
import com.xhg.core.web.vo.RqHead;
import com.xhg.ops.workorders.dto.TerminalWorkOrderSubmitDTO;

/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)*/
public class HttpTest {

	public static void main(String[] args) throws Exception {
		String url = "http://10.10.10.103:9089/orders/ops/terminal/submitSiteFault";
		Map<String, Object> params = new HashMap<>();
		params.put("siteCode", "441900001");
		
		TerminalWorkOrderSubmitDTO dto = new TerminalWorkOrderSubmitDTO();
		dto.setAreaCode("000");
		dto.setBoxType("2");
		dto.setBoxTypeName("纸类");
		dto.setDescription("上位机错位");
		dto.setDeviceAddress("杭州市天河区");
		dto.setDeviceId("12345");
		dto.setFaultId(3);
		dto.setFaultType("2");
		dto.setLatitude("100");
		dto.setLongitude("200");
		dto.setSiteCode("5689");
		
		//封装请求数据
		RequestBean<TerminalWorkOrderSubmitDTO> requestBean = new RequestBean<>();
		RqBody<TerminalWorkOrderSubmitDTO> requestBody = new RqBody<>();
		requestBody.setData(dto);
		requestBean.setRequestBody(requestBody);
		RqHead rqHead = new RqHead();
		rqHead.setAppId("string");
		rqHead.setAppVersion("0.01");
		rqHead.setConfigVersion("configVersion");
		rqHead.setSystemVersion("0.01");
		rqHead.setDeviceId("string");
		rqHead.setChannel("system");
		rqHead.setOstype("system");
		rqHead.setSign("string");
		rqHead.setValidateTime(String.valueOf(new Date().getTime()));
		requestBean.setRequestHead(rqHead);
		//请求数据
		System.out.println(JSON.toJSONString(requestBean));
		//发送请求
		SimpleHttpClient httpClient = SimpleHttpClient.newInstance();
		SimpleHttpClient httpPost = httpClient.httpPost("application/json; charset=utf-8", url, JSON.toJSONString(requestBean));
		String httpResponse = httpPost.execute();
		@SuppressWarnings("unchecked")
		ResponseBean<Map<String, Object>> responseBean = JSONObject.parseObject(httpResponse, ResponseBean.class);
		System.out.println(responseBean);
	}
}

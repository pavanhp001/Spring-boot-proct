package com.AL.ui.builder;

import static com.AL.ui.constants.Constants.*;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.AL.ui.vo.OrderSubmitVO;
import com.AL.util.DateUtil;
import com.AL.xml.cm.v4.PaymentEventType;

public enum OrderSubmitVOBuilder {
	
	INSTANCE;
	
	public OrderSubmitVO buildOrderSubmitVO(Map<String, String> requestParamMap, 
			Map<String, PaymentEventType> paymentEvents, OrderSubmitVO submitVO) {
		if(submitVO == null) {
			submitVO = new OrderSubmitVO();
		}
		//Collect all payment events
		if(paymentEvents != null) {
			for(String str : requestParamMap.values()) {
				if(paymentEvents.containsKey(str)) {
					submitVO.getPaymentEvents().add(paymentEvents.get(str));
				}
			}
		}
		if(!StringUtils.isEmpty(requestParamMap.get(INSTALL_DATE))) {
			submitVO.setInstallDate(DateUtil.asXMLGregorianCalendar
					(requestParamMap.get(INSTALL_DATE), INSTALL_DATE_FORMAT));
		}
		
		String strInstallTime = requestParamMap.get(INSTALL_TIME);
		if(!StringUtils.isEmpty(strInstallTime)) {
			String[] tokens = strInstallTime.split(INSTALL_TIME_TOKEN);
			submitVO.setStartTime(DateUtil.asXMLGregorianCalendar
					(tokens[0], INSTALL_TIME_FORMAT));
			submitVO.setEndTime(DateUtil.asXMLGregorianCalendar
					(tokens[1], INSTALL_TIME_FORMAT));
		}
		
		return submitVO;
	}

}

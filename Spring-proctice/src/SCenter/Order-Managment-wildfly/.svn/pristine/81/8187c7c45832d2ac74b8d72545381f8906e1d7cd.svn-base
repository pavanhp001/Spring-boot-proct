package com.AL.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.ome.mapping.ATTOrderMappingUtil;
import com.AL.ome.mapping.CenturylinkOrderMappingUtil;
import com.AL.ome.mapping.ComcastOrderMappingUtil;
import com.AL.ome.mapping.DishOrderMappingUtil;
import com.AL.ome.mapping.G2BComcastOrderMappingUtil;
import com.AL.ome.mapping.OrderMappingUtil;
import com.AL.ome.mapping.ProviderMappingEnum;
import com.AL.op.util.RTIMProviderUtil;
import com.AL.service.VOrderMappingService;
import com.AL.V.beans.entity.VOrderMapping;
import com.AL.Vdao.dao.VOrderMappingDao;
import com.AL.Vdao.util.ProviderConfigVO;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.ProductType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.StatusType;

@Component
public class VOrderMappingServiceImpl implements VOrderMappingService {

    private static final Logger logger = Logger.getLogger(VOrderMappingServiceImpl.class);

    private static final String ORDER_EXT_ID_KEY = "OrderExtId";
    private static final String PROVIDER_ID_KEY = "ProviderId";
    private static final String V_ORDER_NO_KEY = "VOrderNo";
    private static final String LI_EXT_ID_KEY = "LineItemExtId";
    private static final String CONTEXT_KEY = "Context";

    @Autowired
    private VOrderMappingDao orderMappingDao;

    public VOrderMapping processOrderMapping(String response) {
	if (response != null && response.trim().length() > 0) {
	    try {
		OrderManagementRequestResponseDocument res = OrderManagementRequestResponseDocument.Factory.parse(response);
		return saveOrderMapping(res);
	    }
	    catch (Exception e) {
		logger.warn("Ignoring processing response as it is not valid req res document");
	    }
	}
	return null;
    }

    /**
     * Method to save V order mappings for external processes(ATTInbound, DishInbound, Comcast Inbound etc)
     *
     * @param orderDoc
     */
    public VOrderMapping saveOrderMapping(OrderManagementRequestResponseDocument orderDoc) {
	VOrderMapping mappingBean = null;
	if (orderDoc != null && orderDoc.getOrderManagementRequestResponse().getStatus() != null) {
	    logger.debug("Processing OrderSubmit response for rtim order mapping");
	    Boolean isValidResponse = validateResponse(orderDoc.getOrderManagementRequestResponse().getStatus());
	    if (isValidResponse) {
		Response response = orderDoc.getOrderManagementRequestResponse().getResponse();
		Map<String, VOrderMapping> mappings = prepareOrderMapping(response);

		Set<String> mappingKeys = mappings.keySet();
		for (String key : mappingKeys) {
		    // mappingBean = populateMappingBean(mappings);
		    mappingBean = mappings.get(key);
		    if (mappingBean != null && mappingBean.getVOrderNo() != null && mappingBean.getVOrderNo().trim().length() > 0) {
			logger.info("Processing mapping bean for key : " + key);
			// Check if mapping already exist for same V order no.
			VOrderMapping extMapping = orderMappingDao.findByVOrderNoAndOrderExtIdAndLIExtId(mappingBean.getVOrderNo(), mappingBean.getOrderExtId(),
				mappingBean.getLiExtId());
			if (extMapping == null) {
			    orderMappingDao.save(mappingBean);
			    orderMappingDao.updateProviderNumber(mappingBean);
			}

		    }
		    else {
			logger.debug("Skipped mapping as order does not contain order mapping data : " + mappingBean.toString());
		    }
		}
	    }
	}
	else {
	    logger.warn("Ignoring OrderSubmit response for RTIM order mapping as status message is missing !!! ");
	}
	return mappingBean;
    }

    private Map<String, VOrderMapping> prepareOrderMapping(Response response) {

	logger.debug("Preparing order mapping");
	Map<String, VOrderMapping> mappings = new HashMap<String, VOrderMapping>();

	Boolean isHarmonyOrder = verifyOrderSource(response);

	String orderExtId = retrieveOrderExtId(response);

	if (!orderExtId.isEmpty()) {
	    if (response != null && response.getOrderInfoList() != null) {
		OrderType orderType = response.getOrderInfoList().get(0);
		if (orderType.getLineItems() != null && orderType.getLineItems().getLineItemList() != null) {
		    List<LineItemType> liTypeList = orderType.getLineItems().getLineItemList();
		    for (LineItemType liType : liTypeList) {
			if (liType.getLineItemDetail() != null && liType.getLineItemDetail().getDetail() != null
				&& liType.getLineItemDetail().getDetailType().toString().equalsIgnoreCase("product")) {
			    ProductType prodType = liType.getLineItemDetail().getDetail().getProductLineItem();
			    if (prodType.getProvider() != null) {
				String providerId = prodType.getProvider().getExternalId();
				if (providerId.equalsIgnoreCase(ProviderMappingEnum.ATTSTI.getValue())) {
				    logger.debug("Processing ATT-STI lineitem response for order mapping");
				    VOrderMapping mappingBean = new VOrderMapping();
				    String rtimOrderNo = ATTOrderMappingUtil.retrieveVOrderNo(isHarmonyOrder, liType);
				    if (rtimOrderNo != null && rtimOrderNo.trim().length() > 0) {
					mappingBean.setOrderExtId(Long.valueOf(orderExtId != null ? orderExtId.trim() : "-1"));
					mappingBean.setProviderExtId(providerId);
					mappingBean.setLiExtId(liType.getExternalId());
					mappingBean.setContext("ATT-STI");
					mappingBean.setVOrderNo(rtimOrderNo);
					mappings.put("ATT-" + liType.getExternalId(), mappingBean);
				    }
				}
				else if (providerId.equalsIgnoreCase(ProviderMappingEnum.DISH_HARMONY.getValue())
					|| providerId.equalsIgnoreCase(ProviderMappingEnum.DISH_SYP.getValue())) {
				    logger.debug("Processing Dish lineitem response for order mapping");
				    VOrderMapping mappingBean = new VOrderMapping();

				    String rtimOrderNo = DishOrderMappingUtil.retrieveVOrderNo(isHarmonyOrder, liType);
				    if (rtimOrderNo != null && rtimOrderNo.trim().length() > 0) {
					mappingBean.setOrderExtId(Long.valueOf(orderExtId != null ? orderExtId.trim() : "-1"));
					mappingBean.setProviderExtId(providerId);
					mappingBean.setLiExtId(liType.getExternalId());
					mappingBean.setContext("DISH");
					mappingBean.setVOrderNo(rtimOrderNo);
					mappings.put("DISH-" + liType.getExternalId(), mappingBean);
				    }
				}
				else if (providerId.equalsIgnoreCase(ProviderMappingEnum.G2BCOMCAST.getValue())) {
				    logger.debug("Processing G2B Comcast lineitem response for order mapping");
				    VOrderMapping mappingBean = new VOrderMapping();
				    String rtimOrderNo = G2BComcastOrderMappingUtil.retrieveVOrderNo(isHarmonyOrder, liType);
				    if (rtimOrderNo != null && rtimOrderNo.trim().length() > 0) {
					mappingBean.setOrderExtId(Long.valueOf(orderExtId != null ? orderExtId.trim() : "-1"));
					mappingBean.setProviderExtId(providerId);
					mappingBean.setLiExtId(liType.getExternalId());
					mappingBean.setContext("G2B");
					mappingBean.setVOrderNo(rtimOrderNo);
					mappings.put("G2B-" + liType.getExternalId(), mappingBean);
				    }
				}
				else if (providerId.equalsIgnoreCase(ProviderMappingEnum.COMCAST.getValue())) {
				    logger.debug("Processing Comcast lineitem response for order mapping");
				    VOrderMapping mappingBean = new VOrderMapping();
				    String rtimOrderNo = ComcastOrderMappingUtil.retrieveVOrderNo(false, liType);
				    if (rtimOrderNo != null && rtimOrderNo.trim().length() > 0) {
					mappingBean.setOrderExtId(Long.valueOf(orderExtId != null ? orderExtId.trim() : "-1"));
					mappingBean.setProviderExtId(providerId);
					mappingBean.setLiExtId(liType.getExternalId());
					mappingBean.setContext("Comcast");
					mappingBean.setVOrderNo(rtimOrderNo);
					mappings.put("COMCAST-" + liType.getExternalId(), mappingBean);
				    }
				}
				else if (providerId.equalsIgnoreCase(ProviderMappingEnum.COMCASTSWIVEL.getValue())) {
				    logger.debug("Processing Comcast-Swivel lineitem response for order mapping");
				    VOrderMapping mappingBean = new VOrderMapping();
				    String rtimOrderNo = ComcastOrderMappingUtil.retrieveVOrderNo(true, liType);
				    if (rtimOrderNo != null && rtimOrderNo.trim().length() > 0) {
					mappingBean.setOrderExtId(Long.valueOf(orderExtId != null ? orderExtId.trim() : "-1"));
					mappingBean.setProviderExtId(providerId);
					mappingBean.setLiExtId(liType.getExternalId());
					mappingBean.setContext("COMCAST-SWIVEL");
					mappingBean.setVOrderNo(rtimOrderNo);
					mappings.put("COMCAST-SWIVEL-" + liType.getExternalId(), mappingBean);
				    }
				}else if (providerId.equalsIgnoreCase(ProviderMappingEnum.VERIZON.getValue())) {
				    logger.debug("Processing Verizon lineitem response for order mapping");
				    VOrderMapping mappingBean = new VOrderMapping();
				    String rtimOrderNo = ComcastOrderMappingUtil.retrieveVOrderNo(true, liType);
				    if (rtimOrderNo != null && rtimOrderNo.trim().length() > 0) {
					mappingBean.setOrderExtId(Long.valueOf(orderExtId != null ? orderExtId.trim() : "-1"));
					mappingBean.setProviderExtId(providerId);
					mappingBean.setLiExtId(liType.getExternalId());
					mappingBean.setContext("VERIZON");
					mappingBean.setVOrderNo(rtimOrderNo);
					mappings.put("VERIZON-" + liType.getExternalId(), mappingBean);
				    }
				}else if (providerId.equalsIgnoreCase(ProviderMappingEnum.CENTURYLINK.getValue())) {
				    logger.debug("Processing CenturyLink lineitem response for order mapping");
				    VOrderMapping mappingBean = new VOrderMapping();
				    String rtimOrderNo = CenturylinkOrderMappingUtil.retrieveVOrderNo(liType);
				    if (rtimOrderNo != null && rtimOrderNo.trim().length() > 0) {
					mappingBean.setOrderExtId(Long.valueOf(orderExtId != null ? orderExtId.trim() : "-1"));
					mappingBean.setProviderExtId(providerId);
					mappingBean.setLiExtId(liType.getExternalId());
					mappingBean.setContext("CENTURYLINK");
					mappingBean.setVOrderNo(rtimOrderNo);
					mappings.put("CENTURYLINK-" + liType.getExternalId(), mappingBean);
				    }
				}else if (providerId.equalsIgnoreCase(ProviderMappingEnum.FRONTIER.getValue())) {
				    logger.debug("Processing FRONTIER lineitem response for order mapping");
				    VOrderMapping mappingBean = new VOrderMapping();
				    String rtimOrderNo = OrderMappingUtil.retrieveVOrderNo(ProviderMappingEnum.FRONTIER.toString(),liType);
				    if (rtimOrderNo != null && rtimOrderNo.trim().length() > 0) {
						mappingBean.setOrderExtId(Long.valueOf(orderExtId != null ? orderExtId.trim() : "-1"));
						mappingBean.setProviderExtId(providerId);
						mappingBean.setLiExtId(liType.getExternalId());
						mappingBean.setContext("FRONTIER");
						mappingBean.setVOrderNo(rtimOrderNo);
						mappings.put("FRONTIER-" + liType.getExternalId(), mappingBean);
				    }
				}
				else if (providerId.equalsIgnoreCase(ProviderMappingEnum.MONITORONICS.getValue())) {
				    logger.debug("Processing MONITORONICS lineitem response for order mapping");
				    VOrderMapping mappingBean = new VOrderMapping();
				    String rtimOrderNo = OrderMappingUtil.retrieveVOrderNo(ProviderMappingEnum.MONITORONICS.toString(),liType);
				    if (rtimOrderNo != null && rtimOrderNo.trim().length() > 0) {
						mappingBean.setOrderExtId(Long.valueOf(orderExtId != null ? orderExtId.trim() : "-1"));
						mappingBean.setProviderExtId(providerId);
						mappingBean.setLiExtId(liType.getExternalId());
						mappingBean.setContext("MONITORONICS");
						mappingBean.setVOrderNo(rtimOrderNo);
						mappings.put("MONITORONICS-" + liType.getExternalId(), mappingBean);
				    }
				}
				else if (providerId.equalsIgnoreCase(ProviderMappingEnum.ADT.getValue())) {
				    logger.debug("Processing ADT lineitem response for order mapping");
				    VOrderMapping mappingBean = new VOrderMapping();
				    String rtimOrderNo = OrderMappingUtil.retrieveVOrderNo(ProviderMappingEnum.ADT.toString(),liType);
				    if (rtimOrderNo != null && rtimOrderNo.trim().length() > 0) {
						mappingBean.setOrderExtId(Long.valueOf(orderExtId != null ? orderExtId.trim() : "-1"));
						mappingBean.setProviderExtId(providerId);
						mappingBean.setLiExtId(liType.getExternalId());
						mappingBean.setContext("ADT");
						mappingBean.setVOrderNo(rtimOrderNo);
						mappings.put("ADT-" + liType.getExternalId(), mappingBean);
				    }
				}
				else if (providerId.equalsIgnoreCase(ProviderMappingEnum.VERIZON_COFEE.getValue())) {
				    logger.debug("Processing VERIZON_COFEE lineitem response for order mapping");
				    String rtimOrderNo = OrderMappingUtil.retrieveVOrderNo(ProviderMappingEnum.VERIZON_COFEE.toString(),liType);
				    if (rtimOrderNo != null && rtimOrderNo.trim().length() > 0) {
				    	VOrderMapping mappingBean = new VOrderMapping();
				    	mappingBean.setOrderExtId(Long.valueOf(orderExtId != null ? orderExtId.trim() : "-1"));
				    	mappingBean.setProviderExtId(providerId);
				    	mappingBean.setLiExtId(liType.getExternalId());
				    	mappingBean.setContext("VERIZON_COFEE");
				    	mappingBean.setVOrderNo(rtimOrderNo);
				    	mappings.put("VERIZON_COFEE-" + liType.getExternalId(), mappingBean);
				    }
				}else{
					ProviderConfigVO providerConfig = RTIMProviderUtil.getRTIMProviderConfig(providerId);
					if (providerConfig != null) {
						logger.info("Processing "+providerConfig.getProviderName()+" lineitem response for order mapping");
						String rtimOrderNo = OrderMappingUtil.retrieveVOrderNo(providerId,liType);
						if (rtimOrderNo != null && rtimOrderNo.trim().length() > 0) {
							VOrderMapping mappingBean = new VOrderMapping();
							mappingBean.setOrderExtId(Long.valueOf(orderExtId != null ? orderExtId.trim() : "-1"));
							mappingBean.setProviderExtId(providerId);
							mappingBean.setLiExtId(liType.getExternalId());
							mappingBean.setContext(providerConfig.getProviderName());
							mappingBean.setVOrderNo(rtimOrderNo);
							mappings.put(providerConfig.getProviderName()+"-" + liType.getExternalId(), mappingBean);
						}
					}
				}
			    }
			}
		    }
		}
	    }
	}

	return mappings;
    }

    /**
     * Verify order source is accord or not.
     *
     * @param response
     * @return
     */
    private Boolean verifyOrderSource(Response response) {
	Boolean isHarmonyOrd = Boolean.FALSE;

	if (response.getSalesContext() != null) {
	    SalesContextType scType = response.getSalesContext();
	    List<SalesContextEntityType> entityList = scType.getEntityList();
	    for (SalesContextEntityType entityType : entityList) {
		if (entityType.getName().equalsIgnoreCase("HARMONY")) {
		    List<NameValuePairType> nvPairList = entityType.getAttributeList();
		    for (NameValuePairType nvType : nvPairList) {
			if (nvType.getName().equalsIgnoreCase("SOURCE") && nvType.getValue().equalsIgnoreCase("ACCORD")) {
			    isHarmonyOrd = Boolean.TRUE;
			}
		    }
		}
	    }
	}
	return isHarmonyOrd;
    }

    /**
     * Validates order submit is successful or not
     *
     * @param statusType
     * @return
     */
    private Boolean validateResponse(StatusType statusType) {

	Boolean isSuccess = Boolean.FALSE;

	if (statusType != null) {
	    String msg = statusType.getStatusMsg();
	    if (msg.equalsIgnoreCase("INFO")) {
		return Boolean.TRUE;
	    }
	}
	return isSuccess;
    }

    /**
     * Prepares order mapping bean to save it in db
     *
     * @param mappings
     */
    private VOrderMapping populateMappingBean(Map<String, String> mappings) {
	VOrderMapping mappingBean = new VOrderMapping();
	mappingBean.setOrderExtId(Long.valueOf(mappings.get(ORDER_EXT_ID_KEY)));
	mappingBean.setVOrderNo(mappings.get(V_ORDER_NO_KEY));
	mappingBean.setProviderExtId(mappings.get(PROVIDER_ID_KEY));
	mappingBean.setLiExtId(Long.valueOf(mappings.get(LI_EXT_ID_KEY)));
	mappingBean.setContext(mappings.get(CONTEXT_KEY));
	return mappingBean;
    }

    private String retrieveOrderExtId(Response response) {
	String orderExtId = "";

	if (response != null && response.getOrderInfoList() != null) {
	    orderExtId = String.valueOf(response.getOrderInfoList().get(0).getExternalId());
	}
	return orderExtId;
    }
}

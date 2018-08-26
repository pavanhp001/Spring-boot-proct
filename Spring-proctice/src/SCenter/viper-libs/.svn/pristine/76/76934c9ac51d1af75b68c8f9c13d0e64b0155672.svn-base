package com.A.vm.service;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.A.util.StringUtils;
import com.A.V.beans.entity.Broadcast;
import com.A.Vdao.dao.BroadcastDao;
import com.A.xml.v4.CustomerManagementRequestResponseDocument;
import com.A.xml.v4.OrderManagementRequestResponseDocument;

@Component("broadcastManager")
public class BroadcastManager {

	private static final Logger logger = Logger.getLogger(BroadcastManager.class);

	@Autowired
	private BroadcastDao broadcastDao;

	public long saveBroadcastMessage(Broadcast broadcast){
		long extId = -1L;
		if(broadcast != null){
			extId= broadcastDao.save(broadcast);
		}else{
			logger.warn("Ignoring persistence of broadcast entity as it is null!!!");
		}
		return extId;
	}

	/**
	 * Method to parse order xml to broadcast object
	 * @param orderXml
	 * @param headers
	 * @return
	 */
	public Broadcast buildOrderBroadcast(String orderXml,Map<String, String> headers){
		logger.info("Preparing order broadcast entity");
		Broadcast broadcast = null;
		if(orderXml != null && StringUtils.isNotBlank(orderXml)){
			broadcast = new Broadcast();
			broadcast.setBroadcastDate(Calendar.getInstance());
			broadcast.setBrodcastMessage(orderXml);
			broadcast.setBroadcastType("order");
			//broadcast.setCcpStatus("");
			//broadcast.setDmAdapterStatus("");
			//broadcast.setDwmeStatus("");

			OrderManagementRequestResponseDocument orderDoc  = null;
			try {
				orderDoc = OrderManagementRequestResponseDocument.Factory.parse(orderXml);

				if(orderDoc != null &&  orderDoc.getOrderManagementRequestResponse() != null && orderDoc.getOrderManagementRequestResponse().getTransactionType() != null){
					String transType = orderDoc.getOrderManagementRequestResponse().getTransactionType().toString();
					broadcast.setTransactionType(transType == null ? "" : transType);
					String guid = orderDoc.getOrderManagementRequestResponse().getCorrelationId();
					broadcast.setGuid(guid == null ? "" : guid);
				}

			} catch (XmlException e) {
				logger.warn("Invalid broadcast xml. Ignoring certain attributes for broadcast persistence.");
			}

			String messageHeaders = getMessageHeaders(headers);
			broadcast.setMessageHeaders(messageHeaders);
			logger.debug("Prepared order broadcast : " + broadcast.toString());

		}
		return broadcast;
	}

	/**
	 * Method to convert map message to string
	 * @param headers
	 * @return
	 */
	private String getMessageHeaders(Map<String, String> headers){
		if(headers == null || headers.isEmpty()){
			return "";
		}

		StringBuilder sb = new StringBuilder(headers.size());
		Set<String> keys = headers.keySet();
		for(String key : keys){
			sb.append(key);
			sb.append("#");
			sb.append(headers.get(key));
			sb.append(",");
		}
		return sb.toString();
	}

	/**
	 * Method to convert customer broadcast xml to broadcast entity
	 * @param custXml
	 * @return
	 */
	public Broadcast buildCustomerBroadcast(String custXml,Map<String, String> headers){
		logger.info("Preparing customer broadcast entity");
		Broadcast broadcast = null;
		if(custXml != null && StringUtils.isNotBlank(custXml)){
			broadcast = new Broadcast();
			broadcast.setBroadcastDate(Calendar.getInstance());
			broadcast.setBrodcastMessage(custXml);
			broadcast.setBroadcastType("customer");
			//broadcast.setCcpStatus("");
			//broadcast.setDmAdapterStatus("");
			//broadcast.setDwmeStatus("");

			CustomerManagementRequestResponseDocument custDoc  = null;
			try {
				custDoc = CustomerManagementRequestResponseDocument.Factory.parse(custXml);

				if(custDoc != null &&  custDoc.getCustomerManagementRequestResponse() != null && custDoc.getCustomerManagementRequestResponse().getTransactionType() != null){
					String transType = custDoc.getCustomerManagementRequestResponse().getTransactionType().toString();
					broadcast.setTransactionType(transType == null ? "" : transType);
					String guid = custDoc.getCustomerManagementRequestResponse().getCorrelationId();
					broadcast.setGuid(guid == null ? "" : guid);
				}

			} catch (XmlException e) {
				logger.warn("Invalid broadcast xml. Ignoring certain attributes for broadcast persistence.");
			}

			String messageHeaders = getMessageHeaders(headers);
			broadcast.setMessageHeaders(messageHeaders);
			logger.debug("Prepared customer broadcast : " + broadcast.toString());

		}
		return broadcast;
	}
}

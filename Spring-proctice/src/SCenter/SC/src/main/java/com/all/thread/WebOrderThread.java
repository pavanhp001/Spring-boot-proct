package com.AL.thread;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.AL.bean.WebOrderBean;
import com.AL.service.RepositoryService;
import com.AL.ui.dao.WebOrderDao;
import com.AL.ui.domain.WebOrder;
import com.AL.ui.util.CompressorUtil;

/**
 * This thread is to run the save html in a different process
 * 
 * @author mnagineni@AL.com
 *
 */
public class WebOrderThread implements Runnable{

	private static final Logger logger = Logger.getLogger(WebOrderThread.class);

	private WebOrderDao webOrderDao;
	private RepositoryService s3Service;
	private WebOrderBean order;
	private String rejectRefKey;
	private String wotName;

	public WebOrderThread(WebOrderBean order, WebOrderDao webOrderDao,
			RepositoryService s3Service, String wotName) {
		this.order = order;
		this.webOrderDao = webOrderDao;
		this.s3Service = s3Service;
		this.rejectRefKey = order.getOrderId()+"_"+order.getPage();
		this.wotName = wotName;
	}

	public void run() {

		long currentTime = System.currentTimeMillis();
		
		String customerId = order.getCustomerId();
		String orderId = order.getOrderId();
		String lineItemId = order.getLineItemId();
		String pageType = order.getPageType();
		String agentId = order.getAgentId();
		String page = order.getPage();
		String htmlContent = order.getHtmlContent();
		String guid = order.getGuid();
		String providerId = order.getProviderId();
		String ProductExtId = order.getProductExtId();
		String ucid = StringUtils.isBlank(order.getUcid()) ? "0" : order.getUcid();
		logger.debug("WebOrderThread: STARTED :: Page -> "+page+", Thread name -> "+wotName);

		if(StringUtils.isBlank(customerId) || customerId.equals("null")){
			customerId = "0";
		}

		if(StringUtils.isBlank(orderId)|| orderId.equals("null")){
			orderId = "0";
		}
		
		String keyName = UUID.randomUUID().toString();

		WebOrder order = new WebOrder();
		order.setSavedOn(Calendar.getInstance());
		order.setCustomerId(customerId);
		order.setOrderId(orderId);
		order.setLineItemId(lineItemId);
		order.setPageType(pageType);
		order.setAgentId(agentId);
		order.setPage(page);
		order.setKeyName(keyName);
		order.setUcid(ucid);
		order.setProviderId(providerId);
		order.setProductExtId(ProductExtId);
		//order.setGuid(guid);

		webOrderDao.saveOrder(order);
		
		long id = order.getId();
		String objectName = keyName + "-" +orderId + "-" + id;

		try 
		{
			String compressedString = CompressorUtil.compressString( htmlContent );
			logger.debug("Size after compression : "+compressedString.length());
			
			//putting the data
			s3Service.putObject(objectName, new ByteArrayInputStream(compressedString.getBytes()));
		} 
		catch (IOException e) 
		{
			logger.error("Error while compressing the Html of Id ["+id+"] " + e.getMessage());
		}
		
		logger.debug("WebOrderThread: FINISHED :: [ObjectName/Thread name -> "
				+objectName+"/"+wotName+"],  Total time -> "+(System.currentTimeMillis() - currentTime) + "ms");
	}

	/**
	 * @return the rejectRefKey
	 */
	public String getRejectRefKey() {
		return rejectRefKey;
	}

}

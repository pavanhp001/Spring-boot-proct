package com.AL.controller;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.AL.bean.WebOrderBean;
import com.AL.service.RepositoryItTicketService;
import com.AL.service.RepositoryService;
import com.AL.service.WebOrderService;
import com.AL.ui.dao.WebOrderDao;

/**
 * @author mnagineni@AL.com
 *
 */
@Controller
public class SalesOrderCaptureController {

	private static final Logger logger = Logger.getLogger(SalesOrderCaptureController.class);
	
	@Autowired
	WebOrderDao webOrderDao;
	
	@Autowired
	RepositoryService s3Service;
	
	@Value("${score.enable}")
	private String scoreEnable;
	
	@Autowired
	RepositoryItTicketService s3ItTicketService;
	

	/**
	 * Ajax Mapping method to save Sales Order
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveOrderReviewData")
	public @ResponseBody void saveOrderData(HttpServletRequest request, HttpServletResponse response)
	{
		logger.debug("saveOrderData: score enabled value -> " + scoreEnable);
		try{
			if(scoreEnable == null || scoreEnable.equalsIgnoreCase("false"))
			{
				return;
			}

			long currentTime = System.currentTimeMillis();
			String customerId = request.getParameter("customer_id");
			String orderId = request.getParameter("order_id");
			String lineItemId = request.getParameter("lineitem_id");
			String pageType = request.getParameter("page_type");
			String agentId = request.getParameter("agent_id");
			String page = request.getParameter("page_title");
			String htmlContent = request.getParameter("html_content");
			String ucid = request.getParameter("ucid");
			String guid = request.getParameter("guid");
			String providerId = request.getParameter("providerId");
			String productExtId = request.getParameter("prodExtId");
           
			WebOrderBean order = new WebOrderBean(customerId, orderId, lineItemId, pageType, agentId, page, htmlContent, ucid, guid, providerId, productExtId); 
			

			WebOrderService.sendWebOrder(order, webOrderDao, s3Service);
			logger.debug("saveOrderData: [OrderId -> "+orderId+",  Total time -> "+(System.currentTimeMillis() - currentTime) + "ms");
		}catch(Exception e){
			logger.error("error_occured_while_saveOrderData"+e.getMessage());
		}
		
	}
	

	
	
	@RequestMapping(value="/itTicketImageData")
	private @ResponseBody String itTicketImageData(HttpServletRequest request,@RequestParam CommonsMultipartFile imageData,@RequestParam CommonsMultipartFile iFrameImageData,@RequestParam String orderId, @RequestParam String agentId) throws Exception {
		logger.info("In_itTicketImageData_method");
		try{
			String imgName = "";
			String iFrameImgName = "";
			if(!StringUtils.isBlank(agentId)){
				imgName = (StringUtils.isBlank(orderId)?agentId:orderId+"_"+agentId);
				iFrameImgName = imgName;
			}
			if(imageData != null && imageData.getSize() > 0 && StringUtils.isNotBlank(imgName)){
				String b64 = DatatypeConverter.printBase64Binary(imageData.getBytes());
				request.getSession().setAttribute("ticketImgString", b64);
				imgName = (iFrameImageData != null && iFrameImageData.getSize() > 0)?imgName+"_SalesCenter":imgName;
				String imageName = s3ItTicketService.putObject(imgName, new ByteArrayInputStream(imageData.getBytes()));
				request.getSession().setAttribute("ticketImgName", imageName);
			}
			if(iFrameImageData != null && iFrameImageData.getSize() > 0 && StringUtils.isNotBlank(iFrameImgName)){
				String b64 = DatatypeConverter.printBase64Binary(iFrameImageData.getBytes());
				request.getSession().setAttribute("ticketIframeImgString", b64);
				String imageName = s3ItTicketService.putObject(iFrameImgName+"_CKO", new ByteArrayInputStream(iFrameImageData.getBytes()));
				request.getSession().setAttribute("ticketIframeImgName", imageName);
			}
			
		}
		catch (Exception e) {
			logger.error("error_in_itTicketImageData="+e.getMessage());
		}
		return "success";
	}
	
}


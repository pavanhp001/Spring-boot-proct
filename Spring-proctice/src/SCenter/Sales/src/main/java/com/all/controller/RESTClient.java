package com.AL.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import com.AL.domain.MDUOrderSourceList;
import com.AL.ui.domain.ConsumerViewLites;
import com.AL.ui.domain.MDUOrderSource;
import com.AL.ui.domain.sales.CustomerLookupObject;
import com.AL.ui.domain.sales.CustomerLookupItem;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.ChannelLineupListVO;
import com.AL.ui.vo.ChannelLineupVO;
import com.AL.ui.vo.ConsumerVO;
import com.AL.ui.vo.CustomerVO;
import com.AL.ui.vo.ConsumerVO.DtAddr;
import com.AL.ui.vo.ConsumerVO.DtPhoneType;
import com.AL.util.StringCaseUtil;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.domain.sales.Alert;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kamesh
 *
 */

public enum RESTClient {

	INSTANCE;


	private static final Logger logger = Logger.getLogger(RESTClient.class);

	private static final String GET = "GET";

	//referrer_endpoint
	public  Map<String,CustomerVO> getCustomersbyReferrer(String referralId, String URL_ENDPOINT) throws Exception {
		Map<String,CustomerVO> details = new HashMap<String, CustomerVO>();
		if (!Utils.isBlank(referralId)){
			String urlString = URL_ENDPOINT;
			urlString = urlString.replaceAll("\\$Referrerid\\$", referralId);
			logger.info("urlString="+urlString);
			HttpURLConnection connection =  null;
			InputStream responseBodyStream = null;
			try{

			URL url = new URL(urlString);

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(GET);

			byte buffer[] = new byte[8192];
			int read = 0;

			connection.connect();

			responseBodyStream = connection.getInputStream();
			StringBuffer responseBody = new StringBuffer();
			if (responseBodyStream != null){
				while ((read = responseBodyStream.read(buffer)) != -1) {
					responseBody.append(new String(buffer, 0, read,"UTF-8"));
				}			
			}
			
			details = generateCustomerData(responseBody.toString());
			logger.info("customerData="+details);
			}catch (IOException e) {
				throw e;
			}finally{
				if(connection!=null){
					connection.disconnect();
				}
				if(responseBodyStream!=null){
					responseBodyStream.close();
				}
				
			}
		}
		return details;
	}

	private Map<String,CustomerVO> generateCustomerData(String response) throws Exception{

		CustomerVO customerVO = null;

		Map<String,CustomerVO> details = new HashMap<String, CustomerVO>();
		try{

		while (response.indexOf("<directTransferViewLite>") != -1
				&& response
				.indexOf("</directTransferViewLite>") != -1) {
			String sequenceID = null;
			String res = extractXmlValue(response,
					"<directTransferViewLite>", "</directTransferViewLite>");

			customerVO = new CustomerVO();

			if (res.indexOf("<sequenceID>") != -1
					&& res.indexOf("</sequenceID>") != -1) {

				sequenceID = extractXmlValue(res,
						"<sequenceID>", "</sequenceID>");
				customerVO.setDtSequenceNum(sequenceID);

			}
			if (res.indexOf("<firstName>") != -1
					&& res.indexOf("</firstName>") != -1) {
				String firstName = extractXmlValue(res,
						"<firstName>", "</firstName>");
				customerVO.setDtNameFirst(StringCaseUtil.toCamelCase(firstName,true));
			}

			if (res.indexOf("<lastName>") != -1
					&& res.indexOf("</lastName>") != -1) {

				String lastName = extractXmlValue(res,
						"<lastName>", "</lastName>");
				customerVO.setDtNameLast(StringCaseUtil.toCamelCase(lastName,true));

			}

			if (res.indexOf("<partnerID>") != -1
					&& res.indexOf("</partnerID>") != -1) {

				String partnerID = extractXmlValue(res,
						"<partnerID>", "</partnerID>");
				customerVO.setDtPartner(partnerID);

			}

			if (res.indexOf("<state>") != -1
					&& res.indexOf("</state>") != -1) {

				String state = extractXmlValue(res,
						"<state>", "</state>");
				customerVO.setDtSaState(state);

			}

			if (res.indexOf("<city>") != -1
					&& res.indexOf("</city>") != -1) {

				String city = extractXmlValue(res,
						"<city>", "</city>");
				customerVO.setDtSaCity(StringCaseUtil.toCamelCase(city,false));

			}

			if (res.indexOf("<partnerAccountID>") != -1
					&& res.indexOf("</partnerAccountID>") != -1) {

				String partnerAccountID = extractXmlValue(res,
						"<partnerAccountID>", "</partnerAccountID>");
				customerVO.setDtPartnerAccountId(partnerAccountID);

			}

			if (res.indexOf("<zipCode>") != -1
					&& res.indexOf("</zipCode>") != -1) {

				String zipCode = extractXmlValue(res,
						"<zipCode>", "</zipCode>");
				customerVO.setDtSaZip(zipCode);

			}

			if (res.indexOf("<dtGasRequestedStartDate>") != -1
					&& res.indexOf("</dtGasRequestedStartDate>") != -1) {

				String dtGasRequestedStartDate = extractXmlValue(res,
						"<dtGasRequestedStartDate>", "</dtGasRequestedStartDate>");
				customerVO.setDtGasRequestedStartDate(dtGasRequestedStartDate);
			}

			if (res.indexOf("<dtRequestedStartDate>") != -1
					&& res.indexOf("</dtRequestedStartDate>") != -1) {

				String dtRequestedStartDate = extractXmlValue(res,
						"<dtRequestedStartDate>", "</dtRequestedStartDate>");
				customerVO.setDtRequestedStartDate(dtRequestedStartDate);
			}

			response = response.substring(response.indexOf(
			"</directTransferViewLite>")
			+ "</directTransferViewLite>".length());
			details.put(sequenceID, customerVO);
		}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return details;
	}

	public static String extractXmlValue(String source, String from, String to) {

		if (source.indexOf(from) != -1 && source.indexOf(to) != -1) {
			int startPos = source.indexOf(from) + from.length();
			int endPos = source.indexOf(to);
			return source.substring(startPos, endPos);
		} else
			return null;

	}

	//dataexchange_endpoint
	public  Map<String,CustomerVO> getCustomersdetailsById(String CustomerId, String URL_ENDPOINT1) throws IOException {
		Map<String,CustomerVO> details = new HashMap<String, CustomerVO>();
		if (!Utils.isBlank(CustomerId)){
			String urlString = URL_ENDPOINT1;
			urlString = urlString.replaceAll("\\$Customerid\\$", CustomerId);
			logger.info("urlString="+urlString);
			HttpURLConnection connection = null;
			InputStream responseBodyStream = null;
			try{

			URL url = new URL(urlString);

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(GET);

			byte buffer[] = new byte[8192];
			int read = 0;

			connection.connect();

			responseBodyStream = connection.getInputStream();
			StringBuffer responseBody = new StringBuffer();
			while ((read = responseBodyStream.read(buffer)) != -1) {
				responseBody.append(new String(buffer, 0, read,"UTF-8"));
			}
			
			details = generateCustomerInfoBasic(responseBody.toString());

			logger.info("customerDetails="+details);
			}catch (IOException e) {
				throw e;
			}finally{
				if(connection!=null){
					connection.disconnect();
				}
				if(responseBodyStream!=null){
					responseBodyStream.close();
				}
			}
		}
		return details;
	}
	
	//dataexchange_endpoint
		public  Map<String,CustomerVO> getCustomersdetailsById(String CustomerId, String URL_ENDPOINT1, HttpServletRequest request) throws IOException {
			Map<String,CustomerVO> details = new HashMap<String, CustomerVO>();
			if (!Utils.isBlank(CustomerId)){
				String urlString = URL_ENDPOINT1;
				urlString = urlString.replaceAll("\\$Customerid\\$", CustomerId);
				logger.info("urlString="+urlString);
				HttpURLConnection connection = null;
				InputStream responseBodyStream = null;
				try{

				URL url = new URL(urlString);

				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod(GET);

				byte buffer[] = new byte[8192];
				int read = 0;

				connection.connect();

				responseBodyStream = connection.getInputStream();
				StringBuffer responseBody = new StringBuffer();
				while ((read = responseBodyStream.read(buffer)) != -1) {
					responseBody.append(new String(buffer, 0, read,"UTF-8"));
				}
				
				details = generateCustomerInfoBasic(request, responseBody.toString());

				logger.info("customerDetails="+details);
				}catch (IOException e) {
					throw e;
				}finally{
					if(connection!=null){
						connection.disconnect();
					}
					if(responseBodyStream!=null){
						responseBodyStream.close();
					}
				}
			}
			return details;
		}

	private Map<String,CustomerVO> generateCustomerInfoBasic(HttpServletRequest request, String response) throws UnsupportedEncodingException {

		CustomerVO customerVO = new CustomerVO();

		Map<String,CustomerVO> details = new HashMap<String, CustomerVO>();
		StringReader sr = null;
		JAXBContext transientContainerJxbContext = null;
		ConsumerVO dtConsumer = null;
		try {
			
			if (request != null && request.getSession() != null && request.getSession().getAttribute("transientConsumerView") != null){
				transientContainerJxbContext = (JAXBContext) request.getSession().getAttribute("transientConsumerView");	
			}else{
				transientContainerJxbContext = JAXBContext.newInstance(ConsumerViewLites.class);
				request.getSession().setAttribute("transientConsumerView",transientContainerJxbContext);
			}
			sr = new StringReader(response);
			Unmarshaller unmarshaller = transientContainerJxbContext.createUnmarshaller();
			JAXBElement<ConsumerVO> b = unmarshaller.unmarshal(new StreamSource(sr), ConsumerVO.class);
			dtConsumer = b.getValue();
			
		} 
		catch (JAXBException e) {
			logger.warn("error_while_unmarshaling_response",e);
		}
		catch (Exception ex) {
			logger.warn("error_in_RESTClient",ex);
		} 
		finally {
			sr.close();
		}
		if(dtConsumer!=null) {
			//this is only for DT harness injection of DT xml data
			logger.info("CustomerVO="+dtConsumer);

			//use the DtConsumer coming from DT harness 
			
			customerVO.setDtNameFirst(StringCaseUtil.toCamelCase(dtConsumer.getDtNameFirst(),true));
			customerVO.setDtNameMiddle(StringCaseUtil.toCamelCase(dtConsumer.getDtNameMiddle(),true));
			customerVO.setDtNameLast(StringCaseUtil.toCamelCase(dtConsumer.getDtNameLast(),true));
			customerVO.setDtPartnerAccountId(dtConsumer.getDtPartnerAccountId());
			customerVO.setDtSequenceNum(String.valueOf(dtConsumer.getDtSequenceNum()));
			customerVO.setDtPartner(dtConsumer.getDtPartner());
			customerVO.setDtGasRequestedStartDate(dtConsumer.getDtGasRequestedStartDate());
			customerVO.setDtRequestedStartDate(dtConsumer.getDtRequestedStartDate());
			customerVO.setDtEmail(dtConsumer.getDtEmail());
			customerVO.setDtConfirmedEmailAddress(dtConsumer.getDtConfirmedEmailAddress());
			customerVO.setDtGasReqStartTimeBegin(dtConsumer.getDtGasReqStartTimeBegin());
			customerVO.setDtGasReqStartTimeEnd(dtConsumer.getDtGasReqStartTimeEnd());
			customerVO.setDtReqStartTimeBegin(dtConsumer.getDtReqStartTimeBegin());
			customerVO.setDtReqStartTimeEnd(dtConsumer.getDtReqStartTimeEnd());
			
			List<DtPhoneType> dt_phone =  new ArrayList<DtPhoneType>();
			dt_phone = dtConsumer.getDt_phone();
			for(DtPhoneType dtPhoneType: dt_phone){
				if(dtPhoneType.getDtTelephoneType().equals("1")){
					customerVO.setDtTelephoneNum(dtPhoneType.getDtTelephoneNum());
				}
			}
			List<DtAddr> dt_address = new ArrayList<DtAddr>();
			dt_address = dtConsumer.getDt_address();
			for(DtAddr dtAddr: dt_address){
				if(dtAddr.getDtAddressType().equals("1")){
					customerVO.setDtSaCity(StringCaseUtil.toCamelCase(dtAddr.getDtAddressCity(),false));
					customerVO.setDtSaState(dtAddr.getDtAddressState());
					customerVO.setDtSaStreet1(StringCaseUtil.toCamelCase(dtAddr.getDtAddressStreet1(),false));
					customerVO.setDtSaStreet2(StringCaseUtil.toCamelCase(dtAddr.getDtAddressStreet2(),false));
					customerVO.setDtSaZip(dtAddr.getDtAddressZip());
				}
			}
			dt_address = dtConsumer.getDt_address();
			customerVO.setDtNamePrefix(dtConsumer.getDtNamePrefix());
			customerVO.setDtNameSuffix(dtConsumer.getDtNameSuffix());
			customerVO.setDtAgentId(dtConsumer.getDtAgentId());
			customerVO.setDtConsumerSsn(dtConsumer.getDtConsumerSsn());
			if(dtConsumer.getDtCreated()!= null){
			    customerVO.setDtCreated(String.valueOf(dtConsumer.getDtCreated()));
			}
			customerVO.setPartnerSpecificDataMap(dtConsumer.getPartnerSpecificDataMap());
		}
		if(dtConsumer.getDtSequenceNum() != null){
		    details.put(dtConsumer.getDtSequenceNum().toString(), customerVO);
		}
		return details;
	}

	//matchconsumer_endpoint
	public  Map<String,CustomerVO> getMatchedConsumer(String CustomerId, String URL_ENDPOINT2) throws IOException {
		Map<String,CustomerVO> details = new HashMap<String, CustomerVO>();
		if (!Utils.isBlank(CustomerId)){
			String urlString = URL_ENDPOINT2;
			urlString = urlString.replaceAll("\\$Customerid\\$", CustomerId);
			logger.info("urlString="+urlString);
			HttpURLConnection connection = null;
			InputStream responseBodyStream = null;

			try{
				
			URL url = new URL(urlString);

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(GET);

			byte buffer[] = new byte[8192];
			int read = 0;

			connection.connect();

			responseBodyStream = connection.getInputStream();
			StringBuffer responseBody = new StringBuffer();
			while ((read = responseBodyStream.read(buffer)) != -1) {
				responseBody.append(new String(buffer, 0, read,"UTF-8"));
			}
			
			details = generateCustomerDataForMatchedConsumers(responseBody.toString());

			logger.info("customer_data_for_matched_consumers"+details);
			}catch (IOException e) {
				throw e;
			}finally{
				if(connection!=null){
					connection.disconnect();	
				}
				if(responseBodyStream!=null){
					responseBodyStream.close();
				}
				
			}
		}
		return details;
	}

	private Map<String, CustomerVO> generateCustomerDataForMatchedConsumers(String response) throws UnsupportedEncodingException {

		CustomerVO customerVO = new CustomerVO();

		Map<String,CustomerVO> details = new HashMap<String, CustomerVO>();

		while (response.indexOf("<dtConsumer>") != -1
				&& response
				.indexOf("</dtConsumer>") != -1) {
			String sequenceID = null;
			String res = extractXmlValue(response,
					"<dtConsumer>", "</dtConsumer>");

			if (res.indexOf("<dtSequenceNum>") != -1
					&& res.indexOf("</dtSequenceNum>") != -1) {

				sequenceID = extractXmlValue(res,
						"<dtSequenceNum>", "</dtSequenceNum>");
				customerVO.setDtSequenceNum(sequenceID);

			}

			if (res.indexOf("<customerMatch>") != -1
					&& res.indexOf("</customerMatch>") != -1) {

				String customerMatch = extractXmlValue(res,
						"<customerMatch>", "</customerMatch>");
				customerVO.setCustomerMatch(customerMatch);

			}


			if (res.indexOf("<dtAgentId>") != -1
					&& res.indexOf("</dtAgentId>") != -1) {

				String dtAgentId = extractXmlValue(res,
						"<dtAgentId>", "</dtAgentId>");
				customerVO.setDtAgentId(dtAgentId);

			}
			if (res.indexOf("<dtConfirmedEmailAddress>") != -1
					&& res.indexOf("</dtConfirmedEmailAddress>") != -1) {

				String dtConfirmedEmailAddress = extractXmlValue(res,
						"<dtConfirmedEmailAddress>", "</dtConfirmedEmailAddress>");
				customerVO.setDtConfirmedEmailAddress(dtConfirmedEmailAddress);

			}
			if (res.indexOf("<dtConsumerSsn>") != -1
					&& res.indexOf("</dtConsumerSsn>") != -1) {
				String dtConsumerSsn = extractXmlValue(res,
						"<dtConsumerSsn>", "</dtConsumerSsn>");
				customerVO.setDtConsumerSsn(dtConsumerSsn);
			}

			if (res.indexOf("<dtCreated>") != -1
					&& res.indexOf("</dtCreated>") != -1) {

				String dtCreated = extractXmlValue(res,
						"<dtCreated>", "</dtCreated>");
				customerVO.setDtCreated(dtCreated);

			}

			if (res.indexOf("<dtNameFirst>") != -1
					&& res.indexOf("</dtNameFirst>") != -1) {

				String dtNameFirst = extractXmlValue(res,
						"<dtNameFirst>", "</dtNameFirst>");
				customerVO.setDtNameFirst(dtNameFirst);

			}
			if (res.indexOf("<dtNameLast>") != -1
					&& res.indexOf("</dtNameLast>") != -1) {

				String dtNameLast = extractXmlValue(res,
						"<dtNameLast>", "</dtNameLast>");
				customerVO.setDtNameLast(dtNameLast);

			}

			if (res.indexOf("<dtPartner>") != -1
					&& res.indexOf("</dtPartner>") != -1) {

				String dtPartner = extractXmlValue(res,
						"<dtPartner>", "</dtPartner>");
				customerVO.setDtPartner(dtPartner);

			}
			if (res.indexOf("<dtPartnerAccountId>") != -1
					&& res.indexOf("</dtPartnerAccountId>") != -1) {

				String dtPartnerAccountId = extractXmlValue(res,
						"<dtPartnerAccountId>", "</dtPartnerAccountId>");
				customerVO.setDtPartnerAccountId(dtPartnerAccountId);

			}

			if (res.indexOf("<dtReqStartTimeBegin>") != -1
					&& res.indexOf("</dtReqStartTimeBegin>") != -1) {

				String dtReqStartTimeBegin = extractXmlValue(res,
						"<dtReqStartTimeBegin>", "</dtReqStartTimeBegin>");
				customerVO.setDtReqStartTimeBegin(dtReqStartTimeBegin);

			}

			if (res.indexOf("<dtRequestedStartDate>") != -1
					&& res.indexOf("</dtRequestedStartDate>") != -1) {

				String dtRequestedStartDate = extractXmlValue(res,
						"<dtRequestedStartDate>", "</dtRequestedStartDate>");
				customerVO.setDtRequestedStartDate(dtRequestedStartDate);

			}

			if (res.indexOf("<dtSaCity>") != -1
					&& res.indexOf("</dtSaCity>") != -1) {

				String dtSaCity = extractXmlValue(res,
						"<dtSaCity>", "</dtSaCity>");
				customerVO.setDtSaCity(dtSaCity);

			}

			if (res.indexOf("<dtSaState>") != -1
					&& res.indexOf("</dtSaState>") != -1) {

				String dtSaState = extractXmlValue(res,
						"<dtSaState>", "</dtSaState>");
				customerVO.setDtSaState(dtSaState);

			}

			if (res.indexOf("<dtSaStreet1>") != -1
					&& res.indexOf("</dtSaStreet1>") != -1) {

				String dtSaStreet1 = extractXmlValue(res,
						"<dtSaStreet1>", "</dtSaStreet1>");
				customerVO.setDtSaStreet1(dtSaStreet1);

			}
			if (res.indexOf("<dtSaZip>") != -1
					&& res.indexOf("</dtSaZip>") != -1) {

				String dtSaZip = extractXmlValue(res,
						"<dtSaZip>", "</dtSaZip>");
				customerVO.setDtSaZip(dtSaZip);

			}				


			if (res.indexOf("<externalId>") != -1
					&& res.indexOf("</externalId>") != -1) {

				String externalId = extractXmlValue(res,
						"<externalId>", "</externalId>");
				customerVO.setExternalId(externalId);

			}


			response = response.substring(response.indexOf(
			"</dtConsumer>")
			+ "</dtConsumer>".length());
			details.put(sequenceID, customerVO);
		}
		return details;
	}


	public  boolean setCustomerLinkAndMatch(String dtSeqNumber, String URL_ENDPOINT2, String customerId) throws IOException {
		boolean updateIndicator = false;
		if (!Utils.isBlank(customerId)){
			String urlString = URL_ENDPOINT2;
			urlString = urlString.replaceAll("\\$Customerid\\$", dtSeqNumber);
			urlString = urlString.replaceAll("link", "&"+"link");
			urlString = urlString.replaceAll("\\$Link\\$", customerId);
			logger.info("urlstring="+urlString);
			HttpURLConnection connection = null;
			InputStream responseBodyStream = null;
			try{
				URL url = new URL(urlString);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod(GET);
				byte buffer[] = new byte[8192];
				int read = 0;
				connection.connect();
				responseBodyStream = connection.getInputStream();
				StringBuffer responseBody = new StringBuffer();
				while ((read = responseBodyStream.read(buffer)) != -1) {
					responseBody.append(new String(buffer, 0, read,"UTF-8"));
				}
				updateIndicator = generateCustomerLinkAndMatchResponse(responseBody.toString());
			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}finally{
				if(connection!=null){
					connection.disconnect();
				}
				if(responseBodyStream!=null){
					responseBodyStream.close();
				}
			}
		}
		return updateIndicator;
	}
	
	private boolean generateCustomerLinkAndMatchResponse(String response) {
		boolean updateIndicator = false;
		while (response.indexOf("<dtConsumer>") != -1
				&& response
				.indexOf("</dtConsumer>") != -1) {
			String res = extractXmlValue(response,
					"<dtConsumer>", "</dtConsumer>");
			if (res.indexOf("<updateIndicator>") != -1
					&& res.indexOf("</updateIndicator>") != -1) {
				updateIndicator = Boolean.valueOf(extractXmlValue(res,
						"<updateIndicator>", "</updateIndicator>"));
			}
			response = response.substring(response.indexOf(
			"</dtConsumer>")
			+ "</dtConsumer>".length());
		}
		return updateIndicator;
	}
	
	/**
	 * Get ChannelLineupData based on ProviderId 
	 * 
	 * 
	 * @param providerId
	 * @param URL_ENDPOINT1
	 * @return ChannelLineupVO
	 * @param request 
	 */
	public  ChannelLineupVO generateChannelLineupDataByProviderId(String providerId, String URL_ENDPOINT1, HttpServletRequest request) throws IOException {
		ChannelLineupVO channelLineupVO = new ChannelLineupVO();
		if (!Utils.isBlank(providerId)){
			String urlString = URL_ENDPOINT1;
			urlString = urlString.replaceAll("\\$ProviderId\\$", providerId);
			logger.info("urlString="+urlString);
			HttpURLConnection connection = null;
			InputStream responseBodyStream = null;
			try{

			URL url = new URL(urlString);

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(GET);

			byte buffer[] = new byte[8192];
			int read = 0;

			connection.connect();

			responseBodyStream = connection.getInputStream();
			StringBuffer responseBody = new StringBuffer();
			while ((read = responseBodyStream.read(buffer)) != -1) {
				responseBody.append(new String(buffer, 0, read,"UTF-8"));
			}
			
			channelLineupVO = getChannelLineupResponseData(request, responseBody.toString());

			logger.info("ProviderDetails="+channelLineupVO);
			}catch (IOException e) {
				throw e;
			}finally{
				if(connection!=null){
					connection.disconnect();
				}
				if(responseBodyStream!=null){
					responseBodyStream.close();
				}
			}
		}
		return channelLineupVO;
	}
	
	/**
	 * Get ChannelLineupData based on ProviderId 
	 * 
	 * 
	 * @param providerId
	 * @param URL_ENDPOINT1
	 * @return ChannelLineupVO
	 * @param request 
	 */
	public  ChannelLineupVO generateChannelLineupDataByProviderId(String providerId, String URL_ENDPOINT1) throws IOException {
		ChannelLineupVO channelLineupVO = new ChannelLineupVO();
		if (!Utils.isBlank(providerId)){
			String urlString = URL_ENDPOINT1;
			urlString = urlString.replaceAll("\\$ProviderId\\$", providerId);
			logger.info("urlString="+urlString);
			HttpURLConnection connection = null;
			InputStream responseBodyStream = null;
			try{

			URL url = new URL(urlString);

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(GET);

			byte buffer[] = new byte[8192];
			int read = 0;

			connection.connect();

			responseBodyStream = connection.getInputStream();
			StringBuffer responseBody = new StringBuffer();
			while ((read = responseBodyStream.read(buffer)) != -1) {
				responseBody.append(new String(buffer, 0, read,"UTF-8"));
			}
			
			channelLineupVO = getChannelLineupResponseData(responseBody.toString());

			logger.info("ProviderDetails="+channelLineupVO);
			}catch (IOException e) {
				throw e;
			}finally{
				if(connection!=null){
					connection.disconnect();
				}
				if(responseBodyStream!=null){
					responseBodyStream.close();
				}
			}
		}
		return channelLineupVO;
	}

	/**
	 * JAXB conservation for ChannelLineupResponseData 
	 * 
	 * @param cluResponseData
	 * @return ChannelLineupVO
	 */
	private ChannelLineupVO getChannelLineupResponseData(String cluResponseData) throws UnsupportedEncodingException {

		ChannelLineupVO channelLineupVO = new ChannelLineupVO();

		StringReader sr = null;
		try {
			JAXBContext transientContainerJxbContext = JAXBContext.newInstance(ChannelLineupListVO.class);
			sr = new StringReader(cluResponseData);
			Unmarshaller unmarshaller = transientContainerJxbContext.createUnmarshaller();
			JAXBElement<ChannelLineupVO> b = unmarshaller.unmarshal(new StreamSource(sr), ChannelLineupVO.class);
			channelLineupVO = b.getValue();
		} 
		catch (JAXBException e) {
			logger.warn("error_while_unmarshaling_response",e);
		}
		catch (Exception ex) {
			logger.warn("error_in_RESTClient",ex);
		} 
		finally {
			sr.close();
		}
		return channelLineupVO;
	}
	
	/**
	 * JAXB conservation for ChannelLineupResponseData 
	 * 
	 * @param cluResponseData
	 * @return ChannelLineupVO
	 */
	private ChannelLineupVO getChannelLineupResponseData(HttpServletRequest request, String cluResponseData) throws UnsupportedEncodingException {

		ChannelLineupVO channelLineupVO = new ChannelLineupVO();
		JAXBContext transientContainerJxbContext = null;
		StringReader sr = null;
		try {
			if (request != null && request.getSession() != null && request.getSession().getAttribute("transient") != null){
				transientContainerJxbContext = (JAXBContext) request.getSession().getAttribute("transient");	
			}else{
				transientContainerJxbContext = JAXBContext.newInstance(ChannelLineupListVO.class);
				request.getSession().setAttribute("transient",transientContainerJxbContext);
			}
			sr = new StringReader(cluResponseData);
			Unmarshaller unmarshaller = transientContainerJxbContext.createUnmarshaller();
			JAXBElement<ChannelLineupVO> b = unmarshaller.unmarshal(new StreamSource(sr), ChannelLineupVO.class);
			channelLineupVO = b.getValue();
		} 
		catch (JAXBException e) {
			logger.warn("error_while_unmarshaling_response",e);
		}
		catch (Exception ex) {
			logger.warn("error_in_RESTClient",ex);
		} 
		finally {
			sr.close();
		}
		return channelLineupVO;
	}
	
	/**
	 * @param phoneId
	 * @param pauseAndResumeURL
	 * @throws IOException
	 */
	public  void resumeCall(String phoneId, String pauseAndResumeURL) {
		String urlString = pauseAndResumeURL+"?phoneId="+phoneId+"&action=Resume";
		logger.info("pauseAndResumeURLString="+urlString);
		HttpURLConnection connection = null;
		try{
			URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(GET);
			connection.connect();
			connection.getResponseMessage();
		}catch (IOException e) {
			logger.warn("error_in_RESTClient_resumeCall",e);;
		}finally{
			if(connection!=null){
				connection.disconnect();
			}
		}
	}
	
	public  String connectCustomerLookup(String URL_ENDPOINT) throws Exception {
		StringBuffer responseBody = new StringBuffer();
		String returnStr = null;
			StringBuffer urlString = new StringBuffer(URL_ENDPOINT);
			HttpURLConnection connection =  null;
			InputStream responseBodyStream = null;
			try{
				URL url = new URL(urlString.toString());
				if (urlString != null && urlString.toString().contains("https:")){
					connection = (HttpsURLConnection) url.openConnection();	
				}else{
					connection = (HttpURLConnection) url.openConnection();
				}
				connection.setRequestMethod(GET);
				byte buffer[] = new byte[8192];
				int read = 0;

				connection.connect();
				responseBodyStream = connection.getInputStream();

				if (responseBodyStream != null){
					while ((read = responseBodyStream.read(buffer)) >= 0) {
						responseBody.append(new String(buffer, 0, read,"UTF-8"));
					}			
				}
				returnStr = responseBody.toString();
			}catch (IOException e) {
				logger.error("error_occured_while_get_CustomerLookup_response",e);
				logger.warn("No response");
			}finally{
				if(connection!=null){
					connection.disconnect();
				}
				if(responseBodyStream!=null){
					responseBodyStream.close();
				}
			}
		return returnStr;
	}
	
	public  String connectActiveAlert(String URL_ENDPOINT) throws Exception {
		StringBuffer responseBody = new StringBuffer();
		String returnStr = null;
			StringBuffer urlString = new StringBuffer(URL_ENDPOINT);
			HttpURLConnection connection =  null;
			InputStream responseBodyStream = null;
			try{
				URL url = new URL(urlString.toString());
				if (urlString != null && urlString.toString().contains("https:")){
					connection = (HttpsURLConnection) url.openConnection();	
				}else{
					connection = (HttpURLConnection) url.openConnection();
				}
				connection.setRequestMethod(GET);
				byte buffer[] = new byte[8192];
				int read = 0;

				connection.connect();
				responseBodyStream = connection.getInputStream();

				if (responseBodyStream != null){
					while ((read = responseBodyStream.read(buffer)) >= 0) {
						responseBody.append(new String(buffer, 0, read,"UTF-8"));
					}	
					returnStr = responseBody.toString();
				} else {
					returnStr = "";
				}
				
			}catch (IOException e) {
				logger.error("error_occured_while_get_ActiveAlert_response",e);
				logger.warn("No response");
			}finally{
				if(connection!=null){
					connection.disconnect();
				}
				if(responseBodyStream!=null){
					responseBodyStream.close();
				}
			}
		return returnStr;
	}	
	
	public Alert getActiveAlert() throws Exception {
		String URL_ENDPOINT = ConfigRepo.getString("*.activeAlertUrl") == null ? null : ConfigRepo.getString("*.activeAlertUrl");
		logger.info(Utils.isBlank(URL_ENDPOINT) ? "activeAlertUrl" : URL_ENDPOINT);
		String returnStr = null;
		Alert alert = new Alert();
		StringBuffer urlString = new StringBuffer(URL_ENDPOINT);
		logger.info("urlString-activeAlert="+urlString);
		returnStr = connectActiveAlert(urlString.toString());
		if(Utils.isBlank(returnStr)) {
			return null;
		}
		if (returnStr != null){
			JSONArray jsonArray = (JSONArray)JSONValue.parse(returnStr);
			if(jsonArray.size() <= 0) {
				return null;
			}
			for (int i = 0; i < jsonArray.size(); i++)
			{
				JSONObject row = (JSONObject)jsonArray.get(i);
					String shortDesc = (String)row.get("shortDesc");
					String longDesc = (String)row.get("longDesc");
					String type = (String)row.get("type");
					String title = (String)row.get("title");
					String createdDate = (String)row.get("createdDate");
					alert.setShortDesc(shortDesc);
					alert.setLongDesc(longDesc);
					alert.setType(type);
					alert.setTitle(title);
					alert.setCreatedDate(createdDate);
				}
			return alert;
			}
		return null;
		}
	
	
	public  String getAllCustomerLookups(String referrerId, String firstName, String lastName, String zipCode) throws Exception {
		String URL_ENDPOINT = ConfigRepo.getString("*.customerLookupUrl") == null ? null : ConfigRepo.getString("*.customerLookupUrl");
		logger.info(Utils.isBlank(URL_ENDPOINT) ? "customerLookupUrl" : URL_ENDPOINT);
		String returnStr = null;
		if(Utils.isBlank(URL_ENDPOINT)) {
			logger.info("CUSTOMERLOOKUP ERROR:could not retrieve sys_config customerLookupUrl");
			return null;
		}
		if(Utils.isBlank(referrerId)) {
			logger.info("CUSTOMERLOOKUP ERROR:could not get referrerId from orderSource");
			return null;
		}
		StringBuffer urlString = new StringBuffer(URL_ENDPOINT);
/*		if (!Utils.isBlank(firstName) && !Utils.isBlank(lastName)){
			urlString.append("getAllCustomerLookupDataByFirstNameAndLastName?firstname=" + firstName + "&lastname=" + lastName + "&program_id=" + programId);			
		}
		if(!Utils.isBlank(zipCode) && !Utils.isBlank(lastName)) {
			urlString.append("getAllCustomerLookupDataByLastNameAndZip?lastname=" + lastName + "&zip="
					+ zipCode + "&program_id=" + programId);
		}*/
		urlString.append("?referrer_Id=" + referrerId);
		if (!Utils.isBlank(firstName)) {
			urlString.append("&firstname="
					+ URLEncoder.encode(firstName, "UTF-8"));
		}
		if (!Utils.isBlank(lastName)) {
			urlString.append("&lastname="
					+ URLEncoder.encode(lastName, "UTF-8"));
		}
		if(!Utils.isBlank(zipCode)) {
			urlString.append("&zip=" + zipCode);
		}
		logger.info("urlString-customerLookup="+urlString);
		returnStr = connectCustomerLookup(urlString.toString());
		return returnStr;
	}
	
	public List<CustomerLookupObject> searchCustomerLookup(String referrerId, String firstName, String lastName, String zipCode) throws Exception  {
		String response = "";
		List<CustomerLookupObject> customerLookupObjects = new ArrayList<CustomerLookupObject>();
		response = getAllCustomerLookups(referrerId, firstName, lastName, zipCode);
		if(Utils.isBlank(response)) {
			return null;
		}
		if (response != null){
			JSONArray jsonArray = (JSONArray)JSONValue.parse(response);
			if(jsonArray.size() <= 0) {
				return null;
			}
			for (int i = 0; i < jsonArray.size(); i++)
			{
				CustomerLookupObject customerLookupObject = new CustomerLookupObject();
				CustomerLookupItem customerLookupItem = new CustomerLookupItem();
				if (jsonArray != null && jsonArray.get(i) != null){
					JSONObject row = (JSONObject)jsonArray.get(i);
					long idRow = (Long)row.get("id");
					String partnerIdRow = (String)row.get("partnerId");
					String accountIdRow = (String)row.get("accountId");
					String middleNameRow = (String)row.get("middleName");
					String firstNameRow = (String)row.get("firstName");
					String lastNameRow = (String)row.get("lastName");
					String address1Row = (String)row.get("address1");
					String address2Row = (String)row.get("address2");
					String cityRow = (String)row.get("city");
					String stateRow = (String)row.get("state");
					String zipRow = (String)row.get("zip");
					String emailRow = (String)row.get("email");
					String phoneRow = (String)row.get("phone");
					if(row.get("contractAccNum")!= null){
						String contractAccountNumber = (String)row.get("contractAccNum");
						customerLookupItem.setContractAccountNumber(contractAccountNumber);
					}
					String providerData1Row = (String)row.get("providerData1");
					String providerData2Row = (String)row.get("providerData2");
					String providerData3Row = (String)row.get("providerData3");
					String providerData4Row = (String)row.get("providerData4");
					String providerData5Row = (String)row.get("providerData5");
					String attributesRow = (String)row.get("attributes");
					String campaignId = (String)row.get("campaignid");
					if(row.get("operatingcompany")!= null){
						String operatingCompany = (String)row.get("operatingcompany");
						customerLookupItem.setOperatingCompany(operatingCompany);
					}
					customerLookupObject.setId(Long.toString(idRow));
					customerLookupItem.setPartnerId(partnerIdRow);
					customerLookupItem.setAccountId(accountIdRow);
					customerLookupItem.setMiddleName(middleNameRow);
					customerLookupItem.setFirstName(firstNameRow);
					customerLookupItem.setLastName(lastNameRow);
					customerLookupItem.setAddress1(address1Row);
					customerLookupItem.setAddress2(address2Row);
					customerLookupItem.setCity(cityRow);
					customerLookupItem.setState(stateRow);
					customerLookupItem.setZip(zipRow);
					customerLookupItem.setEmail(emailRow);
					customerLookupItem.setPhone(phoneRow);
					customerLookupItem.setProviderData1(providerData1Row);
					customerLookupItem.setProviderData2(providerData2Row);
					customerLookupItem.setProviderData3(providerData3Row);
					customerLookupItem.setProviderData4(providerData4Row);
					customerLookupItem.setProviderData5(providerData5Row);
					customerLookupItem.setAttributes(attributesRow);
					customerLookupItem.setCampaignId(campaignId);
					customerLookupObject.getLineItems().add(customerLookupItem);
					customerLookupObjects.add(customerLookupObject);
				}
			}
		}
		return customerLookupObjects;
	}
	
	public String getAllMDUProgramExternalIds(String mdu_url) throws IOException {
		String programExternalIds = null;
		if (!Utils.isBlank(mdu_url)){
			String urlString = mdu_url;
			logger.info("urlString="+urlString);
			HttpURLConnection connection = null;
			InputStream responseBodyStream = null;
			try{

			URL url = new URL(urlString);

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(GET);

			byte buffer[] = new byte[8192];
			int read = 0;

			connection.connect();

			responseBodyStream = connection.getInputStream();
			StringBuffer responseBody = new StringBuffer();
			while ((read = responseBodyStream.read(buffer)) != -1) {
				responseBody.append(new String(buffer, 0, read,"UTF-8"));
			}
			
			programExternalIds = responseBody.toString();

			//logger.info("ProviderDetails="+ProgramExternalIds);
			}catch (IOException e) {
				throw e;
			}finally{
				if(connection!=null){
					connection.disconnect();
				}
				if(responseBodyStream!=null){
					responseBodyStream.close();
				}
			}
		}
		return programExternalIds;
	}
	public List<MDUOrderSource> getMDUProperties(String mdu_url,int programId) throws IOException {
		List<MDUOrderSource>  mduOrderSource = null;
		if (!Utils.isBlank(mdu_url)){
			String urlString = mdu_url;
			urlString=urlString+"programId="+programId;
			logger.info("urlString="+urlString);
			HttpURLConnection connection = null;
			InputStream responseBodyStream = null;
			try{
			URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(GET);
			byte buffer[] = new byte[8192];
			int read = 0;
			connection.connect();
			responseBodyStream = connection.getInputStream();
			StringBuffer responseBody = new StringBuffer();
			while ((read = responseBodyStream.read(buffer)) != -1) {
				responseBody.append(new String(buffer, 0, read,"UTF-8"));
			}
			mduOrderSource = getMDUPropertiesData(responseBody.toString());
			logger.info("mduOrderSource_value"+mduOrderSource.size());
			//logger.info("ProviderDetails="+ProgramExternalIds);
			}catch (IOException e) {
				throw e;
			}finally{
				if(connection!=null){
					connection.disconnect();
				}
				if(responseBodyStream!=null){
					responseBodyStream.close();
				}
			}
		}
		return mduOrderSource;
	}
	
	/**
	 * MDU propeies JSON 
	 * 
	 * @param mduPropertyData
	 * @return ChannelLineupVO
	 */
	private List<MDUOrderSource> getMDUPropertiesData(String mduPropertyData) throws UnsupportedEncodingException {
		logger.debug("mduPropertyData"+mduPropertyData);
		try {
			return new Gson().fromJson(mduPropertyData, new TypeToken<List<MDUOrderSource>>(){}.getType());
		} 
		catch (Exception ex) {
			logger.error("error_in_RESTClient",ex);
		} 
		return null;		
	}

	public  Map<String,CustomerVO> getDataExchangeByCallingAddress(String partnerSpecificDataName, String callingAddress, String URL_ENDPOINT, HttpServletRequest request) throws IOException {
		Map<String,CustomerVO> details = new HashMap<String, CustomerVO>();
		if (!Utils.isBlank(callingAddress)){
			logger.info("urlString="+URL_ENDPOINT);
			String urlString = URL_ENDPOINT;
			urlString = urlString.replaceAll("\\$name\\$", partnerSpecificDataName);
			urlString = urlString.replaceAll("value=", "&"+"value=");
			urlString = urlString.replaceAll("\\$value\\$", callingAddress);
			logger.info("urlString="+urlString);
			HttpURLConnection connection = null;
			InputStream responseBodyStream = null;
			try{

				URL url = new URL(urlString);

				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod(GET);

				byte buffer[] = new byte[8192];
				int read = 0;

				connection.connect();

				responseBodyStream = connection.getInputStream();
				StringBuffer responseBody = new StringBuffer();
				while ((read = responseBodyStream.read(buffer)) != -1) {
					responseBody.append(new String(buffer, 0, read,"UTF-8"));
				}

				details = generateCustomerInfoBasicByCallingAddress(responseBody.toString(), callingAddress, request);

				logger.info("customerDetails="+details);
			}catch (IOException e) {
				throw e;
			}finally{
				if(connection!=null){
					connection.disconnect();
				}
				if(responseBodyStream!=null){
					responseBodyStream.close();
				}
			}
		}
		return details;
	}

	private Map<String,CustomerVO> generateCustomerInfoBasicByCallingAddress(String response, String callingAddress, HttpServletRequest request) throws UnsupportedEncodingException {

		CustomerVO customerVO = new CustomerVO();

		Map<String,CustomerVO> details = new HashMap<String, CustomerVO>();
		StringReader sr = null;
		ConsumerVO dtConsumer = null;
		JAXBContext transientContainerJxbContext = null;
		try {
			
			if (request != null && request.getSession() != null && request.getSession().getAttribute("transientConsumerView") != null){
				transientContainerJxbContext = (JAXBContext) request.getSession().getAttribute("transientConsumerView");	
			}else{
				transientContainerJxbContext = JAXBContext.newInstance(ConsumerViewLites.class);
				request.getSession().setAttribute("transientConsumerView",transientContainerJxbContext);
			}
			
			sr = new StringReader(response);
			Unmarshaller unmarshaller = transientContainerJxbContext.createUnmarshaller();
			JAXBElement<ConsumerVO> b = unmarshaller.unmarshal(new StreamSource(sr), ConsumerVO.class);
			dtConsumer = b.getValue();
			
		} 
		catch (JAXBException e) {
			logger.warn("error_while_unmarshaling_response",e);
		}
		catch (Exception ex) {
			logger.warn("error_in_RESTClient",ex);
		} 
		finally {
			sr.close();
		}
		if(dtConsumer!=null) {
			//this is only for DT harness injection of DT xml data
			logger.info("CustomerVO="+dtConsumer);

			//use the DtConsumer coming from DT harness 
			
			customerVO.setDtNameFirst(StringCaseUtil.toCamelCase(dtConsumer.getDtNameFirst(),true));
			customerVO.setDtNameMiddle(StringCaseUtil.toCamelCase(dtConsumer.getDtNameMiddle(),true));
			customerVO.setDtNameLast(StringCaseUtil.toCamelCase(dtConsumer.getDtNameLast(),true));
			customerVO.setDtPartnerAccountId(dtConsumer.getDtPartnerAccountId());
			customerVO.setDtSequenceNum(String.valueOf(dtConsumer.getDtSequenceNum()));
			customerVO.setDtPartner(dtConsumer.getDtPartner());
			customerVO.setDtGasRequestedStartDate(dtConsumer.getDtGasRequestedStartDate());
			customerVO.setDtRequestedStartDate(dtConsumer.getDtRequestedStartDate());
			customerVO.setDtEmail(dtConsumer.getDtEmail());
			customerVO.setDtConfirmedEmailAddress(dtConsumer.getDtConfirmedEmailAddress());
			customerVO.setDtGasReqStartTimeBegin(dtConsumer.getDtGasReqStartTimeBegin());
			customerVO.setDtGasReqStartTimeEnd(dtConsumer.getDtGasReqStartTimeEnd());
			customerVO.setDtReqStartTimeBegin(dtConsumer.getDtReqStartTimeBegin());
			customerVO.setDtReqStartTimeEnd(dtConsumer.getDtReqStartTimeEnd());
			
			List<DtPhoneType> dt_phone =  new ArrayList<DtPhoneType>();
			dt_phone = dtConsumer.getDt_phone();
			for(DtPhoneType dtPhoneType: dt_phone){
				if(dtPhoneType.getDtTelephoneType().equals("1")){
					customerVO.setDtTelephoneNum(dtPhoneType.getDtTelephoneNum());
				}
			}
			List<DtAddr> dt_address = new ArrayList<DtAddr>();
			dt_address = dtConsumer.getDt_address();
			for(DtAddr dtAddr: dt_address){
				if(dtAddr.getDtAddressType().equals("1")){
					customerVO.setDtSaCity(StringCaseUtil.toCamelCase(dtAddr.getDtAddressCity(),false));
					customerVO.setDtSaState(dtAddr.getDtAddressState());
					customerVO.setDtSaStreet1(StringCaseUtil.toCamelCase(dtAddr.getDtAddressStreet1(),false));
					customerVO.setDtSaStreet2(StringCaseUtil.toCamelCase(dtAddr.getDtAddressStreet2(),false));
					customerVO.setDtSaZip(dtAddr.getDtAddressZip());
				}
			}
			dt_address = dtConsumer.getDt_address();
			customerVO.setDtNamePrefix(dtConsumer.getDtNamePrefix());
			customerVO.setDtNameSuffix(dtConsumer.getDtNameSuffix());
			customerVO.setDtAgentId(dtConsumer.getDtAgentId());
			customerVO.setDtConsumerSsn(dtConsumer.getDtConsumerSsn());
			if(dtConsumer.getDtCreated()!= null){
			    customerVO.setDtCreated(String.valueOf(dtConsumer.getDtCreated()));
			}
			customerVO.setPartnerSpecificDataMap(dtConsumer.getPartnerSpecificDataMap());
		}
		if(dtConsumer.getDtSequenceNum() != null){
		    details.put(callingAddress, customerVO);
		}
		return details;
	}
	
	private Map<String,CustomerVO> generateCustomerInfoBasic(String response) throws UnsupportedEncodingException {

		CustomerVO customerVO = new CustomerVO();

		Map<String,CustomerVO> details = new HashMap<String, CustomerVO>();
		StringReader sr = null;
		ConsumerVO dtConsumer = null;
		try {
			JAXBContext transientContainerJxbContext = JAXBContext.newInstance(ConsumerViewLites.class);
			sr = new StringReader(response);
			Unmarshaller unmarshaller = transientContainerJxbContext.createUnmarshaller();
			JAXBElement<ConsumerVO> b = unmarshaller.unmarshal(new StreamSource(sr), ConsumerVO.class);
			dtConsumer = b.getValue();
			
		} 
		catch (JAXBException e) {
			logger.warn("error_while_unmarshaling_response",e);
		}
		catch (Exception ex) {
			logger.warn("error_in_RESTClient",ex);
		} 
		finally {
			sr.close();
		}
		if(dtConsumer!=null) {
			//this is only for DT harness injection of DT xml data
			logger.info("CustomerVO="+dtConsumer);

			//use the DtConsumer coming from DT harness 
			
			customerVO.setDtNameFirst(StringCaseUtil.toCamelCase(dtConsumer.getDtNameFirst(),true));
			customerVO.setDtNameMiddle(StringCaseUtil.toCamelCase(dtConsumer.getDtNameMiddle(),true));
			customerVO.setDtNameLast(StringCaseUtil.toCamelCase(dtConsumer.getDtNameLast(),true));
			customerVO.setDtPartnerAccountId(dtConsumer.getDtPartnerAccountId());
			customerVO.setDtSequenceNum(String.valueOf(dtConsumer.getDtSequenceNum()));
			customerVO.setDtPartner(dtConsumer.getDtPartner());
			customerVO.setDtGasRequestedStartDate(dtConsumer.getDtGasRequestedStartDate());
			customerVO.setDtRequestedStartDate(dtConsumer.getDtRequestedStartDate());
			customerVO.setDtEmail(dtConsumer.getDtEmail());
			customerVO.setDtConfirmedEmailAddress(dtConsumer.getDtConfirmedEmailAddress());
			customerVO.setDtGasReqStartTimeBegin(dtConsumer.getDtGasReqStartTimeBegin());
			customerVO.setDtGasReqStartTimeEnd(dtConsumer.getDtGasReqStartTimeEnd());
			customerVO.setDtReqStartTimeBegin(dtConsumer.getDtReqStartTimeBegin());
			customerVO.setDtReqStartTimeEnd(dtConsumer.getDtReqStartTimeEnd());
			
			List<DtPhoneType> dt_phone =  new ArrayList<DtPhoneType>();
			dt_phone = dtConsumer.getDt_phone();
			for(DtPhoneType dtPhoneType: dt_phone){
				if(dtPhoneType.getDtTelephoneType().equals("1")){
					customerVO.setDtTelephoneNum(dtPhoneType.getDtTelephoneNum());
				}
			}
			List<DtAddr> dt_address = new ArrayList<DtAddr>();
			dt_address = dtConsumer.getDt_address();
			for(DtAddr dtAddr: dt_address){
				if(dtAddr.getDtAddressType().equals("1")){
					customerVO.setDtSaCity(StringCaseUtil.toCamelCase(dtAddr.getDtAddressCity(),false));
					customerVO.setDtSaState(dtAddr.getDtAddressState());
					customerVO.setDtSaStreet1(StringCaseUtil.toCamelCase(dtAddr.getDtAddressStreet1(),false));
					customerVO.setDtSaStreet2(StringCaseUtil.toCamelCase(dtAddr.getDtAddressStreet2(),false));
					customerVO.setDtSaZip(dtAddr.getDtAddressZip());
				}
			}
			dt_address = dtConsumer.getDt_address();
			customerVO.setDtNamePrefix(dtConsumer.getDtNamePrefix());
			customerVO.setDtNameSuffix(dtConsumer.getDtNameSuffix());
			customerVO.setDtAgentId(dtConsumer.getDtAgentId());
			customerVO.setDtConsumerSsn(dtConsumer.getDtConsumerSsn());
			if(dtConsumer.getDtCreated()!= null){
			    customerVO.setDtCreated(String.valueOf(dtConsumer.getDtCreated()));
			}
			customerVO.setPartnerSpecificDataMap(dtConsumer.getPartnerSpecificDataMap());
		}
		if(dtConsumer.getDtSequenceNum() != null){
		    details.put(dtConsumer.getDtSequenceNum().toString(), customerVO);
		}
		return details;
	}

}

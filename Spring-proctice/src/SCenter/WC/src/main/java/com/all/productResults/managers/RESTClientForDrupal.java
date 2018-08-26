package com.A.productResults.managers;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.A.ui.service.config.ConfigRepo;
import com.A.ui.util.Utils;


/**
 * @author Nanda Kishore Palapala
 *
 */

public enum RESTClientForDrupal {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(RESTClientForDrupal.class);
	private static String creditScoreURL = ConfigRepo.getString("*.creditScoreUrl") == " " ? " " : ConfigRepo.getString("*.creditScoreUrl");
	private static final String GET = "GET";
	private static final String POST = "POST";

	public  String getDialoguesFromDrupal(String referralId, String URL_ENDPOINT) {
		StringBuffer responseBody = new StringBuffer();
		String returnStr = null;
		if (!isBlank(referralId)){
			StringBuffer urlString = new StringBuffer(URL_ENDPOINT);
			urlString.append(referralId+".html");
			logger.info("urlString="+urlString);
			HttpURLConnection connection =  null;
			InputStream responseBodyStream = null;
			try{
				URL url = new URL(urlString.toString());
				connection = (HttpURLConnection) url.openConnection();
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
				returnStr = generateDialogues(responseBody.toString());
				logger.info("RESTClientForDrupal_Done");
			}catch (IOException e) {
				logger.warn("No Dialogues Found in Drupal");
			}catch (Exception e) {
				logger.warn("No Dialogues Found in Drupal"+e.getMessage());
			}
			finally{
				if(connection!=null){
					connection.disconnect();
				}
				if(responseBodyStream!=null){
					try {
						responseBodyStream.close();
					} 
					catch (IOException e) {
						logger.warn("No Dialogues Found in Drupal"+e.getMessage());
					}
				}
			}
		}
		return returnStr;
	}

	
	private String generateDialogues(String response){
		String res  = null;
		try{
			Document doc = Jsoup.parse(response);
			Elements  element = doc.getElementsByClass("taxonomy-term-description");
			if(element != null){
				res = element.html();  
			}
		}catch (Exception e) {
			logger.warn("No Dialogues Found in Drupal");
		}
		return res;
	}

	public String generateDialoguesAll(String response){
		String res  = null;
		try{
			Document doc = Jsoup.parse(response);
			Element  element = doc.getElementById("AllDependencyMap_Supplier_Selection_Data");
			if(element != null){
				res = element.html(); 
				res = StringEscapeUtils.unescapeHtml(escapeSpecialCharacters(res));
			}
		}catch (Exception e) {
			logger.warn("No Dialogues Found in Drupal");
		}
		return res;
	}

	public String generateDialoguesEnable(String response){
		String res  = null;
		try{
			Document doc = Jsoup.parse(response);
			Element  element = doc.getElementById("EnableDependencyMap_Supplier_Selection_Data");
			if(element != null){
				res = element.html();  
				res = StringEscapeUtils.unescapeHtml(escapeSpecialCharacters(res));
			}
		}catch (Exception e) {
			logger.warn("No Dialogues Found in Drupal");
		}
		return res;
	}




	public String generateDialoguesDisable(String response){
		String res  = null;
		try{
			Document doc = Jsoup.parse(response);
			Element  element = doc.getElementById("DisableDependencyMap_Supplier_Selection_Data");
			if(element != null){
				res = element.html(); 
				res = StringEscapeUtils.unescapeHtml(escapeSpecialCharacters(res));
			}
		}catch (Exception e) {
			logger.warn("No Dialogues Found in Drupal");
		}
		return res;
	}

	public String generateSelectedDialogues(String response,String divId){
		String res  = null;
		try{
			Document doc = Jsoup.parse(response);
			Element  element = doc.getElementById(divId);
			if(element != null){
				res = element.html(); 
				res = StringEscapeUtils.unescapeHtml(escapeSpecialCharacters(res));
			}
		}catch (Exception e) {
			logger.warn("No Dialogues Found in Drupal");
		}
		return res;
	}

	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}
	public String escapeSpecialCharacters(String str){
		if(str!=null){
			str = str.replaceAll("&amp;", "&");
			str = str.replaceAll("'", "&#39;");
			str = str.replaceAll("&quot;", "&#34;");

			str = str.replaceAll("&#10;", "&nbsp;");
			str = str.replaceAll("\u00a0", "&nbsp;");
			//this is for - mark
			str = str.replaceAll("\u2013", "&#8211;");
			//this is for trademark
			str = str.replaceAll("\u2122", "&#8482;");

			//this is for Copyright mark
			str = str.replaceAll("\u00a9", "&#169;");
			//this is for Registered trade mark
			str = str.replaceAll("\u00ae", "&#174;");

			//this is for bullet point
			str = str.replaceAll("\u2022", "&#8226;");
			//this is for exclamation point
			str = str.replaceAll("\u0021", "&#33;");
			//this is for colon
			str = str.replaceAll("\u003a", "&#58;");
			//this is for inverted question mark
			str = str.replaceAll("\u00bf", "&#191;");

			//this is for right single quotation mark
			str = str.replaceAll("\u2019", "&#8217;");
			//this is for left single quotation mark
			str = str.replaceAll("\u2018", "&#8216;");
			//this is for left double quotation mark
			str = str.replaceAll("\u201C", "&#8220;");
			//this is for right double quotation mark
			str = str.replaceAll("\u201D", "&#8221;");

			//this is for left tag
			str = str.replaceAll("&lt;", "<");

			//this is for right tag
			str = str.replaceAll("&gt;", ">");
		}
		return str;
	}

	public  String getHtmlConententFromDrupal(String URL_ENDPOINT) {
		StringBuffer responseBody = new StringBuffer();
		String returnStr = null;
		if (!isBlank(URL_ENDPOINT)){
			StringBuffer urlString = new StringBuffer(URL_ENDPOINT);
			logger.info("urlString="+urlString);
			HttpURLConnection connection =  null;
			InputStream responseBodyStream = null;
			try{

				URL url = new URL(urlString.toString());
				connection = (HttpURLConnection) url.openConnection();
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
				returnStr = generateDialogues(responseBody.toString());
				if(Utils.isBlank(returnStr)){
					returnStr = responseBody.toString();
				}
				returnStr = removeScriptContentFromHtmlContent(returnStr,"global.js");
				logger.info("RESTClientForDrupal_Done");
			}catch (IOException e) {
				logger.warn("No Dialogues Found in Drupal");
			}catch (Exception e) {
				logger.warn("No Dialogues Found in Drupal"+e.getMessage());
			}finally{
				if(connection!=null){
					connection.disconnect();
				}
				if(responseBodyStream!=null){
					try {
						responseBodyStream.close();
					} catch (IOException e) {
						logger.warn("No Dialogues Found in Drupal"+e.getMessage());
					}
				}
			}
		}
		return returnStr;
	}

	public String removeScriptContentFromHtmlContent(String response, String scriptFileName){
		String res  = null;
		try{
			Document doc = Jsoup.parse(response);
			Elements elements = doc.getElementsByTag("script");
			for(Element element:elements){
				if(element.toString().contains(scriptFileName)){
					element.remove();
				}
			}
			res = doc.html();
		}catch (Exception e) {
			logger.warn("No Dialogues Found in Drupal");
		}
		return res;
	}

	public String getCreditScore(String zipCode){
		String creditRes = null;
		try{
			creditRes = restCallCreditScore(zipCode);
			JSONObject json = new JSONObject(creditRes);
			return json.getString("score");
		}catch(Exception e){
			if(Utils.isBlank(creditRes)){
				logger.error("error_occured_while_get_creditscore",e);
			}else{
				logger.info("restCallCreditScore="+creditRes);
			}
		}
		return "";
	}
	private String restCallCreditScore(String zipcode) throws Exception {
		StringBuffer responseBody = new StringBuffer();
		if (!Utils.isBlank(creditScoreURL)) {
			String zipCreditScoreURL = creditScoreURL.replaceAll("\\$zipcode\\$", zipcode);
			logger.info("zipCreditScoreURL=" + zipCreditScoreURL);
			HttpURLConnection connection = null;
			InputStream responseBodyStream = null;
			try {
				URL url = new URL(zipCreditScoreURL);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod(GET);
				byte buffer[] = new byte[8192];
				int read = 0;
				connection.connect();
				responseBodyStream = connection.getInputStream();
				if (responseBodyStream != null) {
					while ((read = responseBodyStream.read(buffer)) != -1) {
						responseBody.append(new String(buffer, 0, read, "UTF-8"));
					}
				}
			} catch (Exception e) {
				throw e;
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
				if (responseBodyStream != null) {
					responseBodyStream.close();
				}
			}
		}
		return responseBody.toString();
	}
	
	public String getInum(String phoneId, String pauseAndResumeURL){
		String inumResp = null;
		try{
			inumResp = restCallForGetInum(phoneId, pauseAndResumeURL);
			JSONObject json = new JSONObject(inumResp);
			return json.getString("inum");
		}catch(Exception e){
			if(Utils.isBlank(inumResp)){
				logger.error("error_occured_while_getInum",e);
			}else{
				logger.info("restCallforInum="+inumResp);
			}
		}
		return "";
	}
	
	private String restCallForGetInum(String phoneId, String pauseAndResumeURL) throws Exception {
		StringBuffer responseBody = new StringBuffer();
		if (!Utils.isBlank(pauseAndResumeURL)) {
			pauseAndResumeURL = pauseAndResumeURL.replace("pauseOrResumeByPhoneIdAndAction", "getInum");
			String urlString = pauseAndResumeURL+"?agentId="+phoneId;
			logger.info("getInumURLString="+urlString);
			HttpURLConnection connection = null;
			InputStream responseBodyStream = null;
			try {
				URL url = new URL(urlString);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod(GET);
				byte buffer[] = new byte[8192];
				int read = 0;
				connection.connect();
				responseBodyStream = connection.getInputStream();
				if (responseBodyStream != null) {
					while ((read = responseBodyStream.read(buffer)) != -1) {
						responseBody.append(new String(buffer, 0, read, "UTF-8"));
					}
				}
			} catch (Exception e) {
				logger.error("error_occured_while_getInum",e);
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
				if (responseBodyStream != null) {
					responseBodyStream.close();
				}
			}
		}
		return responseBody.toString();
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
	
	public void postVZSaveSmartCartProduct(String orderId, String guid, String URL_ENDPOINT) throws Exception {
		StringBuffer urlString = new StringBuffer(URL_ENDPOINT);

		if (!Utils.isBlank(orderId)) {
			urlString.append("?orderId=" + orderId);
		}
		if (!Utils.isBlank(guid)) {
			urlString.append("&guid="
					+ URLEncoder.encode(guid, "UTF-8"));
		}
		
		logger.info("urlString-postVZSaveSmartCartProduct="+urlString);
		connectVZMicroService(urlString.toString());
	}
	public void connectVZMicroService(String URL_ENDPOINT) throws Exception {
		HttpURLConnection connection =  null;
		InputStream responseBodyStream = null;
		try{
			URL url = new URL(URL_ENDPOINT);
			if (URL_ENDPOINT != null && URL_ENDPOINT.contains("https:")){
				connection = (HttpsURLConnection) url.openConnection();	
			}else{
				connection = (HttpURLConnection) url.openConnection();
			}
			connection.setRequestMethod(POST);
			connection.connect();
			connection.getResponseMessage();
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
	}
}

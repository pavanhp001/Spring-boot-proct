package com.AL.ui.service.workflow.intent;

import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.AL.ui.service.workflow.Intent;
import com.AL.ui.service.workflow.stat.StaticIntentSteps;

public enum IntentInitial {

	INSTANCE;
	
	private static Logger logger = Logger.getLogger(IntentInitial.class);
	
	/**
	 * obtains CKOInput, sessionID from request, set them to intent and returned
	 * @param intentStep
	 * @param request
	 * @return Intent
	 */
	public Intent process(StaticIntentSteps intentStep, HttpServletRequest request) {
		
		final Intent intent = new Intent(intentStep);
		
		String CKOInput = request.getParameter("CKOInput");
		String sessionId = request.getSession().getId();
		
		if(CKOInput!=null && CKOInput.indexOf("dataLayer")!=-1)
			CKOInput= splitDataLayerFromJson(CKOInput, request);
		
		intent.getExtras().put("CKOInput", CKOInput);
		intent.getExtras().put("sessionId", sessionId);
		
		return intent;
		
	}
	
	public boolean validateIntent(Intent intent) {
		return Boolean.TRUE;
	}
	
	@SuppressWarnings("unchecked")
	public String splitDataLayerFromJson(String CKOInput,HttpServletRequest request){
		  JSONObject mainJson = new JSONObject();
		  JSONParser jsonParser= new JSONParser();
		  try{
		   mainJson = (JSONObject)jsonParser.parse(CKOInput);
		   JSONObject CKO = (JSONObject) mainJson.get("CKO");
		   JSONObject params = (JSONObject) CKO.get("params");
		   JSONObject dataLayer =new JSONObject();
		   JSONArray stringJsonArrayNew=new JSONArray();
		   JSONArray stringJsonArray = (JSONArray) params.get("string");
		   for (int i = 0; i < stringJsonArray.size(); i++) {
		    if(stringJsonArray.get(i)!=null && stringJsonArray.get(i).toString().indexOf("dataLayer")!=-1){
		     dataLayer =(JSONObject) stringJsonArray.get(i);
		     dataLayer=(JSONObject)dataLayer.get("dataLayer");
		     String decodedDataLayer=URLDecoder.decode(dataLayer.toString(), "UTF-8");
		     dataLayer=(JSONObject) jsonParser.parse(decodedDataLayer);
		    }else{
		     String typeObject = (String) stringJsonArray.get(i);
		     if(typeObject!=null)
		      stringJsonArrayNew.add(i, typeObject);
		    }
		   }
		   request.getSession().setAttribute("dataLayer", dataLayer);
		   params.put("string",stringJsonArrayNew);
		   CKO.put("params", params);
		   mainJson.put("CKO", CKO);
		   logger.info("splitDataLayerFromJson_completed"); 
		  }catch(Exception e){
		   logger.error("error_splitDataLayerFromJson"+e);
		   e.printStackTrace();
		  }
		  return mainJson.toString();
	}
}

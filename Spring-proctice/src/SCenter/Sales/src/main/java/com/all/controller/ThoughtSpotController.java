package com.AL.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AL.ui.service.V.impl.ThoughtspotCacheService;
import com.AL.ui.vo.AgentVO;

@Controller
public class ThoughtSpotController extends BaseController {

	@Autowired

	@Value("${buildVersion}")
	private String buildVersion;

	private static final Logger logger = Logger.getLogger(ThoughtSpotController.class);
	
	@SuppressWarnings("rawtypes")
	
	@RequestMapping(value = "/getThoughtSpotDataFromCurl")
	public ModelAndView getThoughtSpotDataFromCurl(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("thoughtSpot");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		
		String inputJson1 = request.getParameter("inputJson");
		
		logger.info("saveThoughtSpotDataToCache inside data");

		AgentVO agentVo = new AgentVO();
		List<AgentVO> voList = new ArrayList<AgentVO>();
		Map<String, List<AgentVO>> callCentersMap = new HashMap<String, List<AgentVO>>();
		Map<String, List<AgentVO>> regionMap = new HashMap<String, List<AgentVO>>();
		List<AgentVO> sortedAgentList = new ArrayList<AgentVO>();

		try{
			logger.info("inputJson1 = " +inputJson1);
			JSONObject json = new JSONObject(inputJson1);

			JSONObject jsonobj = new JSONObject();
			JSONArray jsonArray = new JSONArray();

			Iterator keys = json.keys();
			//System.out.println("keys ="+keys);
			while(keys.hasNext()) {
				// loop to get the dynamic key
				String currentDynamicKey = (String)keys.next();
				logger.info("dynamicKey ="+currentDynamicKey);
				jsonobj = json.getJSONObject(currentDynamicKey);
				jsonArray = jsonobj.getJSONArray("data");
				logger.info("dynamicValue ="+jsonArray);
			}

			//System.out.println("valie = " +c);
			for (int i = 0 ; i < jsonArray.length(); i++) {
				JSONArray e =  (JSONArray) jsonArray.get(i);
				agentVo = new AgentVO();
				for (int j = 0 ; j < e.length(); j++) {
					if (e.get(0) != null && !e.get(0).toString().equalsIgnoreCase("null")){
						agentVo.setAgentId(String.valueOf( e.get(0)));
						agentVo.setCallCenter(String.valueOf(  e.get(1)));
						agentVo.setAgentName(String.valueOf(  e.get(2)));
						agentVo.setRegion(String.valueOf(  e.get(3)));
						agentVo.setGCRevenue( Double.parseDouble(e.get(4).toString()));
						agentVo.setTotalCallsAnswered( Integer.parseInt(e.get(5).toString()));	
					}
				}
				voList.add(agentVo);
			}
			for (AgentVO list : voList){
				if (list.getAgentId() != null){
					sortedAgentList.add(list);
				}
			}
			sortedAgentList = sort(sortedAgentList);
			for (int i = 0 ; i < sortedAgentList.size(); i++){
				sortedAgentList.get(i).setEnterpriseRank(i +1);
			}
			
			if (sortedAgentList != null){
				ThoughtspotCacheService.INSTANCE.store("Enterprise",sortedAgentList);
				ThoughtspotCacheService.INSTANCE.store("TSCacheDateTime",date);
			}

			for (AgentVO list : sortedAgentList){
				if (callCentersMap.get(list.getCallCenter()) == null){
					List<AgentVO> agentVo1 = new ArrayList<AgentVO>();
					agentVo1.add(list);
					callCentersMap.put(list.getCallCenter(), agentVo1);	
				}else{
					List<AgentVO> agentVo1 = callCentersMap.get(list.getCallCenter());
					agentVo1.add(list);
					callCentersMap.put(list.getCallCenter(), agentVo1);
				}
				if (regionMap.get(list.getRegion()) == null){
					List<AgentVO> agentVo1 = new ArrayList<AgentVO>();
					agentVo1.add(list);
					regionMap.put(list.getRegion(), agentVo1);	
				}else{
					List<AgentVO> agentVo1 = regionMap.get(list.getRegion());
					agentVo1.add(list);
					regionMap.put(list.getRegion(), agentVo1);
				}
			}
			for (Entry<String, List<AgentVO>> entry : callCentersMap.entrySet()) {
				List<AgentVO> list = sort(entry.getValue());
				for (int i = 0 ; i < list.size(); i++){
					list.get(i).setCallCenterRank(i +1);
				}
			}	
			for (Entry<String, List<AgentVO>> entry : regionMap.entrySet()) {
				List<AgentVO> list = sort(entry.getValue());
				for (int i = 0 ; i < list.size(); i++){
					list.get(i).setRegionRank(i +1);
				}
			}	

			if (callCentersMap != null){
				ThoughtspotCacheService.INSTANCE.store("callCenterData",callCentersMap);
			}
			if (regionMap != null){
				ThoughtspotCacheService.INSTANCE.store("regionData",regionMap);
			}

			logger.info("callCentersMap = "+callCentersMap.toString());
			logger.info("regionMap = "+regionMap.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getThoughtSpotData")
	public ModelAndView getTSData(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("thoughtSpot");
		return mav;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/saveThoughtSpotDataToCache")
	public @ResponseBody String getThoughtSpotData(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("thoughtSpot");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));

		String inputJson1 = request.getParameter("inputJson");

		logger.info("saveThoughtSpotDataToCache inside data");

		AgentVO agentVo = new AgentVO();
		List<AgentVO> voList = new ArrayList<AgentVO>();
		Map<String,List<AgentVO>> thoughtSpotTopTenDataMap = new HashMap<String,List<AgentVO>>();
		Map<String,List<AgentVO>> enterpriseMap = new HashMap<String,List<AgentVO>>();
		Map<String, List<AgentVO>> callCentersMap = new HashMap<String, List<AgentVO>>();
		Map<String, List<AgentVO>> regionMap = new HashMap<String, List<AgentVO>>();
		Map<String, Map<String, List<AgentVO>>> thoughtSpotDataMap = new HashMap<String, Map<String, List<AgentVO>>>();
		Map<String,AgentVO> agentDataMap = new HashMap<String,AgentVO>();

		Set<String> callCenterSet = new HashSet<String>();
		Set<String> regionSet = new HashSet<String>();

		List<AgentVO> sortedAgentList = new ArrayList<AgentVO>();

		try{
			logger.info("inputJson1 = " +inputJson1);
			JSONObject json = new JSONObject(inputJson1);

			JSONObject jsonobj = new JSONObject();
			JSONArray jsonArray = new JSONArray();

			Iterator keys = json.keys();
			//System.out.println("keys ="+keys);
			while(keys.hasNext()) {
				// loop to get the dynamic key
				String currentDynamicKey = (String)keys.next();
				logger.info("dynamicKey ="+currentDynamicKey);
				jsonobj = json.getJSONObject(currentDynamicKey);
				jsonArray = jsonobj.getJSONArray("data");
				logger.info("dynamicValue ="+jsonArray);
			}

			//System.out.println("valie = " +c);
			for (int i = 0 ; i < jsonArray.length(); i++) {
				JSONArray e =  (JSONArray) jsonArray.get(i);
				agentVo = new AgentVO();
				for (int j = 0 ; j < e.length(); j++) {
					if (e.get(0) != null && !e.get(0).toString().equalsIgnoreCase("null")){
						agentVo.setAgentId(String.valueOf( e.get(0)));
						agentVo.setCallCenter(String.valueOf(  e.get(1)));
						agentVo.setAgentName(String.valueOf(  e.get(2)));
						agentVo.setRegion(String.valueOf(  e.get(3)));
						agentVo.setGCRevenue( Double.parseDouble(e.get(4).toString()));
						agentVo.setTotalCallsAnswered( Integer.parseInt(e.get(5).toString()));
					}
				}
				voList.add(agentVo);
			}
			for (AgentVO list : voList){
				if (list.getAgentId() != null){
					sortedAgentList.add(list);
				}
			}
			sortedAgentList = sort(sortedAgentList);
			for (int i = 0 ; i < sortedAgentList.size(); i++){
				sortedAgentList.get(i).setEnterpriseRank(i +1);
			}

			if (sortedAgentList != null){
				ThoughtspotCacheService.INSTANCE.store("Enterprise",sortedAgentList);
				ThoughtspotCacheService.INSTANCE.store("TSCacheDateTime",dateFormat.format(date));
				thoughtSpotTopTenDataMap.put("enterpriseData", sortedAgentList);
				thoughtSpotDataMap.put("enterpriseData", thoughtSpotTopTenDataMap);

			}
			for (int i = 0 ; i < sortedAgentList.size(); i++){
				agentVo = sortedAgentList.get(i);
				if (agentVo.getCallCenter()!= null){
					callCenterSet.add(agentVo.getCallCenter());
				}
				if(agentVo.getRegion() != null){
					regionSet.add(agentVo.getRegion());
				}	
				agentDataMap.put(agentVo.getAgentId(), agentVo);
			}
			List<AgentVO> enterpriseList = new ArrayList<AgentVO>();
			for(AgentVO agentVO : sortedAgentList){
				enterpriseList.add(agentVO);
				if(enterpriseList.size() == 10){
					break;
				}
			}
			
			if(!enterpriseList.isEmpty()){
				enterpriseMap.put("enterprise", enterpriseList);
			}
			for(String callCenter : callCenterSet){
				int rank = 0;
				int agentCallCenterRank = 0;
				List<AgentVO> callCenterVoList = new ArrayList<AgentVO>();
				for (AgentVO agentVo1 : sortedAgentList){
					if(agentVo1.getCallCenter().equalsIgnoreCase(callCenter)){
						++rank;
						agentVo1.setCallCenterRank(rank);
						callCenterVoList.add(agentVo1);
						if(rank == 10){
							break;
						}
					}
				}
				if(!callCenterVoList.isEmpty()){
					callCentersMap.put(callCenter, callCenterVoList);
				}
				for (AgentVO agentVo2 : sortedAgentList){
					if(agentVo2.getCallCenter().equalsIgnoreCase(callCenter)){
						++agentCallCenterRank;
						if(agentDataMap.get(agentVo2.getAgentId())!= null){
							agentDataMap.get(agentVo2.getAgentId()).setCallCenterRank(agentCallCenterRank);
						}
					}
				}
			}

			for(String region : regionSet){
				int rank = 0;
				int agentRegionRank = 0;
				List<AgentVO> regionAgentVoList = new ArrayList<AgentVO>();
				for (AgentVO agentVo1 : sortedAgentList){
					if(agentVo1.getRegion().equalsIgnoreCase(region)){
						++rank;
						agentVo1.setRegionRank(rank);
						regionAgentVoList.add(agentVo1);
						if(rank == 10){
							break;
						}
					}
				}
				if(!regionAgentVoList.isEmpty()){
					regionMap.put(region, regionAgentVoList);
				}
				for (AgentVO agentVo2 : sortedAgentList){
					if(agentVo2.getRegion().equalsIgnoreCase(region)){
						++agentRegionRank;
						if(agentDataMap.get(agentVo2.getAgentId())!= null){
							agentDataMap.get(agentVo2.getAgentId()).setRegionRank(agentRegionRank);
						}
					}
				}
			}
			if (agentDataMap != null){
				//ThoughtspotCacheService.INSTANCE.store("callCenterData",callCentersMap);
				ThoughtspotCacheService.INSTANCE.store("agentDataMap",agentDataMap);
				logger.info("agentDataMap = "+agentDataMap.toString());
			}
			if (enterpriseMap != null){
				//ThoughtspotCacheService.INSTANCE.store("callCenterData",callCentersMap);
				thoughtSpotDataMap.put("enterpriseMap", enterpriseMap);
				logger.info("enterpriseMap = "+enterpriseMap.toString());
			}

			if (callCentersMap != null){
				//ThoughtspotCacheService.INSTANCE.store("callCenterData",callCentersMap);
				thoughtSpotDataMap.put("callCenterData", callCentersMap);
				logger.info("callCentersMap = "+callCentersMap.toString());
			}
			if (regionMap != null){
				//ThoughtspotCacheService.INSTANCE.store("regionData",regionMap);
				thoughtSpotDataMap.put("regionData", regionMap);
				logger.info("regionMap = "+regionMap.toString());
			}
			if(thoughtSpotDataMap != null){
				ThoughtspotCacheService.INSTANCE.store("thoughtSpotData",thoughtSpotDataMap);
			}

			logger.info("callCentersMap = "+callCentersMap.toString());
			logger.info("regionMap = "+regionMap.toString());

			logger.info("callcenterMap size = "+callCentersMap.size() +" :: regionMap size ="+regionMap.size() +" :: enterpriseMap size ="+enterpriseMap.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//return mav;
		return "";
	}


	public static List<AgentVO> sort(List<AgentVO> list){
		Collections.sort(list, new Comparator<AgentVO>() {
			public int compare(AgentVO o2, AgentVO o1) {
				Double tag1 = null;
				Double tag2 = null;

				if (o1.getGCRevenue() != null) {
					tag1 = o1.getGCRevenue();
				}
				if (o2.getGCRevenue() != null) {
					tag2 = o2.getGCRevenue();
				}
				if (tag1 == null && tag2 == null) {
					return 0;
				} else if (tag1 == null) {
					return -1;
				} else if (tag2 == null) {
					return 1;
				} else {
					return tag1.compareTo(tag2);
				}
			}
		});
		return list;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/getThoughtSpotDataFromCache")
	public @ResponseBody String getThoughtSpotDataFromCache(HttpServletRequest request)
	{
		logger.info("Before cache clear ==========");
		/*logger.info("Enterprice data="+ThoughtspotCacheService.INSTANCE.get("Enterprise"));
		logger.info("callCenterData data="+ThoughtspotCacheService.INSTANCE.get("callCenterData"));
		logger.info("regionData data="+ThoughtspotCacheService.INSTANCE.get("regionData"));*/
		logger.info("thoughtSpotData from cache="+ThoughtspotCacheService.INSTANCE.get("thoughtSpotData"));

		Map<String,List<AgentVO>> enterpriseDataMap = new HashMap<String,List<AgentVO>>();
		Map<String, List<AgentVO>> callCenterDataMap = new HashMap<String, List<AgentVO>>();
		Map<String, List<AgentVO>> regionDataMap = new HashMap<String, List<AgentVO>>();
		Map<String, List<AgentVO>> thoughtSpotDataMap =new HashMap<String, List<AgentVO>>();
		if(ThoughtspotCacheService.INSTANCE.get("thoughtSpotData") != null){
			thoughtSpotDataMap =(Map<String, List<AgentVO>>)ThoughtspotCacheService.INSTANCE.get("thoughtSpotData");
		}
		JSONObject thoughtSpotJsonObj = new JSONObject();
		try{
			if(thoughtSpotDataMap != null && !thoughtSpotDataMap.isEmpty()){
				if(thoughtSpotDataMap.get("enterpriseData") != null){
					enterpriseDataMap = (Map<String, List<AgentVO>>) thoughtSpotDataMap.get("enterpriseData");
					JSONArray enterpriseDataArray = new JSONArray();
					for(Map.Entry<String,List<AgentVO>> entry : enterpriseDataMap.entrySet()){
						List<AgentVO> agentVOList = entry.getValue();
						int i =0;
						if(i <= 10){
							for(AgentVO agentObect: agentVOList){
								i++;
								JSONObject obj = new JSONObject();
								obj.put("agentId", agentObect.getAgentId());
								obj.put("agentName", agentObect.getAgentName());
								obj.put("callCenter", agentObect.getCallCenter());
								obj.put("callCenterRank", agentObect.getCallCenterRank());
								obj.put("enterpriseRank", agentObect.getEnterpriseRank());
								obj.put("region", agentObect.getRegion());
								obj.put("regionRank", agentObect.getRegionRank());
								obj.put("points", agentObect.getGCRevenue());
								enterpriseDataArray.put(obj);
								if(i == 10){
									break;
								}
							}
						}
					}
					thoughtSpotJsonObj.put("enterpriseData", enterpriseDataArray);
				}
				if(thoughtSpotDataMap.get("callCenterData") != null){
					callCenterDataMap = (Map<String, List<AgentVO>>) thoughtSpotDataMap.get("callCenterData");	
					JSONArray callCenterDataArray = new JSONArray();
					int j =0;
					if(j <= 10){
						for(Map.Entry<String,List<AgentVO>> entry : callCenterDataMap.entrySet()){
							List<AgentVO> agentVOList = entry.getValue();
							j++;
							for(AgentVO agentObect: agentVOList){
								JSONObject obj = new JSONObject();
								obj.put("agentId", agentObect.getAgentId());
								obj.put("agentName", agentObect.getAgentName());
								obj.put("callCenter", agentObect.getCallCenter());
								obj.put("callCenterRank", agentObect.getCallCenterRank());
								obj.put("enterpriseRank", agentObect.getEnterpriseRank());
								obj.put("region", agentObect.getRegion());
								obj.put("regionRank", agentObect.getRegionRank());
								obj.put("points", agentObect.getGCRevenue());
								callCenterDataArray.put(obj);
								if(j == 10){
									break;
								}
							}
						}
					}
					thoughtSpotJsonObj.put("callCenterData", callCenterDataArray);
				}
				if(thoughtSpotDataMap.get("regionData") != null){
					regionDataMap = (Map<String, List<AgentVO>>) thoughtSpotDataMap.get("regionData");
					JSONArray regionDataArray = new JSONArray();
					for(Map.Entry<String,List<AgentVO>> entry : regionDataMap.entrySet()){
						List<AgentVO> agentVOList = entry.getValue();
						int k =0;
						if(k <= 10){
							for(AgentVO agentObect: agentVOList){
								k++;
								JSONObject obj = new JSONObject();
								obj.put("agentId", agentObect.getAgentId());
								obj.put("agentName", agentObect.getAgentName());
								obj.put("callCenter", agentObect.getCallCenter());
								obj.put("callCenterRank", agentObect.getCallCenterRank());
								obj.put("enterpriseRank", agentObect.getEnterpriseRank());
								obj.put("region", agentObect.getRegion());
								obj.put("regionRank", agentObect.getRegionRank());
								obj.put("points", agentObect.getGCRevenue());
								regionDataArray.put(obj);
								if(k == 10){
									break;
								}
							}
						}
					}
					thoughtSpotJsonObj.put("regionData", regionDataArray);
				}
				logger.info("thoughtSpotJsonObj="+thoughtSpotJsonObj.toString());
				//context.getFlowScope().put("thoughtSpotJsonObj", thoughtSpotJsonObj);
			}
		}catch(Exception e){
			logger.info("error occured while retrieving data from cache"+e);
		}

		/*boolean isCleared = ThoughtspotCacheService.INSTANCE.clearTsCache();

		logger.info("After cache clear ==========");
		logger.info("Enterprice data="+ThoughtspotCacheService.INSTANCE.get("Enterprise"));
		logger.info("callCenterData data="+ThoughtspotCacheService.INSTANCE.get("callCenterData"));
		logger.info("regionData data="+ThoughtspotCacheService.INSTANCE.get("regionData"));*/

		request.setAttribute("thoughtSpotData", thoughtSpotJsonObj.toString());
		return thoughtSpotJsonObj.toString();
	}
}

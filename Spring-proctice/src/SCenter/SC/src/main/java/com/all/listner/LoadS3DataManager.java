package com.AL.listner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.AL.ui.service.V.impl.ThoughtspotCacheService;
import com.AL.ui.vo.AgentVO;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.AL.ui.service.config.ConfigRepo;

/**
 * 
 * @author Nanda Kishore Palapala
 *
 */

public class LoadS3DataManager implements Runnable {

	private static final Logger logger = Logger.getLogger(LoadS3DataManager.class);

	private static String bucketName = "AL-V-test"; 
	private static String key        = "salescenter/thoughtSpotRevenue"; 
	private static String key1        = "salescenter/thoughtSpotAgentData";
	private static String key2        = "salescenter/thoughtSpotTime";
	
	private Long thoughtSpotRankDataRefreshTime  = Long.parseLong(ConfigRepo.getString("*.thoughtspot_top10_data_refresh_time"));
	public void run() {
		boolean dataLoaded = true;
		try {
			while (dataLoaded){
				getAgentsData();
				getAgentsRevenueData();
				try {
					Thread.sleep(thoughtSpotRankDataRefreshTime * 60000);
					//Thread.sleep(30000L); // for Testing we made it 3 mins
					dataLoaded = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void getAgentsRevenueData() throws IOException {
		AmazonS3 s3Client = new AmazonS3Client(new InstanceProfileCredentialsProvider(false));
		
		try {
			logger.info("Downloading S3 file");
			S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketName, key));
			S3Object s3object1 = s3Client.getObject(new GetObjectRequest(bucketName, key2));
			saveThoughtSpotDataIntoCache(displayTextInputStream(s3object.getObjectContent()),s3object1);
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException, which" +
					" means your request made it " +
					"to Amazon S3, but was rejected with an error response" +
					" for some reason.");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException, which means"+
					" the client encountered " +
					"an internal error while trying to " +
					"communicate with S3, " +
					"such as not being able to access the network.");
			logger.info("Error Message: " + ace.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String displayTextInputStream(InputStream input)
			throws IOException {
		// Read one text line at a time and display.
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		while (true) {
			String line = reader.readLine();
			if (line == null) break;
			logger.info("    " + line.length());
			return line;
		}
		return null;
	}

	public void saveThoughtSpotDataIntoCache(String inputJson1, S3Object s3object1) throws Exception{

		logger.info("saveThoughtSpotDataToCache inside data");
		
		if(inputJson1 != null){
			if(isDataRefreshed(inputJson1)){
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
					JSONObject json = new JSONObject(inputJson1);

					JSONObject jsonobj = new JSONObject();
					JSONArray jsonArray = new JSONArray();

					Iterator keys = json.keys();
					//System.out.println("keys ="+keys);
					while(keys.hasNext()) {
						// loop to get the dynamic key
						String currentDynamicKey = (String)keys.next();
						jsonobj = json.getJSONObject(currentDynamicKey);
						jsonArray = jsonobj.getJSONArray("data");
					}

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
								Double d = Double.parseDouble(e.get(5).toString());
								agentVo.setTotalCallsAnswered(Integer.valueOf(d.intValue()));
								//agentVo.setTotalCallsAnswered( Integer.parseInt(e.get(5).toString()));	
							}
						}
						voList.add(agentVo);
					}
					for (AgentVO list : voList){
						if (list.getAgentId() != null){
							sortedAgentList.add(list);
						}
					}
					logger.info("sortedAgentList_sorting="+sortedAgentList.size());
					sortedAgentList = sort(sortedAgentList);
					for (int i = 0 ; i < sortedAgentList.size(); i++){
						sortedAgentList.get(i).setEnterpriseRank(i +1);
					}
					if (sortedAgentList != null){
						ThoughtspotCacheService.INSTANCE.store("Enterprise",sortedAgentList);
						getTSTime(displayTextInputStream(s3object1.getObjectContent()));
						//ThoughtspotCacheService.INSTANCE.store("TSCacheDateTime",dateFormat.format(date));
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
						ThoughtspotCacheService.INSTANCE.store("agentDataMap",agentDataMap);
					}
					if (enterpriseMap != null){
						thoughtSpotDataMap.put("enterpriseMap", enterpriseMap);
					}

					if (callCentersMap != null){
						thoughtSpotDataMap.put("callCenterData", callCentersMap);
					}
					if (regionMap != null){
						thoughtSpotDataMap.put("regionData", regionMap);
					}
					if(thoughtSpotDataMap != null){
						ThoughtspotCacheService.INSTANCE.store("thoughtSpotData",thoughtSpotDataMap);
					}
					logger.info("callcenterMap size = "+callCentersMap.size() +" :: regionMap size ="+regionMap.size() +" :: enterpriseMap size ="+enterpriseMap.size());
				}catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	private boolean isDataRefreshed(String inputJson1) {
		boolean returnValue = false;
		if (ThoughtspotCacheService.INSTANCE.get("Enterprise") != null){
			List<AgentVO> cachedAgentsList = (List<AgentVO>) ThoughtspotCacheService.INSTANCE.get("Enterprise");
			if(cachedAgentsList == null || cachedAgentsList.size()==0){
				returnValue = true;
			}
			AgentVO agentVo = new AgentVO();
			List<AgentVO> voList = new ArrayList<AgentVO>();
			List<AgentVO> sortedAgentList = new ArrayList<AgentVO>();	
			try{
				JSONObject json = new JSONObject(inputJson1);

				JSONObject jsonobj = new JSONObject();
				JSONArray jsonArray = new JSONArray();

				Iterator keys = json.keys();
				//System.out.println("keys ="+keys);
				while(keys.hasNext()) {
					// loop to get the dynamic key
					String currentDynamicKey = (String)keys.next();
					jsonobj = json.getJSONObject(currentDynamicKey);
					jsonArray = jsonobj.getJSONArray("data");
				}

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
				for (AgentVO agentVo1 : cachedAgentsList){
					for (AgentVO agentVo2 : sortedAgentList){
						if (agentVo1.getAgentId().equalsIgnoreCase(agentVo2.getAgentId())){
							if(agentVo1.getGCRevenue() != agentVo2.getGCRevenue()){
								returnValue = true;
								break;
							}
						}
					}
				}

			}catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			returnValue = true;
		}
		logger.info("IsS3DataGotUpdated="+returnValue);
		return returnValue;
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

	private void getAgentsData() throws IOException {
		AmazonS3 s3Client = new AmazonS3Client(new InstanceProfileCredentialsProvider(false));
		try {
			logger.info("Downloading S3 file");
			S3Object s3object = s3Client.getObject(new GetObjectRequest(
					bucketName, key1));
			saveAgentsInfoToCache(displayTextInputStream(s3object.getObjectContent()));
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException, which" +
					" means your request made it " +
					"to Amazon S3, but was rejected with an error response" +
					" for some reason.");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException, which means"+
					" the client encountered " +
					"an internal error while trying to " +
					"communicate with S3, " +
					"such as not being able to access the network.");
			logger.info("Error Message: " + ace.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void saveAgentsInfoToCache(String inputJson1) throws Exception{

		logger.info("saveThoughtSpotDataAgentsToCache");

		if(inputJson1 != null){

			AgentVO agentVo = new AgentVO();
			List<AgentVO> voList = new ArrayList<AgentVO>();
			List<AgentVO> sortedAgentList = new ArrayList<AgentVO>();
			try{
				JSONObject json = new JSONObject(inputJson1);

				JSONObject jsonobj = new JSONObject();
				JSONArray jsonArray = new JSONArray();

				Iterator keys = json.keys();
				//System.out.println("keys ="+keys);
				while(keys.hasNext()) {
					// loop to get the dynamic key
					String currentDynamicKey = (String)keys.next();
					jsonobj = json.getJSONObject(currentDynamicKey);
					jsonArray = jsonobj.getJSONArray("data");
				}
				//["User Name","Call Center","Agent Name","PA Region","Supervisor"],"data":[["aafluker","atl","fluker, aaron","west","rennetta watson"]
				for (int i = 0 ; i < jsonArray.length(); i++) {
					JSONArray e =  (JSONArray) jsonArray.get(i);
					agentVo = new AgentVO();
					for (int j = 0 ; j < e.length(); j++) {
						if (e.get(0) != null && !e.get(0).toString().equalsIgnoreCase("null")){
							agentVo.setAgentId(String.valueOf( e.get(0)));
							agentVo.setCallCenter(String.valueOf(  e.get(1)));
							agentVo.setAgentName(String.valueOf(  e.get(2)));
							agentVo.setRegion(String.valueOf(  e.get(3)));
							agentVo.setSupervisor(e.get(4).toString());
						}
					}
					voList.add(agentVo);
				}
				for (AgentVO list : voList){
					if (list.getAgentId() != null){
						sortedAgentList.add(list);
					}
				}

				if(sortedAgentList != null){
					ThoughtspotCacheService.INSTANCE.store("agentsList",sortedAgentList);
				}
				logger.info("sortedAgentList size = "+sortedAgentList.size());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void getTSTime(String inputJson1) throws Exception{

		if(inputJson1 != null){
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			JSONObject json = new JSONObject(inputJson1);
			JSONObject jsonobj = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			Iterator keys = json.keys();
			//System.out.println("keys ="+keys);
			while(keys.hasNext()) {
				// loop to get the dynamic key
				String currentDynamicKey = (String)keys.next();
				jsonobj = json.getJSONObject(currentDynamicKey);
				jsonArray = jsonobj.getJSONArray("data");
			}
			JSONArray e =  (JSONArray) jsonArray.get(0);
			Date  date = new Date();
			String strDate = dateFormat.format(date) +" " +String.valueOf( e.get(0)); 
			logger.info("TSCacheDateTime="+strDate);
			ThoughtspotCacheService.INSTANCE.store("TSCacheDateTime",strDate);
		}
	}
}
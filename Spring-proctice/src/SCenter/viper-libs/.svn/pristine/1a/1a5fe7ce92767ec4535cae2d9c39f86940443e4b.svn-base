package com.A.Vdao.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.A.Vdao.dao.ProviderConfigDao;

@Component("providerConfigCache")
public class ProviderConfigCache {
	
	private static Map<String, ProviderConfigVO> rtProviderConfigMap = null;
	
	@Autowired
	ProviderConfigDao providerConfigDao;
	
	private static volatile Object lock = new Object();
	
	public Map<String, ProviderConfigVO> getProviderConfigMap() {
		if(rtProviderConfigMap == null) {
			synchronized (lock) {
				if(rtProviderConfigMap == null) {
					rtProviderConfigMap = providerConfigDao.getRTIMProviderConfig();
				}
			}
		}
		return rtProviderConfigMap;
	}
	
}

package com.AL.op.util;

import java.util.HashMap;
import java.util.Map;

import com.AL.util.OmeSpringUtil;
import com.AL.Vdao.util.ProviderConfigCache;
import com.AL.Vdao.util.ProviderConfigVO;

public class RTIMProviderUtil {

	private static Map<String, ProviderConfigVO> providerConfigMap = new HashMap<String, ProviderConfigVO>();
	
	static {
		ProviderConfigCache configCache = (ProviderConfigCache)OmeSpringUtil.INSTANCE.
				getBean("providerConfigCache");
		providerConfigMap = configCache.getProviderConfigMap();
	}
	
	public static ProviderConfigVO getRTIMProviderConfig(String externalId) {
		return providerConfigMap.get(externalId);
	}
	
}

package com.A.ui.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.A.ui.constants.Constants;
import com.A.ui.vo.WarmtransferTpvProvidersVO;

/**
 * Utility class to retrieve WarmtransferTpvRepo at runtime.
 * 
 * @author 
 *
 */
public class WarmtransferTpvRepo
{
	private static final Logger logger = Logger.getLogger(WarmtransferTpvRepo.class);
	public static Map<String, Integer> wtProviderMap = new HashMap<String, Integer>();
	public static Map<String, Integer> tpvProviderMap = new HashMap<String, Integer>();

	public static void setWarmTransferTpvProviders(List<WarmtransferTpvProvidersVO> wtTpvList)
	{
		if (wtTpvList != null) {
			for(WarmtransferTpvProvidersVO warmtransferTpvProvidersVO: wtTpvList){
				if(Constants.YES.equalsIgnoreCase(warmtransferTpvProvidersVO.getIsWarmtransfer())){
					wtProviderMap.put(String.valueOf(warmtransferTpvProvidersVO.getProviderId()), warmtransferTpvProvidersVO.getPriority());
				}else if(Constants.YES.equalsIgnoreCase(warmtransferTpvProvidersVO.getIsTpv())){
					tpvProviderMap.put(String.valueOf(warmtransferTpvProvidersVO.getProviderId()), warmtransferTpvProvidersVO.getPriority());
				}
			}
			logger.info("wtProviderMap="+wtProviderMap);
			logger.info("tpvProviderMap="+tpvProviderMap);
		}
	}
}

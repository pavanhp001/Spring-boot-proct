package com.AL.ui.sort;

import java.util.Comparator;

import com.AL.ui.vo.ProviderVo;

public class ProviderVoSort implements
		Comparator<ProviderVo> {
	
	public static final ProviderVoSort INSTANCE = new ProviderVoSort();

	public int compare(ProviderVo o1,
			ProviderVo o2) {

		return o1.getProvider().getName().compareTo(o2.getProvider().getName());
	}
}

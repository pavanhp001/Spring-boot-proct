package com.AL.ui.dao;

import com.AL.ui.vo.ProviderTransferAgentGroup;


public interface ProviderTransferAgentGroupDao {

	ProviderTransferAgentGroup getProviderTransferAgentGroups(String agentGroup);
	boolean hasComcastGroup();
	boolean hasATTGroup();
	boolean hasATTV6Group();

}

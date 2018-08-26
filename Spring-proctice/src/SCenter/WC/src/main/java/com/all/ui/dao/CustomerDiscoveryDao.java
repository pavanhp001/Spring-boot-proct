package com.A.ui.dao;

import com.A.ui.domain.CustomerDiscovery;
import java.util.Map;

public interface CustomerDiscoveryDao {

	public void insert(Long orderId, Long customerId, Map<String, String> customerDiscoveryInsertMap);

}

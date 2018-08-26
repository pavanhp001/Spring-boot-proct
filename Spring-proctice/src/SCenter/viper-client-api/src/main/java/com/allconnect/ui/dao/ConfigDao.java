package com.A.ui.dao;

import java.util.List;
import com.A.ui.domain.SystemConfig;

public interface ConfigDao {

	List<SystemConfig> findByContext(final String context);
	
	SystemConfig findByNameAndContext(final String name, final String context);
	
	void sync();
	
	void update(SystemConfig sysConfig);
	
    void update(List<SystemConfig> sysConfigList);

}

package com.AL.ui.dao.impl;

import java.util.HashMap;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.AL.ui.dao.ConfigDao;
import com.AL.ui.domain.SystemConfig;
import com.AL.ui.service.config.ConfigRepo;

@Component
public class ConfigDaoDefault implements
		ConfigDao {

	public List<SystemConfig> findByContext(String context) {
		// TODO Auto-generated method stub
		return null;
	}

	public SystemConfig findByNameAndContext(String name, String context) {
		// TODO Auto-generated method stub
		return null;
	}

	public void sync() {
		// TODO Auto-generated method stub
		
	}

	public void update(SystemConfig sysConfig) {
		// TODO Auto-generated method stub
		
	}

	public void update(List<SystemConfig> sysConfig) {
		// TODO Auto-generated method stub
		
	}
}

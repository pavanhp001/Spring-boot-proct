 
package com.A.ui.dao;

import java.util.List;
import java.util.Map;

import com.A.ui.domain.UserResourceEntity;

public interface UserResourceEntityDao {

	void sync();
	
	String update(UserResourceEntity userResource);
	
	int getUserLevel(String name);
	
	public UserResourceEntity findByName(final String name);
	
	Map<String, String> update(List<UserResourceEntity> userResource);
	
	public List<UserResourceEntity> getAllEntities();
	
}
 
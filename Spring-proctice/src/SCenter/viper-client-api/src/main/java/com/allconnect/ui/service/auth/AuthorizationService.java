package com.A.ui.service.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.A.ui.repository.SessionCache;
import com.A.vo.UserAuthorization;

public enum AuthorizationService {

	INSTANCE;
	
	private Logger logger = Logger.getLogger(AuthorizationService.class.getName());

	public boolean isAuthorized(HttpSession session, String obj,
			Permission targetPermission) {

		UserAuthorization userAuthorization = SessionCache.INSTANCE
				.getSubject(session);
		String context = SessionCache.INSTANCE.getActiveProvider(session);

		return isAuthorized(userAuthorization, context, obj,
				targetPermission.name());
	}

	public boolean isAuthorized(HttpSession session, String obj,
			String targetPermission) {

		UserAuthorization userAuthorization = SessionCache.INSTANCE
				.getSubject(session);
		String context = SessionCache.INSTANCE.getActiveProvider(session);

		return isAuthorized(userAuthorization, context, obj, targetPermission);
	}

	public Permission isAuthorized(HttpSession session, String obj) {

		UserAuthorization userAuthorization = SessionCache.INSTANCE
				.getSubject(session);
		String context = SessionCache.INSTANCE.getActiveProvider(session);
		
		return isAuthorized(userAuthorization, context, obj);

	}

	public boolean isAuthorized(UserAuthorization userAuthorization,
			String context, String obj,
			Permission targetPermission) {
		return isAuthorized(userAuthorization, context, obj, name());
	}

	public boolean isAuthorized(UserAuthorization userAuthorization,
			String context, String obj, String targetPermission) {

		Map<String, List<String>> contextAuthorization = userAuthorization
				.getPermissions().getRepository().get(context);

		List<String> objectAuthorization = contextAuthorization.get(obj);

		boolean isAuthorized = isAuthorized(objectAuthorization,
				targetPermission);

		return isAuthorized;
	}

	public Permission isAuthorized(
			UserAuthorization userAuthorization, String context, String obj) {
		logger.info("[isAuthorized] context: "+context+", obj: "+obj);

		Map<String, List<String>> contextAuthorization = userAuthorization
				.getPermissions().getRepository().get(context);

		if(contextAuthorization != null){
			logger.debug("[isAuthorized] found contextAuthorization for provider "+context);
			List<String> objectAuthorization = contextAuthorization.get(obj);

			return Permission.level(objectAuthorization);
		} else {
			logger.debug("[isAuthorized] contextAuthorization is null... ");
			return Permission.none;
		}
	}
	
	/**
	 * To get the AuthorizationMap from session
	 * 	using active provider set to session
	 * 
	 * @param session
	 * @return
	 */
	public Map<String, List<String>> getAuthorizationMap(HttpSession session){
		
		UserAuthorization userAuthorization = SessionCache.INSTANCE.getSubject(session);
		
		String context = "";
		//AccessType is for Fulfillment use
		String accessType = SessionCache.INSTANCE.getAccessType(session);
		logger.debug("getAuthorizationMap, AccessType: "+accessType);
		if(StringUtils.isNotBlank(accessType) && accessType.equals("GLOBAL")){
			context = "GLOBAL";
		} else {
			context = SessionCache.INSTANCE.getActiveProvider(session);
		}
		
		Map<String, List<String>> contextAuthorization = new HashMap<String, List<String>>();
		if(StringUtils.isNotBlank(context)){
			contextAuthorization = userAuthorization.getPermissions().getRepository().get(context);
			logger.info("[getAuthorizationMap] from session, context: "+context+", size: "+contextAuthorization.size());
		}
		return contextAuthorization;
	}
	
	/**
	 * To get the AuthorizationMap from session
	 * 	using context passed
	 * 
	 * @param session
	 * @param context
	 * @return Map<String, List<String>>
	 */
	public Map<String, List<String>> getAuthorizationMap(HttpSession session, String context){
		
		logger.info("[getAuthorizationMap] context: "+context);
		
		UserAuthorization userAuthorization = SessionCache.INSTANCE.getSubject(session);
		
		Map<String, List<String>> contextAuthorization = userAuthorization.getPermissions().getRepository().get(context);
		
		if(contextAuthorization != null){
			logger.info("[getAuthorizationMap] size: "+contextAuthorization.size());
		}
		
		return contextAuthorization;
	}

	private boolean isAuthorized(List<String> permissionList,
			String targetPermission) {
		
		logger.debug("[isAuthorized] targetPermission: "+targetPermission+", permissionList: "+permissionList);

		for (String permission : permissionList) {
			if (targetPermission.equalsIgnoreCase(permission)
					|| (targetPermission.indexOf(permission) != -1)) {
				return Boolean.TRUE;
			}
		}

		return Boolean.FALSE;
	}
}

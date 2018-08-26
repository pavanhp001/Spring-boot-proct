package com.A.ui.service.auth.impl;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.ui.repository.SessionCache;
import com.A.ui.service.auth.AuthorizationPolicy;
import com.A.ui.service.auth.AuthorizationService;
import com.A.ui.service.auth.Permission;
import com.A.vo.UserAuthorization;

@Component
public class AuthorizationPolicyDefault implements AuthorizationPolicy {

	private Logger log = Logger.getLogger(AuthorizationPolicyDefault.class.getName());
	
	public static final String GLOBAL_TYPE = "GLOBAL";
	
	@Override
	public Boolean eval(HttpSession session, String modulename) {

		//AccessType is for Fulfillment use
		String accessType = SessionCache.INSTANCE.getAccessType(session);
		log.debug("eval, AccessType: "+accessType+", modulename: "+modulename);
		
		if(StringUtils.equals(accessType, GLOBAL_TYPE)){
			return eval(session, modulename, GLOBAL_TYPE);
		} else {
			// Required policy level for this module
			Permission requiredMinimumPermission = AuthorizationRepository.INSTANCE
					.get(modulename);
			Permission userConfiguredPermission = AuthorizationService.INSTANCE
					.isAuthorized(session, modulename.toUpperCase());

			if ((requiredMinimumPermission == null)
					|| (userConfiguredPermission == null)) {
				return Boolean.FALSE;
			}
			
			log.debug("eval, UserPermission: "+userConfiguredPermission.getLevel()+", ReqPermission: "+requiredMinimumPermission.getLevel());

			return (userConfiguredPermission.getLevel() >= requiredMinimumPermission
					.getLevel());
		}
	}

	@Override
	public Boolean isMasked(HttpSession session, String modulename) {
		// Required policy level for this module
		Permission userConfiguredPermissionForMask = AuthorizationService.INSTANCE
				.isAuthorized(session, modulename.toUpperCase());

		if ((userConfiguredPermissionForMask == null)) {
			return Boolean.FALSE;
		}

		return (userConfiguredPermissionForMask.getLevel() == Permission.mask
				.getLevel());
	}

	@Override
	public Boolean isCurrentUserPermissionEqual(HttpSession session,
			String modulename, Permission evalPermission) {
		log.debug("isCurrentUserPermissionEqual, modulename: "+modulename+", evalPermission: "+evalPermission.getLevel());
		
		// Required policy level for this module
		Permission userConfiguredPermissionForMask = AuthorizationService.INSTANCE
				.isAuthorized(session, modulename.toUpperCase());

		if ((userConfiguredPermissionForMask == null)) {
			return Boolean.FALSE;
		}

		return (userConfiguredPermissionForMask.getLevel() == evalPermission
				.getLevel());
	}

	@Override
	public Boolean isCurrentUserPermissionLess(HttpSession session,
			String modulename, Permission evalPermission) {
		log.debug("isCurrentUserPermissionLess, modulename: "+modulename+", evalPermission: "+evalPermission.getLevel());
		
		// Required policy level for this module
		Permission userConfiguredPermissionForMask = AuthorizationService.INSTANCE
				.isAuthorized(session, modulename.toUpperCase());

		if ((userConfiguredPermissionForMask == null)) {
			return Boolean.FALSE;
		}

		return (userConfiguredPermissionForMask.getLevel() < evalPermission
				.getLevel());
	}

	@Override
	public Boolean isCurrentUserPermissionMore(HttpSession session,
			String modulename, Permission evalPermission) {
		log.debug("isCurrentUserPermissionMore, modulename: "+modulename+", evalPermission: "+evalPermission.getLevel());
		
		// Required policy level for this module
		Permission userConfiguredPermissionForMask = AuthorizationService.INSTANCE
				.isAuthorized(session, modulename.toUpperCase());

		if ((userConfiguredPermissionForMask == null)) {
			return Boolean.FALSE;
		}

		return (userConfiguredPermissionForMask.getLevel() > evalPermission
				.getLevel());
	}

	@Override
	public Boolean isCurrentUserPermissionLessEq(HttpSession session,
			String modulename, Permission evalPermission) {
		log.debug("isCurrentUserPermissionLessEq, modulename: "+modulename+", evalPermission: "+evalPermission.getLevel());
		
		// Required policy level for this module
		Permission userConfiguredPermissionForMask = AuthorizationService.INSTANCE
				.isAuthorized(session, modulename.toUpperCase());

		if ((userConfiguredPermissionForMask == null)) {
			return Boolean.FALSE;
		}

		return (userConfiguredPermissionForMask.getLevel() <= evalPermission
				.getLevel());
	}

	@Override
	public Boolean isCurrentUserPermissionMoreEq(HttpSession session,
			String modulename, Permission evalPermission) {
		log.debug("isCurrentUserPermissionMoreEq, modulename: "+modulename+", evalPermission: "+evalPermission.getLevel());

		Permission userConfiguredPermission = Permission.none;

		//AccessType is for Fulfillment use
		String accessType = SessionCache.INSTANCE.getAccessType(session);
		log.info("isCurrentUserPermissionMoreEq, AccessType: "+accessType+", modulename: "+modulename);

		if(StringUtils.equals(accessType, GLOBAL_TYPE)){
			UserAuthorization userAuthorization = SessionCache.INSTANCE.getSubject(session);
			userConfiguredPermission = AuthorizationService.INSTANCE.isAuthorized(userAuthorization, 
					GLOBAL_TYPE, modulename.toUpperCase());
		} else {
			userConfiguredPermission = AuthorizationService.INSTANCE.isAuthorized(session, 
					modulename.toUpperCase());
		}
		
		if ((userConfiguredPermission == null)) {
			return Boolean.FALSE;
		}
		log.info("isCurrentUserPermissionMoreEq, userConfiguredPermission: "+userConfiguredPermission.getLevel());

		return (userConfiguredPermission.getLevel() >= evalPermission
				.getLevel());
	}
	
	@Override
	public Boolean eval(HttpSession session, String modulename, String context) {
		log.debug("eval, context: "+context+", modulename: "+modulename);
		Permission requiredMinimumPermission = AuthorizationRepository.INSTANCE.get(modulename);
		
		UserAuthorization userAuthorization = SessionCache.INSTANCE.getSubject(session);
		Permission userConfiguredPermission = AuthorizationService.INSTANCE.isAuthorized(userAuthorization, context.toUpperCase(), modulename.toUpperCase());
		log.debug("eval, UserPermission: "+userConfiguredPermission.getLevel()+", ReqPermission: "+requiredMinimumPermission.getLevel());
		if ((requiredMinimumPermission == null)
				|| (userConfiguredPermission == null)) {
			return Boolean.FALSE;
		}
		
		return (userConfiguredPermission.getLevel() >= requiredMinimumPermission.getLevel());
	}

}

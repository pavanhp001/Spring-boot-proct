package com.A.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.A.comm.manager.jms.util.StringUtil;
import com.A.ui.service.auth.Permission;

public class AuthorizationMap<T, U> extends HashMap<T, U> implements Map<T, U> {

	private static final long serialVersionUID = 1L;
	private Map<String, Map<String, List<String>>> repository = null;
	
	private static final Logger logger = Logger.getLogger(AuthorizationMap.class);
	
	public Permission grantMost(final String context, final String resource) {
		Permission permission = Permission.none;
		List<String> permissionList = getPermissions(context, resource);

		permission = Permission.level(permissionList);

		return permission;
	}

	public Permission grant(final String context, final String resource) {
		Permission permission = Permission.none;
		List<String> permissionList = getPermissions(context, resource);

		permission = Permission.level(permissionList);

		return permission;
	}

	public boolean grant(final String context, final String resource,
			final String required) {
		Boolean grant = Boolean.FALSE;
		List<String> permissionList = getPermissions(context, resource);

		permissionLoop: for (String current : permissionList) {
			if (current.equals(required)) {
				grant = Boolean.TRUE;
				break permissionLoop;
			}
		}

		return grant;
	}

	public boolean grant(final String context, final String resource,
			final List<String> requiredList) {

		Boolean grant = Boolean.FALSE;

		List<String> permissionList = getPermissions(context, resource);

		permissionLoop: for (String required : requiredList) {

			for (String current : permissionList) {
				if (current.equals(required)) {
					grant = Boolean.TRUE;
					break permissionLoop;
				}
			}
		}

		return grant;
	}

	public List<String> addPermissions(final String context,
			final String resource, final List<String> permissions) {

		List<String> permissionList = getPermissions(context, resource);

		permissionList.addAll(permissions);

		return permissionList;
	}

	public List<String> addPermissions(final String context,
			final String resource, final String[] permissions) {

		List<String> permissionList = getPermissions(context, resource);

		for (int i = 0; i < permissions.length; i++) {
			permissionList.add(permissions[i]);
		}

		return permissionList;
	}

	public List<String> addPermissions(final String context,
			final String resource, final String[] permissions, int start,
			int end) {

		List<String> permissionList = getPermissions(context, resource);

		for (int i = start; i <= end; i++) {
			permissionList.add(permissions[i]);
		}

		return permissionList;
	}

	public List<String> getPermissions(final String context,
			final String resource) {

		List<String> permissionList = getContext(context).get(resource);

		if (getContext(context).get(resource) == null) {

			permissionList = new ArrayList<String>();
			getContext(context).put(resource, permissionList);
		}

		return permissionList;
	}

	public Map<String, List<String>> getContext(final String context) {

		Map<String, List<String>> contextdata = getRepository().get(context);

		if (contextdata == null) {
			contextdata = new HashMap<String, List<String>>();
			getRepository().put(context, contextdata);
		}

		return contextdata;
	}

	public Map<String, Map<String, List<String>>> getRepository() {

		if (repository == null) {
			repository = new HashMap<String, Map<String, List<String>>>();
		}

		return repository;
	}

	public void setObjects(Map<String, Map<String, List<String>>> objects) {
		this.repository = objects;
	}

	public void add(final String authData) {
		if(StringUtils.isNotBlank(authData)){
			String[] ele = StringUtil.tokenize(authData, "_");

			if (ele.length < 3) {
				logger.info("authorization unable to add:" + authData);
			} else{
				String context = ele[0];
				String resource = ele[1];
				addPermissions(context, resource, ele, 2, 2);
			}
		}
	}

	public static final void main(String[] arg) {

		AuthorizationMap<String, Map<String, List<String>>> auth = new AuthorizationMap<String, Map<String, List<String>>>();

		auth.add("3762731_DOB_VIEW");
		auth.add("3762731_HOLD_QUEUE_VIEW");
		auth.add("3762731_NEW_QUEUE_VIEW");
		auth.add("3762731_NOTES_ADD");
		auth.add("3762731_NOTES_EDIT");
		auth.add("3762731_PYMNT_VIEW_ALL");
		auth.add("3762731_PYMNT_VIEW_PARTIAL");
		auth.add("3762731_SEARCH_ORDERS");
		auth.add("3762731_SSN_VIEW_ALL");
		auth.add("3762731_SSN_VIEW_PARTIAL");
		auth.add("3762731_UPDATE_STATUS_ALL");
		auth.add("3762731_UPDATE_STATUS_CANCEL");
		auth.add("3762731_UPDATE_STATUS_HOLD");
		auth.add("3762731_UPDATE_STATUS_RESEND");

		logger.info(auth.toString());

	}

}

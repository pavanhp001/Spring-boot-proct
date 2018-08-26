package com.A.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

import com.A.vo.AuthorizationMap;
import com.A.vo.UserGroup;
import com.A.V.domain.User;

public class UserAuthorization {

	private AuthorizationMap<String, String> roles = new AuthorizationMap<String, String>();
	private AuthorizationMap<String, String> resources = new AuthorizationMap<String, String>();
	private Map<String, String> metaDataMap = new HashMap<String, String>();
	AuthorizationMap<String, Map<String, List<String>>> permissions = new AuthorizationMap<String, Map<String, List<String>>>();

	private Map<String, Object> codes = new HashMap<String, Object>();
	private Map<String, Object> texts = new HashMap<String, Object>();
	private String userLogin;
	private String userName;
	private String userLevel;
	private String userRefID;
	private boolean userActive;
	private Date created;
	private String sessionId;
	private String userType;
	private String context;
	private String order;
	private String lineitem;
	private String activeProvider;
	private String activeProviderName;
	private String image;
	private List<UserGroup> userGroups = new ArrayList<UserGroup>();

	private User user;

	private static final String SUCCESSFUL_AUTHENTICATION_CODE = "0.0";
	private static final String WRONG_PASS_AUTH_TEXT = "Invalid credentials";

	public AuthorizationMap<String, String> getResources() {

		if (resources == null) {
			resources = new AuthorizationMap<String, String>();
		}
		return resources;
	}

	public AuthorizationMap<String, String> getRoles() {

		if (roles == null) {
			roles = new AuthorizationMap<String, String>();
		}
		return roles;
	}

	public boolean authenticated() {
		return getCodes().containsKey(SUCCESSFUL_AUTHENTICATION_CODE);
	}

	public String getFailureText() {
		if(getTexts().containsKey(WRONG_PASS_AUTH_TEXT)){
			return "WRONG_PWD";
		} else {
			return "WRONG_UNAME";
		}
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserRefID() {
		return userRefID;
	}

	public void setUserRefID(String userRefID) {
		this.userRefID = userRefID;
	}

	public boolean isUserActive() {
		return userActive;
	}

	public void setUserActive(boolean userActive) {
		this.userActive = userActive;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Map<String, Object> getCodes() {
		return codes;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getLineitem() {
		return lineitem;
	}

	public void setLineitem(String lineitem) {
		this.lineitem = lineitem;
	}

	public String getActiveProvider() {
		return activeProvider;
	}

	public void setActiveProvider(String activeProvider) {
		this.activeProvider = activeProvider;
	}

	public String getActiveProviderName() {
		return activeProviderName;
	}

	public void setActiveProviderName(String activeProviderName) {
		this.activeProviderName = activeProviderName;
	}

	public void setRoles(AuthorizationMap<String, String> roles) {
		this.roles = roles;
	}

	public void setCodes(Map<String, Object> codes) {
		this.codes = codes;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setResources(final Set<String> contextList) {

		for (String context : contextList) {
			resources.put(context, context);
		}

	}

	public void setResources(AuthorizationMap<String, String> resources) {
		this.resources = resources;
	}

	public AuthorizationMap<String, Map<String, List<String>>> getPermissions() {
		return permissions;
	}

	public static String getSuccessfulAuthenticationCode() {
		return SUCCESSFUL_AUTHENTICATION_CODE;
	}

	public Map<String, String> getMetaDataMap() {
		return metaDataMap;
	}

	public void setMetaDataMap(Map<String, String> metaDataMap) {
		this.metaDataMap = metaDataMap;
	}
	
	public Map<String, Object> getTexts() {
		return texts;
	}

	public void setTexts(Map<String, Object> texts) {
		this.texts = texts;
	}
	
	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	@Override
	public String toString() {
		return "UserAuthorization [activeProvider=" + activeProvider
				+ ", activeProviderName=" + activeProviderName + ", codes="
				+ codes + ", context=" + context + ", created=" + created
				+ ", image=" + image + ", lineitem=" + lineitem
				+ ", metaDataMap=" + metaDataMap + ", order=" + order
				+ ", permissions=" + permissions + ", resources=" + resources
				+ ", roles=" + roles + ", sessionId=" + sessionId + ", texts="
				+ texts + ", user=" + user + ", userActive=" + userActive
				+ ", userLevel=" + userLevel + ", userLogin=" + userLogin
				+ ", userName=" + userName + ", userRefID=" + userRefID
				+ ", userType=" + userType + "]";
	}
}

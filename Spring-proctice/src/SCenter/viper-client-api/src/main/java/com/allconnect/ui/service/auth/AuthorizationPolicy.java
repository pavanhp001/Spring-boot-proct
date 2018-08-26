package com.A.ui.service.auth;

import javax.servlet.http.HttpSession;

public interface AuthorizationPolicy {

	Boolean eval(HttpSession session,String careModuleName);
	Boolean eval(HttpSession session, String careModuleName, String context);
	Boolean isMasked(HttpSession session, String modulename);
	Boolean isCurrentUserPermissionEqual(HttpSession session, String modulename, Permission evalPermission);
	Boolean isCurrentUserPermissionLess(HttpSession session, String modulename, Permission evalPermission);
	Boolean isCurrentUserPermissionMore(HttpSession session, String modulename, Permission evalPermission);
	Boolean isCurrentUserPermissionLessEq(HttpSession session, String modulename, Permission evalPermission);
	Boolean isCurrentUserPermissionMoreEq(HttpSession session, String modulename, Permission evalPermission);

}

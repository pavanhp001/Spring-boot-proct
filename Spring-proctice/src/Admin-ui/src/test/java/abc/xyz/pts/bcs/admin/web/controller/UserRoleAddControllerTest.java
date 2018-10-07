/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.admin.web.controller;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.support.SessionStatus;
import org.springmodules.validation.commons.DefaultBeanValidator;

import abc.xyz.pts.bcs.admin.business.UserRoleService;
import abc.xyz.pts.bcs.admin.web.command.UserRoleDataCommand;
import abc.xyz.pts.bcs.common.enums.UserPermissionType;
import abc.xyz.pts.bcs.common.ldap.dto.UserRoleData;
import abc.xyz.pts.bcs.common.ldap.exception.RoleExistsException;

/**
 * @author Deepesh.Rathore
 *
 */
public class UserRoleAddControllerTest {

	private UserRoleAddController userRolePermissionController;
	private ExtendedModelMap model;
	private Mockery context = new Mockery(){
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private MockHttpSession mockSession;
	private SessionStatus mockSessionStatus;
	private BindException bindingResult;
	private UserRoleDataCommand userRoleDataCommand;
	private DefaultBeanValidator mockValidator;
	private UserRoleService mockUserRoleService;
	
	@Before
	public void setup() throws SecurityException, NoSuchFieldException {
		userRolePermissionController = new UserRoleAddController();
		model = new ExtendedModelMap();
		mockSession = new MockHttpSession();
		mockSessionStatus = context.mock(SessionStatus.class);
		userRoleDataCommand = new UserRoleDataCommand();
		bindingResult = new BindException(userRoleDataCommand, "userRoleDataCommand");
		mockValidator = context.mock(DefaultBeanValidator.class);
		mockUserRoleService = context.mock(UserRoleService.class);
		
        Field field = userRolePermissionController.getClass().getSuperclass().getDeclaredField("commonsValidator");
        field.setAccessible(true);
        ReflectionUtils.setField(field,userRolePermissionController,mockValidator);
        
        field = userRolePermissionController.getClass().getDeclaredField("userRoleService");
        field.setAccessible(true);
        ReflectionUtils.setField(field,userRolePermissionController,mockUserRoleService);
	}
	
	@Test
	public void testNewUserRoleListCommand(){
		UserRoleDataCommand returnedUserRoleDataCommand = userRolePermissionController.newUserRoleCommand();
		assertThat(returnedUserRoleDataCommand, notNullValue(UserRoleDataCommand.class));
	}
	
	@Test
	public void testNewUserRoleListCommandAlwaysReturnsANewInstance(){
		UserRoleDataCommand returnedUserRoleDataCommand = userRolePermissionController.newUserRoleCommand();
		UserRoleDataCommand returnedUserRoleDataCommand2 = userRolePermissionController.newUserRoleCommand();
		assertThat(returnedUserRoleDataCommand, not(sameInstance(returnedUserRoleDataCommand2)));
	}
	
	@Test
	public void testSetupForm() throws Exception {
		String viewName = userRolePermissionController.setupForm(new UserRoleDataCommand(), null, null, null, model, null, null);
		
		Object object = model.get("userRoleCommand");
		assertThat(object, notNullValue());
		assertThat(object, instanceOf(UserRoleDataCommand.class));
        assertThat(viewName, is("addUserRole.view"));
	}

	@Test
	public void testProcessCancel() throws Exception {
		final UserRoleDataCommand userRoleDataCommandInSession = new UserRoleDataCommand();
		mockSession.setAttribute("userRoleCommand", userRoleDataCommandInSession);
		context.checking(new Expectations(){
			{
				oneOf(mockSessionStatus).setComplete();
			}
		});
		String viewName = userRolePermissionController.processCancelFromAdd(model, mockSessionStatus, mockSession);
		
		assertThat((UserRoleDataCommand)mockSession.getAttribute("userRoleCommand"), is(userRoleDataCommandInSession));
		
		assertThat(viewName, is("redirect:/userRoleList.form"));
	}
	
	@Test
	public void testAddUserRoleWhenNoErrors() throws Exception {
		
		context.checking(new Expectations(){
			{
				oneOf(mockValidator).validate(userRoleDataCommand, bindingResult);
				oneOf(mockUserRoleService).addRole(with(any(UserRoleData.class)));
			}
		});
		
		String viewName = userRolePermissionController.processAddUserRole(userRoleDataCommand, bindingResult, mockSession, model);
		
		Object object = model.get("userRoleCommand");
		
		assertThat(object, notNullValue());
		assertThat(object, instanceOf(UserRoleDataCommand.class));
		assertThat((UserRoleDataCommand)object, sameInstance(userRoleDataCommand));
		
		assertThat(userRoleDataCommand.isUserRoleAdded(), is(true));
		assertThat(viewName, is("addUserRole.view"));
	}
	
	@Test
	public void testAddUserRoleWhenThereAreErrors() throws Exception {
		
		bindingResult.addError(new ObjectError("error", "error"));
		
		context.checking(new Expectations(){
			{
				oneOf(mockValidator).validate(userRoleDataCommand, bindingResult);
				never(mockUserRoleService).addRole(with(any(UserRoleData.class)));
			}
		});
		
		String viewName = userRolePermissionController.processAddUserRole(userRoleDataCommand, bindingResult, mockSession, model);
		
		Object object = model.get("userRoleCommand");
		
		assertThat(object, notNullValue());
		assertThat(object, instanceOf(UserRoleDataCommand.class));
		assertThat((UserRoleDataCommand)object, sameInstance(userRoleDataCommand));
		
		assertThat(userRoleDataCommand.isUserRoleAdded(), is(false));
		assertThat(viewName, is("addUserRole.view"));
	}
	
	@Test
	public void testAddUserRoleWhenExceptionThrownAddingRole() throws Exception {
		
		bindingResult.addError(new ObjectError("error", "error"));
		final BindingResult mockErrors = context.mock(BindingResult.class);
		context.checking(new Expectations(){
			{
				oneOf(mockValidator).validate(userRoleDataCommand, mockErrors);
				oneOf(mockUserRoleService).addRole(with(any(UserRoleData.class))); will(throwException(new RoleExistsException(new Exception("error"))));
				oneOf(mockErrors).rejectValue("permissionList", "role.already.exists");
				oneOf(mockErrors).getAllErrors();will(returnValue(Collections.EMPTY_LIST));
			}
		});
		
		String viewName = userRolePermissionController.processAddUserRole(userRoleDataCommand, mockErrors, mockSession, model);
		
		Object object = model.get("userRoleCommand");
		
		assertThat(object, notNullValue());
		assertThat(object, instanceOf(UserRoleDataCommand.class));
		assertThat((UserRoleDataCommand)object, sameInstance(userRoleDataCommand));
		
		assertThat(userRoleDataCommand.isUserRoleAdded(), is(false));
		assertThat(viewName, is("addUserRole.view"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testReferenceData() throws Exception {
		userRolePermissionController.referenceData(model);
		Object permissionList = model.get("permissionList");
		assertThat((List<UserPermissionType>)permissionList, is(Arrays.asList(UserPermissionType.values())));
	}
	
	@After
	public void verify() throws Exception {
		context.assertIsSatisfied();
	}
}

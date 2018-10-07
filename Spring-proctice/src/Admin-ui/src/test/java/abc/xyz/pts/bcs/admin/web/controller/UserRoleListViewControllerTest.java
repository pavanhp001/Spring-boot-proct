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

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;

import abc.xyz.pts.bcs.admin.business.UserRoleService;
import abc.xyz.pts.bcs.admin.web.command.UserRoleListCommand;
import abc.xyz.pts.bcs.common.ldap.dto.UserRoleData;
/**
 * @author Deepesh.Rathore
 *
 */
public class UserRoleListViewControllerTest {
	private UserRoleListViewController userRoleListViewController;
	private ExtendedModelMap model;
	private UserRoleListCommand userRoleListCommand;
	private UserRoleService mockUserRoleService;
	
	private Mockery context = new Mockery();
	
	
	@Before
	public void setup() {
		userRoleListViewController = new UserRoleListViewController();
	    model = new ExtendedModelMap();
	    userRoleListCommand = new UserRoleListCommand();
	    mockUserRoleService = context.mock(UserRoleService.class);
	    userRoleListViewController.setUserRoleService(mockUserRoleService);
	}
	
	@Test
	public void testNewUserRoleListCommand(){
		UserRoleListCommand returnedUserRoleListCommand = userRoleListViewController.newUserRoleListCommand();
		assertThat(returnedUserRoleListCommand, notNullValue(UserRoleListCommand.class));
	}

	
	@Test
	public void testNewUserRoleListCommandAlwaysReturnsANewInstance(){
		UserRoleListCommand returnedUserRoleListCommand = userRoleListViewController.newUserRoleListCommand();
		UserRoleListCommand returnedUserRoleListCommand2 = userRoleListViewController.newUserRoleListCommand();
		assertThat(returnedUserRoleListCommand, not(sameInstance(returnedUserRoleListCommand2)));
	}

	@Test
	public void testSetupForm() throws Exception {
		final List<UserRoleData> userRoleList = new ArrayList<UserRoleData>();
		context.checking(new Expectations(){
			{
				oneOf(mockUserRoleService).findAllRoles(); will(returnValue(userRoleList));
			}
		});
		
		String viewName = userRoleListViewController.setupForm(userRoleListCommand, null, null, null, model, null, null);
		
		Object userRoleListCommand =  model.get("userRoleListCommand");
		assertThat(userRoleListCommand, instanceOf(UserRoleListCommand.class));
		List<UserRoleData> returnedUserRoleList = ((UserRoleListCommand)userRoleListCommand).getUserRoleList();
		assertThat(returnedUserRoleList, sameInstance(userRoleList));
		assertThat(viewName, is("userRoleList.view"));
	}
	
	@After
	public void tearDown(){
		context.assertIsSatisfied();
	}
}

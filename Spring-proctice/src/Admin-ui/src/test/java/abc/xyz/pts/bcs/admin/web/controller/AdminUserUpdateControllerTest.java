/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.admin.web.controller;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.support.SessionStatus;
import org.springmodules.validation.commons.DefaultBeanValidator;

import abc.xyz.pts.bcs.admin.business.AdminService;
import abc.xyz.pts.bcs.admin.business.UserStatus;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.web.command.AdminUserUpdateCommand;
import abc.xyz.pts.bcs.admin.web.validator.AdminUserUpdateValidator;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;


public class AdminUserUpdateControllerTest extends TestCase {    
	private static final String UPDATE_COMMAND = "adminUserUpdateCommand";
	private static final String SUCCESS_VIEW = "adminUserUpdate.view";
	private static final String FORWARD_TO_USER_PROFILE = "forward:/adminUserProfile.cform";
	private static final String TABLE_COMMAND = "updateUserTableCommand";
	 
    private final Mockery context = new JUnit4Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private AdminUserUpdateController controller;
    private AdminService adminService;
	private DefaultBeanValidator commonsValidator;
	private AdminUserUpdateValidator adminUserUpdateValidator;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private BindException errors;
	private SessionStatus sessionStatus;

   @Override
    public void setUp() throws Exception {

         this.controller = new AdminUserUpdateController();

        request = new MockHttpServletRequest("POST", "/adminUserUpdate.form");
        response = new MockHttpServletResponse();
        errors = new BindException("test", "test");
        sessionStatus =  context.mock(SessionStatus.class);

        // Create mock objects
        adminService = context.mock(AdminService.class);
        commonsValidator = context.mock(DefaultBeanValidator.class);
        adminUserUpdateValidator = context.mock(AdminUserUpdateValidator.class);
        ReflectionTestUtils.setField(controller, "adminService", adminService);
        ReflectionTestUtils.setField(controller, "commonsValidator", commonsValidator);
        ReflectionTestUtils.setField(controller, "adminUserUpdateValidator", adminUserUpdateValidator);;


    }
   
   public void testSetupForm() throws Exception{
	   AdminUserUpdateCommand command = controller.newCommand();
	   final User user = new User();
	   user.setUsername("test");
	   command.setUser(user);
	   TableActionCommand tableCommand = controller.newTableCommand();
	   String viewName = null;

	   MockHttpSession session = new MockHttpSession(); 
	   ModelMap model = new ModelMap();
	   context.checking(new Expectations() {
		   {
			   one(sessionStatus).setComplete();
			   one(adminService).getUser(user.getUsername()) ;
		   }
	   });
	   viewName = controller.setupForm(tableCommand, command, errors, sessionStatus, session, model);
	   assertNotNull(viewName);
	   assertEquals(SUCCESS_VIEW, viewName);
   }

   public void testCancel() throws Exception{
	   AdminUserUpdateCommand command = controller.newCommand();
	   User user = new User();
	   user.setUsername("SUP");
	   command.setUser(user);
	   MockHttpSession session = new MockHttpSession(); 
	   ModelMap model = new ModelMap();
	   session.setAttribute(UPDATE_COMMAND, command);
	   session.setAttribute(TABLE_COMMAND, new TableActionCommand());   
	   context.checking(new Expectations() {
		   {
			   one(sessionStatus).setComplete();
			   one(adminService).getUser("SUP") ;
		   }
	   });
	   String viewName = null;
	   viewName = controller.cancel(model, sessionStatus, session);
	   assertNotNull(viewName);
	   assertEquals(SUCCESS_VIEW, viewName);

   }
  
    public void testNewCommand() throws Exception{
    	AdminUserUpdateCommand command = controller.newCommand();
    	assertNotNull(command);
	}
	  
    public void testNewTableCommand() throws Exception{
    	TableActionCommand tableCommand = controller.newTableCommand();
	  	assertNotNull(tableCommand);        
	} 
	  
    public void testProcessUpdate() throws Exception{
 	   final AdminUserUpdateCommand command = controller.newCommand();
 	    final User user = new User();
    	user.setUsername("test");
    	user.setEmail("test.test@abc.com");
    	user.setLastname("testlastname");
    	user.setForename("testformane");
	 	command.setUser(user);
       TableActionCommand tableCommand = controller.newTableCommand();
        MockHttpSession session = new MockHttpSession();  
		session.setAttribute(UPDATE_COMMAND, command);
        ModelMap model = new ModelMap();
        context.checking(new Expectations() {
            {
                one(commonsValidator).validate(with(any(AdminUserUpdateCommand.class)), with(any(BindException.class)));  
                one(adminUserUpdateValidator).validate(with(any(AdminUserUpdateCommand.class)), with(any(BindException.class)));                
            }
        });
        String viewName = controller.updateUser(tableCommand, command, errors, session, model);
	    assertEquals(SUCCESS_VIEW, viewName);
	    assertEquals(true, command.isUpdateValidated());	    
        context.assertIsSatisfied();
    }
  
    public void testUpdateConfirm() throws Exception{
  	   final AdminUserUpdateCommand command = controller.newCommand();
	  	 final User user = new User();
    	user.setUsername("test");
    	user.setEmail("test.test@abc.com");
    	user.setLastname("testlastname");
    	user.setForename("testformane");
    	user.setRoles(new String[]{"SUP","BAM"});
	 	command.setUser(user);
        TableActionCommand tableCommand = controller.newTableCommand();
         MockHttpSession session = new MockHttpSession();  
 		session.setAttribute(UPDATE_COMMAND, command);
         ModelMap model = new ModelMap();
         
         context.checking(new Expectations() {
             {
                 one(commonsValidator).validate(with(any(AdminUserUpdateCommand.class)), with(any(BindException.class)));  
                 one(adminUserUpdateValidator).validate(with(any(AdminUserUpdateCommand.class)), with(any(BindException.class)));                
             }
         });
         context.checking(new Expectations() {
             {
            	 one(sessionStatus).setComplete();
            	 one(adminService).updateUser(command.getUser()) ;
                 one(adminService).getUser(user.getUsername()) ;
             }
         });
         String viewName = controller.updateConfirm(tableCommand, command, errors, sessionStatus, session,  model);
 	    assertEquals(FORWARD_TO_USER_PROFILE, viewName);
     }
    
    public void testResetUserPassword() throws Exception{
    	final AdminUserUpdateCommand command = controller.newCommand();
    	final User user = new User();
    	user.setUsername("test");
    	user.setEmail("test.test@abc.com");
    	user.setLastname("testlastname");
    	user.setForename("testformane");
    	user.setUserStatus(UserStatus.DISABLED);
    	command.setUser(user);
    	TableActionCommand tableCommand = controller.newTableCommand();
    	MockHttpSession session = new MockHttpSession();  
    	session.setAttribute(UPDATE_COMMAND, command);
    	ModelMap model = new ModelMap();
    	context.checking(new Expectations() {
            {
            	one(sessionStatus).setComplete();
            	one(adminService).updateUser(user);
            	one(adminService).resetPassword(user.getUsername());
            	one(adminService).getUser(user.getUsername()) ;
            }
        });
    	String viewName = controller.resetUserPassword(tableCommand, command, errors,sessionStatus, session, model);
    	assertEquals(SUCCESS_VIEW, viewName);
    	context.assertIsSatisfied();
    }
}

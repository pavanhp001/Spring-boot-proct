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

import java.util.ArrayList;
import java.util.List;

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
import abc.xyz.pts.bcs.admin.web.command.AdminUserAddCommand;
import abc.xyz.pts.bcs.admin.web.validator.AdminUserAddValidator;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;


public class AdminUserAddControllerTest extends TestCase {    
	private static final String ADD_COMMAND = "addUserCommand";
	private static final String SUCCESS_VIEW = "adminUserAdd.view";
    private final Mockery context = new JUnit4Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private AdminUserAddController controller;
    private AdminService adminService;
	private DefaultBeanValidator commonsValidator;
	private AdminUserAddValidator adminUserAddValidator;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private BindException errors;
	private SessionStatus sessionStatus;

   @Override
    public void setUp() throws Exception {

         this.controller = new AdminUserAddController();

        request = new MockHttpServletRequest("POST", "/adminUserAdd.form");
        response = new MockHttpServletResponse();
        errors = new BindException("test", "test");
        sessionStatus =  context.mock(SessionStatus.class);

        // Create mock objects
        adminService = context.mock(AdminService.class);
        commonsValidator = context.mock(DefaultBeanValidator.class);
        adminUserAddValidator = context.mock(AdminUserAddValidator.class);
        ReflectionTestUtils.setField(controller, "adminService", adminService);
        ReflectionTestUtils.setField(controller, "commonsValidator", commonsValidator);
        ReflectionTestUtils.setField(controller, "adminUserAddValidator", adminUserAddValidator);;


    }
   
   public void testSetupForm(){
	   AdminUserAddCommand command = controller.newCommand();
       TableActionCommand tableCommand = controller.newTableCommand();
       String viewName = null;

       MockHttpSession session = new MockHttpSession(); 
       ModelMap model = new ModelMap();
       context.checking(new Expectations() {
           {
               one(sessionStatus).setComplete();
           }
       });
	try {
		viewName = controller.setupForm(tableCommand, command, errors, sessionStatus, session, model);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       assertNotNull(viewName);
       assertEquals(SUCCESS_VIEW, viewName);
    }

   public void testProcessReset(){
	   AdminUserAddCommand command = controller.newCommand();
       MockHttpSession session = new MockHttpSession(); 
       ModelMap model = new ModelMap();
		session.setAttribute(ADD_COMMAND, command);        
       context.checking(new Expectations() {
           {
               one(sessionStatus).setComplete();
           }
       });
       String viewName = null;
		try {
			viewName = controller.cancel(model, sessionStatus, session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       assertNotNull(viewName);
	       command = (AdminUserAddCommand)session.getAttribute(ADD_COMMAND);   
	       assertEquals(SUCCESS_VIEW, viewName);
	       assertNull(command);
	       
	   }
   
  
    public void testNewCommand() throws Exception{
    	AdminUserAddCommand command = controller.newCommand();
    	assertNotNull(command);
	}
	  
    public void testNewTableCommand() throws Exception{
    	TableActionCommand tableCommand = controller.newTableCommand();
	  	assertNotNull(tableCommand);        
	} 
	  
    public void testProcessAdd() throws Exception{
 	   final AdminUserAddCommand command = controller.newCommand();
       TableActionCommand tableCommand = controller.newTableCommand();
        final List<AdminUserAddCommand> results = new ArrayList<AdminUserAddCommand>();
        MockHttpSession session = new MockHttpSession();  
		session.setAttribute(ADD_COMMAND, command);
        ModelMap model = new ModelMap();
        context.checking(new Expectations() {
            {
                one(adminService).addUser(command.getUser()) ;
            }
        });
        context.checking(new Expectations() {
            {
                one(commonsValidator).validate(with(any(AdminUserAddCommand.class)), with(any(BindException.class)));  
                one(adminUserAddValidator).validate(with(any(AdminUserAddCommand.class)), with(any(BindException.class)));                
            }
        });
        String viewName = controller.processAdd(tableCommand, command, errors, session, model);
	    assertEquals(SUCCESS_VIEW, viewName);
	    assertEquals(true, command.isUserAdded());	    
        context.assertIsSatisfied();
    }
  
 
}

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
package abc.xyz.pts.bcs.common.web.controller;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.josso.tc60.agent.jaas.CatalinaSSOUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

/**
 * Test the check login controller functionality, that the user is directed either to a role appropriate
 * login page or to the default if their is no start page defined for their role.
 */
public class CheckLoginTest {
    private Mockery mockContext;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private CheckLogin checkLogin;

    private static final String DEFAULT_VIEW = "default";
    private static final String ROLE_1 = "ROLE1";
    private static final String ROLE_2 = "ROLE2";
    private static final String ROLE_3 = "ROLE3";
    private static final String ROLE_1_START_PAGE = "PAGE1";
    private static final String ROLE_2_START_PAGE = "PAGE2";
    private Mockery classMockcontext = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    /**
     * Set up the objects for the tests.
     */
    @Before
    public void setup(){
        mockContext = new Mockery();
        checkLogin = new CheckLogin();
        checkLogin.setDefaultView(DEFAULT_VIEW);
        Map<String, String> roleStartPages = new HashMap<String, String>();
        roleStartPages.put(ROLE_1, ROLE_1_START_PAGE);
        roleStartPages.put(ROLE_2, ROLE_2_START_PAGE);
        checkLogin.setRoleStartPages(roleStartPages);
        mockRequest = mockContext.mock(HttpServletRequest.class);
        mockResponse = mockContext.mock(HttpServletResponse.class);
    }

    /**
     * This method must be in all mock based tests in order to check that the context has been fully satisfied.
     */
    @After
    public void checkMockContext(){
        mockContext.assertIsSatisfied();
    }

    /**
     * If the role is role 1 then we should be directed to the role 1 start page.
     * @throws Exception If there is an unexpected problem.
     */
    @Test
    public void testKnownRole1() throws Exception {
        //The user has role 1 so that should be checked, role 2 may or may not be checked.
        final CatalinaSSOUser mockCatalinaSSOUser = classMockcontext.mock(CatalinaSSOUser.class);
        final String[] roles = {ROLE_1};
        
        mockContext.checking(new Expectations() {{
            one(mockRequest).getUserPrincipal(); will(returnValue(mockCatalinaSSOUser));
        }});
        classMockcontext.checking(new Expectations() {{
            one(mockCatalinaSSOUser).getRoles(); 
            will(returnValue(roles));
        }});
        ModelAndView result = checkLogin.handleRequestInternal(mockRequest, mockResponse);

        assertEquals("Did not return the correct start page for role 1.", ROLE_1_START_PAGE, result.getViewName());

    }

    /**
     * If the role is role 2 then we should be directed to the role 2 start page.
     * @throws Exception If there is an unexpected problem.
     */
    @Test
    public void testKnownRole2() throws Exception {
        //The user has role 2 so that should be checked, role 1 may or may not be checked.
        final CatalinaSSOUser mockCatalinaSSOUser = classMockcontext.mock(CatalinaSSOUser.class);
        final String[] roles = {ROLE_2};
        mockContext.checking(new Expectations() {{
            one(mockRequest).getUserPrincipal(); will(returnValue(mockCatalinaSSOUser));
        }});
        classMockcontext.checking(new Expectations() {{
            one(mockCatalinaSSOUser).getRoles(); 
            will(returnValue(roles));
        }});
        ModelAndView result = checkLogin.handleRequestInternal(mockRequest, mockResponse);

        assertEquals("Did not return the correct start page for role 2.", ROLE_2_START_PAGE, result.getViewName());

    }

    /**
     * If the role is unknown then we should be directed to the default start page.
     * @throws Exception If there is an unexpected problem.
     */
    @Test
    public void testPrincipalSet() throws Exception {

        //The user has neither role 1 or role 2 so both roles will be checked.
        final CatalinaSSOUser mockCatalinaSSOUser = classMockcontext.mock(CatalinaSSOUser.class);
        final String[] roles = {ROLE_3};
        mockContext.checking(new Expectations() {{
            one(mockRequest).getUserPrincipal(); will(returnValue(mockCatalinaSSOUser));
        }});
        classMockcontext.checking(new Expectations() {{
            one(mockCatalinaSSOUser).getRoles(); 
            will(returnValue(roles));
        }});
        ModelAndView result = checkLogin.handleRequestInternal(mockRequest, mockResponse);

        assertEquals("Did not return the default view.", DEFAULT_VIEW, result.getViewName());
    }
}

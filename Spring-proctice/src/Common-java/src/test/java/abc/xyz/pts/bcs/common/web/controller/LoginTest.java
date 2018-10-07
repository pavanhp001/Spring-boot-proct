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

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.springframework.web.servlet.ModelAndView;
import static org.junit.Assert.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * This class is designed to test the login functionality.
 */
public class LoginTest {
    private Mockery mockContext;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private Login login;
    private Principal testPrincipal;

    public static final String LOGIN_VIEW = "login.view";
    private static final String POST_LOGIN_VIEW = "post.login.view";

    /**
     * Set up the objects for the tests.
     */
    @Before
    public void setup(){
        mockContext = new Mockery();
        login = new Login();
        login.setLoginView(LOGIN_VIEW);
        login.setPostLoginView(POST_LOGIN_VIEW);
        mockRequest = mockContext.mock(HttpServletRequest.class);
        mockResponse = mockContext.mock(HttpServletResponse.class);
        testPrincipal =  new Principal() {
            public String getName() {
                return "TEST";
            }
        };
    }

    /**
     * This method must be in all mock based tests in order to check that the context has been fully satisfied.
     */
    @After
    public void checkMockContext(){
        mockContext.assertIsSatisfied();
    }

    /**
     * If there is no principal then we should be directed to the login view.
     * @throws Exception If there is an unexpected problem.
     */
    @Test
    public void testNoPrincipal() throws Exception {
        mockContext.checking(new Expectations() {{
            one(mockRequest).getUserPrincipal(); will(returnValue(null));
        }});

        ModelAndView result = login.handleRequestInternal(mockRequest, mockResponse);

        assertEquals("Did not get login view.", LOGIN_VIEW, result.getViewName());
    }

    /**
     * If there is a principal then we should be directed to the post login view.
     * @throws Exception If there is an unexpected problem.
     */
    @Test
    public void testPrincipalSet() throws Exception {
        mockContext.checking(new Expectations() {{
            one(mockRequest).getUserPrincipal(); will(returnValue(testPrincipal));
        }});

        ModelAndView result = login.handleRequestInternal(mockRequest, mockResponse);

        assertEquals("Did not get post login view.", POST_LOGIN_VIEW, result.getViewName());
    }
}

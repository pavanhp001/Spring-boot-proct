package abc.xyz.pts.bcs.common.web.user;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;


public class TestCheckUserRole extends TestCase {

	private Mockery context;
	private HttpServletRequest request;

    @Before
    public void setUp() throws Exception {
    	context = new Mockery();
    	request =  context.mock(HttpServletRequest.class);
    	System.setProperty("SEARCH_MENU", "permissions");
    }

    public void testCheckUserRole() {
        context.checking(new Expectations() {
        	{
	            one(request).isUserInRole(with(any(String.class)));
	            will(returnValue(Boolean.TRUE));
        	}
        });
        assertFalse(CheckUserRole.checkUserRole(null, request));
        assertTrue(CheckUserRole.checkUserRole(new String[] {"1", "2"}, request));
    }

    public void testGetFunctionRoles() {
        // Test null
        assertNull(CheckUserRole.getFunctionRoles(null));

        // Test wrong value
        assertNull(CheckUserRole.getFunctionRoles("WRONG"));

        // Test correct value
        assertNotNull(CheckUserRole.getFunctionRoles("SEARCH_MENU"));
    }
}

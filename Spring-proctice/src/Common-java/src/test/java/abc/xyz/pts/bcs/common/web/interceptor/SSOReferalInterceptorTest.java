package abc.xyz.pts.bcs.common.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.josso.gateway.SSONameValuePair;
import org.josso.gateway.identity.SSOUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.mvc.Controller;

public class SSOReferalInterceptorTest {

    private Mockery mockContext;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private SSOReferalInterceptor ssoReferalInterceptor;
    private Object mockHandler;
    private SSOUser mockPrincipal = null;
    private SSONameValuePair[] emptySSOProps = new SSONameValuePair[0];
    private static String expiredPwdChgDate = "20010101010100Z";
    private static String CHG_PWD_URL = "/changePassword.form";
    @Before
    public void setup(){
        mockContext = new Mockery();
        mockRequest = mockContext.mock(HttpServletRequest.class);
        mockResponse = mockContext.mock(HttpServletResponse.class);
        ssoReferalInterceptor = new SSOReferalInterceptor();
        mockPrincipal = mockContext.mock(SSOUser.class);
        ssoReferalInterceptor.setChangePasswordView(CHG_PWD_URL);
        mockHandler = mockContext.mock(Controller.class);

    }
    
    @After
    public void checkMockContext(){
        mockContext.assertIsSatisfied();
    }
    
    @Test
    public void testPwdResetRedirect() throws Exception {
        final SSONameValuePair pwdResetProp = new SSONameValuePair("passwordReset", "TRUE");
        mockContext.checking(new Expectations() {{
            one(mockRequest).getQueryString(); will(returnValue(""));
            one(mockRequest).getHeader("Referer"); will(returnValue("http://localhost:8080/josso/signon/login.do?josso_back_to=http://localhost:8080/ui/josso_security_check"));
            one(mockRequest).getUserPrincipal(); will(returnValue(mockPrincipal));
            one(mockPrincipal).getProperties(); will(returnValue(new SSONameValuePair[] { pwdResetProp }));
            one(mockResponse).sendRedirect(CHG_PWD_URL);
        }});
        
        boolean result = ssoReferalInterceptor.preHandle(mockRequest, mockResponse, mockHandler);
        org.junit.Assert.assertFalse(result);
    }
    
    @Test
    public void testPwdWarningRedirect() throws Exception {
        final SSONameValuePair pwdChangeTimeProp = new SSONameValuePair("pwdChangedTime", expiredPwdChgDate);
        final SSONameValuePair expiryTime = new SSONameValuePair("secondsBeforePasswordExpiryWarning", "0");
        
        mockContext.checking(new Expectations() {{
            one(mockRequest).getQueryString(); will(returnValue(""));
            one(mockRequest).getHeader("Referer"); will(returnValue("http://localhost:8080/josso/signon/login.do?josso_back_to=http://localhost:8080/ui/josso_security_check"));
            one(mockRequest).getUserPrincipal(); will(returnValue(mockPrincipal));
            one(mockPrincipal).getProperties(); will(returnValue(new SSONameValuePair[] { pwdChangeTimeProp, expiryTime }));
            one(mockResponse).sendRedirect(CHG_PWD_URL);
        }});
        
        boolean result = ssoReferalInterceptor.preHandle(mockRequest, mockResponse, mockHandler);
        org.junit.Assert.assertFalse(result);
    }
    
    @Test
    public void testNonRedirect() throws Exception {
        mockContext.checking(new Expectations() {{
            one(mockRequest).getQueryString(); will(returnValue(""));
            one(mockRequest).getHeader("Referer"); will(returnValue("http://localhost:8080/josso/signon/login.do?josso_back_to=http://localhost:8080/ui/josso_security_check"));
            one(mockRequest).getUserPrincipal(); will(returnValue(mockPrincipal));
            one(mockPrincipal).getProperties(); will(returnValue(emptySSOProps));
        }});
        boolean result = ssoReferalInterceptor.preHandle(mockRequest, mockResponse, mockHandler);
        org.junit.Assert.assertTrue(result);
    }
    
    @Test
    public void testPreviousRedirect() throws Exception {
        mockContext.checking(new Expectations() {{
            one(mockRequest).getQueryString(); will(returnValue("homePage=true"));
            one(mockRequest).getHeader("Referer"); will(returnValue("http://localhost:8080/josso/signon/login.do?josso_back_to=http://localhost:8080/ui/josso_security_check"));
        }});
        boolean result = ssoReferalInterceptor.preHandle(mockRequest, mockResponse, mockHandler);
        org.junit.Assert.assertTrue(result);
    }

}

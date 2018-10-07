package abc.xyz.pts.bcs.common.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.josso.gateway.identity.SSOUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockPageContext;
import abc.xyz.pts.bcs.common.bean.UserDetails;
import abc.xyz.pts.bcs.common.dao.utils.Constants;

public class TestUserRoleTag extends TestCase {

    private UserRoleTag tag;
    private PageContext pc;
    private UserDetails ud;
    private String testString = "test";
    private HttpServletRequest mockRequest;
    private SSOUser mockPrincipal = null;
    private PageContext mockPageContext;
    private static final Map<String, String[]> functionRoleList = new HashMap<String, String[]>();
    static {
        functionRoleList.put("TEST_GROUP", new String[]{"ABA","ABM","CCS"});
    }
    private Mockery mockContext = new JUnit4Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    @Before
    public void setUp() throws Exception {
        mockRequest =  mockContext.mock(HttpServletRequest.class);
        mockPageContext  =  mockContext.mock(PageContext.class);
        mockPrincipal  =  mockContext.mock(SSOUser.class);
        tag = new UserRoleTag();
        pc = new MockPageContext();
        ud = new UserDetails();
        pc.getRequest().setAttribute(Constants.USER_PROFILE_KEY, ud);
        tag.setPageContext(pc);
        CheckUserRole.setFunctionalRoles(functionRoleList);
        System.setProperty("TEST_GROUP", "Permissions");
    }

    @After
    public void tearDown() throws Exception {
        tag = null;
        pc = null;
    }

    @Test
    public void testDoStartTagNoUserRole() throws Exception {
        tag.setRoles("ZZZ");
        assertEquals(UserRoleTag.SKIP_BODY, tag.doStartTag());
        tag.setRoles("CCS");
        assertEquals(UserRoleTag.SKIP_BODY, tag.doStartTag());

        tag.setRoleGroup("TEST_GROUP");
        assertEquals(UserRoleTag.SKIP_BODY, tag.doStartTag());
    }

    @Test
    public void testDoStartTagInvalidRole() throws Exception {
        ud.setRoles(new String[] {"AAA"});
        tag.setRoles("ZZZ");
        assertEquals(UserRoleTag.SKIP_BODY, tag.doStartTag());
        tag.setRoles("CCS");
        assertEquals(UserRoleTag.SKIP_BODY, tag.doStartTag());
    }

    @Test
    public void testDoStartTagValidRole() throws Exception {
        mockContext.checking(new Expectations() {{
            one(mockPageContext).getRequest(); will(returnValue(mockRequest));
            one(mockRequest).getUserPrincipal(); will(returnValue(mockPrincipal));
            one(mockRequest).isUserInRole(with(any(String.class))); will(returnValue(Boolean.TRUE));
        }}); 
        tag.setPageContext(mockPageContext);
        tag.setRoles("CCS");
        assertEquals(UserRoleTag.EVAL_BODY_INCLUDE, tag.doStartTag());
    }

    @Test
    public void testDoStartTagInvalidRoleGroup() throws Exception {
        mockContext.checking(new Expectations() {{
            atLeast(2).of(mockPageContext).getRequest(); will(returnValue(mockRequest));
            atLeast(2).of(mockRequest).getUserPrincipal(); will(returnValue(mockPrincipal));
            atLeast(2).of(mockRequest).isUserInRole(with(any(String.class))); will(returnValue(Boolean.FALSE));
        }}); 
        tag.setPageContext(mockPageContext);
        tag.setRoleGroup("BBB");
        assertEquals(UserRoleTag.SKIP_BODY, tag.doStartTag());
        tag.setRoleGroup("CCC");
        assertEquals(UserRoleTag.SKIP_BODY, tag.doStartTag());
    }

    @Test
    public void testDoStartTagInvalidRoleGroupNot() throws Exception {
        mockContext.checking(new Expectations() {{
            atLeast(2).of(mockPageContext).getRequest(); will(returnValue(mockRequest));
            atLeast(2).of(mockRequest).getUserPrincipal(); will(returnValue(mockPrincipal));
            atLeast(2).of(mockRequest).isUserInRole(with(any(String.class))); will(returnValue(Boolean.FALSE));
        }}); 
        tag.setPageContext(mockPageContext);
        tag.setRoleGroup("BBB");
        tag.setNot(true);
        assertEquals(UserRoleTag.EVAL_BODY_INCLUDE, tag.doStartTag());
        tag.setRoleGroup("CCC");
        assertEquals(UserRoleTag.EVAL_BODY_INCLUDE, tag.doStartTag());
    }

    @Test
    public void testDoStartTagValidRoleGroup() throws Exception {
        mockContext.checking(new Expectations() {{
            atLeast(1).of(mockPageContext).getRequest(); will(returnValue(mockRequest));
            atLeast(1).of(mockRequest).getUserPrincipal(); will(returnValue(mockPrincipal));
            atLeast(1).of(mockRequest).isUserInRole(with(any(String.class))); will(returnValue(Boolean.TRUE));
        }}); 
        tag.setPageContext(mockPageContext);
        tag.setRoleGroup("TEST_GROUP");
        assertEquals(UserRoleTag.EVAL_BODY_INCLUDE, tag.doStartTag());
    }

    @Test
    public void testDoStartTagValidRoleGroupNot() throws Exception {
        tag.setRoleGroup("TEST_GROUP");
        tag.setNot(true);
        assertEquals(UserRoleTag.SKIP_BODY, tag.doStartTag());
    }

    @Test
    public void testDoStartTagHide() throws Exception {
        ud.setRoles(new String[] {"CCS"});
        tag.setRoles("CCS");

        tag.setHide(true);
        assertEquals(UserRoleTag.SKIP_BODY, tag.doStartTag());
    }

    @Test
    public void testDoStartTagAirport() throws Exception {
        mockContext.checking(new Expectations() {{
            atLeast(2).of(mockPageContext).getRequest(); will(returnValue(mockRequest));
            atLeast(2).of(mockRequest).getUserPrincipal(); will(returnValue(mockPrincipal));
            atLeast(2).of(mockRequest).isUserInRole(with(any(String.class))); will(returnValue(Boolean.TRUE));
        }}); 
        tag.setPageContext(mockPageContext);
        tag.setRoles("CCS");
        assertEquals(UserRoleTag.EVAL_BODY_INCLUDE, tag.doStartTag());
    }

    @Test
    public void testRoles() {
        assertNull(tag.getRoles());
        tag.setRoles(testString);
        assertEquals(testString, tag.getRoles());
        tag.setRoles(null);
        assertNull(tag.getRoles());
    }
    @Test
    public void testRoleGroup() {
        assertNull(tag.getRoleGroup());
        tag.setRoleGroup(testString);
        assertEquals(testString, tag.getRoleGroup());
        tag.setRoleGroup(null);
        assertNull(tag.getRoleGroup());
    }

    @Test
    public void testHide() {
        assertFalse(tag.getHide());
        tag.setHide(false);
        assertFalse(tag.getHide());
        tag.setHide(true);
        assertTrue(tag.getHide());
    }

    @Test
    public void testNot() {
        assertFalse(tag.getHide());
        tag.setNot(false);
        assertFalse(tag.getNot());
        tag.setNot(true);
        assertTrue(tag.getNot());
    }
}

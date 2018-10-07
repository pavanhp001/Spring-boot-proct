package abc.xyz.pts.bcs.admin.dao;

import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.core.ContextExecutor;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapOperations;
import org.springframework.ldap.core.simple.SimpleLdapTemplate;

import abc.xyz.pts.bcs.admin.AbstractTestCase;
import abc.xyz.pts.bcs.admin.business.UserStatus;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.exception.UserExistsException;
import abc.xyz.pts.bcs.admin.ldap.ContextMapperCallbackHandlerWithControls;
import abc.xyz.pts.bcs.admin.ldap.control.PasswordModifyControl.PasswordModifyResponse;
import abc.xyz.pts.bcs.admin.ldap.support.AccountUsabilityDirContextProcessor;
import abc.xyz.pts.bcs.admin.web.command.AdminUserSearchCommand;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;

public class UserDaoTest extends AbstractTestCase{

    private UserDao dao = null;
    private SimpleLdapTemplate mockLdapTemplate = null;
    private SimpleLdapTemplate mockPagingLdapTemplate = null;
    private SimpleLdapTemplate mockReadOnlyLdapTemplate = null;
    private LdapUserAttributesMapper mockMapper = null;
    private Attributes mockAttributes = null;
    private User user = null;
    
    @Before
    public void setUp(){
       super.setup();
	dao = new UserDao();
       mockAttributes = mockContext.mock(Attributes.class);
       mockLdapTemplate = mockContext.mock(SimpleLdapTemplate.class, "mockLdapTemplate");
       mockPagingLdapTemplate = mockContext.mock(SimpleLdapTemplate.class, "mockPagingLdapTemplate");
       mockReadOnlyLdapTemplate = mockContext.mock(SimpleLdapTemplate.class);
       mockMapper = mockContext.mock(LdapUserAttributesMapper.class);
       dao.setLdapTemplate(mockLdapTemplate);
       dao.setPagingLdapTemplate(mockPagingLdapTemplate);
       dao.setReadOnlyLdapTemplate(mockReadOnlyLdapTemplate);
       dao.setUserAttributesMapper(mockMapper);
       dao.setRoleAbsoluteBaseName("ou=test");
       dao.setUsersAbsoluteBaseName("ou=testUser2");
       user = new User();
       user.setUsername("username");
       user.setForename("forename");
       user.setLastname("lastname");
       user.setMobileNumber("123456");
       user.setFaxNumber("123456");
       user.setEmail("test@test.com");
       user.setLocation("FCO");
       user.setUserStatus(UserStatus.ENABLED);
       user.setRoles(new String[]{"ADM"});
    }
    
    @Test
    public void testAddUser() throws Exception{
        final LdapOperations mockOperations = mockContext.mock(LdapOperations.class);
        final PasswordModifyResponse mockPasswordModifyResponse = mockContext.mock(PasswordModifyResponse.class);
        mockContext.checking(new Expectations(){
            {
                exactly(2).of(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.USERNAME);
                will(returnValue("cn"));
                one(mockMapper).mapToAttributes(user, "ou=test");
                will(returnValue(mockAttributes));
                one(mockLdapTemplate).bind(with(any(DistinguishedName.class)), with(any(Object.class)), with(any(Attributes.class)));
                one(mockLdapTemplate).getLdapOperations();will(returnValue(mockOperations));
                one(mockOperations).executeReadOnly(with(any(ContextExecutor.class)));will(returnValue(mockPasswordModifyResponse));
                one(mockPasswordModifyResponse).getPassword();will(returnValue("testPassword"));
            }
        });
        dao.addUser(user);
    }
    
    @Test
    public void testAddUserUserExists() throws Exception{
        final NameAlreadyBoundException mockException = mockContext.mock(NameAlreadyBoundException.class);
        mockContext.checking(new Expectations(){
            {
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.USERNAME);
                will(returnValue("cn"));
                one(mockMapper).mapToAttributes(user, "ou=test");
                will(returnValue(mockAttributes));
                one(mockLdapTemplate).bind(with(any(DistinguishedName.class)), with(any(Object.class)), with(any(Attributes.class)));
                will(throwException(mockException));
                one(mockException).fillInStackTrace();
            }
        });
        try{
            dao.addUser(user);
            Assert.fail();
        }
        catch(UserExistsException uee){
            Assert.assertTrue(true);
        }
    }
    
    @Test
    public void testGetUser() throws Exception{
        final LdapOperations mockLdapOps = mockContext.mock(LdapOperations.class);
        mockContext.checking(new Expectations(){
            {
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.USERNAME);will(returnValue("test1"));
                
                one(mockReadOnlyLdapTemplate).getLdapOperations();
                will(returnValue(mockLdapOps));
                one(mockLdapOps).search(with(any(String.class)), with(any(String.class)), with(any(SearchControls.class)), with(any(ContextMapperCallbackHandlerWithControls.class)), with(any(AccountUsabilityDirContextProcessor.class)));
                one(mockMapper).getAttributeNamesArray();will(returnValue(new String[]{"username"}));
            }
        });
        User actual = dao.getUser("testUser");
    }
    
    @Test
    public void testGetSearchUsers() throws Exception{
       	final AdminUserSearchCommand adminUserSearchCommand = new AdminUserSearchCommand();
        adminUserSearchCommand.setUser(user);
        final TableActionCommand adminSearchTableCommand = new TableActionCommand();
        adminSearchTableCommand.setSortBy("username");
        
        final LdapOperations mockLdapOps = mockContext.mock(LdapOperations.class);
        mockContext.checking(new Expectations(){
            {
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.USERNAME);will(returnValue("test1"));
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.USERNAME.value());will(returnValue("test2"));
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.FORENAME);will(returnValue("test3"));
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.LASTNAME);will(returnValue("test4"));
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.MOBILE);will(returnValue("test6"));
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.FAX);will(returnValue("test7"));
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.EMAIL);will(returnValue("test8"));
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.LOCATION);will(returnValue("test9"));
                exactly(2).of(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.ACCOUNT_DISABLED);will(returnValue("test11"));
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.ROLE);will(returnValue("test13"));
                one(mockPagingLdapTemplate).getLdapOperations();
                will(returnValue(mockLdapOps));
                one(mockLdapOps).search(with(any(String.class)), with(any(String.class)), with(any(SearchControls.class)), with(any(ContextMapperCallbackHandlerWithControls.class)), with(any(AccountUsabilityDirContextProcessor.class)));
                one(mockMapper).getAttributeNamesArray();will(returnValue(new String[]{"username"}));
            }
        });
        
        dao.getSearchUsers(adminUserSearchCommand, adminSearchTableCommand);
    }
    
    @Test
    public void testGetUsersMinimalCriteria() throws Exception{
        User userTest = new User();
        userTest.setUsername("test");
        userTest.setUserStatus(UserStatus.DISABLED);
 
        final AdminUserSearchCommand adminUserSearchCommand = new AdminUserSearchCommand();
        adminUserSearchCommand.setUser(userTest);
        final TableActionCommand adminSearchTableCommand = new TableActionCommand();
        adminSearchTableCommand.setSortBy("username");
        
        final LdapOperations mockLdapOps = mockContext.mock(LdapOperations.class);
        mockContext.checking(new Expectations(){
            {
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.USERNAME);will(returnValue("test1"));
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.USERNAME.value());will(returnValue("test2"));
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.ACCOUNT_DISABLED);will(returnValue("test11"));
                one(mockPagingLdapTemplate).getLdapOperations();
                will(returnValue(mockLdapOps));
                one(mockLdapOps).search(with(any(String.class)), with(any(String.class)), with(any(SearchControls.class)), with(any(ContextMapperCallbackHandlerWithControls.class)), with(any(AccountUsabilityDirContextProcessor.class)));
                one(mockMapper).getAttributeNamesArray();will(returnValue(new String[]{"username"}));
            }
        });
        
        dao.getSearchUsers(adminUserSearchCommand, adminSearchTableCommand);
    }
    
    @Test
    public void testDeleteUser() throws Exception{
        mockContext.checking(new Expectations(){
            {
                one(mockMapper).mapAttributeName(UserAttributesMapper.AttributeName.USERNAME);will(returnValue("test1"));
                one(mockLdapTemplate).unbind(with(any(DistinguishedName.class)));
            }
        });
        dao.deleteUser("test");
    }    
}

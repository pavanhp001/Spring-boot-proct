package abc.xyz.pts.bcs.admin.business.impl;

import java.util.Locale;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import abc.xyz.pts.bcs.admin.AbstractTestCase;
import abc.xyz.pts.bcs.admin.business.PasswordGenerator;
import abc.xyz.pts.bcs.admin.dao.AirportDao;
import abc.xyz.pts.bcs.admin.dao.PasswordChangeResult;
import abc.xyz.pts.bcs.admin.dao.UserDao;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.exception.InvalidAirportException;
import abc.xyz.pts.bcs.admin.exception.UserExistsException;
import abc.xyz.pts.bcs.admin.web.command.AdminUserSearchCommand;
import abc.xyz.pts.bcs.common.mail.EmailService;
import abc.xyz.pts.bcs.common.web.command.TableActionCommand;
import static org.junit.Assert.*;

public class LdapAdminServiceImplTest extends AbstractTestCase{

    private LdapAdminServiceImpl adminService;
    private UserDao mockDao;
    private AirportDao mockAirportDao;
    private EmailService mockEmailService;
    private ApplicationContext mockAppContext;
    private PasswordGenerator mockPasswordGenerator;
    
    @Before
    public void setUp(){
        super.setup();
        adminService = new LdapAdminServiceImpl();
        mockDao = mockContext.mock(UserDao.class);
        mockAirportDao = mockContext.mock(AirportDao.class);
        mockEmailService = mockContext.mock(EmailService.class);
        mockAppContext = mockContext.mock(ApplicationContext.class);
        mockPasswordGenerator = mockContext.mock(PasswordGenerator.class);
        adminService.setApplicationContext(mockAppContext);
        adminService.setEmailService(mockEmailService);
        adminService.setLocale(Locale.UK);
        adminService.setUserDao(mockDao);
        adminService.setAirportDao(mockAirportDao);
    }
    
    @Test
    public void testGetSearchUsers() throws Exception{
        User user = new User();
        user.setUsername("testUsername");
        final AdminUserSearchCommand adminUserSearchCommand = new AdminUserSearchCommand();
        adminUserSearchCommand.setUser(user);
        final TableActionCommand adminSearchTableCommand = new TableActionCommand();
        
        
        mockContext.checking(new Expectations(){
            {
            	one(mockDao).getSearchUsers(adminUserSearchCommand, adminSearchTableCommand);
            }
        });
        
        adminService.getSearchUsers(adminUserSearchCommand,adminSearchTableCommand);
    }
    
    @Test
    public void testGetUser() throws Exception{
        final String username = "testUsername";
        
        mockContext.checking(new Expectations(){
            {
                one(mockDao).getUser(username);
            }
        });
        
        adminService.getUser(username);
    }
    
    @Test
    public void testUpdateUser() throws Exception{
        final User user = new User();
        user.setUsername("testUsername");
        user.setRoles(new String[]{"IAG"});
                
        mockContext.checking(new Expectations(){
            {
                one(mockDao).updateUser(user);
            }
        });
        
        adminService.updateUser(user);
    }
    
    @Test
    public void testUpdateUserNonIAG() throws Exception{
        final User user = new User();
        user.setUsername("testUsername");
        user.setRoles(new String[]{"SUP"});
        user.setLocation("fco");
                
        mockContext.checking(new Expectations(){
            {
                one(mockDao).updateUser(user);
                one(mockAirportDao).exists("FCO"); will(returnValue(true));
            }
        });
        
        adminService.updateUser(user);
    }
    
    
    @Test
    public void testUpdateUserInvalidAirport() throws Exception{
        final User user = new User();
        user.setUsername("testUsername");
        user.setRoles(new String[]{"BAM"});
        user.setLocation("FCA");
        mockContext.checking(new Expectations(){
            {
                one(mockAirportDao).exists("FCA"); will(returnValue(false));
            }
        });
        
        try{
            adminService.updateUser(user);
            Assert.fail();
        }
        catch(InvalidAirportException iae){
            Assert.assertTrue(true);
        }
    }
    
    @Test
    public void testDeleteUser() throws Exception{
        final String username = "testUsername";
        
        mockContext.checking(new Expectations(){
            {
                one(mockDao).deleteUser(username);
            }
        });
        
        adminService.deleteUser(username); 
    }
    
    @Test
    public void testAddUser() throws Exception{
        final User user = new User();
        user.setUsername("testUsername");
        user.setEmail("test@test.com");
        user.setForename("forename");
        user.setLastname("lastname");
        user.setRoles(new String[]{"BAM", "DAN"});
        user.setLocation("fco");
        user.setPassword("ASD.-dss22");
        mockContext.checking(new Expectations(){
            {
                one(mockDao).addUser(user);
                exactly(2).of(mockEmailService).sendMail(with(any(String.class)), with(any(String.class)), with(any(String.class)));
                //exactly(2).of(mockAppContext).getMessage("user.role.BAM", new String[0], Locale.UK);
                //will(returnValue("testBAM"));
                //exactly(2).of(mockAppContext).getMessage("user.role.DAN", null, Locale.UK);
                //will(returnValue("testDAN"));
                one(mockAppContext).getMessage("admin.create.user.email.text", null, Locale.UK);
                will(returnValue("testBody"));
                one(mockAppContext).getMessage("admin.email.username.subject", null, Locale.UK);
                will(returnValue("testSubject"));
                one(mockAppContext).getMessage("admin.email.password.text", null, Locale.UK);
                will(returnValue("testPasswordBody"));
                one(mockAppContext).getMessage("admin.email.password.subject", null, Locale.UK);
                will(returnValue("testPasswordSubject"));
                //one(mockPasswordGenerator).generate();
                //will(returnValue("ASD.-dss22"));
                one(mockAirportDao).exists("FCO"); will(returnValue(true));
            }
        });
        adminService.addUser(user);
    }
    
    @Test
    public void testAddUserUserExists() throws Exception{
        final User user = new User();
        user.setUsername("testUsername");
        user.setEmail("test@test.com");
        user.setForename("forename");
        user.setLastname("lastname");
        user.setRoles(new String[]{"BAM", "DAN"});
        user.setLocation("fco");
        final UserExistsException userExistsException = new UserExistsException(new Exception());
        mockContext.checking(new Expectations(){
            {
                one(mockDao).addUser(user); will(throwException(userExistsException));
                one(mockAirportDao).exists("FCO"); will(returnValue(true));
            }
        });
        try{
            adminService.addUser(user);
            Assert.fail();
        }catch(UserExistsException uee){
            Assert.assertTrue(true);
        }
    }
    
    
    @Test
    public void testAddUserInvalidAirport() throws Exception{
        final User user = new User();
        user.setUsername("testUsername");
        user.setEmail("test@test.com");
        user.setForename("forename");
        user.setLastname("lastname");
        user.setRoles(new String[]{"IAG", "AUD"});
        user.setLocation("TEST");
        mockContext.checking(new Expectations(){
            {
                one(mockAirportDao).exists("TEST"); will(returnValue(false));
            }
        });
        try{
            adminService.addUser(user);
            Assert.fail();
        }
        catch(InvalidAirportException iae){
            Assert.assertTrue(true);
        }
    }
    
    @Test
    public void testResetPassword() throws Exception{
        final User user = new User();
        user.setUsername("testUsername");
        user.setEmail("test@test.com");
        user.setForename("forename");
        user.setLastname("lastname");
        user.setRoles(new String[]{"BAM", "DAN"});
        mockContext.checking(new Expectations(){
            {
                one(mockDao).resetPassword(user.getUsername());
                one(mockDao).getUser("user");
                will(returnValue(user));
                one(mockEmailService).sendMail(with(any(String.class)), with(any(String.class)), with(any(String.class)));
                one(mockAppContext).getMessage("admin.email.password.subject", null, Locale.UK);
                will(returnValue("testSubject"));
                one(mockAppContext).getMessage("admin.email.password.text", null, Locale.UK);
                will(returnValue("testPasswordBody"));
            }
        });
        adminService.resetPassword("user");
    }
    
    public void testChangePassword() throws Exception {
        final User user = new User();
        user.setUsername("testUsername");
        user.setEmail("test@test.com");
        user.setForename("forename");
        user.setLastname("lastname");
        user.setRoles(new String[]{"ADM", "AUD"});
        
        mockContext.checking(new Expectations(){
            {
                one(mockDao).changePassword(user.getUsername(), "oldPassword", "newPassword");
                will(returnValue(PasswordChangeResult.SUCCESS));
            }
        });
        PasswordChangeResult result = adminService.changePassword("testUsername", "oldPassword", "newPassword");
        assertEquals(PasswordChangeResult.SUCCESS, result);
    }
    
    public void testChangePasswordInvalidOldPassword() throws Exception {
        final User user = new User();
        user.setUsername("testUsername");
        user.setEmail("test@test.com");
        user.setForename("forename");
        user.setLastname("lastname");
        user.setRoles(new String[]{"ADM", "AUD"});
        
        mockContext.checking(new Expectations(){
            {
                one(mockDao).changePassword(user.getUsername(), "oldPassword", "newPassword");
                will(returnValue(PasswordChangeResult.INVALID_OLD_PWD));
            }
        });
        PasswordChangeResult result = adminService.changePassword("testUsername", "oldPassword", "newPassword");
        assertEquals(PasswordChangeResult.INVALID_OLD_PWD, result);
    }
    
    public void testChangePasswordTooYoung() throws Exception {
        final User user = new User();
        user.setUsername("testUsername");
        user.setEmail("test@test.com");
        user.setForename("forename");
        user.setLastname("lastname");
        user.setRoles(new String[]{"ADM", "AUD"});
        
        mockContext.checking(new Expectations(){
            {
                one(mockDao).changePassword(user.getUsername(), "oldPassword", "newPassword");
                will(returnValue(PasswordChangeResult.TOO_YOUNG));
            }
        });
        PasswordChangeResult result = adminService.changePassword("testUsername", "oldPassword", "newPassword");
        assertEquals(PasswordChangeResult.TOO_YOUNG, result);
    }
    
    public void testChangePasswordTooShort() throws Exception {
        final User user = new User();
        user.setUsername("testUsername");
        user.setEmail("test@test.com");
        user.setForename("forename");
        user.setLastname("lastname");
        user.setRoles(new String[]{"ADM", "AUD"});
        
        mockContext.checking(new Expectations(){
            {
                one(mockDao).changePassword(user.getUsername(), "oldPassword", "newPassword");
                will(returnValue(PasswordChangeResult.TOO_SHORT));
            }
        });
        PasswordChangeResult result = adminService.changePassword("testUsername", "oldPassword", "newPassword");
        assertEquals(PasswordChangeResult.TOO_SHORT, result);
    }
    
    public void testChangePasswordRejected() throws Exception {
        final User user = new User();
        user.setUsername("testUsername");
        user.setEmail("test@test.com");
        user.setForename("forename");
        user.setLastname("lastname");
        user.setRoles(new String[]{"ADM", "AUD"});
        
        mockContext.checking(new Expectations(){
            {
                one(mockDao).changePassword(user.getUsername(), "oldPassword", "newPassword");
                will(returnValue(PasswordChangeResult.CHG_REJECTED));
            }
        });
        PasswordChangeResult result = adminService.changePassword("testUsername", "oldPassword", "newPassword");
        assertEquals(PasswordChangeResult.CHG_REJECTED, result);
    }
}

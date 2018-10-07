package abc.xyz.pts.bcs.admin.web.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindException;

import abc.xyz.pts.bcs.admin.AbstractTestCase;
import abc.xyz.pts.bcs.admin.business.AdminService;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.web.command.AdminChangePasswordCommand;
import abc.xyz.pts.bcs.common.enums.UserPermissionType;

public class AdminChangePasswordTest extends AbstractTestCase {

    private AdminService adminService;
    private AdminChangePasswordValidator adminChangePasswordValidator = new AdminChangePasswordValidator();
    private User user = new User();

    private static final String NEW_PASSWORD = "NewPa$$";
    private static final String MISTYPED_NEW_PASSWORD = "NewPas$";
    private static final String TOO_SHORT_NEW_PASSWORD = "PW";
    private static final String ADMIN_NEW_PASSWORD = "NewPa$$word1";
    private static final String USERNAME = "John$mith12";
    private static final String ADM_PERMISSION = UserPermissionType.USER_WRITE.getPermission();
    
    @Override
    @Before
    public void setup() {
        super.setup();

        adminService = mockContext.mock(AdminService.class);
        ReflectionTestUtils.setField(adminChangePasswordValidator, "adminService", adminService);
        mockContext.checking(new Expectations() {
            {
                allowing(adminService).getUser(with(any(String.class)));
                will(returnValue(user));
            }
        });
    }

    @Test
    public void testSuccessfulNewPassword() throws Exception {
        final AdminChangePasswordCommand command = new AdminChangePasswordCommand();
        command.setNewPassword(NEW_PASSWORD);
        command.setConfirmPassword(NEW_PASSWORD);
        command.setUserName(USERNAME);
        user.setRoles(new String[]{""});

        final BindException errorsBlank = new BindException(command, "blank");
        adminChangePasswordValidator.validate(command, errorsBlank);

        assertFalse(errorsBlank.hasErrors());
    }
    
    @Test
    public void testTooShortNewPassword() throws Exception {
        final AdminChangePasswordCommand command = new AdminChangePasswordCommand();
        command.setNewPassword(TOO_SHORT_NEW_PASSWORD);
        command.setConfirmPassword(TOO_SHORT_NEW_PASSWORD);
        command.setUserName(USERNAME);
        user.setRoles(new String[]{""});

        final BindException errorsBlank = new BindException(command, "blank");
        adminChangePasswordValidator.validate(command, errorsBlank);

        assertTrue(errorsBlank.hasErrors());
    }
    
    @Test
    public void testMissingNewPassword() throws Exception {
        final AdminChangePasswordCommand command = new AdminChangePasswordCommand();
        command.setNewPassword(null);
        command.setConfirmPassword(null);
        command.setUserName(USERNAME);
        user.setRoles(new String[]{""});

        final BindException errorsBlank = new BindException(command, "blank");
        adminChangePasswordValidator.validate(command, errorsBlank);

        assertTrue(errorsBlank.hasErrors());
    }
    
    @Test
    public void testMistypedNewPassword() throws Exception {
        final AdminChangePasswordCommand command = new AdminChangePasswordCommand();
        command.setNewPassword(NEW_PASSWORD);
        command.setConfirmPassword(MISTYPED_NEW_PASSWORD);
        command.setUserName(USERNAME);
        user.setRoles(new String[]{""});

        final BindException errorsBlank = new BindException(command, "blank");
        adminChangePasswordValidator.validate(command, errorsBlank);

        assertTrue(errorsBlank.hasErrors());
    }
    
    @Test
    public void testSuccessfulAdminNewPassword() throws Exception {
        final AdminChangePasswordCommand command = new AdminChangePasswordCommand();
        command.setNewPassword(ADMIN_NEW_PASSWORD);
        command.setConfirmPassword(ADMIN_NEW_PASSWORD);
        command.setUserName(USERNAME);
        user.setRoles(new String[]{ADM_PERMISSION});

        final BindException errorsBlank = new BindException(command, "blank");
        adminChangePasswordValidator.validate(command, errorsBlank);

        assertFalse(errorsBlank.hasErrors());
    }
    
    @Test
    public void testTooShortAdminNewPassword() throws Exception {
        final AdminChangePasswordCommand command = new AdminChangePasswordCommand();
        command.setNewPassword(NEW_PASSWORD);
        command.setConfirmPassword(NEW_PASSWORD);
        command.setUserName(USERNAME);
        user.setRoles(new String[]{ADM_PERMISSION});

        final BindException errorsBlank = new BindException(command, "blank");
        adminChangePasswordValidator.validate(command, errorsBlank);

        assertTrue(errorsBlank.hasErrors());
    }

    @Test
    public void testThreeConsecutiveCharecters() throws Exception {

        final AdminChangePasswordCommand command = new AdminChangePasswordCommand();
        command.setNewPassword("RedIyy123YatTpu");
        command.setConfirmPassword("RedIyy123YatTpu");
        command.setUserName("ReYaTaPPU123");
        user.setRoles(new String[]{""});

        final BindException errorsBlank = new BindException(command, "blank");
        adminChangePasswordValidator.validate(command, errorsBlank);

        assertTrue(errorsBlank.hasErrors());
    }

}

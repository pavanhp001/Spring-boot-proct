package abc.xyz.pts.bcs.admin.web.validator;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Errors;

import abc.xyz.pts.bcs.admin.AbstractTestCase;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.web.command.AdminUserAddCommand;
import abc.xyz.pts.bcs.admin.web.command.AdminUserUpdateCommand;
import abc.xyz.pts.bcs.admin.web.validator.AdminUserUpdateValidator;

public class AdminUserUpdateValidatorTest extends AbstractTestCase {

    private AdminUserUpdateValidator validator = null;
    private MessageSource mockMessageSource = null;
    private Errors mockErrors = null;
    
    @Before
    public void setUp() {
        super.setup();
        
        mockErrors = mockContext.mock(Errors.class);
        mockMessageSource = mockContext.mock(ResourceBundleMessageSource.class);
        validator = new AdminUserUpdateValidator();
        validator.setMessageSource(mockMessageSource);
    }
    
    @Test
    public void testSupports() {
        boolean actual = validator.supports(AdminUserUpdateCommand.class);
        Assert.assertTrue(actual);
        actual = validator.supports(AdminUserAddCommand.class);
        Assert.assertFalse(actual);
    }
    
    @Test
    public void testValidateAirportRole() {
        User user = new User();
        String[] roles = new String[]{"BAM"};
        user.setRoles(roles);
        user.setLocation("FCO");
        AdminUserUpdateCommand command = new AdminUserUpdateCommand();
        command.setUser(user);
        validator.validate(command, mockErrors);
    }
    
}

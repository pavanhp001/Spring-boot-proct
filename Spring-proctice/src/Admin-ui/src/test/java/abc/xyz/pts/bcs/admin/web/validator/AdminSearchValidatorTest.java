package abc.xyz.pts.bcs.admin.web.validator;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Errors;

import abc.xyz.pts.bcs.admin.AbstractTestCase;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.web.command.AdminUserAddCommand;
import abc.xyz.pts.bcs.admin.web.command.AdminUserSearchCommand;

public class AdminSearchValidatorTest extends AbstractTestCase {
    private AdminSearchValidator validator = null;
    private MessageSource mockMessageSource = null;
    private Errors mockErrors = null;

    @Before
    public void setUp() {
        super.setup();
        mockErrors = mockContext.mock(Errors.class);
        mockMessageSource = mockContext.mock(ResourceBundleMessageSource.class);
        validator = new AdminSearchValidator();
        validator.setMessageSource(mockMessageSource);
    }
    
    @Test
    public void testSupports() {
        boolean actual = validator.supports(AdminUserSearchCommand.class);
        Assert.assertTrue(actual);
        actual = validator.supports(AdminUserAddCommand.class);
        Assert.assertFalse(actual);
    }
    
    @Test
    public void testValidate() {
        User user = new User();
        String[] roles = new String[]{"IAG"};
        user.setRoles(roles);
        AdminUserSearchCommand command = new AdminUserSearchCommand();
        command.setUser(user);
        mockContext.checking(new Expectations(){
            {
            }
        });
        validator.validate(command, mockErrors);
    }
}

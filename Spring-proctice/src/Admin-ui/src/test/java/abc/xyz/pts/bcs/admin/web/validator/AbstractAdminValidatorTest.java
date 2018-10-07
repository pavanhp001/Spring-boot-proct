package abc.xyz.pts.bcs.admin.web.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Locale;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;

import abc.xyz.pts.bcs.admin.AbstractTestCase;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.web.command.AdminUserAddCommand;
import abc.xyz.pts.bcs.admin.web.command.AdminUserSearchCommand;

public class AbstractAdminValidatorTest extends AbstractTestCase {
	
	private AdminSearchValidator searchValidator;
	private AdminUserAddValidator addValidator;
	private BindException searchErrors;
	private BindException addErrors;
	private AdminUserSearchCommand searchCommand;
	private AdminUserAddCommand addCommand;
	private Mockery context;
	private MessageSource messageSource;
	
	@Override
	@Before
	public void setup() {
	    super.setup();
		context = new Mockery();
		messageSource = context.mock(MessageSource.class);

		searchValidator = new AdminSearchValidator();
		searchValidator.setMessageSource(messageSource);
		searchCommand = new AdminUserSearchCommand();
		searchErrors = new BindException(searchCommand, "command");
		
		addValidator = new AdminUserAddValidator();
		addValidator.setMessageSource(messageSource);
		addCommand = new AdminUserAddCommand();
		addErrors = new BindException(addCommand, "command");
	}

	@Test
	public void testValidateNullRoles() {
		
        User user = new User();
		user.setRoles(null);
        addCommand.setUser(user);
        
		context.checking(new Expectations(){
			{
				one(messageSource).getMessage(with(any(String.class)), with(any(String[].class)), with(any(Locale.class)));
			}
		});
        
        addValidator.validate(addCommand, addErrors);
        assertTrue(addErrors.hasErrors());
	}
	
	@Test
	public void testValidateEmptyRoles() {
		
        User user = new User();
		user.setRoles(new String[]{});
        addCommand.setUser(user);
        
		context.checking(new Expectations(){
			{
				one(messageSource).getMessage(with(any(String.class)), with(any(String[].class)), with(any(Locale.class)));
			}
		});
        
        addValidator.validate(addCommand, addErrors);
        assertTrue(addErrors.hasErrors());
	}
	
	@Test
	public void testValidateAirportRoleNoLocation() {
        User user = new User();
		user.setRoles(new String[]{"PPA"});
        addCommand.setUser(user);
        
		context.checking(new Expectations(){
			{
				one(messageSource).getMessage(with(any(String.class)), with(any(String[].class)), with(any(Locale.class)));
			}
		});
        
    	addValidator.validate(addCommand, addErrors);
    	assertTrue(addErrors.hasErrors());
	}
	
	@Test
	public void testValidateAirportRoleValidLocation() {
        User user = new User();
		user.setRoles(new String[]{"PPA"});
		user.setLocation("TEST");
        addCommand.setUser(user);
        
		context.checking(new Expectations(){
			{
				one(messageSource).getMessage(with(any(String.class)), with(any(String[].class)), with(any(Locale.class)));
			}
		});
        
    	addValidator.validate(addCommand, addErrors);
    	assertFalse(addErrors.hasErrors());
	}
	
}

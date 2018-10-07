package abc.xyz.pts.bcs.admin.dao;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.Control;
import javax.naming.ldap.HasControls;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ldap.core.DirContextAdapter;

import abc.xyz.pts.bcs.admin.AbstractTestCase;
import abc.xyz.pts.bcs.admin.business.UserStatus;
import abc.xyz.pts.bcs.admin.dao.LdapUserAttributesMapper;
import abc.xyz.pts.bcs.admin.dto.User;
import abc.xyz.pts.bcs.admin.ldap.control.AccountUsabilityControl;

public class UserAttributesMapperTest extends AbstractTestCase {

    private Map<String, String> attrNames = null;
    private LdapUserAttributesMapper mapper = null;
    private User user = null;
    
    @Before
    public void setUp() throws Exception{
	super.setup();
        mapper = new LdapUserAttributesMapper();
        attrNames = mockContext.mock(HashMap.class);
        mapper.setAttributeNames(attrNames);
        user = new User();
        user.setUsername("username");
        user.setForename("forename");
        user.setLastname("lastname");
        user.setMobileNumber("123456");
        user.setFaxNumber("123456");
        user.setEmail("test@test.com");
        user.setLocation("FCO");
        user.setPassword("test");
        user.setUserStatus(UserStatus.ENABLED);
        user.setRoles(new String[]{"ADM"});
    }
    
    @Test
    public void testMapToAttributes() throws Exception{
        mockContext.checking(new Expectations(){
            {
                one(attrNames).get(UserAttributesMapper.AttributeName.USERNAME.value());will(returnValue("test1"));
                one(attrNames).get(UserAttributesMapper.AttributeName.FORENAME.value());will(returnValue("test2"));
                one(attrNames).get(UserAttributesMapper.AttributeName.LASTNAME.value());will(returnValue("test3"));
                one(attrNames).get(UserAttributesMapper.AttributeName.NAME.value());will(returnValue("test4"));
                one(attrNames).get(UserAttributesMapper.AttributeName.MOBILE.value());will(returnValue("test5"));
                one(attrNames).get(UserAttributesMapper.AttributeName.FAX.value());will(returnValue("test6"));
                one(attrNames).get(UserAttributesMapper.AttributeName.EMAIL.value());will(returnValue("test7"));
                one(attrNames).get(UserAttributesMapper.AttributeName.LOCATION.value());will(returnValue("test8"));
                one(attrNames).get(UserAttributesMapper.AttributeName.PASSWORD.value());will(returnValue("test11"));
                
            }
        });
        Attributes attrs = mapper.mapToAttributes(user, "ou=test1,cn=test2");
        Assert.assertEquals(11, attrs.size());
    }
    
    @Test
    public void testMapFromContextWithControls() throws Exception{
        final DirContextAdapter ctx = mockContext.mock(DirContextAdapter.class);
        final HasControls hasControls = mockContext.mock(HasControls.class);
        final Attributes attrs = mockContext.mock(Attributes.class);
        final Attribute attr = mockContext.mock(Attribute.class, "attr");
        final Attribute dateAttr = mockContext.mock(Attribute.class, "dateAttr");
        final Control[] controls = new Control[1];
        final Control mockControl = mockContext.mock(Control.class);
        controls[0] = mockControl;
        final byte[] controlData = new byte[]{-95,4,-124,2,2,58};
        final NamingEnumeration namingList = mockContext.mock(NamingEnumeration.class);
        mockContext.checking(new Expectations(){
            {
                one(ctx).getAttributes(); will(returnValue(attrs));
                one(attrNames).get(UserAttributesMapper.AttributeName.USERNAME.value());will(returnValue("test1"));
                one(attrNames).get(UserAttributesMapper.AttributeName.FORENAME.value());will(returnValue("test2"));
                one(attrNames).get(UserAttributesMapper.AttributeName.LASTNAME.value());will(returnValue("test3"));
                one(attrNames).get(UserAttributesMapper.AttributeName.NAME.value());will(returnValue("test4"));
                one(attrNames).get(UserAttributesMapper.AttributeName.MOBILE.value());will(returnValue("test5"));
                one(attrNames).get(UserAttributesMapper.AttributeName.FAX.value());will(returnValue("test6"));
                one(attrNames).get(UserAttributesMapper.AttributeName.EMAIL.value());will(returnValue("test7"));
                one(attrNames).get(UserAttributesMapper.AttributeName.LOCATION.value());will(returnValue("test8"));
                one(attrNames).get(UserAttributesMapper.AttributeName.ACCOUNT_DISABLED.value());will(returnValue("test10"));
                one(attrNames).get(UserAttributesMapper.AttributeName.PASSWORD_CHANGED.value());will(returnValue("test11"));
                one(attrNames).get(UserAttributesMapper.AttributeName.LAST_LOGIN.value());will(returnValue("test12"));
                one(attrNames).get(UserAttributesMapper.AttributeName.ROLE.value());will(returnValue("test13"));
                one(attrNames).get(UserAttributesMapper.AttributeName.MODIFY_TIMESTAMP.value());will(returnValue("test14"));
                
                one(attrs).get("test11");will(returnValue(dateAttr));
                one(attrs).get("test12");will(returnValue(dateAttr));
                exactly(11).of(attrs).get(with(any(String.class)));will(returnValue(attr));
                exactly(10).of(attr).get();will(returnValue("testAttrValue"));
                exactly(2).of(dateAttr).get();will(returnValue("20011201115959Z"));
                one(hasControls).getControls(); will(returnValue(controls));
                one(mockControl).getID(); will(returnValue(AccountUsabilityControl.ID));
                one(mockControl).getEncodedValue(); will(returnValue(controlData));
                exactly(1).of(attr).getAll(); will(returnValue(namingList));
                exactly(1).of(namingList).hasMoreElements(); will(returnValue(false));
            }
        });
        User user = mapper.mapFromContextWithControls(ctx, hasControls);
        Assert.assertNotNull(user);
    }
}

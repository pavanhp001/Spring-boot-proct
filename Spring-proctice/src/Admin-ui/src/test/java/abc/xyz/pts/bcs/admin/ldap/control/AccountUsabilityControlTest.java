package abc.xyz.pts.bcs.admin.ldap.control;

import org.junit.Test;

import junit.framework.Assert;
import abc.xyz.pts.bcs.admin.AbstractTestCase;
import abc.xyz.pts.bcs.admin.ldap.control.AccountUsabilityControl;

public class AccountUsabilityControlTest extends AbstractTestCase {

    private AccountUsabilityControl control;
    
    @Test
    public void testIsInactive() throws Exception{
        byte[] testData = new byte[]{-95,4,-124,2,2,58};
        control = new AccountUsabilityControl(testData);
        boolean actual = control.isInactive();
        Assert.assertTrue(actual);
    }
    
    @Test
    public void testIsNotInactive() throws Exception{
        byte[] testData = new byte[]{-128,1,-1};
        control = new AccountUsabilityControl(testData);
        boolean actual = control.isInactive();
        Assert.assertFalse(actual);
    }

    @Test
    public void testIsReset() throws Exception{
        byte[] testData = new byte[]{-95, 3, -127, 1, -1};
        control = new AccountUsabilityControl(testData);
        boolean actual = control.isInactive();
        Assert.assertFalse(actual);
    }

    // This has a password reset status and a "seconds until unlock" status
    // so the account is inactive
    @Test
    public void testMultipleStatusValues() throws Exception{
        byte[] testData = new byte[]{-95, 7, -127, 1, -1, -124, 2, 2, 0};
        control = new AccountUsabilityControl(testData);
        boolean actual = control.isInactive();
        Assert.assertTrue(actual);
    }

    @Test
    public void testIsInactiveInvalidData() throws Exception{
        byte[] testData = new byte[0];
        control = new AccountUsabilityControl(testData);
        try{
            control.isInactive();
            Assert.fail();
        }
        catch(IllegalArgumentException iae){
            Assert.assertTrue(true);
        }
    }
}

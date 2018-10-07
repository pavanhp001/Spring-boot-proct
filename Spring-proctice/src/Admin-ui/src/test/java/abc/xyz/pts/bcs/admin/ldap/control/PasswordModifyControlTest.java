package abc.xyz.pts.bcs.admin.ldap.control;

import abc.xyz.pts.bcs.admin.AbstractTestCase;
import abc.xyz.pts.bcs.admin.ldap.control.PasswordModifyControl;
import abc.xyz.pts.bcs.admin.ldap.control.PasswordModifyControl.PasswordModifyResponse;
import junit.framework.Assert;
import java.util.Arrays;

import org.junit.Test;

public class PasswordModifyControlTest extends AbstractTestCase {

    @Test
    public void testResetResponse() throws Exception{
        byte[] testData = new byte[]{48, 10, -128, 8, 113, 52, 37, 68, 53, 71, 40, 49};
        PasswordModifyControl control = new PasswordModifyControl("testUser");
        PasswordModifyControl.PasswordModifyResponse response;
        response = (PasswordModifyResponse) control.createExtendedResponse(control.getID(), testData, 0, testData.length);
        Assert.assertEquals(response.getPassword(), "q4%D5G(1");
    }

    @Test
    public void testOffsetLengthResponse() throws Exception{
        byte[] testData = new byte[]{0, 48, 10, -128, 8, 113, 52, 37, 68, 53, 71, 40, 49};
        PasswordModifyControl control = new PasswordModifyControl("testUser");
        PasswordModifyControl.PasswordModifyResponse response;
        response = (PasswordModifyResponse) control.createExtendedResponse(control.getID(), testData, 1, testData.length - 1);
        Assert.assertEquals(response.getPassword(), "q4%D5G(1");
    }

    @Test
    public void testNullResponse() throws Exception{
        byte[] testData = null;
        PasswordModifyControl control = new PasswordModifyControl("testUser");
        PasswordModifyControl.PasswordModifyResponse response;
        response = (PasswordModifyResponse) control.createExtendedResponse(control.getID(), testData, 0,0);
        Assert.assertEquals(response.getPassword(), null);
    }
    
    @Test
    public void testAdminResetRequest() throws Exception {
        byte[] expectedResult = new byte[]{48, 10, -128, 8, 116, 101, 115, 116, 85, 115, 101, 114};
        PasswordModifyControl control = new PasswordModifyControl("testUser");
        Assert.assertTrue(Arrays.equals(control.getEncodedValue(), expectedResult));
    }

    @Test
    public void testAdminChangeRequest() throws Exception {
        byte[] expectedResult = new byte[]{48, 18, -128, 8, 116, 101, 115, 116, 85, 115, 101, 114, -126, 6, 110, 101, 119, 80, 119, 100};
        PasswordModifyControl control = new PasswordModifyControl("testUser", "newPwd");
        Assert.assertTrue(Arrays.equals(control.getEncodedValue(), expectedResult));
    }

    @Test
    public void testUserChangeRequest() throws Exception {
        byte[] expectedResult = new byte[]{48, 26, -128, 8, 116, 101, 115, 116, 85, 115, 101, 114, -127, 6, 111, 108, 100, 80, 119, 100, -126, 6, 110, 101, 119, 80, 119, 100};
        PasswordModifyControl control = new PasswordModifyControl("testUser", "oldPwd", "newPwd");
        Assert.assertTrue(Arrays.equals(control.getEncodedValue(), expectedResult));
    }

    @Test
    public void testLongPwd() throws Exception {
        byte[] testArray = new byte[65537];
        Arrays.fill(testArray, (byte)48);
        String testString = new String(testArray);
        PasswordModifyControl control = new PasswordModifyControl("testUser", "oldPwd", testString);
        Assert.assertTrue(control.getEncodedValue().length == 65565);
    }
}

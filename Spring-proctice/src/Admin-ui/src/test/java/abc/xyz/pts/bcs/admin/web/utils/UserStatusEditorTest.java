package abc.xyz.pts.bcs.admin.web.utils;

import junit.framework.TestCase;
import abc.xyz.pts.bcs.admin.business.UserStatus;

public class UserStatusEditorTest extends TestCase {

    private UserStatusEditor editor;

    public void setUp(){
        editor = new UserStatusEditor();
    }
    
    public void testGetAsText() throws Exception{
        UserStatus testInput = UserStatus.ENABLED;
        editor.setValue(testInput);
        assertEquals(String.valueOf(UserStatus.ENABLED.getValue()),
        			 editor.getAsText());
    }
    
    public void testGetAsTextNull() throws Exception{
        editor.setValue(null);
        assertEquals("", editor.getAsText());
    }
    
    public void testSetAsText() throws Exception{
        editor.setAsText(String.valueOf(UserStatus.ENABLED.getValue()));
        assertEquals(UserStatus.ENABLED, editor.getValue());
    }
    
    public void testSetAsTextNull() throws Exception{
        editor.setAsText(null);
        assertNull(editor.getValue());
    }
}

package abc.xyz.pts.bcs.admin.web.utils;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import abc.xyz.pts.bcs.admin.AbstractTestCase;
import abc.xyz.pts.bcs.admin.web.command.Action;
import abc.xyz.pts.bcs.admin.web.utils.ActionEditor;

public class ActionEditorTest extends AbstractTestCase {

    private ActionEditor editor;

    @Before
    public void setUp(){
        editor = new ActionEditor();
    }
    
    @Test
    public void testGetAsText() throws Exception{
        Action testInput = Action.ADD;
        String expectedResult = Action.ADD.getValue();
        editor.setValue(testInput);
        String result = editor.getAsText();
        Assert.assertTrue(result.equals(expectedResult));
    }
    
    @Test
    public void testGetAsTextNull() throws Exception{
        Object testInput = null;
        String expectedResult = "";
        editor.setValue(testInput);
        String result = editor.getAsText();
        Assert.assertSame(expectedResult, result);
    }
    
    @Test
    public void testSetAsText() throws Exception{
        String testInput = Action.ADD.getValue();
        Action expectedResult = Action.ADD;
        editor.setAsText(testInput);
        Object result = editor.getValue();
        Assert.assertTrue(result.equals(expectedResult));
    }
    
    @Test
    public void testSetAsTextNull() throws Exception{
        String testInput = null;
        editor.setAsText(testInput);
        Object result = editor.getValue();
        Assert.assertNull(result);
    }
}

package abc.xyz.pts.bcs.admin.web.utils;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import abc.xyz.pts.bcs.admin.AbstractTestCase;
import abc.xyz.pts.bcs.admin.web.utils.StringArrayEditor;

public class StringArrayEditorTest extends AbstractTestCase {

    private StringArrayEditor editor;
    
    @Before
    public void setUp(){
        editor = new StringArrayEditor();
    }
    
    @Test
    public void testGetAsText() throws Exception{
        String[] testInput = new String[]{"1","2","3"};
        String expectedResult = "1,2,3";
        editor.setValue(testInput);
        String result = editor.getAsText();
        Assert.assertTrue(result.equals(expectedResult));
    }
    
    @Test
    public void testGetAsTextNull() throws Exception{
        String[] testInput = null;
        String expectedResult = "";
        editor.setValue(testInput);
        String result = editor.getAsText();
        Assert.assertSame(expectedResult, result);
    }
    
    @Test
    public void testSetAsText() throws Exception{
        String testInput = "1,2,3";
        String[] expectedResult = new String[]{"1","2","3"};
        editor.setAsText(testInput);
        Object result = editor.getValue();
        org.junit.Assert.assertArrayEquals(expectedResult, (String[])result);
    }
    
    @Test
    public void testSetAsTextNull() throws Exception{
        String testInput = null;
        editor.setAsText(testInput);
        Object result = editor.getValue();
        Assert.assertNull(result);
    }
}

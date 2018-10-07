package abc.xyz.pts.bcs.common.util;

import java.math.BigDecimal;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Test class for CustomBigDecimalEditor.
 */
public class CustomBigDecimalEditorTest extends TestCase {

    private CustomBigDecimalEditor cbde;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        cbde = new CustomBigDecimalEditor();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        cbde = null;
    }

    @Test
    public void testGetAsText(){
        cbde.setValue(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN.toString(), cbde.getAsText());
        cbde.setValue(null);
        assertEquals("", cbde.getAsText());
        cbde.setValue("test");
        assertEquals("", cbde.getAsText());
    }

    @Test
    public void testSetAsText(){
        cbde.setValue(BigDecimal.ONE);
        cbde.setAsText(BigDecimal.TEN.toString());
        assertEquals(BigDecimal.TEN,(BigDecimal)cbde.getValue());
        cbde.setValue(BigDecimal.ONE);
        cbde.setAsText(null);
        assertNull(cbde.getValue());
        cbde.setValue(BigDecimal.ONE);
        cbde.setAsText("");
        assertNull(cbde.getValue());
    }
}

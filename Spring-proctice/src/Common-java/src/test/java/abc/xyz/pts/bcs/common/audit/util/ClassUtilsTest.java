package abc.xyz.pts.bcs.common.audit.util;

import java.lang.reflect.Field;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class ClassUtilsTest {

    private class TestClass {
        public String field1;

        protected String field2;
 
        private Long field3;
    }

    private class TestClass2 extends TestClass {
 
        public String field1a;
 
        protected String field2a;

        private Long field3a;
    }

    @Test
    public void testGetFields() throws Exception{
        List<Field> fields = new ClassUtils().getFields(TestClass2.class);
        // 6 specified fields + two java-generated fields for "this"
        Assert.assertEquals(8, fields.size());
    }
}

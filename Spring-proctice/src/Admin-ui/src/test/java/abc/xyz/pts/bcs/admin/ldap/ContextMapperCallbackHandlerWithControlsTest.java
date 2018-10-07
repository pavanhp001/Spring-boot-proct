package abc.xyz.pts.bcs.admin.ldap;

import javax.naming.Binding;
import javax.naming.NameClassPair;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ldap.core.ObjectRetrievalException;

import abc.xyz.pts.bcs.admin.AbstractTestCase;

public class ContextMapperCallbackHandlerWithControlsTest extends AbstractTestCase {

    private ContextMapperCallbackHandlerWithControls mapperCallback = null;
    private ParameterizedContextMapperWithControls<?> mockMapper = null;
    
    @Before
    public void setUp(){
        super.setup();
	mockMapper = mockContext.mock(ParameterizedContextMapperWithControls.class);
        mapperCallback = new ContextMapperCallbackHandlerWithControls(mockMapper);
    }
    
    @Test
    public void testGetObjectFromNameClassPair() throws Exception{
        final Binding ncp = mockContext.mock(Binding.class);
        final Object ncpObject = new Object();
        final Object expected = new Object();
        mockContext.checking(new Expectations(){
            {
                one(ncp).getObject();will(returnValue(ncpObject));
                one(mockMapper).mapFromContext(ncpObject); will(returnValue(expected));
            }
        });
        Object actual = mapperCallback.getObjectFromNameClassPair(ncp);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void testGetObjectFromNameClassPairInvalidArgument() throws Exception{
        final NameClassPair ncp = mockContext.mock(NameClassPair.class);
        
        try{
            Object actual = mapperCallback.getObjectFromNameClassPair(ncp);
            Assert.fail();
        }
        catch(IllegalArgumentException iae){
            Assert.assertTrue(true);
        }
    }
    
    @Test
    public void testGetObjectFromNameClassPairNullObject() throws Exception{
        final Binding ncp = mockContext.mock(Binding.class);
        mockContext.checking(new Expectations(){
            {
                one(ncp).getObject();will(returnValue(null));
            }
        });
        try{
            mapperCallback.getObjectFromNameClassPair(ncp);
            Assert.fail();
        }
        catch(ObjectRetrievalException iae){
            Assert.assertTrue(true);
        }
    }
}

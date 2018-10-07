package abc.xyz.pts.bcs.admin.business.impl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import abc.xyz.pts.bcs.admin.AbstractTestCase;
import abc.xyz.pts.bcs.admin.business.impl.SimpleRandomPasswordGenerator;

public class RandomPasswordGeneratorTest extends AbstractTestCase {

    private SimpleRandomPasswordGenerator generator;
    
    @Before
    public void setUp(){
        generator = new SimpleRandomPasswordGenerator();
    }
    
    @Test
    public void testGenerate() throws Exception{
        String newPassword = generator.generate();
        Assert.assertNotNull(newPassword);
    }
}

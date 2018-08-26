/**
 *
 */
package com.AL.ome.audit;

/**
 * @author ebthomas
 *
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.ome.system.SimpleBaseALTestBase;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class TimeServiceTest extends SimpleBaseALTestBase {
 
     
     
    
    /**
     * @throws Exception
     */

    public void setUp() throws Exception {

    }
    
    
    @Test
    public void testServiceAudit() throws Exception {
          
        Thread.sleep( 30000 );
    }
  
}

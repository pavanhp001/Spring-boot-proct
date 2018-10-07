/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.admin;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;

public abstract class AbstractTestCase {
    
    private static final String PERMISSION="permission";
    
    protected Mockery mockContext = new JUnit4Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    static{
        //All expectations chosen when log4j is set to debug
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.DEBUG);
    }
    
    @After
    public void end() {
        mockContext.assertIsSatisfied();
    }

    public void setup() {
        System.setProperty(PERMISSION + ".adm", "adm_r");
        System.setProperty(PERMISSION + ".bam", "bam_r");
        System.setProperty(PERMISSION + ".int", "int_r");
        System.setProperty(PERMISSION + ".dan", "dan_r");
        System.setProperty(PERMISSION + ".iag", "iag_r");
        
/*        categoriesRolesMap = new HashMap<String, List<String>>();
        List<String> bcsList = new ArrayList<String>();
        bcsList.add("HOS");
        bcsList.add("PPA");
        List<String> iagList = new ArrayList<String>();
        iagList.add("IAG");
        categoriesRolesMap.put("BCS", bcsList);
        categoriesRolesMap.put("IAG", iagList);*/
        
    }
}

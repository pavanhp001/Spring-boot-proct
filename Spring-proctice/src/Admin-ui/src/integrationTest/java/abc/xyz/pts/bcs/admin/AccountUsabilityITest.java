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

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.BasicControl;
import javax.naming.ldap.Control;
import javax.naming.ldap.HasControls;
import javax.naming.ldap.InitialLdapContext;

import org.junit.Ignore;
import org.springframework.context.ApplicationContext;

import com.sun.jndi.ldap.BerDecoder;

@Ignore 
public final class AccountUsabilityITest {

    private ApplicationContext context = null;
    private static final String[] CONFIG_FILES = {"file:src/main/webapp/WEB-INF/SpringConfig/admin_ldap.xml",
            "file:src/main/webapp/WEB-INF/SpringConfig/admin_business.xml" };

    private AccountUsabilityITest() {
        // context = new ClassPathXmlApplicationContext(CONFIG_FILES);
        runTests();
    }

    public void runTests() {
        testControl();
    }

    private void testControl() {
        final Properties env = new Properties();

        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.setProperty(Context.SECURITY_AUTHENTICATION, "simple");
        env.setProperty(Context.PROVIDER_URL, "ldap://camq2:389");
        env.setProperty(Context.SECURITY_PRINCIPAL, "uid=testapp,ou=People,dc=pts,dc= ,dc=aero");
        env.put(Context.SECURITY_CREDENTIALS, "t3st4pp");
        try {
            InitialLdapContext ctx = new InitialLdapContext(env, new Control[0]);
            BasicControl accUsablilityCtrl = new BasicControl("1.3.6.1.4.1.42.2.27.9.5.8", true, null);
            Control[] requestControls = new Control[] {accUsablilityCtrl };
            ctx.setRequestControls(requestControls);
            Attributes matchAttrs = new BasicAttributes(true); // ignore attribute name case
            matchAttrs.put(new BasicAttribute("uid", "SUP"));
            matchAttrs.put(new BasicAttribute("objectclass", "inetOrgPerson"));
            NamingEnumeration<SearchResult> results = ctx.search("ou=Users,ou=BCS,dc=pts,dc= ,dc=aero", matchAttrs);
            while (results.hasMoreElements()) {
                SearchResult sr = results.next();
                if (sr instanceof HasControls) {
                    HasControls hasControls = (HasControls) sr;
                    Control[] responseCtrls = hasControls.getControls();
                    for (int i = 0; i < responseCtrls.length; i++) {
                        Control ctrl = responseCtrls[i];
                        if (ctrl.getID().equals("1.3.6.1.4.1.42.2.27.9.5.8")) {
                            byte[] encodedValue = ctrl.getEncodedValue();
                            BerDecoder.dumpBER(System.out, "test", encodedValue, 0, encodedValue.length);
                            System.out.println(ctrl.getID() + " : " + encodedValue);
                            BerDecoder berDecoder = new BerDecoder(encodedValue, 0, encodedValue.length);
                            if (berDecoder.peekByte() == TYPE_MORE_INFO) {
                                // berDecoder.parseByte();
                                berDecoder.parseSeq(null);
                                int t1 = berDecoder.parseByte();
                                System.out.println(t1);
                                boolean testResult = berDecoder.parseBoolean();
                                System.out.println(testResult);
                            } else if (berDecoder.peekByte() == TYPE_SECONDS_BEFORE_EXPIRATION) {
                                int testResult = berDecoder.parseInt();
                            }

                            // boolean inactive = berDecoder.parseBoolean();

                        }
                    }
                }
                Attributes attrs = sr.getAttributes();
                System.out.println(attrs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) {
        AccountUsabilityITest test = new AccountUsabilityITest();
    }

    public static final int TYPE_MORE_INFO = 0xA1;
    public static final int TYPE_SECONDS_BEFORE_EXPIRATION = 0x80;

}

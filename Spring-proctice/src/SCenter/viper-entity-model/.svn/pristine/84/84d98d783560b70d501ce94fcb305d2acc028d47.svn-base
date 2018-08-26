package com.A.V.utility;

import java.util.Hashtable;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import org.ietf.ldap.LDAPAttribute;
import org.ietf.ldap.LDAPConnection;
import org.ietf.ldap.LDAPException;
import com.A.V.beans.entity.UserBean;

/**
 * 
 * @author rchapple
 * 
 */

public class LDAPAuthenticator extends AuthenticationMechanism
{
    private String ldapServerAddr;
    
    private static final int MAX_LDAP_VALUES = 11;

    /**
     * Default constructor.
     */
    public LDAPAuthenticator()
    {
        // Do nothing.
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void doAuthentication()
    {
        // Do nothing.
    }

    /**
     * @param lc
     *            The connection to the LDAP server
     * @param objectDN
     *            The DN of the user who's password is being validated
     * @param objectPassword
     *            The user password to be validated
     * @return a UserBean (detached) containing the user information for the credentials given return null if user was not
     *         authenticated. This user bean is only a data object, it is not persisted by Hibernate, its just used to carry user
     *         data to the next step.
     * @throws LDAPException
     *             if there is any error while communicating with the LDAP Server.
     */

    public UserBean validateCredentials( final LDAPConnection lc, final String objectDN, final String objectPassword )
            throws LDAPException
    {
        boolean passwordValid = false;
        UserBean user = new UserBean();

        try
        {
            LDAPAttribute attr = new LDAPAttribute( "userPassword", objectPassword );

            passwordValid = lc.compare( objectDN, attr );

//            if ( passwordValid )
//            {
//                // Do nothing.
//            }

            // disconnect from the server
            lc.disconnect();

        }
        catch ( LDAPException e )
        {
            // Throw this one up so that the caller can see why the call failed
            throw e;
        }
        catch ( Exception e )
        {
            System.out.println( "Error: " + e.toString() );
        }

        // Return whatever result we got.
        if ( passwordValid )
        {
            return user;
        }
        else
        {
            return null;
        }

    }

    /**
     * Validate the user with LDAP and return a Vector with user details and return a vector of email if authenticated.
     * 
     * @param userName
     *            ldap user id
     * @param password
     *            ldap password
     * @return Vector user details with email
     */

    public Attributes authenticateUser( final String userName, final String password )
    {
        Hashtable<String, String> authEnv = new Hashtable<String, String>( MAX_LDAP_VALUES );
        Attributes attrs = null;

        String sLdapServer = "", sLdapPort = ""; // , sLdapBase="";

        String base = "";
        sLdapServer = "sgldap";
        sLdapPort = "10389";
        base = "ou=users,ou=internal,dc=A,dc=com";
        String dn = "uid=" + userName + "," + base;

        authEnv.put( Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory" );
        authEnv.put( Context.PROVIDER_URL, "ldap://" + sLdapServer + ":" + sLdapPort );
        authEnv.put( Context.SECURITY_AUTHENTICATION, "simple" );
        authEnv.put( Context.SECURITY_PRINCIPAL, dn );
        authEnv.put( Context.SECURITY_CREDENTIALS, password );

        try
        {
            DirContext authContext = new InitialDirContext( authEnv );
            attrs = authContext.getAttributes( dn );
        }
        catch ( AuthenticationException authEx )
        {
            authEx.printStackTrace();
        }
        catch ( NamingException namEx )
        {
            namEx.printStackTrace();
        }

        return attrs;
    }

    /**
     * This convenience method creates a connection to the LDAP server specified, using the credentials specified.
     * 
     * @param ldapHost
     *            the host name (or IP address) of the LDAP server
     * @param ldapPort
     *            the port that the LDAP service listens on
     * @param loginDN
     *            the DN of the entry that has permission to read other entries
     * @param loginPassword
     *            the password of the loginDN
     * @return the LDAPConnection that was created
     * @throws LDAPException
     *             is thrown if there are any issues connecting to the LDAP server
     */
    public LDAPConnection createLDAPConnection( final String ldapHost, final String ldapPort, final String loginDN,
            final String loginPassword ) throws LDAPException
    {
        LDAPConnection lc = new LDAPConnection();
        int port = Integer.parseInt( ldapPort );

        try
        {
            // connect to the server
            lc.connect( ldapHost, port );

            // authenticate to the server
            // lc.bind( LDAPConnection.LDAP_V3, loginDN, loginPassword.getBytes( "UTF8" ) );
        }
        catch ( LDAPException le )
        {
            le.printStackTrace();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return lc;
    }

    public String getLdapServerAddr()
    {
        return ldapServerAddr;
    }

    public void setLdapServerAddr( final String addr )
    {
        this.ldapServerAddr = addr;
    }

}

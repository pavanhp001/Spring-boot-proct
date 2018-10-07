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
package abc.xyz.pts.bcs.admin.ldap.control;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.naming.NamingException;
import javax.naming.ldap.ExtendedRequest;
import javax.naming.ldap.ExtendedResponse;

import org.apache.log4j.Logger;

import com.sun.jmx.snmp.BerDecoder;
import com.sun.jmx.snmp.BerEncoder;
import com.sun.jmx.snmp.BerException;
import com.sun.jndi.ldap.Ber;

/**
 * @author simona
 */

/*
 * PasswdModifyRequestValue ::= SEQUENCE { userIdentity [0] OCTET STRING OPTIONAL oldPasswd [1] OCTET STRING OPTIONAL
 * newPasswd [2] OCTET STRING OPTIONAL }
 *
 * PasswdModifyResponseValue ::= SEQUENCE { genPasswd [0] OCTET STRING OPTIONAL }
 */
@SuppressWarnings("serial")
public class PasswordModifyControl implements ExtendedRequest {

    private static final Logger LOGGER = Logger.getLogger(PasswordModifyControl.class);
    public static final String PWD_MODIFY_OID = "1.3.6.1.4.1.4203.1.11.1";
    private static final byte[] EMPTY_ARRAY = new byte[0];

    public class PasswordModifyResponse implements ExtendedResponse {

        private final byte[] berResponse;

        public PasswordModifyResponse(final byte[] berResponse, final int offset, final int length) {
            if (berResponse != null && (offset > 0 || length != berResponse.length)) {
                this.berResponse = new byte[length];
                System.arraycopy(berResponse, offset, this.berResponse, 0, length);
            } else {
                this.berResponse = berResponse;
            }
        }

        @Override
        public String getID() {
            return PWD_MODIFY_OID;
        }

        @Override
        public byte[] getEncodedValue() {
            return berResponse;
        }

        // Returns generated password, or null if no password was returned
        public String getPassword() {

            if (berResponse == null) {
                return null;
            }

            BerDecoder berDecoder = new BerDecoder(berResponse);
            String genPwd = null;

            try {
                berDecoder.openSequence();
                genPwd = new String(berDecoder.fetchOctetString(Ber.ASN_CONTEXT));
            } catch (BerException e) {
                LOGGER.debug("Unable to get new password", e);
                throw new IllegalArgumentException("Unable to reset password", e);
            }

            return genPwd;
        }
    }

    private final byte[] userId;
    private final byte[] oldPwd;
    private final byte[] newPwd;

    public PasswordModifyControl(final String userId, final String oldPwd, final String newPwd)
            throws UnsupportedEncodingException {
        this.userId = userId.getBytes("utf-8");
        this.oldPwd = (oldPwd == null) ? EMPTY_ARRAY : oldPwd.getBytes("utf-8");
        this.newPwd = (newPwd == null) ? EMPTY_ARRAY : newPwd.getBytes("utf-8");
    }

    public PasswordModifyControl(final String userId, final String newPwd) throws UnsupportedEncodingException {
        this(userId, null, newPwd);
    }

    public PasswordModifyControl(final String userId) throws UnsupportedEncodingException {
        this(userId, null);
    }

    @Override
    public String getID() {
        return PWD_MODIFY_OID;
    }

    // Returns the number of bytes needed to encode the tag, data length
    // and the byte array content
    private int numEncBytes(final byte[] array) {

        int length = array.length;

        if (length == 0) {
            return 0;
        }

        return numTagEncBytes(length) + length; // The tag is a single byte
    }

    // Returns the number of bytes needed to encode the tag amd length value
    private int numTagEncBytes(final int length) {

        int numLengthBytes = 0;

        // If the length is < 128, then only a single byte is needed, otherwise
        // an extra single byte tag is used in addition to the bytes needed
        // to encode the length
        if (length < 0x80) {
            numLengthBytes = 1;
        } else if (length < 0xFF) {
            numLengthBytes = 2;
        } else if (length < 0xFFFF) {
            numLengthBytes = 3;
        } else if (length < 0xFFFFFF) {
            numLengthBytes = 4;
        } else {
            numLengthBytes = 5;
        }

        return numLengthBytes + 1; // The tag is always a single byte in this control
    }

    @Override
    public byte[] getEncodedValue() {

        // The size of the sequence content
        int maxSize = numEncBytes(userId);
        maxSize += numEncBytes(oldPwd);
        maxSize += numEncBytes(newPwd);

        // The number of bytes needed to encode the sequence tag and length
        maxSize += numTagEncBytes(maxSize);

        // Create the array used by the BerEncoder
        byte[] encodingArray = new byte[maxSize];

        BerEncoder ber = new BerEncoder(encodingArray);

        // The encoding process is slightly odd - the entries must be added in reverse order!
        ber.openSequence();

        // Encode optional new password
        if (newPwd.length > 0) {
            ber.putOctetString(newPwd, Ber.ASN_CONTEXT | 0x2);
            Arrays.fill(newPwd, (byte) 0); // Zero out the content
        }

        // Encode optional old password
        if (oldPwd.length > 0) {
            ber.putOctetString(oldPwd, Ber.ASN_CONTEXT | 0x1);
            Arrays.fill(oldPwd, (byte) 0); // Zero out the context
        }

        // Encode user name
        ber.putOctetString(userId, Ber.ASN_CONTEXT);

        // Complete the sequence
        ber.closeSequence(Ber.ASN_SEQUENCE | Ber.ASN_CONSTRUCTOR);

        // We've sized the array correctly, so no need to call trim()
        return encodingArray;
    }

    @Override
    public ExtendedResponse createExtendedResponse(final String id, final byte[] berValue, final int offset,
            final int length) throws NamingException {

        if (length == 0) {
            LOGGER.debug("No extended response returned");
        }

        return new PasswordModifyResponse(berValue, offset, length);

    }
}

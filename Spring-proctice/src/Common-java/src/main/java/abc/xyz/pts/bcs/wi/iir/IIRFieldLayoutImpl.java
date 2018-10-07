/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
/**
 * @author MMotlib-Siddiqui
 */
package abc.xyz.pts.bcs.wi.iir;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import abc.xyz.pts.bcs.wi.iir.exception.IIRSearchException;

public final class IIRFieldLayoutImpl implements IIRFieldLayout
{

    private String name;
    private int length;
    private int offset;
    private int repeat;
    private String format;

    public IIRFieldLayoutImpl()
    {

    }

    public IIRFieldLayoutImpl(final IIRFieldLayout field)
    {
        this.name = field.getName();
        this.length = field.getLength();
        this.offset = field.getOffset();
        this.repeat = field.getRepeat();
        this.format = field.getFormat();
    }

    public IIRFieldLayoutImpl(final String name, final int len, final int offset, final int repeat, final String fmt)
    {
        this.name = name;
        this.length = len;
        this.offset = offset;
        this.repeat = repeat;
        this.format = fmt;
    }

    @Override
    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    @Override
    public int getOffset()
    {
        return offset;
    }

    public void setOffset(final int offset)
    {
        this.offset = offset;
    }

    @Override
    public int getRepeat()
    {
        return repeat;
    }

    public void setRepeat(final int repeat)
    {
        this.repeat = repeat;
    }

    @Override
    public String getFormat()
    {
        return format;
    }

    public void setFormat(final String format)
    {
        this.format = format;
    }

    @Override
    public int getLength()
    {
        return length;
    }

    public void setLength(final int length)
    {
        this.length = length;
    }

    @Override
    public byte[] getSearchReqRecord(final String value) throws IIRSearchException
    {
        final byte[] buf = new byte[length];
        int encodedLength = 0;

        if (value != null)
        {
            byte[] encodedString;

            try {
                encodedString = value.getBytes("UTF-8");
            } catch (final UnsupportedEncodingException e) {
                throw new IIRSearchException("encoding string to UTF-8", null, "Unable to encode to UTF-8");
            }

            encodedLength = Math.min(length, encodedString.length);
            System.arraycopy(encodedString, 0, buf, 0, encodedLength);
        }

        Arrays.fill(buf, encodedLength, length, (byte)' ');

        return buf;
    }

}

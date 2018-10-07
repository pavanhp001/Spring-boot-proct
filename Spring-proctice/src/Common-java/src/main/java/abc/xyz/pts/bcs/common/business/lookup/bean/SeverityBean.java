/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.business.lookup.bean;

public class SeverityBean implements Comparable<ReferralStatusBean>
{
    private String code;
    private String description;


    public SeverityBean(final String code)
    {
        super();
        this.code = code;
        this.description = code;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        return result;
    }


    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SeverityBean other = (SeverityBean) obj;
        if (code == null)
        {
            if (other.code != null) {
                return false;
            }
        }
        else if (!code.equals(other.code)) {
            return false;
        }
        if (description == null)
        {
            if (other.description != null) {
                return false;
            }
        }
        else if (!description.equals(other.description)) {
            return false;
        }
        return true;
    }


    @Override
    public int compareTo(final ReferralStatusBean o)
    {
        return this.getCode().compareTo(o.getCode());
    }



    public String getCode()
    {
        return code;
    }



    public void setCode(final String code)
    {
        this.code = code;
    }



    public String getDescription()
    {
        return description;
    }



    public void setDescription(final String description)
    {
        this.description = description;
    }



}

